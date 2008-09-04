
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.AgendaInfoDeterminationStructureDTO;

@XmlRootElement(name = "fetchAgendaInfoDeterminationStructureResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAgendaInfoDeterminationStructureResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FetchAgendaInfoDeterminationStructureResponse {

    @XmlElement(name = "return", namespace = "")
    private AgendaInfoDeterminationStructureDTO _return;

    /**
     * 
     * @return
     *     returns AgendaInfoDeterminationStructureDTO
     */
    public AgendaInfoDeterminationStructureDTO getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(AgendaInfoDeterminationStructureDTO _return) {
        this._return = _return;
    }

}
