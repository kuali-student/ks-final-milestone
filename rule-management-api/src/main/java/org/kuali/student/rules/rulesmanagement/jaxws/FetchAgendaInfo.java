
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.AgendaInfoDeterminationStructureDTO;

@XmlRootElement(name = "fetchAgendaInfo", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAgendaInfo", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement", propOrder = {
    "agendaTypeKey",
    "agendaInfoDeterminationStructure"
})
public class FetchAgendaInfo {

    @XmlElement(name = "agendaTypeKey", namespace = "")
    private String agendaTypeKey;
    @XmlElement(name = "agendaInfoDeterminationStructure", namespace = "")
    private AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure;

    /**
     * 
     * @return
     *     returns String
     */
    public String getAgendaTypeKey() {
        return this.agendaTypeKey;
    }

    /**
     * 
     * @param agendaTypeKey
     *     the value for the agendaTypeKey property
     */
    public void setAgendaTypeKey(String agendaTypeKey) {
        this.agendaTypeKey = agendaTypeKey;
    }

    /**
     * 
     * @return
     *     returns AgendaInfoDeterminationStructureDTO
     */
    public AgendaInfoDeterminationStructureDTO getAgendaInfoDeterminationStructure() {
        return this.agendaInfoDeterminationStructure;
    }

    /**
     * 
     * @param agendaInfoDeterminationStructure
     *     the value for the agendaInfoDeterminationStructure property
     */
    public void setAgendaInfoDeterminationStructure(AgendaInfoDeterminationStructureDTO agendaInfoDeterminationStructure) {
        this.agendaInfoDeterminationStructure = agendaInfoDeterminationStructure;
    }

}
