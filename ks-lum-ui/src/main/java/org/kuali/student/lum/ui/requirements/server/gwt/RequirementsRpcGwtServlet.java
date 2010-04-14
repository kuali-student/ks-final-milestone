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

package org.kuali.student.lum.ui.requirements.server.gwt;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.brms.statement.dto.ReqCompFieldInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.ReqComponentTypeInfo;
import org.kuali.student.brms.statement.dto.StatementTreeViewInfo;
import org.kuali.student.brms.statement.service.StatementService;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.service.RequirementsRpcService;

/**
 * @author Zdenek Zraly
 */
public class RequirementsRpcGwtServlet extends BaseRpcGwtServletAbstract<LuService> implements RequirementsRpcService {

    final Logger logger = Logger.getLogger(RequirementsRpcGwtServlet.class);
    
    private StatementService statementService;
    
    private static final long serialVersionUID = 822326113643828855L;
    
    public String getNaturalLanguageForReqComponentInfo(ReqComponentInfo compInfo, String nlUsageTypeKey, String language) throws Exception {
        
        String naturalLanguage = "";   
        naturalLanguage = statementService.translateReqComponentToNL(compInfo, nlUsageTypeKey, language);

        return naturalLanguage;
    }   
        
    public String getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey, String language) throws Exception {
        StatementTreeViewInfo statementTreeViewInfo = new StatementTreeViewInfo();
        
        // first translate StatementVO to StatementTreeViewInfo object
        String error = statementVO.composeStatementTreeViewInfo(statementVO, statementTreeViewInfo);
        if (error.isEmpty() == false) {
            throw new Exception(error + "cluId: " + cluId + ", usage: " + nlUsageTypeKey);
        }

        // cluId can't be empty
        if ((cluId != null) && cluId.isEmpty()) {
            cluId = null;
        }
        
        // the get natural language for the statement
        String nlStatement = "";
        try {
            nlStatement = statementService.translateStatementTreeViewToNL(statementTreeViewInfo, nlUsageTypeKey, language);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to get natural language for clu: " + cluId + " and nlUsageTypeKey: " + nlUsageTypeKey);
        }
        
        return nlStatement;
    }
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
                
        List<ReqComponentTypeInfo> reqComponentTypeInfoList = null;
        try { 
            reqComponentTypeInfoList = statementService.getReqComponentTypesForStatementType(luStatementTypeKey);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
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
    	           		List<SearchParam> cluParam = new ArrayList<SearchParam>();
    	           		SearchParam orgOptionalTypeParam = new SearchParam();
    	        		orgOptionalTypeParam.setKey("lu.criteria.code");
    	        		orgOptionalTypeParam.setValue(id);   
    	        		cluParam.add(orgOptionalTypeParam);         		
    	        		SearchRequest searchRequest = new SearchRequest();
    	        		searchRequest.setSearchKey("lu.search.cluByCode");
    	        		searchRequest.setParams(cluParam);
    	        		SearchResult resultId = service.search(searchRequest);
    	        		if ((resultId == null) || (resultId.getRows().size() < 1)) {
    	        			throw new Exception("Invalid code: '" + id + "'");
    	        		}
    	        		List<SearchResultCell> cells = resultId.getRows().get(0).getCells();        		        		
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

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }
}
