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
package org.kuali.student.enrollment.acal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.r2.common.dto.KeyEntityInfo;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TermInfo", propOrder = {"key", "typeKey", "stateKey", "name", "descr", "startDate", "endDate", "metaInfo", "attributes", "_futureElements"})

public class TermInfo extends KeyEntityInfo implements Term, Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement
    private final Date startDate;

    @XmlElement
    private final Date endDate;

    @XmlAnyElement
    private final List<Element> _futureElements;

    public static TermInfo newInstance() {
        return new TermInfo();
    }

    public static TermInfo getInstance(Term term) {
        return new TermInfo(term);
    }

    private TermInfo() {
        startDate = null;
        endDate = null;
        _futureElements = null;
    }

    /**
     * Constructs a new TermInfo from another
     * Term.
     *
     * @param term the Term to copy
     */
    public TermInfo(Term term) {
        super(term);

        this.startDate = null != term.getStartDate() ? new Date(term.getStartDate().getTime()) : null;
        this.endDate = null != term.getEndDate() ? new Date(term.getEndDate().getTime()) : null;

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
}
