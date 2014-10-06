/**
 * Copyright 2014 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by Paul Richardson on 9/3/14
 */
package org.kuali.student.enrollment.class2.courseoffering.krms.termresolver.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.kuali.rice.krms.api.engine.TermResolver;
import org.kuali.student.enrollment.courseoffering.dto.RegistrationGroupInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Support class for Key Date term resolvers
 *
 * @author Kuali Student Team
 */
public abstract class KeyDateTermResolverSupport<T> implements TermResolver<T> {

    private LuiService luiService;
    private AtpService atpService;

    private CacheManager cacheManager;
    private Cache cache;

    private static final String KEYDATE_CACHE = "keydateCache";

    protected Date getEndDateForKeydateType(ContextInfo contextInfo, RegistrationGroupInfo regGroupInfo,
                                            String keydateTypeParameter) throws MissingParameterException,
            PermissionDeniedException, InvalidParameterException, OperationFailedException, DoesNotExistException {
        Date endDate = null;
        List<MilestoneInfo> mstones = getMilestones(contextInfo, regGroupInfo, keydateTypeParameter);
        for (MilestoneInfo mstone : mstones) {
            if (mstone.getEndDate() != null) {
                if (endDate == null || endDate.before(mstone.getEndDate())) {
                    endDate = mstone.getEndDate();
                }
            }
        }
        return endDate;
    }

    @SuppressWarnings("unchecked")
    protected List<MilestoneInfo> getMilestones(ContextInfo contextInfo, RegistrationGroupInfo regGroupInfo,
                                                String keydateTypeParameter)
            throws PermissionDeniedException, MissingParameterException, InvalidParameterException,
            OperationFailedException, DoesNotExistException {

        MultiKey cacheKey = new MultiKey(regGroupInfo.getId(),keydateTypeParameter);
        Element cachedValue = getCache().get(cacheKey);
        List<MilestoneInfo> milestones;
        if (cachedValue == null) {
            milestones = lookupMilestones(contextInfo, regGroupInfo, keydateTypeParameter);
            getCache().put(new Element(cacheKey, milestones));
        } else {
            milestones = (List<MilestoneInfo>) cachedValue.getValue();
        }

        return milestones;
    }

    private List<MilestoneInfo> lookupMilestones(ContextInfo contextInfo, RegistrationGroupInfo regGroupInfo, String keydateTypeParameter) throws PermissionDeniedException, MissingParameterException, InvalidParameterException, OperationFailedException, DoesNotExistException {

        String termId;
        // getting CO and AOs for the RegGroup to find the correct term
        LuiInfo coLui = getLuiService().getLui(regGroupInfo.getCourseOfferingId(), contextInfo);
        List<LuiInfo> aoLuis = getLuiService().getLuisByIds(regGroupInfo.getActivityOfferingIds(), contextInfo);

        // We have CO and AO terms.  There is special business logic to determine which term to use.
        // The logic: find out if all AO terms are the same, and if so use the AO term, else use CO term
        if(isAllAtpIdsTheSame(aoLuis)){
            //Get(0) is appropriate here since we are testing that they are all the same as part of business logic
            termId = aoLuis.get(0).getAtpId();
        } else {
            termId = coLui.getAtpId();
        }

        // Milestones store start and end date information. All KeyDates are Milestones
        return getAtpService().getMilestonesByTypeForAtp(termId, keydateTypeParameter, contextInfo);
    }

    // helper method that takes in a list of luis and returns if all atpIds are the same
    private boolean isAllAtpIdsTheSame(List<LuiInfo> l) {
        Set<String> set = new HashSet<>(l.size());
        for (LuiInfo o : l) {
            if (set.isEmpty()) {
                set.add(o.getAtpId());
            } else {
                if (set.add(o.getAtpId())) {
                    return false;
                }
            }
        }
        return true;
    }

    private Cache getCache() {
        if (cache == null) {
            cache = cacheManager.getCache(KEYDATE_CACHE);
        }
        return cache;
    }

    public LuiService getLuiService() {
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
