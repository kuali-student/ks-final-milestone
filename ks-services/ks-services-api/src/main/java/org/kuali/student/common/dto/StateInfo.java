/*
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

package org.kuali.student.common.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.StateInfc;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class StateInfo extends HasAttributesInfo implements StateInfc, Serializable {
	
	@XmlAttribute
	private final String key;
	
	@XmlElement
	private final String name;
	
	@XmlElement(name ="desc")
	private final String descr;

	@XmlElement
	private final Date effectiveDate;
	
	@XmlElement
	private final Date expirationDate;

	private StateInfo() {
		key = null;
		name = null;
		descr = null;
		effectiveDate = null;
		expirationDate = null;
	}
	
	private StateInfo(StateInfc builder) {
		super(builder);
		this.key = builder.getKey();
		this.name = builder.getName();
		this.descr = builder.getDescr();
    	this.effectiveDate = null != builder.getEffectiveDate() ? new Date(builder.getEffectiveDate().getTime()) : null;
    	this.expirationDate = null != builder.getExpirationDate() ? new Date(builder.getExpirationDate().getTime()) : null;
	}
	

    @Override
    public String getKey() {
        return key;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getDescr() {
        return descr;
    }


    @Override
    public Date getEffectiveDate() {
        return effectiveDate;
    }


    @Override
    public Date getExpirationDate() {
        return expirationDate;
    }

    public static class Builder extends HasAttributesInfo.Builder implements StateInfc {
		private String key;
		private String name;
		private String descr;
		private Date effectiveDate;
    	private Date expirationDate;
		
		public Builder() {}
		
    	public Builder(StateInfc stateInfo) {
    		this.key = stateInfo.getKey();
    		this.name = stateInfo.getName();
    		this.descr = stateInfo.getDescr();
    		this.effectiveDate = stateInfo.getEffectiveDate();
    		this.expirationDate = stateInfo.getExpirationDate();
    	}

        public StateInfo build() {
            return new StateInfo(this);
        }    	
    	
		@Override
		public String getKey() {
			return key;
		}

		@Override
		public Date getEffectiveDate() {
			return effectiveDate;
		}

		@Override
		public Date getExpirationDate() {
			return expirationDate;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String getDescr() {
			return descr;
		}

		/**
		 * @param key the key to set
		 */
		public final void key(String key) {
			this.key = key;
		}

		/**
		 * @param name the name to set
		 */
		public final void name(String name) {
			this.name = name;
		}

		/**
		 * @param descr the descr to set
		 */
		public final void descr(String descr) {
			this.descr = descr;
		}

		/**
		 * @param effectiveDate the effectiveDate to set
		 */
		public final void effectiveDate(Date effectiveDate) {
			this.effectiveDate = effectiveDate;
		}

		/**
		 * @param expirationDate the expirationDate to set
		 */
		public final void expirationDate(Date expirationDate) {
			this.expirationDate = expirationDate;
		}
    }
}
