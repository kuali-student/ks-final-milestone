package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;

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
        keyDatesToDeleteOnSave = new ArrayList<KeyDateWrapper>();
        termInfo = new TermInfo();
        termInfo.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("Test");
        termInfo.setDescr(desc);

    }

    public AcademicTermWrapper(TermInfo termInfo,boolean isCopy){
        this.startDate = termInfo.getStartDate();
        this.endDate = termInfo.getEndDate();
        this.termType = termInfo.getTypeKey();
        this.keyDatesGroupWrappers = new ArrayList();
        this.keyDatesToDeleteOnSave = new ArrayList<KeyDateWrapper>();

        if (isCopy){
            setTermInfo(new TermInfo());
            RichTextInfo desc = new RichTextInfo();
            desc.setPlain(termInfo.getTypeKey());
            getTermInfo().setDescr(desc);
            getTermInfo().setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        } else{
           setTermInfo(termInfo);
           this.name = termInfo.getName();
        }

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

    public boolean isKeyDateGroupExists(String keydateGroupTypeKey){
        for(KeyDatesGroupWrapper wrapper : keyDatesGroupWrappers){
            if (StringUtils.equalsIgnoreCase(wrapper.getKeyDateGroupType(),keydateGroupTypeKey)){
                return true;
            }
        }
        return false;
    }

}
