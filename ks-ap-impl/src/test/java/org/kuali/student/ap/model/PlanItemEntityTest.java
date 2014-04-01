package org.kuali.student.ap.model;

import org.junit.Test;
import org.kuali.student.ap.academicplan.model.PlanItemEntity;

import java.util.*;

import static org.junit.Assert.*;


public class PlanItemEntityTest {
	
	@Test
    public void testExercisePlanTermIdAddRemove() {
        PlanItemEntity pie = new PlanItemEntity();
        //  Provide an empty set since Hibernate isn't "hydrating" the entity.
        Set<String> planTermIds = new HashSet<String>();
        pie.setPlanTermIds(planTermIds);

        assertFalse(pie.addPlanTermId(null));
        assertFalse(pie.addPlanTermId(""));
        assertFalse(pie.addPlanTermId(" "));
        assertTrue(pie.addPlanTermId("pp1"));
        assertFalse(pie.addPlanTermId("pp1"));
        assertFalse(pie.removePlanTermId("unknown"));
        assertFalse(pie.removePlanTermId(null));
        assertTrue(pie.removePlanTermId("pp1"));
    }
}
