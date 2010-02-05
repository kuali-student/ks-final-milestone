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
package org.kuali.student.lum.ui.requirements.server.gwt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.ReqCompFieldInfo;
import org.kuali.student.lum.lu.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.dto.ReqComponentTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.nlt.dto.LuNlStatementInfo;
import org.kuali.student.lum.nlt.service.TranslationService;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;

/**
 * @author Zdenek Zraly
 */
public class RequirementsRpcGwtServlet extends BaseRpcGwtServletAbstract<LuService> implements RequirementsRpcService {

    final Logger logger = Logger.getLogger(RequirementsRpcGwtServlet.class);
    
    private TranslationService translationService;
    
    private static final long serialVersionUID = 822326113643828855L;

    public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey) throws Exception {
        
        String naturalLanguage = "";           
        
        try {             
            naturalLanguage = translationService.getNaturalLanguageForReqComponentInfo(compInfo, nlUsageTypeKey, null);            
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }                      
        
        return naturalLanguage;
    }   
        
    public String getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey) throws Exception {
                     
        LuNlStatementInfo luNlStatementInfo = new LuNlStatementInfo();
        
        //first translate StatementVO to LuNlStatementInfo object
        String error = composeLuNlStatementInfo(statementVO, luNlStatementInfo);
        if (error.isEmpty() == false) {
            throw new Exception(error + "cluId: " + cluId + ", usage: " + nlUsageTypeKey);            
        }
                       
        //cluId can't be empty
        if ((cluId != null) && cluId.isEmpty()) {
        	cluId = null;
        }
 
        //then get natural language for the statement
        String NLStatement = "";        
        try {        
            NLStatement = translationService.getNaturalLanguageForLuStatementInfo(cluId, luNlStatementInfo, nlUsageTypeKey, null);            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to get natural language for clu: " + cluId + " and nlUsageTypeKey: " + nlUsageTypeKey, ex);
        }; 
        
        return NLStatement;
    }
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
                
        List<ReqComponentTypeInfo> reqComponentTypeInfoList = null;
        try { 
        	//FIXME: LuService API Change
            //reqComponentTypeInfoList = service.getReqComponentTypesForLuStatementType(luStatementTypeKey);                                   
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }                    
    
    private String composeLuNlStatementInfo(StatementVO statementVO, LuNlStatementInfo luNlStatementInfo) throws Exception {
        
        List<StatementVO> statementVOs = statementVO.getStatementVOs();       
        List<ReqComponentVO> reqComponentVOs = statementVO.getReqComponentVOs();
        
        luNlStatementInfo.setOperator(statementVO.getLuStatementInfo().getOperator());
        luNlStatementInfo.setStatementTypeId(statementVO.getLuStatementInfo().getType());
        if ((statementVOs != null) && (reqComponentVOs != null) && (statementVOs.size() > 0) && (reqComponentVOs.size() > 0))
        {
            return "Internal error: found both Statements and Requirement Components on the same level of boolean expression";
        } 
        
        if ((statementVOs != null) && (statementVOs.size() > 0)) {
            //retrieve all statements
            List<LuNlStatementInfo> stmtInfoList = new ArrayList<LuNlStatementInfo>();
            for (StatementVO statement : statementVOs) {  
                LuNlStatementInfo tempLuNlStmtInfo = new LuNlStatementInfo(); 
                tempLuNlStmtInfo.setOperator(statement.getLuStatementInfo().getOperator());
                tempLuNlStmtInfo.setStatementTypeId(statement.getLuStatementInfo().getType());
                composeLuNlStatementInfo(statement, tempLuNlStmtInfo);  //inside set the children of this LuNlStatementInfo
                stmtInfoList.add(tempLuNlStmtInfo);
            }   
            luNlStatementInfo.setChildren(stmtInfoList);
        } else {
            //retrieve all req. component LEAFS
            List<ReqComponentInfo> reqComponentList = new ArrayList<ReqComponentInfo>();
            for (ReqComponentVO reqComponent : reqComponentVOs) {                                    
                ReqComponentInfo newReqComp = new ReqComponentInfo();
                newReqComp.setId(reqComponent.getReqComponentInfo().getId());
                newReqComp.setType(reqComponent.getReqComponentInfo().getType());
                newReqComp.setDesc(reqComponent.getTypeDesc());
                newReqComp.setReqCompFields(reqComponent.getReqComponentInfo().getReqCompFields());
                reqComponentList.add(newReqComp);
            }  
            luNlStatementInfo.setRequiredComponents(reqComponentList);
        }        
        
        return "";
    }                   
    
    /**
     * @throws Exception 
     * @see org.kuali.student.lum.lu.ui.course.client.service.LuRemoteService#updateClu(java.lang.String, org.kuali.student.lum.lu.dto.CluInfo)
     */
    public List<ReqCompFieldInfo> verifyFieldsAndSetIds(List<ReqCompFieldInfo> editedFields) throws Exception {

    	//check each type of field values and find related ids
    	for (ReqCompFieldInfo comp : editedFields) {
    		
    		//find clu ids based on clu code
    		if (comp.getId().equals("reqCompFieldType.clu")) {
    			String[] codes = comp.getValue().split(", ");
    			StringBuffer ids = new StringBuffer();
    			
    	        try {
    	        	for (String id : codes) {    
    	        		id = id.trim();
    	           		List<QueryParamValue> cluParam = new ArrayList<QueryParamValue>();
    	        		QueryParamValue orgOptionalTypeParam = new QueryParamValue();
    	        		orgOptionalTypeParam.setKey("lu.criteria.code");
    	        		orgOptionalTypeParam.setValue(id);   
    	        		cluParam.add(orgOptionalTypeParam);         		
    	        		
    	        		List<Result> resultId = service.searchForResults("lu.search.cluByCode", cluParam);
    	        		if ((resultId == null) || (resultId.size() < 1)) {
    	        			throw new Exception("Invalid code: '" + id + "'");
    	        		}
    	        		List<ResultCell> cells = resultId.get(0).getResultCells();        		        		
        				ids.append(ids.length() > 0 ? ", " : "");
        				ids.append(cells.get(0).getValue());        				
    	        	}
    	        } catch (DoesNotExistException e) {
    	            e.printStackTrace();
    	        } catch (InvalidParameterException e) {
    	            e.printStackTrace();
    	        } catch (MissingParameterException e) {
    	            e.printStackTrace();
    	        } catch (OperationFailedException e) {
    	            e.printStackTrace();
    	        } catch (PermissionDeniedException e) {
    				e.printStackTrace();
    			}    
    	        
    			comp.setValue(ids.toString());    	        
			}  // if()
    	}  // for()  
 
        return editedFields;
    } 
    
    public String retrieveCluCode(String cluId) {

        String cluCode = null;       
        
        try {   		        		
    		CluInfo clu = service.getClu(cluId);
    		if ((clu != null)) {
    			return clu.getOfficialIdentifier().getCode();
    		}                             
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
 
        return cluCode;
    }     
          
        
    
    /******************************************************************************************************************
     * 
     *                                                     GETTERS & SETTERS 
     *
     *******************************************************************************************************************/         

    public void setTranslationService(TranslationService translationService) {
        this.translationService = translationService;
    } 
}
