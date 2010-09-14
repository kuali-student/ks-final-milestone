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

package org.kuali.student.lum.lu.assembly;


import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseConstants;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoHelper;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.server.gwt.LuRuleInfoPersistanceBean;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

/**
 * This class is just meant to create a copy of the given credit course proposal data.
 * The only method implemented is get(String id) which will return a saved deep copy of 
 * the credit course proposal with that id. 
 *
 */

//TODO: Rewrite to extend/use CreditCourseRpcService or replace with filters
public class ModifyCreditCourseProposalManager{

    private final Logger LOG = Logger.getLogger(ModifyCreditCourseProposalManager.class);
	
	Assembler<Data,Void> creditCourseAssembler; 
	LuService luService;
	StatementService statementService;
	private ProposalService proposalService;
	
	public Data getNewProposalWithCopyOfClu(String cluId) throws AssemblyException {
		
		//Get The data to copy
		Data data = creditCourseAssembler.get(cluId);
		
		//Clear out all ids in the data and set runtime data to created
		clearIdsRecursively(data, false);
		
		//Create the proposal and attach the course
		LuData luData = new LuData();
		CreditCourseProposalHelper result = CreditCourseProposalHelper.wrap(luData);
		CreditCourseHelper course = CreditCourseHelper.wrap(data);
		course.setCopyOfCourseId(cluId);
		
		course.setState(null);
		
		List<ProposalInfo> proposalList = null;
		try{
		    proposalList = proposalService.getProposalsByReference(CreditCourseProposalAssembler.PROPOSAL_REFERENCE_TYPE, cluId);
		} catch(DoesNotExistException dnex) {
			// swallow this one since existing courses might not have proposals associated with them
		} catch(Exception e){
		    throw new AssemblyException(e.getMessage(), e);
		}
		
		CreditCourseProposalInfoHelper proposal = CreditCourseProposalInfoHelper.wrap(new Data());
		
		// Can we have more than one course in a proposal? if yes how do we handle this here?
		if(proposalList != null && !proposalList.isEmpty()) {
			proposal.setRationale(proposalList.get(0).getRationale());
		}
		
		result.setCourse(course);
		result.setProposal(proposal);
		
		//Get a copy of the RuleInfoData
        LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
        ruleInfoBean.setLuService(luService);
        ruleInfoBean.setStatementService(statementService);
        List<RuleInfo> rules = ruleInfoBean.fetchRules(cluId);
		
        //Clear out ids from resultInfo object and anything else with external ids
    	Integer tempId = 0;
        for(RuleInfo rule:rules){
    		rule.setCluId(null);
    		clearStatementIds(rule.getStatementVO(),tempId);
    	}
		luData.setRuleInfos(rules);
	
		//add any additional data that defines this as a copy here (state? other relationships/flags)
		return result.getData();
	}
	


	private void clearStatementIds(StatementVO stmt,Integer tempId) {
		//increment the TempId
		tempId++;
		//Clear out any ids that are statements
		for(ReqComponentVO reqComp: stmt.getAllReqComponentVOs()){
			reqComp.setId(tempId.toString());
			reqComp.getReqComponentInfo().setId(tempId.toString());
		}
		stmt.getStatementInfo().setId(null);
		stmt.getStatementInfo().setParentId(null);
		stmt.getStatementInfo().setReqComponentIds(null);
		for(StatementVO nestedStmt:stmt.getStatementVOs()){
			clearStatementIds(nestedStmt,tempId);
		}
		
	}

	private void clearIdsRecursively(Data data, boolean shouldSetCreated){
		//Set runtime create to true
    	//AssemblerUtils.setCreated(data, true);
		if(data==null) return;
		
    	//Loop through all properties
    	for(Iterator<Property> i=data.realPropertyIterator();i.hasNext();){
    		
    		Property prop = i.next();
    		
    		//recurse through any nested maps (don't recurse through runtime data since the setCreated call will create new Data children
    		//also don't recurse through lo categories
    		if(prop.getValueType().isAssignableFrom(Data.class)&&!"_runtimeData".equals(prop.getKey())&&!"categories".equals(prop.getKey())){
    			
    			if(shouldSetCreated){
    				//Set runtime data to created and clear out other runtime data(versions etc)
    	            Data runtime = data.get("_runtimeData");
    	            if (runtime == null) {
    	                runtime = new Data();
    	                data.set("_runtimeData", runtime);
    	            }else{
    	            	runtime.remove(new Data.StringKey("versions"));	
    	            }
    	            runtime.set("created", true);
    			}
    			
    			//From this point on its a format tree so set the runtime data to created (and clear out other runtime data)
    			if("formats".equals(prop.getKey())||CreditCourseConstants.OUTCOME_OPTIONS.equals(prop.getKey())){
    				shouldSetCreated=true;
    			}
    			clearIdsRecursively((Data)prop.getValue(), shouldSetCreated);
    		}
    		
    		//Clear out any id fields
    		if( prop.getKey() instanceof String && "id".equals(prop.getKey())){
    			data.set(prop.getWrappedKey(), (String)null);
    		}
    	}
    	
    }

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



	public Assembler<Data, Void> getCreditCourseAssembler() {
		return creditCourseAssembler;
	}



	public void setCreditCourseAssembler(Assembler<Data, Void> creditCourseAssembler) {
		this.creditCourseAssembler = creditCourseAssembler;
	}



    public ProposalService getProposalService() {
        return proposalService;
    }



    public void setProposalService(ProposalService proposalService) {
        this.proposalService = proposalService;
    }
	

}
