package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.schedulebuilder.ShoppingCartRequest;
import org.kuali.student.ap.schedulebuilder.ShoppingCartStrategy;
import org.kuali.student.ap.schedulebuilder.infc.CourseOption;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.r2.core.acal.infc.Term;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class ShoppingCartStrategyMockTest implements ShoppingCartStrategy {
    /**
     * Determine if shopping cart is available for the student on the given
     * term.
     *
     * @param termId     The term ID.
     * @param campusCode The campus code value, from the "campusCode" dynamic attribute
     *                   on the course. When null, this method will check for access to
     *                   the shopping cart for the given term on all campuses.
     * @return True if the student has access to the shopping cart for the given
     *         term, false if not.
     */
    @Override
    public boolean isCartAvailable(String termId, String campusCode) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the course options for use with shopping cart for a specific term,
     * based on a plan item.
     *
     * @param term     The term to create a shopping cart request for.
     * @param planItem The plan item to create a shopping cart request for.
     * @return A shopping cart request for the specific plan item and term.
     */
    @Override
    public List<CourseOption> getCourseOptionsForPlanItem(Term term, PlanItem planItem) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create a shopping cart request for a specific term, based on a plan item.
     *
     * @param term     The term to create a shopping cart request for.
     * @param planItem The plan item to create a shopping cart request for.
     * @return A shopping cart request for the specific plan item and term.
     */
    @Override
    public List<ShoppingCartRequest> createRequests(Term term, List<CourseOption> courseOptions) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Create a shopping cart request for a specific term, based on a plan item.
     *
     * @param term     The term to create a shopping cart request for.
     * @param planItem The plan item to create a shopping cart request for.
     * @return A shopping cart request for the specific plan item and term.
     */
    @Override
    public List<ShoppingCartRequest> createRequests(String learningPlanId, Term term, PossibleScheduleOption schedule) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Process shopping cart requests.
     *
     * @param requests The shopping cart requests.
     * @return Shopping cart requests, with results populated.
     */
    @Override
    public List<ShoppingCartRequest> processRequests(List<ShoppingCartRequest> requests) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
