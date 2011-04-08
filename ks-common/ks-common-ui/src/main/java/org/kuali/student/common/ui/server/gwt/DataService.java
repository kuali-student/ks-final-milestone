package org.kuali.student.common.ui.server.gwt;

import java.util.Map;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.rice.authorization.PermissionType;
import org.kuali.student.common.ui.client.service.DataSaveResult;

public interface DataService {
	//Data operations
	public Data getData(String dataId) throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
	
	public Metadata getMetadata(String id, Map<String, String> attributes) throws OperationFailedException;

	public DataSaveResult saveData(Data data) throws OperationFailedException;
	
	//AuthZ operations
	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes);
}
