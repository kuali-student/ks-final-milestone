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
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.dto.KeyEntityInfo;
import org.kuali.student.core.atp.infc.AtpInfc;
import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * Information about an academic time period.
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
public class AtpInfo 
    extends KeyEntityInfo
    implements AtpInfc,
	       Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private Date startDate;

    @XmlElement
    private Date endDate;


    /**
     * Date and time the academic time period became effective. This
     * does not provide a bound on date ranges or milestones
     * associated with this time period, but instead indicates the
     * time period proper. This is a similar concept to the effective
     * date on enumerated values. When an expiration date has been
     * specified, this field must be less than or equal to the
     * expiration date.
     */

    @Override
    public Date getStartDate() {
        return startDate;
    }


    /**
     * Date and time the academic time period expires. This does not
     * provide a bound on date ranges or milestones associated with
     * this time period, but instead indicates the time period
     * proper. If specified, this must be greater than or equal to the
     * effective date. If this field is not specified, then no
     * expiration date has been currently defined and should
     * automatically be considered greater than the effective date.
     */

    @Override
    public Date getEndDate() {
        return endDate;
    }


    /**
     * The builder class for this abstract EntityInfo.
     */

    public static class Builder 
	extends KeyEntityInfo.Builder 
	implements AtpInfc {
    	
    	private Date startDate;
	private Date endDate;


	public Builder() {}
    	
    	public Builder(AtpInfc atp) {
	    super(atp);
	    this.startDate = atp.getStartDate();
	    this.startDate = atp.getEndDate();
    	}
		

	@Override
	public Date getStartDate() {
	    return startDate;
	}
    	
	public Builder startDate(Date startDate) {
	    this.startDate = startDate;
	    return this;
	}


	@Override
	public Date getEndDate() {
	    return endDate;
	}
    	
	public Builder endDate(Date endDate) {
	    this.endDate = endDate;
	    return this;
	}
    }
}
