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

package org.kuali.student.common.ui.server.gwt;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.service.BaseDataOrchestrationRpcService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.client.service.exceptions.OperationFailedException;
import org.kuali.student.common.ui.client.service.exceptions.VersionMismatchClientException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;

import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Generic implementation of data gwt data operations calls.
 *
 */
public class DataGwtServlet extends RemoteServiceServlet implements BaseDataOrchestrationRpcService {

	private static final long serialVersionUID = 1L;

	final Logger LOG = Logger.getLogger(DataGwtServlet.class);

	private DataService dataService;
	
	@Override
	public Data getData(String id) throws OperationFailedException {
		try{
			return dataService.getData(id, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error("Could not get Data ", e);
			throw new OperationFailedException("Failed to get data");
		}
	}

	@Override
	public Metadata getMetadata(String id, Map<String, String> idAttributes) throws OperationFailedException {
		try{
			return dataService.getMetadata(id, idAttributes, ContextUtils.getContextInfo());
		} catch (Exception e) {
			LOG.error("Could not get metadata ", e);
			throw new OperationFailedException("Failed to get metadata");
		}
	}

	@Override
	public DataSaveResult saveData(Data data) throws OperationFailedException, VersionMismatchClientException {
		try{
			return dataService.saveData(data, ContextUtils.getContextInfo());
		} catch (DataValidationErrorException dvee){
			//This should only get thrown if service save call resulted in validation errors. These errors
			//should be sent to the UI using DataSaveResult instead of throwing an exception.
			//Sending null for data value, since UI should already have it and nothing changed.
			DataSaveResult saveResult = new DataSaveResult();
			saveResult.setValidationResults(dvee.getValidationResults());
			return saveResult;
		} catch (VersionMismatchException vme){
		    throw new VersionMismatchClientException(vme.getMessage());
		} catch (Exception e) {
			LOG.error("Could not save data ", e);
			throw new OperationFailedException(e.getMessage());
		} 
	}

	public List<ValidationResultInfo> validate(Data data)throws OperationFailedException {
		try{
		    List<ValidationResultInfo> result= dataService.validateData(data, ContextUtils.getContextInfo());    //result info loaded with info about conflicts 
		    return result;
		} catch (Exception e) {
			LOG.error("Could not validate data ", e);
			throw new OperationFailedException("Failed to  data");
		} 
	}

	@Override
	public Boolean isAuthorized(PermissionType type, Map<String,String> attributes) {
		return dataService.isAuthorized(type, attributes, ContextUtils.getContextInfo());
	}

	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}


}
