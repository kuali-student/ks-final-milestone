package org.kuali.student.poc.rules.credit.limit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestInfo;
import org.kuali.student.enrollment.courseregistration.dto.RegistrationRequestItemInfo;
import org.kuali.student.poc.rules.credit.limit.CourseRegistrationAction.Action;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

/**
 * This code merges a registration request with a student's already persisted data to create a composite so we can apply rules
 * against it such as credit limit checks and time conflict checks and co-requisite checks.
 *
 */
public class MergeRequestAndActual {

    private CourseOfferingService courseOfferingService;

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

    public List<CourseRegistrationAction> updateRegistrations(RegistrationRequestInfo request,
            List<CourseRegistrationInfo> registrations,
            ContextInfo contextInfo)
            throws OperationFailedException {
        List<CourseRegistrationAction> list = new ArrayList<CourseRegistrationAction>();
        // load the existing ones as no change
        for (CourseRegistrationInfo reg : registrations) {
            list.add(new CourseRegistrationAction(Action.NO_CHANGE, reg));
        }
        for (RegistrationRequestItemInfo item : request.getRegistrationRequestItems()) {
            // Adds
            if (item.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_ADD_TYPE_KEY)) {
                CourseRegistrationInfo reg = this.calculateNewRegistration(item, contextInfo);
                list.add(new CourseRegistrationAction(Action.CREATE, reg));
                continue;
            }
            // drops
            if (item.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_DROP_TYPE_KEY)) {
                CourseRegistrationAction cra = this.findMatchingActiveRegistration(item, contextInfo, list);
                if (cra == null) {
                    throw new OperationFailedException("Cannot drop non-existent  or non=active course registration");
                }
                dropRegistration(cra.getRegistration(), item, contextInfo);
                cra.setAction(Action.UPDATE);
                continue;
            } 
            // just updating data bits 
            if (item.getTypeKey().equals(LprServiceConstants.LPRTRANS_ITEM_UPDATE_TYPE_KEY)) {
                CourseRegistrationAction cra = this.findMatchingActiveRegistration(item, contextInfo, list);
                if (cra == null) {
                    throw new OperationFailedException("Cannot drop non-existent  or non=active course registration");
                }
                updateRegistration(cra.getRegistration(), item, contextInfo);
                cra.setAction(Action.UPDATE);
            }
            // TODO: implement the swap
        }
        return list;

    }

    private CourseRegistrationInfo calculateNewRegistration(RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = new CourseRegistrationInfo();
        RegistrationGroupInfo regGroup = this.getExistingRegGroup(item, contextInfo);
        reg.setStudentId(item.getStudentId());
        reg.setTypeKey(LprServiceConstants.REGISTRANT_TYPE_KEY);
        reg.setStateKey(LprServiceConstants.REGISTERED_STATE_KEY);
        reg.setCourseOfferingId(regGroup.getCourseOfferingId());
        reg.setCredits(item.getCredits());
        reg.setGradingOptionId(item.getGradingOptionId());
        reg.setEffectiveDate(contextInfo.getCurrentDate());
        reg.setExpirationDate(null);
        return reg;
    }

    private void dropRegistration(CourseRegistrationInfo reg, RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        String stateKey = calculateDroppedStateKey(reg, item, contextInfo);
        reg.setStateKey(stateKey);
        reg.setExpirationDate(contextInfo.getCurrentDate());
        return;
    }

    private void updateRegistration(CourseRegistrationInfo reg, RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        reg.setCredits(item.getCredits());
        reg.setGradingOptionId(item.getGradingOptionId());
        return;
    }

    private RegistrationGroupInfo getExistingRegGroup(RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        RegistrationGroupInfo regGroup;
        try {
            regGroup = this.getCourseOfferingService().getRegistrationGroup(item.getNewRegistrationGroupId(),
                    contextInfo);
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

    private CourseRegistrationAction findMatchingActiveRegistration(RegistrationRequestItemInfo item,
            ContextInfo contextInfo, List<CourseRegistrationAction> cras)
            throws OperationFailedException {
        RegistrationGroupInfo regGroup = this.getExistingRegGroup(item, contextInfo);
        for (CourseRegistrationAction cra : cras) {
            CourseRegistrationInfo reg = cra.getRegistration();
            if (!reg.getCourseOfferingId().equals(regGroup.getCourseOfferingId())) {
                continue;
            }
            if (!reg.getStudentId().equals(item.getStudentId())) {
                continue;
            }
            if (!isActive(reg.getStateKey())) {
                continue;
            }
            return cra;
        }
        return null;
    }

    private boolean isActive(String stateKey) {
        if (stateKey.equals(LprServiceConstants.REGISTERED_STATE_KEY)) {
            return true;
        }
        return false;
    }

    // TODO: figure out how this logic can be made configurable by institutions
    private String calculateDroppedStateKey(CourseRegistrationInfo reg, RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
        Date date = calculateLateDropDate(reg, item, contextInfo);
        if (contextInfo.getCurrentDate().after(date)) {
            return LprServiceConstants.DROPPED_STATE_KEY;
        }
        return LprServiceConstants.DROPPED_LATE_STATE_KEY;
    }

    private Date calculateLateDropDate(CourseRegistrationInfo reg, RegistrationRequestItemInfo item, ContextInfo contextInfo)
            throws OperationFailedException {
//     TODO: get the term from the co and get the late drop date from ATP and return that date.
        return new Date();
    }
}
