package org.kuali.student.r2.lum.lrc.service.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DependentObjectsExistException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.lum.lrc.dto.ResultScaleInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCServiceDecorator;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;



/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 9/17/12
 * Time: 11:22 PM
 * This class is a cache decorator for the LRC service. the cache config can be found in ks-ehcache.xml
 */
public class LRCServiceCacheDecorator extends LRCServiceDecorator {
    private String cacheName = "LRCServiceCache";      // has config in ks-ehcache.xml
    private CacheManager cacheManager;

    private static String RESULT_SCALE_CACHE_PREFIX = "resultScalePrefix";

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getResultValuesGroupsByResultScale", resultScaleKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getResultValuesGroupsByResultScale(resultScaleKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (List<ResultValuesGroupInfo>)result;
    }

    @Override
    public ResultValuesGroupInfo getCreateFixedCreditResultValuesGroup(@WebParam(name = "creditValue") String creditValue, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getCreateFixedCreditResultValuesGroup", creditValue, scaleKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getCreateFixedCreditResultValuesGroup(creditValue, scaleKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (ResultValuesGroupInfo)result;
    }

    @Override
    public ResultValuesGroupInfo getCreateRangeCreditResultValuesGroup(@WebParam(name = "creditValueMin") String creditValueMin, @WebParam(name = "creditValueMax") String creditValueMax, @WebParam(name = "creditValueIncrement") String creditValueIncrement, @WebParam(name = "scaleKey") String scaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getCreateRangeCreditResultValuesGroup", creditValueMin, creditValueMax, creditValueIncrement, scaleKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getCreateRangeCreditResultValuesGroup(creditValueMin, creditValueMax, creditValueIncrement, scaleKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (ResultValuesGroupInfo)result;
    }

    @Override
    public List<String> getResultValueKeysByType(@WebParam(name = "resultValueTypeKey") String resultValueTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getResultValueKeysByType", resultValueTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getResultValueKeysByType(resultValueTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (List<String>)result;
    }

    @Override
    public List<ResultValueInfo> getResultValuesByKeys(List<String> resultValueIds, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getResultValuesByKeys", resultValueIds.toString());

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getResultValuesByKeys(resultValueIds, context);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<ResultValueInfo>)result;
    }

    @Override
    public ResultValuesGroupInfo getResultValuesGroup(String resultValuesGroupId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey("getResultValuesGroup", resultValuesGroupId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getResultValuesGroup(resultValuesGroupId, context);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (ResultValuesGroupInfo)result;
    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByKeys(List<String> resultValuesGroupIds, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        List<ResultValuesGroupInfo> result = new ArrayList<ResultValuesGroupInfo>(resultValuesGroupIds.size());

        for(String id: resultValuesGroupIds){
            result.add(this.getResultValuesGroup(id,context));
        }
        return result;

    }

    @Override
    public List<ResultValuesGroupInfo> getResultValuesGroupsByResultValue(String resultValueId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getResultValuesGroupsByResultValue", resultValueId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getResultValuesGroupsByResultValue(resultValueId, context);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (List<ResultValuesGroupInfo>)result;
    }

    @Override
    public List<String> getResultValuesGroupKeysByType(String resultValuesGroupTypeKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        return getNextDecorator().getResultValuesGroupKeysByType(resultValuesGroupTypeKey, context);
    }
    @Override
    public ResultScaleInfo getResultScale(String resultScaleId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(RESULT_SCALE_CACHE_PREFIX, resultScaleId);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getResultScale(resultScaleId, context);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (ResultScaleInfo)result;
    }

    @Override
    public List<ResultValueInfo> getResultValuesForScale(String resultScaleKey, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getResultValuesForScale", resultScaleKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getResultValuesForScale(resultScaleKey, context);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return   (List<ResultValueInfo>)result;
    }

    @Override
    public ResultValueInfo updateResultValue(String resultValueId, ResultValueInfo resultValueInfo, ContextInfo context)
            throws DataValidationErrorException, DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException,
            VersionMismatchException {
        cacheManager.getCache(cacheName).removeAll();
        return getNextDecorator().updateResultValue(resultValueId, resultValueInfo, context);
    }

    @Override
    public StatusInfo deleteResultValue(String resultValueId, ContextInfo context) throws DoesNotExistException,
            InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException, DependentObjectsExistException {
        cacheManager.getCache(cacheName).removeAll();
        return getNextDecorator().deleteResultValue(resultValueId, context);
    }

    @Override
    public ResultScaleInfo updateResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "resultScaleInfo") ResultScaleInfo resultScaleInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {

        ResultScaleInfo rInfo = getNextDecorator().updateResultScale(resultScaleKey,resultScaleInfo,contextInfo);
        MultiKey cacheKey = new MultiKey(RESULT_SCALE_CACHE_PREFIX, rInfo.getKey());

       cacheManager.getCache(cacheName).put(new Element(cacheKey, rInfo));

        return     rInfo;
    }

    @Override
    public StatusInfo deleteResultScale(@WebParam(name = "resultScaleKey") String resultScaleKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, DependentObjectsExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(RESULT_SCALE_CACHE_PREFIX, resultScaleKey);
        cacheManager.getCache(cacheName).remove(cacheKey);

        return getNextDecorator().deleteResultScale(resultScaleKey,contextInfo);
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
