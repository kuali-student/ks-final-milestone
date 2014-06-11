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
     private String editTermKey;
     private String editDays;
     private String editStartTime;
     private String editStartTimeAmPm;
     private String editEndTime;
     private String editEndTimeAmPm;

     private String addTermKey;
     private String addDays;
     private String addStartTime;
     private String addStartTimeAmPm;
     private String addEndTime;
     private String addEndTimeAmPm;

     private boolean editInProcess;

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
         if (isEditInProcess()){
            return getEditTermKey();
         } else {
            return getAddTermKey();
         }
     }

     public String getAddOrEditDays() {
         if (isEditInProcess()){
            return getEditDays();
          } else {
            return getAddDays();
          }
     }

     public String getAddOrEditStartTime() {
         if (isEditInProcess()){
            return getEditStartTime();
          } else {
             return getAddStartTime();
          }
     }

     public String getAddOrEditStartTimeAmPm() {
         if (isEditInProcess()){
            return getEditStartTimeAmPm();
          } else {
            return getAddStartTimeAmPm();
          }
     }

     public String getAddOrEditEndTime() {
         if (isEditInProcess()){
            return getEditEndTime();
          } else {
            return getAddEndTime();
          }
     }

     public String getAddOrEditEndTimeAmPm() {
         if (isEditInProcess()){
            return getEditEndTimeAmPm();
          } else {
            return getAddEndTimeAmPm();
          }
     }

    public void setAddOrEditTermKey(String termKey) {
         if (isEditInProcess()){
            setEditTermKey(termKey);
         } else {
            setAddTermKey(termKey);
         }
     }

     public void setAddOrEditDays(String days) {
         if (isEditInProcess()){
            setEditDays(days);
          } else {
            setAddDays(days);
          }
     }

     public void setAddOrEditStartTime(String startTime) {
         if (isEditInProcess()){
            setEditStartTime(startTime);
          } else {
             setAddStartTime(startTime);
          }
     }

     public void setAddOrEditStartTimeAmPm(String startTimeAmPm) {
         if (isEditInProcess()){
            setEditStartTimeAmPm(startTimeAmPm);
          } else {
            setAddStartTimeAmPm(startTimeAmPm);
          }
     }

     public void setAddOrEditEndTime(String endTime) {
         if (isEditInProcess()){
            setEditEndTime(endTime);
          } else {
            setAddEndTime(endTime);
          }
     }

     public void setAddOrEditEndTimeAmPm(String endTimeAmPm) {
         if (isEditInProcess()){
            setEditEndTimeAmPm(endTimeAmPm);
          } else {
            setAddEndTimeAmPm(endTimeAmPm);
          }
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

    public String getEditTermKey() {
        return editTermKey;
    }

    public void setEditTermKey(String editTermKey) {
        this.editTermKey = editTermKey;
    }

    public String getEditDays() {
        return editDays;
    }

    public void setEditDays(String editDays) {
        this.editDays = editDays;
    }

    public String getEditStartTime() {
        return editStartTime;
    }

    public void setEditStartTime(String editStartTime) {
        this.editStartTime = editStartTime;
    }

    public String getEditStartTimeAmPm() {
        return editStartTimeAmPm;
    }

    public void setEditStartTimeAmPm(String editStartTimeAmPm) {
        this.editStartTimeAmPm = editStartTimeAmPm;
    }

    public String getEditEndTime() {
        return editEndTime;
    }

    public void setEditEndTime(String editEndTime) {
        this.editEndTime = editEndTime;
    }

    public String getEditEndTimeAmPm() {
        return editEndTimeAmPm;
    }

    public void setEditEndTimeAmPm(String editEndTimeAmPm) {
        this.editEndTimeAmPm = editEndTimeAmPm;
    }

    public String getAddTermKey() {
        return addTermKey;
    }

    public void setAddTermKey(String addTermKey) {
        this.addTermKey = addTermKey;
    }

    public String getAddDays() {
        return addDays;
    }

    public void setAddDays(String addDays) {
        this.addDays = addDays;
    }

    public String getAddStartTime() {
        return addStartTime;
    }

    public void setAddStartTime(String addStartTime) {
        this.addStartTime = addStartTime;
    }

    public String getAddStartTimeAmPm() {
        return addStartTimeAmPm;
    }

    public void setAddStartTimeAmPm(String addStartTimeAmPm) {
        this.addStartTimeAmPm = addStartTimeAmPm;
    }

    public String getAddEndTime() {
        return addEndTime;
    }

    public void setAddEndTime(String addEndTime) {
        this.addEndTime = addEndTime;
    }

    public String getAddEndTimeAmPm() {
        return addEndTimeAmPm;
    }

    public void setAddEndTimeAmPm(String addEndTimeAmPm) {
        this.addEndTimeAmPm = addEndTimeAmPm;
    }

    public boolean isEditInProcess() {
        return editInProcess;
    }

    public void setEditInProcess(boolean editInProcess) {
        this.editInProcess = editInProcess;
    }

}
