/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.class1.process;


import org.kuali.rice.core.api.criteria.AndPredicate;
import org.kuali.rice.core.api.criteria.LikePredicate;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.jws.WebParam;
import java.util.*;

public class ProcessPocPopulationServiceMockImpl implements PopulationService {

    private static Map<String, PopulationInfo> populations;
    private static Map<String, Set<String>> caches;
    private List<PopulationInfo> populationInfos;

    public ProcessPocPopulationServiceMockImpl() {
        initialize();
    }

    private void initialize() {
        try {
            populations = new HashMap<String, PopulationInfo>();
            caches = new HashMap<String, Set<String>>();

            final String ALL_STUDENTS = PopulationServiceConstants.EVERYONE_POPULATION_KEY;
            final String SUMMER_ONLY_STUDENTS = PopulationServiceConstants.SUMMER_ONLY_STUDENTS_POPULATION_KEY;
            final String SENIOR_ONLY_STUDENTS = "kuali.population.senior.only.student";
            final String ATHLETES_ONLY_STUDENTS = "kuali.population.athletes.only.student";

            PopulationInfo allStudentsPopulation = new PopulationInfo();
            allStudentsPopulation.setKey(ALL_STUDENTS);
            allStudentsPopulation.setName("All students");
            RichTextInfo allStudDesc = new RichTextInfo();
            allStudDesc.setPlain("All students population");
            allStudentsPopulation.setDescr(allStudDesc);
            createPopulation(allStudentsPopulation, new ContextInfo());

            PopulationInfo summerOnlyStudentsPopulation = new PopulationInfo();
            summerOnlyStudentsPopulation.setKey(SUMMER_ONLY_STUDENTS);
            summerOnlyStudentsPopulation.setName("Summer only students");
            RichTextInfo summerDesc = new RichTextInfo();
            summerDesc.setPlain("Summer only students population");
            summerOnlyStudentsPopulation.setDescr(summerDesc);
            createPopulation(summerOnlyStudentsPopulation, new ContextInfo());

            PopulationInfo seniorsOnlyStudentsPopulation = new PopulationInfo();
            seniorsOnlyStudentsPopulation.setKey(SENIOR_ONLY_STUDENTS);
            seniorsOnlyStudentsPopulation.setName("Senior students");
            RichTextInfo seniorsDesc = new RichTextInfo();
            seniorsDesc.setPlain("Senior only students");
            seniorsOnlyStudentsPopulation.setDescr(seniorsDesc);
            createPopulation(seniorsOnlyStudentsPopulation, new ContextInfo());

            PopulationInfo athletesOnlyStudentsPopulation = new PopulationInfo();
            athletesOnlyStudentsPopulation.setKey(ATHLETES_ONLY_STUDENTS);
            athletesOnlyStudentsPopulation.setName("Athletes only students");
            RichTextInfo athletesDesc = new RichTextInfo();
            athletesDesc.setPlain("Athletes students population");
            athletesOnlyStudentsPopulation.setDescr(athletesDesc);
            createPopulation(athletesOnlyStudentsPopulation, new ContextInfo());


            caches.get(SUMMER_ONLY_STUDENTS).add("2155");

            caches.get(ALL_STUDENTS).addAll(caches.get(SUMMER_ONLY_STUDENTS));
            caches.get(ALL_STUDENTS).add("2005");
            caches.get(ALL_STUDENTS).add("2016");
            caches.get(ALL_STUDENTS).add("2132");
            caches.get(ALL_STUDENTS).add("2166");
            caches.get(ALL_STUDENTS).add("2272");
            caches.get(ALL_STUDENTS).add("2374");
            caches.get(ALL_STUDENTS).add("2397");
            caches.get(ALL_STUDENTS).add("2406");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Boolean isMember(@WebParam(name = "personId") String personId, @WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == personId || 0 == personId.length()) {
            throw new MissingParameterException("personId");
        }
        if (null == populationKey || 0 == populationKey.length()) {
            throw new MissingParameterException("populationKey");
        }

        Set<String> cache = caches.get(populationKey);
        if (null == cache) {
            throw new DoesNotExistException("populationKey '" + populationKey + "' not found");
        }
        return cache.contains(personId);
    }

    @Override
    public List<String> getMembers(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == populationKey || 0 == populationKey.length()) {
            throw new MissingParameterException("populationKey");
        }

        Set<String> cache = caches.get(populationKey);
        if (null == cache) {
            throw new DoesNotExistException("populationKey '" + populationKey + "' not found");
        }
        return new ArrayList<String>(cache);
    }

    @Override
    public PopulationInfo getPopulation(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == populationKey || 0 == populationKey.length()) {
            throw new MissingParameterException("populationKey");
        }

        PopulationInfo population = populations.get(populationKey);
        if (null == population) {
            throw new DoesNotExistException("populationKey '" + populationKey + "' not found");
        }
        return population;
    }

    @Override
    public List<PopulationInfo> getPopulationsByIds(@WebParam(name = "populationKeys") List<String> populationKeys, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (0 == populationKeys.size()) {
            throw new MissingParameterException("populationKeys");
        }

        List<PopulationInfo> result = new ArrayList<PopulationInfo>();
        for (String populationKey : populationKeys) {
            PopulationInfo population = populations.get(populationKey);
            if (null == population) {
                throw new DoesNotExistException("populationKey '" + populationKey + "' not found");
            }
            result.add(population);
        }
        return result;
    }

    @Override
    public List<String> getPopulationKeysByType(@WebParam(name = "populationTypeId") String populationTypeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationInfo> getPopulationsForPopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> searchForPopulationKeys(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationInfo> searchForPopulations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        //throw new OperationFailedException("Method not implemented.");
        String name="",desc="";
        if(criteria == null){
            throw new MissingParameterException("SearchCriteria is null");
        }
        AndPredicate predicate = (AndPredicate)criteria.getPredicate();
        Set<Predicate> predicates = predicate.getPredicates();
        for(Predicate simplePredicate:predicates){
            if(((LikePredicate)simplePredicate).getPropertyPath().equalsIgnoreCase("name")){
                name = ((LikePredicate)simplePredicate).getValue().getValue().toString();
            } else{
                desc = ((LikePredicate)simplePredicate).getValue().getValue().toString();
            }
        }
        List<PopulationInfo> populationInfos = new ArrayList<PopulationInfo>();
        for(String key:populations.keySet()){
            if(key!=null){
                PopulationInfo populationInfo = populations.get(key);
                if(populationInfo.getName().toLowerCase().indexOf(name.toLowerCase())>=0 && populationInfo.getDescr().getPlain().toLowerCase().indexOf(desc.toLowerCase())>=0){
                    populationInfos.add(populationInfo);
                }
            }
        }
        return populationInfos;
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(@WebParam(name = "validationTypeId") String validationTypeId, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationInfo createPopulation(@WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        PopulationInfo population = populations.get(populationInfo.getKey());
        if (null != population) {
            throw new AlreadyExistsException("populationKey '" + populationInfo.getKey() + "' already exists");
        }
        populations.put(populationInfo.getKey(), populationInfo);
        caches.put(populationInfo.getKey(), new HashSet<String>());
        return populationInfo;
    }

    @Override
    public PopulationInfo updatePopulation(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo deletePopulation(@WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == populationKey || 0 == populationKey.length()) {
            throw new MissingParameterException("populationKey");
        }

        PopulationInfo population = populations.get(populationKey);
        if (null == population) {
            throw new DoesNotExistException("populationKey '" + populationKey + "' not found");
        }
        populations.remove(populationKey);
        caches.remove(populationKey);
        return new StatusInfo();
    }

    @Override
    public PopulationRuleInfo getPopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationRuleInfo> getPopulationRulesByIds(@WebParam(name = "populationRuleIds") List<String> populationRuleIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> getPopulationRuleIdsByType(@WebParam(name = "populationTypeKey") String populationTypeKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationRuleInfo getPopulationRuleForPopulation(@WebParam(name = "populationid") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> searchForPopulationRuleIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationRuleInfo> searchForPopulationRules(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(@WebParam(name = "validationTypeKey") String validationTypeKey, @WebParam(name = "populationRuleInfo") PopulationRuleInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationRuleInfo createPopulationRule(@WebParam(name = "populationInfo") PopulationRuleInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationRuleInfo updatePopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationInfo") PopulationRuleInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo deletePopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo applyPopulationRuleToPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo removePopulationRuleFromPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationKey") String populationKey, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }


}
