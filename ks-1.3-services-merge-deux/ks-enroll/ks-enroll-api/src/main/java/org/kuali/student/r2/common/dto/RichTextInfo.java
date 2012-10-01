/*
 * Copyright 2010 The Kuali Foundation 
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.r2.common.dto;

import org.kuali.student.r2.common.infc.RichText;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RichTextInfo", propOrder = {
        "plain", "formatted", "_futureElements"})

public class RichTextInfo
        implements RichText, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String plain;

    @XmlElement
    private String formatted;

    @XmlAnyElement
    private List<Element> _futureElements;


    /**
     * Constructs a new RichTextInfo.
     */
    public RichTextInfo() {
    }

    /**
     * Constructs a new RichTextInfo from another RichText.
     *
     * @param richText the RichText to copy
     */
    public RichTextInfo(RichText richText) {
        if (null != richText) {
            this.plain = richText.getPlain();
            this.formatted = richText.getFormatted();
        }
    }

    /**
     * Constructs a new RichTextInfo from a plain and a formatted string
     *
     * @param plain     The Plain text representation
     * @param formatted The Formatted text representation
     */
    public RichTextInfo(String plain, String formatted) {
        this.plain = plain;
        this.formatted = formatted;
    }

    @Override
    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    @Override
    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }

    @Override
    public String toString() {
        return "RichTextInfo[plain=" + plain + ", formatted=" + formatted + "]";
    }
}
