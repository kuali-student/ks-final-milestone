/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.core.atp.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.KeyEntityInfo;
import org.kuali.student.core.atp.infc.MilestoneInfc;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * Information about a milestone.
 */ 

@XmlAccessorType(XmlAccessType.FIELD)

public class MilestoneInfo 
    extends KeyEntityInfo
    implements MilestoneInfc, 
	       Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private boolean isDateRange;

    @XmlElement
    private Date startDate;

    @XmlElement
    private Date endDate;


    private MilestoneInfo() {
	isDateRange = false;
	startDate = null;
	endDate = null;
    }


    private MilestoneInfo(MilestoneInfc builder) {
        super(builder);
	this.isDateRange = false;
        this.startDate = null != builder.getStartDate() ? new Date(builder.getStartDate().getTime()) : null;
        this.endDate = null != builder.getEndDate() ? new Date(builder.getEndDate().getTime()) : null;
    }


    /**
     * Tests if this milestone has a date range. If true, the end date
     * value follows the start date.
     */

    @Override
    public boolean getIsDateRange() {
        return isDateRange;
    }


    /**
     * Start Date and time of the milestone.
     */

    @Override
    public Date getStartDate() {
        return startDate;
    }


    /**
     * End Date and time of the milestone.
     */

    @Override
    public Date getEndDate() {
        return endDate;
    }


    public static class Builder 
	extends KeyEntityInfo.Builder 
	implements MilestoneInfc {

	private boolean isDateRange;
        private Date startDate;
        private Date endDate;


        public Builder() {
        }


        public Builder(MilestoneInfc milestoneInfo) {
            super(milestoneInfo);
            this.startDate = milestoneInfo.getStartDate();
            this.endDate = milestoneInfo.getEndDate();
        }


        public MilestoneInfo build() {
            return new MilestoneInfo(this);
        }


	public Builder dateRange(boolean isDateRange) {
	    this.isDateRange = isDateRange;
	    return this;
	}


        public Builder startDate(Date startDate) {
            this.startDate = new Date(startDate.getTime());
            return this;
        }


        public Builder endDate(Date endDate) {
            this.endDate = new Date(endDate.getTime());
            return this;
        }


	@Override
	public boolean getIsDateRange() {
	    return isDateRange;
	}


        @Override
        public Date getStartDate() {
            return startDate;
        }


        @Override
        public Date getEndDate() {
            return endDate;
        }
    }
}
