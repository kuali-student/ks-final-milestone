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
package org.kuali.student.enrollment.courseofferingset.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.courseofferingset.infc.SocRolloverResultItem;
import org.kuali.student.r2.common.dto.IdNamelessEntityInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.w3c.dom.Element;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocRolloverResultItemInfo", propOrder = {"id",
    "typeKey",
    "stateKey",
    "socRolloverResultId",
    "sourceCourseOfferingId",
    "targetCourseOfferingId",
    "message",
    "meta",
    "attributes",
    "_futureElements"})
public class SocRolloverResultItemInfo
        extends IdNamelessEntityInfo
        implements SocRolloverResultItem {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String socRolloverResultId;
    @XmlElement
    private String sourceCourseOfferingId;
    @XmlElement
    private String targetCourseOfferingId;
    @XmlElement
    private RichTextInfo message;
    @XmlAnyElement
    private List<Element> _futureElements;

   
    public SocRolloverResultItemInfo() {
    }

    
    public SocRolloverResultItemInfo(SocRolloverResultItem orig) {

        super(orig);

        if (orig == null) {
            return;
        }

        this.socRolloverResultId = orig.getSocRolloverResultId();
        this.targetCourseOfferingId = orig.getTargetCourseOfferingId();
        this.sourceCourseOfferingId = orig.getSourceCourseOfferingId();
        if (orig.getMessage() != null) {
            this.message = new RichTextInfo (orig.getMessage());
        }
    }

    @Override
    public String getSocRolloverResultId() {
        return socRolloverResultId;
    }

    public void setSocRolloverResultId(String rolloverResultId) {
        this.socRolloverResultId = rolloverResultId;
    }
    
    @Override
    public RichTextInfo getMessage() {
        return message;
    }

    public void setMessage(RichTextInfo message) {
        this.message = message;
    }

    @Override
    public String getSourceCourseOfferingId() {
        return sourceCourseOfferingId;
    }

    public void setSourceCourseOfferingId(String sourceCourseOfferingId) {
        this.sourceCourseOfferingId = sourceCourseOfferingId;
    }

    @Override
    public String getTargetCourseOfferingId() {
        return targetCourseOfferingId;
    }

    public void setTargetCourseOfferingId(String targetCourseOfferingId) {
        this.targetCourseOfferingId = targetCourseOfferingId;
    }

    
    
}
