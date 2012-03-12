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
package org.kuali.student.r2.core.document.dto;

import org.kuali.student.r2.core.document.infc.DocumentBinary;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * Refer to interface javadoc
 *
 * @Version 2.0
 * @Author tom
 * @Author Sri komandur@uw.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentBinaryInfo", propOrder = {"binary"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})
public class DocumentBinaryInfo implements DocumentBinary, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String binary;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public DocumentBinaryInfo() {
    }

    public DocumentBinaryInfo(DocumentBinary documentBinary) {
        if (null != documentBinary) {
            this.binary = documentBinary.getBinary();
        }
    }

    @Override
    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }
}
