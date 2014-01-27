package org.kuali.student.enrollment.rules.credit.limit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.type.KualiDecimal;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.enrollment.courseregistration.service.CourseRegistrationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseRegistrationServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
 */
public class RegistrationRequestMergerImpl implements RegistrationRequestMerger {

    //
    // configuration
    //
    private CourseRegistrationService courseRegistrationService;

    public CourseRegistrationService getCourseRegistrationService() {
        if (courseRegistrationService == null) {
            QName qname = new QName(CourseRegistrationServiceConstants.NAMESPACE,
                    CourseRegistrationServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseRegistrationService = GlobalResourceLoader.getService(qname);
        }
        return courseRegistrationService;
    }

    public void setCourseRegistrationService(CourseRegistrationService courseRegistrationService) {
        this.courseRegistrationService = courseRegistrationService;
    }
    private CourseOfferingService courseOfferingService;

    public RegistrationRequestMergerImpl(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            QName qname = new QName(CourseOfferingServiceConstants.NAMESPACE,
                    CourseOfferingServiceConstants.SERVICE_NAME_LOCAL_PART);
            courseOfferingService = GlobalResourceLoader.getService(qname);
        }
        return courseOfferingService;
    }

    public void setCourseOfferingService(CourseOfferingService courseOfferingService) {
        this.courseOfferingService = courseOfferingService;
    }

    public RegistrationRequestMergerImpl() {
    }

    // 
    // main logic
    //
    @Override
    public List<CourseRegistrationTransaction> merge(RegistrationRequestInfo request,
            List<CourseRegistrationInfo> registrations,
            boolean skipActivities,
            ContextInfo contextInfo)
            throws OperationFailedException {
        List<CourseRegistrationTransaction> list = new ArrayList<CourseRegistrationTransaction>();
        // load the existing registrations initially as no change so they can be modified to be drops etc.
        for (CourseRegistrationInfo reg : registrations) {
            CourseRegistrationTransaction rt = this.createExistingRegistrationTransaction(reg, contextInfo);
            list.add(rt);
        }

        // now process each request item
        for (RegistrationRequestItemInfo item : request.getRegistrationRequestItems()) {
            // TODO: change these if statemens to a SWITCH statement once we move to java 7
            // Adds
            if (item.getTypeKey().equals(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_ADD_TYPE_KEY)) {
                CourseRegistrationTransaction rt = this.createNewCourseRegistrationTransaction(item, skipActivities, contextInfo);
                list.add(rt);
                continue;
            }
            // drops
            if (item.getTypeKey().equals(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_DROP_TYPE_KEY)) {
                CourseRegistrationTransaction rt = this.findMatchingActiveCourseRegistration(item, contextInfo, list);
                if (rt == null) {
                    throw new OperationFailedException("Cannot drop non-existent or non-active course registration");
                }
                markCourseRegistrationTransactionAsDropped(rt, item, contextInfo);
                continue;
            }
            // just updating data bits 
            if (item.getTypeKey().equals(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_UPDATE_TYPE_KEY)) {
                CourseRegistrationTransaction rt = this.findMatchingActiveCourseRegistration(item, contextInfo, list);
                if (rt == null) {
                    throw new OperationFailedException("Cannot update a non-existent or non-active course registration");
                }
                updateCourseRegistrationTransaction(rt, item, contextInfo);
                continue;
            }
            // swap
            if (item.getTypeKey().equals(CourseRegistrationServiceTypeStateConstants.REQ_ITEM_SWAP_TYPE_KEY)) {
                CourseRegistrationTransaction rt1 = this.findMatchingActiveCourseRegistration(item, contextInfo, list);
                if (rt1 == null) {
                    throw new OperationFailedException("Cannot swap non-existent or non-active course registration");
                }
                // if trying to use swap to swap course A for course B
                if (!areRegGroupsForSameCourseFormat(rt1, item, contextInfo)) {
                    throw new OperationFailedException("you cannot use the swap to change courses use drop and add instead");
                }
                // if swapping sections they could also be changing the data bits on the CR
                updateCourseRegistrationTransaction(rt1, item, contextInfo);
                // TODO: actually swap the sections (activity offerings)
            }
        }
        return list;
    }

    private boolean areRegGroupsForSameCourseFormat(CourseRegistrationTransaction rt, RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        RegistrationGroupInfo rg1 = this.getRegGroup(item.getRegistrationGroupId(), contextInfo);
        RegistrationGroupInfo rg2 = this.getRegGroup(rt.getRegistration().getRegistrationGroupId(), contextInfo);
        //TODO: decide if swap should be ok between format offerings but just not course offerings 
        if (rg1.getFormatOfferingId().equals(rg2.getFormatOfferingId())) {
            return true;
        }
        return false;
    }

    private CourseRegistrationTransaction findMatchingActiveCourseRegistration(RegistrationRequestItemInfo item,
            ContextInfo contextInfo, List<CourseRegistrationTransaction> rts)
            throws OperationFailedException {
        for (CourseRegistrationTransaction rt : rts) {
            CourseRegistrationInfo reg = rt.getRegistration();
            if (!reg.getId().equals(item.getExistingCourseRegistrationId())) {
                continue;
            }
            if (!isActive(reg)) {
                continue;
            }
            return rt;
        }
        return null;
    }

    /**
     * protected so can be overridden by implementing institution if what they consider "ACTIVE" is different
     *
     * @param reg
     * @return
     */
    protected boolean isActive(CourseRegistrationInfo reg) {
        return this.isActive(reg.getStateKey());
    }

    /**
     * protected so can be overridden by implementing institution if what they consider "ACTIVE" is different
     *
     * @param stateKey
     * @return
     */
    protected boolean isActive(String stateKey) {
        if (stateKey.equals(LprServiceConstants.REGISTERED_STATE_KEY)) {
            return true;
        }
        return false;
    }

    @Override
    public List<CourseRegistrationInfo> simulate(RegistrationRequestInfo request,
            List<CourseRegistrationInfo> registrations,
            boolean skipActivities,
            ContextInfo contextInfo) throws OperationFailedException {
        List<CourseRegistrationTransaction> transactions = this.merge(request, registrations, skipActivities, contextInfo);
        List<CourseRegistrationInfo> infos = new ArrayList<CourseRegistrationInfo>(transactions.size());
        for (CourseRegistrationTransaction tran : transactions) {
            switch (tran.getAction()) {
                case CREATE:
                case UPDATE:
                case NO_CHANGE:
                    infos.add(tran.getRegistration());
            }
        }
        return infos;
    }

    // 
    // process new transactions
    //
    private CourseRegistrationTransaction createNewCourseRegistrationTransaction(RegistrationRequestItemInfo item,
            boolean skipActivities,
            ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = this.createNewCourseRegistration(item, contextInfo);
        List<ActivityRegistrationTransaction> activityTrans;
        if (skipActivities) {
            activityTrans = Collections.EMPTY_LIST;
        } else {
            activityTrans = this.createNewActivityTransactions(item, contextInfo);
        }
        CourseRegistrationTransaction rt = new CourseRegistrationTransaction(ActionEnum.CREATE, reg, activityTrans);
        return rt;
    }

    private List<ActivityRegistrationTransaction> createNewActivityTransactions(RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        List<ActivityRegistrationTransaction> list = new ArrayList<ActivityRegistrationTransaction>();
        RegistrationGroupInfo regGroup = this.getRegGroup(item.getRegistrationGroupId(), contextInfo);
        for (String activityOfferingId : regGroup.getActivityOfferingIds()) {
            ActivityRegistrationTransaction trans = this.createNewActivityTransaction(item, contextInfo, activityOfferingId);
            list.add(trans);
        }
        return list;
    }

    private ActivityRegistrationTransaction createNewActivityTransaction(RegistrationRequestItemInfo item,
            ContextInfo contextInfo,
            String activityOfferingId)
            throws OperationFailedException {
        ActivityRegistrationInfo reg = this.createNewActivityRegistration(item, contextInfo, activityOfferingId);
        ActivityRegistrationTransaction trans = new ActivityRegistrationTransaction(ActionEnum.CREATE, reg);
        return trans;
    }

    private CourseRegistrationInfo createNewCourseRegistration(RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = new CourseRegistrationInfo();
        RegistrationGroupInfo regGroup = this.getRegGroup(item.getRegistrationGroupId(), contextInfo);
        reg.setPersonId(item.getPersonId());
        reg.setTypeKey(LprServiceConstants.REGISTRANT_TYPE_KEY);
        reg.setStateKey(LprServiceConstants.REGISTERED_STATE_KEY);
        reg.setCourseOfferingId(regGroup.getCourseOfferingId());
        reg.setCredits(item.getCredits());
        reg.setGradingOptionId(item.getGradingOptionId());
        reg.setEffectiveDate(contextInfo.getCurrentDate());
        reg.setExpirationDate(null);
        // TODO: how do we know which attributes on item should be mapped to the Activity and which to the Course registration?
        reg.getAttributes().addAll(item.getAttributes());
        return reg;
    }

    private ActivityRegistrationInfo createNewActivityRegistration(RegistrationRequestItemInfo item,
            ContextInfo contextInfo,
            String activityOfferingId)
            throws OperationFailedException {
        ActivityRegistrationInfo reg = new ActivityRegistrationInfo();
        reg.setPersonId(item.getPersonId());
        reg.setTypeKey(LprServiceConstants.REGISTRANT_TYPE_KEY);
        reg.setStateKey(LprServiceConstants.REGISTERED_STATE_KEY);
        reg.setActivityOfferingId(activityOfferingId);
        reg.setEffectiveDate(contextInfo.getCurrentDate());
        reg.setExpirationDate(null);
        // TODO: how do we know which attributes on item should be mapped to the Activity and which to the Course registration?
        reg.getAttributes().addAll(item.getAttributes());
        return reg;
    }

    // 
    // process existing registrations 
    //
    private CourseRegistrationTransaction createExistingRegistrationTransaction(CourseRegistrationInfo reg,
            ContextInfo contextInfo)
            throws OperationFailedException {
        List<ActivityRegistrationTransaction> activityTrans = this.createExistingActivityTransactions(reg, contextInfo);
        CourseRegistrationTransaction rt = new CourseRegistrationTransaction(ActionEnum.NO_CHANGE, reg, activityTrans);
        return rt;
    }

    private List<ActivityRegistrationTransaction> createExistingActivityTransactions(CourseRegistrationInfo reg,
            ContextInfo contextInfo)
            throws OperationFailedException {
        List<ActivityRegistrationTransaction> list = new ArrayList<ActivityRegistrationTransaction>();

        List<ActivityRegistrationInfo> ars = this.getActivityRegistrationsForCourseRegistration(reg.getId(), contextInfo);
        for (ActivityRegistrationInfo ar : ars) {
            ActivityRegistrationTransaction trans = this.createExistingActivityTransaction(ar, contextInfo);
            list.add(trans);
        }
        return list;
    }

    private ActivityRegistrationTransaction createExistingActivityTransaction(ActivityRegistrationInfo reg,
            ContextInfo contextInfo)
            throws OperationFailedException {
        ActivityRegistrationTransaction trans = new ActivityRegistrationTransaction(ActionEnum.NO_CHANGE, reg);
        return trans;
    }

    // 
    // update logic
    //
    private void markCourseRegistrationTransactionAsDropped(CourseRegistrationTransaction rt,
            RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        this.markCourseRegistrationInfoAsDropped(rt.getRegistration(), item, contextInfo);
        rt.setAction(ActionEnum.UPDATE);
        for (ActivityRegistrationTransaction art : rt.getActivityRegistrationTransactions()) {
            this.markActivityRegistrationTransactionAsDropped(art, item, contextInfo);
        }
        return;
    }

    /**
     * protected so it can be overridden by implementing institution
     *
     * @param reg
     * @param item
     * @param contextInfo
     * @throws OperationFailedException
     */
    protected void markCourseRegistrationInfoAsDropped(CourseRegistrationInfo reg,
            RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        String stateKey = calculateDroppedStateKeyForCourse(reg, item, contextInfo);
        reg.setStateKey(stateKey);
        reg.setExpirationDate(contextInfo.getCurrentDate());
        return;
    }

    private void markActivityRegistrationTransactionAsDropped(ActivityRegistrationTransaction art,
            RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        this.markActivityRegistrationInfoAsDropped(art.getRegistration(), item, contextInfo);
        art.setAction(ActionEnum.UPDATE);
        return;
    }

    /**
     * protected so it can be overridden by implementing institution
     *
     * @param reg
     * @param item
     * @param contextInfo
     * @throws OperationFailedException
     */
    protected void markActivityRegistrationInfoAsDropped(ActivityRegistrationInfo reg,
            RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        String stateKey = calculateDroppedStateKeyForActivity(reg, item, contextInfo);
        reg.setStateKey(stateKey);
        reg.setExpirationDate(contextInfo.getCurrentDate());
        return;
    }

    /**
     * protected so it can be overridden by implementing institutions
     *
     * @param reg the CR to be updated
     * @param item the item to look at to see if should update
     * @param contextInfo supplied context
     * @return true if there has been any change
     * @throws OperationFailedException if can't complete
     */
    private boolean updateCourseRegistrationTransaction(CourseRegistrationTransaction rt, RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        boolean updated = this.updateCourseRegistrationInfo(rt.getRegistration(), item, contextInfo);
        if (updated) {
            rt.setAction(ActionEnum.UPDATE);
        }
        return updated;
    }

    /**
     * protected so it can be overridden by implementing institutions
     *
     * @param reg the CR to be updated
     * @param item the item to look at to see if should update
     * @param contextInfo supplied context
     * @return true if there has been any change
     * @throws OperationFailedException if can't complete
     */
    private boolean updateCourseRegistrationInfo(CourseRegistrationInfo reg, RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        boolean changed = false;
        if (!isSame(reg.getCredits(), item.getCredits())) {
            changed = true;
            reg.setCredits(item.getCredits());
        }
        if (!isSame(reg.getCredits(), item.getCredits())) {
            changed = true;
            reg.setCredits(item.getCredits());
            reg.setGradingOptionId(item.getGradingOptionId());
        }
        // TODO: deal with dynamic attributes
        return changed;
    }

    private boolean isSame(KualiDecimal oldKD, KualiDecimal newKD) {
        if (oldKD == null) {
            if (newKD == null) {
                return true;
            }
            return false;
        }
        return oldKD.equals(newKD);
    }

    /**
     * protected so can be overridden by implementing institutions
     *
     * @param reg
     * @param item
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    protected String calculateDroppedStateKeyForCourse(CourseRegistrationInfo reg, RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        Date date = calculateLateDropDateForCourse(reg, item, contextInfo);
        if (contextInfo.getCurrentDate().after(date)) {
            return LprServiceConstants.DROPPED_STATE_KEY;
        }
        return LprServiceConstants.DROPPED_LATE_STATE_KEY;
    }

    /**
     * protected so can be overridden by implementing institutions
     *
     * @param reg
     * @param item
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    protected String calculateDroppedStateKeyForActivity(ActivityRegistrationInfo reg, RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
        Date date = calculateLateDropDateForActivity(reg, item, contextInfo);
        if (contextInfo.getCurrentDate().after(date)) {
            return LprServiceConstants.DROPPED_STATE_KEY;
        }
        return LprServiceConstants.DROPPED_LATE_STATE_KEY;
    }

    /**
     * protected so can be overridden by implementing institutions
     *
     * @param reg
     * @param item
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    private Date calculateLateDropDateForCourse(CourseRegistrationInfo reg, RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
//     TODO: get the term from the co and get the late drop date from ATP and return that date.
        return new Date();
    }

    /**
     * protected so can be overridden by implementing institutions
     *
     * @param reg
     * @param item
     * @param contextInfo
     * @return
     * @throws OperationFailedException
     */
    protected Date calculateLateDropDateForActivity(ActivityRegistrationInfo reg, RegistrationRequestItemInfo item,
            ContextInfo contextInfo)
            throws OperationFailedException {
//     TODO: get the term from the co and get the late drop date from ATP and return that date.
        return new Date();
    }

    /// 
    // fetch operations
    //
    private List<ActivityRegistrationInfo> getActivityRegistrationsForCourseRegistration(String courseRegistrationId,
            ContextInfo contextInfo)
            throws OperationFailedException {
        List<ActivityRegistrationInfo> list;
        try {
            list = this.getCourseRegistrationService().getActivityRegistrationsForCourseRegistration(courseRegistrationId,
                    contextInfo);
            return list;
//        } catch (DoesNotExistException ex) {
//            throw new OperationFailedException("new reg group should exist", ex);
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    private RegistrationGroupInfo getRegGroup(String regGroupId, ContextInfo contextInfo)
            throws OperationFailedException {
        RegistrationGroupInfo regGroup;
        try {
            regGroup = this.getCourseOfferingService().getRegistrationGroup(regGroupId, contextInfo);
            return regGroup;
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("new reg group should exist", ex);
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }
}
