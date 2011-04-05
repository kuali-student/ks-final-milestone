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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.common.infc.RichTextInfc;

@XmlAccessorType(XmlAccessType.FIELD)
public class RichTextInfo implements RichTextInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private final String plain;

    @XmlElement
    private final String formatted;

    private RichTextInfo() {
    	plain = null;
    	formatted = null;
    }
    
    private RichTextInfo(RichTextInfc builder) {
    	plain = builder.getPlain();
    	formatted = builder.getFormatted();
    }
    
    @Override
    public String getPlain() {
        return plain;
    }

    @Override
    public String getFormatted() {
        return formatted;
    }

    @Override
    public String toString() {
    	return "RichTextInfo[plain=" + plain + ", formatted=" + formatted + "]";
    }
    
    public static class Builder implements RichTextInfc {
    	private String plain;
		private String formatted;

		public Builder() {}
    	
    	public Builder(RichTextInfc rtInfo) {
    		this.plain = rtInfo.getPlain();
    		this.formatted = rtInfo.getFormatted();
    	}
    	
    	public RichTextInfo build() {
    		return new RichTextInfo(this);
    	}

		@Override
		public String getPlain() {
			return plain;
		}

		@Override
		public String getFormatted() {
			return formatted;
		}
    }
}
