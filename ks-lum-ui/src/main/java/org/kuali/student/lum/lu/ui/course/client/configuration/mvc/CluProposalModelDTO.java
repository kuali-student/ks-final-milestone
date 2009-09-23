/*
 * Copyright 2007 The Kuali Foundation Licensed under the Educational Community License, Version 1.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.opensource.org/licenses/ecl1.php Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and limitations under the License.
 */
package org.kuali.student.lum.lu.ui.course.client.configuration.mvc;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOAdapter;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;

import com.google.gwt.user.client.Window;

/**
 * This is a description of what this class does - Will Gomes don't forget to fill this in.
 * 
 * @author Kuali Student Team
 */
public class CluProposalModelDTO extends ModelDTO {

   private static final long serialVersionUID = 1L;
	   
   public CluProposalModelDTO() {
	   Window.alert("GOT HERE");
		setAdapter(new ModelDTOAdapter(this, null, null));
	        
		ModelDTO cluInfoModelDTO = new ModelDTO(CluDictionaryClassNameHelper.CLU_INFO_CLASS);
		cluInfoModelDTO.setAdapter(new ModelDTOAdapter(cluInfoModelDTO, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "cluInfo"));
	
		ModelDTOValue.ModelDTOType cluInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
		cluInfoModelDTOValue.set(cluInfoModelDTO);
		
		ModelDTO proposalInfoModelDTO = new ModelDTO(CluDictionaryClassNameHelper.PROPOSAL_INFO_CLASS);
		proposalInfoModelDTO.setAdapter(new ModelDTOAdapter(proposalInfoModelDTO, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "proposalInfo"));
		
		ModelDTOValue.ModelDTOType proposalInfoModelDTOValue = new ModelDTOValue.ModelDTOType();
		proposalInfoModelDTOValue.set(proposalInfoModelDTO);
		
		this.put("cluInfo", cluInfoModelDTOValue);
		this.put("proposalInfo", proposalInfoModelDTOValue);		
	}

/*    *//**
     * The CluProposalModelDTO extends the base ModelDTO("CluInfo") to add additional
     * properties to a clu, eg. Activity clus.
     * 
     * @see org.kuali.student.common.ui.client.mvc.dto.ModelDTO#put(java.lang.String, org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue)
     *//*
    @Override
    public void put(String key, ModelDTOValue value) {
    	if(GWT.isClient() && getAdapter() == null){
    		setAdapter(new ModelDTOAdapter(this, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "cluInfo"));
    	}
    	super.put(key, value);            
    }


    *//**
     * @see org.kuali.student.common.ui.client.mvc.dto.ModelDTO#get(java.lang.String)
     *//*
    @Override
    public ModelDTOValue get(String key) {
    	if(GWT.isClient() && getAdapter() == null){
    		setAdapter(new ModelDTOAdapter(this, CluDictionaryClassNameHelper.getObjectKeytoClassMap(), "cluInfo"));
    	}
        return super.get(key);            
    } */   
}
