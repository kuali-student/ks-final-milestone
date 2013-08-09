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

package org.kuali.student.r2.common.datadictionary.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import org.kuali.student.r2.common.datadictionary.infc.ValidCharactersConstraintInfc;


@XmlAccessorType(XmlAccessType.FIELD)
public class ValidCharactersConstraintInfo 
    implements ValidCharactersConstraintInfc, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String value;

    @XmlElement
    private String jsValue;

    @XmlElement
    private Boolean isApplyClientSide;

    @XmlElement
    private String labelKey;

    /**
     * Constructs a new ValidCharactersConstraintInfo.
     */
    public ValidCharactersConstraintInfo() {
    }

    /**
     * Constructs a new ValidCharactersConstraintInfo from a
     * ValidCharactersConstraintInfc.
     *
     * @param vcc the ValidcharactersConstraint to copy
     */
    public ValidCharactersConstraintInfo(ValidCharactersConstraintInfc vcc) {
        this.value = vcc.getValue();
        this.jsValue = vcc.getJsValue();
        this.isApplyClientSide = vcc.getIsApplyClientSide();
        this.labelKey = vcc.getLabelKey();
    }

    @Override
    public String getJsValue() {
        return this.jsValue;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public Boolean getIsApplyClientSide() {
        return this.isApplyClientSide;
    }

    @Override
    public String getLabelKey() {
        return this.labelKey;
    }

    public static class Builder implements ValidCharactersConstraintInfc {

        private String value;
        private String jsValue;
        private Boolean isApplyClientSide;
        private String labelKey;

        public Builder() {
        }

        public Builder(ValidCharactersConstraintInfc vcc) {
            super();
            this.value = vcc.getValue();
            this.jsValue = vcc.getJsValue();
            this.isApplyClientSide = vcc.getIsApplyClientSide();
            this.labelKey = vcc.getLabelKey();
        }

        public ValidCharactersConstraintInfo build() {
            return new ValidCharactersConstraintInfo(this);
        }

        @Override
        public String getJsValue() {
            return this.jsValue;
        }

        @Override
        public String getValue() {
            return this.value;
        }

        public Builder setJsValue(String jsValue) {
            this.jsValue = jsValue;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        @Override
        public Boolean getIsApplyClientSide() {
            return this.isApplyClientSide;
        }

        @Override
        public String getLabelKey() {
            return this.labelKey;
        }

        public Builder setApplyClientSide(Boolean applyClientSide) {
            this.isApplyClientSide = applyClientSide;
            return this;
        }

        public Builder setLabelKey(String labelKey) {
            this.labelKey = labelKey;
            return this;
        }
    }
}
