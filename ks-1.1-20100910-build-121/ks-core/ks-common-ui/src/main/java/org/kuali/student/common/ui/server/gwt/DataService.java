package org.kuali.student.common.ui.server.gwt;

import java.util.Map;

import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.rice.authorization.PermissionType;

public interface DataService {
	//Data operations
	public Data getData(String dataId) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
	
	public Metadata getMetadata(String idType, String id) throws OperationFailedException;

	public DataSaveResult saveData(Data data) throws OperationFailedException, DataValidationErrorException;
	
	//AuthZ operations
	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes);
}
