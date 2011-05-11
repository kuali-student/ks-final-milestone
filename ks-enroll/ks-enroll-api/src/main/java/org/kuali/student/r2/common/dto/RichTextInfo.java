/*
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichTextInfo", propOrder = {"plain", "formatted", "_futureElements"})
public class RichTextInfo implements RichText, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String plain;

    @XmlElement
    private String formatted;
    
    @XmlAnyElement
    private List<Element> _futureElements;    


    public static RichTextInfo newInstance() {
        return new RichTextInfo();
    }

    public static RichTextInfo getInstance(RichText descr) {
        return new RichTextInfo(descr);
    }
    
    private RichTextInfo() {
    	plain = null;
    	formatted = null;
    	_futureElements=null;
    }
    
    private RichTextInfo(RichText richText) {
        if (null != richText) {
	    	this.plain = richText.getPlain();
	    	this.formatted = richText.getFormatted();
	    	this._futureElements=null;
    	}
    }
    
    @Override
    public String getPlain() {
        return plain;
    }

    @Override
    public void setPlain(String plain) {
        this.plain = plain;
    }

    @Override
    public String getFormatted() {
        return formatted;
    }

    @Override
    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    @Override
    public String toString() {
    	return "RichTextInfo[plain=" + plain + ", formatted=" + formatted + "]";
    }
}
