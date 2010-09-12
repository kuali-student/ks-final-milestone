package org.kuali.student.brms.factfinder.runtime;

import java.util.List;

import org.kuali.student.brms.factfinder.dto.FactResultInfo;
import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.factfinder.dto.FactTypeInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.OperationFailedException;

public interface FactFinder {
    /**
     * Retrieves the list of Facts Types known by this service.
     * 
     * @return list of Agenda types
     * @throws OperationFailedException
     */
    public List<FactTypeInfo> getFactTypes() throws OperationFailedException;

    /**
     * Retrieves information about a type of Fact, listing all the keys required for its computation.
     * 
     * @return list of business rule types
     * @throws OperationFailedException
     */
    public FactTypeInfo getFactType(String factTypeKey) throws OperationFailedException, DoesNotExistException;

    /**
     * Retrieves the result of computation of the Fact by the service.
     * 
     * @return
     * @throws OperationFailedException
     */
    public FactResultInfo getFact(String factTypeKey, FactStructureInfo factStructure) throws OperationFailedException, DoesNotExistException;    
}
