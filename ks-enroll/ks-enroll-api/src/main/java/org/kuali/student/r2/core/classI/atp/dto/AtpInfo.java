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
package org.kuali.student.r2.core.classI.atp.dto;

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
import org.kuali.student.r2.core.classI.atp.infc.Atp;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AtpInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "metaInfo", "attributes", "_futureElements"})
public class AtpInfo extends KeyEntityInfo implements Atp, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private final Date startDate;
    @XmlElement
    private final Date endDate;
    @XmlAnyElement
    private final List<Element> _futureElements;

    private AtpInfo() {
        startDate = null;
        endDate = null;
        _futureElements = null;
    }

    /**
     * Constructs a new AtpInfo from another Atp.
     *
     * @param atp the ATP to copy
     */
    public AtpInfo(Atp atp) {
        super(atp);
        this.startDate = atp.getStartDate();
        this.endDate = atp.getEndDate();
        _futureElements = null;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    /**
     * The builder class for this AtpInfo.
     */
    public static class Builder extends KeyEntityInfo.Builder implements ModelBuilder<AtpInfo>, Atp {

        private Date startDate;
        private Date endDate;

        /**
         * Constructs a new builder.
         */
        public Builder() {
        }

        /**
         * Constructs a new builder initialized from another ATP.
         */
        public Builder(Atp atp) {
            super(atp);
            this.startDate = atp.getStartDate();
            this.startDate = atp.getEndDate();
        }

        @Override
        public AtpInfo build() {
            return new AtpInfo(this);
        }

        @Override
        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        @Override
        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }
    }
}
