package org.kuali.student.ap.schedulebuilder.support;

import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.planner.support.AbstractPlanItemForm;
import org.kuali.student.ap.schedulebuilder.ScheduleBuildStrategy;
import org.kuali.student.ap.schedulebuilder.ShoppingCartForm;
import org.kuali.student.ap.schedulebuilder.ShoppingCartRequest;
import org.kuali.student.ap.schedulebuilder.dto.ActivityOptionInfo;
import org.kuali.student.ap.schedulebuilder.dto.CourseOptionInfo;
import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.CourseOption;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.r2.lum.course.infc.Course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class DefaultShoppingCartForm extends AbstractPlanItemForm implements ShoppingCartForm {

	private static final long serialVersionUID = 674405491584786921L;

	private String activityOfferingId;
	private String possibleScheduleId;

	private List<ShoppingCartRequest> shoppingCartRequests;
	private List<CourseOption> courseOptions;

	private transient List<CourseOption> removeFromCart;
	private transient List<CourseOption> addToCart;
	private transient List<CourseOption> keepInCart;

	private void createActivityLists() {
		String psoid = possibleScheduleId;
		if (psoid == null)
			return;

		@SuppressWarnings("unchecked")
		Map<String, PossibleScheduleOption> psomap = (Map<String, PossibleScheduleOption>) GlobalVariables
				.getUserSession().getObjectMap().get(POSSIBLE_OPTIONS_KEY);
		if (psomap == null)
			return;

		PossibleScheduleOption pso = psomap.get(psoid);
		if (pso == null)
			return;

		CourseHelper courseHelper = KsapFrameworkServiceLocator.getCourseHelper();
		Map<String, ActivityOption> byRegCode = new HashMap<String, ActivityOption>();
		for (ActivityOption ao : pso.getActivityOptions())
			byRegCode.put(ao.getRegistrationCode(), ao);

		Map<String, Course> courseMap = new HashMap<String, Course>();
		Map<String, List<ActivityOption>> removeMap = new LinkedHashMap<String, List<ActivityOption>>();
		Map<String, List<ActivityOption>> addMap = new LinkedHashMap<String, List<ActivityOption>>();

		if (shoppingCartRequests != null)
			for (ShoppingCartRequest req : shoppingCartRequests) {
				Course course = req.getCourse();
				String courseId = course.getId();
				courseMap.put(courseId, course);

				String preg = req.getPrimaryRegistrationCode();
				List<String> sreg = req.getSecondaryRegistrationCodes();

				if (req.isAddToCart()) {
					if (preg != null) {
						ActivityOption pao = byRegCode.remove(preg);
						assert pao != null : preg;
						List<ActivityOption> addList = addMap.get(courseId);
						if (addList == null)
							addMap.put(courseId, addList = new ArrayList<ActivityOption>());
						// used to tie processing results in to the front end
						((ActivityOptionInfo) pao).setUniqueId(req.getUniqueId());
						addList.add(pao);
					}
					if (sreg != null)
						for (String reg : sreg) {
							ActivityOption sao = byRegCode.remove(reg);
							assert sao != null : reg;
							List<ActivityOption> addList = addMap.get(courseId);
							if (addList == null)
								addMap.put(courseId, addList = new ArrayList<ActivityOption>());
							addList.add(sao);
						}
				} else {
					ScheduleBuildStrategy sb = KsapFrameworkServiceLocator
							.getScheduleBuildStrategy();
					if (preg != null) {
						assert !byRegCode.containsKey(preg) : preg;
						List<ActivityOption> removeList = removeMap.get(courseId);
						if (removeList == null)
							removeMap.put(courseId, removeList = new ArrayList<ActivityOption>());
						ActivityOptionInfo ao = (ActivityOptionInfo)
								sb.getActivityOption(pso.getTermId(), req.getCourse().getId(), preg);
						// used to tie processing results in to the front end
						ao.setUniqueId(req.getUniqueId());
						removeList.add(ao);
					}
					if (sreg != null)
						for (String reg : sreg) {
							assert !byRegCode.containsKey(preg) : reg;
							List<ActivityOption> removeList = removeMap.get(courseId);
							if (removeList == null)
								removeMap.put(courseId,
										removeList = new ArrayList<ActivityOption>());
							removeList.add(sb.getActivityOption(pso.getTermId(), req.getCourse()
									.getId(), reg));
						}
				}
			}

		removeFromCart = new ArrayList<CourseOption>(removeMap.size());
		for (Entry<String, List<ActivityOption>> removeEntry : removeMap.entrySet()) {
			Course course = courseMap.get(removeEntry.getKey());
			CourseOptionInfo coi = new CourseOptionInfo();
			coi.setUniqueId(UUID.randomUUID().toString());
			coi.setCourseId(course.getId());
			coi.setCourseCode(course.getCode());
			coi.setCourseTitle(course.getCourseTitle());
			coi.setActivityOptions(removeEntry.getValue());
			removeFromCart.add(coi);
		}

		addToCart = new ArrayList<CourseOption>(addMap.size());
		for (Entry<String, List<ActivityOption>> addEntry : addMap.entrySet()) {
			Course course = courseMap.get(addEntry.getKey());
			CourseOptionInfo coi = new CourseOptionInfo();
			coi.setUniqueId(UUID.randomUUID().toString());
			coi.setCourseId(course.getId());
			coi.setCourseCode(course.getCode());
			coi.setCourseTitle(course.getCourseTitle());
			coi.setActivityOptions(addEntry.getValue());
			addToCart.add(coi);
		}

		Map<String, List<ActivityOption>> keepMap = new LinkedHashMap<String, List<ActivityOption>>();
		for (ActivityOption keep : byRegCode.values()) {
			String courseId = keep.getCourseId();
			if (!courseMap.containsKey(courseId))
				courseMap.put(courseId, courseHelper.getCourseInfo(courseId));

			List<ActivityOption> keepList = keepMap.get(courseId);
			if (keepList == null)
				keepMap.put(courseId, keepList = new ArrayList<ActivityOption>());
			keepList.add(keep);
		}

		keepInCart = new ArrayList<CourseOption>(keepMap.size());
		for (Entry<String, List<ActivityOption>> keepEntry : keepMap.entrySet()) {
			Course course = courseMap.get(keepEntry.getKey());
			CourseOptionInfo coi = new CourseOptionInfo();
			coi.setUniqueId(UUID.randomUUID().toString());
			coi.setCourseId(course.getId());
			coi.setCourseCode(course.getCode());
			coi.setCourseTitle(course.getCourseTitle());
			coi.setActivityOptions(keepEntry.getValue());
			keepInCart.add(coi);
		}

	}

	@Override
	public String getShoppingCartUrl() {
		return ConfigContext.getCurrentContextConfig().getProperty("ks.ap.sb.cart.url");
	}

	@Override
	public String getActivityOfferingId() {
		return activityOfferingId;
	}

	public void setActivityOfferingId(String activityOfferingId) {
		this.activityOfferingId = activityOfferingId;
	}

	@Override
	public String getPossibleScheduleId() {
		return possibleScheduleId;
	}

	public void setPossibleScheduleId(String possibleScheduleId) {
		this.possibleScheduleId = possibleScheduleId;
		removeFromCart = null;
		addToCart = null;
		keepInCart = null;
	}

	public List<ShoppingCartRequest> getShoppingCartRequests() {
		return shoppingCartRequests;
	}

	public void setShoppingCartRequests(List<ShoppingCartRequest> shoppingCartRequests) {
		this.shoppingCartRequests = shoppingCartRequests;
		removeFromCart = null;
		addToCart = null;
		keepInCart = null;
	}

	@Override
	public List<CourseOption> getCourseOptions() {
		return courseOptions;
	}

	public void setCourseOptions(List<CourseOption> courseOptions) {
		this.courseOptions = courseOptions;
		removeFromCart = null;
		addToCart = null;
		keepInCart = null;
	}

	public List<CourseOption> getRemoveFromCart() {
		if (removeFromCart == null)
			createActivityLists();
		return removeFromCart;
	}

	public List<CourseOption> getAddToCart() {
		if (addToCart == null)
			createActivityLists();
		return addToCart;
	}

	public List<CourseOption> getKeepInCart() {
		if (keepInCart == null)
			createActivityLists();
		return keepInCart;
	}

}
