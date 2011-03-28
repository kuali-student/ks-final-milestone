/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.datadictionary.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.kuali.student.datadictionary.infc.ValidCharactersConstraintInfc;

/**
 * This is an info ojbect that holds a valid characters definition
 *
 * @author nwright
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ValidCharactersConstraintInfo implements ValidCharactersConstraintInfc, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String value;
    @XmlElement
    private String jsValue;
    @XmlElement
    private Boolean applyClientSide;
    @XmlElement
    private String labelKey;

    public ValidCharactersConstraintInfo() {
        this.value = null;
        this.jsValue = null;
        this.applyClientSide = null;
        this.labelKey = null;
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
    public Boolean isApplyClientSide() {
        return this.applyClientSide;
    }

    @Override
    public String getLabelKey() {
        return this.labelKey;
    }

    private ValidCharactersConstraintInfo(ValidCharactersConstraintInfc infc) {
        super();
        this.value = infc.getValue();
        this.jsValue = infc.getJsValue();
        this.applyClientSide = infc.isApplyClientSide();
        this.labelKey = infc.getLabelKey();
    }

    public static class Builder implements ValidCharactersConstraintInfc {

        private String value;
        private String jsValue;
        private Boolean applyClientSide;
        private String labelKey;

        public Builder() {
        }

        public Builder(ValidCharactersConstraintInfc infc) {
            super();
            this.value = infc.getValue();
            this.jsValue = infc.getJsValue();
            this.applyClientSide = infc.isApplyClientSide();
            this.labelKey = infc.getLabelKey();
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
        public Boolean isApplyClientSide() {
            return this.applyClientSide;
        }

        @Override
        public String getLabelKey() {
            return this.labelKey;
        }

        public Builder setApplyClientSide(Boolean applyClientSide) {
            this.applyClientSide = applyClientSide;
            return this;
        }

        public Builder setLabelKey(String labelKey) {
            this.labelKey = labelKey;
            return this;
        }
    }
}
