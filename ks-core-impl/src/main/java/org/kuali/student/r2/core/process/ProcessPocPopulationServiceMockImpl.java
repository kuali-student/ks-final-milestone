/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;


import org.kuali.rice.core.api.criteria.AndPredicate;
import org.kuali.rice.core.api.criteria.LikePredicate;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.common.mock.MockService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationCategoryInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessPocPopulationServiceMockImpl implements PopulationService, MockService {

    private Map<String, PopulationInfo> populations;
    private Map<String, Set<String>> caches;

    public ProcessPocPopulationServiceMockImpl() {
        initialize();
    }

    
    @Override
	public void clear() {
    	
    	if (this.caches != null)
    		this.caches.clear();
		
    	if (this.populations != null)
			this.populations.clear();
		
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
            allStudentsPopulation.setId(ALL_STUDENTS);
            allStudentsPopulation.setName("All students");
            RichTextInfo allStudDesc = new RichTextInfo();
            allStudDesc.setPlain("All students population");
            allStudentsPopulation.setDescr(allStudDesc);
            createPopulation(allStudentsPopulation, new ContextInfo());

            PopulationInfo summerOnlyStudentsPopulation = new PopulationInfo();
            summerOnlyStudentsPopulation.setId(SUMMER_ONLY_STUDENTS);
            summerOnlyStudentsPopulation.setName("Summer only students");
            RichTextInfo summerDesc = new RichTextInfo();
            summerDesc.setPlain("Summer only students population");
            summerOnlyStudentsPopulation.setDescr(summerDesc);
            createPopulation(summerOnlyStudentsPopulation, new ContextInfo());

            PopulationInfo seniorsOnlyStudentsPopulation = new PopulationInfo();
            seniorsOnlyStudentsPopulation.setId(SENIOR_ONLY_STUDENTS);
            seniorsOnlyStudentsPopulation.setName("Senior students");
            RichTextInfo seniorsDesc = new RichTextInfo();
            seniorsDesc.setPlain("Senior only students");
            seniorsOnlyStudentsPopulation.setDescr(seniorsDesc);
            createPopulation(seniorsOnlyStudentsPopulation, new ContextInfo());

            PopulationInfo athletesOnlyStudentsPopulation = new PopulationInfo();
            athletesOnlyStudentsPopulation.setId(ATHLETES_ONLY_STUDENTS);
            athletesOnlyStudentsPopulation.setName("Athletes only students");
            RichTextInfo athletesDesc = new RichTextInfo();
            athletesDesc.setPlain("Athletes students population");
            athletesOnlyStudentsPopulation.setDescr(athletesDesc);
            createPopulation(athletesOnlyStudentsPopulation, new ContextInfo());


            generateStudentPopulations(SUMMER_ONLY_STUDENTS, "SUMMER_ONLY_STUDENTS", 5000);
            generateStudentPopulations(SENIOR_ONLY_STUDENTS,"SENIOR_ONLY_STUDENTS", 5000);
            generateStudentPopulations(ATHLETES_ONLY_STUDENTS,"ATHLETES_ONLY_STUDENTS", 550);

            // These values are needed for the unit tests.
            caches.get(SUMMER_ONLY_STUDENTS).add("2155");

            caches.get(ALL_STUDENTS).add("2005");
            caches.get(ALL_STUDENTS).add("2016");
            caches.get(ALL_STUDENTS).add("2132");
            caches.get(ALL_STUDENTS).add("2166");
            caches.get(ALL_STUDENTS).add("2272");
            caches.get(ALL_STUDENTS).add("2374");
            caches.get(ALL_STUDENTS).add("2397");
            caches.get(ALL_STUDENTS).add("2406");


            caches.get(ALL_STUDENTS).addAll(caches.get(SUMMER_ONLY_STUDENTS));
            caches.get(ALL_STUDENTS).addAll(caches.get(SENIOR_ONLY_STUDENTS));
            caches.get(ALL_STUDENTS).addAll(caches.get(ATHLETES_ONLY_STUDENTS));


        } catch (Exception e) {
           throw new RuntimeException("Error initializing", e);
        }
    }

    private void generateStudentPopulations(String populationCacheKey, String populationPrefix, int numToGenerate){
        int base =  100000000;

        for(int i = 0; i<numToGenerate; i++){
            caches.get(populationCacheKey).add(populationPrefix + (base + i));
        }
    }

    @Override
    public Boolean isMemberAsOfDate( String personId,  String populationId,  Date date,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == personId || 0 == personId.length()) {
            throw new MissingParameterException("personId");
        }
        if (null == populationId || 0 == populationId.length()) {
            throw new MissingParameterException("populationId");
        }

        Set<String> cache = caches.get(populationId);
        if (null == cache) {
            throw new DoesNotExistException("populationId '" + populationId + "' not found");
        }
        return cache.contains(personId);
    }

    @Override
    public List<String> getMembersAsOfDate( String populationId,  Date date,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == populationId || 0 == populationId.length()) {
            throw new MissingParameterException("populationId");
        }

        Set<String> cache = caches.get(populationId);
        if (null == cache) {
            throw new DoesNotExistException("populationId '" + populationId + "' not found");
        }
        return new ArrayList<String>(cache);
    }

    @Override
    public PopulationInfo getPopulation( String populationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == populationId || 0 == populationId.length()) {
            throw new MissingParameterException("populationId");
        }

        PopulationInfo population = populations.get(populationId);
        if (null == population) {
            throw new DoesNotExistException("populationId '" + populationId + "' not found");
        }
        return population;
    }

    @Override
    public List<PopulationInfo> getPopulationsByIds(List<String> populationIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (0 == populationIds.size()) {
            throw new MissingParameterException("populationIds");
        }

        List<PopulationInfo> result = new ArrayList<PopulationInfo>();
        for (String populationId : populationIds) {
            PopulationInfo population = populations.get(populationId);
            if (null == population) {
                throw new DoesNotExistException("populationId '" + populationId + "' not found");
            }
            result.add(population);
        }
        return result;
    }

    @Override
    public List<String> getPopulationIdsByType(String populationTypeId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationInfo> getPopulationsForPopulationRule(String populationRuleId,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> searchForPopulationIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationInfo> searchForPopulations(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
            if(key != null){
                PopulationInfo populationInfo = populations.get(key);
                if(populationInfo.getName().toLowerCase().indexOf(name.toLowerCase())>=0 && populationInfo.getDescr().getPlain().toLowerCase().indexOf(desc.toLowerCase())>=0){
                    populationInfos.add(populationInfo);
                }
            }
        }
        return populationInfos;
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(String validationTypeId, PopulationInfo populationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationInfo createPopulation(PopulationInfo populationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        populations.put(populationInfo.getId(), populationInfo);
        caches.put(populationInfo.getId(), new HashSet<String>());
        return populationInfo;
    }

    @Override
    public PopulationInfo updatePopulation( String populationId, PopulationInfo populationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo deletePopulation( String populationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if (null == populationId || 0 == populationId.length()) {
            throw new MissingParameterException("populationId");
        }

        PopulationInfo population = populations.get(populationId);
        if (null == population) {
            throw new DoesNotExistException("populationId '" + populationId + "' not found");
        }
        populations.remove(populationId);
        caches.remove(populationId);
        return new StatusInfo();
    }

    @Override
    public PopulationRuleInfo getPopulationRule(String populationRuleId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationRuleInfo> getPopulationRulesByIds(List<String> populationRuleIds,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> getPopulationRuleIdsByType(String populationTypeKey,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationRuleInfo getPopulationRuleForPopulation( String populationKey,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> searchForPopulationRuleIds(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationRuleInfo> searchForPopulationRules(QueryByCriteria criteria,  ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<ValidationResultInfo> validatePopulationRule(String validationTypeKey, PopulationRuleInfo populationInfo,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationRuleInfo createPopulationRule(PopulationRuleInfo populationInfo,  ContextInfo contextInfo) throws  DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationRuleInfo updatePopulationRule(String populationRuleId, PopulationRuleInfo populationInfo,  ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo deletePopulationRule(String populationRuleId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo applyPopulationRuleToPopulation(String populationRuleId,  String populationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo removePopulationRuleFromPopulation(String populationRuleId,  String populationId,  ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationCategoryInfo getPopulationCategory(String populationCategoryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesByIds(List<String> populationCategoryIds, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> getPopulationCategoryIdsByType(String populationTypeKey, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationCategoryInfo> getPopulationCategoriesForPopulation(String populationId, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> searchForPopulationCategoryIds(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationCategoryInfo> searchForPopulationCategories(QueryByCriteria criteria, ContextInfo contextInfo)
            throws InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<ValidationResultInfo> validatePopulationCategory(String validationTypeKey, String populationCategoryTypeKey, PopulationCategoryInfo populationCategoryInfo, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
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
        throw new OperationFailedException("Method not implemented.");
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
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo deletePopulationCategory(String populationCategoryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
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
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo removePopulationFromPopulationCategory(String populationId, String populationCategoryId, ContextInfo contextInfo)
            throws DoesNotExistException
            ,InvalidParameterException
            ,MissingParameterException
            ,OperationFailedException
            ,PermissionDeniedException
    {
        throw new OperationFailedException("Method not implemented.");
    }

}
