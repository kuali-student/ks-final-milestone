package org.kuali.student.r2.core.population.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
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
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PopulationServiceMockImpl implements PopulationService {

    private static Map<String, PopulationInfo> populations;
    private static Map<String, Set<String>> caches;

    public PopulationServiceMockImpl() {
        initialize();
    }

    private void initialize() {
        try {
            populations = new HashMap<String, PopulationInfo>();
            caches = new HashMap<String, Set<String>>();

            final String ALL_STUDENTS = "allStudents";
            final String SUMMER_ONLY_STUDENTS = "summerOnlyStudents";

            PopulationInfo allStudentsPopulation = new PopulationInfo();
            allStudentsPopulation.setId(ALL_STUDENTS);
            createPopulation(allStudentsPopulation, new ContextInfo());

            PopulationInfo summerOnlyStudentsPopulation = new PopulationInfo();
            summerOnlyStudentsPopulation.setId(SUMMER_ONLY_STUDENTS);
            createPopulation(summerOnlyStudentsPopulation, new ContextInfo());

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
    public Boolean isMember(@WebParam(name = "personId") String personId, @WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<String> getMembers(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public PopulationInfo getPopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<PopulationInfo> getPopulationsByIds(@WebParam(name = "populationIds") List<String> populationIds, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public List<String> getPopulationIdsByType(@WebParam(name = "populationTypeId") String populationTypeId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationInfo> getPopulationsForPopulationRule(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> searchForPopulationIds(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<PopulationInfo> searchForPopulations(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<ValidationResultInfo> validatePopulation(@WebParam(name = "validationTypeId") String validationTypeId, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public PopulationInfo createPopulation(@WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        PopulationInfo population = populations.get(populationInfo.getId());
        if (null != population) {
            throw new AlreadyExistsException("populationId '" + populationInfo.getId() + "' already exists");
        }
        populations.put(populationInfo.getId(), populationInfo);
        caches.put(populationInfo.getId(), new HashSet<String>());
        return populationInfo;
    }

    @Override
    public PopulationInfo updatePopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "populationInfo") PopulationInfo populationInfo, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException, VersionMismatchException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo deletePopulation(@WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public PopulationRuleInfo getPopulationRuleForPopulation(@WebParam(name = "populationid") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
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
    public StatusInfo applyPopulationRuleToPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StatusInfo removePopulationRuleFromPopulation(@WebParam(name = "populationRuleId") String populationRuleId, @WebParam(name = "populationId") String populationId, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> getDataDictionaryEntryKeys(@WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public DictionaryEntryInfo getDataDictionaryEntry(@WebParam(name = "entryKey") String entryKey, @WebParam(name = "context") ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StateProcessInfo getProcessByKey(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<String> getProcessByObjectType(@WebParam(name = "refObjectUri") String refObjectUri, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StateInfo getState(@WebParam(name = "processKey") String processKey, @WebParam(name = "stateKey") String stateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<StateInfo> getStatesByProcess(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<StateInfo> getInitialValidStates(@WebParam(name = "processKey") String processKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public StateInfo getNextHappyState(@WebParam(name = "processKey") String processKey, @WebParam(name = "currentStateKey") String currentStateKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public TypeInfo getType(@WebParam(name = "typeKey") String typeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(@WebParam(name = "refObjectURI") String refObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relatedRefObjectURI") String relatedRefObjectURI, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(@WebParam(name = "ownerTypeKey") String ownerTypeKey, @WebParam(name = "relationTypeKey") String relationTypeKey, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        throw new OperationFailedException("Method not implemented.");
    }
}
