/*
 * Copyright 2013 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.search;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.Query;

import org.kuali.student.enrollment.class1.lui.dao.LuiLuiRelationDao;
import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingClusterEntity;
import org.kuali.student.enrollment.class2.courseoffering.model.ActivityOfferingSetEntity;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.r2.core.search.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 *
 * @author Kuali Student Team 
 *
 */
public class CourseOfferingServiceAutogenCountSearchServiceImpl implements SearchService {
    private static final Logger log = LoggerFactory
            .getLogger(CourseOfferingServiceAutogenCountSearchServiceImpl.class);

    @Resource
    private EntityManager entityManager;
    
    private LuiLuiRelationDao luiLuiRelationDao;
    
    /**
     * 
     */
    public CourseOfferingServiceAutogenCountSearchServiceImpl() {
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     * @param typeKey
     * @param name
     * @param description
     * @param effectiveDate as a string "MM/dd/yyyy"
     * @return initialized type info
     */
    private static TypeInfo initializeTypeInfo (String typeKey, String typeName, String effectiveDate) {
        TypeInfo info = new TypeInfo();
        info.setKey(typeKey);
        info.setName(typeName);
        info.setDescr(new RichTextHelper().fromPlain(typeName));
        DateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
        try {
            info.setEffectiveDate(mmddyyyy.parse(effectiveDate));
        } catch (ParseException ex) {
            throw new RuntimeException("bad code");
        }
        
        return info;
    }
    
    /*
     * TODO: turn these into real types and use the TypeService to resolve them.
     */
    private static TypeInfo BY_CO_TYPE = initializeTypeInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_CO, "Autogen Counts by CO", "02/28/2013");
    private static TypeInfo BY_FO_TYPE = initializeTypeInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_FO, "Autogen Counts by FO", "02/28/2013");
    private static TypeInfo BY_AOC_TYPE =  initializeTypeInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_AOC, "Autogen Counts by AOC", "02/28/2013");
    
    /* (non-Javadoc)
     * @see org.kuali.student.r2.core.search.service.SearchService#getSearchTypes(org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<TypeInfo> getSearchTypes(
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException, MissingParameterException,
            OperationFailedException {
        return Arrays.asList(new TypeInfo[] {BY_CO_TYPE, BY_FO_TYPE, BY_AOC_TYPE});
    }

    /* (non-Javadoc)
     * @see org.kuali.student.r2.core.search.service.SearchService#getSearchType(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public TypeInfo getSearchType(
            @WebParam(name = "searchTypeKey") String searchTypeKey,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException {
        
        if (searchTypeKey.equals(BY_CO_TYPE.getKey()))
                return new TypeInfo(BY_CO_TYPE);
        else if (searchTypeKey.equals(BY_FO_TYPE.getKey()))
            return new TypeInfo(BY_FO_TYPE);
        else if (searchTypeKey.equals(BY_AOC_TYPE.getKey()))
            return new TypeInfo(BY_AOC_TYPE);
        else
            throw new DoesNotExistException(String.format ("No key of type %s", searchTypeKey));
    }

    /*
     * Extract the expected parameter from the search request throwing errors if there is not one value for the expected key.
     */
    private String extractSearchParameter (String expectedParameterName, SearchRequestInfo searchRequestInfo) throws MissingParameterException, InvalidParameterException {
        if (searchRequestInfo.getParams().size() != 1)
            throw new MissingParameterException("Missing Search Parameter for what to search for");
        
        SearchParamInfo p = searchRequestInfo.getParams().get(0);
        
        if (!p.getKey().equals(expectedParameterName))
            throw new InvalidParameterException(String.format("Expected parameter of name(%s) but was (%s)", expectedParameterName, p.getKey()));
        
        int numberOfValues = p.getValues().size();
        
        if (numberOfValues != 1)
            throw new InvalidParameterException(String.format("Expected one parameter value for key(%s) not %d values", expectedParameterName, numberOfValues));
        
        return p.getValues().get(0);
        
    }
    /* (non-Javadoc)
     * @see org.kuali.student.r2.core.search.service.SearchService#search(org.kuali.student.r2.core.search.dto.SearchRequestInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public SearchResultInfo search(SearchRequestInfo searchRequestInfo,
            @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws MissingParameterException, InvalidParameterException,
            OperationFailedException, PermissionDeniedException {
        
        String searchKey = searchRequestInfo.getSearchKey();
        
        if (searchKey.equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_CO)) {
            String searchParameter = extractSearchParameter(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_CO_ID_PARAM, searchRequestInfo);
            return searchAutogenCountsByCO(searchParameter, contextInfo);
        }
        else if (searchKey.equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_FO)) {
            
            String searchParameter = extractSearchParameter(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_FO_ID_PARAM, searchRequestInfo);
            return searchAutogenCountsByFO(searchParameter, contextInfo);
        }
        if (searchKey.equals(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_AOC)) {
            String searchParameter = extractSearchParameter(CourseOfferingServiceConstants.AUTOGEN_COUNTS_BY_AOC_ID_PARAM, searchRequestInfo);
            return searchAutogenCountsByAOC(searchParameter, contextInfo);
        }
        else {
            SearchResultInfo results = new SearchResultInfo();
            results.setRows(new ArrayList<SearchResultRowInfo>());
            results.setStartAt(0);
            results.setTotalResults(0);
            return results;
        
    }
    
    
}

    /*
     * Find the number of AO's and RG's defined in a specified AOC.
     */
    private SearchResultInfo searchAutogenCountsByAOC(String activityOfferingClusterId,
            ContextInfo contextInfo) {
        
        List<SearchResultCellInfo> cellData = new ArrayList<SearchResultCellInfo>();
        
        Set<String>aoIds = new HashSet<String>();
        
        ActivityOfferingClusterEntity aocEntity = entityManager.find(ActivityOfferingClusterEntity.class, activityOfferingClusterId);
        
        if (aocEntity == null) {
            // doesn't match so return zero's
            SearchResultInfo results = new SearchResultInfo();
            results.setRows(new ArrayList<SearchResultRowInfo>());
            results.setStartAt(0);
            results.setTotalResults(0);
            return results;
        }
        
        Set<ActivityOfferingSetEntity> aoSets = aocEntity.getAoSets();
        
        for (ActivityOfferingSetEntity activityOfferingSetEntity : aoSets) {
            // accumulate ao ids.
            aoIds.addAll(activityOfferingSetEntity.getAoIds());
        }
        
        // count ao's in the identified aoc
        cellData.add(new SearchResultCellInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_AOS, String.valueOf(aoIds.size())));
        
        // special bulk query to load all of the rg id's for the ao id's given.
        Query q = entityManager.createNamedQuery("LuiLuiRelationENR.getLuiIdsByRelatedLuisAndRelationType");

        q.setParameter("luiIds", aoIds);
        q.setParameter("luiLuiRelationTypeKey", LuiServiceConstants.LUI_LUI_RELATION_REGISTERED_FOR_VIA_RG_TO_AO_TYPE_KEY);
        
        List<String> rgIds =  (List<String>)q.getResultList();
        
        cellData.add(new SearchResultCellInfo(CourseOfferingServiceConstants.AUTOGEN_COUNTS_TOTAL_RGS, String.valueOf (rgIds.size())));
        
        SearchResultInfo results = new SearchResultInfo();
        
        SearchResultRowInfo data = new SearchResultRowInfo();
       
        data.setCells(cellData);
        
        results.setRows(Arrays.asList(new SearchResultRowInfo [] {data}));
        results.setStartAt(0);
        results.setTotalResults(1);
        results.setSortDirection(SortDirection.DESC);
        
        return results;
    }

    private SearchResultInfo searchAutogenCountsByFO(String searchParameter,
            ContextInfo contextInfo) {
        
        // TODO implement this method to return the counts
        SearchResultInfo results = new SearchResultInfo();
        
        results.setRows(Arrays.asList(new SearchResultRowInfo [] {}));
        results.setStartAt(0);
        results.setTotalResults(0);
        results.setSortDirection(SortDirection.DESC);
        
        return results;
    }

    private SearchResultInfo searchAutogenCountsByCO(String searchParameter,
            ContextInfo contextInfo) {
        
        // TODO implement this method to return the counts
        SearchResultInfo results = new SearchResultInfo();
        
        results.setRows(Arrays.asList(new SearchResultRowInfo [] {}));
        results.setStartAt(0);
        results.setTotalResults(0);
        results.setSortDirection(SortDirection.DESC);
        
        return results;
    }
    
}
