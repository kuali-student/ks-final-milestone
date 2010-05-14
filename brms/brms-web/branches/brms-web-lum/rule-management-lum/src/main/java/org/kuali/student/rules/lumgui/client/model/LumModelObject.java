package org.kuali.student.rules.lumgui.client.model;

import java.util.List;


public class LumModelObject implements ILumModelObject {
    
    static final long serialVersionUID = 91283749088L;
    
    public static enum FieldName{
        STATEMENT_ID, SHOW_ALGEBRA, CURRENT_VIEW,
        RATIONALE, PRE_REQ_COURSES, NATURAL_LANGUAGE, ALGEBRA,
        STATEMENT, REQ_TYPE;
    }
    public static enum LumView {
        SIMPLE_VIEW, COMPLEX_VIEW;
        
        public int getViewIndex() {
            int viewIndex = -1;
            if (this == SIMPLE_VIEW) {
                viewIndex = 0;
            } else if (this == COMPLEX_VIEW) {
                viewIndex = 1;
            }
            return viewIndex;
        }
    }
    
    //COMMON data
    private String statementId;
    private StatementVO statement;
    private LumView currentView;
    
    //SIMPLE VIEW data
    private String rationale;
    private String preReqCourses;
    private String naturalLanguage;
    
    //COMPLEX VIEW data
    private boolean showAlgebra;
    private String algebra;
    
    //REQUIREMENT DIALOG data
    private boolean showRequirementDialog;
    private List<ReqComponentTypeInfo> reqComponentTypeInfoList;
    private String selectedRequirementType;

    public LumView getCurrentView() {
        return currentView;
    }
    public void setCurrentView(LumView currentView) {
        this.currentView = currentView;
    }
    public boolean isShowAlgebra() {
        return showAlgebra;
    }
    public void setShowAlgebra(boolean showAlgebra) {
        this.showAlgebra = showAlgebra;
    }
    public String getUniqueId() {
        return getStatementId();
    }
    public String getStatementId() {
        return statementId;
    }
    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }
    public String getRationale() {
        return rationale;
    }
    public void setRationale(String rationale) {
        this.rationale = rationale;
    }
    public String getPreReqCourses() {
        return preReqCourses;
    }
    public void setPreReqCourses(String preReqCourses) {
        this.preReqCourses = preReqCourses;
    }
    public String getNaturalLanguage() {
        return naturalLanguage;
    }
    public void setNaturalLanguage(String naturalLanguage) {
        this.naturalLanguage = naturalLanguage;
    }
    public String getAlgebra() {
        return algebra;
    }
    public void setAlgebra(String algebra) {
        this.algebra = algebra;
    }        
    public boolean isShowRequirementDialog() {
        return showRequirementDialog;
    }
    public void setShowRequirementDialog(boolean showRequirementDialog) {
        this.showRequirementDialog = showRequirementDialog;
    }
    public StatementVO getStatement() {
        return statement;
    }
    public void setStatement(StatementVO statement) {
        this.statement = statement;
    }    
    public String getSelectedRequirementType() {
        return selectedRequirementType;
    }
    public void setSelectedRequirementType(String selectedRequirementType) {
        this.selectedRequirementType = selectedRequirementType;
    }        
    public List<ReqComponentTypeInfo> getReqComponentTypeInfoList() {
        return reqComponentTypeInfoList;
    }
    public void setReqComponentTypeInfoList(List<ReqComponentTypeInfo> reqComponentTypeInfoList) {
        this.reqComponentTypeInfoList = reqComponentTypeInfoList;
    }
    
    public Object getValue(String fieldName) {
        FieldName enumFieldName = FieldName.valueOf(fieldName);
        if (enumFieldName == FieldName.STATEMENT_ID) {
            return getStatementId();
        } else if (enumFieldName == FieldName.SHOW_ALGEBRA) {
            return new Boolean(isShowAlgebra());
        } else if (enumFieldName == FieldName.CURRENT_VIEW) {
            LumView lumView = getCurrentView();
            return new Integer(lumView.getViewIndex());
        } else if (enumFieldName == FieldName.RATIONALE) {
            return getRationale();
        } else if (enumFieldName == FieldName.PRE_REQ_COURSES) {
            return getPreReqCourses();
        } else if (enumFieldName == FieldName.NATURAL_LANGUAGE) {
            return getNaturalLanguage();
        } else if (enumFieldName == FieldName.ALGEBRA) {
            return getNaturalLanguage();
        } else if (enumFieldName == FieldName.STATEMENT) {
            return getStatement();
        } else if (enumFieldName == FieldName.REQ_TYPE) {
            return getSelectedRequirementType();
        } else {
            throw new RuntimeException("Unknown fieldName " + fieldName);
        }
    }        
}
