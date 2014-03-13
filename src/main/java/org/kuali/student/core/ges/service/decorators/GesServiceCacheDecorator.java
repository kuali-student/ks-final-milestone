package org.kuali.student.core.ges.service.decorators;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.search.Attribute;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GesServiceCacheDecorator extends GesServiceDecorator {
    protected static final String GES_SERVICE_CACHE = "gesServiceCache";


    private CacheManager cacheManager;
    private Cache cache;

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<ValueInfo> evaluateValues(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            , MissingParameterException
            , OperationFailedException
            , PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(parameterKey,criteria);
        List<ValueInfo> result = (List<ValueInfo>)pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValues(parameterKey,criteria,contextInfo);
            saveCachedValue(cacheKey,result);
        } else{
             result = copyValues(result);
        }
        return result;

    }

    @Override
    public List<ValueInfo> evaluateValuesOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(parameterKey,criteria,onDate);
        List<ValueInfo> result = (List<ValueInfo>)pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValuesOnDate(parameterKey, criteria, onDate, contextInfo);
            saveCachedValue(cacheKey,result);
        }else{
            result = copyValues(result);
        }
        return result;
    }

    @Override
    public ValueInfo evaluateValueOnDate(String parameterKey, GesCriteriaInfo criteria, Date onDate, ContextInfo contextInfo) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(parameterKey,criteria,onDate);
        ValueInfo result = (ValueInfo) pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValueOnDate(parameterKey, criteria, onDate, contextInfo);
            saveCachedValue(cacheKey,result);
        }else{
            result = new ValueInfo(result);
        }
        return result;
    }

    @Override
    public ValueInfo evaluateValue(String parameterKey, GesCriteriaInfo criteria, ContextInfo contextInfo) throws InvalidParameterException, DoesNotExistException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(parameterKey,criteria);
        ValueInfo result = (ValueInfo) pullCachedValue(cacheKey);
        if (result == null){
            result = getNextDecorator().evaluateValue(parameterKey, criteria, contextInfo);
            saveCachedValue(cacheKey,result);
        } else{
            result = new ValueInfo(result);
        }
        return result;

    }

    @Override
    public StatusInfo deleteValue(String valueId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteValue(valueId, contextInfo);
        deleteCachedResult(valueId);
        return result;
    }

    @Override
    public ValueInfo updateValue(String valueId, ValueInfo valueInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        ValueInfo result = getNextDecorator().updateValue(valueId, valueInfo, contextInfo);
        deleteCachedResult(valueId);
        return result;
    }
    private List<ValueInfo> copyValues(List<ValueInfo> values) throws OperationFailedException {
        List<ValueInfo> copiedValues = new ArrayList<ValueInfo>();
        for (ValueInfo value : values){
            copiedValues.add(new ValueInfo(value));
        }

        return copiedValues;
    }
    private void saveCachedValue(MultiKey multiKey,List<ValueInfo> values) throws OperationFailedException {
        getCache().put(new Element(multiKey, copyValues(values)));
    }

    private void saveCachedValue(MultiKey multiKey,ValueInfo value) throws OperationFailedException {
        getCache().put(new Element(multiKey, new ValueInfo(value)));
    }

    private void deleteCachedResult(String valueId){
        Cache cache = getCache();
        Attribute<String> id = cache.getSearchAttribute("id");

        Query query = cache.createQuery();
        query.includeKeys();
        query.includeValues();
        query.includeAttribute(id);
        query.addCriteria(id.ilike("% " + valueId + " %"));

        Results results = query.execute();
        for (Result result : results.all()) {
            cache.remove(result.getKey());
        }
    }

    private Object pullCachedValue(MultiKey multiKey){
        Element cachedResult = getCache().get(multiKey);
        if(cachedResult != null){
            return cachedResult.getValue();
        }
        return null;
    }

    private Cache getCache(){
        if (cache == null){
           cache = cacheManager.getCache(GES_SERVICE_CACHE);
        }
        return cache;
    }


}
