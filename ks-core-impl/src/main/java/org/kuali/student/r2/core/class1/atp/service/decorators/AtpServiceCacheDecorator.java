package org.kuali.student.r2.core.class1.atp.service.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;

/**
 * Caches Atps and Milestones
 */
public class AtpServiceCacheDecorator extends AtpServiceDecorator {

    private static final String ATP_CACHE_NAME = "atpCache";
    private static final String MILESTONE_CACHE_NAME = "milestoneCache";

    private CacheManager cacheManager;

    @Override
    public AtpInfo getAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Element cachedResult = getCacheManager().getCache(ATP_CACHE_NAME).get(atpId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getAtp(atpId, context);
            getCacheManager().getCache(ATP_CACHE_NAME).put(new Element(atpId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (AtpInfo)result;
    }

    @Override
    public StatusInfo deleteAtp(String atpId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteAtp(atpId, context);
        getCacheManager().getCache(ATP_CACHE_NAME).remove(atpId);
        return result;
    }

    @Override
    public AtpInfo createAtp(String atpTypeKey, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        AtpInfo result = getNextDecorator().createAtp(atpTypeKey, atpInfo, context);
        getCacheManager().getCache(ATP_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public AtpInfo updateAtp(String atpId, AtpInfo atpInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        AtpInfo result = getNextDecorator().updateAtp(atpId, atpInfo, context);
        getCacheManager().getCache(ATP_CACHE_NAME).remove(atpId);
        getCacheManager().getCache(ATP_CACHE_NAME).put(new Element(atpId, result));
        return result;
    }

    @Override
    public MilestoneInfo createMilestone(String milestoneTypeKey, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        MilestoneInfo result = getNextDecorator().createMilestone(milestoneTypeKey, milestoneInfo, context);
        getCacheManager().getCache(MILESTONE_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public MilestoneInfo getMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Element cachedResult = getCacheManager().getCache(MILESTONE_CACHE_NAME).get(milestoneId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getMilestone(milestoneId, context);
            getCacheManager().getCache(MILESTONE_CACHE_NAME).put(new Element(milestoneId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (MilestoneInfo)result;
    }

    @Override
    public MilestoneInfo updateMilestone(String milestoneId, MilestoneInfo milestoneInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, ReadOnlyException {
        MilestoneInfo result = getNextDecorator().updateMilestone(milestoneId, milestoneInfo, context);
        getCacheManager().getCache(MILESTONE_CACHE_NAME).remove(milestoneId);
        getCacheManager().getCache(MILESTONE_CACHE_NAME).put(new Element(milestoneId, result));
        return result;
    }

    @Override
    public StatusInfo deleteMilestone(String milestoneId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteMilestone(milestoneId, context);
        getCacheManager().getCache(MILESTONE_CACHE_NAME).remove(milestoneId);
        return result;
    }

    public CacheManager getCacheManager() {
        if(cacheManager == null){
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
