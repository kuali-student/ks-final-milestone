package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.lookup.LookupForm;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
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
import org.kuali.student.r2.core.acal.infc.Term;
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
	public List<FullPlanItemsDataObject> performSearch(
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

            YearTerm startYear=null;
            YearTerm endYear = startYear;
            List<Term> termsInYear=null;
            for (int j = 0; j < numberOfTerms; j++){
                PlannedTerm pluckedTerm=null;
                if(perfectPlannedTerms.size()>0){
                    pluckedTerm = perfectPlannedTerms.get(0);
                    if (startYear==null) {
                        startYear = getStartYearOfAcadYearHavingTerm(pluckedTerm.getAtpId());
                        endYear=getEndYearOfAcadYearHavingTerm(pluckedTerm.getAtpId());
                        termsInYear=KsapFrameworkServiceLocator.getTermHelper().getTermsInAcademicYear(startYear);
                    }
                }
                if (pluckedTerm!=null && termsInYear!=null
                        && termsInYear.get(j).getId().equals(pluckedTerm.getAtpId())
                        ) {
                    plannedTermList.add(pluckedTerm);
                    perfectPlannedTerms.remove(0);
                } else {
                    plannedTermList.add(new PlannedTerm());
                }
            }

			StringBuilder yearRange = new StringBuilder();
			yearRange = yearRange.append(startYear.getYear()).append("-")
					.append(endYear.getYear());
			fullPlanItemsDataObject.setYearRange(yearRange.toString());
			fullPlanItemsDataObject.setTerms(plannedTermList);
			fullPlanItemsDataObjectList.add(fullPlanItemsDataObject);
		}
		return fullPlanItemsDataObjectList;
	}
    private YearTerm getEndYearOfAcadYearHavingTerm(String termAtpId) {
        YearTerm startYear=getStartYearOfAcadYearHavingTerm(termAtpId);
        YearTerm endYear=null;
        if (startYear!=null){
            List<Term> termsInYear =KsapFrameworkServiceLocator.getTermHelper().getTermsInAcademicYear(startYear);
            endYear=KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termsInYear.get(termsInYear.size() - 1));
        } else {
            endYear=KsapFrameworkServiceLocator.getTermHelper()
                    .getYearTerm(KsapFrameworkServiceLocator.getTermHelper().getCurrentTerm());
        }
        return endYear;
    }


    private YearTerm getStartYearOfAcadYearHavingTerm(String termAtpId) {
        YearTerm startYear=KsapFrameworkServiceLocator.getTermHelper().getYearTerm(termAtpId);
        if (startYear!=null){
            startYear=KsapFrameworkServiceLocator.getTermHelper().getYearTerm(KsapFrameworkServiceLocator.getTermHelper()
                    .getFirstTermOfAcademicYear(startYear));
        } else {
            startYear=KsapFrameworkServiceLocator.getTermHelper()
                    .getYearTerm(KsapFrameworkServiceLocator.getTermHelper().getCurrentTerm());
        }
        return startYear;
    }

}
