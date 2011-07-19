/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.enrollment.lpr.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAnyElement;

import org.kuali.student.enrollment.lpr.infc.LuiPersonRelationTransactionItemResult;
import org.w3c.dom.Element;

/**
 * This is a description of what this class does - sambitpatnaik don't forget to fill this in. 
 * 
 * @author Kuali Student Team (sambitpatnaik)
 *
 */
public class LuiPersonRelationTransactionItemResultInfo implements LuiPersonRelationTransactionItemResult, Serializable {

    private static final long serialVersionUID = 1L;

    private String resultingLprId;
    
    private List<String> messages;
    
    private String status;
    
    @XmlAnyElement
    private List<Element> _futureElements;


    public void setResultingLprId(String resultingLprId) {
        this.resultingLprId = resultingLprId;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String getResultingLprId() {
        return resultingLprId;
    }

    @Override
    public List<String> getMessages() {
        return messages;
    }

    @Override
    public String getStatus() {
        return status;
    }

}
