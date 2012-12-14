package org.kuali.student.myplan.course.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.rice.kim.api.identity.principal.Principal;
import org.kuali.rice.kim.impl.identity.PersonImpl;
import org.kuali.student.common.search.dto.SearchRequest;
import org.kuali.student.myplan.course.service.CoursePreReqSearch;
import org.kuali.student.myplan.plan.controller.PlanController;
import org.kuali.student.myplan.plan.form.PlanForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 4/12/12
 * Time: 10:32 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:myplan-test-context.xml"})

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
        PlanForm planForm = new PlanForm();
        planForm.setCourseId("e8a3fe1f-0592-4515-822c-4f806910775a");
        PlanController controller = getPlanController();
        controller.start(planForm, null, null, null);
        assertTrue(planForm.getCourseDetails() != null);
    }

    @Test
    public void addPlannedCourseTest() {
        PlanForm planForm = new PlanForm();
        planForm.setCourseId("10421b71-a740-4018-8d60-915ea639b88e");
        planForm.setAtpId("kuali.uw.atp.summer2012");
       /* planForm.setTerm("autumn");
        planForm.setYear("2012");*/
        planForm.setTermYear("kuali.uw.atp.2012.1");
//        person = getPersonImpl();

        planForm.setViewId("PlannedCourse-FormView");
        PlanController controller = getPlanController();
        controller.addPlannedCourse(planForm, null, null, null);
        assertTrue(planForm.getPlanItemId() != null);
    }

    @Test
    public void addSavedCourseTest() {
        PlanForm planForm = new PlanForm();
//        person = getPersonImpl();
        PlanController controller = getPlanController();
        planForm.setCourseId("10421b71-a740-4018-8d60-915ea639b88e");
        controller.addSavedCourse(planForm, null, null, null);
        assertTrue(planForm.getPlanItemId() != null);

    }


    /*TODO: Fix When removePlanItem() in plan controller is fixed */
    @Test
    public void removePlanItemTest() {
        PlanForm planForm = new PlanForm();
//        person = getPersonImpl();
        PlanController controller = getPlanController();
        planForm.setPlanItemId("26d19b30-ce60-4405-80d9-153d263d83cb");
        controller.removePlanItem(planForm, null, null, null);

    }


}
