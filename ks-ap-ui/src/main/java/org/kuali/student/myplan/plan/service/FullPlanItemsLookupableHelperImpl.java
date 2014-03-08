package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.web.form.LookupForm;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.myplan.plan.dataobject.FullPlanItemsDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA. User: hemanthg Date: 4/13/12 Time: 1:49 PM To
 * change this template use File | Settings | File Templates.
 */
public class FullPlanItemsLookupableHelperImpl extends
		PlanItemLookupableHelperBase {

	private static final long serialVersionUID = -2081050491075600747L;

	private static final Logger LOG = LoggerFactory.getLogger(FullPlanItemsLookupableHelperImpl.class);


	@Override
	protected List<FullPlanItemsDataObject> getSearchResults(
			LookupForm lookupForm, Map<String, String> fieldValues,
			boolean unbounded) {
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();
		/************* PlannedCourseList **************/
		List<PlannedCourseDataObject> plannedCoursesList = new ArrayList<PlannedCourseDataObject>();
		try {
			plannedCoursesList = getPlanItems(
					AcademicPlanServiceConstants.ItemCategory.PLANNED, studentId);
		} catch (Exception e) {
			LOG.error("Could not load plannedCourseslist", e);

		}
		/**** academic record SWS call to get the studentCourseRecordInfo list *****/

		List<StudentCourseRecordInfo> studentCourseRecordInfos;
		try {
			studentCourseRecordInfos = KsapFrameworkServiceLocator
					.getAcademicRecordService().getCompletedCourseRecords(
							studentId,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (DoesNotExistException e) {
			studentCourseRecordInfos = new java.util.LinkedList<StudentCourseRecordInfo>();
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("AR lookup error", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("AR lookup error", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("AR lookup error", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("AR lookup error", e);
		}

		List<PlannedTerm> perfectPlannedTerms = PlannedTermsHelperBase
				.populatePlannedTerms(plannedCoursesList, null,
						studentCourseRecordInfos,null, null,"", true);
        int numberOfTerms =KsapFrameworkServiceLocator.getTermHelper().getNumberOfTermsInAcademicYear();


		List<FullPlanItemsDataObject> fullPlanItemsDataObjectList = new ArrayList<FullPlanItemsDataObject>();
		while (!perfectPlannedTerms.isEmpty()) {
			FullPlanItemsDataObject fullPlanItemsDataObject = new FullPlanItemsDataObject();
			List<PlannedTerm> plannedTermList = new ArrayList<PlannedTerm>();

            for (int j = 0; j < numberOfTerms; j++){
                if(perfectPlannedTerms.size()>0){
                    plannedTermList.add(perfectPlannedTerms.remove(0));
                }else{
                    plannedTermList.add(new PlannedTerm());
                }
            }
            YearTerm minYear;
			try{
                minYear= KsapFrameworkServiceLocator.getTermHelper()
                        .getYearTerm(KSCollectionUtils.getRequiredZeroElement(plannedTermList).getAtpId());
            }catch (OperationFailedException e){
                minYear=KsapFrameworkServiceLocator.getTermHelper()
                        .getYearTerm(KsapFrameworkServiceLocator.getTermHelper().getCurrentTerm());
            }
            YearTerm maxYear = minYear;
            if(!StringUtils.isEmpty(plannedTermList.get(plannedTermList.size() - 1)
                    .getAtpId())){
			    maxYear = KsapFrameworkServiceLocator.getTermHelper()
					.getYearTerm(
							plannedTermList.get(plannedTermList.size() - 1)
									.getAtpId());
            }
			StringBuilder yearRange = new StringBuilder();
			yearRange = yearRange.append(minYear.getYear()).append("-")
					.append(maxYear.getYear());
			fullPlanItemsDataObject.setYearRange(yearRange.toString());
			fullPlanItemsDataObject.setTerms(plannedTermList);
			fullPlanItemsDataObjectList.add(fullPlanItemsDataObject);
		}
		return fullPlanItemsDataObjectList;
	}

}
