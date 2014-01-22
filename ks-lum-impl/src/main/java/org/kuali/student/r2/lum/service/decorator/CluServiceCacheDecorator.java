package org.kuali.student.r2.lum.service.decorator;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.CircularRelationshipException;
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
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;

import javax.jws.WebParam;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Daniel
 * Date: 11/1/13
 * Time: 10:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class CluServiceCacheDecorator extends CluServiceDecorator {

    private static final String CLU_CACHE_NAME = "cluCache";
    private static final String CLU_CLU_RELTN_KEY = "cluclureltn";

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
        for(CluCluRelationInfo cluCluRelationInfo : getCluCluRelationsByClu(cluId, contextInfo)) {
            //remove the related Clus
            removeRelatedClusByClu(cluCluRelationInfo.getCluId());
        }
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
        for(CluCluRelationInfo cluCluRelationInfo : getCluCluRelationsByClu(cluId, contextInfo)) {
            //remove the related Clus
            removeRelatedClusByClu(cluCluRelationInfo.getCluId());
        }
        getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(cluId, result));
        return result;
    }


    @Override
    public List<CluInfo> getRelatedClusByCluAndRelationType(@WebParam(name = "cluId") String cluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(CLU_CLU_RELTN_KEY, cluId, cluCluRelationTypeKey);
        Element cachedResult = getCacheManager().getCache(CLU_CACHE_NAME).get(cacheKey);
        Object result;
        if (cachedResult != null && !((List<CluInfo>)cachedResult.getValue()).isEmpty()) {
            result = cachedResult.getValue();
        } else {
            result = getNextDecorator().getRelatedClusByCluAndRelationType(cluId, cluCluRelationTypeKey, contextInfo);
            getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(cacheKey, result));
        }

        return (List<CluInfo>) result;
    }

    @Override
    public List<CluCluRelationInfo> getCluCluRelationsByClu(@WebParam(name = "cluId") String cluId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(CLU_CLU_RELTN_KEY, cluId);
        Element cachedResult = getCacheManager().getCache(CLU_CACHE_NAME).get(cacheKey);
        Object result;
        if (cachedResult != null && !((List<CluCluRelationInfo>)cachedResult.getValue()).isEmpty()) {
            result = cachedResult.getValue();
        } else {
            result = getNextDecorator().getCluCluRelationsByClu(cluId, contextInfo);
            getCacheManager().getCache(CLU_CACHE_NAME).put(new Element(cacheKey, result));
        }

        return (List<CluCluRelationInfo>) result;
    }

    @Override
    public CluCluRelationInfo createCluCluRelation(@WebParam(name = "cluId") String cluId, @WebParam(name = "relatedCluId") String relatedCluId, @WebParam(name = "cluCluRelationTypeKey") String cluCluRelationTypeKey, @WebParam(name = "cluCluRelationInfo") CluCluRelationInfo cluCluRelationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws CircularRelationshipException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        //remove the related Clus
        removeRelatedClusByClu(cluId);
        removeRelatedClusByClu(relatedCluId);
        return getNextDecorator().createCluCluRelation(cluId, relatedCluId, cluCluRelationTypeKey, cluCluRelationInfo, contextInfo);
    }

    private void removeRelatedClusByClu(String cluId) {
        for(Object key  : getCacheManager().getCache(CLU_CACHE_NAME).getKeys()) {
            if(key instanceof MultiKey) {
                MultiKey cacheKey = (MultiKey) key;
                if(cacheKey.size() > 1 && CLU_CLU_RELTN_KEY.equals(cacheKey.getKey(0)) && cluId.equals(cacheKey.getKey(1))) {
                    getCacheManager().getCache(CLU_CACHE_NAME).remove(cacheKey);
                }
            }
        }
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
