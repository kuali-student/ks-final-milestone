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
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.lpr.infc.LprTransactionItemResult;
import org.w3c.dom.Element;


/**
 * This is a description of what this class does - sambitpatnaik don't forget to fill this in. 
 * 
 * @author Kuali Student Team (sambitpatnaik)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LuiPersonRelationTransactionItemResultInfo", propOrder = {"resultingLprId", "messages", "status", "_futureElements"})
public class LprTransactionItemResultInfo implements LprTransactionItemResult, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String resultingLprId;

    @XmlElement
    private List<String> messages;
    
    @XmlElement    
    private String status;
    
    @XmlAnyElement
    private List<Element> _futureElements;


    public LprTransactionItemResultInfo() {
        this.resultingLprId = null;
        this.messages = new ArrayList<String>();
        this.status = null;
        this._futureElements = null;
    }
    
    
    public LprTransactionItemResultInfo(LprTransactionItemResult result) {

        if(null == result) return;
        
        this.resultingLprId = result.getResultingLprId();
        this.status = result.getStatus();
        this.messages = (null != result.getMessages()) ? new ArrayList<String>(result.getMessages()) : new ArrayList<String>();
        this._futureElements = null;        
    }
    
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
