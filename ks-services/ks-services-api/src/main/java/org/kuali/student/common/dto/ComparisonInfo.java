/*
 * Copyright 2009 The Kuali Foundation
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
package org.kuali.student.common.dto;

import org.kuali.student.common.infc.ComparisonInfc;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Query to return some information regarding LUI to person relationships.
 *
 * @Author KSContractMojo
 * @Author Kamal
 * @Since Tue Mar 01 15:54:06 PST 2011
 * @See <a href="https://wiki.kuali.org/display/KULSTU/luiPersonRelationCriteria+Structure">LuiPersonRelationCriteria</a>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ComparisonInfo implements ComparisonInfc, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private final String fieldKey;
    @XmlElement
    private final String operator;
    @XmlElement
    private final List<String> values;
    @XmlElement
    private final boolean ignoreCase;

    private ComparisonInfo() {
        fieldKey = null;
        operator = null;
        values = null;
        ignoreCase = false;
    }

    private ComparisonInfo(ComparisonInfc bldr) {
        this.fieldKey = bldr.getFieldKey();
        this.operator = bldr.getOperator();
        if (bldr.getValues() == null)
        {
            this.values = null;
        }else
        {
            this.values = Collections.unmodifiableList(bldr.getValues());
        }
        this.ignoreCase = bldr.isIgnoreCase();
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    @Override
    public String getOperator() {
        return operator;
    }

    @Override
    public List<String> getValues() {
        return values;
    }

    @Override
    public boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    public static class Builder implements ComparisonInfc {

        private String fieldKey;
        private String operator;
        private List<String> values;
        private boolean ignoreCase;

        public Builder() {
        }

        public Builder(ComparisonInfc infc) {
            this.fieldKey = infc.getFieldKey();
            this.operator = infc.getOperator();
            if (infc.getValues() != null) {
                this.values = new ArrayList(infc.getValues());
            }
        }

        public ComparisonInfo build() {
            return new ComparisonInfo(this);
        }

        @Override
        public String getFieldKey() {
            return fieldKey;
        }

        @Override
        public String getOperator() {
            return operator;
        }

        @Override
        public List<String> getValues() {
            return values;
        }

        @Override
        public boolean isIgnoreCase() {
            return this.ignoreCase;
        }

        public Builder setFieldKey(String fieldKey) {
            this.fieldKey = fieldKey;
            return this;
        }

        public Builder setOperator(String operator) {
            this.operator = operator;
            return this;
        }

        /**
         * convenience method
         * @param value
         * @return
         */
        public Builder setValue(String value) {
            this.setValues(Arrays.asList(value));
            return this;
        }

        public Builder setValues(List<String> values) {
            this.values = values;
            return this;
        }

        public Builder setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
            return this;
        }
    }
}
