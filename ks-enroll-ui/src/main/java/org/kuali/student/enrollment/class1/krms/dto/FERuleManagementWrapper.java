/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.enrollment.class1.krms.dto;

import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kuali Student Team
 */
public class FERuleManagementWrapper extends RuleManagementWrapper {

    private TypeInfo type;
    private String termToUse;
    private boolean location;
    private List<String> linkedTermTypes;

    public FERuleManagementWrapper() {
        this.setTermToUse("na");
    }

    public TypeInfo getType() {
        return type;
    }

    public void setType(TypeInfo type) {
        this.type = type;
    }

    public String getTermToUse() {
        return termToUse;
    }

    public void setTermToUse(String termToUse) {
        this.termToUse = termToUse;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public List<String> getLinkedTermTypes() {
        if(linkedTermTypes==null){
            linkedTermTypes = new ArrayList<String>();
        }
        return linkedTermTypes;
    }

    public void setLinkedTermTypes(List<String> linkedTermTypes) {
        this.linkedTermTypes = linkedTermTypes;
    }

    public boolean isMatrixEmpty(){
        for(AgendaEditor agenda : this.getAgendas()){
            FEAgendaEditor feAgenda = (FEAgendaEditor) agenda;
            if(!feAgenda.getRules().isEmpty()){
                return false;
            }
        }
        return true;
    }
}
