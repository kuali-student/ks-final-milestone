package org.kuali.student.myplan.plan.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedTerm;
import org.kuali.student.myplan.plan.dataobject.TermNoteDataObject;
import org.kuali.student.r2.core.acal.infc.Term;

/**
 * Created by IntelliJ IDEA. User: hemanthg Date: 5/16/12 Time: 3:49 PM To
 * change this template use File | Settings | File Templates.
 */
public class PlannedTermsHelperBase {

	private static final Logger LOG = Logger.getLogger(PlannedTermsHelperBase.class);

	public static List<PlannedTerm> populatePlannedTerms(List<PlannedCourseDataObject> plannedCoursesList,
			List<PlannedCourseDataObject> backupCoursesList, List<StudentCourseRecordInfo> studentCourseRecordInfos,
			List<PlannedCourseDataObject> cartCoursesList, List<TermNoteDataObject> termNoteList, String focusAtpId, boolean fullPlanView) {

		TermHelper th = KsapFrameworkServiceLocator.getTermHelper();
		Map<String, PlannedTerm> termsList = new HashMap<String, PlannedTerm>();
		List<Term> planningTerms = th.getPlanningTerms();
		for (Term t : planningTerms) {
			PlannedTerm term = new PlannedTerm();
			term.setAtpId(t.getId());
			term.setQtrYear(t.getName());
			termsList.put(t.getId(), term);
		}

		YearTerm focusQuarterYear;
		if (StringUtils.isEmpty(focusAtpId)) {
			   try{
	                focusQuarterYear = th.getYearTerm(th.getCurrentTerm());
	            }catch(Exception e){
	                LOG.warn("Could not find current term, using next term",e);
	                try{
	                    focusQuarterYear = th.getYearTerm(KSCollectionUtils.getRequiredZeroElement(th.getPlanningTerms()));
	                }catch(Exception e2){
	                    try{
	                        LOG.warn("Could not find future planned term, using last term", e2);
                            StudentCourseRecordInfo studentInfo = studentCourseRecordInfos.get(studentCourseRecordInfos.size() - 1);
                            String termId = studentInfo.getTermId();
                            focusQuarterYear = th.getYearTerm(termId);
	                    }catch(Exception e3){
	                        LOG.error("Could not find last term, using first term");
	                        focusQuarterYear = th.getYearTerm(planningTerms.get(0));
	                    }
	                }
	            }
		} else {
			focusQuarterYear = th.getYearTerm(th.getFirstTermOfAcademicYear(th.getYearTerm(focusAtpId)));
		}

		for (PlannedCourseDataObject plan : plannedCoursesList) {
			String atp = plan.getPlanItemDataObject().getAtp();
			PlannedTerm pt = termsList.get(atp);
			if (pt == null)
				LOG.warn("Course is not planned for a planning term " + plan.getCourseDetails() + " " + atp);
			else
				pt.getPlannedList().add(plan);
		}

		if (backupCoursesList != null)
			for (PlannedCourseDataObject bl : backupCoursesList) {
				String atp = bl.getPlanItemDataObject().getAtp();
				PlannedTerm pt = termsList.get(atp);
				if (pt == null)
					LOG.warn("Backup course is not planned for a planning term " + bl.getCourseDetails() + " " + atp);
				else
					pt.getBackupList().add(bl);
			}

		if (cartCoursesList != null) {
			for (PlannedCourseDataObject bl : cartCoursesList) {
				String atp = bl.getPlanItemDataObject().getAtp();
				PlannedTerm pt = termsList.get(atp);
				if (pt == null)
					LOG.warn("Cart course is not planned for a planning term " + bl.getCourseDetails() + " " + atp);
				else
					pt.getCartList().add(bl);
			}
		}

		if (studentCourseRecordInfos.size() > 0) {
			for (StudentCourseRecordInfo studentInfo : studentCourseRecordInfos) {
				String termId = studentInfo.getTermId();
				PlannedTerm pt = termsList.get(termId);
				if (pt == null) {
					Term t = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
					PlannedTerm term = new PlannedTerm();
					term.setAtpId(t.getId());
					term.setQtrYear(t.getName());
					termsList.put(termId, term);
					pt = term;
				}

				AcademicRecordDataObject academicRecordDataObject = new AcademicRecordDataObject();
				academicRecordDataObject.setAtpId(termId);
				academicRecordDataObject.setPersonId(studentInfo.getPersonId());
				academicRecordDataObject.setCourseCode(studentInfo.getCourseCode());
				/*
				 * TODO: StudentCourseRecordInfo does not have a courseId
				 * property so using Id to set the course Id
				 */
				academicRecordDataObject.setCourseId(studentInfo.getId());
				academicRecordDataObject.setCourseTitle(studentInfo.getCourseTitle());
				academicRecordDataObject.setCredit(studentInfo.getCreditsEarned());
				if (!"X".equalsIgnoreCase(studentInfo.getCalculatedGradeValue())) {
					academicRecordDataObject.setGrade(studentInfo.getCalculatedGradeValue());
				} else if ("X".equalsIgnoreCase(studentInfo.getCalculatedGradeValue())
						&& KsapFrameworkServiceLocator.getTermHelper().isCompleted(termId)) {
					academicRecordDataObject.setGrade(studentInfo.getCalculatedGradeValue());
				}
				academicRecordDataObject.setRepeated(studentInfo.getIsRepeated()!=null ? studentInfo.getIsRepeated():false);
				pt.getAcademicRecord().add(academicRecordDataObject);
			}
		}

		List<PlannedTerm> perfectPlannedTerms = new ArrayList<PlannedTerm>(termsList.values());

        if (termNoteList != null) {
            int count = perfectPlannedTerms.size();
            for (TermNoteDataObject bl : termNoteList) {
                String atp = bl.getAtpId();

                boolean added = false;
                for (int i = 0; i < count; i++) {
                    if (atp.equalsIgnoreCase(perfectPlannedTerms.get(i).getAtpId())) {
                        perfectPlannedTerms.get(i).getTermNoteList().add(bl);
                        added = true;
                    }
                }
                if (!added) {
                    PlannedTerm plannedTerm = new PlannedTerm();
                    plannedTerm.setAtpId(atp);
                    StringBuilder str = new StringBuilder();
                    YearTerm yearTerm = KsapFrameworkServiceLocator
                            .getTermHelper().getYearTerm(atp);
                    str = str.append(yearTerm.getTermName());
                    String QtrYear = str.substring(0, 1).toUpperCase()
                            .concat(str.substring(1, str.length()));
                    plannedTerm.setQtrYear(QtrYear);
                    plannedTerm.getTermNoteList().add(bl);
                    perfectPlannedTerms.add(plannedTerm);
                    count++;
                }
            }
        }

		/*
		 * Sort terms in order
		 */
		Collections.sort(perfectPlannedTerms, new Comparator<PlannedTerm>() {
			@Override
			public int compare(PlannedTerm plannedTerm1, PlannedTerm plannedTerm2) {
				return KsapFrameworkServiceLocator.getTermHelper().getYearTerm(plannedTerm1.getAtpId())
						.compareTo(KsapFrameworkServiceLocator.getTermHelper().getYearTerm(plannedTerm2.getAtpId()));
			}
		});

		// Can't do this step until the sort has been done else the index
		// won't be correct.
		for (int i = 0; i < perfectPlannedTerms.size(); i++) {
			PlannedTerm pt = perfectPlannedTerms.get(i);
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
					plannedTerms.get(index).setDisplayCartHelp(true);
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
				if (index < plannedTerms.size() && plannedTerms.get(index).isCompletedTerm() && i == 1) {
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
				if (index < 0)
					break;
				if (plannedTerms.get(index).isCurrentTermForView()
						|| !plannedTerms.get(index).isCompletedTerm()
						&& (plannedTerms.get(index).getAcademicRecord().size() > 0 || !plannedTerms.get(index)
								.isOpenForPlanning())) {
					plannedTerms.get(index).setDisplayRegisteredHelp(true);
					index = index - i;
					break;
				}
				index--;
			}
		}

	}

}
