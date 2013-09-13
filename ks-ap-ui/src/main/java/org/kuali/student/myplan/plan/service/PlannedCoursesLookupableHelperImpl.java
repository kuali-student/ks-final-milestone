package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.kuali.student.myplan.plan.dataobject.TermNoteDataObject;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Produce a list of planned course items.
 */
public class PlannedCoursesLookupableHelperImpl extends PlanItemLookupableHelperBase {

	private static final long serialVersionUID = 1502719861875054079L;

	private static final Logger LOG = Logger
			.getLogger(PlannedCoursesLookupableHelperImpl.class);

	private transient AcademicRecordService academicRecordService;

	public AcademicRecordService getAcademicRecordService() {
		if (this.academicRecordService == null) {
			this.academicRecordService = KsapFrameworkServiceLocator.getAcademicRecordService();
		}

		return this.academicRecordService;
	}

	public void setAcademicRecordService(AcademicRecordService academicRecordService) {
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
	public boolean validateSearchParameters(LookupForm form, Map<String, String> searchCriteria) {
		return true;
	}

	@Override
	protected List<PlannedTerm> getSearchResults(LookupForm lookupForm, Map<String, String> fieldValues,
			boolean unbounded) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String focusAtpId = request.getParameter(PlanConstants.FOCUS_ATP_ID_KEY);
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper().getStudentId();

		/**** academic record SWS call to get the studentCourseRecordInfo list *****/
		List<StudentCourseRecordInfo> studentCourseRecordInfos = new ArrayList<StudentCourseRecordInfo>();
		try {
			studentCourseRecordInfos = getAcademicRecordService().getCompletedCourseRecords(studentId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("AR lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("AR lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("AR lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("AR lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("AR lookup failure", e);
		}

		String firstAtpId = null;
		if (studentCourseRecordInfos != null)
			for (StudentCourseRecordInfo item : studentCourseRecordInfos)
				if (item.getTermName() != null)
					firstAtpId = item.getTermName();
		KsapFrameworkServiceLocator.getTermHelper().frontLoadForPlanner(firstAtpId);

		/************* PlannedCourseList **************/
		List<PlannedCourseDataObject> plannedCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			plannedCoursesList = getPlanItems(PlanConstants.LEARNING_PLAN_ITEM_TYPE_PLANNED, studentId);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup failure", e);
		}

		/************* BackupCourseList **************/
		List<PlannedCourseDataObject> backupCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			backupCoursesList = getPlanItems(PlanConstants.LEARNING_PLAN_ITEM_TYPE_BACKUP, studentId);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup failure", e);
		}

		/************* Cart List **************/
		List<PlannedCourseDataObject> cartCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			cartCoursesList = getPlanItems(PlanConstants.LEARNING_PLAN_ITEM_TYPE_CART, studentId);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup failure", e);
		}

        /************* Cart List **************/
        List<TermNoteDataObject> termNoteList = new ArrayList<TermNoteDataObject>();
        try {
            termNoteList = getTermNotes(studentId);
        } catch (Exception e) {
            LOG.error("Could not load term note list", e);

        }

		List<PlannedTerm> perfectPlannedTerms = PlannedTermsHelperBase
				.populatePlannedTerms(plannedCoursesList, backupCoursesList,
						studentCourseRecordInfos, cartCoursesList, termNoteList, focusAtpId, false);
		return perfectPlannedTerms;
	}
}
