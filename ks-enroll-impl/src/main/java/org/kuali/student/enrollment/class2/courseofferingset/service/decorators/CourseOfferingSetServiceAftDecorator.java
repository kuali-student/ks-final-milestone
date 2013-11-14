package org.kuali.student.enrollment.class2.courseofferingset.service.decorators;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.student.enrollment.class2.courseofferingset.dao.SocDao;
import org.kuali.student.enrollment.class2.courseofferingset.model.SocEntity;
import org.kuali.student.enrollment.class2.courseofferingset.service.decorators.CourseOfferingSetServiceDecorator;
import org.kuali.student.enrollment.courseofferingset.dto.SocInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import javax.annotation.Resource;

/**
 * This decorator allows for special-behavior not desired in production, but needed to support testing.
 * For example, directly changing the state of a SOC thereby bypassing business-logic in order to validate other
 * behavior based on a specific state.
 */
public class CourseOfferingSetServiceAftDecorator
        extends CourseOfferingSetServiceDecorator {

    private static final Logger LOGGER = Logger.getLogger(CourseOfferingSetServiceAftDecorator.class);

    @Resource
    private SocDao socDao;

    @Override
    public SocInfo updateSoc(String socId, SocInfo socInfo, ContextInfo context) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        boolean bypassBusinessLogicStringValue = BooleanUtils.toBoolean(StringUtils.defaultIfEmpty(context.getAttributeValue(CourseOfferingSetServiceConstants.BYPASS_BUSINESS_LOGIC_ON_SOC_STATE_CHANGE_FOR_AFT_TESTING), String.valueOf(false)));
        if( !bypassBusinessLogicStringValue ) {
            this.getNextDecorator().updateSoc( socId, socInfo, context );
        }

        LOGGER.info( "bypassing business logic to change state(" + socInfo.getStateKey() + ") for SOC(" + socId + ")" );

        SocEntity entity = this.socDao.find(socId);
        entity.setSocState( socInfo.getStateKey() );
        entity.setEntityUpdated( context );

        entity = this.socDao.merge(entity);
        
        this.socDao.getEm().flush();
        
        return entity.toDto();
    }

    public SocDao getSocDao() {
        return socDao;
    }

    public void setSocDao(SocDao socDao) {
        this.socDao = socDao;
    }

}
