package org.kuali.student.enrollment.util;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.principal.Principal;

/**
 * This class is used as a helper for the KIM Identity Service
 */
public class KSIdentityServiceHelper {

    public static String PRINCIPAL_CACHE =   "kimPrincipalCache";

    private IdentityService identityService;
    private CacheManager cacheManager;


    /**
     *  This method returns an entityId that corresponds to the principalId.
     *  uses the identity service getPrincipal method. The Principal object is cached
     *  the Entity ID is pulled from the Principal Object.
     */
    public String getEntityIdByPrincipalId(String principalId){
        String sRet = null;
        if(principalId == null || principalId.isEmpty()) return sRet;

        Element cachedResult = getCacheManager().getCache(PRINCIPAL_CACHE).get(principalId);
        if (cachedResult == null) {
            Principal p = getIdentityService().getPrincipal(principalId);
            getCacheManager().getCache(PRINCIPAL_CACHE).put(new Element(p.getPrincipalId(), p));
            sRet = p.getEntityId();
        } else {
            //Get cached data
            sRet = ((Principal) cachedResult.getValue()).getEntityId();
        }

        return sRet;
    }


    public IdentityService getIdentityService() {
        return identityService;
    }

    public void setIdentityService(IdentityService identityService) {
        this.identityService = identityService;
    }

    public CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
