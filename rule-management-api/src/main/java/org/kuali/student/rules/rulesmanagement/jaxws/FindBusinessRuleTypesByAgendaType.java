
package org.kuali.student.rules.rulesmanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "findBusinessRuleTypesByAgendaType", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "findBusinessRuleTypesByAgendaType", namespace = "http://student.kuali.org/poc/wsdl/brms/rulesmanagement")
public class FindBusinessRuleTypesByAgendaType {

    @XmlElement(name = "agendaTypeKey", namespace = "")
    private String agendaTypeKey;

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

}
