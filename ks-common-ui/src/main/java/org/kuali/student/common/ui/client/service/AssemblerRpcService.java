package org.kuali.student.common.ui.client.service;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;

public interface AssemblerRpcService {
	//Data operations
	public Data getData(String dataId) throws OperationFailedException;
	public Metadata getMetadata() throws OperationFailedException;

	public DataSaveResult saveData(Data data) throws OperationFailedException;

}
