/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may	obtain a copy of the License at
 *
 * 	http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.core.population.service.impl;


import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PopulationServiceMockImpl implements PopulationService, MockService {

    ///////////////////////////
    // Data Variables
    ///////////////////////////

    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, PopulationRuleInfo> populationRuleMap = new LinkedHashMap<String, PopulationRuleInfo>();

    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, PopulationInfo> populationMap = new LinkedHashMap<String, PopulationInfo>();

    // cache variable
    // The LinkedHashMap is just so the values come back in a predictable order
    private Map<String, PopulationCategoryInfo> populationCategoryMap = new LinkedHashMap<String, PopulationCategoryInfo>();

    // stores the mapping between population rule id and population infos (one to many) - the key is population rule
    // id, and value is the list of population infos
    private Map<String, ArrayList<PopulationInfo>> populationsPerRuleForAllRules = new LinkedHashMap<String, ArrayList<PopulationInfo>> ();

    // stores the mapping between population and population category. the key is the population id, the list is the population categories for that population id
    private Map<String, ArrayList<PopulationCategoryInfo>> populationCategoriesForPopulationMap = new LinkedHashMap<String, ArrayList<PopulationCategoryInfo>> ();

    ////////////////////////////////
    // FUNCTIONALS
    ////////////////////////////////

    @Override
    public Boolean isMemberAsOfDate(String personId, String populationId, Date date, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("isMemberAsOfDate has not been implemented");
    }

    @Override
    public List<String> getMembersAsOfDate(String populationId, Date date, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("getMembersAsOfDate has not been implemented");
    }

    @Override
    public PopulationInfo getPopulation(String populationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.populationMap.containsKey(populationId)) {
            throw new DoesNotExistException(populationId);
        }
        PopulationInfo populationInfo = this.populationMap.get (populationId);
        // update the rule fields in population info object
        try { setPopulationRuleFieldsForPopulation(populationInfo, contextInfo); } catch (Exception ignored) {}
        return populationInfo;
    }

    @Override
    public List<PopulationInfo> getPopulationsByIds(List<String> populationIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<PopulationInfo> list = new ArrayList<PopulationInfo> ();
        for (String id: populationIds) {
            PopulationInfo populationInfo = this.getPopulation(id, contextInfo);
            // update the rule fields in population info object
            try { setPopulationRuleFieldsForPopulation(populationInfo, contextInfo); } catch (Exception ignored) {}
            list.add (populationInfo);
        }
        return list;
    }

    @Override
    public List<String> getPopulationIdsByType(String populationTypeId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (PopulationInfo info: populationMap.values ()) {
            if (populationTypeId.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<PopulationInfo> getPopulationsForPopulationRule(String populationRuleId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!populationRuleMap.containsKey(populationRuleId)) throw new InvalidParameterException("Could not find population rule with id: " + populationRuleId);
        List<PopulationInfo> popsForRule = populationsPerRuleForAllRules.get(populationRuleId);
        if (popsForRule==null) { return new ArrayList<PopulationInfo>(); }
        // update the rule fields in population info objects
        for (PopulationInfo populationInfo : popsForRule) {
            try { setPopulationRuleFieldsForPopulation(populationInfo, contextInfo); } catch (Exception ignored) {}
        }
        return popsForRule;
    }

    @Override
    public List<String> searchForPopulationIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForPopulationIds has not been implemented");
    }

    @Override
    public List<PopulationInfo> searchForPopulations(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForPopulations has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(String validationTypeKey, PopulationInfo populationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public PopulationInfo createPopulation(PopulationInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        PopulationInfo copy = new PopulationInfo(populationInfo);
        if (copy.getId() == null) {
            copy.setId(populationMap.size() + 1 + "");
        }
        copy.setMeta(newMeta(contextInfo));
        populationMap.put(copy.getId(), copy);
        PopulationInfo populationInfoToReturn = new PopulationInfo(copy);
        // update the rule fields in population info object
        try { setPopulationRuleFieldsForPopulation(populationInfoToReturn, contextInfo); } catch (Exception ignored) {}
        return populationInfoToReturn;
    }

    @Override
    public PopulationInfo updatePopulation(String populationId, PopulationInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!populationId.equals (populationInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        PopulationInfo copy = new PopulationInfo(populationInfo);
        PopulationInfo old = this.getPopulation(populationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.populationMap .put(populationInfo.getId(), copy);
        PopulationInfo populationInfoToReturn = new PopulationInfo(copy);
        // update the rule fields in population info object
        try { setPopulationRuleFieldsForPopulation(populationInfoToReturn, contextInfo); } catch (Exception ignored) {}
        return populationInfoToReturn;
    }

    @Override
    public StatusInfo deletePopulation(String populationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.populationMap.remove(populationId) == null) {
            throw new DoesNotExistException(populationId);
        }
        return newStatus();
    }

    @Override
    public PopulationRuleInfo getPopulationRule(String populationRuleId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.populationRuleMap.containsKey(populationRuleId)) {
            throw new DoesNotExistException(populationRuleId);
        }
        return this.populationRuleMap.get (populationRuleId);
    }

    @Override
    public List<PopulationRuleInfo> getPopulationRulesByIds(List<String> populationRuleIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<PopulationRuleInfo> list = new ArrayList<PopulationRuleInfo> ();
        for (String id: populationRuleIds) {
            list.add (this.getPopulationRule(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getPopulationRuleIdsByType(String populationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (PopulationRuleInfo info: populationRuleMap.values ()) {
            if (populationTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public PopulationRuleInfo getPopulationRuleForPopulation(String populationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // go through each population info for each population rule.
        for (String ruleId : populationsPerRuleForAllRules.keySet()) {
            List<PopulationInfo> popsForRule = populationsPerRuleForAllRules.get(ruleId);
            for (PopulationInfo populationInfo : popsForRule) {
                if (populationInfo.getId().equals(populationId))
                    return getPopulationRule(ruleId, contextInfo);
            }
        }
        throw new DoesNotExistException("Could not find rule for population with id: " + populationId);
    }

    @Override
    public List<String> searchForPopulationRuleIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForPopulationRuleIds has not been implemented");
    }

    @Override
    public List<PopulationRuleInfo> searchForPopulationRules(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForPopulationRules has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(String validationTypeKey, PopulationRuleInfo populationInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public PopulationRuleInfo createPopulationRule(PopulationRuleInfo populationRuleInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        PopulationRuleInfo copy = new PopulationRuleInfo(populationRuleInfo);
        if (copy.getId() == null) {
            copy.setId(populationRuleMap.size() + 1 + "");
        }
        copy.setMeta(newMeta(contextInfo));
        populationRuleMap.put(copy.getId(), copy);
        return new PopulationRuleInfo(copy);
    }

    @Override
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!populationRuleId.equals (populationInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        PopulationRuleInfo copy = new PopulationRuleInfo(populationInfo);
        PopulationRuleInfo old = this.getPopulationRule(populationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.populationRuleMap .put(populationInfo.getId(), copy);
        return new PopulationRuleInfo(copy);
    }

    @Override
    public StatusInfo deletePopulationRule(String populationRuleId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.populationRuleMap.remove(populationRuleId) == null) {
            throw new DoesNotExistException(populationRuleId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo applyPopulationRuleToPopulation(String populationRuleId, String populationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        PopulationInfo populationInfo = getPopulation(populationId, contextInfo); // to check if population exists
        PopulationRuleInfo populationRuleInfo = getPopulationRule(populationRuleId, contextInfo); // to check if population rule exists
        // go through all the population rules and check their populations. If they contain this population, remove.
        for (String ruleId : populationsPerRuleForAllRules.keySet()) {
            ArrayList<PopulationInfo> popsForRule = populationsPerRuleForAllRules.get(ruleId);
            for (PopulationInfo info: popsForRule) {
                if (info.getId().equals(populationId)) {
                    // remove the population
                    popsForRule.remove(info);
                    populationsPerRuleForAllRules.put(ruleId, popsForRule);
                }
            }
        }

        // add this rule to this population
        ArrayList<PopulationInfo> popsForRule = populationsPerRuleForAllRules.get(populationRuleId);
        if (popsForRule==null) { popsForRule = new ArrayList<PopulationInfo>(); }
        popsForRule.add(populationInfo);
        populationsPerRuleForAllRules.put(populationRuleId, popsForRule);
        return newStatus();
    }

    @Override
    public StatusInfo removePopulationRuleFromPopulation(String populationRuleId, String populationId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        PopulationInfo populationInfo = getPopulation(populationId, contextInfo); // to check if population exists
        PopulationRuleInfo populationRuleInfo = getPopulationRule(populationRuleId, contextInfo); // to check if population rule exists
        ArrayList<PopulationInfo> popsForRule = populationsPerRuleForAllRules.get(populationRuleId);
        if (popsForRule!=null) {
            for (PopulationInfo info: popsForRule) {
                if (info.getId().equals(populationId)) {
                    popsForRule.remove(info);
                    populationsPerRuleForAllRules.put(populationRuleId, popsForRule);
                    return newStatus();
                }
            }
        }
        throw new DoesNotExistException("Could not find association of population rule with id: " + populationRuleId + " and population with id: " + populationId);
    }

    @Override
    public PopulationCategoryInfo getPopulationCategory(String populationCategoryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.populationCategoryMap.containsKey(populationCategoryId)) {
            throw new DoesNotExistException(populationCategoryId);
        }
        return this.populationCategoryMap.get (populationCategoryId);
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesByIds(List<String> populationCategoryIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<PopulationCategoryInfo> list = new ArrayList<PopulationCategoryInfo> ();
        for (String id: populationCategoryIds) {
            list.add (this.getPopulationCategory(id, contextInfo));
        }
        return list;
    }

    @Override
    public List<String> getPopulationCategoryIdsByType(String populationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        List<String> list = new ArrayList<String> ();
        for (PopulationCategoryInfo info: populationCategoryMap.values ()) {
            if (populationTypeKey.equals(info.getTypeKey())) {
                list.add (info.getId ());
            }
        }
        return list;
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesForPopulation(String populationId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (!this.populationCategoriesForPopulationMap.containsKey(populationId)) {
            return new ArrayList<PopulationCategoryInfo>();
        }
        return populationCategoriesForPopulationMap.get(populationId);
    }

    @Override
    public List<String> searchForPopulationCategoryIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForPopulationCategoryIds has not been implemented");
    }

    @Override
    public List<PopulationCategoryInfo> searchForPopulationCategories(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException ("searchForPopulationCategories has not been implemented");
    }

    @Override
    public List<ValidationResultInfo> validatePopulationCategory(String validationTypeKey, String populationCategoryTypeKey, PopulationCategoryInfo populationCategoryInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        // validate
        return new ArrayList<ValidationResultInfo> ();
    }

    @Override
    public PopulationCategoryInfo createPopulationCategory(String populationCategoryTypeKey, PopulationCategoryInfo populationCategoryInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
    {
        // create
        if (!populationCategoryTypeKey.equals (populationCategoryInfo.getTypeKey())) {
            throw new InvalidParameterException ("The type parameter does not match the type on the info object");
        }
        PopulationCategoryInfo copy = new PopulationCategoryInfo(populationCategoryInfo);
        if (copy.getId() == null) {
            copy.setId(populationCategoryMap.size() + 1 + "");
        }
        copy.setMeta(newMeta(contextInfo));
        populationCategoryMap.put(copy.getId(), copy);
        return new PopulationCategoryInfo(copy);
    }

    @Override
    public PopulationCategoryInfo updatePopulationCategory(String populationCategoryId, PopulationCategoryInfo populationInfo, ContextInfo contextInfo)
            throws DataValidationErrorException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
            ,ReadOnlyException
            ,VersionMismatchException
    {
        // update
        if (!populationCategoryId.equals (populationInfo.getId())) {
            throw new InvalidParameterException ("The id parameter does not match the id on the info object");
        }
        PopulationCategoryInfo copy = new PopulationCategoryInfo(populationInfo);
        PopulationCategoryInfo old = this.getPopulationCategory(populationInfo.getId(), contextInfo);
        if (!old.getMeta().getVersionInd().equals(copy.getMeta().getVersionInd())) {
            throw new VersionMismatchException(old.getMeta().getVersionInd());
        }
        copy.setMeta(updateMeta(copy.getMeta(), contextInfo));
        this.populationCategoryMap .put(populationInfo.getId(), copy);
        return new PopulationCategoryInfo(copy);
    }

    @Override
    public StatusInfo deletePopulationCategory(String populationCategoryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        if (this.populationCategoryMap.remove(populationCategoryId) == null) {
            throw new DoesNotExistException(populationCategoryId);
        }
        return newStatus();
    }

    @Override
    public StatusInfo addPopulationToPopulationCategory(String populationId, String populationCategoryId, ContextInfo contextInfo)
            throws AlreadyExistsException
            ,DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        PopulationCategoryInfo info = populationCategoryMap.get(populationCategoryId);
        ArrayList<PopulationCategoryInfo> populationCategoryInfos = populationCategoriesForPopulationMap.get(populationId);
        if (populationCategoryInfos==null || populationCategoryInfos.size()==0) { populationCategoryInfos = new ArrayList<PopulationCategoryInfo>(); }
        if (!populationCategoryInfos.contains(info)) {
            populationCategoryInfos.add(info);
        } else throw new AlreadyExistsException(populationCategoryId);
        populationCategoriesForPopulationMap.put(populationId, populationCategoryInfos); // store new updated relations
        return newStatus();
    }

    @Override
    public StatusInfo removePopulationFromPopulationCategory(String populationId, String populationCategoryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        PopulationCategoryInfo info = populationCategoryMap.get(populationCategoryId);
        ArrayList<PopulationCategoryInfo> populationCategoryInfos = populationCategoriesForPopulationMap.get(populationId);
        if (populationCategoryInfos!=null && populationCategoryInfos.size()>0) {
            if (populationCategoryInfos.contains(info)) {
                populationCategoryInfos.remove(info);
            } else throw new DoesNotExistException(populationCategoryId); // this category not found for population
        } else throw new DoesNotExistException(populationId); // no categories at all found for population
        populationCategoriesForPopulationMap.put(populationId, populationCategoryInfos); // store new updated relations
        return newStatus();
    }

    private StatusInfo newStatus() {
        StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);
        return status;
    }

    private MetaInfo newMeta(ContextInfo context) {
        MetaInfo meta = new MetaInfo();
        meta.setCreateId(context.getPrincipalId());
        meta.setCreateTime(new Date());
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(meta.getCreateTime());
        meta.setVersionInd("0");
        return meta;
    }

    private MetaInfo updateMeta(MetaInfo old, ContextInfo context) {
        MetaInfo meta = new MetaInfo(old);
        meta.setUpdateId(context.getPrincipalId());
        meta.setUpdateTime(new Date());
        meta.setVersionInd((Integer.parseInt(meta.getVersionInd()) + 1) + "");
        return meta;
    }

    private void setPopulationRuleFieldsForPopulation (PopulationInfo populationInfo, ContextInfo contextInfo)
        throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException {
        // look up the rule this Population is part of
        PopulationRuleInfo populationRuleInfo = getPopulationRuleForPopulation(populationInfo.getId(), contextInfo);

        // change all underlying population properties to match population rule properties
        populationInfo.setSortOrderTypeKeys(populationRuleInfo.getSortOrderTypeKeys());
        populationInfo.setSupportsGetMembers(populationRuleInfo.getSupportsGetMembers());
        populationInfo.setVariesByTime(populationRuleInfo.getVariesByTime());
    }

    @Override
    public void clear() {
        populationRuleMap.clear();
        populationMap.clear();
        populationCategoryMap.clear();
        populationsPerRuleForAllRules.clear();
        populationCategoriesForPopulationMap.clear();
    }
}

