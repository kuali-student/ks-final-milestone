package org.kuali.student.rules.factfinder.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;
import org.kuali.student.rules.factfinder.dto.FactResultDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactTypeInfoDTO;
import org.kuali.student.rules.factfinder.service.FactFinderService;
import org.kuali.student.rules.factfinder.util.FactDataSupport;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.rules.factfinder.service.FactFinderService", serviceName = "FactFinderService", portName = "FactFinderService", targetNamespace = "http://student.kuali.org/poc/wsdl/brms/factfinder")
@Transactional
public class FactFinderServiceImpl implements FactFinderService {
    
    // Hard Code List of Facts
    FactDataSupport factDataSupport;
    
    @Override
    public FactResultDTO fetchFact(String factTypeKey, FactStructureDTO factStructure) throws OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;                
    }

    @Override
    public FactTypeInfoDTO fetchFactType(String factTypeKey) throws OperationFailedException {
        // TODO Change hard coded implementation to look at actual known types
        FactTypeInfoDTO result = null;
        
        List<FactTypeInfoDTO> factTypeList = factDataSupport.getFactTypeInfoList();
        if(null != factTypeList) {
            for(FactTypeInfoDTO ft : factTypeList) {
                if(factTypeKey.equals(ft.getFactTypeKey())) {
                    result = ft;
                    break;
                }
            }
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
     * @param factDataSupport the factDataSupport to set
     */
    public void setFactDataSupport(FactDataSupport factDataSupport) {
        this.factDataSupport = factDataSupport;
    }    
}
