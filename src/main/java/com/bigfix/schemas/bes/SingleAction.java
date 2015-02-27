//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.05 at 04:58:40 PM CST 
//


package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SingleAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SingleAction">
 *   &lt;complexContent>
 *     &lt;extension base="{}Action">
 *       &lt;sequence>
 *         &lt;element name="Settings" type="{}ActionSettings" minOccurs="0"/>
 *         &lt;element name="SettingsLocks" type="{}ActionSettingsLocks" minOccurs="0"/>
 *         &lt;element name="IsUrgent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Domain" type="{}Domain" minOccurs="0"/>
 *         &lt;element name="Target" type="{}BESActionTarget" minOccurs="0"/>
 *         &lt;element name="SourceFixlet" type="{}BESSourceSiteAndFixlet" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SkipUI" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SingleAction", propOrder = {
    "settings",
    "settingsLocks",
    "isUrgent",
    "domain",
    "target",
    "sourceFixlet"
})
public class SingleAction
    extends Action
{

    @XmlElement(name = "Settings")
    protected ActionSettings settings;
    @XmlElement(name = "SettingsLocks")
    protected ActionSettingsLocks settingsLocks;
    @XmlElement(name = "IsUrgent")
    protected Boolean isUrgent;
    @XmlElement(name = "Domain")
    protected String domain;
    @XmlElement(name = "Target")
    protected BESActionTarget target;
    @XmlElement(name = "SourceFixlet")
    protected BESSourceSiteAndFixlet sourceFixlet;
    @XmlAttribute(name = "SkipUI")
    protected Boolean skipUI;

    /**
     * Gets the value of the settings property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettings }
     *     
     */
    public ActionSettings getSettings() {
        return settings;
    }

    /**
     * Sets the value of the settings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettings }
     *     
     */
    public void setSettings(ActionSettings value) {
        this.settings = value;
    }

    /**
     * Gets the value of the settingsLocks property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettingsLocks }
     *     
     */
    public ActionSettingsLocks getSettingsLocks() {
        return settingsLocks;
    }

    /**
     * Sets the value of the settingsLocks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettingsLocks }
     *     
     */
    public void setSettingsLocks(ActionSettingsLocks value) {
        this.settingsLocks = value;
    }

    /**
     * Gets the value of the isUrgent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUrgent() {
        return isUrgent;
    }

    /**
     * Sets the value of the isUrgent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUrgent(Boolean value) {
        this.isUrgent = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link BESActionTarget }
     *     
     */
    public BESActionTarget getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link BESActionTarget }
     *     
     */
    public void setTarget(BESActionTarget value) {
        this.target = value;
    }

    /**
     * Gets the value of the sourceFixlet property.
     * 
     * @return
     *     possible object is
     *     {@link BESSourceSiteAndFixlet }
     *     
     */
    public BESSourceSiteAndFixlet getSourceFixlet() {
        return sourceFixlet;
    }

    /**
     * Sets the value of the sourceFixlet property.
     * 
     * @param value
     *     allowed object is
     *     {@link BESSourceSiteAndFixlet }
     *     
     */
    public void setSourceFixlet(BESSourceSiteAndFixlet value) {
        this.sourceFixlet = value;
    }

    /**
     * Gets the value of the skipUI property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSkipUI() {
        return skipUI;
    }

    /**
     * Sets the value of the skipUI property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSkipUI(Boolean value) {
        this.skipUI = value;
    }

}
