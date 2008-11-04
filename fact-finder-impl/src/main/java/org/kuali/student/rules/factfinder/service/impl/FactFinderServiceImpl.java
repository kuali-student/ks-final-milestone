package org.kuali.student.rules.factfinder.service.impl;

import java.util.List;
import java.util.Map;

import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;

public class FactFinderServiceImpl implements FactFinderService {

    
    // Hard Code List of Facts
    private List<Map<String,Object>> factList;
    
    @Override
    public FactResultDTO fetchFact(String factTypeKey, FactStructureDTO factStructure) throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;                }

    @Override
    public FactTypeInfoDTO fetchFactType(String factTypeKey) throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<FactTypeInfoDTO> findFactTypes() throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;                
    }
}
