package org.kuali.student.ap.model;

import org.junit.Test;
import org.kuali.student.ap.academicplan.model.PlanItemEntity;

import java.util.*;

import static org.junit.Assert.*;


public class PlanItemEntityTest {
	
	@Test
    public void testExercisePlanPeriodsAddRemove() {
        PlanItemEntity pie = new PlanItemEntity();
        //  Provide an empty set since Hibernate isn't "hydrating" the entity.
        Set<String> planPeriods = new HashSet<String>();
        pie.setPlanPeriods(planPeriods);

        assertFalse(pie.addPlanPeriod(null));
        assertFalse(pie.addPlanPeriod(""));
        assertFalse(pie.addPlanPeriod(" "));
        assertTrue(pie.addPlanPeriod("pp1"));
        assertFalse(pie.addPlanPeriod("pp1"));
        assertFalse(pie.removePlanPeriod("unknown"));
        assertFalse(pie.removePlanPeriod(null));
        assertTrue(pie.removePlanPeriod("pp1"));
    }
}
