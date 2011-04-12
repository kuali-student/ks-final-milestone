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
import org.kuali.student.core.academiccalendar.infc.KeyDate;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * Information about a key date.
 *
 * @Author tom
 * @Since Tue Apr 05 14:22:34 EDT 2011
 */ 

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "KeyDateInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "isDateRange", "startDate", "endDate", "metaInfo", "attributes", "_futureElements"})

public class KeyDateInfo extends KeyEntityInfo implements KeyDate, Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private final Boolean isDateRange;

	@XmlElement
	private final Date startDate;

	@XmlElement
	private final Date endDate;

	@XmlAnyElement
	private final List<Element> _futureElements;

	private KeyDateInfo() {
		isDateRange = false;
		startDate = null;
		endDate = null;
		_futureElements = null;
	}

	/**
	 * Constructs a new KeyDateInfo from another KeyDate.
	 *
	 * @param keyDate the KeyDate to copy
	 */
	public KeyDateInfo(KeyDate keyDate) {
		super(keyDate);
		this.isDateRange = keyDate.getIsDateRange();
		this.startDate = null != keyDate.getStartDate() ? new Date(keyDate.getStartDate().getTime()) : null;
		this.endDate = null != keyDate.getEndDate() ? new Date(keyDate.getEndDate().getTime()) : null;
		_futureElements = null;
	}

	/**
	 * Name: IsDateRange
	 * Tests if this keyDate has a date range. If true, the end date
	 * value follows the start date.
	 *
	 * @return true if this KeyDate has different start end end
	 *         dates, false if this KeyDate represents a single date
	 */
	@Override
	public Boolean getIsDateRange() {
		return isDateRange;
	}

	/**
	 * Name: StartDate
	 * Gets the start Date and time of the keyDate.
	 *
	 * @return the keyDate start
	 */
	@Override
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Name: EndDate
	 * Gets the end Date and time of the keyDate.
	 *
	 * @return the keyDate end
	 */
	@Override
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * The builder class for this KeyDateInfo.
	 */
	public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<KeyDateInfo>, KeyDate {

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
		 *  KeyDate.
		 */
		public Builder(KeyDate keyDate) {
			super(keyDate);
			this.isDateRange = keyDate.getIsDateRange();
			this.startDate = null != keyDate.getStartDate() ? new Date(keyDate.getStartDate().getTime()) : null;
			this.endDate = null != keyDate.getEndDate() ? new Date(keyDate.getEndDate().getTime()) : null;
		}

		/**
		 * Builds the KeyDate.
		 *
		 * @return a new KeyDate
		 */
		public KeyDateInfo build() {
			return new KeyDateInfo(this);
		}

		/**
		 * Tests if this keyDate has a date range. If true, the end date
		 * value follows the start date.
		 *
		 * @return true if this KeyDate has different start end end
		 *         dates, false if this KeyDate represents a single date
		 */
		@Override
		public Boolean getIsDateRange() {
			return isDateRange;
		}

		/**
		 * Sets the date range flag (should this flag be inferred from
		 * the dates?)
		 *
		 * @param isDateRange true if this KeyDate has different
		 *         start end end dates, false if this KeyDate
		 *         represents a single date
		 */
		public void dateRange(Boolean isDateRange) {
			this.isDateRange = isDateRange;
		}

		/**
		 * Gets the start date.
		 *
		 * @return the KeyDate start date
		 */
		@Override
		public Date getStartDate() {
			return startDate;
		}

		/**
		 * Sets the KeyDate start date.
		 *
		 * @param endDate the start date
		 */
		public void setStartDate(Date startDate) {
			this.startDate = new Date(startDate.getTime());
		}

		/**
		 * Gets the start date.
		 *
		 * @return the KeyDate end date
		 */
		@Override
		public Date getEndDate() {
			return endDate;
		}

		/**
		 * Sets the KeyDate end date.
		 *
		 * @param endDate the end date
		 */
		public void setEndDate(Date endDate) {
			this.endDate = new Date(endDate.getTime());
		}
	}
}
