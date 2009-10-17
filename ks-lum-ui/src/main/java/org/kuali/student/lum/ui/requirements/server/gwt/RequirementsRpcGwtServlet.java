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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluSetInfo;
import org.kuali.student.lum.lu.dto.LuStatementInfo;
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
                
        //then get natural language for the statement
        String NLStatement = "";
        cluId = (cluId.isEmpty() ? null : cluId);  //cluId can't be empty
        try {        
            NLStatement = translationService.getNaturalLanguageForLuStatementInfo(cluId, luNlStatementInfo, nlUsageTypeKey, null);            
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to get natural language for clu: " + cluId + " and nlUsageTypeKey: " + nlUsageTypeKey, ex);
        }; 
        
        return NLStatement;
    }
    
    public List<ReqComponentTypeInfo> getReqComponentTypesForLuStatementType(String luStatementTypeKey) throws Exception {
                
        List<ReqComponentTypeInfo> reqComponentTypeInfoList;
        try {        
            reqComponentTypeInfoList = service.getReqComponentTypesForLuStatementType(luStatementTypeKey);                                   
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Requirement Component Types based on LU Statement Type Key:" + luStatementTypeKey, ex);
        }
        
        return reqComponentTypeInfoList;
    }  
    
    //this method is temporary and will be replaced by search widgets setup on the client side
    public Map<String, String> getAllClus() throws Exception {
        
        Map<String, String> cluCodes = new HashMap<String, String>();
        List<Result> clus;
        List<Result> cluNames = null;
        try {
            List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(0);           
            clus = service.searchForResults("lu.search.clus", queryParamValues);
            if (clus != null) {
                for (Result result : clus) {
                    Result cluCodeResult = new Result();
                    List<ResultCell> cluCodeResultCells = new ArrayList<ResultCell>();
                    ResultCell cluCodeResultCell = new ResultCell();

                    CluInfo cluInfo = service.getClu(result.getResultCells().get(0).getValue());   
                    CluIdentifierInfo cluIdentInfo = cluInfo.getOfficialIdentifier();
                    String displayName = cluIdentInfo.getCode();
                    //String displayName = service.getClu(result.getResultCells().get(0).getValue()).getOfficialIdentifier().getShortName();
                    displayName = (displayName == null)? "" : displayName;                  
                    displayName = displayName.replace(',', '/'); 
                    if (displayName.equals("Code")) continue;  //TODO: remove once these Clus are not in ks-lu.sql
                    cluCodeResultCell.setKey(cluInfo.getId());
                    cluCodeResultCell.setValue(displayName);
                    cluCodeResultCells.add(cluCodeResultCell);
                    cluCodeResult.setResultCells(cluCodeResultCells);
                    cluNames = (cluNames == null)? new ArrayList<Result>() : cluNames;
                    cluNames.add(cluCodeResult);
                    
                    cluCodes.put(displayName, cluInfo.getId());  //should be other way around but...we go by name that user enters
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve Clus", ex);
        }

        return cluCodes;
    }      
    
    //to be removed when search widgets are added to the client
    public Map<String, String> getAllClusets() throws Exception {
        
        Map<String, String> clusetCodes = new HashMap<String, String>();
        List<Result> clus;
        List<Result> cluNames = null;
        try {
            List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(0);           
            clus = service.searchForResults("lu.search.clusets", queryParamValues);
            if (clus != null) {
                for (Result result : clus) {
                    Result cluCodeResult = new Result();
                    List<ResultCell> cluCodeResultCells = new ArrayList<ResultCell>();
                    ResultCell cluCodeResultCell = new ResultCell();

                    CluSetInfo cluSetInfo = service.getCluSetInfo(result.getResultCells().get(0).getValue());   
                 /*   CluIdentifierInfo cluIdentInfo = cluInfo.getOfficialIdentifier();
                    String displayName = cluIdentInfo.getCode();
                    //String displayName = service.getClu(result.getResultCells().get(0).getValue()).getOfficialIdentifier().getShortName();
                    displayName = (displayName == null)? "" : displayName;                  
                    displayName = displayName.replace(',', '/'); 
                    if (displayName.equals("Code")) continue;  //TODO: remove once these Clus are not in ks-lu.sql
                    cluCodeResultCell.setKey(cluInfo.getId());
                    cluCodeResultCell.setValue(displayName);
                    cluCodeResultCells.add(cluCodeResultCell);
                    cluCodeResult.setResultCells(cluCodeResultCells);
                    cluNames = (cluNames == null)? new ArrayList<Result>() : cluNames;
                    cluNames.add(cluCodeResult); */
                    
                    clusetCodes.put(cluSetInfo.getId(), cluSetInfo.getName());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to retrieve Clusets", ex);
        }

        return clusetCodes;
    }         
    
    //retrieve statement based on CLU ID and STATEMENT TYPE
    public LuStatementInfo getLuStatementForCluAndStatementType(String cluId, String luStatementTypeKey) throws Exception {
        
        try {
            List<LuStatementInfo> stmtInfoList = service.getLuStatementsForClu(cluId);
            
            for (LuStatementInfo statementInfo : stmtInfoList) {
                if (statementInfo.getType().equals(luStatementTypeKey)) {
                    return statementInfo;
                }
            }
            
            logger.debug("Did not find LuStatementInfo based on cluid: " + cluId + " and luStatementTypeKey: " + luStatementTypeKey);                                       
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Unable to find Lu Statement based on CLU id:" + cluId + " and Statement type key: " + luStatementTypeKey, ex);
        }
        
        return null;
    }
    
    public StatementVO getStatementVO(String cluId, String luStatementTypeKey) throws Exception {
        
        LuStatementInfo luStatementInfo = getLuStatementForCluAndStatementType(cluId, luStatementTypeKey);
        
        //true if no rule defined for given statement type
        if (luStatementInfo == null) {
            return null;
        }
        
        StatementVO rootStatementVO = new StatementVO(luStatementInfo);
        if (luStatementInfo != null) {
            String error = composeStatementVO(luStatementInfo, rootStatementVO);
            if (error.isEmpty() == false) {
                throw new Exception(error + "cluId: " + cluId + ", luStatementTypeKey: " + luStatementTypeKey);            
            }
        }
        
        return rootStatementVO;        
    }      
    
    private String composeStatementVO(LuStatementInfo luStatementInfo, StatementVO statementVO) throws Exception {
        
        List<String> statementIDs = luStatementInfo.getLuStatementIds();       
        List<String> reqComponentIDs = luStatementInfo.getReqComponentIds();
        
        if ((statementIDs != null) && (reqComponentIDs != null) && (statementIDs.size() > 0) && (reqComponentIDs.size() > 0))
        {
            return "Internal error: found both Statements and Requirement Components on the same level of boolean expression";
        }
        
        if ((statementIDs != null) && (statementIDs.size() > 0)) {
            //retrieve all statements
            for (String stmtID : statementIDs) {
                LuStatementInfo tempStmtInfo;                    
                try {
                    tempStmtInfo = service.getLuStatement(stmtID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statement based on luStatementID: " + stmtID, ex);
                }
                StatementVO tempStmtVO = new StatementVO(tempStmtInfo);
                composeStatementVO(tempStmtInfo, tempStmtVO);
                statementVO.addStatementVO(tempStmtVO);
            }            
        } else {
            //retrieve all req. component LEAFS
            for (String reqCompID : reqComponentIDs) {
                    
                ReqComponentInfo tempReqCompInfo;
                try {
                    tempReqCompInfo = service.getReqComponent(reqCompID);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on reqComponentID: " + reqCompID, ex);
                }
                
                ReqComponentVO tempReqCompVO = new ReqComponentVO(tempReqCompInfo);
                
                try {
                    tempReqCompVO.setTypeDesc(getNaturalLanguageForReqComponentInfo(tempReqCompInfo, "KUALI.CATALOG"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new Exception("Unable to retrieve Lu Statemetn based on reqComponentID: " + reqCompID, ex);
                }                                

                statementVO.addReqComponentVO(tempReqCompVO);
            }               
        }        
        
        return "";
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
                logger.debug("got STATEMENT witho operator: " + statement.getLuStatementInfo().getOperator());
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
    
    public String getRuleRationale(String cluId, String luStatementTypeKey) throws Exception {        
        return "To be prepared for this course, students must have in-depth knowledge of the topics from at least on introductory education course.";
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
