package org.kuali.student.enrollment.class2.appointment.controller;

import javax.servlet.http.HttpServletRequest;

import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */

@Controller
@RequestMapping(value = "/registrationWindows")
public class RegistrationWindowsController extends UifControllerBase {

    @Override
    protected UifFormBase createInitialForm(HttpServletRequest request) {
        return new RegistrationWindowsManagementForm();
    }
}
