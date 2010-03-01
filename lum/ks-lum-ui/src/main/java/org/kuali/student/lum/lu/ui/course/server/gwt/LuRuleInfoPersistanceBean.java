package org.kuali.student.lum.lu.ui.course.server.gwt;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.brms.statement.dto.RefStatementRelationInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.brms.statement.dto.StatementTreeViewInfo;
import org.kuali.student.brms.statement.service.StatementService;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.ui.requirements.client.model.EditHistory;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

public class LuRuleInfoPersistanceBean {
	final Logger logger = Logger.getLogger(LuRuleInfoPersistanceBean.class);
	
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
        List<RuleInfo> newRules = luData.getRuleInfos();
    	for (RuleInfo ruleInfo : newRules) {
    		StatementVO stmtVO = ruleInfo.getStatementVO();
            StatementTreeViewInfo statementTreeViewInfo = new StatementTreeViewInfo();
            StatementTreeViewInfo updatedSTVInfo = null;
            RefStatementRelationInfo refStatementRelationInfo = null;
    		
            List<RefStatementRelationInfo> referencedObjects = statementService.getRefStatementRelationsByRef("clu", cluId);
            
            // delete the statements with the same type referenced by this clu
            if (referencedObjects != null) {
                for (RefStatementRelationInfo referencedObject : referencedObjects) {
                    String refId = referencedObject.getId();
                    String refTypeId = referencedObject.getType();
                    if (refTypeId.equals(STATEMENT_REF_TYPE_ID)) {
                        StatementInfo refedS = statementService.getStatement(referencedObject.getStatementId());
                        if (stmtVO == null || stmtVO.getStatementInfo().getType().equals(
                                refedS.getType())) {
                            StatusInfo status = statementService.deleteRefStatementRelation(refId);
                            if (!status.getSuccess()) {
                                throw new Exception("Unable to remove statement with id: " + 
                                        stmtVO.getStatementInfo().getId() + " from clu with id: " + cluId);  
                            }
                        }
                    }
                }
            }
    		
            if (stmtVO != null) {
                stmtVO.composeStatementTreeViewInfo(stmtVO, statementTreeViewInfo);
                updatedSTVInfo = statementService.updateStatementTreeView(stmtVO.getStatementInfo().getId(), statementTreeViewInfo);
                refStatementRelationInfo = new RefStatementRelationInfo();
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
		
		//FIXME remove once we can use "kuali.luStatementType.createCourseAcademicReadiness" to
		// determine which rules belong to the course proposal screen
  		List<String> courseAcademicReadinessRules = new ArrayList<String>() {
    		   {
    			   add("kuali.luStatementType.prereqAcademicReadiness");
    			   add("kuali.luStatementType.coreqAcademicReadiness");
    			   add("kuali.luStatementType.antireqAcademicReadiness");
    			   add("kuali.luStatementType.enrollAcademicReadiness");    			   
    		   }
    		};  		
    		
    	/* TODO
    	List<String> createCourseAcademicReadinessRuleTypes;   		
		try {    		
			List<LuStatementTypeInfo> relatedStatementTypes = luService.getLuStatementTypesForLuStatementType(luStatementTypeKey);
			createCourseAcademicReadinessRuleTypes = relatedStatementTypes.;
		} catch (Exception e) {
			logger.error("Error fetching rules from luService:" + cluId, e);
			throw new RuntimeException("Error fetching statement types for statement type " +
											"'kuali.luStatementType.createCourseAcademicReadiness' from luService:" + cluId);
		} */   		
    		
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
					if (!courseAcademicReadinessRules.contains(statementInfo.getType())) {
						continue;
					}
					
					StatementVO statementVO = createStatementVO(statementInfo);
	
					
	            	//create a blank root statementVO
					/*
	                LuStatementInfo newLuStatementInfo = new LuStatementInfo();
	                newLuStatementInfo.setOperator(StatementOperatorTypeKey.AND);
	                newLuStatementInfo.setType(statementType);
	                StatementVO statementVO = new StatementVO();                            
	                statementVO.setLuStatementInfo(newLuStatementInfo);         	            	                
	                newPrereqInfo.setId(Integer.toString(id++));  */				
					
					RuleInfo ruleInfo = new RuleInfo();
					ruleInfo.setCluId(cluId);
					ruleInfo.setEditHistory(new EditHistory());
					ruleInfo.setStatementVO(statementVO);
					ruleInfo.setSelectedStatementType(statementInfo.getType());
					
			        EditHistory editHistory = new EditHistory();
			        editHistory.save(statementVO);
			        ruleInfo.setEditHistory(editHistory);
					
					ruleInfos.add(ruleInfo);
				}
			}
		} catch (Exception e) {
			logger.error("Error fetching rules from luService:" + cluId, e);
			throw new RuntimeException("Error fetching rules from luService:" + cluId);
		}
	
		return ruleInfos;
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
				    nl = statementService.translateReqComponentToNL(childReqComponent, "KUALI.CATALOG", "en");
				} catch(Exception e) {
					logger.error("Error fetching NL for req. component:" + childReqComponent.getRequiredComponentType().getId(), e);
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
	
    //work in progress
    private void deleteRule(String cluId, StatementVO statement, boolean topLevel) throws Exception {
        
        List<StatementVO> stmtVOs = statement.getStatementVOs();       
        List<ReqComponentVO> reqCompVOs = statement.getReqComponentVOs();        
        
        if ((stmtVOs != null) && (reqCompVOs != null) && (stmtVOs.size() > 0) && (reqCompVOs.size() > 0))
        {
        	throw new Exception("Internal error: found both Statements and Requirement Components on the same level of boolean expression");
        }
        
        //if this is the top level statement and other clu/statement reference this statement
        // then just disconnect it from the clu and we are done
        if (topLevel) {
        	
        	StatementInfo topStmt = statement.getStatementInfo();
        	
        	//remove the statement from clu    	

    		//FIXME - LU Service Change
//            StatusInfo status = luService.removeLuStatementFromClu(cluId, topStmt.getId());
//            if (!status.getSuccess()) {
//            	throw new Exception("Unable to remove statement with id: " + topStmt.getId() + " from clu with id: " + cluId);	
//            }  
        	        	
        	//first check if there is a parent statement
            //(TODO right now the schema shows one to many relationship between parent and child which limits the re-usability
            // of statements because without many-to-many relationship, statement trees can't share branches. Rather clus 
            // can only share different portions of the same tree
            String parentId = topStmt.getParentId();
        	if ((parentId != null) && !parentId.isEmpty()) {
        		//we can't delete this statement because it is being used by its parent that might be referenced by a clu
        		return;
        	}
        	
        	//then check if other clus reference this statement and return if they do
        	//TODO
        }       
        
        if ((stmtVOs != null) && (stmtVOs.size() > 0)) {        	          

            //remove children belonging to each statement
            for (StatementVO stmtVO : stmtVOs) {            	          	
            	
            	StatementInfo stmt = stmtVO.getStatementInfo();
            	
            	//remove association with the parent statement
            	//TODO
            	
            	//first check if other statements reference this statement and return if they do
            	//TODO Not Applicable if statement has only one parent (this statement)
            	
            	//then check if other clus reference this statement and return if they do
            	//TODO
            	 	
            	//now we are safe to delete all leaves (req. components)
                deleteRule(cluId, stmtVO, false);
                
                //now delete the statement itself
                logger.info("DELETING Lu Statement with id: " + stmt.getId());
                
        		//FIXME - LU Service Change
//                StatusInfo status = luService.deleteLuStatement(stmt.getId());
//                if (!status.getSuccess()) {
//                	throw new Exception("Unable to delete Statement with id: " + stmt.getId());	
//                }                
            }            
        } else {        	
            
            //for each requirement component, first find whether it referenced by other statements..
            for (ReqComponentVO reqCompVO : reqCompVOs) {             
            	
            	ReqComponentInfo reqComp = reqCompVO.getReqComponentInfo();
            	
            	//remove association with this statement
            	//TODO
            	
            	//TODO implement
        		//FIXME - LU Service Change
//                List<LuStatementInfo> refStmts = luService.getStatementsUsingComponent(reqComp.getId()); 
//                
//            	//if we have other statements referencing this req. component then don't delete it
//            	if ((refStmts == null) || (refStmts.size() > 1)) {
//            	    continue;
//            	}
//            	
//                logger.info("DELETING Req. Component with id: " + reqComp.getId());
//            	StatusInfo status = luService.deleteReqComponent(reqComp.getId());
//            	if (!status.getSuccess()) {            	
//                    throw new Exception("Unable to delete Req. Component with id: " + reqComp.getId());
//            	}
            }             
        }               
        
        return;
    }     

}
