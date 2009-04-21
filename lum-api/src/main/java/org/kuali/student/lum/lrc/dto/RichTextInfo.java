/*
 * Copyright 2009 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.lum.lrc.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Complex type supporting both a plain text and formatted version of a string.
 *
 * @Author KSContractMojo
 * @Author lindholm
 * @Since Tue Apr 21 13:47:31 PDT 2009
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/richTextInfo+Structure">RichTextInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RichTextInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String plain;

    @XmlElement
    private String formatted;

    /**
     * Plain version of the text. This may be used for searching.
     */
    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    /**
     * Formatted version of the text.
     */
    public String getFormatted() {
        return formatted;
    }

    public void setFormatted(String formatted) {
        this.formatted = formatted;
    }
}