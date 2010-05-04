/**
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

package org.kuali.student.brms.rulemanagement.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.kuali.student.core.ws.binding.JaxbAttributeMapListAdapter;


/**
 * Maps a set of Business Rule Types to form an AgendaInfo and a specif instance of Business Rules form an Agenda for this
 * AgendaInfo
 * 
 * @author Kuali Student Team (kamal.kuali@gmail.com)
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AgendaDeterminationInfo implements Serializable {
        
    private static final long serialVersionUID = 1L;
    
    @XmlElement(name="agendaDeterminationKey")
    @XmlJavaTypeAdapter(JaxbAttributeMapListAdapter.class)
    private Map<String, String> agendaDeterminationKeyList;

    /**
     * @return the agendaDeterminationKeyList
     */
    public Map<String, String> getAgendaDeterminationKeyList() {
        if(null == agendaDeterminationKeyList) {
            return new HashMap<String, String> ();
        }
        return agendaDeterminationKeyList;
    }

    /**
     * @param agendaDeterminationKeyList the agendaInfoDeterminationKeyList to set
     */
    public void setAgendaInfoDeterminationKeyList(Map<String, String> agendaDeterminationKeyList) {
        this.agendaDeterminationKeyList = agendaDeterminationKeyList;
    }
}
