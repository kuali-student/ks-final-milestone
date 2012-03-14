package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcademicTermWrapper {

    private static final long serialVersionUID = 4898118410378641665L;

    private TermInfo termInfo;

    private String name;
    private int instructionalDays;
    private String termType;
    private Date startDate;
    private Date endDate;

    private String termNameForUI;
    private List<KeyDatesGroupWrapper> keyDatesGroupWrappers;
    private boolean readOnly = false;

    private TypeInfo typeInfo;

    public AcademicTermWrapper(){
        keyDatesGroupWrappers = new ArrayList();
    }

    public AcademicTermWrapper(TermInfo termInfo){
        this.name = termInfo.getName();
        this.startDate = termInfo.getStartDate();
        this.endDate = termInfo.getEndDate();
        this.termInfo = termInfo;
        this.termType = termInfo.getTypeKey();
        keyDatesGroupWrappers = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TermInfo getTermInfo() {
        return termInfo;
    }

    public void setTermInfo(TermInfo termInfo) {
        this.termInfo = termInfo;
        if (termInfo != null){
            if (StringUtils.equals(AtpServiceConstants.ATP_OFFICIAL_STATE_KEY,termInfo.getStateKey())){
                readOnly = true;
            }
        }
    }

    public int getInstructionalDays() {
        return instructionalDays;
    }

    public void setInstructionalDays(int instructionalDays) {
        this.instructionalDays = instructionalDays;
    }

    public String getTermNameForUI() {
        return termNameForUI;
    }

    public void setTermNameForUI(String termNameForUI) {
        this.termNameForUI = termNameForUI;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public List<KeyDatesGroupWrapper> getKeyDatesGroupWrappers() {
        return keyDatesGroupWrappers;
    }

    public void setKeyDatesGroupWrappers(List<KeyDatesGroupWrapper> keyDatesGroupWrappers) {
        this.keyDatesGroupWrappers = keyDatesGroupWrappers;
    }

    public void clear(){
        setEndDate(null);
        setStartDate(null);
        setTermType(null);
//        setKeydates( new ArrayList<KeyDateWrapper>());
        setName(null);
        setTypeInfo(null);
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }
}
