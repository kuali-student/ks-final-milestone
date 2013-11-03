package org.kuali.student.r2.lum.service.decorator;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;

import javax.jws.WebParam;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 11/1/13
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class CluServiceCacheDecorator extends CluServiceDecorator {

    private static final String CLU_CACHE_NAME = "cluCache";

    private CacheManager cacheManager;

    @Override
    public CluInfo createClu(@WebParam(name = "luTypeKey") String luTypeKey, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        CluInfo result = getNextDecorator().createClu(luTypeKey, cluInfo, contextInfo);
        getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(result.getId(), result));
        return result;
    }

    @Override
    public StatusInfo deleteClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DependentObjectsExistException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteClu(cluId, contextInfo);
        getCacheManager().getCache(CLU_CACHE_NAME).remove(cluId);
        return result;
    }

    @Override
    public CluInfo getClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        Element cachedResult = getCacheManager().getCache(CLU_CACHE_NAME).get(cluId);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getClu(cluId, contextInfo);
            getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(cluId, result));
        } else {
            result = cachedResult.getValue();
        }

        return (CluInfo) result;
    }

    @Override
    public CluInfo updateClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluInfo") CluInfo cluInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        CluInfo result = getNextDecorator().updateClu(cluId, cluInfo, contextInfo);
        getCacheManager().getCache(CLU_CACHE_NAME).remove(cluId);
        getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(cluId, result));
        return result;
    }

    public CacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
