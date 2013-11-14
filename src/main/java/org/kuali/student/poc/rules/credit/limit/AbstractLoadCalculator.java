package org.kuali.student.poc.rules.credit.limit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.student.common.UUIDHelper;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import static org.kuali.student.poc.rules.credit.limit.CourseRegistrationAction.Action.CREATE;
import static org.kuali.student.poc.rules.credit.limit.CourseRegistrationAction.Action.NO_CHANGE;
import static org.kuali.student.poc.rules.credit.limit.CourseRegistrationAction.Action.UPDATE;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;

/**
 * this is an abstract class that handles lots of the plumbing required to do the calculation 
 */
public abstract class AbstractLoadCalculator implements LoadCalculator {

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

    protected LoadInfo constructLoadInfo(String typeKey,
            List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {

        LoadInfo load = new LoadInfo();
        load.setId(UUIDHelper.genStringUUID());
        // this is actually the calculation type key, should we rename this?
        load.setLoadLevelTypeKey(loadLevelTypeKey);
        load.setTypeKey(typeKey);
        load.setStateKey(calcStateKey(actions, loadLevelTypeKey, contextInfo));
        load.setMeta(this.newMeta(contextInfo));
        return load;

    }

    protected boolean accept(CourseRegistrationAction action,
            LoadInfo load,
            List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        if (action.getRegistration().getStateKey().equals(LprServiceConstants.REGISTERED_STATE_KEY)) {
            return true;
        }
        return false;
    }

    protected String getCreditsForRegistration(CourseRegistrationAction action,
            LoadInfo load,
            List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        CourseRegistrationInfo reg = action.getRegistration();
        if (reg.getCredits() != null) {
            return reg.getCredits();
        }
        CourseOfferingInfo co = this.getCourseOffering(reg.getCourseOfferingId(), contextInfo);
        // when did we add credit cnt to the CO record?  I was expecting to have to look up the actual value 
        // using the creditOptionId in the result value
        // Why the ugly abbreviation? "Cnt"  can't we spell it out?
        return co.getCreditCnt();
    }

    protected CourseOfferingInfo getCourseOffering(String coId,
            ContextInfo contextInfo)
            throws OperationFailedException {
        try {
            CourseOfferingInfo co = this.courseOfferingService.getCourseOffering(coId, contextInfo);
            return co;
        } catch (DoesNotExistException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (InvalidParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (MissingParameterException ex) {
            throw new OperationFailedException("unexpected", ex);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("unexpected", ex);
        }
    }

    protected int parseCreditsAsInt(String creditString) throws OperationFailedException {
        try {
            int credits = Integer.parseInt(creditString);
            return credits;
        } catch (NumberFormatException ex) {
            throw new OperationFailedException("this implementation only supports integer credits: " + creditString, ex);
        }
    }

    protected BigDecimal parseCreditsAsBigDecimal(String creditString) throws OperationFailedException {
        try {
            BigDecimal credits = new BigDecimal(creditString);
            return credits;
        } catch (NumberFormatException ex) {
            throw new OperationFailedException("this implementation only supports Big Decimal credits: " + creditString, ex);
        }
    }

    protected String calcStateKey(List<CourseRegistrationAction> actions,
            String loadLevelTypeKey,
            ContextInfo contextInfo)
            throws OperationFailedException {
        for (CourseRegistrationAction action : actions) {
            switch (action.getAction()) {
                case NO_CHANGE:
                    continue;
                case CREATE:
                case UPDATE:
                    return AcademicRecordServiceTypeStateConstants.LOAD_STATE_PRELIMIARY;
                default:
                    throw new OperationFailedException(action.toString());

            }
        }
        return AcademicRecordServiceTypeStateConstants.LOAD_STATE_FINAL;
    }

    protected MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }
}
