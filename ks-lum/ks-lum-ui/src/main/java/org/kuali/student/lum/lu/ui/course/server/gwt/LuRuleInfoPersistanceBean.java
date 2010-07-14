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

package org.kuali.student.lum.lu.ui.course.server.gwt;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.statement.dto.RefStatementRelationInfo;
import org.kuali.student.core.statement.dto.StatementInfo;
import org.kuali.student.core.statement.dto.ReqComponentInfo;
import org.kuali.student.core.statement.dto.StatementTreeViewInfo;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.ui.requirements.client.model.EditHistory;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;
import org.kuali.student.lum.ui.requirements.client.view.RuleComponentEditorView;
import org.kuali.student.lum.ui.requirements.client.view.RuleConstants;

public class LuRuleInfoPersistanceBean {
	final Logger LOG = Logger.getLogger(LuRuleInfoPersistanceBean.class);
	
	private LuService luService;
	private StatementService statementService;
    private static final String STATEMENT_REF_TYPE_ID = "clu.rule";

	public LuService getLuService() {
		return luService;
	}
	
	public void setLuService(LuService luService) {
		this.luService = luService;
	}
	
	public StatementService getStatementService() {
        return statementService;
    }

    public void setStatementService(StatementService statementService) {
        this.statementService = statementService;
    }

    public String updateRules(String cluId, LuData luData) throws Exception {
        List<RefStatementRelationInfo> referencedObjects = statementService.getRefStatementRelationsByRef("clu", cluId);
        
        // delete all applicable statements attached to this clu
        if (referencedObjects != null) {
            for (RefStatementRelationInfo referencedObject : referencedObjects) {
                 if (referencedObject.getType().equals(STATEMENT_REF_TYPE_ID)) {
                    StatementInfo rule = statementService.getStatement(referencedObject.getStatementId());
                    
                    //ignore rules not related to our course proposal context
                    if (!isCourseAcademicReadinessRule(rule.getType())) {
                        continue;
                    }                        
                    
                    StatusInfo status = statementService.deleteRefStatementRelation(referencedObject.getId());
                    if (!status.getSuccess()) {
                        throw new Exception("Unable to remove statement with id: " + referencedObject.getId() + " from clu with id: " + cluId);  
                    }                        
                }
            }
        }        
        
        //re-create current statements
    	for (RuleInfo ruleInfo : luData.getRuleInfos()) {
    		StatementVO stmtVO = ruleInfo.getStatementVO(); 		           
    		
            if (stmtVO != null) {
                StatementTreeViewInfo statementTreeViewInfo = new StatementTreeViewInfo();
                stmtVO.composeStatementTreeViewInfo(stmtVO, statementTreeViewInfo);
                StatementTreeViewInfo updatedSTVInfo = statementService.updateStatementTreeView(stmtVO.getStatementInfo().getId(), statementTreeViewInfo);

                RefStatementRelationInfo refStatementRelationInfo = new RefStatementRelationInfo();
                refStatementRelationInfo.setRefObjectId(cluId); //MATH152
                refStatementRelationInfo.setRefObjectTypeKey("clu"); // CLU
                refStatementRelationInfo.setState("ACTIVE");
                refStatementRelationInfo.setStatementId(updatedSTVInfo.getId());
                refStatementRelationInfo.setType(STATEMENT_REF_TYPE_ID);
//              refStatementRelationInfo.setEffectiveDate(effDate.getTime());
//              refStatementRelationInfo.setExpirationDate(expDate.getTime());
                statementService.createRefStatementRelation(refStatementRelationInfo);
            }
    	}
        luData.setRuleInfos(fetchRules(cluId));
		return "";
    }

	public List<RuleInfo> fetchRules(String cluId) {		  			   
    		
		List<RuleInfo> ruleInfos = new ArrayList<RuleInfo>();
		
		try {		
			List<StatementInfo> statements = null;
			List<RefStatementRelationInfo> referencedObjects = statementService.getRefStatementRelationsByRef("clu", cluId);
			
			if (referencedObjects != null) {
			    for (RefStatementRelationInfo referencedObject : referencedObjects) {
			        String statementId = referencedObject.getStatementId();
			        String refTypeId = referencedObject.getType();
			        if (refTypeId.equals(STATEMENT_REF_TYPE_ID)) {
			            StatementInfo statement = statementService.getStatement(statementId);
			            statements = (statements == null)? new ArrayList<StatementInfo>(7) : statements;
			            statements.add(statement);
			        }
			    }
			}

			if (statements != null){
				for (StatementInfo statementInfo:statements){
					
					//ignore rules not related to our course proposal context
					if (!isCourseAcademicReadinessRule(statementInfo.getType())) {
						continue;
					}
					
					StatementVO statementVO = createStatementVO(statementInfo);			
					
					RuleInfo ruleInfo = new RuleInfo();
					ruleInfo.setCluId(cluId);
					ruleInfo.setEditHistory(new EditHistory());
					ruleInfo.setStatementVO(statementVO);
					ruleInfo.setSelectedStatementType(statementInfo.getType());
					
			        EditHistory editHistory = new EditHistory();
			        editHistory.save(statementVO);
			        ruleInfo.setEditHistory(editHistory);
					
			        String naturalLanguage = getNaturalLanguageForStatementVO(cluId, statementVO,  "KUALI.RULEEDIT", RuleComponentEditorView.TEMLATE_LANGUAGE);
			        ruleInfo.setNaturalLanguageForRuleEdit(naturalLanguage);
			        			        
					ruleInfos.add(ruleInfo);
				}
			}
		} catch (Exception e) {
			LOG.error("Error fetching rules from luService:" + cluId, e);
			throw new RuntimeException("Error fetching rules from luService:" + cluId);
		}
	
		return ruleInfos;
	}
	
    //FIXME [KSCOR-225] remove once we can use "kuali.luStatementType.course" to
    // determine which rules belong to the course proposal screen
	private boolean isCourseAcademicReadinessRule(String statementType) {	
        List<String> courseAcademicReadinessRules = new ArrayList<String>();
        courseAcademicReadinessRules.add(RuleConstants.KS_STATEMENT_TYPE_PREREQ);
        courseAcademicReadinessRules.add(RuleConstants.KS_STATEMENT_TYPE_COREQ);
        courseAcademicReadinessRules.add(RuleConstants.KS_STATEMENT_TYPE_ANTIREQ);
        courseAcademicReadinessRules.add(RuleConstants.KS_STATEMENT_TYPE_ENROLLREQ);
        
        return courseAcademicReadinessRules.contains(statementType); 
	}
    
	private StatementVO createStatementVO(StatementInfo statementInfo) throws Exception{
		StatementVO statementVO = new StatementVO(statementInfo);
        List<String> statementIDs = statementInfo.getStatementIds();       
        List<String> reqComponentIDs = statementInfo.getReqComponentIds();	
        
        //each statement can contain EITHER child statements or child req. components but not both
        if ((statementIDs != null) && (reqComponentIDs != null) && (statementIDs.size() > 0) && (reqComponentIDs.size() > 0))
        {
        	throw new Exception("Found both Statements and Requirement Components on the same level of a boolean expression");
        }		
		
		//Fetch child statements

		if (statementIDs != null && statementIDs.size() > 0){
			List<StatementInfo> childStatements = getStatements(statementInfo.getStatementIds());
			for (StatementInfo childStatement:childStatements){
				StatementVO childStatementVO = createStatementVO(childStatement);
				statementVO.addStatementVO(childStatementVO);
			}
		} else { //Fetch child requirement components		
			List<ReqComponentInfo> childReqComponents = getReqComponents(statementInfo.getReqComponentIds());
			for (ReqComponentInfo childReqComponent:childReqComponents){
				ReqComponentVO reqComponentVO = new ReqComponentVO(childReqComponent);
					
				String nl;
				try {
				    nl = statementService.translateReqComponentToNL(childReqComponent, "KUALI.RULEEDIT", "en");
				} catch(Exception e) {
					LOG.error("Error fetching NL for req. component:" + childReqComponent.getRequiredComponentType().getId(), e);
					throw new RuntimeException("Error fetching NL for req. component:" + childReqComponent.getRequiredComponentType().getId());
				} 				
				reqComponentVO.setTypeDesc(nl);
				
				statementVO.addReqComponentVO(reqComponentVO);
			}
		}
		
		return statementVO;
	}
	
	private List<StatementInfo> getStatements(List<String> statementIds) throws Exception {
	    List<StatementInfo> result = null;
	    if (statementIds != null) {
	        for (String statementId : statementIds) {
	            result = (result == null)? new ArrayList<StatementInfo>(7) : result;
	            result.add(statementService.getStatement(statementId));
	        }
	    }
	    return result;
	}
	
	private List<ReqComponentInfo> getReqComponents(List<String> reqComponentIds) throws Exception {
	    List<ReqComponentInfo> result = null;
        if (reqComponentIds != null) {
            for (String reqComponentId : reqComponentIds) {
                result = (result == null)? new ArrayList<ReqComponentInfo>(7) : result;
                result.add(statementService.getReqComponent(reqComponentId));
            }
        }
        return result;
	}	 
	
    private String getNaturalLanguageForStatementVO(String cluId, StatementVO statementVO, String nlUsageTypeKey, String language) throws Exception {
        StatementTreeViewInfo statementTreeViewInfo = new StatementTreeViewInfo();
        
        // first translate StatementVO to StatementTreeViewInfo object
        String error = statementVO.composeStatementTreeViewInfo(statementVO, statementTreeViewInfo);
        if (error.isEmpty() == false) {
            throw new  RuntimeException(error + "cluId: " + cluId + ", usage: " + nlUsageTypeKey);
        }

        // cluId can't be empty
        if ((cluId != null) && cluId.isEmpty()) {
            cluId = null;
        }
        
        // then get natural language for the statement
        String nlStatement = "";
        try {
            nlStatement = statementService.translateStatementTreeViewToNL(statementTreeViewInfo, nlUsageTypeKey, language);
        } catch (Exception ex) {
        	LOG.error(ex);
            throw new RuntimeException("Unable to get natural language for clu: " + cluId + " and nlUsageTypeKey: " + nlUsageTypeKey);
        }
        
        return nlStatement;
    }	
}
