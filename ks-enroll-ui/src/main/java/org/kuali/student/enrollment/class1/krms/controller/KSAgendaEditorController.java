package org.kuali.student.enrollment.class1.krms.controller;

import org.kuali.rice.krms.impl.ui.AgendaEditorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/01/18
 * Time: 12:36 PM
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping(value = org.kuali.rice.krms.impl.util.KrmsImplConstants.WebPaths.AGENDA_EDITOR_PATH)
public class KSAgendaEditorController extends AgendaEditorController {
}
