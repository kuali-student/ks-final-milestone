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
package org.kuali.student.r2.core.exemption.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.core.exemption.infc.AgendaOverride;
import org.w3c.dom.Element;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgendaOverrideInfo", propOrder = {"agendaId", "anchorId",
    "_futureElements"})
public class AgendaOverrideInfo implements AgendaOverride, Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String existingAgendaId;
    @XmlElement
    private String newAgendaId;
    @XmlElement
    private String anchorRefObjectUri;
    @XmlElement
    private String anchorRefObjectId;
    @XmlAnyElement
    private List<Element> _futureElements;

    public AgendaOverrideInfo() {
        super();
        existingAgendaId = null;
        anchorRefObjectId = null;
        _futureElements = null;
    }

    /**
     * Constructs a new AgendaOverrideInfo from another
     * AgendaOverride.
     * 
     * @param orig AgendaOverride to copy
     */
    public AgendaOverrideInfo(AgendaOverride orig) {
        super();
        if (null != orig) {
            this.existingAgendaId = orig.getExistingAgendaId();
            this.newAgendaId = orig.getNewAgendaId();
            this.anchorRefObjectId = orig.getAnchorRefObjectId();
            this.anchorRefObjectUri = orig.getAnchorRefObjectUri();
        }

        _futureElements = null;
    }

    @Override
    public String getExistingAgendaId() {
        return existingAgendaId;
    }

    public void setExistingId(String existingAgendaId) {
        this.existingAgendaId = existingAgendaId;
    }

    @Override
    public String getNewAgendaId() {
        return newAgendaId;
    }

    public void setNewAgendaId(String newAgendaId) {
        this.newAgendaId = newAgendaId;
    }

    @Override
    public String getAnchorRefObjectUri() {
        return anchorRefObjectUri;
    }

    public void setAnchorRefObjectUri(String anchorRefObjectUri) {
        this.anchorRefObjectUri = anchorRefObjectUri;
    }

    @Override
    public String getAnchorRefObjectId() {
        return anchorRefObjectId;
    }

    public void setAnchorId(String anchorRefObjectId) {
        this.anchorRefObjectId = anchorRefObjectId;
    }
}
