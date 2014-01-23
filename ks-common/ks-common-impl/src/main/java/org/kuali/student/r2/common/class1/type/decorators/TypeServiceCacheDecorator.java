package org.kuali.student.r2.common.class1.type.decorators;

import net.sf.ehcache.Cache;
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
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.core.scheduling.dto.ScheduleInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for TypeService to add caching to select type service methods.
 */
public class TypeServiceCacheDecorator extends TypeServiceDecorator{
    private static String cacheName = "TypeServiceCache";
    public static final String TYPES_BY_KEY_KEY = "getKey";
    public static final String GET_TYPES_BY_REF_OBJECT_URI_KEY = "getTypesByRefObjectUri";
    public static final String GET_ALLOWED_TYPES_FOR_TYPE_KEY = "getAllowedTypesForType";
    public static final String GET_TYPE_TYPE_RELATIONS_BY_OWNER_AND_TYPE_KEY = "getTypeTypeRelationsByOwnerAndType";
    public static final String GET_TYPES_FOR_GROUP_TYPE_KEY = "getTypesForGroupType";
    public static final String GET_TYPE_TYPE_RELATIONS_BY_RELATED_TYPE_AND_TYPE_KEY = "getTypeTypeRelationsByRelatedTypeAndType";
    private CacheManager cacheManager;

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByRelatedTypeAndType(String relatedTypeKey, String typeTypeRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(GET_TYPE_TYPE_RELATIONS_BY_RELATED_TYPE_AND_TYPE_KEY, relatedTypeKey, typeTypeRelationTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getTypeTypeRelationsByRelatedTypeAndType(relatedTypeKey, typeTypeRelationTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<TypeTypeRelationInfo>) result;
    }

    @Override
    public List<TypeInfo> getTypesByKeys(List<String> typeKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<TypeInfo> typeInfos = new ArrayList<TypeInfo>();
        List<String> typeKeysToSearch = new ArrayList<String>();
        Cache cache = cacheManager.getCache(cacheName);
        //Check the cache for the ids
        for(String typeKey : typeKeys){
            MultiKey cacheKey = new MultiKey(TYPES_BY_KEY_KEY, typeKey);
            Element cachedResult = cache.get(cacheKey);
            if (cachedResult != null) {
                //If the id was found in the cache then use that and remove from the list of ids one is searching for
                typeInfos.add((TypeInfo) cachedResult.getValue());
            } else {
                //Otherwise save this id as one that needs to be looked up
                typeKeysToSearch.add(typeKey);
            }
        }
        //Call the underlying service to get the remainder
        if (!typeKeysToSearch.isEmpty()) {
            List<TypeInfo> uncachedTypeInfos = getNextDecorator().getTypesByKeys(typeKeysToSearch, contextInfo);
            for (TypeInfo typeInfo : uncachedTypeInfos) {
                MultiKey cacheKey = new MultiKey(TYPES_BY_KEY_KEY, typeInfo.getKey());
                cache.put(new Element(cacheKey, typeInfo));
            }
            typeInfos.addAll(uncachedTypeInfos);
        }

        return typeInfos;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(GET_TYPES_BY_REF_OBJECT_URI_KEY, refObjectUri);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getTypesByRefObjectUri(refObjectUri, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<TypeInfo>) result;
    }

    @Override
    public TypeInfo getType(String typeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(TYPES_BY_KEY_KEY, typeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
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
        MultiKey cacheKey = new MultiKey(GET_ALLOWED_TYPES_FOR_TYPE_KEY, ownerTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getAllowedTypesForType(ownerTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<TypeInfo>)result;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeTypeRelationsByOwnerAndType(String ownerTypeKey, String typeTypeRelationTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(GET_TYPE_TYPE_RELATIONS_BY_OWNER_AND_TYPE_KEY, ownerTypeKey, typeTypeRelationTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getTypeTypeRelationsByOwnerAndType(ownerTypeKey, typeTypeRelationTypeKey, contextInfo);
            cacheManager.getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<TypeTypeRelationInfo>)result;
    }

    @Override
    public List<TypeInfo> getTypesForGroupType(String groupTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(GET_TYPES_FOR_GROUP_TYPE_KEY, groupTypeKey);

        Element cachedResult = cacheManager.getCache(cacheName).get(cacheKey);
        Object result;
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

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
