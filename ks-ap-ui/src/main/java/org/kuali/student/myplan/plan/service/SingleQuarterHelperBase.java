package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.ap.coursesearch.service.impl.CourseDetailsInquiryHelperImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.support.DefaultTermHelper;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.ap.coursesearch.dataobject.ActivityOfferingItem;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;

/**
 * Created by IntelliJ IDEA. User: hemanthg Date: 5/16/12 Time: 3:49 PM To
 * change this template use File | Settings | File Templates.
 * 
 * Planner information for the calendar.
 */
public class SingleQuarterHelperBase {

	public static PlannedTerm populatePlannedTerms(
			List<PlannedCourseDataObject> plannedCoursesList,
			List<PlannedCourseDataObject> backupCoursesList,
			List<StudentCourseRecordInfo> studentCourseRecordInfos,
			String termAtp, boolean isServiceUp) {
		String globalCurrentAtpId = null;
		globalCurrentAtpId = KsapFrameworkServiceLocator.getTermHelper()
				.getCurrentTerm().getId();

		/*
		 * Populating the PlannedTerm List.
		 */
		PlannedTerm plannedTerm = new PlannedTerm();
		plannedTerm.setAtpId(termAtp);
		plannedTerm.setQtrYear(KsapFrameworkServiceLocator.getTermHelper()
				.getYearTerm(termAtp).getTermName());
		for (PlannedCourseDataObject plan : plannedCoursesList) {
			String atp = plan.getPlanItemDataObject().getAtp();
			if (termAtp.equalsIgnoreCase(atp)) {
				plannedTerm.getPlannedList().add(plan);
			}
		}

		/*
		 * Populating the backup list for the Plans
		 */

		if (backupCoursesList != null) {

			for (PlannedCourseDataObject backup : backupCoursesList) {
				String atp = backup.getPlanItemDataObject().getAtp();
				if (termAtp.equalsIgnoreCase(atp)) {
					plannedTerm.getBackupList().add(backup);
				}
			}

		}

		List<AcademicRecordDataObject> academicRecordDataObjectList = new ArrayList<AcademicRecordDataObject>();

		/***********
		 * Implementation to populate the plannedTerm list with academic record
		 * and planned terms
		 ******************/
		if (studentCourseRecordInfos.size() > 0) {
			for (StudentCourseRecordInfo studentInfo : studentCourseRecordInfos) {
                String termId = studentInfo.getTermId();
				if (termAtp.equalsIgnoreCase(termId)) {
					CourseDetailsInquiryHelperImpl courseDetailsService = new CourseDetailsInquiryHelperImpl();
					AcademicRecordDataObject academicRecordDataObject = new AcademicRecordDataObject();
					academicRecordDataObject
							.setAtpId(termId);
					academicRecordDataObject.setPersonId(studentInfo
							.getPersonId());
					academicRecordDataObject.setCourseCode(studentInfo
							.getCourseCode());

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
									.isCompleted(termId)) {
						academicRecordDataObject.setGrade(studentInfo
								.getCalculatedGradeValue());
					}
					if (!KsapFrameworkServiceLocator.getTermHelper()
							.isCompleted(termId)) {
						academicRecordDataObject.setActivityCode(studentInfo
								.getActivityCode());
					}
					academicRecordDataObject.setRepeated(studentInfo
							.getIsRepeated());

					if (academicRecordDataObject.getCourseId() != null) {
						List<ActivityOfferingItem> activityOfferingItemList = courseDetailsService
								.getActivityOfferingItemsById(
										academicRecordDataObject.getCourseId(),
										academicRecordDataObject.getAtpId());
						for (ActivityOfferingItem activityOfferingItem : activityOfferingItemList) {
							if (activityOfferingItem.getCode()
									.equalsIgnoreCase(
											academicRecordDataObject
													.getActivityCode())) {
								academicRecordDataObject
										.setActivityOfferingItem(activityOfferingItem);
								break;
							}

						}
					}

					academicRecordDataObjectList.add(academicRecordDataObject);
				}
			}
		}

		plannedTerm.setAcademicRecord(academicRecordDataObjectList);

		/*
		 * Implementation to set the conditional flags based on each plannedTerm
		 * atpId
		 */

		if (KsapFrameworkServiceLocator.getTermHelper().isPlanning(
				plannedTerm.getAtpId())) {
			plannedTerm.setOpenForPlanning(true);
		}
		if (KsapFrameworkServiceLocator.getTermHelper().isCompleted(
				plannedTerm.getAtpId())) {
			plannedTerm.setCompletedTerm(true);
		}
		if (globalCurrentAtpId.equalsIgnoreCase(plannedTerm.getAtpId())) {
			plannedTerm.setCurrentTermForView(true);
		}

		/*
		 * populateHelpIconFlags(perfectPlannedTerms);
		 */
		return plannedTerm;

	}

}
