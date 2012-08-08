package org.kuali.student.r2.core.class1.state.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.ObjectExistsException;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.state.service.StateServiceDecorator;
import org.kuali.student.r2.common.state.dto.LifecycleInfo;
import org.kuali.student.r2.common.state.dto.StateInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Decorator for TypeService to add caching to select type service methods.
 *
 * WARNING: The cache decorator implementation does not currently take into account ContextInfo
 *
 */
public class StateServiceCacheDecorator extends StateServiceDecorator {
    private CacheManager cacheManager;


    protected static final String STATE_SERVICE_CACHE = "StateServiceCache";

    protected Map<String, LifecycleInfo> lifecycleInfoMap;
    protected Map<String, StateInfo> stateInfoMap;


    public StateServiceCacheDecorator(){
        cacheManager = CacheManager.getInstance();
        try {
            cacheManager.addCache(STATE_SERVICE_CACHE);

            //Add LifecycleInfo Map to cache
            lifecycleInfoMap = new HashMap<String, LifecycleInfo>();

            //Add StateInfo Map to cache
            stateInfoMap = new HashMap<String, StateInfo>();


        } catch (ObjectExistsException e) {
        }
    }


    @Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //LifecycleInfo is stored in cache using a map of lifecycle keys to LifeCycleInfo
        //This will attempt to retrieve from cached map, otherwise will call call the service.

        LifecycleInfo result = lifecycleInfoMap.get(lifecycleKey);
        if (result == null){
            result = getNextDecorator().getLifecycle(lifecycleKey, contextInfo);
            lifecycleInfoMap.put(lifecycleKey, result);
        }

        return result;
    }

    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(List<String> lifecycleKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //LifecycleInfo is stored in cache using a map of lifecycle keys to LifeCycleInfo
        //This will attempt to retreive from cache, if key not found will defer back to service and add any missing items
        //to the cache.

        //Attempt to build results from cache
        List<LifecycleInfo> results = new ArrayList<LifecycleInfo>();
        for (String lifeCycleKey:lifecycleKeys){
            LifecycleInfo lifecycleInfo = lifecycleInfoMap.get(lifeCycleKey);
            if (lifecycleInfo != null){
                results.add(lifecycleInfo);
            } else {
                //List could not be built from cache, need to retreive from service
                results.clear();
                break;
            }
        }

        //All results could not be retrieved from cache, retrieve from service and add missing items to cache.
        if (results.isEmpty()){
            results.addAll(getNextDecorator().getLifecyclesByKeys(lifecycleKeys, contextInfo));
            for (LifecycleInfo lifcycleInfo:results){
                if (!lifecycleInfoMap.containsKey(lifcycleInfo.getKey())){
                    lifecycleInfoMap.put(lifcycleInfo.getKey(),lifcycleInfo);
                }
            }
        }

        return results;
    }

    @Override
    public List<String> getLifecycleKeysByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLifecycleKeysByRefObjectUri", refObjectUri);

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getLifecycleKeysByRefObjectUri(refObjectUri, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>)result;
    }

    @Override
    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("searchForLifecycleKeys", criteria.toString());

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().searchForLifecycleKeys(criteria, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>)result;
    }

    @Override
    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //NOTE: This is duplicating storage of lifecycle info objects in the cache. Better solution?
        MultiKey cacheKey = new MultiKey("searchForLifecycles", criteria.toString());

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().searchForLifecycleKeys(criteria, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<LifecycleInfo>)result;
    }

    @Override
    public StateInfo getState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //StateInfo is stored in cache using a map of state keys to StateInfo
        //This will attempt to retrieve from cached map, otherwise will call call the service.

        StateInfo result = stateInfoMap.get(stateKey);
        if (result == null){
            result = getNextDecorator().getState(stateKey, contextInfo);
            stateInfoMap.put(stateKey, result);
        }

        return result;
    }

    @Override
    public List<StateInfo> getStatesByKeys(List<String> stateKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //StateInfo is stored in cache using a map of state keys to StateInfo
        //This will attempt to retrieve from cache, if key not found will defer back to service and add any missing items
        //to the cache.

        //Attempt to build results from cache
        List<StateInfo> results = new ArrayList<StateInfo>();
        for (String stateKey:stateKeys){
            StateInfo stateInfo = stateInfoMap.get(stateKey);
            if (stateInfo != null){
                results.add(stateInfo);
            } else {
                //List could not be built from cache, need to retreive from service
                results.clear();
                break;
            }
        }

        //All results could not be retrieved from cache, retrieve from service and add missing items to cache.
        if (results.isEmpty()){
            results.addAll(getNextDecorator().getStatesByKeys(stateKeys, contextInfo));
            for (StateInfo stateInfo:results){
                if (!stateInfoMap.containsKey(stateInfo.getKey())){
                    stateInfoMap.put(stateInfo.getKey(),stateInfo);
                }
            }
        }

        return results;
    }

    @Override
    public List<StateInfo> getStatesByLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //NOTE: This is duplicating storage of state info objects in the cache. Could iterate over stateInfoMap, but
        //only if all stateInfo objects were loaded into the cache.
        MultiKey cacheKey = new MultiKey("getStatesByLifecycle", lifecycleKey);

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().getStatesByLifecycle(lifecycleKey, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<StateInfo>)result;
    }

    @Override
    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("searchForStateKeys", criteria.toString());

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().searchForStateKeys(criteria, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>)result;
    }

    @Override
    public List<StateInfo> searchForStates(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //NOTE: This is duplicating storage of state info objects in the cache. Better Solution?
        MultiKey cacheKey = new MultiKey("searchForStates", criteria.toString());

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result = null;
        if (cachedResult == null) {
            result = getNextDecorator().searchForStates(criteria, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<StateInfo>)result;

    }
}
