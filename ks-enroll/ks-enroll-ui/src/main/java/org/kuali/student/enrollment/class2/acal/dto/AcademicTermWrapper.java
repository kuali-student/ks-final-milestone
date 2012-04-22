package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
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

    private TypeInfo typeInfo;

    private List<KeyDateWrapper> keyDatesToDeleteOnSave;

    private boolean isOfficial;
    private boolean isAlreadySaved;


    public AcademicTermWrapper(){
        keyDatesGroupWrappers = new ArrayList();
        termInfo = new TermInfo();
        keyDatesToDeleteOnSave = new ArrayList<KeyDateWrapper>();
    }

    public AcademicTermWrapper(TermInfo termInfo){
        this.name = termInfo.getName();
        this.startDate = termInfo.getStartDate();
        this.endDate = termInfo.getEndDate();
        this.termInfo = termInfo;
        this.termType = termInfo.getTypeKey();
        this.keyDatesGroupWrappers = new ArrayList();
        this.keyDatesToDeleteOnSave = new ArrayList<KeyDateWrapper>();
    }

    public void copy(TermInfo termInfo){
        this.startDate = termInfo.getStartDate();
        this.endDate = termInfo.getEndDate();
        this.termType = termInfo.getTypeKey();
        this.keyDatesGroupWrappers = new ArrayList();
        this.keyDatesToDeleteOnSave = new ArrayList<KeyDateWrapper>();
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

    public List<KeyDatesGroupWrapper> getKeyDatesGroupWrappers() {
        return keyDatesGroupWrappers;
    }

    public void setKeyDatesGroupWrappers(List<KeyDatesGroupWrapper> keyDatesGroupWrappers) {
        this.keyDatesGroupWrappers = keyDatesGroupWrappers;
    }

    public List<KeyDateWrapper> getKeyDatesToDeleteOnSave() {
        return keyDatesToDeleteOnSave;
    }

    public void setKeyDatesToDeleteOnSave(List<KeyDateWrapper> keyDatesToDeleteOnSave) {
        this.keyDatesToDeleteOnSave = keyDatesToDeleteOnSave;
    }

    public void clear(){
        setEndDate(null);
        setStartDate(null);
        setTermType(null);
//        setKeydates( new ArrayList<KeyDateWrapper>());
        setName(null);
        setTypeInfo(null);
        keyDatesToDeleteOnSave.clear();
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public boolean isOfficial() {
        return StringUtils.equals(termInfo.getStateKey(), AcademicCalendarServiceConstants.TERM_OFFICIAL_STATE_KEY);
    }

    public boolean isNew() {
        return StringUtils.isBlank(termInfo.getId());
    }

    public boolean isKeyDateGroupAlreadyExists(String keydateGroupTypeKey){
        for(KeyDatesGroupWrapper wrapper : keyDatesGroupWrappers){
            if (StringUtils.equalsIgnoreCase(wrapper.getKeyDateGroupType(),keydateGroupTypeKey)){
                return true;
            }
        }
        return false;
    }

}
