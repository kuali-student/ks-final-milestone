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

import org.kuali.student.r2.common.infc.BulkStatus;
//import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

/**
 * Information about the state of an object
 * 
 * @author nwright
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BulkStatusInfo", propOrder = {"id", "isSuccess", "message", "_futureElements" }) 
public class BulkStatusInfo 
    implements BulkStatus, Serializable {

    private static final long serialVersionUID = 1L;
     
    @XmlElement
    private String id;
    
    @XmlElement
    private Boolean isSuccess;
    
    @XmlElement
    private String message;
    
    
    @XmlAnyElement
    private List<Object> _futureElements;	

    public BulkStatusInfo() {
    }
    
    public BulkStatusInfo(BulkStatus bulkStatus) {
        this.id = bulkStatus.getId();
        this.isSuccess = bulkStatus.getIsSuccess();
        this.message = bulkStatus.getMessage();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    @Override
    public Boolean getIsSuccess(){
        return isSuccess;
    }
    
    public void setSuccess(Boolean success) {
        this.isSuccess = success;
    }
    
    @Override
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}

