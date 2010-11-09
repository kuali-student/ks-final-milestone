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
package org.kuali.student.core.document.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * The encoded document. The expectation is that this could be a base64 encoding.
 *
 * @Author KSContractMojo
 * @Author tom
 * @Since Wed Aug 18 12:10:39 EDT 2010
 * @See <a href="https://test.kuali.org/confluence/display/KULSTU/documentBinaryInfo+Structure">DocumentBinaryInfo</>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentBinaryInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String binary;

    /**
     * Encoded document
     */
    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }
}
