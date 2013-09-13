package org.kuali.student.myplan.course.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.myplan.plan.controller.PlanController;
import org.kuali.student.myplan.plan.form.PlanForm;
import org.kuali.rice.krad.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 4/12/12
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ks-ap-test-context.xml"})

public class PlanControllerTest {
    @Autowired
    private PlanController planController;

    public PlanController getPlanController() {
        return planController;
    }

    public void setPlanController(PlanController planController) {
        this.planController = planController;
    }

//    @Autowired
//    private PersonImpl person;
//
//    public PersonImpl getPersonImpl() {
//        return person;
//    }
//
//    public void setPersonImpl(PersonImpl personImpl) {
//        this.person = personImpl;
//    }

    @Test
    public void startAddPlannedCourseFormTest() {
        UserSession session = new UserSession("ks");
        GlobalVariables.setUserSession(session);
        PlanForm planForm = new PlanForm();
        planForm.setCourseId("e8a3fe1f-0592-4515-822c-4f806910775a");
        PlanController controller = getPlanController();
        controller.start(planForm, null, null, null);
        assertTrue(planForm.getCourseSummaryDetails() != null);
    }

    @Test
    public void addPlannedCourseTest() {
        UserSession session = new UserSession("ks");
        GlobalVariables.setUserSession(session);
        PlanForm planForm = new PlanForm();
        planForm.setCourseId("10421b71-a740-4018-8d60-915ea639b88e");
        planForm.setAtpId("kuali.atp.1991.1");
       /* planForm.setTerm("autumn");
        planForm.setYear("2012");*/
//        person = getPersonImpl();

        planForm.setViewId("PlannedCourse-FormView");
        PlanController controller = getPlanController();
        controller.addPlannedCourse(planForm, null, null, null);
        // TODO: need reference data to support this, see KSAP-5
        //assertTrue(planForm.getPlanItemId() != null);
    }

    @Test
    public void addSavedCourseTest() {
        UserSession session = new UserSession("ks");
        GlobalVariables.setUserSession(session);
        PlanForm planForm = new PlanForm();
//        person = getPersonImpl();
        PlanController controller = getPlanController();
        planForm.setCourseId("10421b71-a740-4018-8d60-915ea639b88e");
        controller.addSavedCourse(planForm, null, null, null);
        // TODO: need reference data to support this, see KSAP-5
        //assertTrue(planForm.getPlanItemId() != null);

    }


    /*TODO: Fix When removePlanItem() in plan controller is fixed */
    @Test
    public void removePlanItemTest() {
        UserSession session = new UserSession("ks");
        GlobalVariables.setUserSession(session);
        PlanForm planForm = new PlanForm();
//        person = getPersonImpl();
        PlanController controller = getPlanController();
        planForm.setPlanItemId("26d19b30-ce60-4405-80d9-153d263d83cb");
        controller.removePlanItem(planForm, null, null, null);

    }


}
