package org.kuali.student.ap.planner.util;

import org.apache.log4j.Logger;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.course.CreditsFormatter;
import org.kuali.student.ap.framework.course.CreditsFormatter.Range;
import org.kuali.student.common.util.KSCollectionUtils;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.lum.course.infc.Course;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Utility class for thread-local building of planner update events.
 * 
 * @author Mark Fyffe <mwfyffe@iu.edu>
 * @version 0.7.6
 */
public class PlanEventUtils {
    private static final Logger LOG = Logger.getLogger(PlanEventUtils.class);

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

	private static String getTotalCredits(String termId, AcademicPlanServiceConstants.ItemCategory  category) {
		BigDecimal plannedTotalMin = BigDecimal.ZERO;
		BigDecimal plannedTotalMax = BigDecimal.ZERO;

		LearningPlanInfo plan = KsapFrameworkServiceLocator.getPlanHelper()
				.getDefaultLearningPlan();

		List<PlanItemInfo> planItems;
		try {
			planItems = KsapFrameworkServiceLocator.getAcademicPlanService()
					.getPlanItemsInPlanByAtp(
							plan.getId(),
							termId,
                            category,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP lookup error", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup error", e);
		}

		for (PlanItemInfo planItem : planItems) {
			if (!PlanConstants.COURSE_TYPE.equals(planItem.getRefObjectType()))
				continue;

			BigDecimal credit = planItem.getCredit();
			if (credit != null) {
				plannedTotalMin = plannedTotalMin.add(credit);
				plannedTotalMax = plannedTotalMax.add(credit);
			} else {
				Course course = KsapFrameworkServiceLocator.getCourseHelper()
						.getCourseInfo(planItem.getRefObjectId());
				Range range = CreditsFormatter.getRange(course);
				plannedTotalMin = plannedTotalMin.add(range.getMin());
				plannedTotalMax = plannedTotalMax.add(range.getMax());
			}
		}

		return CreditsFormatter.formatCredits(new Range(plannedTotalMin,
				plannedTotalMax));
	}

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

        Course course = courseHelper.getCourseInfo(planItem.getRefObjectId());
        assert course != null : "Missing course for plan item "
                + planItem.getId() + ", ref ID " + planItem.getRefObjectId();

        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("uid", UUID.randomUUID().toString());
        addEvent.add("learningPlanId", planItem.getLearningPlanId());
        addEvent.add("planItemId", planItem.getId());
        addEvent.add("courseId", course.getId());
        addEvent.add("courseTitle", course.getCourseTitle());
        if (planItem.getCredit() != null) {
            addEvent.add("credits", CreditsFormatter.trimCredits(planItem
                    .getCredit().toString()));
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

        List<String> planPeriods = planItem.getPlanPeriods();
        try{
            String termId = KSCollectionUtils.getRequiredZeroElement(planPeriods);

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
                    KSCollectionUtils.getRequiredZeroElement(planItem.getPlanPeriods()).replace('.', '-'));
            }catch(OperationFailedException e){
                LOG.warn("No term id found during remove",e);
            }
        }

        JsonObjectBuilder events = getEventsBuilder();
        events.add(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_DELETED.name(),
                removeEvent);
        return events;
    }

    public static JsonObjectBuilder updatePlanItemCreditsEvent(String uniqueId,
                                                               PlanItem planItem) {
        JsonObjectBuilder updateCreditsEvent = Json.createObjectBuilder();
        updateCreditsEvent.add("uniqueId", uniqueId);

        if (planItem.getCredit() != null) {
            updateCreditsEvent.add("credit", CreditsFormatter
                    .trimCredits(planItem.getCredit().toString()));
        } else {
            Course course = KsapFrameworkServiceLocator.getCourseHelper()
                    .getCourseInfo(planItem.getRefObjectId());
            updateCreditsEvent.add("credit",
                    CreditsFormatter.formatCredits(course));
        }

        JsonObjectBuilder events = getEventsBuilder();
        events.add("PLAN_ITEM_UPDATED", updateCreditsEvent);
        return events;
    }

    public static JsonObjectBuilder updateTotalCreditsEvent(boolean newTerm,
                                                            String termId) {
        JsonObjectBuilder updateTotalCreditsEvent = Json.createObjectBuilder();
        updateTotalCreditsEvent.add("termId", termId.replace('.', '-'));
        updateTotalCreditsEvent.add(
                "totalCredits",
                getTotalCredits(termId,
                        AcademicPlanServiceConstants.ItemCategory.PLANNED));
        updateTotalCreditsEvent.add(
                "cartCredits",
                getTotalCredits(termId,
                        AcademicPlanServiceConstants.ItemCategory.CART));

        JsonObjectBuilder events = getEventsBuilder();
        events.add(
                newTerm ? PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS
                        .name()
                        : PlanConstants.JS_EVENT_NAME.UPDATE_OLD_TERM_TOTAL_CREDITS
                        .name(), updateTotalCreditsEvent);
        return events;
    }

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
	 * Creates an add plan item event on the current transaction.
	 * 
	 * @param planItem
	 *            The plan item to report as added.
	 * @return The transactional events builder, with the add plan item event
	 *         added.
	 */
	public static JsonObjectBuilder makeAddEvent(PlanItem planItem, JsonObjectBuilder eventList) {
		CourseHelper courseHelper = KsapFrameworkServiceLocator
				.getCourseHelper();
		TermHelper termHelper = KsapFrameworkServiceLocator.getTermHelper();

		assert PlanConstants.COURSE_TYPE.equals(planItem.getRefObjectType()) : planItem
				.getRefObjectType() + " " + planItem.getId();

		Course course = courseHelper.getCourseInfo(planItem.getRefObjectId());
		assert course != null : "Missing course for plan item "
				+ planItem.getId() + ", ref ID " + planItem.getRefObjectId();

		JsonObjectBuilder addEvent = Json.createObjectBuilder();
		addEvent.add("uid", UUID.randomUUID().toString());
		addEvent.add("learningPlanId", planItem.getLearningPlanId());
		addEvent.add("planItemId", planItem.getId());
		addEvent.add("courseId", course.getId());
		addEvent.add("courseTitle", course.getCourseTitle());
		if (planItem.getCredit() != null) {
			addEvent.add("credits", CreditsFormatter.trimCredits(planItem
					.getCredit().toString()));
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

		List<String> planPeriods = planItem.getPlanPeriods();
		try{
			String termId = KSCollectionUtils.getRequiredZeroElement(planPeriods);

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

	public static JsonObjectBuilder makeRemoveEvent(String uniqueId,
			PlanItem planItem, JsonObjectBuilder eventList) {
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
                        KSCollectionUtils.getRequiredZeroElement(planItem.getPlanPeriods()).replace('.', '-'));
            }catch(OperationFailedException e){
                LOG.warn("No term found during remove", e);
            }
		}

        eventList.add(PlanConstants.JS_EVENT_NAME.PLAN_ITEM_DELETED.name(),
				removeEvent);
		return eventList;
	}

	public static JsonObjectBuilder updatePlanItemEvent(String uniqueId,
                                                        PlanItem planItem, JsonObjectBuilder eventList) {
		JsonObjectBuilder updatePlanItemEvent = Json.createObjectBuilder();
        updatePlanItemEvent.add("uniqueId", uniqueId);

		if (planItem.getCredit() != null) {
            updatePlanItemEvent.add("credit", CreditsFormatter
					.trimCredits(planItem.getCredit().toString()));
		} else {
			Course course = KsapFrameworkServiceLocator.getCourseHelper()
					.getCourseInfo(planItem.getRefObjectId());
            updatePlanItemEvent.add("credit",
					CreditsFormatter.formatCredits(course));
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

	public static JsonObjectBuilder updateTotalCreditsEvent(boolean newTerm,
			String termId, JsonObjectBuilder eventList) {
		JsonObjectBuilder updateTotalCreditsEvent = Json.createObjectBuilder();
		updateTotalCreditsEvent.add("termId", termId.replace('.', '-'));
		updateTotalCreditsEvent.add(
				"totalCredits",
				getTotalCredits(termId,
						AcademicPlanServiceConstants.ItemCategory.PLANNED));
		updateTotalCreditsEvent.add(
				"cartCredits",
				getTotalCredits(termId,
						AcademicPlanServiceConstants.ItemCategory.CART));

        eventList.add(
				newTerm ? PlanConstants.JS_EVENT_NAME.UPDATE_NEW_TERM_TOTAL_CREDITS
						.name()
						: PlanConstants.JS_EVENT_NAME.UPDATE_OLD_TERM_TOTAL_CREDITS
								.name(), updateTotalCreditsEvent);
		return eventList;
	}

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

        Course course = courseHelper.getCourseInfo(planItem.getRefObjectId());
        assert course != null : "Missing course for plan item "
                + planItem.getId() + ", ref ID " + planItem.getRefObjectId();

        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("uid", UUID.randomUUID().toString());
        addEvent.add("learningPlanId", planItem.getLearningPlanId());
        addEvent.add("planItemId", planItem.getId());
        addEvent.add("courseId", course.getId());
        addEvent.add("courseCd",course.getCode());
        addEvent.add("courseTitle", course.getCourseTitle());
        if (planItem.getCredit() != null) {
            addEvent.add("credits", CreditsFormatter.trimCredits(planItem
                    .getCredit().toString()));
        } else {
            addEvent.add("credits", CreditsFormatter.formatCredits(course));
        }

        eventList.add(PlanConstants.JS_EVENT_NAME.BOOKMARK_ADDED.name(), addEvent);
        return eventList;
    }

    /**
     * Creates an add plan item event on the current transaction.
     *
     * @param planItem
     *            The plan item to report as added.
     * @return The transactional events builder, with the add plan item event
     *         added.
     */
    public static JsonObjectBuilder makeUpdateBookmarkTotalEvent(PlanItem planItem, JsonObjectBuilder eventList) {
        List<PlanItemInfo> bookmarks;
        try{
            bookmarks = KsapFrameworkServiceLocator.getAcademicPlanService().getPlanItemsInPlanByCategory(
                    planItem.getLearningPlanId(), AcademicPlanServiceConstants.ItemCategory.WISHLIST,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        }catch (Exception e){
            return eventList;
        }
        JsonObjectBuilder addEvent = Json.createObjectBuilder();
        addEvent.add("bookmarkTotal",bookmarks.size());

        eventList.add(PlanConstants.JS_EVENT_NAME.UPDATE_BOOKMARK_TOTAL.name(), addEvent);
        return eventList;
    }

}
