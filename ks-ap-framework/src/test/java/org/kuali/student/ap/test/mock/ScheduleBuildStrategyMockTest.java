package org.kuali.student.ap.test.mock;

import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.schedulebuilder.ScheduleBuildForm;
import org.kuali.student.ap.schedulebuilder.ScheduleBuildStrategy;
import org.kuali.student.ap.schedulebuilder.ShoppingCartForm;
import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.CourseOption;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.ap.schedulebuilder.infc.ReservedTime;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 10:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class ScheduleBuildStrategyMockTest implements ScheduleBuildStrategy {
    /**
     * Get the initial schedule build form.
     *
     * @return The initial schedule build form.
     */
    @Override
    public ScheduleBuildForm getInitialForm() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Load course options for a term from a list of course IDs.
     *
     * @param courseIDs Course IDs from the existing shopping cart.
     * @param termId    The term to get options for.
     * @return The course options to use as inputs for generating schedules.
     */
    @Override
    public List<CourseOption> getCourseOptions(List<String> courseIds, String termId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Load the course options to use as inputs for generating schedules.
     *
     * @param learningPlanId The learning plan ID.
     * @param termId         The term to get options for.
     * @return The course options to use as inputs for generating schedules.
     */
    @Override
    public List<CourseOption> getCourseOptions(String learningPlanId, String termId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the learning plan for schedule build to use as inputs.
     *
     * @param requestedLearningPlanId The requested learning plan ID. May be null to get the first
     *                                learning plan of type
     *                                {@link org.kuali.student.ap.framework.context.PlanConstants#LEARNING_PLAN_TYPE_PLAN} for the student.
     * @return The learning plan for schedule build to use as inputs.
     * @throws org.kuali.student.r2.common.exceptions.PermissionDeniedException
     *          If the current user does not have access to the requested
     *          learning plan.
     */
    @Override
    public LearningPlan getLearningPlan(String requestedLearningPlanId) throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get reserved times related to the current learning plan.
     *
     * @param requestedLearningPlanId See {@link #getLearningPlan(String)}.
     * @return The reserved times related to the current learning plan.
     */
    @Override
    public List<ReservedTime> getReservedTimes(String requestedLearningPlanId) throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a new reserved time on the current learning plan.
     *
     * @param reservedTime The reserved time to add.
     */
    @Override
    public void createReservedTime(String requestedLearningPlanId, ReservedTime reservedTime) throws PermissionDeniedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a new reserved time on the current learning plan.
     *
     * @param reservedTime The reserved time to add.
     */
    @Override
    public void updateReservedTime(String requestedLearningPlanId, ReservedTime reservedTime) throws PermissionDeniedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a new reserved time on the current learning plan.
     *
     * @param reservedTimeId The ID of the reserved time to delete.
     */
    @Override
    public void deleteReservedTime(String requestedLearningPlanId, String reservedTimeId) throws PermissionDeniedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get saved schedules related to the current learning plan.
     *
     * @param requestedLearningPlanId See {@link #getLearningPlan(String)}.
     * @return The saved schedules related to the current learning plan.
     */
    @Override
    public List<PossibleScheduleOption> getSchedules(String requestedLearningPlanId) throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a new reserved time on the current learning plan.
     *
     * @param reservedTime The reserved time to add.
     */
    @Override
    public PossibleScheduleOption createSchedule(String requestedLearningPlanId, PossibleScheduleOption schedule) throws PermissionDeniedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a new saved schedule on the current learning plan.
     *
     * @param schedule The schedule to add.
     */
    @Override
    public void updateSchedule(String requestedLearningPlanId, PossibleScheduleOption schedule) throws PermissionDeniedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Add a new saved schedule on the current learning plan.
     *
     * @param scheduleId The ID of the schedule to delete.
     */
    @Override
    public void deleteSchedule(String requestedLearningPlanId, String scheduleId) throws PermissionDeniedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get the initial shopping cart form.
     *
     * @return The initial shopping cart form.
     */
    @Override
    public ShoppingCartForm getInitialCartForm() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Get a populated activity option for a given term, course, and
     * registration code.
     *
     * @param termId   The term ID.
     * @param courseId The course ID.
     * @param regCode  The registration code.
     * @return A populated activity option for a given term, course, and
     *         registration code. Returns null if data is missing.
     */
    @Override
    public ActivityOption getActivityOption(String termId, String courseId, String regCode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
