/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.enrollment.batchjobresults.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.batchjobresults.infc.BatchJobResultItem;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "batchJobResultId",
    "sourceId",
    "targetId",
    "message",
    "meta",
    "attributes",
    "_futureElements"})
public class BatchJobResultItemInfo
        extends IdNamelessEntityInfo
        implements BatchJobResultItem {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String batchJobResultId;
    @XmlElement
    private String sourceId;
    @XmlElement
    private String targetId;
    @XmlElement
    private RichTextInfo message;
    @XmlAnyElement
    private List<Element> _futureElements;

   
    public BatchJobResultItemInfo() {
    }

    
    public BatchJobResultItemInfo(BatchJobResultItem orig) {

        super(orig);

        if (orig == null) {
            return;
        }

        this.batchJobResultId = orig.getBatchJobResultId();
        this.targetId = orig.getTargetId();
        this.sourceId = orig.getSourceId();
        if (orig.getMessage() != null) {
            this.message = new RichTextInfo (orig.getMessage());
        }
    }

    @Override
    public RichTextInfo getMessage() {
        return message;
    }

    public void setMessage(RichTextInfo message) {
        this.message = message;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    @Override
    public String getBatchJobResultId() {
        return batchJobResultId;
    }

    public void setBatchJobResultId(String batchJobResultId) {
        this.batchJobResultId = batchJobResultId;
    }

    
    
}
