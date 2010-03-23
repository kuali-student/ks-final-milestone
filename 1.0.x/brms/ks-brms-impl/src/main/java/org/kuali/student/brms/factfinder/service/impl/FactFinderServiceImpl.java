package org.kuali.student.brms.factfinder.service.impl;

import java.util.List;

import javax.jws.WebService;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.brms.factfinder.runtime.FactFinder;
import org.kuali.student.brms.factfinder.service.FactFinderService;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

@WebService(endpointInterface = "org.kuali.student.brms.factfinder.service.FactFinderService", serviceName = "FactFinderService", portName = "FactFinderService", targetNamespace = "http://student.kuali.org/wsdl/brms/FactFinder")
@Transactional
public class FactFinderServiceImpl implements FactFinderService {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(FactFinderServiceImpl.class);
    		
    private FactFinder factFinder;
    
    public void setFactFinder(FactFinder factFinder) {
		this.factFinder = factFinder;
	}

	@Override
    public FactResultInfo getFact(String factTypeKey, FactStructureInfo factStructure) throws OperationFailedException, DoesNotExistException {
		return this.factFinder.getFact(factTypeKey, factStructure);
    }

    @Override
    public FactTypeInfo getFactType(String factTypeKey) throws OperationFailedException, DoesNotExistException {
    	return this.factFinder.getFactType(factTypeKey);
    }

    @Override
    public List<FactTypeInfo> getFactTypes() throws OperationFailedException {
    	return this.factFinder.getFactTypes();
    }
}
