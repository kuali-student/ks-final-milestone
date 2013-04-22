package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Produce a list of planned course items.
 */
public class PlannedCoursesLookupableHelperImpl extends
		PlanItemLookupableHelperBase {

	private static final long serialVersionUID = 1502719861875054079L;

	private static final Logger LOG = Logger
			.getLogger(PlannedCoursesLookupableHelperImpl.class);

	private transient AcademicRecordService academicRecordService;

	public AcademicRecordService getAcademicRecordService() {
		if (this.academicRecordService == null) {
			this.academicRecordService = KsapFrameworkServiceLocator
					.getAcademicRecordService();
		}

		return this.academicRecordService;
	}

	public void setAcademicRecordService(
			AcademicRecordService academicRecordService) {
		this.academicRecordService = academicRecordService;
	}

	/**
	 * Skip the validation so that we use the criteriaFields param to pass in
	 * args to the getSearchResults method.
	 * 
	 * @param form
	 * @param searchCriteria
	 * @return
	 */
	@Override
	public boolean validateSearchParameters(LookupForm form,
			Map<String, String> searchCriteria) {
		return true;
	}

	@Override
	protected List<PlannedTerm> getSearchResults(LookupForm lookupForm,
			Map<String, String> fieldValues, boolean unbounded) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String focusAtpId = request
				.getParameter(PlanConstants.FOCUS_ATP_ID_KEY);
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();
		String[] params = {};

		/************* PlannedCourseList **************/
		List<PlannedCourseDataObject> plannedCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			plannedCoursesList = getPlanItems(
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED, studentId);
		} catch (Exception e) {
			LOG.error("Could not load plannedCourseslist", e);

		}

		/**** academic record SWS call to get the studentCourseRecordInfo list *****/
		List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
		try {
			studentCourseRecordInfos = getAcademicRecordService()
					.getCompletedCourseRecords(
							studentId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (Exception e) {
			GlobalVariables.getMessageMap().putWarningForSectionId(
					PlanConstants.PLAN_ITEM_RESPONSE_PAGE_ID,
					PlanConstants.ERROR_TECHNICAL_PROBLEMS, params);
			LOG.error(
					"Could not retrieve StudentCourseRecordInfo from the SWS.",
					e);
		}

		/************* BackupCourseList **************/
		List<PlannedCourseDataObject> backupCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			backupCoursesList = getPlanItems(
					PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP, studentId);
		} catch (Exception e) {
			LOG.error("Could not load backupCourseList", e);

		}

		List<PlannedTerm> perfectPlannedTerms = PlannedTermsHelperBase
				.populatePlannedTerms(plannedCoursesList, backupCoursesList,
						studentCourseRecordInfos, focusAtpId, PlanConstants.MAX_FUTURE_YEARS, false);
		return perfectPlannedTerms;
	}
}
