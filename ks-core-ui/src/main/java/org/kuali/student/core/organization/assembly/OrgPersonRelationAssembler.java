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

package org.kuali.student.core.organization.assembly;

import org.apache.log4j.Logger;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Data.Property;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.old.Assembler;
import org.kuali.student.r1.common.assembly.old.data.SaveResult;
import org.kuali.student.r1.common.assembly.util.AssemblerUtils;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.core.organization.assembly.data.server.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.server.org.OrgPersonHelper;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.isCreated;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.isDeleted;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.isUpdated;

public class OrgPersonRelationAssembler implements Assembler<Data, OrgPersonHelper>{
	final Logger LOG = Logger.getLogger(OrgPersonRelationAssembler.class);
    private OrganizationService orgService;
    private Metadata metadata;
    private DataModel orgPersonModel = new DataModel();
    public static final String PERSON_PATH                  = "orgPersonRelationInfo";
    
    public void setMetaData(Metadata metadata){
        this.metadata=metadata;
        
    }

    public void setOrgService(OrganizationService service){
        orgService = service;
    }
    @Override
    public Data assemble(OrgPersonHelper input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgPersonHelper disassemble(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Data get(String id) throws AssemblyException {
        List<OrgPersonRelationInfo> relations = new ArrayList<OrgPersonRelationInfo>();
        Data orgRelationMap = null;
        try{
            relations = orgService.getOrgPersonRelationsByOrg(id, ContextUtils.getContextInfo());
            orgRelationMap = buildOrgPersonRelationMap(relations, ContextUtils.getContextInfo());
        }
        catch(Exception e){
        	LOG.error(e);
        }
        return orgRelationMap;
    }



    @Override
    public SaveResult<Data> save(Data input) throws AssemblyException {
    	SaveResult<Data> result = new SaveResult<Data>();
        result.setValue(input);
        List<ValidationResultInfo> validationResults = validate(input);
        result.setValidationResults(validationResults);
        
        updatePersonRelations(input, ContextUtils.getContextInfo());
        return result;
    }



    @Override
    public List<ValidationResultInfo> validate(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    private void updatePersonRelations(Data input, ContextInfo context) throws AssemblyException{
        if (input == null) {
            return;
        }
        DataModelDefinition def = new DataModelDefinition(metadata);
        orgPersonModel.setDefinition(def);
        //Set this for readonly permission
        QueryPath metaPath = QueryPath.concat(null, PERSON_PATH);
        Metadata orgPersonMeta =orgPersonModel.getMetadata(metaPath);
        
        for (Iterator<Property> propertyIter = ((Data)input.get("orgPersonRelationInfo")).iterator();propertyIter.hasNext();) {
        	Property p = propertyIter.next();
            OrgPersonHelper orgPersonHelper = OrgPersonHelper.wrap((Data) p.getValue());
            if (isUpdated(orgPersonHelper.getData())) {
                if (orgPersonMeta.isCanEdit()) {
                    OrgPersonRelationInfo orgPersonRelationInfo = buildOrgPersonRelationInfo(orgPersonHelper);
                    orgPersonRelationInfo.setId(orgPersonHelper.getId());
                    try {
                        OrgPersonRelationInfo result = orgService.updateOrgPersonRelation(orgPersonHelper.getId(), orgPersonRelationInfo, context);
                        addVersionIndicator(orgPersonHelper.getData(), OrgPersonRelationInfo.class.getName(), result.getId(), result.getMeta().getVersionInd());
                    } catch (Exception e) {
                        throw new AssemblyException();
                    }
                    AssemblerUtils.setUpdated(orgPersonHelper.getData(), false);
                }
            }
            else if(isDeleted(orgPersonHelper.getData())&&orgPersonHelper.getId()!=null){
                try{
                    orgService.deleteOrgPersonRelation(orgPersonHelper.getId(), context);
                    propertyIter.remove();
                }
                catch(Exception e ){
                	LOG.error(e);
                    throw(new AssemblyException());
                }
            }
            else if(isCreated(orgPersonHelper.getData())){
                orgPersonHelper.setOrgId((OrgHelper.wrap((Data) input.get("orgInfo")).getId()));
                OrgPersonRelationInfo orgPersonRelationInfo = buildOrgPersonRelationInfo(orgPersonHelper);
                try{
                    OrgPersonRelationInfo  result = orgService.createOrgPersonRelation(orgPersonHelper.getOrgId(), orgPersonHelper.getPersonId(), orgPersonHelper.getTypeKey(), orgPersonRelationInfo, context);
                    orgPersonHelper.setId(result.getId());
                    addVersionIndicator(orgPersonHelper.getData(),OrgPersonRelationInfo.class.getName(),result.getId(),result.getMeta().getVersionInd());
                }
                catch(Exception e ){
                	LOG.error(e);
                    throw new AssemblyException();
                }
                AssemblerUtils.setCreated(orgPersonHelper.getData(), false);
            }
           
          
        }
    }
    
    private OrgPersonRelationInfo buildOrgPersonRelationInfo(OrgPersonHelper orgPersonHelper){
        OrgPersonRelationInfo orgPersonRelationInfo = new OrgPersonRelationInfo();
        orgPersonRelationInfo.setOrgId(orgPersonHelper.getOrgId());      
        orgPersonRelationInfo.setPersonId(orgPersonHelper.getPersonId());
        orgPersonRelationInfo.setTypeKey(orgPersonHelper.getTypeKey());
        orgPersonRelationInfo.setEffectiveDate(orgPersonHelper.getEffectiveDate());
        orgPersonRelationInfo.setExpirationDate(orgPersonHelper.getExpirationDate());
        if (orgPersonHelper.getState()!=null)
        	orgPersonRelationInfo.setStateKey(orgPersonHelper.getState());
        else
        	orgPersonHelper.setState("Active");
        	
       
        if (isModified(orgPersonHelper.getData())) {
            if (isUpdated(orgPersonHelper.getData())||isDeleted(orgPersonHelper.getData())) {
                MetaInfo metaInfo = new MetaInfo();
                orgPersonRelationInfo.setMeta(metaInfo);
                orgPersonRelationInfo.setId(orgPersonHelper.getId());
            }
        }
        if(orgPersonRelationInfo.getMeta()!=null){
            orgPersonRelationInfo.getMeta().setVersionInd(getVersionIndicator(orgPersonHelper.getData()));
        }
        return orgPersonRelationInfo;
    }
    
    private Data buildOrgPersonRelationMap( List<OrgPersonRelationInfo> relations, ContextInfo context){
        Data orgRelations = new Data();
        try {
        int count =0;

        DataModelDefinition def = new DataModelDefinition(metadata);
        orgPersonModel.setDefinition(def);
        //Set this for readonly permission
        QueryPath metaPath = QueryPath.concat(null, PERSON_PATH);
        Metadata orgPersonMeta =orgPersonModel.getMetadata(metaPath);
        for(OrgPersonRelationInfo relation:relations){
            Data relationMap = new Data();
            OrgPersonHelper orgPersonHelper = OrgPersonHelper.wrap(relationMap);
            orgPersonHelper.setId(relation.getId());
            orgPersonHelper.setOrgId(relation.getOrgId());
            if(!orgPersonMeta.isCanEdit()){
                orgPersonHelper.setTypeKey(orgService.getOrgPersonRelationByTypeAndOrg(relation.getTypeKey(),relation.getOrgId(), context).getTypeKey());
            }
            else{
                orgPersonHelper.setTypeKey(relation.getTypeKey());
            }
            
            orgPersonHelper.setPersonId(relation.getPersonId());
            orgPersonHelper.setEffectiveDate(relation.getEffectiveDate());
            orgPersonHelper.setExpirationDate(relation.getExpirationDate());
            addVersionIndicator(orgPersonHelper.getData(),OrgPersonRelationInfo.class.getName(),relation.getId(),relation.getMeta().getVersionInd());
            orgRelations.set(count,orgPersonHelper.getData());
            count = count +1;
        }
        }
        catch(Exception e){
        	LOG.error(e);
        }
        return orgRelations;
    }
    @Override
    public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

}
