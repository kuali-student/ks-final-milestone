package org.kuali.student.lum.ui.requirements.client.model;

import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.core.dto.Idable;

public class PrereqInfo implements Idable {

    private String cluId;
    private StatementVO statementVO;
    private String rationale;
    private String naturalLanguage;

    
    @Override
    public String getId() {
        return cluId;
    }
    @Override
    public void setId(String id) {
        this.cluId = id;
    }
    public String getCluId() {
        return cluId;
    }
    public void setCluId(String cluId) {
        this.cluId = cluId;
    }
    public String getRationale() {
        return rationale;
    }
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }
    public String getNaturalLanguage() {
        return naturalLanguage;
    }
    public void setNaturalLanguage(String naturalLanguage) {
        this.naturalLanguage = naturalLanguage;
    }
    
    public StatementVO getStatementVO() {
        return statementVO;
    }
    public void setStatementVO(StatementVO statementVO) {
        this.statementVO = statementVO;
    }
    public Node getStatementTree() {
        Node tree = null;
        if (statementVO != null) {          
            tree = statementVO.getTree();
        }
        return tree;
    }
}
