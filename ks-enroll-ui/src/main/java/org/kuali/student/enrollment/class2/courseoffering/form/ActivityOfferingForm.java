package org.kuali.student.enrollment.class2.courseoffering.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.web.form.MaintenanceForm;

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

}
