package org.kuali.student.cm.course.controller;

import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.web.form.DocumentFormBase;

/**
 * Created with IntelliJ IDEA.
 * User: delyea
 * Date: 3/19/14
 * Time: 4:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface CourseControllerTransactionHelper {

    public void performWorkflowActionSuper(DocumentFormBase form, UifConstants.WorkflowAction action, boolean checkSensitiveData, CourseController courseController);

}
