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
 * 
 * @author Kuali Student Team (sambitpatnaik)
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LprTransactionItemResultInfo", propOrder = {"resultingLprId", "message", "status", "_futureElements"})
public class LprTransactionItemResultInfo implements LprTransactionItemResult, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String resultingLprId;

    @XmlElement
    private String message;
    
    @XmlElement    
    private Boolean status;
    
    @XmlAnyElement
    private List<Element> _futureElements;


    public LprTransactionItemResultInfo() {
    }
    
    
    public LprTransactionItemResultInfo(LprTransactionItemResult result) {

        if(null == result) return;
        
        this.resultingLprId = result.getResultingLprId();
        this.status = result.getStatus();
        this.message = result.getMessage();
    }
    
    public void setResultingLprId(String resultingLprId) {
        this.resultingLprId = resultingLprId;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    
    @Override
    public String getResultingLprId() {
        return resultingLprId;
    }


    @Override
    public Boolean getStatus() {
        return status;
    }


    @Override
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LprTransactionItemResultInfo [resultingLprId=");
		builder.append(resultingLprId);
		builder.append(", message=");
		builder.append(message);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
	

}
