package org.kuali.student.r2.core.class1.type.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.dto.TypeInfo;

import java.util.List;

/**
 * Decorator for TypeService to add caching to select type service methods.
 */
public class TypeServiceCacheDecorator extends TypeServiceDecorator{
    private String cacheName = "TypeServiceCache";
    private CacheManager cacheManager;

    public TypeServiceCacheDecorator(){
        cacheManager = CacheManager.getInstance();
        try {
            cacheManager.addCache(cacheName);
        } catch (ObjectExistsException e) {
        }
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getKey", typeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getType(typeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (TypeInfo)result;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getAllowedTypesForType", ownerTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getAllowedTypesForType(ownerTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<TypeInfo>)result;
    }

    @Override
    public List<TypeInfo> getTypesForGroupType(String groupTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getTypesForGroupType", groupTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getTypesForGroupType(groupTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<TypeInfo>)result;
    }

    @Override
    public TypeInfo updateType(String typeKey, TypeInfo typeInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        TypeInfo result = getNextDecorator().updateType(typeKey, typeInfo, contextInfo);
        cacheManager.getCache(cacheName).removeAll();
        return result;
    }

    @Override
    public StatusInfo deleteType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        StatusInfo result = getNextDecorator().deleteType(typeKey, contextInfo);
        cacheManager.getCache(cacheName).removeAll();
        return result;
    }
}
