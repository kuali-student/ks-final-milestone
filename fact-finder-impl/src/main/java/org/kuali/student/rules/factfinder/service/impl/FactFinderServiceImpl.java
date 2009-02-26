package org.kuali.student.rules.factfinder.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;
import javax.persistence.NoResultException;

import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dao.FactFinderDAO;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.entity.LUIPerson;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.factfinder.util.FactDataSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.rules.factfinder.service.FactFinderService", serviceName = "FactFinderService", portName = "FactFinderService", targetNamespace = "http://student.kuali.org/wsdl/brms/FactFinder")
@Transactional
public class FactFinderServiceImpl implements FactFinderService {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(FactFinderServiceImpl.class);
    		
    // Hard Code List of Facts
    FactDataSupport factDataSupport;

    FactFinderDAO factFinderDAO;

    @Override
    public FactResultDTO fetchFact(String factTypeKey, FactStructureDTO factStructure) throws OperationFailedException, DoesNotExistException {
    	if(logger.isInfoEnabled()) {
    		logger.info("\n---------- fetchFact ----------\n" +
    				"factStructureId="+factStructure.getFactStructureId()+
    				"\nfactTypeKey="+factTypeKey+
    				"\nfactStructure.paramValueMap="+
    				(factStructure == null ? "null" : factStructure.getParamValueMap())+
    				"\n-----------------------------------------");
    	}
    	FactResultDTO result = new FactResultDTO();
        
        result.setFactResultTypeInfo(fetchFactType(factTypeKey).getFactResultTypeInfo());

        String studentId = factStructure.getParamValueMap().get("factParam.studentId");
        List<LUIPerson> luiPersonList = factFinderDAO.lookupByStudentId(studentId);

        List<Map<String, String>> resultValueList = new ArrayList<Map<String, String>>();
   
        /*
         * Only Two Fact Type Keys are known currently. 
         * TODO: Change the implementation to a more generic one
         */
        if ("fact.earned_credit_list".equalsIgnoreCase(factTypeKey)) {
        	// Get the set of courses for which credit is computed
            String courseList = factStructure.getParamValueMap().get("factParam.clusetId");
            Set<String> courseSet = getAsSet(courseList);
                        
            // Get the set of courses for which credit
            String courseExcludeList = factStructure.getParamValueMap().get("factParam.excludeCluSet");
            Set<String> courseExcludeSet = getAsSet(courseExcludeList);

            for (LUIPerson lpr : luiPersonList) {
                if (courseSet.contains(lpr.getCluId()) && !courseExcludeSet.contains(lpr.getCluId())) {
                    Map<String, String> resultColumn = new HashMap<String, String>();
                    resultColumn.put("resultColumn.cluId", String.valueOf(lpr.getCluId()));
                    resultColumn.put("resultColumn.credit", String.valueOf(lpr.getCredits()));
                    resultColumn.put("resultColumn.description", String.valueOf(lpr.getDescription()));
                    resultValueList.add(resultColumn);
                }
            }
        } else if ("fact.completed_course_list".equalsIgnoreCase(factTypeKey)) {
            for (LUIPerson lpr : luiPersonList) {
                Map<String, String> resultColumn = new HashMap<String, String>();
                resultColumn.put("resultColumn.cluId", lpr.getCluId());
                resultColumn.put("resultColumn.description", String.valueOf(lpr.getDescription()));
                resultValueList.add(resultColumn);
            }
        } else if ("fact.clusetId".equalsIgnoreCase(factTypeKey)) {
            Map<String, String> resultColumn = new HashMap<String, String>();
            String[] cluSet = factStructure.getParamValueMap().get("factParam.clusetId").split(",");
            for(String cluId : cluSet) {
	            resultColumn.put("resultColumn.cluId", cluId);
	            resultValueList.add(resultColumn);
            }
        }

        result.setResultList(resultValueList);

        if(logger.isInfoEnabled()) {
    		logger.info("resultValueList="+resultValueList);
    	}

        return result;
    }

    @Override
    public FactTypeInfoDTO fetchFactType(String factTypeKey) throws OperationFailedException, DoesNotExistException {
        // TODO Change hard coded implementation to look at actual known types
        FactTypeInfoDTO result = null;

        try {
            List<FactTypeInfoDTO> factTypeList = factDataSupport.getFactTypeInfoList();
            if (null != factTypeList) {
                for (FactTypeInfoDTO ft : factTypeList) {
                    if (factTypeKey.equals(ft.getFactTypeKey())) {
                        result = ft;
                        break;
                    }
                }
            }

        } catch (NoResultException nre) {
            throw new DoesNotExistException("No fact exists with type key: " + factTypeKey);
        }

        return result;
    }

    @Override
    public List<FactTypeInfoDTO> findFactTypes() throws OperationFailedException {
        // TODO Change from hard coded values to actual persisted implementation
        return factDataSupport.getFactTypeInfoList();
    }

    /**
     * @return the factDataSupport
     */
    public FactDataSupport getFactDataSupport() {
        return factDataSupport;
    }

    /**
     * @param factDataSupport
     *            the factDataSupport to set
     */
    public void setFactDataSupport(FactDataSupport factDataSupport) {
        this.factDataSupport = factDataSupport;
    }

    /**
     * @return the factFinderDAO
     */
    public FactFinderDAO getFactFinderDAO() {
        return factFinderDAO;
    }

    /**
     * @param factFinderDAO
     *            the factFinderDAO to set
     */
    public void setFactFinderDAO(FactFinderDAO factFinderDAO) {
        this.factFinderDAO = factFinderDAO;
    }
    
    private Set<String> getAsSet(String list) {
        list = (null == list)? "" : list;
        
        String[] array = list.split(",");
        Set<String> set = new HashSet<String>();
        
        for(String s : array) {
            set.add(s.trim());
        }
        
        return set;
    }
}
