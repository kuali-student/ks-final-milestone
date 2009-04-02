package org.kuali.student.lum.ui.requirements.client.model;

import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.core.dto.Idable;

public class PrereqInfo implements Idable {

    private String rationale;
    private LuStatementInfo luStatementInfo;
    private String naturalLanguage;
    private String cluId;

    @Override
    public String getId() {
        return cluId;
    }
    @Override
    public void setId(String id) {
        this.cluId = id;
    }
    
}
