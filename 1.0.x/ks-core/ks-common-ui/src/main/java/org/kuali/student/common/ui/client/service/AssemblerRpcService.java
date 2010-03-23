package org.kuali.student.common.ui.client.service;

import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;

public interface AssemblerRpcService {
	//Data operations
	public Data getData(String dataId) throws OperationFailedException;
	
	public Metadata getMetadata(String idType, String id) throws OperationFailedException;

	public DataSaveResult saveData(Data data) throws OperationFailedException;

}
