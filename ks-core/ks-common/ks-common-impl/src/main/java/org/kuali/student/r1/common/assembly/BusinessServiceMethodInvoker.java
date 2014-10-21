package org.kuali.student.r1.common.assembly;

import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;

public interface BusinessServiceMethodInvoker {
	@SuppressWarnings("unchecked")
	public void invokeServiceCalls(BaseDTOAssemblyNode results, ContextInfo contextInfo) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException, CircularRelationshipException, AssemblyException, UnsupportedActionException, UnsupportedOperationException, CircularReferenceException, ReadOnlyException;
}
