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

package org.kuali.student.core.statement.ui.client.widgets.rules;

import org.kuali.student.r1.common.dto.Idable;
import org.kuali.student.r1.core.statement.dto.ReqComponentInfo;

import java.io.Serializable;

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
    public String getId() {        
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        /*
        String reqCompDescription = typeDesc;

        for (ReqCompFieldInfo fieldInfo : reqComponentInfo.getReqCompFields()) {
            reqCompDescription = reqCompDescription.replaceAll("<" + fieldInfo.getId() + ">", fieldInfo.getValue());
        }

        return (reqCompDescription == null ? "" : reqCompDescription); */
        return reqComponentInfo.getNaturalLanguageTranslation();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ReqComponentVO that = (ReqComponentVO) o;

        if (dirty != that.dirty) return false;
        if (clusetId != null ? !clusetId.equals(that.clusetId) : that.clusetId != null) return false;
        if (guiReferenceLabelId != null ? !guiReferenceLabelId.equals(that.guiReferenceLabelId) : that.guiReferenceLabelId != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reqComponentInfo != null ? !reqComponentInfo.equals(that.reqComponentInfo) : that.reqComponentInfo != null)
            return false;
        if (typeDesc != null ? !typeDesc.equals(that.typeDesc) : that.typeDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (reqComponentInfo != null ? reqComponentInfo.hashCode() : 0);
        result = 31 * result + (typeDesc != null ? typeDesc.hashCode() : 0);
        result = 31 * result + (dirty ? 1 : 0);
        result = 31 * result + (clusetId != null ? clusetId.hashCode() : 0);
        result = 31 * result + (guiReferenceLabelId != null ? guiReferenceLabelId.hashCode() : 0);
        return result;
    }
}
