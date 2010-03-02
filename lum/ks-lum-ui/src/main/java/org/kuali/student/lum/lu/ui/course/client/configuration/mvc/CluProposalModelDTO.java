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
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOAdapter;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;

import com.google.gwt.core.client.GWT;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * @author Kuali Student Team
 */
@Deprecated
public class CluProposalModelDTO extends ModelDTO {

   private static final long serialVersionUID = 1L;
   private boolean adaptersCreated = false;

   private List<RuleInfo> ruleInfos = new ArrayList<RuleInfo>();
	   
   public CluProposalModelDTO() {
		ModelDTO cluInfoModelDTO = new ModelDTO(CluDictionaryClassNameHelper.CLU_INFO_CLASS);
		cluInfoModelDTO.setKey("cluInfo");
		ModelDTOValue.ModelDTOType cluInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
		cluInfoModelDTOValue.set(cluInfoModelDTO);
		
		ModelDTO proposalInfoModelDTO = new ModelDTO(CluDictionaryClassNameHelper.PROPOSAL_INFO_CLASS);
		proposalInfoModelDTO.setKey("proposalInfo");
		ModelDTOValue.ModelDTOType proposalInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
		proposalInfoModelDTOValue.set(proposalInfoModelDTO);
		
		map.put("cluInfo", cluInfoModelDTOValue);
		map.put("proposalInfo", proposalInfoModelDTOValue);
		// ModelDTOValue.ModelDTOType loInfoModelDTOValue = LOInfoModelDTO.createLOInfoModelDTOValue(LOInfoModelDTO.DTO_KEY);
        // map.put(LOInfoModelDTO.DTO_KEY, loInfoModelDTOValue);

		//You MUST create adapters after, no dictionary for top level at this point
		checkAndCreateAdapters();
	}

    /**
     * The CluProposalModelDTO extends the base ModelDTO("CluInfo") to add additional
     * properties to a clu, eg. Activity clus.
     * 
     * @see org.kuali.student.common.ui.client.mvc.dto.ModelDTO#put(java.lang.String, org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     */
    @Override
    public void put(String key, ModelDTOValue value, boolean autoCommit) {
    	checkAndCreateAdapters();
    	super.put(key, value, autoCommit);            
    }
    
    @Override
    public void put(String key, ModelDTOValue value) {
    	checkAndCreateAdapters();
    	super.put(key, value);            
    }


    /**
     * @see org.kuali.student.common.ui.client.mvc.dto.ModelDTO#get(java.lang.String)
     */
    @Override
    public ModelDTOValue get(String key) {
    	checkAndCreateAdapters();
        return super.get(key);            
    }
    
    private void checkAndCreateAdapters(){
    	if(GWT.isClient() && adaptersCreated == false){
	    	setAdapter(new ModelDTOAdapter(this, null, null));
	    	//CluInfo
	    	ModelDTO cluInfoModelDTO = (ModelDTO) ((ModelDTOType) this.map.get("cluInfo")).get();
	    	cluInfoModelDTO.setAdapter(new ModelDTOAdapter(cluInfoModelDTO, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "cluInfo"));
	    	//ProposalInfo
	    	ModelDTO proposalInfoModelDTO = (ModelDTO) ((ModelDTOType) this.map.get("proposalInfo")).get();
	    	proposalInfoModelDTO.setAdapter(new ModelDTOAdapter(proposalInfoModelDTO, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "proposalInfo"));
	    	//LoInfo
	    	// ModelDTO loInfoModelDTO = (ModelDTO) ((ModelDTOType) this.map.get(LOInfoModelDTO.DTO_KEY)).get();
	    	// loInfoModelDTO.setAdapter(new ModelDTOAdapter(loInfoModelDTO,CluDictionaryClassNameHelper.getObjectKeytoClassMap(), LOInfoModelDTO.DTO_KEY));
	    	adaptersCreated = true;
    	}
    }

    public List<RuleInfo> getRuleInfos() {
        return ruleInfos;
    }

    public void setRuleInfos(List<RuleInfo> ruleInfos) {
        this.ruleInfos = ruleInfos;
    }
    
    public boolean isAdaptersCreated() {
    	return adaptersCreated;
    }

    public void setAdaptersCreated(boolean adaptersCreated) {
    	this.adaptersCreated = adaptersCreated;
    }
}
