package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.MaintenanceForm;
import org.kuali.student.enrollment.class2.courseoffering.dto.ActivityOfferingWrapper;

/**
 * Created by IntelliJ IDEA.
 * User: venkat
 * Date: 9/26/12
 * Time: 4:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class ActivityOfferingForm extends MaintenanceForm {

    public static final String MAIN_PAGE = "ActivityOfferingEdit-MainPage";
    public static final String SCHEDULE_PAGE = "ActivityOfferingEdit-SchedulePage";

    private boolean showReviseLink;

    public ActivityOfferingForm(){
        showReviseLink = true;
    }

    public boolean isSchedulePage(){
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
    }

    public boolean isShowReviseLink() {
        ActivityOfferingWrapper dataObject = (ActivityOfferingWrapper)getDocument().getNewMaintainableObject().getDataObject();
        if (showReviseLink && dataObject.getActualScheduleComponents().isEmpty()){
            return true;
        }
        return false;
    }

    public void setShowReviseLink(boolean showReviseLink) {
        this.showReviseLink = showReviseLink;
    }


}
