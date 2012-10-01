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

import org.kuali.student.enrollment.batchjobresults.infc.BatchJobResult;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "name",
    "descr",
    "parameters",
    "context",
    "globalResults",
    "itemsProcessed",
    "itemsExpected",
    "message",
    "meta",
    "attributes",
    "_futureElements"})
public class BatchJobResultInfo
        extends IdEntityInfo
        implements BatchJobResult {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private List<AttributeInfo> parameters;
    @XmlElement
    private ContextInfo context;
    @XmlElement
    private List<AttributeInfo> globalResults;
    @XmlElement
    private Integer itemsProcessed;
    @XmlElement
    private Integer itemsExpected;
    @XmlElement
    private RichTextInfo message;
    @XmlAnyElement
    private List<Element> _futureElements;

    public BatchJobResultInfo() {
    }

    /**
     * Copies batch job result
     */
    public BatchJobResultInfo(BatchJobResult orig) {

        super(orig);

        if (orig == null) {
            return;
        }

        if (orig.getParameters() != null) {
            for (Attribute attr : orig.getParameters()) {
                this.getParameters().add(new AttributeInfo(attr));
            }
        }
        if (orig.getGlobalResults() != null) {
            for (Attribute attr : orig.getGlobalResults()) {
                this.getGlobalResults().add(new AttributeInfo(attr));
            }
        }
        this.context = new ContextInfo(orig.getContext());
        this.itemsProcessed = orig.getItemsProcessed();
        this.itemsExpected = orig.getItemsExpected();
        if (orig.getMessage() != null) {
            this.message = new RichTextInfo (orig.getMessage());
        }
    }

    @Override
    public ContextInfo getContext() {
        return context;
    }

    public void setContext(ContextInfo context) {
        this.context = context;
    }

    @Override
    public List<AttributeInfo> getGlobalResults() {
        return globalResults;
    }

    public void setGlobalResults(List<AttributeInfo> globalResults) {
        this.globalResults = globalResults;
    }

    @Override
    public List<AttributeInfo> getParameters() {
        return parameters;
    }

    public void setParameters(List<AttributeInfo> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Integer getItemsProcessed() {
        return itemsProcessed;
    }

    public void setItemsProcessed(Integer itemsProcessed) {
        this.itemsProcessed = itemsProcessed;
    }

    @Override
    public Integer getItemsExpected() {
        return itemsExpected;
    }

    public void setItemsExpected(Integer itemsExpected) {
        this.itemsExpected = itemsExpected;
    }

    @Override
    public RichTextInfo getMessage() {
        return message;
    }

    public void setMessage(RichTextInfo message) {
        this.message = message;
    }

   
}
