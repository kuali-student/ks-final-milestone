package org.kuali.student.common.dto;

import org.kuali.student.common.infc.Locale;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * @Version 2.0
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocaleInfo", propOrder = {"localeLanguage", "localeVariant", "localeRegion", "localeScript" /*TODO KSCM-gwt-compile , "_futureElements" */})
public class LocaleInfo implements Locale, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String localeLanguage;

    @XmlElement
    private String localeVariant;

    @XmlElement
    private String localeRegion;

    @XmlElement
    private String localeScript;

    //TODO KSCM-gwt-compile
    //@XmlAnyElement
    //private List<Element> _futureElements;

    public LocaleInfo() {
    }

    public LocaleInfo(Locale locale) {
        if (null != locale) {
            this.localeLanguage = locale.getLocaleLanguage();
            this.localeVariant = locale.getLocaleVariant();
            this.localeRegion = locale.getLocaleRegion();
            this.localeScript = locale.getLocaleScript();
        }
    }

    @Override
    public String getLocaleLanguage() {
        return this.localeLanguage;
    }

    public void setLocaleLanguage(String localeLanguage) {
        this.localeLanguage = localeLanguage;
    }

    @Override
    public String getLocaleVariant() {
        return this.localeVariant;
    }

    public void setLocaleVariant(String localeVariant) {
        this.localeVariant = localeVariant;
    }

    @Override
    public String getLocaleRegion() {
        return this.localeRegion;
    }

    public void setLocaleRegion(String localeRegion) {
        this.localeRegion = localeRegion;
    }

    @Override
    public String getLocaleScript() {
        return this.localeScript;
    }

    public void setLocaleScript(String localeScript) {
        this.localeScript = localeScript;
    }
}
