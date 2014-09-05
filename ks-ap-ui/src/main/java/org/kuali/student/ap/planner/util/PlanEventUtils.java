package org.kuali.student.ap.planner.util;

import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.util.KsapHelperUtil;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.lum.course.infc.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Utility class for thread-local building of planner update events.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 0.7.6
 */
public class PlanEventUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PlanEventUtils.class);

	private static class EventsKey {
		@Override
		public int hashCode() {
			return EventsKey.class.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return obj instanceof EventsKey;
		}
	}

	private static final EventsKey EVENTS_KEY = new EventsKey();

	/**
	 * Get a transactional events object.
	 *
	 * @return A transaction events object builder.
	 */
	public static JsonObjectBuilder getEventsBuilder() {
		if (TransactionSynchronizationManager.isSynchronizationActive()) {
			JsonObjectBuilder rv = (JsonObjectBuilder) TransactionSynchronizationManager
					.getResource(EVENTS_KEY);

			if (rv == null) {
				rv = Json.createObjectBuilder();
				TransactionSynchronizationManager
						.registerSynchronization(new TransactionSynchronizationAdapter() {
							@Override
							public void afterCompletion(int status) {
								TransactionSynchronizationManager
										.unbindResourceIfPossible(EVENTS_KEY);
							}
						});
				TransactionSynchronizationManager.bindResource(EVENTS_KEY, rv);
			}

			return rv;

		} else
			return Json.createObjectBuilder();
	}

    /**
     * Creates an add plan item event on the current transaction.
     *
     * @param planItem
     *            The plan item to report as added.
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public static JsonObjectBuilder makeAddEvent(PlanItem planItem) {
        CourseHelper courseHelper = KsapFrameworkServiceLocator
                .getCourseHelper();
        TermHelper termHelper = KsapFrameworkServiceLocator.getTermHelper();

        assert PlanConstants.COURSE_TYPE.equals(planItem.getRefObjectType()) : planItem
                .getRefObjectType() + " " + planItem.getId();

        Course course = courseHelper.getCurrentVersionOfCourse(planItem.getRefObjectId());
        assert course != null : "Missing course for plan item "
                + planItem.getId() + ", ref ID " + planItem.getRefObjectId();

        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("uid", UUID.randomUUID().toString());
        addEvent.add("learningPlanId", planItem.getLearningPlanId());
        addEvent.add("planItemId", planItem.getId());
        addEvent.add("courseId", course.getId());
        addEvent.add("courseTitle", course.getCourseTitle());
        if (planItem.getCredits() != null) {
            addEvent.add("credits", CreditsFormatter.trimCredits(planItem
                    .getCredits().toString()));
        } else {
            addEvent.add("credits", CreditsFormatter.formatCredits(course));
        }

        StringBuilder code = new StringBuilder(course.getCode());
        String campusCode = null, activityCode = null;
        for (Attribute attr : course.getAttributes()) {
            String key = attr.getKey();
            if ("campusCode".equals(key))
                campusCode = attr.getValue();
        }
        for (Attribute attr : planItem.getAttributes()) {
            String key = attr.getKey();
            if ("campusCode".equals(key))
                campusCode = attr.getValue();
            if ("activityCode".equals(key))
                activityCode = attr.getValue();
        }
        if (campusCode != null)
            code.insert(0, " ").insert(0, campusCode);
        if (activityCode != null)
            code.append(" ").append(activityCode);
        addEvent.add("code", code.toString());

        String category = planItem.getCategory().toString().toLowerCase();
        String menusuffix = "";

        List<String> planTermIds = planItem.getPlanTermIds();
        try{
            String termId = KSCollectionUtils.getRequiredZeroElement(planTermIds);

            Term term = termHelper.getTerm(termId);
            assert term != null : "Invalid term " + termId + " in plan item "
                    + planItem.getId();

            // NOTE: termId is used as a post parameter by the add event.
            // For other events, it is used as a selector so needs '.' replaced
            // by '-' The replacement is not desired here.
            addEvent.add("termId", termId);

            if ("planned".equals(category)
                    && campusCode != null
                    && KsapFrameworkServiceLocator.getShoppingCartStrategy()
                    .isCartAvailable(termId, campusCode))
                menusuffix = "_cartavailable";
        }catch (OperationFailedException e){
            LOG.warn("No term found during add event", e);
        }

        addEvent.add("category", category);
        addEvent.add("menusuffix", menusuffix);

        JsonObjectBuilder events = getEventsBuilder();
        events.add(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_ADDED.name(), addEvent);
        return events;
    }

    /**
     * Creates a remove event on the current transaction
     *
     * @param uniqueId - Id of the component being removed
     * @param planItem - PlanItem being removed
     * @return The transactional events builder, with the event added.
     */
    public static JsonObjectBuilder makeRemoveEvent(String uniqueId,
                                                    PlanItem planItem) {
        JsonObjectBuilder removeEvent = Json.createObjectBuilder();
        removeEvent.add("uid", uniqueId);
        removeEvent.add("planItemId", planItem.getId());
        removeEvent.add("category", planItem.getCategory().toString().toLowerCase());

        // Only planned or backup items get an atpId attribute.
        if (planItem.getCategory().equals(
                AcademicPlanServiceConstants.ItemCategory.PLANNED)
                || planItem.getCategory().equals(
                AcademicPlanServiceConstants.ItemCategory.BACKUP)
                || planItem.getCategory().equals(
                AcademicPlanServiceConstants.ItemCategory.CART)) {
            try{
                removeEvent.add("termId",
                    KSCollectionUtils.getRequiredZeroElement(planItem.getPlanTermIds()).replace('.', '-'));
            }catch(OperationFailedException e){
                LOG.warn("No term id found during remove",e);
            }
        }

        JsonObjectBuilder events = getEventsBuilder();
        events.add(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_DELETED.name(),
                removeEvent);
        return events;
    }

    /**
     * Creates an update event for a plan item's credits on the current transaction
     *
     * @param uniqueId - Id of the component being updated
     * @param planItem - PlanItem being updated
     * @return The transactional events builder, with the event added.
     */
    public static JsonObjectBuilder updatePlanItemCreditsEvent(String uniqueId,
                                                               PlanItem planItem) {
        JsonObjectBuilder updateCreditsEvent = Json.createObjectBuilder();
        updateCreditsEvent.add("uniqueId", uniqueId);

        if (planItem.getCredits() != null) {
            updateCreditsEvent.add("credit", CreditsFormatter
                    .trimCredits(planItem.getCredits().toString()));
        } else {
            Course course = KsapFrameworkServiceLocator.getCourseHelper()
                    .getCurrentVersionOfCourse(planItem.getRefObjectId());
            updateCreditsEvent.add("credit",
                    CreditsFormatter.formatCredits(course));
        }

        JsonObjectBuilder events = getEventsBuilder();
        events.add("PLAN_ITEM_UPDATED", updateCreditsEvent);
        return events;
    }

    /**
     * Creates an update event for a term's credits on the current transaction
     *
     * @param newTerm - Flag on whether its new or old credits being updated
     * @param termId  - Id of the term being updated
     * @return The transactional events builder, with the event added.
     */
    public static JsonObjectBuilder updateTotalCreditsEvent(boolean newTerm,
                                                            String termId) {
        JsonObjectBuilder updateTotalCreditsEvent = Json.createObjectBuilder();
        updateTotalCreditsEvent.add("termId", termId.replace('.', '-'));
        updateTotalCreditsEvent.add(
                "totalCredits",
                KsapHelperUtil.getTotalCredits(termId,
                AcademicPlanServiceConstants.ItemCategory.PLANNED));
        updateTotalCreditsEvent.add(
                "cartCredits",
                KsapHelperUtil.getTotalCredits(termId,
                        AcademicPlanServiceConstants.ItemCategory.CART));

        JsonObjectBuilder events = getEventsBuilder();
        events.add(
                newTerm ? PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS
                        .name()
                        : PlanConstants.JS_EVENT_NAME.UPDATE_OLD_TERM_TOTAL_CREDITS
                        .name(), updateTotalCreditsEvent);
        return events;
    }

    /**
     * Creates an update event for a terms note on the current transaction
     * @param uniqueId - Id of the component being updated
     * @param termNote - The new term note text
     * @return The transactional events builder, with the event added.
     */
    public static JsonObjectBuilder updateTermNoteEvent(String uniqueId,
                                                        String termNote) {
        JsonObjectBuilder updateTotalTermNoteEvent = Json.createObjectBuilder();
        updateTotalTermNoteEvent.add("uniqueId", uniqueId);
        updateTotalTermNoteEvent.add("termNote", termNote == null ? ""
                : termNote);
        JsonObjectBuilder events = getEventsBuilder();
        events.add(PlanConstants.JS_EVENT_NAME.TERM_NOTE_UPDATED.name(),
                updateTotalTermNoteEvent);
        return events;
    }

    /**
     * Writes the json of the current transactional events builder
     * @param success - If actions was successful
     * @param message - Message to display
     * @param response - Pages response object
     * @throws IOException - Thrown if write fails
     * @throws ServletException - Thrown if write fails
     */
    public static void sendJsonEvents(boolean success, String message,
                                      HttpServletResponse response) throws IOException, ServletException {
        JsonObjectBuilder json = PlanEventUtils.getEventsBuilder();
        json.add("success", success);
        if (message != null)
            json.add("message", message);

        response.setContentType("application/json; charset=UTF-8");
        response.setHeader("Cache-Control", "No-cache");
        response.setHeader("Cache-Control", "No-store");
        response.setHeader("Cache-Control", "max-age=0");
        JsonWriter jwriter = Json.createWriter(response.getWriter());
        jwriter.writeObject(json.build());
        jwriter.close();
    }

	/**
	 * Creates an add plan item event on a passed in event builder
	 * 
	 * @param planItem The plan item to report as added.
     * @param eventList - Event builder being used to compile event list
	 * @return The events builder, with the add plan item event added.
	 */
	public static JsonObjectBuilder makeAddEvent(PlanItem planItem, JsonObjectBuilder eventList) {
		CourseHelper courseHelper = KsapFrameworkServiceLocator
				.getCourseHelper();
		TermHelper termHelper = KsapFrameworkServiceLocator.getTermHelper();

		assert PlanConstants.COURSE_TYPE.equals(planItem.getRefObjectType()) : planItem
				.getRefObjectType() + " " + planItem.getId();

		Course course = courseHelper.getCurrentVersionOfCourseByVersionIndependentId(planItem.getRefObjectId());
		assert course != null : "Missing course for plan item "
				+ planItem.getId() + ", ref ID " + planItem.getRefObjectId();

		JsonObjectBuilder addEvent = Json.createObjectBuilder();
		addEvent.add("uid", UUID.randomUUID().toString());
		addEvent.add("learningPlanId", planItem.getLearningPlanId());
		addEvent.add("planItemId", planItem.getId());
		addEvent.add("courseId", course.getId());
		addEvent.add("courseTitle", course.getCourseTitle());
		if (planItem.getCredits() != null) {
			addEvent.add("credits", CreditsFormatter.trimCredits(planItem
					.getCredits().toString()));
		} else {
			addEvent.add("credits", CreditsFormatter.formatCredits(course));
		}
        if (planItem.getDescr() != null && planItem.getDescr().getPlain()!=null && !planItem.getDescr().getPlain().isEmpty()) {
                addEvent.add("courseNote", planItem.getDescr().getPlain());
                addEvent.add("courseNoteRender", "true");
        } else {
            addEvent.add("courseNote", "");
            addEvent.add("courseNoteRender", "false");
        }

		StringBuilder code = new StringBuilder(course.getCode());
		String campusCode = null, activityCode = null;
		for (Attribute attr : course.getAttributes()) {
			String key = attr.getKey();
			if ("campusCode".equals(key))
				campusCode = attr.getValue();
		}
		for (Attribute attr : planItem.getAttributes()) {
			String key = attr.getKey();
			if ("campusCode".equals(key))
				campusCode = attr.getValue();
			if ("activityCode".equals(key))
				activityCode = attr.getValue();
		}
		if (campusCode != null)
			code.insert(0, " ").insert(0, campusCode);
		if (activityCode != null)
			code.append(" ").append(activityCode);
		addEvent.add("code", code.toString());

		String category = planItem.getCategory().toString().toLowerCase();
		String menusuffix = "";

		List<String> planTermIds = planItem.getPlanTermIds();
		try{
			String termId = KSCollectionUtils.getRequiredZeroElement(planTermIds);

			Term term = termHelper.getTerm(termId);
			assert term != null : "Invalid term " + termId + " in plan item "
					+ planItem.getId();

			// NOTE: termId is used as a post parameter by the add event.
			// For other events, it is used as a selector so needs '.' replaced
			// by '-' The replacement is not desired here.
			addEvent.add("termId", termId);

			if ("planned".equals(category)
					&& campusCode != null
					&& KsapFrameworkServiceLocator.getShoppingCartStrategy()
							.isCartAvailable(termId, campusCode))
				menusuffix = "_cartavailable";
		}catch (OperationFailedException e){
            LOG.warn("No term found on add event", e);
        }

		addEvent.add("category", category);
		addEvent.add("menusuffix", menusuffix);

        eventList.add(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_ADDED.name(), addEvent);
		return eventList;
	}

    /**
     * Creates a remove event on a passed in event builder
     *
     * @param uniqueId - Id of the component being removed
     * @param planItem - PlanItem being removed
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
	public static JsonObjectBuilder makeRemoveEvent(String uniqueId,
			PlanItem planItem, JsonObjectBuilder eventList) {
		JsonObjectBuilder removeEvent = Json.createObjectBuilder();
		removeEvent.add("uid", uniqueId);
		removeEvent.add("planItemId", planItem.getId());
		removeEvent.add("category", planItem.getCategory().toString().toLowerCase());

        Course course = KsapFrameworkServiceLocator
                .getCourseHelper().getCurrentVersionOfCourseByVersionIndependentId(planItem.getRefObjectId());
        assert course != null : "Missing course for plan item "
                + planItem.getId() + ", ref ID " + planItem.getRefObjectId();

        removeEvent.add("courseId", course.getId());

		// Only planned or backup items get an atpId attribute.
		if (planItem.getCategory().equals(
				AcademicPlanServiceConstants.ItemCategory.PLANNED)
				|| planItem.getCategory().equals(
						AcademicPlanServiceConstants.ItemCategory.BACKUP)
				|| planItem.getCategory().equals(
						AcademicPlanServiceConstants.ItemCategory.CART)) {
            try{
			    removeEvent.add("termId",
                        KSCollectionUtils.getRequiredZeroElement(planItem.getPlanTermIds()).replace('.', '-'));
            }catch(OperationFailedException e){
                LOG.warn("No term found during remove", e);
            }
		}

        eventList.add(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_DELETED.name(),
				removeEvent);
		return eventList;
	}

    /**
     * Creates an update event for a plan item on a passed in event builder
     *
     * @param uniqueId - Id of the component being updated
     * @param planItem - PlanItem being updated
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
	public static JsonObjectBuilder updatePlanItemEvent(String uniqueId,
                                                        PlanItem planItem, JsonObjectBuilder eventList) {
		JsonObjectBuilder updatePlanItemEvent = Json.createObjectBuilder();
        updatePlanItemEvent.add("uniqueId", uniqueId);

		if (planItem.getCredits() != null) {
            updatePlanItemEvent.add("credit", CreditsFormatter
					.trimCredits(planItem.getCredits().toString()));
		} else {
			Course course = KsapFrameworkServiceLocator.getCourseHelper()
					.getCurrentVersionOfCourseByVersionIndependentId(planItem.getRefObjectId());
            updatePlanItemEvent.add("credit",CreditsFormatter.formatCredits(course));
		}
        if (planItem.getDescr() != null && planItem.getDescr().getPlain()!=null && !planItem.getDescr().getPlain().isEmpty()) {
            updatePlanItemEvent.add("courseNote", planItem.getDescr().getPlain());
            updatePlanItemEvent.add("courseNoteRender", "true");
        } else {
            updatePlanItemEvent.add("courseNote", "");
            updatePlanItemEvent.add("courseNoteRender", "false");
        }

        eventList.add("PLAN_ITEM_UPDATED", updatePlanItemEvent);
		return eventList;
	}


    /**
     * Creates an update event for a term's credits on a passed in event builder
     *
     * @param newTerm - Flag on whether its new or old credits being updated
     * @param termId  - Id of the term being updated
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
	public static JsonObjectBuilder updateTotalCreditsEvent(boolean newTerm,
			String termId, JsonObjectBuilder eventList) {
		JsonObjectBuilder updateTotalCreditsEvent = Json.createObjectBuilder();
		updateTotalCreditsEvent.add("termId", termId.replace('.', '-'));
		updateTotalCreditsEvent.add(
				"totalCredits",
                KsapHelperUtil.getTotalCredits(termId,
						AcademicPlanServiceConstants.ItemCategory.PLANNED));
		updateTotalCreditsEvent.add(
				"cartCredits",
                KsapHelperUtil.getTotalCredits(termId,
						AcademicPlanServiceConstants.ItemCategory.CART));

        eventList.add(
				newTerm ? PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS
						.name()
						: PlanConstants.JS_EVENT_NAME.UPDATE_OLD_TERM_TOTAL_CREDITS
								.name(), updateTotalCreditsEvent);
		return eventList;
	}

    /**
     * Creates an update event for a terms note on a passed in event builder
     * @param uniqueId - Id of the component being updated
     * @param termNote - The new term note text
     * @param eventList - Event builder being used to compile event list
     * @return The events builder, with the add plan item event added.
     */
	public static JsonObjectBuilder updateTermNoteEvent(String uniqueId,
			String termNote, JsonObjectBuilder eventList) {
		JsonObjectBuilder updateTotalTermNoteEvent = Json.createObjectBuilder();
		updateTotalTermNoteEvent.add("uniqueId", uniqueId);
		updateTotalTermNoteEvent.add("termNote", termNote == null ? ""
				: termNote);
        eventList.add(PlanConstants.JS_EVENT_NAME.TERM_NOTE_UPDATED.name(),
				updateTotalTermNoteEvent);
		return eventList;
	}

    /**
     * Writes the json of a passed in event builder
     * @param success - If actions was successful
     * @param message - Message to display
     * @param response - Pages response object
     * @param eventList - Event builder being used to compile event list
     * @throws IOException - Thrown if write fails
     * @throws ServletException - Thrown if write fails
     */
	public static void sendJsonEvents(boolean success, String message,
			HttpServletResponse response, JsonObjectBuilder eventList) throws IOException, ServletException {

        eventList.add("success", success);
		if (message != null)
            eventList.add("message", message);

		response.setContentType("application/json; charset=UTF-8");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		JsonWriter jwriter = Json.createWriter(response.getWriter());
		jwriter.writeObject(eventList.build());
		jwriter.close();
	}

	private PlanEventUtils() {
	}

    /**
     * Creates an add plan item event on the current transaction.
     *
     * @param planItem
     *            The plan item to report as added.
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public static JsonObjectBuilder makeAddBookmarkEvent(PlanItem planItem, JsonObjectBuilder eventList) {
        CourseHelper courseHelper = KsapFrameworkServiceLocator
                .getCourseHelper();

        assert PlanConstants.COURSE_TYPE.equals(planItem.getRefObjectType()) : planItem
                .getRefObjectType() + " " + planItem.getId();

        Course course = courseHelper.getCurrentVersionOfCourseByVersionIndependentId(planItem.getRefObjectId());
        assert course != null : "Missing course for plan item "
                + planItem.getId() + ", ref ID " + planItem.getRefObjectId();

        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("uid", UUID.randomUUID().toString());
        addEvent.add("learningPlanId", planItem.getLearningPlanId());
        addEvent.add("planItemId", planItem.getId());
        addEvent.add("courseId", course.getId());
        addEvent.add("courseCd",course.getCode());
        addEvent.add("courseTitle", course.getCourseTitle());
        if (planItem.getCredits() != null) {
            addEvent.add("credits", CreditsFormatter.trimCredits(planItem
                    .getCredits().toString()));
        } else {
            addEvent.add("credits", CreditsFormatter.formatCredits(course));
        }

        eventList.add(PlanConstants.JS_EVENT_NAME.BOOKMARK_ADDED.name(), addEvent);
        return eventList;
    }

    /**
     * Creates an update bookmark total count event on the current transaction.
     *
     * @param planItem
     *            The plan item to use to get the plan id, containing the bookmarks
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public static JsonObjectBuilder makeUpdateBookmarkTotalEvent(PlanItem planItem, JsonObjectBuilder eventList) {
        return makeUpdateBookmarkTotalEvent(planItem.getLearningPlanId(), eventList);
    }

    /**
     * Creates an update bookmark total count event on the current transaction.
     *
     * @param learningPlanId
     *            The plan id to use to get the bookmark count from
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public static JsonObjectBuilder makeUpdateBookmarkTotalEvent(String learningPlanId, JsonObjectBuilder eventList) {
        List<PlanItemInfo> bookmarks;
        try{
            bookmarks = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItemsInPlanByCategory(
                    learningPlanId, AcademicPlanServiceConstants.ItemCategory.WISHLIST,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        }catch (Exception e){
            return eventList;
        }
        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("bookmarkTotal",bookmarks.size());

        eventList.add(PlanConstants.JS_EVENT_NAME.UPDATE_BOOKMARK_TOTAL.name(), addEvent);
        return eventList;
    }

    public static JsonObjectBuilder makeUpdatePlanItemStatusMessage(List<PlanItem> planItems, JsonObjectBuilder eventList) {

        String plannedStatusMessage = KsapFrameworkServiceLocator.getPlanHelper().createPlanningStatusMessages(planItems);
        String bookmarkStatusMessage = KsapFrameworkServiceLocator.getPlanHelper().createBookmarkStatusMessages(planItems);

        JsonObjectBuilder event = Json.createObjectBuilder();
        event.add("plannedStatusMessage",plannedStatusMessage);
        event.add("bookmarkedStatusMessage",bookmarkStatusMessage);

        eventList.add(PlanConstants.JS_EVENT_NAME.UPDATE_PLAN_ITEM_STATUS.name(), event);
        return eventList;
    }

}
