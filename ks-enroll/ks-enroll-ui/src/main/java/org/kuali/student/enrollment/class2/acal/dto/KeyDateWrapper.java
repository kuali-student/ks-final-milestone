package org.kuali.student.enrollment.class2.acal.dto;

import org.apache.commons.lang.StringUtils;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;
import org.kuali.student.r2.common.dto.TypeInfo;

public class KeyDateWrapper extends TimeSetWrapper{

    private String keyDateType;
    private String keyDateNameUI;

    private KeyDateInfo keyDateInfo;
    private TypeInfo typeInfo;

    public KeyDateWrapper(){
        setAllDay(false);
        setDateRange(true);
        keyDateInfo = new KeyDateInfo();
        keyDateInfo.setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        RichTextInfo desc = new RichTextInfo();
        desc.setPlain("test");
        keyDateInfo.setDescr(desc);
    }

    public KeyDateWrapper(KeyDateInfo keydate,boolean isCopy){

        this.setStartDate(keydate.getStartDate());
        this.setEndDate(keydate.getEndDate());
        this.setAllDay(keydate.getIsAllDay());
        this.setDateRange(keydate.getIsDateRange());
        this.setKeyDateType(keydate.getTypeKey());

        if (isCopy){
            this.setKeyDateInfo(new KeyDateInfo());
            RichTextInfo desc = new RichTextInfo();
            desc.setPlain(keydate.getTypeKey());
            getKeyDateInfo().setDescr(desc);
            getKeyDateInfo().setStateKey(AtpServiceConstants.MILESTONE_DRAFT_STATE_KEY);
        }else{
            this.setKeyDateInfo(keydate);
        }

        buildDateAndTime();
    }

    public String getKeyDateType() {
        return keyDateType;
    }

    public void setKeyDateType(String keyDateType) {
        this.keyDateType = keyDateType;
    }

    public KeyDateInfo getKeyDateInfo() {
        return keyDateInfo;
    }

    public void setKeyDateInfo(KeyDateInfo keyDateInfo) {
        this.keyDateInfo = keyDateInfo;
    }

    public String getKeyDateNameUI() {
        return keyDateNameUI;
    }

    public void setKeyDateNameUI(String keyDateNameUI) {
        this.keyDateNameUI = keyDateNameUI;
    }

    public TypeInfo getTypeInfo() {
        return typeInfo;
    }

    public void setTypeInfo(TypeInfo typeInfo) {
        this.typeInfo = typeInfo;
    }

    public boolean isNew() {
        return StringUtils.isBlank(keyDateInfo.getId());
    }

    //This is for UI display purpose
    public String getStartDateUI(){
        return formatStartDateUI(keyDateInfo.getStartDate());
    }

    //This is for UI display purpose
    public String getEndDateUI(){
        return formatEndDateUI(keyDateInfo.getEndDate());
    }

}
