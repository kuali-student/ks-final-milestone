package org.kuali.student.enrollment.class1.lpr.service.decorators;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.*;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LprServiceCacheDecorator extends LprServiceDecorator {

    private static String cacheName = "lprCache";
    private CacheManager cacheManager;

    private final String LPR_ID = "lprId";
    private final String LUI_ID = "luiId";
    private final String LPR_TYPE_KEY = "lprTypeKey";
    private final String LUI_TYPE_KEY = "luiTypeKey";
    private final String PERSON_ID = "personId";

    public CacheManager getCacheManager() {
        if(cacheManager == null){
            cacheManager = CacheManager.getInstance();
        }
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public List<LprInfo> getLprsByIds(@WebParam(name = "lprIds") List<String> lprIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        Cache cache = getCacheManager().getCache(cacheName);
        List<LprInfo> lprInfos = new ArrayList<LprInfo>(lprIds.size());
        List<String> unCachedLprIds = new ArrayList<String>(lprIds.size());

        // Check the cache for the ids
        for(String lprId : lprIds) {
            MultiKey cacheKey = new MultiKey(LPR_ID,lprId);
            Element cachedResult = cache.get(cacheKey);
            if(cachedResult!=null) {
                //If the id was found in the cache then use it
                lprInfos.add((LprInfo)cachedResult.getValue());
            } else {
                //Otherwise save this id as one that needs to be looked up
                unCachedLprIds.add(lprId);
            }
        }

        //Call the underlying service to get the remainder
        if(unCachedLprIds.size()>0) {
            List<LprInfo> uncachedLprInfos = getNextDecorator().getLprsByIds(unCachedLprIds,contextInfo);
            for(LprInfo lprInfo : uncachedLprInfos) {
                MultiKey cacheKey = new MultiKey(LPR_ID,lprInfo.getId());
                cache.put(new Element(cacheKey,lprInfo));
            }
            lprInfos.addAll(uncachedLprInfos);
        }

        //printLprInfoList(lprInfos,"getLprsByIds(lprIds="+lprIds+")");

        return lprInfos;

    }

    @Override
    public LprInfo getLpr(@WebParam(name = "lprId") String lprId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(LPR_ID, lprId);

        Element cachedResult = getCacheManager().getCache(cacheName).get(cacheKey);
        Object result;
        if (cachedResult == null) {
            result = getNextDecorator().getLpr(lprId, contextInfo);
            getCacheManager().getCache(cacheName).put(new Element(cacheKey, result));
        } else {
            result = cachedResult.getValue();
        }

        return (LprInfo)result;
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLuiType(String personId, String luiTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(PERSON_ID, personId,LUI_TYPE_KEY,luiTypeKey);

        Cache cache = getCacheManager().getCache(cacheName);

        Element cachedResult = cache.get(cacheKey);

        List<String> lprIds = null;
        List<LprInfo> lprInfos = null;

        if(cachedResult!=null) {

            lprIds = (List<String>)cachedResult.getValue();

            lprInfos = getLprsByIds(lprIds,contextInfo);

        } else {

            lprInfos = getNextDecorator().getLprsByPersonAndLuiType(personId, luiTypeKey, contextInfo);

            _addLprInfosInCache(lprInfos);

            lprIds = _getLprIdsFromLprInfos(lprInfos);

            cache.put(new Element(cacheKey,lprIds));

        }

        //printLprInfoList(lprInfos,"getLprsByPersonAndLuiType(personId="+personId+",luiTypeKey="+luiTypeKey+")");

        return lprInfos;
    }

    @Override
    public List<LprInfo> getLprsByPersonAndLui(String personId, String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(PERSON_ID, personId,LUI_ID,luiId);

        Cache cache = getCacheManager().getCache(cacheName);

        Element cachedResult = cache.get(cacheKey);

        List<String> lprIds = null;
        List<LprInfo> lprInfos = null;

        if(cachedResult!=null) {

            lprIds = (List<String>)cachedResult.getValue();
            lprInfos = getLprsByIds(lprIds,contextInfo);

        } else {

            lprInfos = getNextDecorator().getLprsByPersonAndLui(personId, luiId, contextInfo);

            _addLprInfosInCache(lprInfos);

            lprIds = _getLprIdsFromLprInfos(lprInfos);

            cache.put(new Element(cacheKey,lprIds));

        }

        return lprInfos;
    }

    @Override
    public List<LprInfo> getLprsByPerson(String personId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(PERSON_ID,personId);

        Cache cache = getCacheManager().getCache(cacheName);

        Element cachedResult = cache.get(cacheKey);

        List<String> lprIds = null;
        List<LprInfo> lprInfos = null;

        if(cachedResult!=null) {

            lprIds = (List<String>)cachedResult.getValue();
            lprInfos = getLprsByIds(lprIds,contextInfo);

        } else {

            lprInfos = getNextDecorator().getLprsByPerson(personId, contextInfo);

            _addLprInfosInCache(lprInfos);

            lprIds = _getLprIdsFromLprInfos(lprInfos);

            cache.put(new Element(cacheKey,lprIds));

        }

        //printLprInfoList(lprInfos,"getLprsByPerson(personId="+personId+")");

        return lprInfos;
    }

    @Override
    public List<LprInfo> getLprsByLuiAndType(String luiId, String lprTypeKey, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(LUI_ID,luiId,LPR_TYPE_KEY,lprTypeKey);

        Cache cache = getCacheManager().getCache(cacheName);

        Element cachedResult = cache.get(cacheKey);

        List<String> lprIds = null;
        List<LprInfo> lprInfos = null;

        if(cachedResult!=null) {

            lprIds = (List<String>)cachedResult.getValue();
            lprInfos = getLprsByIds(lprIds,contextInfo);

        } else {

            lprInfos = getNextDecorator().getLprsByLuiAndType(luiId, lprTypeKey, contextInfo);

            _addLprInfosInCache(lprInfos);

            lprIds = _getLprIdsFromLprInfos(lprInfos);

            cache.put(new Element(cacheKey,lprIds));

        }

        //printLprInfoList(lprInfos,"getLprsByLuiAndType(luiId="+luiId+",lprTypeKey="+lprTypeKey+")");

        return lprInfos;
    }

    @Override
    public List<LprInfo> getLprsByLui(String luiId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        MultiKey cacheKey = new MultiKey(LUI_ID,luiId);

        Cache cache = getCacheManager().getCache(cacheName);

        Element cachedResult = cache.get(cacheKey);

        List<String> lprIds = null;
        List<LprInfo> lprInfos = null;

        if(cachedResult!=null) {

            lprIds = (List<String>)cachedResult.getValue();
            lprInfos = getLprsByIds(lprIds,contextInfo);

        } else {

            lprInfos = getNextDecorator().getLprsByLui(luiId, contextInfo);

            _addLprInfosInCache(lprInfos);

            lprIds = _getLprIdsFromLprInfos(lprInfos);

            cache.put(new Element(cacheKey,lprIds));

        }

        //printLprInfoList(lprInfos,"getLprsByLui(luiId="+luiId+")");

        return lprInfos;

    }

    private void printLprInfoList(List<LprInfo> lprInfos,String name) {

        for(int i=0 ; i<lprInfos.size() ; i++) {
              System.out.println("JLRK -> ["+name+"]["+i+"]="+lprInfos.get(i));
        }

    }

    @Override
    public List<LprInfo> getLprsByLuis(
            @WebParam(name = "luiIds") List<String> luiIds,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        Cache cache = getCacheManager().getCache(cacheName);
        List<String> lprIds = new ArrayList<String>();
        List<String> unCachedLuiIds = new ArrayList<String>(luiIds.size());

        // Check the cache for the ids
        for(String luiId : luiIds) {
            MultiKey cacheKey = new MultiKey(LUI_ID,luiId);
            Element cachedResult = cache.get(cacheKey);
            if(cachedResult!=null) {
                //If the id was found in the cache then use it
                lprIds.addAll((List<String>)cachedResult.getValue());
            } else {
                //Otherwise save this id as one that needs to be looked up
                unCachedLuiIds.add(luiId);
            }
        }

        List<LprInfo> lprInfos = getLprsByIds(lprIds,contextInfo);

        //Call the underlying service to get the remainder
        if(unCachedLuiIds.size()>0) {

            List<LprInfo> uncachedLprInfos = getNextDecorator().getLprsByLuis(unCachedLuiIds,contextInfo);

            _addLprInfosInCache(uncachedLprInfos);

            lprInfos.addAll(uncachedLprInfos);
        }

        return  lprInfos;

    }

    private List<MultiKey> _getMultiKeyByLprInfo(LprInfo lprInfo) {

        List<MultiKey> lstMultiKeys = new ArrayList<MultiKey>();
        lstMultiKeys.add(new MultiKey(LUI_ID, lprInfo.getLuiId()));
        lstMultiKeys.add(new MultiKey(LUI_ID, lprInfo.getLuiId(),LPR_TYPE_KEY,lprInfo.getTypeKey()));
        lstMultiKeys.add(new MultiKey(PERSON_ID, lprInfo.getPersonId()));
        lstMultiKeys.add(new MultiKey(PERSON_ID, lprInfo.getPersonId(),LUI_TYPE_KEY,lprInfo.getTypeKey()));
        lstMultiKeys.add(new MultiKey(PERSON_ID, lprInfo.getPersonId(),LUI_ID, lprInfo.getLuiId()));

        return lstMultiKeys;

    }

    private List<String> _getLprIdsFromLprInfos(List<LprInfo> lprInfos) {
        List<String> lprIds = new ArrayList<String>(lprInfos.size());
        for(LprInfo lprInfo : lprInfos) {
            lprIds.add(lprInfo.getId());
        }
        return lprIds;
    }

    private void _addLprInfosInCache(List<LprInfo> lprInfos) {

        Cache cache = getCacheManager().getCache(cacheName);

        for(LprInfo lprInfo : lprInfos) {

            MultiKey lprIdCacheKey = new MultiKey(LPR_ID, lprInfo.getId());

            if(!cache.isKeyInCache(lprIdCacheKey)) {

                cache.put(new Element(lprIdCacheKey,lprInfo));

            }
        }

    }

    private void _deleteLprInfoInCache(LprInfo lprInfo) {

        Cache cache = getCacheManager().getCache(cacheName);

        MultiKey lprIdCacheKey = new MultiKey(LPR_ID, lprInfo.getId());

        if(cache.isKeyInCache(lprIdCacheKey)) {

            List<MultiKey> lstMultiKeys = _getMultiKeyByLprInfo(lprInfo);

            for(MultiKey cacheKey: lstMultiKeys) {

                if(cache.isKeyInCache(cacheKey)) {
                    cache.remove(cacheKey);
                }

            }

            cache.remove(lprIdCacheKey);

        }

    }

    @Override
    public LprInfo createLpr(String personId, String luiId, String lprTypeKey, LprInfo lprInfo, ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {

        LprInfo result = getNextDecorator().createLpr(personId, luiId, lprTypeKey, lprInfo, contextInfo);

        MultiKey lprIdCacheKey = new MultiKey(LPR_ID, lprInfo.getId());

        Cache cache = getCacheManager().getCache(cacheName);

        cache.put(new Element(lprIdCacheKey,result));

        List<MultiKey> lstMultiKeys = _getMultiKeyByLprInfo(result);

        for(MultiKey cacheKey: lstMultiKeys) {

            Element cachedResult = cache.get(cacheKey);

            if(cachedResult!=null) {

                List<String> lprIds = (List<String>)cachedResult.getValue();
                lprIds.add(result.getId());

                cache.remove(cacheKey);
                cache.put(new Element(cacheKey,lprIds));

            }

        }
        return result;
    }



    @Override
    public LprInfo updateLpr(String lprId, LprInfo lprInfo, ContextInfo contextInfo) throws DoesNotExistException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {

        LprInfo result = getNextDecorator().updateLpr(lprId, lprInfo, contextInfo);

        Cache cache = getCacheManager().getCache(cacheName);

        MultiKey lprIdCacheKey = new MultiKey(LPR_ID, lprInfo.getId());

        List<MultiKey> lstMultiKeys = null;
        Element cachedResult = cache.get(lprIdCacheKey);

        if(cachedResult!=null) {

            LprInfo oldLprInfo = (LprInfo)cachedResult.getValue();

            _deleteLprInfoInCache(oldLprInfo);

        }

        cache.put(new Element(lprIdCacheKey,lprInfo));

        lstMultiKeys = _getMultiKeyByLprInfo(lprInfo);

        for(MultiKey cacheKey: lstMultiKeys) {

            cachedResult = cache.get(cacheKey);

            if(cachedResult!=null) {

                List<String> lprIds = (List<String>)cachedResult.getValue();
                lprIds.add(lprId);

                cache.remove(cacheKey);
                cache.put(new Element(cacheKey,lprIds));

            }

        }

        return result;
    }

    @Override
    public StatusInfo deleteLpr(String lprId, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        StatusInfo result = getNextDecorator().deleteLpr(lprId, contextInfo);

        if(result.getIsSuccess()) {

            Cache cache = getCacheManager().getCache(cacheName);

            MultiKey cacheKey = new MultiKey(LPR_ID, lprId);

            Element cachedResult = cache.get(cacheKey);

            if(cachedResult!=null) {

                LprInfo lprInfo = (LprInfo)cachedResult.getValue();
                _deleteLprInfoInCache(lprInfo);

            }

        }

        return result;

    }

}
