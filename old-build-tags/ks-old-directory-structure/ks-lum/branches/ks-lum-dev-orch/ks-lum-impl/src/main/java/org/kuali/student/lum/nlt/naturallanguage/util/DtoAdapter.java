/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.nlt.naturallanguage.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
import org.kuali.student.lum.lu.dto.LuStatementTypeInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;

/**
 * DTO adapter class.
 */
public class DtoAdapter {

	private LuService luService;
	
	private Map<String, String> idReferenceMap = new HashMap<String, String>();

	public DtoAdapter() {
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	/**
	 * Converts an {@link LuStatementInfo} into a {@link CustomLuStatementInfo}.
	 * 
	 * @param luStatementInfo LU statement to convert
	 * @return A custom LU statement
	 * @throws Exception If  conversion fails
	 */
	public CustomLuStatementInfo toCustomLuStatementInfo(LuStatementInfo luStatementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		CustomLuStatementInfo custom = createCustomLuStatementInfo(luStatementInfo);
		createParent(luStatementInfo, custom);
		createChildren(luStatementInfo, custom);
		return custom;
	}

	/**
	 * Converts an {@link ReqComponentInfo} into a {@link CustomReqComponentInfo}.
	 * 
	 * @param reqComponentInfo required component to convert
	 * @return A custom required component
	 * @throws Exception If conversion fails
	 */
	public CustomReqComponentInfo toCustomReqComponentInfo(ReqComponentInfo reqComponentInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		CustomReqComponentInfo custom = new CustomReqComponentInfo();
		custom.setId(reqComponentInfo.getId());
		custom.setReqCompFields(reqComponentInfo.getReqCompFields());
		custom.setRequiredComponentType(reqComponentInfo.getRequiredComponentType());
		if(reqComponentInfo.getRequiredComponentType() == null) {
			ReqComponentTypeInfo type = this.luService.getReqComponentType(reqComponentInfo.getType());
			custom.setRequiredComponentType(type);
		}
		return custom;
	}

	/**
	 * Converts a list of {@link ReqComponentInfo} into a list of {@link CustomReqComponentInfo}.
	 * 
	 * @param reqComponentInfoList required components to convert
	 * @return A list of custom required components
	 * @throws Exception If conversion fails
	 */
	public List<CustomReqComponentInfo> toCustomReqComponentInfos(List<ReqComponentInfo> reqComponentInfoList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<CustomReqComponentInfo> list = new ArrayList<CustomReqComponentInfo>();
		for(ReqComponentInfo req : reqComponentInfoList) {
			CustomReqComponentInfo custom = toCustomReqComponentInfo(req);
			list.add(custom);
		}
		return list;
	}
	
	private CustomLuStatementInfo createCustomLuStatementInfo(LuStatementInfo luStatementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		CustomLuStatementInfo custom = new CustomLuStatementInfo();
		custom.setId(luStatementInfo.getId());
		custom.setName(luStatementInfo.getName());
		custom.setLuStatementType(luStatementInfo.getLuStatementType());
		if(luStatementInfo.getLuStatementType() == null) {
			custom.setLuStatementType(this.luService.getLuStatementType(luStatementInfo.getType()));
		}
		custom.setOperator(luStatementInfo.getOperator());
        if (luStatementInfo.getLuStatementIds() == null || luStatementInfo.getLuStatementIds().isEmpty()) {
			custom.setRequiredComponents(getRequiredComponents(luStatementInfo));
        }
		return custom;
	}
	
	private void createParent(LuStatementInfo stmtInfo, CustomLuStatementInfo rootCustomLuStatement) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		if(stmtInfo.getParentId() != null) {
			LuStatementInfo info = this.luService.getLuStatement(stmtInfo.getParentId());
			CustomLuStatementInfo custom = createCustomLuStatementInfo(info);
			rootCustomLuStatement.setParent(custom);
			createParent(info, custom);
		}
	}

	private void checkReference(String statementId, List<String> statementIds) throws OperationFailedException {
		for(String id : statementIds) {
			if(this.idReferenceMap.containsKey(id)) {
	            throw new OperationFailedException("LuStatementInfo nested within itself. LuStatement id='" 
	            		+ statementId + "' references LuStatement id='" + id + "'");
			}
		}
	}
	
	private void createChildren(LuStatementInfo stmtInfo, CustomLuStatementInfo rootCustomLuStatement) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		if(stmtInfo.getLuStatementIds() != null && !stmtInfo.getLuStatementIds().isEmpty()) {
            this.idReferenceMap.put(stmtInfo.getId(), stmtInfo.getLuStatementIds().toString());
            checkReference(stmtInfo.getId(), stmtInfo.getLuStatementIds());
			List<LuStatementInfo> infoList = this.luService.getLuStatements(stmtInfo.getLuStatementIds());
			for(LuStatementInfo info : infoList) {
				CustomLuStatementInfo customStmt = new CustomLuStatementInfo();
				customStmt.setId(info.getId());
				customStmt.setName(info.getName());
		        customStmt.setParent(rootCustomLuStatement);
		        customStmt.setOperator(info.getOperator());
		        customStmt.setLuStatementType(info.getLuStatementType());
		        if(info.getLuStatementType() == null) {
					customStmt.setLuStatementType(this.luService.getLuStatementType(info.getType()));
				}
		        if(rootCustomLuStatement.getChildren() == null) {
		        	rootCustomLuStatement.setChildren(new ArrayList<CustomLuStatementInfo>());
		        }
		        rootCustomLuStatement.getChildren().add(customStmt);
		        if (info.getLuStatementIds() == null || info.getLuStatementIds().isEmpty()) {
		            List<CustomReqComponentInfo> children = getRequiredComponents(info);
		            customStmt.setRequiredComponents(children);
		        } else {
		        	createChildren(info, customStmt);
		        }
			}
		}
	}
	
	private List<CustomReqComponentInfo> getRequiredComponents(LuStatementInfo stmtInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<CustomReqComponentInfo> list = null;
		if(stmtInfo.getReqComponentIds() != null && !stmtInfo.getReqComponentIds().isEmpty()) {
			List<ReqComponentInfo> reqList = this.luService.getReqComponents(stmtInfo.getReqComponentIds());
			list = toCustomReqComponentInfos(reqList);
		}
		return list;
	}

	/**
	 * <p>Converts a {@link LuNlStatementInfo} into a {@link CustomReqComponentInfo}.</p>
	 * <p>Note: LuNlStatementInfo has no id since it is only used for 
	 * on-the-fly translations and is not persisted.</p>
	 * 
	 * @param statementInfo LuNlStatementInfo to convert
	 * @return A custom LU statement
	 * @throws Exception If conversion fails
	 */
	public CustomLuStatementInfo toCustomLuStatementInfo(LuNlStatementInfo statementInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		CustomLuStatementInfo stmt = new CustomLuStatementInfo();
        stmt.setName(statementInfo.getName());
        stmt.setOperator(statementInfo.getOperator());
        LuStatementTypeInfo stmtType = this.luService.getLuStatementType(statementInfo.getStatementTypeId());
        stmt.setLuStatementType(stmtType);
        
        if(statementInfo.getChildren() == null || statementInfo.getChildren().isEmpty()) {
        	setReqComponentType(statementInfo.getRequiredComponents());
        	List<CustomReqComponentInfo> customReqList = toCustomReqComponentInfos(statementInfo.getRequiredComponents());
        	stmt.setRequiredComponents(customReqList);
        } else {
            createStatement(statementInfo, stmt);
        }

        return stmt;
	}

	private void setReqComponentType(List<ReqComponentInfo> reqList) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		for(ReqComponentInfo req : reqList) {
			setReqComponentType(req);
		}
	}

	private void setReqComponentType(ReqComponentInfo req) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		if(req.getRequiredComponentType() == null) {
			ReqComponentTypeInfo type = this.luService.getReqComponentType(req.getType());
			req.setRequiredComponentType(type);
		}
	}

	private void createStatement(LuNlStatementInfo stmtInfo, CustomLuStatementInfo rootLuStatement) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		for(LuNlStatementInfo luNlStmt : stmtInfo.getChildren()) {
			CustomLuStatementInfo stmt = new CustomLuStatementInfo();
	        stmt.setName(luNlStmt.getName());
	        stmt.setParent(rootLuStatement);
	        stmt.setOperator(luNlStmt.getOperator());
			if(luNlStmt.getStatementTypeId() != null) {
		        stmt.setLuStatementType(this.luService.getLuStatementType(luNlStmt.getStatementTypeId()));
			}

	        if(rootLuStatement.getChildren() == null) {
	        	rootLuStatement.setChildren(new ArrayList<CustomLuStatementInfo>());
	        }
	        rootLuStatement.getChildren().add(stmt);
	        if (luNlStmt.getChildren() == null || luNlStmt.getChildren().isEmpty()) {
	            List<ReqComponentInfo> children = luNlStmt.getRequiredComponents();
	        	//setReqComponentType(children);
	        	List<CustomReqComponentInfo> customReqList = toCustomReqComponentInfos(children);
	            stmt.setRequiredComponents(customReqList);
	        } else {
	            createStatement(luNlStmt, stmt);
	        }
	    }
	}
}
