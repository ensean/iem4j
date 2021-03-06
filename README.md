#IEM4J - Java Library for Bigfix / IBM Endpoint Manager (IEM) APIs#

##About the Library##
This library is a wrapper of the Bigfix (IBM Endpoint Manager / IEM) REST API and Webreports API. This library does not cover the functionality of the REST API 100%, but is mostly focused on content (fixlet) manipulation.

IEM4J uses the Bigfix RESTAPI schema version 9.2 and higher.

##Build Instruction##
```bash
git clone https://github.com/eyce9000/iem4j.git
cd iem4j
mvn install -Dmaven.test.skip=true
```

##Use as Maven Dependency##
In order to use this library as a maven dependency you will need to add the following repository:
```xml
<repositories>
  <repository>
    <id>jcenter</id>
    <url>https://jcenter.bintray.com/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>
```
Then add this dependency:
```xml
<dependency>
  <groupId>com.github.eyce9000</groupId>
  <artifactId>iem4j-client</artifactId>
  <version>2.0.2</version>
</dependency>
```

##Using the Library##

###Reading, Creating and Uploading Fixlets###
You can upload and download ActionFixlets, Tasks, Analyses and Baselines. All of the objects conform to the BES.xml schema, so they can be serialized and deserialized using JAXB from your existing .bes files.
```java
RESTAPI client = new RESTAPI("hostname","username","password");

//Reading Fixlets
Fixlet patchFixlet1 = client.getFixlet("custom","Patching",1);

//Uploading an existing fixlet
JAXBContext ctx = new JAXBContext(BES.class);
Unmarshaller unmarshaller = ctx.createUnmarshaller();
BES bes = (BES)unmarshaller.unmarshall(new File("mycontent.bes"))

Fixlet myContent = (Fixlet)bes.getFixletOrTaskOrAnalysis().get(0);
client.createFixlet("custom","Patching",myContent);

//Uploading an entire .bes file
client.importContent("custom","All-Patching",bes);

```

###Creating and Running Actions###
Some tools are provided to make building actions easier. The following is an example of building a SingleAction (as opposed to a SourcedFixletAction) and its actionscript using the appropriate builder tools.

```java
List<Long> computerIds = Arrays.asList(1,2,3,4);

//Build the actionscript
ActionScriptBuilder builder = ActionScriptBuilder.start()
        .lines("setting \"test\"=\"new value\" on \"{now}\" for client");

//Build the SingleAction
SingleAction action = SingleActionBuilder.singleAction()
        .setExpirationTimeOffset(Period.days(2))
        .relevance("true")
        .actionScript(builder.build())
        .title("Change computer setting local time")
        .build();
action.setTarget(ActionTargetBuilder.targetComputers(computerIds));

//Start the action
BESAPI.Action actionReference = client.createFixletSourcedAction(action);

//Check the action status
BESAPI.ActionResults actionResults = client.getActionStatus(actionReference);

for(ComputerResultType computerResult:actionResults.getComputer()){
  System.out.println(
    "Got status "+computerResult.getStatus()+" for computer "+computerResult.getID());
}

```

### Relevance Queries ###

Relevance queries can be run using both the REST API and Webreports API. Both clients accept SessionRelevanceQuery objects, which wrap a normal session relevance query and provide support for named columns.

```java
//Run Relevance Query through REST API
SessionRelevanceQuery query = SessionRelevanceBuilder
    .fromRelevance("(name of it, id of it) of bes computers")
    .addColumns("computerName","computerId")
    .build();

RelevanceAPI client = new RESTAPI(
    "hostname",
    "consoleUsername",
    "consolePassword");
List<Map<String,Object>> restResults = client.executeQuery(query);

//Run Relevance Query through Webreports API
RelevanceAPI relevanceClient = new WebreportsAPI(
   "hostname",
   "webreportsUsername",
   "webreportsPassword");

List<Map<String,Object>> webreportsResults = relevanceClient.executeQuery(query);
```

You can also read relevance results into Pojos using JAXB or Jackson. For example the following Pojo with Jackson annotations:
```java
public class MyComputer{
  @JsonProperty("computerId")
  long id;
  @JsonProperty("computerName")
  String name;
  @JsonProperty("osName")
  String os;
}
```
Can be read as follows:
```java
SessionRelevanceQuery query = SessionRelevanceBuilder
    .fromRelevance("(name of it, id of it, operating system of it) of bes computers")
    .addColumns("computerName","computerId","osName")
    .build();

List<MyComputer> computers = relevanceClient.executeQuery(query,MyComputer.class);
```

### Rebuilding Baselines ###

This library includes code for re-building baselines when they become out-of-sync with their source fixlets.

The code attempts to build the baseline so that it exactly matches what Bigfix is looking for when checking for out-of-sync baselines, but in some cases it may not match exactly character for character. This will be true with new releases of Bigfix where the formatting of generated relevance changes slightly.

```java
RESTAPI client = new RESTAPI("hostname","username","password");

//Create synchronizer object
BaselineSynchronizer sync = new BaselineSynchronizer(client);

//Get your baseline
Baseline baseline = client.getBaseline("custom","My Site",123456);
//Rebuild the components of the baseline from their source fixlets
sync.rebuildBaselineComponents(baseline);

//Re-upload your baseline
client.updateBaseline("custom","My Site", 123456,baseline);

```
