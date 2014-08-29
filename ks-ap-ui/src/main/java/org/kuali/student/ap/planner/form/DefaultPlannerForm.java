package org.kuali.student.ap.planner.form;

import org.kuali.rice.krad.web.bind.RequestAccessible;
import org.kuali.student.ap.academicplan.constants.AcademicPlanServiceConstants;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.infc.PlanItem;
import org.kuali.student.ap.coursesearch.CreditsFormatter;
import org.kuali.student.ap.coursesearch.CreditsFormatter.Range;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.planner.PlannerForm;
import org.kuali.student.ap.planner.PlannerItem;
import org.kuali.student.ap.planner.PlannerTerm;
import org.kuali.student.ap.planner.PlannerTermNote;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.comment.dto.CommentInfo;
import org.kuali.student.r2.core.comment.service.CommentService;
import org.kuali.student.r2.lum.course.infc.Course;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

/**
 * KSAP planner form.
 *
 * <p>
 * Refactored from original PlanController and PlanForm, for improved stability,
 * performance, and maintainability.
 * </p>
 *
 * @author Mark Fyffe
 * @version 0.7
 */
public class DefaultPlannerForm extends AbstractPlanItemForm implements
        PlannerForm {

    private static final long serialVersionUID = -7205838511702504159L;

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    @RequestAccessible
    private String courseCd;
    @RequestAccessible
    private BigDecimal courseCredit;
    @RequestAccessible
    private String courseNote;
    @RequestAccessible
    private String termNote;
    @RequestAccessible
    private boolean backup;
    //used to indicated if the course has been bookmarked in KSAP
    //Note: because when bookmarked is true, we need to display
    //      the checkbox of keepBookmarked and leave it as unchecked by default,
    //      we have to define two separate fields
    @RequestAccessible
    private boolean bookmarked;
    //used to indicated if the course has been planned already in KSAP
    //Note: because when it is planned already, we need to display
    //      a msg indicating it on the bookmark add to plan dialog
    @RequestAccessible
    private String plannedMessage;

    //used to hold the checkbox value in "Add to Plan" popover form
    @RequestAccessible
    private boolean keepBookmarked;

    @RequestAccessible
    private AcademicPlanServiceConstants.ItemCategory expectedPlanItemCategory;
    @RequestAccessible
    private AcademicPlanServiceConstants.ItemCategory targetPlanItemCategory;

    @RequestAccessible
    private String targetTermId;

    @RequestAccessible
    private int focusTermIndex;

    @RequestAccessible
    private transient boolean termNoteInitialized;
    @RequestAccessible
    private transient BigDecimal creditsForPlanItem;
    private transient List<PlannerTerm> terms;
    @RequestAccessible
    private transient String creditString;
    @RequestAccessible
    private List<String> plannedTermIds;

    @Override
    public void setLearningPlanId(String learningPlanId) {
        super.setLearningPlanId(learningPlanId);
        this.creditsForPlanItem = null;
    }

    @Override
    public void setPlanItemId(String planItemId) {
        super.setPlanItemId(planItemId);
        this.creditsForPlanItem = null;
    }

    public boolean isBackup() {
        return backup;
    }

    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    public boolean isBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean isKeepBookmarked() {
        return keepBookmarked;
    }

    public void setKeepBookmarked(boolean keepBookmarked) {
        this.keepBookmarked = keepBookmarked;
    }

    @Override
    public void setCourseId(String courseId) {
        super.setCourseId(courseId);
        this.creditString = null;
    }

    public String getCourseCd() {
        return courseCd;
    }

    public void setCourseCd(String courseCd) {
        this.courseCd = courseCd;
    }

    public BigDecimal getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(BigDecimal courseCredit) {
        this.courseCredit = courseCredit;
        this.creditsForPlanItem = null;
    }

    public String getCreditString() {
        return creditString == null ? creditString = courseCredit == null ? CreditsFormatter
                .formatCredits(getCourse()) : CreditsFormatter
                .trimCredits(courseCredit.toString())
                : creditString;
    }

    public BigDecimal getCreditsForPlanItem() {
        return getCreditsForPlanItem(getCourse());
    }
    public BigDecimal getCreditsForPlanItem(Course course) {
        if (creditsForPlanItem == null && courseCredit != null) {
            BigDecimal minCredit = BigDecimal.ZERO;
            BigDecimal maxCredit = ONE_HUNDRED;
            ResultValuesGroupInfo rci;
            try{
                rci = KSCollectionUtils.getRequiredZeroElement(course.getCreditOptions());
            }catch (OperationFailedException e){
                throw new RuntimeException("Invalid Credit Options", e);
            }
            String type = rci.getTypeKey();
            if (type.equals("kuali.result.values.group.type.fixed")) {
                boolean useAttributes = rci.getResultValueKeys().isEmpty();
                if (!useAttributes)
                    try {
                        ResultValueInfo rv = KsapFrameworkServiceLocator
                                .getLrcService().getResultValue(
                                        KSCollectionUtils.getRequiredZeroElement(rci.getResultValueKeys()),
                                        KsapFrameworkServiceLocator
                                                .getContext().getContextInfo());
                        if (rv == null)
                            useAttributes = true;
                        else
                            minCredit = maxCredit = new BigDecimal(
                                    rv.getValue());
                    } catch (DoesNotExistException e) {
                        throw new IllegalArgumentException("LRC lookup error",
                                e);
                    } catch (InvalidParameterException e) {
                        throw new IllegalArgumentException("LRC lookup error",
                                e);
                    } catch (MissingParameterException e) {
                        throw new IllegalArgumentException("LRC lookup error",
                                e);
                    } catch (OperationFailedException e) {
                        throw new IllegalStateException("LRC lookup error", e);
                    } catch (PermissionDeniedException e) {
                        throw new IllegalStateException("LRC lookup error", e);
                    }
                if (useAttributes)
                    minCredit = maxCredit = new BigDecimal(
                            rci.getAttributeValue("fixedCreditValue"));
            } else if (type.equals("kuali.result.values.group.type.range")) {
                ResultValueRangeInfo rvr = rci.getResultValueRange();
                if (rvr != null) {
                    minCredit = new BigDecimal(rvr.getMinValue());
                    maxCredit = new BigDecimal(rvr.getMaxValue());
                } else {
                    minCredit = new BigDecimal(
                            rci.getAttributeValue("minCreditValue"));
                    maxCredit = new BigDecimal(
                            rci.getAttributeValue("maxCreditValue"));
                }
            }

            if (courseCredit.compareTo(maxCredit) > 0)
                creditsForPlanItem = maxCredit;
            else if (courseCredit.compareTo(minCredit) < 0)
                creditsForPlanItem = minCredit;
            else
                creditsForPlanItem = courseCredit;

        }
        return creditsForPlanItem;
    }

    public String getCourseNote() {
        return courseNote;
    }

    public void setCourseNote(String courseNote) {
        this.courseNote = courseNote;
    }

    public String getTermNote() {
        String termId;
        if (termNote == null && !termNoteInitialized
                && (termId = getTermId()) != null) {
            CommentService commentService = KsapFrameworkServiceLocator
                    .getCommentService();
            List<CommentInfo> commentInfos;
            try {
                commentInfos = commentService.getCommentsByRefObject(
                        getLearningPlan().getId(),
                        PlanConstants.TERM_NOTE_COMMENT_TYPE,
                        KsapFrameworkServiceLocator.getContext()
                                .getContextInfo());
            } catch (DoesNotExistException e) {
                throw new IllegalArgumentException("Comment lookup failure", e);
            } catch (InvalidParameterException e) {
                throw new IllegalArgumentException("Comment lookup failure", e);
            } catch (MissingParameterException e) {
                throw new IllegalArgumentException("Comment lookup failure", e);
            } catch (OperationFailedException e) {
                throw new IllegalStateException("Comment lookup failure", e);
            } catch (PermissionDeniedException e) {
                throw new IllegalStateException("Comment lookup failure", e);
            }

            for (CommentInfo comment : commentInfos) {
                String commentAtpId = comment
                        .getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
                if (termId.equals(commentAtpId)) {
                    RichTextInfo text = comment.getCommentText();
                    if (text != null)
                        termNote = text.getPlain();
                }
            }

            termNoteInitialized = true;
        }

        return termNote;
    }

    public void setTermNote(String termNote) {
        this.termNote = termNote;
        termNoteInitialized = true;
    }

    public AcademicPlanServiceConstants.ItemCategory getExpectedPlanItemCategory() {
        return expectedPlanItemCategory;
    }

    public void setExpectedPlanItemCategory(AcademicPlanServiceConstants.ItemCategory category) {
        this.expectedPlanItemCategory = category;
    }

    public AcademicPlanServiceConstants.ItemCategory getTargetPlanItemCategory() {
        return targetPlanItemCategory;
    }

    public void setTargetPlanItemCategory(AcademicPlanServiceConstants.ItemCategory targetPlanItemCategory) {
        this.targetPlanItemCategory = targetPlanItemCategory;
    }

    public String getTargetTermId() {
        return targetTermId;
    }

    public void setTargetTermId(String targetTermId) {
        this.targetTermId = targetTermId;
    }

    public int getFocusTermIndex() {
        return focusTermIndex;
    }

    public void setFocusTermIndex(int focusTermIndex) {
        this.focusTermIndex = focusTermIndex;
    }

    public String getPlannedMessage() {
        return this.plannedMessage;
    }

    public void setPlannedMessage(String plannedMessage) {
        this.plannedMessage = plannedMessage;
    }

    @Override
    public void populateFromPlanItem() {
        PlanItem planItem = getPlanItem();
        setCourseId(planItem.getRefObjectId());

        Course course = getCourse();
        assert course != null;

        setCourseCd(course.getCode());
        setBackup(planItem.getCategory().equals(
                AcademicPlanServiceConstants.ItemCategory.BACKUP));

        RichText descr = planItem.getDescr();
        setCourseNote(descr != null && StringUtils.hasText(descr.getPlain()) ? descr
                .getPlain() : null);

        setCourseCredit(planItem.getCredits());
    }

    public List<PlannerTerm> getTerms() {
        if (terms == null) {

            LearningPlan learningPlan = getLearningPlan();

            Map<String, List<PlannerItem>> planned = new HashMap<String, List<PlannerItem>>();
            Map<String, List<PlannerItem>> backup = new HashMap<String, List<PlannerItem>>();
            Map<String, List<PlannerItem>> cart = new HashMap<String, List<PlannerItem>>();
            Map<String, List<PlannerItem>> completed = new HashMap<String, List<PlannerItem>>();
            Map<String, List<PlannerTermNote>> termNotes = new HashMap<String, List<PlannerTermNote>>();

            List<StudentCourseRecordInfo> completedRecords = retrieveCourseRecords(learningPlan.getStudentId());

            List<PlanItem> planItems = retrieveCoursePlanItems(learningPlan.getId());

            List<String> courseIds = new ArrayList<String>(
                    completedRecords.size() + planItems.size());
            SortedSet<String> termIds = new TreeSet<String>();

            Iterator<PlanItem> planItemIterator = planItems.iterator();
            while (planItemIterator.hasNext()) {
                PlanItem planItem = planItemIterator.next();
                List<String> planTermIds = planItem.getPlanTermIds();
                termIds.addAll(planTermIds);
                courseIds.add(KsapFrameworkServiceLocator.getCourseHelper().getCurrentVersionOfCourseByVersionIndependentId(planItem.getRefObjectId()).getId());
            }

            if (completedRecords != null)
                for (StudentCourseRecordInfo completedRecord : completedRecords) {
                    String termId =completedRecord.getTermId();
                    termIds.add(termId);
                    List<PlannerItem> itemList = completed.get(termId);
                    if (itemList == null)
                        completed.put(termId,itemList = new LinkedList<PlannerItem>());
                    itemList.add(KsapFrameworkServiceLocator.getPlanHelper().createPlannerItem(completedRecord));
                }

            CourseHelper courseHelper = KsapFrameworkServiceLocator
                    .getCourseHelper();
            courseHelper.frontLoad(courseIds);

            for (PlanItem planItem : planItems) {
                AcademicPlanServiceConstants.ItemCategory category = planItem.getCategory();

                for (String termId : planItem.getPlanTermIds()) {
                    Map<String, List<PlannerItem>> itemMap;
                    if (AcademicPlanServiceConstants.ItemCategory.CART
                            .equals(category))
                        itemMap = cart;
                    else if (AcademicPlanServiceConstants.ItemCategory.PLANNED
                            .equals(category))
                        itemMap = planned;
                    else if (AcademicPlanServiceConstants.ItemCategory.BACKUP
                            .equals(category))
                        itemMap = backup;
                    else
                        throw new UnsupportedOperationException(
                                "Plan item category " + category);

                    List<PlannerItem> itemList = itemMap.get(termId);
                    if (itemList == null)
                        itemMap.put(termId,
                                itemList = new LinkedList<PlannerItem>());

                    itemList.add(KsapFrameworkServiceLocator.getPlanHelper().createPlannerItem(planItem));
                }
            }

            TermHelper termHelper = KsapFrameworkServiceLocator.getTermHelper();
            List<Term> tempTerms = new ArrayList<Term>();
            for(String tempId : termIds){
                tempTerms.add(termHelper.getTerm(tempId));
            }
            String firstTermId;
            if(termIds.isEmpty()){
                firstTermId = termHelper.getCurrentTerm().getId();
            }else{
                firstTermId = termIds.first();
            }
            Term firstTerm;
            if(tempTerms.size()>0){
                tempTerms = termHelper.sortTermsByStartDate(tempTerms,true);
                firstTerm = tempTerms.get(0);
                firstTermId = firstTerm.getId();
            }else{
                firstTerm = termHelper.getTerm(firstTermId);
            }
            termHelper.frontLoadForPlanner(firstTermId);

            List<Term> calendarTerms = KsapFrameworkServiceLocator.getPlanHelper().getPlannerCalendarTerms(firstTerm);
            String focusTermId = KsapFrameworkServiceLocator.getPlanHelper().getPlannerFirstTermId();

            Calendar cal = Calendar.getInstance();
            cal.setTime(firstTerm.getStartDate());

            List<PlannerTermNote> notes = retrieveTermNotes(learningPlan.getId());
            termNotes = createTermTermNoteMap(notes);

            List<PlannerTerm> pterms = new ArrayList<PlannerTerm>(
                    termIds.size());
            for(Term t : calendarTerms){
                String termId = t.getId();
                PlannerTerm pterm = new PlannerTerm(termId);
                pterm.setUniqueId(UUID.randomUUID().toString());

                List<PlannerItem> completedList = completed.get(termId);
                pterm.setAcademicRecord(completedList == null ? completedList = new ArrayList<PlannerItem>()
                        : completedList);
                for (PlannerItem item : completedList)
                    item.setParentUniqueId(pterm.getUniqueId());

                List<PlannerItem> cartList = cart.get(termId);
                pterm.setCartList(cartList == null ? cartList = new ArrayList<PlannerItem>()
                        : cartList);
                for (PlannerItem item : cartList)
                    item.setParentUniqueId(pterm.getUniqueId());

                List<PlannerItem> plannedList = planned.get(termId);
                pterm.setPlannedList(plannedList == null ? plannedList = new ArrayList<PlannerItem>()
                        : plannedList);
                for (PlannerItem item : plannedList)
                    item.setParentUniqueId(pterm.getUniqueId());

                List<PlannerItem> backupList = backup.get(termId);
                pterm.setBackupList(backupList == null ? backupList = new ArrayList<PlannerItem>()
                        : backupList);
                for (PlannerItem item : backupList)
                    item.setParentUniqueId(pterm.getUniqueId());

                List<PlannerTermNote> termNotesList = termNotes.get(termId);
                pterm.setTermNoteList(termNotesList == null ? termNotesList = new ArrayList<PlannerTermNote>()
                        : termNotesList);
                for (PlannerTermNote termNote : termNotesList)
                    termNote.setParentUniqueId(pterm.getUniqueId());

                if (termId.equals(focusTermId)) {
                    focusTermIndex = pterms.size();
                }

                pterms.add(pterm);
            }

            terms = pterms;
            Collections.sort(terms);
        }

        return terms;
    }

    public boolean isVariableCredit() {
        Range range = CreditsFormatter.getRange(getCourse());
        if(range.getMultiple()!=null && !range.getMultiple().isEmpty()) return true;
        return !range.getMax().equals(range.getMin());
    }

    @Override
    public List<String> getPlannedTermIds() {
        return plannedTermIds;
    }

    @Override
    public void setPlannedTermIds(List<String> plannedTermIds) {
        this.plannedTermIds = plannedTermIds;
    }

    /**
     * Retrieves records of completed courses
     * Need to look into how Registerd records are retrieved
     *
     * @param studentId - Id of student records are being retrieved for
     * @return List of Course Records student is enrolled for
     */
    private List<StudentCourseRecordInfo> retrieveCourseRecords(String studentId){
        List<StudentCourseRecordInfo> completedRecords;
        try {
            completedRecords = KsapFrameworkServiceLocator.getAcademicRecordService().getCompletedCourseRecords(
                    studentId,KsapFrameworkServiceLocator.getContext().getContextInfo());
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

        return completedRecords;
    }

    private List<PlanItem> retrieveCoursePlanItems(String planId){
        List<PlanItem> itemsToReturn = new ArrayList<PlanItem>();
        List<PlanItem> planItems = KsapFrameworkServiceLocator.getPlanHelper().getPlanItems(planId);

        for(PlanItem item : planItems){
            if(!item.getRefObjectType().equals(PlanConstants.COURSE_TYPE)){
                continue;
            }
            if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.WISHLIST)){
                continue;
            }
            itemsToReturn.add(item);
        }

        return itemsToReturn;
    }

    private Map<String, List<PlannerItem>> createTermPlannerItemMap(List<PlannerItem> items){
        Map<String, List<PlannerItem>> itemMap = new TreeMap<String, List<PlannerItem>>() {
            @Override
            public Comparator<? super String> comparator() {
                return new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o1);
                        Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o2);
                        return t1.getStartDate().compareTo(t2.getStartDate());
                    }
                };
            }
        };

        for(PlannerItem item : items){
            List<PlannerItem> existingItems;
            if(itemMap.containsKey(item.getTermId())){
                existingItems = itemMap.get(item.getTermId());
            }
            else{
                existingItems = new ArrayList<PlannerItem>();
            }
            existingItems.add(item);
            itemMap.put(item.getTermId(),existingItems);
        }

        return itemMap;
    }

    private List<PlannerTermNote> retrieveTermNotes(String planId){
        List<PlannerTermNote> termNotes = new ArrayList<PlannerTermNote>();
        CommentService commentService = KsapFrameworkServiceLocator.getCommentService();
        List<CommentInfo> commentInfos;
        try {
            commentInfos = commentService.getCommentsByRefObject(planId,PlanConstants.TERM_NOTE_COMMENT_TYPE,
                    KsapFrameworkServiceLocator.getContext().getContextInfo());
        } catch (DoesNotExistException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (InvalidParameterException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (MissingParameterException e) {
            throw new IllegalArgumentException("Comment lookup failure", e);
        } catch (OperationFailedException e) {
            throw new IllegalStateException("Comment lookup failure", e);
        } catch (PermissionDeniedException e) {
            throw new IllegalStateException("Comment lookup failure", e);
        }

        for (CommentInfo comment : commentInfos) {
            PlannerTermNote newTermNote = new PlannerTermNote();
            String termId = comment.getAttributeValue(PlanConstants.TERM_NOTE_COMMENT_ATTRIBUTE_ATPID);
            newTermNote.setUniqueId(termId.replace('.', '-'));
            newTermNote.setTermId(termId);
            newTermNote.setDate(comment.getEffectiveDate());
            newTermNote.setTermNote(comment.getCommentText().getFormatted());
            termNotes.add(newTermNote);
        }

        return termNotes;
    }

    private Map<String, List<PlannerTermNote>> createTermTermNoteMap(List<PlannerTermNote> items){
        Map<String, List<PlannerTermNote>> itemMap = new TreeMap<String, List<PlannerTermNote>>() {
            @Override
            public Comparator<? super String> comparator() {
                return new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o1);
                        Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(o2);
                        return t1.getStartDate().compareTo(t2.getStartDate());
                    }
                };
            }
        };

        for(PlannerTermNote item : items){
            List<PlannerTermNote> existingItems;
            if(itemMap.containsKey(item.getTermId())){
                existingItems = itemMap.get(item.getTermId());
            }
            else{
                existingItems = new ArrayList<PlannerTermNote>();
            }
            existingItems.add(item);
            itemMap.put(item.getTermId(),existingItems);
        }

        return itemMap;
    }

    private String findFirstTermForStudent(Map<String,List<PlannerTerm>> items, Map<String,List<PlannerTermNote>> notes){
        if(items.isEmpty() && notes.isEmpty()){
            return KsapFrameworkServiceLocator.getTermHelper().getCurrentTerm().getId();
        }
        if(items.isEmpty()){
            return notes.keySet().iterator().next();
        }
        if(notes.isEmpty()){
            return items.keySet().iterator().next();
        }
        String earliestItemTerm = items.keySet().iterator().next();
        String earliestNoteTerm = notes.keySet().iterator().next();
        Term t1 = KsapFrameworkServiceLocator.getTermHelper().getTerm(earliestItemTerm);
        Term t2 = KsapFrameworkServiceLocator.getTermHelper().getTerm(earliestNoteTerm);
        int compare = t1.getStartDate().compareTo(t2.getStartDate());
        if(compare<0) return earliestNoteTerm;
        return earliestItemTerm;
    }

    private List<PlannerTerm> createPlannerTerms(List<Term> terms, Map<String,List<PlannerItem>> itemsMap, Map<String,List<PlannerTermNote>> notesMap){
        List<PlannerTerm> plannerTerms = new ArrayList<PlannerTerm>();

        for(Term term : terms){
            PlannerTerm plannerTerm = new PlannerTerm(term.getId());
            plannerTerm = fillPlannerTerm(plannerTerm,itemsMap.get(term.getId()),notesMap.get(term.getId()));
            plannerTerms.add(plannerTerm);
        }

        return plannerTerms;
    }

    private PlannerTerm fillPlannerTerm(PlannerTerm term, List<PlannerItem> items, List<PlannerTermNote> notes){
        term.setTermNoteList(notes);

        List<PlannerItem> completed = new ArrayList<PlannerItem>();
        List<PlannerItem> registered = new ArrayList<PlannerItem>();
        List<PlannerItem> planned = new ArrayList<PlannerItem>();
        List<PlannerItem> backup = new ArrayList<PlannerItem>();

        for(PlannerItem item : items){
            if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.PLANNED)){
                planned.add(item);
            }else if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.BACKUP)){
                backup.add(item);
            }else if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.COMPLETE)){
                completed.add(item);
            }else if(item.getCategory().equals(AcademicPlanServiceConstants.ItemCategory.REGISTERED)){
                registered.add(item);
            }
        }

        // Add Add to Course Item
        if(term.isPlanning()){
            PlannerItem addPlanned = new PlannerItem();
            addPlanned.setType(PlannerItem.ADD_ITEM);
            PlannerItem addBackup = new PlannerItem();
            addBackup.setType(PlannerItem.ADD_ITEM);
            planned.add(addPlanned);
            backup.add(addBackup);
        }

        term.setAcademicRecord(completed);
        term.setRegistrationList(registered);
        term.setPlannedList(planned);
        term.setBackupList(backup);

        term = calculateCreditLineForPlannerTerm(term);
        term = padCategory(term);

        return term;
    }

    private PlannerTerm padCategory(PlannerTerm term){
        int numberToAdd = 6;
        if(term.isCompleted()){
            numberToAdd = numberToAdd - term.getAcademicRecord().size();
            term.setAcademicRecord(padCategory(term.getAcademicRecord(),numberToAdd));
        } else if(term.isFutureTerm()){
            if(term.isRegistrationOpen()){
                if(term.getRegistrationList().isEmpty()){
                    numberToAdd = numberToAdd - term.getPlannedList().size();
                    term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
                } else{
                    numberToAdd = numberToAdd - term.getPlannedList().size() - term.getRegistrationList().size()-1;
                    term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
                }
            } else{
                numberToAdd = numberToAdd - term.getPlannedList().size();
                term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
            }
        } else if(term.isInProgress()){
            if(term.isRegistrationOpen()){
                if(term.getRegistrationList().isEmpty()){
                    numberToAdd = numberToAdd - term.getPlannedList().size();
                    term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
                } else{
                    numberToAdd = numberToAdd - term.getPlannedList().size() - term.getRegistrationList().size()-1;
                    term.setPlannedList(padCategory(term.getPlannedList(),numberToAdd));
                }
            } else{
                numberToAdd = numberToAdd - term.getRegistrationList().size();
                term.setRegistrationList(padCategory(term.getRegistrationList(),numberToAdd));
            }
        }
        return term;
    }

    private List<PlannerItem> padCategory(List<PlannerItem> category, int numberToAdd){
        for(int i = 0; i<numberToAdd; i++){
            PlannerItem blank = new PlannerItem();
            category.add(blank);
        }
        return category;
    }


    private PlannerTerm calculateCreditLineForPlannerTerm(PlannerTerm term){
        List<PlannerItem> itemsToTotal = new ArrayList<PlannerItem>();

        if(term.isCompleted()){
            itemsToTotal.addAll(term.getAcademicRecord());
        }else if(term.isFutureTerm()){
            itemsToTotal.addAll(term.getPlannedList());
            if(term.isRegistrationOpen()){
                itemsToTotal.addAll(term.getRegistrationList());
            }
        }else if(term.isInProgress()){
            itemsToTotal.addAll(term.getRegistrationList());
            if(term.isRegistrationOpen()){
                itemsToTotal.addAll(term.getPlannedList());
            }
        }

        return calculateCreditLineForPlannerTerm(term,itemsToTotal);

    }

    private PlannerTerm calculateCreditLineForPlannerTerm(PlannerTerm term, List<PlannerItem> items){
        BigDecimal minCredits = BigDecimal.ZERO;
        BigDecimal maxCredits = BigDecimal.ZERO;
        for(PlannerItem item : items){
            minCredits.add(item.getMinCredits());
            maxCredits.add(item.getMaxCredits());
        }

        term.setCreditLineMinCredits(minCredits);
        term.setCreditLineMaxCredits(maxCredits);

        return term;

    }
}
