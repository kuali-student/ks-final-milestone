package org.kuali.student.common.ui.server.gwt;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.validation.dto.ValidationResultInfo;

public interface DataService {
	//Data operations
	public Data getData(String dataId) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
	
	public Metadata getMetadata(String id, Map<String, String> attributes) throws OperationFailedException;

	public DataSaveResult saveData(Data data) throws OperationFailedException, DataValidationErrorException;
	
	public List<ValidationResultInfo> validateData(Data data) throws OperationFailedException;
	
	//AuthZ operations
	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes);
}
