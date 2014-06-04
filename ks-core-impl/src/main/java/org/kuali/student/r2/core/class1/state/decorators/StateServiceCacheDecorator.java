package org.kuali.student.r2.core.class1.state.decorators;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.class1.state.dto.LifecycleInfo;
import org.kuali.student.r2.core.class1.state.dto.StateInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for TypeService to add caching to select type service methods.
 *
 * WARNING: The cache decorator implementation does not currently take into account ContextInfo
 *
 */
public class StateServiceCacheDecorator extends StateServiceDecorator {
    private CacheManager cacheManager;


    protected static final String STATE_SERVICE_CACHE = "StateServiceCache";
    protected static final String GET_LIFECYCLE_BY_KEY = "getLifecycleByLifecycleKey";
    protected static final String GET_STATE_BY_KEY    = "getStateByStateKey";

    @Override
    public LifecycleInfo getLifecycle(String lifecycleKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey(GET_LIFECYCLE_BY_KEY, lifecycleKey);

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLifecycleKeysByRefObjectUri(lifecycleKey, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (LifecycleInfo)result;

    }

    @Override
    public List<LifecycleInfo> getLifecyclesByKeys(List<String> lifecycleKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //LifecycleInfo is stored in cache using a map of lifecycle keys to LifeCycleInfo
        //This will attempt to retreive from cache, if key not found will defer back to service and add any missing items
        //to the cache.

        //Attempt to build results from cache
        List<LifecycleInfo> results = new ArrayList<LifecycleInfo>();
        List<String> notInCacheList = new ArrayList<String>();        // add any ids not in the cache to this list
        for (String lifeCycleKey:lifecycleKeys){
            MultiKey cacheKey = new MultiKey(GET_LIFECYCLE_BY_KEY, lifeCycleKey);
            Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
            if (cachedResult != null){
                results.add((LifecycleInfo)cachedResult.getValue());
            } else {
                notInCacheList.add(lifeCycleKey);
            }
        }

        // at this point we've gotten everything from the cache and have a list of stuff we didn't find.
        // call the service to get the list of missing items. add them to the cache and the result list.
        if(!notInCacheList.isEmpty()){
            List<LifecycleInfo> newList =  getNextDecorator().getLifecyclesByKeys(notInCacheList, contextInfo);
            for(LifecycleInfo info : newList){
                MultiKey cacheKey = new MultiKey(GET_LIFECYCLE_BY_KEY, info.getKey());
                cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, info));
                results.add(info);
            }
        }

        return results;
    }

    @Override
    public List<String> getLifecycleKeysByRefObjectUri(String refObjectUri, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        MultiKey cacheKey = new MultiKey("getLifecycleKeysByRefObjectUri", refObjectUri);

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLifecycleKeysByRefObjectUri(refObjectUri, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<String>)result;
    }

//    Commented out all the caching for the searchForXXX methods because they don't work
//            See JIRA https://jira.kuali.org/browse/KSENROLL-2952
// 
//    @Override
//    public List<String> searchForLifecycleKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
//        MultiKey cacheKey = new MultiKey("searchForLifecycleKeys", criteria.toString());
//        System.out.println ("cachekey=" + cacheKey.toString ());
//        System.out.println ("criteria=" + criteria.toString ());
//        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
//        Object result = null;
//        if (cachedResult == null) {
//            result = getNextDecorator().searchForLifecycleKeys(criteria, contextInfo);
//            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
//            System.out.println ("added data to the cache");            
//        } else {
//            System.out.println ("got data from the cache");
//            result = cachedResult.getValue();
//        }
//
//        return (List<String>)result;
//    }
//
//    @Override
//    public List<LifecycleInfo> searchForLifecycles(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
//        List<String> keys = this.searchForLifecycleKeys(criteria, contextInfo);
//        try {
//            return this.getLifecyclesByKeys(keys, contextInfo);
//        } catch (DoesNotExistException ex) {
//            throw new OperationFailedException ("unexpected", ex);
//        }
//        // NORM: 8/6/2012 replaced below which I don't understand and is returning a list of keys with the above
////        //NOTE: This is duplicating storage of lifecycle info objects in the cache. Better solution?
////        MultiKey cacheKey = new MultiKey("searchForLifecycles", criteria.toString());
////
////        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
////        Object result = null;
////        if (cachedResult == null) {
////            result = getNextDecorator().searchForLifecycleKeys(criteria, contextInfo);
////            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
////        } else {
////            result = cachedResult.getValue();
////        }
////
////        return (List<LifecycleInfo>)result;
//    }

    @Override
    public StateInfo getState(String stateKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //StateInfo is stored in cache using a map of state keys to StateInfo
        //This will attempt to retrieve from cached map, otherwise will call call the service.

        MultiKey cacheKey = new MultiKey(GET_STATE_BY_KEY, stateKey);

        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getState(stateKey, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (StateInfo)result;
    }

    @Override
    public List<StateInfo> getStatesByKeys(List<String> stateKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //StateInfo is stored in cache using a map of state keys to StateInfo
        //This will attempt to retrieve from cache, if key not found will defer back to service and add any missing items
        //to the cache.

        //Attempt to build results from cache
        List<StateInfo> results = new ArrayList<StateInfo>();
        List<String> notInCacheList = new ArrayList<String>();        // add any ids not in the cache to this list
        for (String stateKey:stateKeys){
            MultiKey cacheKey = new MultiKey(GET_STATE_BY_KEY, stateKey);
            Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
            if (cachedResult != null){
                results.add((StateInfo)cachedResult.getValue());
            } else {
                notInCacheList.add(stateKey);
            }
        }

        // at this point we've gotten everything from the cache and have a list of stuff we didn't find.
        // call the service to get the list of missing items. add them to the cache and the result list.
        if(!notInCacheList.isEmpty()){
            List<StateInfo> newList =  getNextDecorator().getStatesByKeys(notInCacheList, contextInfo);
            for(StateInfo info : newList){
                MultiKey cacheKey = new MultiKey(GET_STATE_BY_KEY, info.getKey());
                cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, info));
                results.add(info);
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
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getStatesByLifecycle(lifecycleKey, contextInfo);
            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (List<StateInfo>)result;
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

    //
//    Commented out all the caching for the searchForXXX methods.
//            See JIRA https://jira.kuali.org/browse/KSENROLL-2952
// 
//testLifecycleCache
//1    [main] WARN  net.sf.ehcache.config.ConfigurationFactory  - No configuration found. Configuring ehcache from ehcache-failsafe.xml  found in the classpath: jar:file:/C:/Users/nwright/.m2/repository/net/sf/ehcache/ehcache-core/2.5.0/ehcache-core-2.5.0.jar!/ehcache-failsafe.xml
//cachekey=MultiKey[searchForLifecycleKeys, org.kuali.rice.core.api.criteria.QueryByCriteria@3fb6101e[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]]
//criteria=org.kuali.rice.core.api.criteria.QueryByCriteria@3fb6101e[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]
//added data to the cache
//cachekey=MultiKey[searchForLifecycleKeys, org.kuali.rice.core.api.criteria.QueryByCriteria@69d6065[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]]
//criteria=org.kuali.rice.core.api.criteria.QueryByCriteria@69d6065[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]
//added data to the cache
//cachekey=MultiKey[searchForLifecycleKeys, org.kuali.rice.core.api.criteria.QueryByCriteria@69d95da8[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]]
//criteria=org.kuali.rice.core.api.criteria.QueryByCriteria@69d95da8[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]
//added data to the cache
//cachekey=MultiKey[searchForLifecycleKeys, org.kuali.rice.core.api.criteria.QueryByCriteria@3a747fa2[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]]
//criteria=org.kuali.rice.core.api.criteria.QueryByCriteria@3a747fa2[predicate=<null>,startAtIndex=<null>,maxResults=<null>,countFlag=NONE,_futureElements=<null>]
//added data to the cache
        
//    @Override
//    public List<String> searchForStateKeys(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
//        MultiKey cacheKey = new MultiKey("searchForStateKeys", criteria.toString());
//
//        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
//        Object result = null;
//        if (cachedResult == null) {
//            result = getNextDecorator().searchForStateKeys(criteria, contextInfo);
//            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
//        } else {
//            result = cachedResult.getValue();
//        }
//
//        return (List<String>)result;
//    }
//
//    @Override
//    public List<StateInfo> searchForStates(QueryByCriteria criteria, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
//        //NOTE: This is duplicating storage of state info objects in the cache. Better Solution?
//        MultiKey cacheKey = new MultiKey("searchForStates", criteria.toString());
//
//        Element cachedResult = cacheManager.getCache(STATE_SERVICE_CACHE).get(cacheKey);
//        Object result = null;
//        if (cachedResult == null) {
//            result = getNextDecorator().searchForStates(criteria, contextInfo);
//            cacheManager.getCache(STATE_SERVICE_CACHE).put(new Element(cacheKey, result));
//        } else {
//            result = cachedResult.getValue();
//        }
//
//        return (List<StateInfo>)result;
//
//    }
}
