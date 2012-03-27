/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.common.ui.client.service;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.client.service.exceptions.VersionMismatchClientException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.dto.ContextInfo;

public interface AssemblerRpcService {
	//Data operations
	public Data getData(String dataId) throws OperationFailedException;
	
	public Metadata getMetadata(String id, Map<String,String> idAttributes) throws OperationFailedException;

	public DataSaveResult saveData(Data data) throws OperationFailedException, VersionMismatchClientException;
	
	public List<ValidationResultInfo> validate(Data data) throws OperationFailedException;

}
