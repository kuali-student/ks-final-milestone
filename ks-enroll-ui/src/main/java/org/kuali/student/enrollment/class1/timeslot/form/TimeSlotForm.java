package org.kuali.student.enrollment.class1.timeslot.form;

import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.student.common.uif.form.KSUifForm;
import org.kuali.student.enrollment.class1.timeslot.dto.TimeSlotWrapper;

import java.util.ArrayList;
import java.util.List;

/**
  * Form for Manage Time Slots.
  */
 public class TimeSlotForm extends KSUifForm {

     /*
      * A list of Type Slot type KeyValues.
      */
     private List<KeyValue> timeSlotTypeKeyValues;

     //  Term type multi-select list selections.
     private List<String> termTypeSelections;
     private List<String> typeNameSelections;
     private String typeNamesUI;

     private boolean timeSlotsLoaded = false;
     private boolean enableAddButton = false;

     //  Storage for Time Slot search results.
     private List<TimeSlotWrapper> timeSlotResults;

    //  Selected Time Slot for deletion.
    private List<TimeSlotWrapper> selectedTimeSlots;
    /*
     These are the properties used at the Add/Edit popup.
      */
     private String addOrEditTermKey;
     private String addOrEditDays;
     private String addOrEditStartTime;
     private String addOrEditStartTimeAmPm;
     private String addOrEditEndTime;
     private String addOrEditEndTimeAmPm;

     public TimeSlotForm() {
         termTypeSelections = new ArrayList<String>();
         timeSlotResults = new ArrayList<TimeSlotWrapper>();
         typeNameSelections = new ArrayList<String>();
         selectedTimeSlots = new  ArrayList<TimeSlotWrapper>();
     }

     public List<TimeSlotWrapper> getTimeSlotResults() {
         return timeSlotResults;
     }

     public void setTimeSlotResults(List<TimeSlotWrapper> timeSlotResults) {
         this.timeSlotResults = timeSlotResults;
     }

     public List<String> getTermTypeSelections() {
         return termTypeSelections;
     }

     public void setTermTypeSelections(List<String> termTypeSelections) {
         this.termTypeSelections = termTypeSelections;
     }

     public boolean isTimeSlotsLoaded() {
         return timeSlotsLoaded;
     }

     public void setTimeSlotsLoaded(boolean timeSlotsLoaded) {
         this.timeSlotsLoaded = timeSlotsLoaded;
     }

     public String getAddOrEditTermKey() {
         return addOrEditTermKey;
     }

     public void setAddOrEditTermKey(String addOrEditTermKey) {
         this.addOrEditTermKey = addOrEditTermKey;
     }

     public String getAddOrEditDays() {
         return addOrEditDays;
     }

     public void setAddOrEditDays(String addOrEditDays) {
         this.addOrEditDays = addOrEditDays;
     }

     public String getAddOrEditStartTime() {
         return addOrEditStartTime;
     }

     public void setAddOrEditStartTime(String addOrEditStartTime) {
         this.addOrEditStartTime = addOrEditStartTime;
     }

     public String getAddOrEditStartTimeAmPm() {
         return addOrEditStartTimeAmPm;
     }

     public void setAddOrEditStartTimeAmPm(String addOrEditStartTimeAmPm) {
         this.addOrEditStartTimeAmPm = addOrEditStartTimeAmPm;
     }

     public String getAddOrEditEndTime() {
         return addOrEditEndTime;
     }

     public void setAddOrEditEndTime(String addOrEditEndTime) {
         this.addOrEditEndTime = addOrEditEndTime;
     }

     public String getAddOrEditEndTimeAmPm() {
         return addOrEditEndTimeAmPm;
     }

     public void setAddOrEditEndTimeAmPm(String addOrEditEndTimeAmPm) {
         this.addOrEditEndTimeAmPm = addOrEditEndTimeAmPm;
     }

     public boolean isEnableAddButton() {
         return enableAddButton;
     }

     public void setEnableAddButton(boolean enableAddButton) {
         this.enableAddButton = enableAddButton;
     }

     public List<KeyValue> getTimeSlotTypeKeyValues() {
         return timeSlotTypeKeyValues;
     }

     public void setTimeSlotTypeKeyValues(List<KeyValue> timeslotKeyValues) {
         this.timeSlotTypeKeyValues = timeslotKeyValues;
     }

    public List<String> getTypeNameSelections() {
        return typeNameSelections;
    }

    public void setTypeNameSelections(List<String> typeNameSelections) {
        this.typeNameSelections = typeNameSelections;
    }

    public List<TimeSlotWrapper> getSelectedTimeSlots() {
        return selectedTimeSlots;
    }

    public void setSelectedTimeSlots(List<TimeSlotWrapper> selectedTimeSlots) {
        this.selectedTimeSlots = selectedTimeSlots;
    }

    public String getTypeNamesUI() {
        return typeNamesUI;
    }

    public void setTypeNamesUI(String typeNamesUI) {
        this.typeNamesUI = typeNamesUI;
    }
}
