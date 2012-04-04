package org.kuali.student.enrollment.class2.acal.dto;

import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.r2.core.type.dto.TypeInfo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class KeyDateWrapper extends TimeSetWrapper{

    private String keyDateType;
    private String keyDateNameUI;

    private KeyDateInfo keyDateInfo;
    private TypeInfo typeInfo;

    //UI related
    private boolean showAddLine;

    public KeyDateWrapper(){
        setAllDay(false);
        setDateRange(true);
        setShowAddLine(true);
    }

    public KeyDateWrapper(KeyDateInfo keydate){
        this.setKeyDateInfo(keydate);
        this.setStartDate(keydate.getStartDate());
        this.setAllDay(keydate.getIsAllDay());
        this.setDateRange(keydate.getIsDateRange());
        this.setKeyDateType(keydate.getTypeKey());
        this.setEndDate(keydate.getEndDate());

        //This is needed to display enddate for readonly view.
        endDateUI = keydate.getEndDate();

        // If not all day, set start/end time in the wrapper
        if (!isAllDay()){
            DateFormat dfm = new SimpleDateFormat("hh:mm");

            setStartTime(dfm.format(keydate.getStartDate()));
            setEndTime(dfm.format(keydate.getEndDate()));

            dfm = new SimpleDateFormat("a");
            setStartTimeAmPm(dfm.format(keydate.getStartDate()));
            setEndTimeAmPm(dfm.format(keydate.getEndDate()));

            if (!isDateRange()) {
                setEndDate(null);
            }
        }
    }

    public void copy(KeyDateInfo keydate){
        keyDateInfo = new KeyDateInfo();
        this.setKeyDateType(keydate.getTypeKey());
        this.setAllDay(keydate.getIsAllDay());
        this.setDateRange(keydate.getIsDateRange());
        this.setStartDate(null);
        this.setEndDate(null);

        //Copy only start/end time
        if (!isAllDay()){
            DateFormat dfm = new SimpleDateFormat("hh:mm");

            setStartTime(dfm.format(keydate.getStartDate()));
            setEndTime(dfm.format(keydate.getEndDate()));

            dfm = new SimpleDateFormat("a");
            setStartTimeAmPm(dfm.format(keydate.getStartDate()));
            setEndTimeAmPm(dfm.format(keydate.getEndDate()));

        }
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

    public boolean isShowAddLine() {
        return showAddLine;
    }

    public void setShowAddLine(boolean showAddLine) {
        this.showAddLine = showAddLine;
    }

}
