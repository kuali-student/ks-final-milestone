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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.KeyEntityInfo;
import org.kuali.student.core.atp.infc.AtpInfc;


/**
 * Information about an academic time period.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtpInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "metaInfo", "attributes", "_futureElements"})

public class AtpInfo 
    extends KeyEntityInfo
    implements AtpInfc,
	       Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Date startDate;

    @XmlElement
    private Date endDate;


    private AtpInfo() {
    	startDate = null;
	endDate = null;
    }
    

    /**
     * Constructs a new AtpInfo from another Atp.
     *
     * @param atp the ATP to copy
     */

    public AtpInfo(AtpInfc atp) {
	super(atp);
	this.startDate = atp.getStartDate();
	this.endDate = atp.getEndDate();
    }


    /**
     * Name: StartDate
     * Date and time the academic time period becomes effective. This
     * does not provide a bound on date ranges or milestones
     * associated with this time period, but instead indicates the
     * time period proper. This is a similar concept to the effective
     * date on enumerated values. When an end date has been specified,
     * this field must be less than or equal to the end date.
     *
     * @return the ATP start date
     */

    @Override
    public Date getStartDate() {
        return startDate;
    }


    /**
     * Name: EndDate
     * Date and time the academic time period becomes
     * ineffective. This does not provide a bound on date ranges or
     * milestones associated with this time period, but instead
     * indicates the time period proper. If specified, this must be
     * greater than or equal to the start date. If this field is not
     * specified, then no end date has been currently defined
     * and should automatically be considered greater than the
     * effective date.
     *
     * @return the ATP end date
     */

    @Override
    public Date getEndDate() {
        return endDate;
    }


    /**
     * The builder class for this AtpInfo.
     */

    public static class Builder 
	extends KeyEntityInfo.Builder 
	implements AtpInfc {
    	
    	private Date startDate;
	private Date endDate;


	/**
	 * Constructs a new builder.
	 */

	public Builder() {}


	/**
	 * Constructs a new builder initialized from another ATP.
	 */

    	public Builder(AtpInfc atp) {
	    super(atp);
	    this.startDate = atp.getStartDate();
	    this.startDate = atp.getEndDate();
    	}
		

	/**
	 * Builds the ATP.
	 *
	 * @return a new ATP
	 */

        public AtpInfc build() {
            return new AtpInfo(this);
        }


	/**
	 * Gets the start date.
	 *
	 * @return the ATP start date
	 */

	@Override
	public Date getStartDate() {
	    return startDate;
	}


	/**
	 * Sets the ATP start date.
	 *
	 * @param startDate the start date for the ATP
	 * @return the builder
	 */
    	
	public Builder startDate(Date startDate) {
	    this.startDate = startDate;
	    return this;
	}


	/**
	 * Gets the start date.
	 *
	 * @return the ATP end date
	 */

	@Override
	public Date getEndDate() {
	    return endDate;
	}
    	

	/**
	 * Sets the ATP end date.
	 *
	 * @param endDate the end date for the ATP
	 * @return the builder
	 */

	public Builder endDate(Date endDate) {
	    this.endDate = endDate;
	    return this;
	}
    }
}
