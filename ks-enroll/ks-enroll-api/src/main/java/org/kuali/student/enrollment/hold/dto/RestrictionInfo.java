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
package org.kuali.student.enrollment.hold.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.kuali.student.r2.common.infc.ModelBuilder;
import org.kuali.student.enrollment.hold.infc.Restriction;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictionInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "metaInfo", "attributes", "_futureElements"})

public class RestrictionInfo extends KeyEntityInfo implements Restriction, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlAnyElement
    private final List<Element> _futureElements;

    private RestrictionInfo() {
        _futureElements = null;
    }

    /**
     * Constructs a new RestrictionInfo from another Restriction.
     *
     * @param restriction the Restriction to copy
     */
    public RestrictionInfo(Restriction restriction) {
        super(restriction);
        _futureElements = null;
    }


    /**
     * The builder class for this RestrictionInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<RestrictionInfo>, Restriction {

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         * Constructs a new builder initialized from another 
	 * Restriction.
         */
        public Builder(Restriction restriction) {
            super(restriction);
        }

        @Override
        public RestrictionInfo build() {
            return new RestrictionInfo(this);
        }
    }
}
