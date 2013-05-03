package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.acal.infc.Term;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

import static org.kuali.rice.core.api.criteria.PredicateFactory.equalIgnoreCase;
import static org.kuali.rice.core.api.criteria.PredicateFactory.like;

/**
 * Created by IntelliJ IDEA. User: hemanthg Date: 5/16/12 Time: 3:49 PM To
 * change this template use File | Settings | File Templates.
 */
public class PlannedTermsHelperBase {

	public static List<PlannedTerm> populatePlannedTerms(
			List<PlannedCourseDataObject> plannedCoursesList,
			List<PlannedCourseDataObject> backupCoursesList,
			List<StudentCourseRecordInfo> studentCourseRecordInfos,
			String focusAtpId, int futureTerms, boolean fullPlanView) {
		TermHelper th = KsapFrameworkServiceLocator.getTermHelper();
        YearTerm focusQuarterYear;
		if(StringUtils.isEmpty(focusAtpId)) focusQuarterYear = th.getYearTerm(th.getCurrentTerms().get(0));
        else focusQuarterYear = th.getYearTerm(th
				.getFirstTermOfAcademicYear(th.getYearTerm(focusAtpId)));

		List<PlannedTerm> plannedTerms = new ArrayList<PlannedTerm>();
		for (PlannedCourseDataObject plan : plannedCoursesList) {
			String atp = plan.getPlanItemDataObject().getAtp();
			boolean exists = false;
			for (PlannedTerm term : plannedTerms) {
				if (term.getAtpId().equalsIgnoreCase(atp)) {
					term.getPlannedList().add(plan);
					exists = true;
				}
			}
			if (!exists) {
				PlannedTerm term = new PlannedTerm();
				term.setAtpId(atp);
				YearTerm yearTerm = KsapFrameworkServiceLocator.getTermHelper()
						.getYearTerm(atp);
				StringBuilder sb = new StringBuilder();
				sb.append(yearTerm.getTermName());
				String QtrYear = sb.substring(0, 1).toUpperCase()
						.concat(sb.substring(1));
				term.setQtrYear(QtrYear);
				term.getPlannedList().add(plan);
				plannedTerms.add(term);
			}
		}

		/*
		 * Populating the backup list for the Plans
		 */
		if (backupCoursesList != null) {
			int count = plannedTerms.size();
			for (PlannedCourseDataObject bl : backupCoursesList) {
				String atp = bl.getPlanItemDataObject().getAtp();

				boolean added = false;
				for (int i = 0; i < count; i++) {
					if (atp.equalsIgnoreCase(plannedTerms.get(i).getAtpId())) {
						plannedTerms.get(i).getBackupList().add(bl);
						added = true;
					}
				}
				if (!added) {
					PlannedTerm plannedTerm = new PlannedTerm();
					plannedTerm.setAtpId(atp);
					StringBuffer str = new StringBuffer();
					YearTerm yearTerm = KsapFrameworkServiceLocator
							.getTermHelper().getYearTerm(atp);
					str = str.append(yearTerm.getTermName());
					String QtrYear = str.substring(0, 1).toUpperCase()
							.concat(str.substring(1, str.length()));
					plannedTerm.setQtrYear(QtrYear);
					plannedTerm.getBackupList().add(bl);
					plannedTerms.add(plannedTerm);
					count++;
				}
			}
		}

		/*
		 * Used for sorting the planItemDataobjects
		 */
		List<AcademicRecordDataObject> academicRecordDataObjectList = new ArrayList<AcademicRecordDataObject>();

        Collections.sort(plannedTerms, new Comparator<PlannedTerm>() {
            @Override
            public int compare(PlannedTerm plannedTerm1,
                               PlannedTerm plannedTerm2) {
                return KsapFrameworkServiceLocator
                        .getTermHelper()
                        .getYearTerm(plannedTerm1.getAtpId())
                        .compareTo(
                                KsapFrameworkServiceLocator.getTermHelper()
                                        .getYearTerm(plannedTerm2.getAtpId()));
            }
        });

		/***********
		 * Implementation to populate the plannedTerm list with academic record
		 * and planned terms
		 ******************/
		Map<String, PlannedTerm> termsList = new LinkedHashMap<String, PlannedTerm>();
		String minTerm = null;
		if (plannedTerms.size() > 0 && fullPlanView){
            int yearStart = KsapFrameworkServiceLocator.getTermHelper().getYearTerm(plannedTerms.get(0).getAtpId()).getYear();
            int yearEnd = KsapFrameworkServiceLocator.getTermHelper().getYearTerm(plannedTerms.get(plannedTerms.size() - 1).getAtpId()).getYear();
            int years = (yearEnd - yearStart)+1;
            if(years>0) futureTerms=years;
            else futureTerms=1;
        }
		if (studentCourseRecordInfos.size() > 0)
			minTerm = studentCourseRecordInfos.get(0).getTermName();
        else if (plannedTerms.size() > 0)
            minTerm = plannedTerms.get(0).getAtpId();
		else
			minTerm = th.getCurrentTerms().get(0).getId();
        populateTermData(minTerm, futureTerms, termsList);

		if (plannedTerms.size() > 0)
			for (PlannedTerm plannedTerm : plannedTerms)
				if (termsList.containsKey(plannedTerm.getAtpId()))
					if (plannedTerm.getPlannedList().size() > 0
							|| plannedTerm.getBackupList().size() > 0) {
						plannedTerm.setQtrYear(termsList.get(
								plannedTerm.getAtpId()).getQtrYear());

						termsList.put(plannedTerm.getAtpId(), plannedTerm);
					}
		if (studentCourseRecordInfos.size() > 0) {
			for (StudentCourseRecordInfo studentInfo : studentCourseRecordInfos) {
				if (termsList.containsKey(studentInfo.getTermName())) {
					AcademicRecordDataObject academicRecordDataObject = new AcademicRecordDataObject();
					academicRecordDataObject
							.setAtpId(studentInfo.getTermName());
					academicRecordDataObject.setPersonId(studentInfo
							.getPersonId());
					academicRecordDataObject.setCourseCode(studentInfo
							.getCourseCode());
					/*
					 * TODO: StudentCourseRecordInfo does not have a courseId
					 * property so using Id to set the course Id
					 */
					academicRecordDataObject.setCourseId(studentInfo.getId());
					academicRecordDataObject.setCourseTitle(studentInfo
							.getCourseTitle());
					academicRecordDataObject.setCredit(studentInfo
							.getCreditsEarned());
					if (!"X".equalsIgnoreCase(studentInfo
							.getCalculatedGradeValue())) {
						academicRecordDataObject.setGrade(studentInfo
								.getCalculatedGradeValue());
					} else if ("X".equalsIgnoreCase(studentInfo
							.getCalculatedGradeValue())
							&& KsapFrameworkServiceLocator.getTermHelper()
									.isCompleted(studentInfo.getTermName())) {
						academicRecordDataObject.setGrade(studentInfo
								.getCalculatedGradeValue());
					}
					academicRecordDataObject.setRepeated(studentInfo
							.getIsRepeated());
					academicRecordDataObjectList.add(academicRecordDataObject);
					termsList.get(studentInfo.getTermName())
							.getAcademicRecord().add(academicRecordDataObject);
				}
			}
		}
		List<PlannedTerm> perfectPlannedTerms = new ArrayList<PlannedTerm>(
				termsList.values());

		/*
		 * Sort terms in order
		 */
		Collections.sort(perfectPlannedTerms, new Comparator<PlannedTerm>() {
			@Override
			public int compare(PlannedTerm plannedTerm1,
					PlannedTerm plannedTerm2) {
				return KsapFrameworkServiceLocator
						.getTermHelper()
						.getYearTerm(plannedTerm1.getAtpId())
						.compareTo(
								KsapFrameworkServiceLocator.getTermHelper()
										.getYearTerm(plannedTerm2.getAtpId()));
				// return
				// plannedTerm1.getAtpId().compareTo(plannedTerm2.getAtpId());
			}
		});
		// Can't do this step until the sort has been done else the index
		// won't be correct.
		for (int i = 0; i < perfectPlannedTerms.size(); i++) {
			PlannedTerm pt = perfectPlannedTerms.get(0);
			if (pt.getAtpId().isEmpty())
				continue;
			YearTerm qy = th.getYearTerm(pt.getAtpId());
			if (qy.equals(focusQuarterYear)) {
				pt.setIndex(i);
				break;
			}
		}

		/*
		 * Implementation to set the conditional flags based on each plannedTerm
		 * atpId
		 */
		for (PlannedTerm pl : perfectPlannedTerms) {
			if (th.isPlanning(pl.getAtpId()))
				pl.setOpenForPlanning(true);
			if (th.isCompleted(pl.getAtpId()))
				pl.setCompletedTerm(true);
			for (Term t : th.getCurrentTerms())
				if (t.getId().equals(pl.getAtpId()))
					pl.setCurrentTermForView(true);
		}

		populateHelpIconFlags(perfectPlannedTerms);
		return perfectPlannedTerms;
	}

	/**
	 * Populates a list of terms starting from the year containing a specified
	 * term id.
	 * 
	 * @param termId
	 * @param termsList
	 */
	private static void populateTermData(String termId, int futureTermsCount,
			Map<String, PlannedTerm> termsList) {
		TermHelper th = KsapFrameworkServiceLocator.getTermHelper();
		Term term = th.getFirstTermOfAcademicYear(th.getYearTerm(termId));
		Date startDate = term.getStartDate();
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		c.add(Calendar.YEAR, futureTermsCount);
		Date endDate = c.getTime();
		try {
			QueryByCriteria criteria =QueryByCriteria.Builder.fromPredicates(
					like("atpStatus",
                    PlanConstants.PUBLISHED)); 


            // need to re-examine this functionality
            // ie would a term that starts before end date but ends after end date be considered in that period.
            // ie- off by one errors.
			for (Term t : KsapFrameworkServiceLocator
					.getAcademicCalendarService().searchForTerms(
							criteria,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo())){

				if(t.getStartDate().compareTo(startDate)<0) continue;
				if(t.getEndDate().compareTo(endDate)>=0) continue;
				PlannedTerm plannedTerm = new PlannedTerm();
				plannedTerm.setAtpId(t.getId());
				plannedTerm.setQtrYear(t.getName());
				termsList.put(t.getId(), plannedTerm);
			}
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("Acal lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("Acal lookup failure", e);
		}
	}

	/**
	 * Sets display statuses used to by the ui for the help icons. Sets statuses
	 * for all entries in a list of terms.
	 * 
	 * @param plannedTerms
	 */
	private static void populateHelpIconFlags(List<PlannedTerm> plannedTerms) {

		int index = plannedTerms.size() - 1;
		while (index >= 0) {
			for (int i = 4; i > 0; i--) {
				if (plannedTerms.get(index).isCurrentTermForView() || i == 1) {
					plannedTerms.get(index).setDisplayBackupHelp(true);
					plannedTerms.get(index).setDisplayPlannedHelp(true);
					index = index - i;
					break;
				}
				index--;
				if (index < 0)
					break;
			}
		}
		index = 0;
		while (index < plannedTerms.size()) {
			for (int i = 1; i < 4; i++) {
				if (index < plannedTerms.size()
						&& plannedTerms.get(index).isCompletedTerm() && i == 1) {
					plannedTerms.get(index).setDisplayCompletedHelp(true);
					index = index + (5 - i);
					break;
				}
				index++;

			}
		}
		index = plannedTerms.size() - 1;
		while (index >= 0) {
			for (int i = 4; i > 0; i--) {
                if(index<0) break;
				if (plannedTerms.get(index).isCurrentTermForView()
						|| !plannedTerms.get(index).isCompletedTerm()
						&& (plannedTerms.get(index).getAcademicRecord().size() > 0 || !plannedTerms
								.get(index).isOpenForPlanning())) {
					plannedTerms.get(index).setDisplayRegisteredHelp(true);
					index = index - i;
					break;
				}
				index--;
			}
		}

	}

}
