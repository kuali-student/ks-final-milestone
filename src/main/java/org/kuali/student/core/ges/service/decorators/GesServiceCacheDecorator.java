package org.kuali.student.core.ges.service.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.core.ges.dto.GesCriteriaInfo;
import org.kuali.student.core.ges.dto.ValueInfo;
import org.kuali.student.core.ges.service.GesServiceDecorator;
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

import java.util.Date;
import java.util.List;

public class GesServiceCacheDecorator extends GesServiceDecorator {
    private CacheManager cacheManager;



    protected static final String GES_SERVICE_CACHE = "gesServiceCache";


    @Override
    public List<ValueInfo> evaluateValues(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(parameterKey,criteria);
        Object result = pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValues(parameterKey,criteria,contextInfo);
            saveCachedValue(cacheKey,result);
        }
        return (List<ValueInfo>)result;

    }

    @Override
    public List<ValueInfo> evaluateValuesOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(parameterKey,criteria,onDate);
        Object result = pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValuesOnDate(parameterKey, criteria, onDate, contextInfo);
            saveCachedValue(cacheKey,result);
        }
        return (List<ValueInfo>)result;
    }

    @Override
    public ValueInfo evaluateValueOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(parameterKey,criteria,onDate);
        Object result = pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValueOnDate(parameterKey, criteria, onDate, contextInfo);
            saveCachedValue(cacheKey,result);
        }
        return (ValueInfo)result;
    }

    @Override
    public ValueInfo evaluateValue(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(parameterKey,criteria);
        Object result = pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValue(parameterKey, criteria, contextInfo);
            saveCachedValue(cacheKey,result);
        }
        return (ValueInfo)result;

    }

    @Override
    public StatusInfo deleteValue(String valueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = super.deleteValue(valueId, contextInfo);
        flushCache();
        return result;
    }

    @Override
    public ValueInfo updateValue(String valueId, ValueInfo valueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        ValueInfo result = super.updateValue(valueId, valueInfo, contextInfo);
        flushCache();
        return result;
    }

    private void saveCachedValue(MultiKey multiKey,Object value){
        cacheManager.getCache(GES_SERVICE_CACHE).put(new Element(multiKey,value));

    }


    private void flushCache(){

        cacheManager.getCache(GES_SERVICE_CACHE).removeAll();
    }
    private Object pullCachedValue(MultiKey multiKey){
        Element cachedResult = getCacheManager().getCache(GES_SERVICE_CACHE).get(multiKey);
        if(cachedResult != null){
            return cachedResult.getValue();
        }
        return null;
    }

    public CacheManager getCacheManager() {
        if (cacheManager == null){
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

}
