package org.kuali.student.lum.lu.assembly;


import java.util.Iterator;
import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

/**
 * This class is just meant to create a copy of the given credit course proposal data.
 * The only method implemented is get(String id) which will return a saved deep copy of 
 * the credit course proposal with that id. 
 *
 */
public class ModifyCreditCourseProposalManager{

	Assembler<Data,Void> creditCourseProposalAssembler; 

	public Data getCopy(String id) throws AssemblyException {
		
		//Get The data to copy
		Data data = creditCourseProposalAssembler.get(id);
		
		//Clear out all ids in the data
		clearIdsRecursively(data);
		
		//Clear the references
		CreditCourseProposalHelper proposal = CreditCourseProposalHelper.wrap(data);
		proposal.getProposal().setReferences(new Data());
    	
		//Clear out ids from resultInfo object and anything else with external ids
        if (data instanceof LuData) {
        	LuData luData = (LuData)data;
        	List<RuleInfo> rules = luData.getRuleInfos();
        	for(RuleInfo rule:rules){
        		rule.setCluId(null);
        		rule.setId(null);
        		clearStatementIds(rule.getStatementVO());
        	}
        }
		//TODO add any additional data that defines this as a copy, or add clu-clu 
		//relations to link this to the original
		
        //Resave the data and return with new ids
    	SaveResult<Data> savedCopy = creditCourseProposalAssembler.save(data); 
    	return savedCopy.getValue();
	}
	
    private void clearStatementIds(StatementVO stmt) {
		for(ReqComponentVO reqComp: stmt.getAllReqComponentVOs()){
			reqComp.setId(null);
			reqComp.getReqComponentInfo().setId(null);
		}
		stmt.getStatementInfo().setId(null);
		stmt.getStatementInfo().setParentId(null);
		for(StatementVO nestedStmt:stmt.getStatementVOs()){
			clearStatementIds(nestedStmt);
		}
		
	}

	private void clearIdsRecursively(Data data){
    	for(Iterator<Property> i=data.iterator();i.hasNext();){
    		Property prop = i.next();
    		//recurse through any nested maps
    		if(prop.getValueType().isAssignableFrom(Data.class)){
    			clearIdsRecursively((Data)prop.getValue());
    		}
    		//Clear out any id fields
    		if( prop.getKey() instanceof String && "id".equals(prop.getKey())){
    			data.set(prop.getWrappedKey(), (String)null);
    		}
    	}
    }

	public Assembler<Data, Void> getCreditCourseProposalAssembler() {
		return creditCourseProposalAssembler;
	}

	public void setCreditCourseProposalAssembler(
			Assembler<Data, Void> creditCourseProposalAssembler) {
		this.creditCourseProposalAssembler = creditCourseProposalAssembler;
	}
	

}
