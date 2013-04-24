package org.kuali.student.myplan.plan.dataobject;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.myplan.course.dataobject.CourseDetails;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hemanthg
 * Date: 4/2/12
 * Time: 3:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlannedTerm {
    private String atpId;
    private String qtrYear;

    private List<PlannedCourseDataObject> plannedList = new ArrayList<PlannedCourseDataObject>();
    private List<PlannedCourseDataObject> backupList = new ArrayList<PlannedCourseDataObject>();
    private List<AcademicRecordDataObject> academicRecord = new ArrayList<AcademicRecordDataObject>();
    private String credits = null;

    /*These flags are used for help icons to display*/

    private boolean displayCompletedHelp;
    private boolean displayPlannedHelp;
    private boolean displayCreditsHelp = true;
    private boolean displayBackupHelp;
    private boolean displayRegisteredHelp;


    /*These flags are used for styling, highlighting, naming  of terms in the quarter view */
    private boolean currentTermForView;
    private boolean completedTerm;
    private boolean openForPlanning;


    /*
    *  The index of this item in a list of PlannedTerms. This is used by the UI to focus the "carousellite" javascript
    *  component. The value should be -1 unless this PlannedTerm should have focus.
    */
    private int index = -1;

    public PlannedTerm(){

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getAtpId() {
        return atpId;
    }

    public String getQtrYear() {
        return qtrYear;
    }

    public void setQtrYear(String qtrYear) {
        this.qtrYear = qtrYear;
    }

    public void setAtpId(String atpId) {
        this.atpId = atpId;
    }

    public List<PlannedCourseDataObject> getPlannedList() {
        return plannedList;
    }

    public void setPlannedList(List<PlannedCourseDataObject> plannedList) {
        this.plannedList = plannedList;
    }

    public List<PlannedCourseDataObject> getBackupList() {
        return backupList;
    }

    public void setBackupList(List<PlannedCourseDataObject> backupList) {
        this.backupList = backupList;
    }

    public String getCredits() {
        String totalCredits = null;
        double plannedTotalMin = 0;
        double plannedTotalMax = 0;
        // TODO need to check code and refine try statement to catch exact place of failures
        try{
            if (getPlannedList().size() > 0 && isOpenForPlanning()) {

                for (PlannedCourseDataObject pc : getPlannedList()) {
                    if (pc.getCourseDetails() != null && !pc.getCourseDetails().getCredit().contains(".")) {
                        String[] str = pc.getCourseDetails().getCredit().split("\\D");
                        double min = Double.parseDouble(str[0]);
                        plannedTotalMin += min;
                        double max = Double.parseDouble(str[str.length - 1]);
                        plannedTotalMax += max;

                    } else if (pc.getCourseDetails() != null && pc.getCourseDetails().getCredit().contains(".")) {
                        if (pc.getCourseDetails().getCredit().contains(PlanConstants.MULTIPLE)) {
                            String[] str = pc.getCourseDetails().getCredit().split(PlanConstants.MULTIPLE);
                            plannedTotalMin += Double.parseDouble(str[0]);
                            plannedTotalMax += Double.parseDouble(str[1]);
                        } else if (pc.getCourseDetails().getCredit().contains(PlanConstants.RANGE)) {
                            String[] str = pc.getCourseDetails().getCredit().split(PlanConstants.RANGE);
                            plannedTotalMin += Double.parseDouble(str[0]);
                            plannedTotalMax += Double.parseDouble(str[1]);
                        } else {
                            plannedTotalMin += Double.parseDouble(pc.getCourseDetails().getCredit());
                            plannedTotalMax += Double.parseDouble(pc.getCourseDetails().getCredit());
                        }
                    }
                }
                totalCredits = Double.toString(plannedTotalMin);

                if (plannedTotalMin != plannedTotalMax) {
                    totalCredits = totalCredits + "-" + Double.toString(plannedTotalMax);

                }
            }
            double academicTotalMin = 0;
            double academicTotalMax = 0;
            if (getAcademicRecord().size() > 0) {

                for (AcademicRecordDataObject ar : getAcademicRecord()) {
                    if (ar.getCredit() != null || !ar.getCredit().isEmpty() && !ar.getCredit().contains(".")) {
                        String[] str = ar.getCredit().split("\\D");
                        double min = Double.parseDouble(str[0]);
                        academicTotalMin += min;
                        double max = Double.parseDouble(str[str.length - 1]);
                        academicTotalMax += max;
                    } else if (ar.getCredit() != null || !ar.getCredit().isEmpty() && ar.getCredit().contains(".")) {
                        academicTotalMin += Double.parseDouble(ar.getCredit());
                        academicTotalMax += Double.parseDouble(ar.getCredit());
                    }
                }
                totalCredits = Double.toString(academicTotalMin);

                if (academicTotalMin != academicTotalMax) {
                    totalCredits = totalCredits + "-" + Double.toString(academicTotalMax);


                }


            }

            /*TODO:Implement this based on the flags (past,present,future) logic*/
            if (getPlannedList().size() > 0 && getAcademicRecord().size() > 0) {
                if (plannedTotalMin != plannedTotalMax && academicTotalMin != academicTotalMax) {
                    double minVal = 0;
                    double maxVal = 0;
                    minVal = plannedTotalMin + academicTotalMin;
                    maxVal = plannedTotalMax + academicTotalMax;
                    totalCredits = minVal + "-" + maxVal;
                }
                if (plannedTotalMin == plannedTotalMax && academicTotalMin == academicTotalMax) {
                    totalCredits = String.valueOf(plannedTotalMin + academicTotalMin);
                }
                if (plannedTotalMin != plannedTotalMax && academicTotalMin == academicTotalMax) {
                    double minVal = 0;
                    double maxVal = 0;
                    minVal = plannedTotalMin + academicTotalMin;
                    maxVal = plannedTotalMax + academicTotalMax;
                    totalCredits = minVal + "-" + maxVal;

                }
                if (plannedTotalMin == plannedTotalMax && academicTotalMin != academicTotalMax) {
                    double minVal = 0;
                    double maxVal = 0;
                    minVal = academicTotalMin;
                    maxVal = plannedTotalMax + academicTotalMax;
                    totalCredits = minVal + "-" + maxVal;
                }
            }
            if (totalCredits != null) {
                if (totalCredits.contains(".0")) totalCredits = totalCredits.replace(".0", "");
            }
        }catch(NumberFormatException e){
            return null;
        }
        return totalCredits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }


    public List<AcademicRecordDataObject> getAcademicRecord() {
        return academicRecord;
    }

    public void setAcademicRecord(List<AcademicRecordDataObject> academicRecord) {
        this.academicRecord = academicRecord;
    }


    public boolean isCurrentTermForView() {
        return currentTermForView;
    }

    public void setCurrentTermForView(boolean currentTermForView) {
        this.currentTermForView = currentTermForView;
    }

    public boolean isCompletedTerm() {
        return completedTerm;
    }

    public void setCompletedTerm(boolean completedTerm) {
        this.completedTerm = completedTerm;
    }

    public boolean isOpenForPlanning() {
        return openForPlanning;
    }

    public void setOpenForPlanning(boolean openForPlanning) {
        this.openForPlanning = openForPlanning;
    }

    public boolean isDisplayCompletedHelp() {
        return displayCompletedHelp;
    }

    public void setDisplayCompletedHelp(boolean displayCompletedHelp) {
        this.displayCompletedHelp = displayCompletedHelp;
    }

    public boolean isDisplayPlannedHelp() {
        return displayPlannedHelp;
    }

    public void setDisplayPlannedHelp(boolean displayPlannedHelp) {
        this.displayPlannedHelp = displayPlannedHelp;
    }

    public boolean isDisplayCreditsHelp() {
        return displayCreditsHelp;
    }

    public void setDisplayCreditsHelp(boolean displayCreditsHelp) {
        this.displayCreditsHelp = displayCreditsHelp;
    }

    public boolean isDisplayBackupHelp() {
        return displayBackupHelp;
    }

    public void setDisplayBackupHelp(boolean displayBackupHelp) {
        this.displayBackupHelp = displayBackupHelp;
    }

    public boolean isDisplayRegisteredHelp() {
        return displayRegisteredHelp;
    }

    public void setDisplayRegisteredHelp(boolean displayRegisteredHelp) {
        this.displayRegisteredHelp = displayRegisteredHelp;
    }

    public String getAtpIdYear(){
        try{
            AtpInfo atp = KsapFrameworkServiceLocator.getAtpService().getAtp(getAtpId()
                ,KsapFrameworkServiceLocator.getContext().getContextInfo());
            return atp.getName();
        }catch (Exception e){
            return "0000";
        }


    }
}

