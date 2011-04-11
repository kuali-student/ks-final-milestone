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

package org.kuali.student.core.academiccalendar.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.common.infc.ModelBuilder;
import org.kuali.student.common.dto.KeyEntityInfo;
import org.kuali.student.core.academiccalendar.infc.TermInfc;


/**
 * Information about a Term.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermCalendarInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "terms", "keyDates", "metaInfo", "attributes", "_futureElements"})
public class TermInfo extends KeyEntityInfo implements TermInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Date startDate;

    @XmlElement
    private final Date endDate;

    @XmlElement
    private final List<TermInfo> terms;

    @XmlElement
    private final List<KeyDateInfo> keyDates;

    private TermInfo() {
    	startDate = null;
	endDate = null;
	terms = null;
	keyDates = null;
    }

    /**
     * Constructs a new TermInfo from another
     * Term.
     *
     * @paramterm the Term to copy
     */
    public TermInfo(TermInfc term) {
	super(term);
	this.startDate = null != term.getStartDate() ? new Date(term.getStartDate().getTime()) : null;
	this.endDate = null != term.getEndDate() ? new Date(term.getEndDate().getTime()) : null;
	/* copy me */
	this.terms = null;
	this.keyDates = null;
    }

    /**
     * Name: StartDate
     * Date and time the term becomes effective. This does not provide
     * a bound on date ranges or milestones associated with this time
     * period, but instead indicates the time period proper. This is a
     * similar concept to the effective date on enumerated
     * values. When an end date has been specified, this field must be
     * less than or equal to the end date.
     *
     * @return the Term start date
     */
    @Override
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Name: EndDate
     * Date and time the term becomes ineffective. This does not
     * provide a bound on date ranges or milestones associated with
     * this time period, but instead indicates the time period
     * proper. If specified, this must be greater than or equal to the
     * start date. If this field is not specified, then no end date
     * has been currently defined and should automatically be
     * considered greater than the effective date.
     *
     * @return the Term end date
     */
    @Override
    public Date getEndDate() {
        return endDate;
    }


    /**
     * Name: Terms
     * Gets the Terms nested inside this Term.
     */
    @Override
    public List<TermInfo> getTerms() {
	return terms;
    }

    /**
     * Name: KeyDates
     * Gets the key dates directly mapped to this term.
     */
    public List<KeyDateInfo> getKeyDates() {
	return (keyDates);
    }


    /**
     * The builder class for this TermInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<TermInfo>, TermInfc {
    	
    	private Date startDate;
	private Date endDate;
	private List<TermInfo> terms;
	private List<KeyDateInfo> keyDates;

	/**
	 * Constructs a new builder.
	 */
	public Builder() {}

	/**
	 * Constructs a new builder initialized from another Term
	 */
    	public Builder(TermInfc term) {
	    super(term);
	    this.startDate = term.getStartDate();
	    this.startDate = term.getEndDate();
	    /* copy me */
	    this.terms = null;
    	}
		
	/**
	 * Builds the Term.
	 *
	 * @return a new Term
	 */
        public TermInfo build() {
            return new TermInfo(this);
        }

	/**
	 * Gets the start date.
	 *
	 * @return the Term start date
	 */
	@Override
	public Date getStartDate() {
	    return startDate;
	}

	/**
	 * Sets the Term start date.
	 *
	 * @param startDate the start date for the Term
	 */
	public void setStartDate(Date startDate) {
	    this.startDate = startDate;
	}

	/**
	 * Gets the start date.
	 *
	 * @return the Term end date
	 */
	@Override
	public Date getEndDate() {
	    return endDate;
	}
    	
	/**
	 * Sets the Term end date.
	 *
	 * @param endDate the end date for the Term
	 */

	public void setEndDate(Date endDate) {
	    this.endDate = endDate;
	}

	/**
	 * Gets the Terms nested inside this Term.
	 */
	@Override
	public List<TermInfo> getTerms() {
	    return terms;
	}

	/**
	 * Gets the key dates directly mapped to this term.
	 */
	public List<KeyDateInfo> getKeyDates() {
	    return keyDates;
	}
    }
}
