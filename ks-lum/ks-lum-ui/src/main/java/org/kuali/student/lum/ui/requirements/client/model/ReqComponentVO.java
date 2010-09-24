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

package org.kuali.student.lum.ui.requirements.client.model;

import java.io.Serializable;

import org.kuali.student.common.ui.client.widgets.rules.Token;
import org.kuali.student.core.dto.Idable;
import org.kuali.student.core.statement.dto.ReqCompFieldInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;

public class ReqComponentVO extends Token implements Serializable, Idable {

    private static final long serialVersionUID = 1L;
    private String id;
    private ReqComponentInfo reqComponentInfo;
    private String typeDesc;
    private boolean dirty;
    private String clusetId = null;
    // for GUI use only the As, Bs and Cs, has no meaning in the LUM service
    private String guiReferenceLabelId;
    
    public ReqComponentVO() {}
    
    public ReqComponentVO(ReqComponentInfo reqComponentInfo) {
        this.reqComponentInfo = reqComponentInfo;
        this.id = reqComponentInfo.getId();
    }

    public ReqComponentInfo getReqComponentInfo() {
        return reqComponentInfo;
    }

    public void setReqComponentInfo(ReqComponentInfo reqComponentInfo) {
        this.reqComponentInfo = reqComponentInfo;
    }        

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
    
    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public String getClusetId() {
        return clusetId;
    }

    public void setClusetId(String clusetId) {
        this.clusetId = clusetId;
    }
    
    public String getGuiReferenceLabelId() {
        return guiReferenceLabelId;
    }

    public void setGuiReferenceLabelId(String guiReferenceLabelId) {
        this.guiReferenceLabelId = guiReferenceLabelId;
        this.setTokenID(guiReferenceLabelId);
    }

    @Override
    public String toString() {                

        String reqCompDescription = typeDesc;
                
        for (ReqCompFieldInfo fieldInfo : reqComponentInfo.getReqCompFields()) {
            reqCompDescription = reqCompDescription.replaceAll("<" + fieldInfo.getId() + ">", fieldInfo.getValue());
        }        

        return reqCompDescription;
    }

    @Override
    public String getId() {        
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

}
