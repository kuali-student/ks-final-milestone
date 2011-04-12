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
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.dto.KeyEntityInfo;
import org.kuali.student.core.atp.infc.Milestone;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;

/**
 * Information about a milestone.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MilestoneInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "isDateRange", "startDate", "endDate", "metaInfo", "attributes", "_futureElements"})

public class MilestoneInfo extends KeyEntityInfo implements Milestone, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Boolean isDateRange;

    @XmlElement
    private final Date startDate;

    @XmlElement
    private final Date endDate;

    @XmlAnyElement
    private final List<Element> _futureElements;  

    private MilestoneInfo() {
	isDateRange = false;
	startDate = null;
	endDate = null;
	_futureElements = null;
    }

    /**
     * Constructs a new MilestoneInfo from another Milestone.
     *
     * @param milestone the Milestone to copy
     */
    public MilestoneInfo(Milestone milestone) {
        super(milestone);
	this.isDateRange = milestone.getIsDateRange();
        this.startDate = null != milestone.getStartDate() ? new Date(milestone.getStartDate().getTime()) : null;
        this.endDate = null != milestone.getEndDate() ? new Date(milestone.getEndDate().getTime()) : null;
	_futureElements = null;
    }

    /**
     * Name: IsDateRange
     * Tests if this milestone has a date range. If true, the end date
     * value follows the start date.
     *
     * @return true if this Milestone has different start end end
     *         dates, false if this Milestone represents a single date
     */
    @Override
    public Boolean getIsDateRange() {
        return isDateRange;
    }

    /**
     * Name: StartDate
     * Gets the start Date and time of the milestone.
     *
     * @return the milestone start
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Name: EndDate
     * Gets the end Date and time of the milestone.
     *
     * @return the milestone end
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * The builder class for this MilestoneInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<MilestoneInfo>, Milestone {

	private Boolean isDateRange;
        private Date startDate;
        private Date endDate;

	/**
	 * Constructs a new builder.
	 */
        public Builder() {
        }

	/**
	 *  Constructs a new builder initialized from another
	 *  Milestone.
	 */
        public Builder(Milestone milestone) {
            super(milestone);
	    this.isDateRange = milestone.getIsDateRange();
            this.startDate = milestone.getStartDate();
            this.endDate = milestone.getEndDate();
        }

	/**
	 * Builds the Milestone.
	 *
	 * @return a new Milestone
	 */
        public MilestoneInfo build() {
            return new MilestoneInfo(this);
        }

	/**
	 * Tests if this milestone has a date range. If true, the end date
	 * value follows the start date.
	 *
	 * @return true if this Milestone has different start end end
	 *         dates, false if this Milestone represents a single date
	 */
	@Override
	public Boolean getIsDateRange() {
	    return isDateRange;
	}

	/**
	 * Sets the date range flag (should this flag be inferred from
	 * the dates?)
	 *
	 * @param isDateRange true if this Milestone has different
	 *         start end end dates, false if this Milestone
	 *         represents a single date
	 */
	public void dateRange(Boolean isDateRange) {
	    this.isDateRange = isDateRange;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the Milestone start date
	 */
        @Override
        public Date getStartDate() {
            return startDate;
        }

	/**
	 * Sets the Milestone start date.
	 *
	 * @param endDate the start date
	 */
        public void setStartDate(Date startDate) {
            this.startDate = new Date(startDate.getTime());
        }

	/**
	 * Gets the start date.
	 *
	 * @return the Milestone end date
	 */
        @Override
        public Date getEndDate() {
            return endDate;
        }

	/**
	 * Sets the Milestone end date.
	 *
	 * @param endDate the end date
	 */
        public void setEndDate(Date endDate) {
            this.endDate = new Date(endDate.getTime());
        }
    }
}
