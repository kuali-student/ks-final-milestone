package org.kuali.student.lum.lu.ui.course.server.gwt;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.brms.statement.dto.StatementInfo;
import org.kuali.student.brms.statement.dto.ReqComponentInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.nlt.service.TranslationService;
import org.kuali.student.lum.ui.requirements.client.model.EditHistory;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

public class LuRuleInfoPersistanceBean {
	final Logger logger = Logger.getLogger(LuRuleInfoPersistanceBean.class);
	
	private LuService luService;
    private TranslationService translationService;	

	public LuService getLuService() {
		return luService;
	}
	
	public void setLuService(LuService luService) {
		this.luService = luService;
	}
	
	public TranslationService getTranslationService() {
		return translationService;
	}

	public void setTranslationService(TranslationService translationService) {
		this.translationService = translationService;
	}	
	
	//eventually this all needs to be one transaction
    public String saveRules(String cluId, List<RuleInfo> rules) throws Exception {
    	for (RuleInfo ruleInfo : rules) {
    		String rooStmtId = saveRule(cluId, ruleInfo.getStatementVO(), null);        
    		
    		//FIXME - LU Service Change
//    		StatusInfo status = luService.addLuStatementToClu(cluId, rooStmtId);
//            
//            if (!status.getSuccess()) {
//            	throw new Exception("Unable to add lu statement with id: " + rooStmtId + " to clu with id: " + cluId);	
//            } 
    	}
		return "";
    }	
	
    public String updateRules(String cluId, List<RuleInfo> newRules) throws Exception {
    	for (RuleInfo ruleInfo : newRules) {
    		StatementVO stmtVO = ruleInfo.getStatementVO();
    		
    		//TODO remove the original rule i.e. first retrieve the original statement from clu 
    		//deleteRule(cluId, origLuStatementInfo, true);
        	//for now just remove the top statement from clu    	
    		StatusInfo status;
    		StatementInfo topStmt = stmtVO.getStatementInfo();
    		if (topStmt.getId() != null){

    			//FIXME: LU Service API Change
//	    		status = luService.removeLuStatementFromClu(cluId, topStmt.getId());
//	            if (!status.getSuccess()) {
//	            	throw new Exception("Unable to remove statement with id: " + stmtVO.getLuStatementInfo().getId() + " from clu with id: " + cluId);	
//	            }
	            //FIXME: CLU ids are no longer in StatementInfo
//	            List<String> cluList = topStmt.getCluIds();
//	            List<String> cluListTemp = new ArrayList<String>(cluList);	            
//	            for(String cluIId : cluListTemp) {
//	                if (cluIId.equals(cluId))  {
//	                    cluList.remove(cluIId);
//	                    topStmt.setCluIds(cluList);
//	                }
//	            }	            
    		}
    		
    		String rooStmtId = saveRule(cluId, stmtVO, null);

    		//FIXME - LU Service Change
//    		status = luService.addLuStatementToClu(cluId, rooStmtId);
//            if (!status.getSuccess()) {
//            	throw new Exception("Unable to add lu statement with id: " + rooStmtId + " to clu with id: " + cluId);	
//            }     		
    	}
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
    		//FIXME - LU Service Change
			//luStatements = luService.getLuStatementsForClu(cluId);
			
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

        //FIXME - LU Service Change
//		if (statementIDs != null && statementIDs.size() > 0){
//			List<LuStatementInfo> childStatements = luService.getLuStatements(luStatementInfo.getLuStatementIds());
//			for (LuStatementInfo childStatement:childStatements){
//				StatementVO childStatementVO = createStatementVO(childStatement);
//				statementVO.addStatementVO(childStatementVO);
//			}
//		} else { //Fetch child requirement components		
//			List<ReqComponentInfo> childReqComponents = luService.getReqComponents(luStatementInfo.getReqComponentIds());
//			for (ReqComponentInfo childReqComponent:childReqComponents){
//				ReqComponentVO reqComponentVO = new ReqComponentVO(childReqComponent);
//					
//				String nl;
//				try {
//					nl = translationService.getNaturalLanguageForReqComponentInfo(childReqComponent, "KUALI.CATALOG", null);
//				} catch(Exception e) {
//					logger.error("Error fetching NL for req. component:" + childReqComponent.getRequiredComponentType().getId(), e);
//					throw new RuntimeException("Error fetching NL for req. component:" + childReqComponent.getRequiredComponentType().getId());
//				} 				
//				reqComponentVO.setTypeDesc(nl);
//				
//				statementVO.addReqComponentVO(reqComponentVO);
//			}
//		}
		
		return statementVO;
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

    //work in progress
    private String saveRule(String cluId, StatementVO statementVO, String parentId) throws Exception {
        
        StatementInfo parentStmt = statementVO.getStatementInfo();
    	List<StatementVO> stmtVOs = statementVO.getStatementVOs();       
        List<ReqComponentVO> reqCompVOs = statementVO.getReqComponentVOs();
        
        if ((stmtVOs != null) && (reqCompVOs != null) && (stmtVOs.size() > 0) && (reqCompVOs.size() > 0))
        {
        	throw new Exception("Internal error: found both Lu Statements and Requirement Components on the same level of boolean expression");
        }               
        
        //create this statement first             
        parentStmt.setParentId(parentId);
        parentStmt.setId(null);

		//FIXME - LU Service Change
//        LuStatementInfo newParentStmt = luService.createLuStatement(parentStmt.getType(), parentStmt);
//        if (newParentStmt == null) {
//        	throw new Exception("Unable to create Statement: " + parentStmt.getName());	
//        }          
        
        //next save contained statements or req. components
        if ((stmtVOs != null) && (stmtVOs.size() > 0)) {        	        

            //save children belonging to each statement

        	//FIXME - LU Service Change
//        	newParentStmt.setLuStatementIds(new ArrayList<String>());
//            for (StatementVO stmtVO : stmtVOs) {            	          	
//            	            	 
//                //Set statement type
//            	stmtVO.getLuStatementInfo().setType(newParentStmt.getType());
//            	/*
//                if (newParentStmt.getLuStatementType() == null){
//        			LuStatementTypeInfo luStatementTypeInfo = new LuStatementTypeInfo();
//        			luStatementTypeInfo.setId(newParentStmt.getLuStatementType().getId());
//        			newParentStmt.setLuStatementType(luStatementTypeInfo);
//        			newParentStmt.setType(luStatementTypeInfo.getId());
//                } */            	
//            	
//            	//save this statement and all its children and leaves
//                String childStmtId = saveRule(cluId, stmtVO, newParentStmt.getId());
//                
//                //add the child statement id to the parent statement
//                newParentStmt.getLuStatementIds().add(childStmtId);                                               
//            }             
//            
//        } else {
//        	           
//            //create each req. component
//        	newParentStmt.setReqComponentIds(new ArrayList<String>());
//            for (ReqComponentVO reqCompVO : reqCompVOs) {             
//            	ReqComponentInfo reqComp = reqCompVO.getReqComponentInfo();
//            	
//            	logger.info("CREATING Req. Component with id: " + reqComp.getId());
//            	reqComp.setId(null);
//            	ReqComponentInfo newReqCmp = luService.createReqComponent(reqComp.getType(), reqComp);
//            	if (newReqCmp == null) {            	
//                    throw new Exception("Unable to create Req. Component with id: " + reqComp.getId());
//            	}
//            	
//            	//add the new req. comp id to the statement
//            	newParentStmt.getReqComponentIds().add(newReqCmp.getId());
//            	
//            	reqCompVO.setReqComponentInfo(newReqCmp);
//            	reqCompVO.setId(newReqCmp.getId());            	
//            }             
        }                                                 
//        
//        //update statement with children ids
//        newParentStmt = luService.updateLuStatement(newParentStmt.getId(), newParentStmt);         
//        
//        statementVO.setLuStatementInfo(newParentStmt);        
//        
//        return newParentStmt.getId();
        	return null;
    }
}
