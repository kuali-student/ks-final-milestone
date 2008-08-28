
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.kuali.student.rules.rulesmanagement.dto.AgendaInfoDTO;

@XmlRootElement(name = "fetchAgendaInfoResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAgendaInfoResponse", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FetchAgendaInfoResponse {

    @XmlElement(name = "return", namespace = "")
    private AgendaInfoDTO _return;

    /**
     * 
     * @return
     *     returns AgendaInfoDTO
     */
    public AgendaInfoDTO getReturn() {
        return this._return;
    }

    /**
     * 
     * @param _return
     *     the value for the _return property
     */
    public void setReturn(AgendaInfoDTO _return) {
        this._return = _return;
    }

}
