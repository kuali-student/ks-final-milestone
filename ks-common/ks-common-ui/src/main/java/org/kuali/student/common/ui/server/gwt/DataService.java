package org.kuali.student.common.ui.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;

import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

public interface DataService {
	//Data operations
	public Data getData(String dataId, ContextInfo contextInfo) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
	
	public Metadata getMetadata(String id, Map<String, String> attributes, ContextInfo contextInfo) throws OperationFailedException;

	public DataSaveResult saveData(Data data, ContextInfo contextInfo) throws OperationFailedException, DataValidationErrorException, VersionMismatchException;
	
	public List<ValidationResultInfo> validateData(Data data, ContextInfo contextInfo) throws OperationFailedException;
	
	//AuthZ operations
	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes, ContextInfo contextInfo);
}
