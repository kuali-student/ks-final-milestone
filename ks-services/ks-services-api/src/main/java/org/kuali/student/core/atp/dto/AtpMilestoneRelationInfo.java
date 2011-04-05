/*
 * Copyright 2009 The Kuali Foundation
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

package org.kuali.student.core.atp.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.dto.RelationshipInfo;
import org.kuali.student.core.atp.infc.AtpMilestoneRelationInfc;


/**
 *  Information about an ATP Milestone Relationship.
 */

@XmlAccessorType(XmlAccessType.FIELD)

public class AtpMilestoneRelationInfo 
    extends RelationshipInfo
    implements AtpMilestoneRelationInfc, 
	       Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String atpKey;

    @XmlElement
    private final String milestoneKey;


    private AtpMilestoneRelationInfo() {
        atpKey = null;
        milestoneKey = null;
    }


    private AtpMilestoneRelationInfo(AtpMilestoneRelationInfc builder) {
        super(builder);
        this.atpKey = builder.getAtpKey();
        this.milestoneKey = builder.getMilestoneKey();
    }


    @Override
    public String getAtpKey() {
        return atpKey;
    }


    @Override
    public String getMilestoneKey() {
        return milestoneKey;
    }


    public static class Builder 
	extends RelationshipInfo.Builder 
	implements AtpMilestoneRelationInfc {

        private String atpKey;
        private String milestoneKey;


        public Builder() {
        }


        public Builder(AtpMilestoneRelationInfc amrInfo) {
            super(amrInfo);
            this.atpKey = amrInfo.getAtpKey();
            this.milestoneKey = amrInfo.getMilestoneKey();
        }


        public AtpMilestoneRelationInfo build() {
            return new AtpMilestoneRelationInfo(this);
        }


        public Builder atpKey(String atpKey) {
            this.atpKey = atpKey;
            return this;
        }


        public Builder milestoneKey(String milestoneKey) {
            this.milestoneKey = milestoneKey;
            return this;
        }


        @Override
        public String getAtpKey() {
            return atpKey;
        }

        @Override
        public String getMilestoneKey() {
            return milestoneKey;
        }
    }
}
