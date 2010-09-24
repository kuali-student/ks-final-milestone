package org.kuali.student.lum.program.service.impl;

import org.apache.log4j.Logger;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.UnsupportedActionException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.lum.service.assembler.LumServiceMethodInvoker;

public class ProgramServiceMethodInvoker extends LumServiceMethodInvoker{
	final Logger LOG = Logger.getLogger(ProgramServiceMethodInvoker.class);

	@Override
	protected void invokeServiceCallOnResult(BaseDTOAssemblyNode results)
			throws AlreadyExistsException, DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, AssemblyException,
			VersionMismatchException, DependentObjectsExistException,
			CircularRelationshipException, UnsupportedActionException,
			UnsupportedOperationException, CircularReferenceException {
		Object node = results.getNodeData();
		LOG.warn(results.getOperation() + ": " + node);
		super.invokeServiceCallOnResult(results);
	}
}
