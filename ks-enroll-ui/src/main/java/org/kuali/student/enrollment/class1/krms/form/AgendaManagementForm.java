package org.kuali.student.enrollment.class1.krms.form;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.tree.node.TreeNode;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingEditWrapper;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingListSectionWrapper;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AgendaManagementForm extends UifFormBase implements Serializable {

    AgendaDefinition agenda;

    //Agenda Types
    String enrollmentEligibility;
    String creditConstraints;

    //Rule Types
    String studentEligAndPrereq;
    String corequisites;
    String antirequisite;
    String recommendedPreparation;
    String restrictCredits;
    String repeatableCredit;

    //Rule Instructions
    String studentEligAndPrereqInstruction;
    String corequisitesInstruction;
    String antirequisiteInstruction;
    String recommendedPreparationInstruction;
    String restrictCreditsInstruction;
    String repeatableCreditInstruction;

    // for Rule Preview display
    private transient Tree<TreeNode, String> studentEligAndPrereqPreviewTree;
    private transient Tree<TreeNode, String> corequisitePreviewTree;
    private transient Tree<TreeNode, String> antirequisitePreviewTree;
    private transient Tree<TreeNode, String> recPrepPreviewTree;
    private transient Tree<TreeNode, String> restrictCreditPreviewTree;
    private transient Tree<TreeNode, String> repeatableCreditPreviewTree;

    //for authorization purpose
    private String adminOrg;

    private String termCode;
    private TermInfo termInfo;
    private String courseOfferingCode;
    private String subjectCode;
    private String subjectCodeDescription;
    private String radioSelection;
    private String inputCode;
    private String selectedOfferingAction;
    private CourseOfferingInfo theCourseOffering;
    private String coViewLinkWrapper = "View"; // temp var to hold/store the View Details Link
    private CourseOfferingEditWrapper courseOfferingEditWrapper = null;

    private boolean readOnly;
    private boolean crossListedCo = false;
    private String  crossListedCoCodes;

    /**
     * The courseOfferingResultList once created is unmodifiable. This had to be done because various
     * methods were illegally modifying the list throughout the app and it took me forever to track down.
     * Since the screen needs the list to be not null and the list.clear() method cannot be called we
     * have created an empty version of the list. There is a helper method to "clear"  courseOfferingResultList
     * which really just sets the "empty" list in it's place.
     */
    private List<CourseOfferingListSectionWrapper> courseOfferingResultList;
    private List<CourseOfferingListSectionWrapper> courseOfferingResultListEmpty;  // look at definition above.

    //For Adding Activity
    private String formatIdForNewAO;
    private String activityIdForNewAO;
    private String noOfActivityOfferings;

    private CourseOfferingInfo previousCourseOffering;
    private CourseOfferingInfo nextCourseOffering;
    private String previousCourseOfferingCodeUI;
    private String nextCourseOfferingCodeUI;

    private String toBeScheduledCourseOfferingsUI;
    private int toBeScheduledCourseOfferingsCount;
    private boolean selectedIllegalAOInDeletion = false;

    private boolean withinPortal = true;

    private boolean editAuthz;

    private boolean enableAddButton = false;

    public AgendaManagementForm(){
        studentEligAndPrereqPreviewTree = new Tree<TreeNode, String>();
        studentEligAndPrereqPreviewTree.setRootElement(new Node<TreeNode, String>());
        corequisitePreviewTree = new Tree<TreeNode, String>();
        corequisitePreviewTree.setRootElement(new Node<TreeNode, String>());
        antirequisitePreviewTree  = new Tree<TreeNode, String>();
        antirequisitePreviewTree.setRootElement(new Node<TreeNode, String>());
        recPrepPreviewTree = new Tree<TreeNode, String>();
        recPrepPreviewTree.setRootElement(new Node<TreeNode, String>());
        restrictCreditPreviewTree = new Tree<TreeNode, String>();
        restrictCreditPreviewTree.setRootElement(new Node<TreeNode, String>());
        repeatableCreditPreviewTree = new Tree<TreeNode, String>();
        repeatableCreditPreviewTree.setRootElement(new Node<TreeNode, String>());

        courseOfferingResultList = new ArrayList<CourseOfferingListSectionWrapper>();
        courseOfferingResultListEmpty = new ArrayList<CourseOfferingListSectionWrapper>();
    }

    public AgendaDefinition getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaDefinition agenda) {
        this.agenda = agenda;
    }

    public String getCreditConstraints() {
        return creditConstraints;
    }

    public void setCreditConstraints(String creditConstraints) {
        this.creditConstraints = creditConstraints;
    }

    public String getEnrollmentEligibility() {
        return enrollmentEligibility;
    }

    public void setEnrollmentEligibility(String enrollmentEligibility) {
        this.enrollmentEligibility = enrollmentEligibility;
    }

    public String getStudentEligAndPrereq() {
        return studentEligAndPrereq;
    }

    public void setStudentEligAndPrereq(String studentEligAndPrereq) {
        this.studentEligAndPrereq = studentEligAndPrereq;
    }

    public String getCorequisites() {
        return corequisites;
    }

    public void setCorequisites(String corequisites) {
        this.corequisites = corequisites;
    }

    public String getAntirequisite() {
        return antirequisite;
    }

    public void setAntirequisite(String antirequisite) {
        this.antirequisite = antirequisite;
    }

    public String getRecommendedPreparation() {
        return recommendedPreparation;
    }

    public void setRecommendedPreparation(String recommendedPreparation) {
        this.recommendedPreparation = recommendedPreparation;
    }

    public String getRestrictCredits() {
        return restrictCredits;
    }

    public void setRestrictCredits(String restrictCredits) {
        this.restrictCredits = restrictCredits;
    }

    public String getRepeatableCredit() {
        return repeatableCredit;
    }

    public void setRepeatableCredit(String repeatableCredit) {
        this.repeatableCredit = repeatableCredit;
    }

    public String getStudentEligAndPrereqInstruction() {
        return studentEligAndPrereqInstruction;
    }

    public void setStudentEligAndPrereqInstruction(String studentEligAndPrereqInstruction) {
        this.studentEligAndPrereqInstruction = studentEligAndPrereqInstruction;
    }

    public String getCorequisitesInstruction() {
        return corequisitesInstruction;
    }

    public void setCorequisitesInstruction(String corequisitesInstruction) {
        this.corequisitesInstruction = corequisitesInstruction;
    }

    public String getAntirequisiteInstruction() {
        return antirequisiteInstruction;
    }

    public void setAntirequisiteInstruction(String antirequisiteInstruction) {
        this.antirequisiteInstruction = antirequisiteInstruction;
    }

    public String getRecommendedPreparationInstruction() {
        return recommendedPreparationInstruction;
    }

    public void setRecommendedPreparationInstruction(String recommendedPreparationInstruction) {
        this.recommendedPreparationInstruction = recommendedPreparationInstruction;
    }

    public String getRestrictCreditsInstruction() {
        return restrictCreditsInstruction;
    }

    public void setRestrictCreditsInstruction(String restrictCreditsInstruction) {
        this.restrictCreditsInstruction = restrictCreditsInstruction;
    }

    public String getRepeatableCreditInstruction() {
        return repeatableCreditInstruction;
    }

    public void setRepeatableCreditInstruction(String repeatableCreditInstruction) {
        this.repeatableCreditInstruction = repeatableCreditInstruction;
    }

    public Tree<TreeNode, String> getStudentEligAndPrereqPreviewTree() {
        return studentEligAndPrereqPreviewTree;
    }

    public void setStudentEligAndPrereqPreviewTree(Tree<TreeNode, String> studentEligAndPrereqPreviewTree) {
        this.studentEligAndPrereqPreviewTree = studentEligAndPrereqPreviewTree;
    }

    public Tree<TreeNode, String> getCorequisitePreviewTree() {
        return corequisitePreviewTree;
    }

    public void setCorequisitePreviewTree(Tree<TreeNode, String> corequisitePreviewTree) {
        this.corequisitePreviewTree = corequisitePreviewTree;
    }

    public Tree<TreeNode, String> getAntirequisitePreviewTree() {
        return antirequisitePreviewTree;
    }

    public void setAntirequisitePreviewTree(Tree<TreeNode, String> antirequisitePreviewTree) {
        this.antirequisitePreviewTree = antirequisitePreviewTree;
    }

    public Tree<TreeNode, String> getRecPrepPreviewTree() {
        return recPrepPreviewTree;
    }

    public void setRecPrepPreviewTree(Tree<TreeNode, String> recPrepPreviewTree) {
        this.recPrepPreviewTree = recPrepPreviewTree;
    }

    public Tree<TreeNode, String> getRepeatableCreditPreviewTree() {
        return repeatableCreditPreviewTree;
    }

    public void setRepeatableCreditPreviewTree(Tree<TreeNode, String> repeatableCreditPreviewTree) {
        this.repeatableCreditPreviewTree = repeatableCreditPreviewTree;
    }

    public Tree<TreeNode, String> getRestrictCreditPreviewTree() {
        return restrictCreditPreviewTree;
    }

    public void setRestrictCreditPreviewTree(Tree<TreeNode, String> restrictCreditPreviewTree) {
        this.restrictCreditPreviewTree = restrictCreditPreviewTree;
    }

    public String getTermCode(){
        return termCode;
    }

    public void setTermCode(String termCode){
        this.termCode = termCode;
    }

    public TermInfo getTermInfo(){
        return termInfo;    
    }
    
    public void setTermInfo(TermInfo termInfo){
        this.termInfo = termInfo;
    }
    
    public String getCourseOfferingCode(){
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode){
        this.courseOfferingCode = courseOfferingCode;
    }
    
    public String getSubjectCode(){
        return subjectCode;
    }
    
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    
    public String getRadioSelection(){
        return radioSelection;
    } 
    
    public void setRadioSelection(String radioSelection){
        this.radioSelection = radioSelection;
    }
    
    public String getInputCode(){
        return inputCode;
    }
    
    public void setInputCode(String inputCode){
        this.inputCode = inputCode;
    }

    public String getSelectedOfferingAction() {
        return selectedOfferingAction;
    }

    public void setSelectedOfferingAction(String selectedOfferingAction) {
        this.selectedOfferingAction = selectedOfferingAction;
    }

    public CourseOfferingInfo getTheCourseOffering(){
        return theCourseOffering;
    }

    public void setTheCourseOffering(CourseOfferingInfo theCourseOffering)  {
        this.theCourseOffering = theCourseOffering;
    }

    public String getNoOfActivityOfferings() {
        return noOfActivityOfferings;
    }

    public void setNoOfActivityOfferings(String noOfActivityOfferings) {
        this.noOfActivityOfferings = noOfActivityOfferings;
    }

    public String getFormatIdForNewAO() {
        return formatIdForNewAO;
    }

    public void setFormatIdForNewAO(String formatIdForNewAO) {
        this.formatIdForNewAO = formatIdForNewAO;
    }

    public String getActivityIdForNewAO() {
        return activityIdForNewAO;
    }

    public void setActivityIdForNewAO(String activityIdForNewAO) {
        this.activityIdForNewAO = activityIdForNewAO;
    }

    public String getCoViewLinkWrapper() {
        return coViewLinkWrapper;
    }

    public void setCoViewLinkWrapper(String coViewLinkWrapper) {
        this.coViewLinkWrapper = coViewLinkWrapper;
    }

    public String getPreviousCourseOfferingCodeUI() {
        return previousCourseOfferingCodeUI;
    }

    public void setPreviousCourseOfferingCodeUI(String previousCourseOfferingCodeUI) {
        this.previousCourseOfferingCodeUI = previousCourseOfferingCodeUI;
    }

    public String getNextCourseOfferingCodeUI() {
        return nextCourseOfferingCodeUI;
    }

    public void setNextCourseOfferingCodeUI(String nextCourseOfferingCodeUI) {
        this.nextCourseOfferingCodeUI = nextCourseOfferingCodeUI;
    }

    public CourseOfferingInfo getPreviousCourseOffering() {
        return previousCourseOffering;
    }

    public void setPreviousCourseOffering(CourseOfferingInfo previousCourseOffering) {
        this.previousCourseOffering = previousCourseOffering;
        if (previousCourseOffering != null){
            setPreviousCourseOfferingCodeUI(previousCourseOffering.getCourseOfferingCode());
        }else{
            setPreviousCourseOfferingCodeUI(StringUtils.EMPTY);
        }
    }

    public CourseOfferingInfo getNextCourseOffering() {
        return nextCourseOffering;
    }

    public String getSubjectCodeDescription() {
        return subjectCodeDescription;
    }

    public void setSubjectCodeDescription(String subjectCodeDescription) {
        this.subjectCodeDescription = subjectCodeDescription;
    }

    public void setNextCourseOffering(CourseOfferingInfo nextCourseOffering) {
        this.nextCourseOffering = nextCourseOffering;
        if (nextCourseOffering != null){
            setNextCourseOfferingCodeUI(nextCourseOffering.getCourseOfferingCode());
        }else{
            setNextCourseOfferingCodeUI(StringUtils.EMPTY);
        }
    }

    public String getToBeScheduledCourseOfferingsUI() {
        return toBeScheduledCourseOfferingsUI;
    }

    public void setToBeScheduledCourseOfferingsUI(String toBeScheduledCourseOfferingsUI) {
        this.toBeScheduledCourseOfferingsUI = toBeScheduledCourseOfferingsUI;
    }

    public int getToBeScheduledCourseOfferingsCount() {
        return toBeScheduledCourseOfferingsCount;
    }

    public void setToBeScheduledCourseOfferingsCount(int toBeScheduledCourseOfferingsCount) {
        this.toBeScheduledCourseOfferingsCount = toBeScheduledCourseOfferingsCount;
    }

    public boolean isSelectedIllegalAOInDeletion() {
        return selectedIllegalAOInDeletion;
    }

    public void setSelectedIllegalAOInDeletion(boolean selectedIllegalAOInDeletion) {
        this.selectedIllegalAOInDeletion = selectedIllegalAOInDeletion;
    }

    public boolean isWithinPortal() {
        return withinPortal;
    }

    public void setWithinPortal(boolean withinPortal) {
        this.withinPortal = withinPortal;
    }

    public String getAdminOrg() {
        return adminOrg;
    }

    public void setAdminOrg(String adminOrg) {
        this.adminOrg = adminOrg;
    }
    
    public List<CourseOfferingListSectionWrapper> getCourseOfferingResultList() {
        return courseOfferingResultList;
    }
    public void setCourseOfferingResultList(List<CourseOfferingListSectionWrapper> courseOfferingResultList) {
        this.courseOfferingResultList = courseOfferingResultList;
    }
    public void clearCourseOfferingResultList(){
        this.courseOfferingResultList = courseOfferingResultListEmpty;
    }

    public CourseOfferingEditWrapper getCourseOfferingEditWrapper() {
        return courseOfferingEditWrapper;
    }

    public void setCourseOfferingEditWrapper(CourseOfferingEditWrapper courseOfferingEditWrapper) {
        this.courseOfferingEditWrapper = courseOfferingEditWrapper;
    }
    public boolean getEditAuthz(){
        return editAuthz;
    }
    public void setEditAuthz(boolean editAuthz){
        this.editAuthz=editAuthz;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isEnableAddButton() {
        return enableAddButton;
    }

    public void setEnableAddButton(boolean enableAddButton) {
        this.enableAddButton = enableAddButton;
    }

    public boolean isCrossListedCo() {
        return crossListedCo;
    }

    public void setCrossListedCo(boolean crossListedCo) {
        this.crossListedCo = crossListedCo;
    }

    public String getCrossListedCoCodes() {
        return crossListedCoCodes;
    }

    public void setCrossListedCoCodes(String crossListedCoCodes) {
        this.crossListedCoCodes = crossListedCoCodes;
    }
}
