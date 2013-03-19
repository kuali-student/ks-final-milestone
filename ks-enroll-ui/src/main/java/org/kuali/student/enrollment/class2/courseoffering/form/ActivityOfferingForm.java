package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;
import org.kuali.student.enrollment.uif.form.KSUifMaintenanceDocumentForm;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/26/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityOfferingForm extends KSUifMaintenanceDocumentForm {

   /* public static final String MAIN_PAGE = "ActivityOfferingEdit-MainPage";
    public static final String SCHEDULE_PAGE = "ActivityOfferingEdit-SchedulePage";*/

//    private String deliveryLogisiticsAddButtonText;
    private boolean scheduleEditInProgress;

    public ActivityOfferingForm(){
    }

    /*public boolean isSchedulePage(){
        View view = getPostedView();
        if (view == null){
            view = getView();
        }
        return StringUtils.equals(view.getCurrentPageId(),SCHEDULE_PAGE);
    }

    public boolean isMainPage(){
        View view = getPostedView();
        if (view == null){
            view = getView();
        }
        return StringUtils.equals(view.getCurrentPageId(),MAIN_PAGE);
    }*/

    /**
    * By default, the new schedule request add button will be 'Add'.
    *
    * Note: References can be found only at the view xml (ActivityOfferingEdit-SchedulePage.xml), not on the java code
    *
    */
   @SuppressWarnings("unused")
    /*public String getDeliveryLogisiticsAddButtonText() {
        if (isMainPage()){
            return "Add";
        } else{
            return deliveryLogisiticsAddButtonText;
        }
    }

    public void setDeliveryLogisiticsAddButtonText(String deliveryLogisiticsAddButtonText) {
         this.deliveryLogisiticsAddButtonText = deliveryLogisiticsAddButtonText;
    }*/

    public boolean isScheduleEditInProgress() {
        return scheduleEditInProgress;
    }

    public void setScheduleEditInProgress(boolean scheduleEditInProgress) {
        this.scheduleEditInProgress = scheduleEditInProgress;
    }

    /**
     * This is to whether display the new schedule request section or not.
     *
     * Note: References can be found only at the view xml (ActivityOfferingEdit-SchedulePage.xml), not on the java code
     *
     */
    @SuppressWarnings("unused")
    public boolean isScheduleCompleted(){
        ActivityOfferingWrapper wrapper = (ActivityOfferingWrapper)getDocument().getNewMaintainableObject().getDataObject();
        if (wrapper.getSocInfo() != null){
            if (StringUtils.equals(CourseOfferingSetServiceConstants.SOC_SCHEDULING_STATE_COMPLETED,wrapper.getSocInfo().getSchedulingStateKey())){
                return true;
            }
        }
        return false;
    }

}
