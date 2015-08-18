package com.github.eyce9000.iem.api.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;

import com.bigfix.schemas.bes.BES;
import com.bigfix.schemas.besapi.BESAPI;
import com.github.eyce9000.iem.api.RelevanceAPI;
import com.github.eyce9000.iem.api.relevance.DataType;
import com.github.eyce9000.iem.api.relevance.QueryResultColumn;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;
import com.github.eyce9000.iem.api.relevance.results.QueryResult;
import com.github.eyce9000.iem.api.relevance.results.ResultTuple;
import com.google.common.base.Optional;

public abstract class AbstractIEMAPI  implements RelevanceAPI{
	protected Unmarshaller	besUnmarshaller;

	protected <T> Optional<T> getSingleBESContent(Response resp, Class<T> clazz){
		if(resp.getStatus()==404)
			return Optional.absent();
		List<T> list = getBESContent(resp,clazz);
		if(list.isEmpty())
			return Optional.absent();
		else
			return Optional.of(list.get(0));
	}
	
	protected <T> Optional<T> getSingleBESContent(HttpResponse resp, Class<T> clazz){
		if(resp.getStatusLine().getStatusCode()==404)
			return Optional.absent();
		List<T> list = getBESContent(resp,clazz);
		if(list.isEmpty())
			return Optional.absent();
		else
			return Optional.of(list.get(0));
	}
	
	protected <T> List<T> getBESContent(BES bes){
		List<T> values = new ArrayList<T>();
		for(Object object : bes.getFixletOrTaskOrAnalysis()){
			values.add((T)object);
		}
		return values;
	}
	
	protected <T> List<T> getBESContent(Response resp, Class<T> clazz){
		BES bes = handleResponse(resp,BES.class);
		return getBESContent(bes);
	}
	protected <T> List<T> getBESContent(HttpResponse resp, Class<T> clazz){
		BES bes = handleResponse(resp,BES.class);
		return getBESContent(bes);
	}

	protected <T> Optional<T> getSingleBESAPIContent(Response resp, Class<T> clazz){
		if(resp.getStatus()==404)
			return Optional.absent();
		List<T> list = getBESAPIContent(resp,clazz);
		if(list.isEmpty())
			return Optional.absent();
		else
			return Optional.of(list.get(0));
	}
	

	protected <T> Optional<T> getSingleBESAPIContent(HttpResponse resp, Class<T> clazz){
		if(resp.getStatusLine().getStatusCode()==404)
			return Optional.absent();
		List<T> list = getBESAPIContent(resp,clazz);
		if(list.isEmpty())
			return Optional.absent();
		else
			return Optional.of(list.get(0));
	}
	
	protected <T> List<T> getBESAPIContent(Response resp, Class<T> clazz){
		BESAPI besapi = handleResponse(resp,BESAPI.class);
		return getBESAPIContent(besapi);
	}
	
	protected <T> List<T> getBESAPIContent(HttpResponse resp, Class<T> clazz){
		BESAPI besapi = handleResponse(resp,BESAPI.class);
		return getBESAPIContent(besapi);
	}

	protected <T> List<T> getBESAPIContent(BESAPI besapi) {
		List<T> values = new ArrayList<T>();
		for(JAXBElement<?> wrapper : besapi.getFixletOrReplicationServerOrReplicationLink()){
			values.add((T)wrapper.getValue());
		}
		return values;
	}
	
	protected <T> void putBESContent(WebTarget target,T value,Class<T> clazz){
		BES bes = new BES();
		bes.getFixletOrTaskOrAnalysis()
			.add(value);

		Entity<BES> entity = Entity.entity(bes, MediaType.APPLICATION_XML);
		Response response = target.request().put(entity);
		handleResponse(response,String.class);
	}

	protected <T> Response postBESContent(WebTarget target,T value){
		BES bes = new BES();
		bes.getFixletOrTaskOrAnalysis()
			.add(value);

		Entity<BES> entity = Entity.entity(bes, MediaType.APPLICATION_XML);
		return target.request().post(entity);
	}
	
	protected <T> T handleResponse(Response resp,Class<T> clazz){
		if(resp.getStatus()>=200 && resp.getStatus()<300){
			return resp.readEntity(clazz);
		}
		else{
			try{
				String error = "";
				BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)resp.getEntity()));
				String line = null;
				while((line=reader.readLine())!=null)
					error += line +"\n";
				throw new BadRequestException(resp.getStatus()+": "+error);
			}
			catch(Exception ex){
				throw new BadRequestException(ex);
			}
		}
	}
	

	protected <T> T handleResponse(HttpResponse resp,Class<T> clazz){
		int code =resp.getStatusLine().getStatusCode(); 
		if(code>=200 && code<300){
			try {
				return (T)besUnmarshaller.unmarshal(resp.getEntity().getContent());
			} catch (Exception e) {
				throw new BadRequestException(e);
			}
		}
		else{
			try{
				String error = "";
				BufferedReader reader = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
				String line = null;
				while((line=reader.readLine())!=null)
					error += line +"\n";
				throw new BadRequestException(code+": "+error);
			}
			catch(Exception ex){
				throw new BadRequestException(ex);
			}
		}
	}
	
	protected WebTarget buildSiteTarget(WebTarget base, String siteType, String site){
		WebTarget target;
		target = base.path("{siteType}")
				.resolveTemplate("siteType", siteType);
		if(site!=null){
			target = target.path("/{site}").resolveTemplate("site", site);
		}
		return target;
	}
	

	//*******************************************************************
	// R E L E V A N C E   M E T H O D S
	//*******************************************************************
	
	
	
	
}
