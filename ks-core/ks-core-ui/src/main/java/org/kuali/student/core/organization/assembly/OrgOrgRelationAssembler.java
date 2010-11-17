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


import static org.kuali.student.core.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isCreated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isDeleted;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isUpdated;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.util.AssemblerUtils;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.assembly.data.server.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.server.org.OrgorgRelationHelper;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgOrgRelationAssembler implements Assembler<Data, OrgorgRelationHelper>{
	final Logger LOG = Logger.getLogger(OrgOrgRelationAssembler.class);
    public static final String ORGORG_PATH                  = "orgOrgRelationInfo";
    private OrganizationService orgService;
    private Metadata metadata;
    private DataModel orgOrgModel = new DataModel();
    
    public void setMetaData(Metadata metadata){
        this.metadata=metadata;
        
    }
    @Override
    public Data assemble(OrgorgRelationHelper input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgorgRelationHelper disassemble(Data input) throws AssemblyException {
        return null;

    }

    @Override
    public Data get(String id) throws AssemblyException {

        List<OrgOrgRelationInfo> relations = new ArrayList<OrgOrgRelationInfo>();
        List<OrgOrgRelationInfo> parentRelations = new ArrayList<OrgOrgRelationInfo>();
        Data orgOrgRelationMap = null;
        try{
            relations = orgService.getOrgOrgRelationsByOrg(id);
            parentRelations = orgService.getOrgOrgRelationsByRelatedOrg(id);
            orgOrgRelationMap = buildOrgOrgRelationDataMap(relations,parentRelations);
            
        }
        catch(DoesNotExistException dnee){
            return null;
            
        }
        catch(Exception e){
            LOG.error(e);
            throw(new AssemblyException());
        }
        return orgOrgRelationMap;
    }

    @Override
    public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    public void setOrgService(OrganizationService service){
        orgService = service;
    }
    
    @Override
    public SaveResult<Data> save(Data input) throws AssemblyException {
        addOrgOrgRelations(input);
        SaveResult<Data> result = new SaveResult<Data>();
        List<ValidationResultInfo> validationResults = validate(input);
        result.setValue(input);
        
        return result;
    }

    @Override
    public List<ValidationResultInfo> validate(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    
    private OrgOrgRelationInfo buildOrgOrgRelationInfo(OrgorgRelationHelper orgorgRelationHelper){
        OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();
        
        if(orgorgRelationHelper.getOrgOrgRelationTypeKey().startsWith("REV_")){
            orgOrgRelationInfo.setOrgId(orgorgRelationHelper.getRelatedOrgId());
            orgOrgRelationInfo.setRelatedOrgId(orgorgRelationHelper.getOrgId());
            orgOrgRelationInfo.setType(orgorgRelationHelper.getOrgOrgRelationTypeKey().substring(4));
        }
        else{
            orgOrgRelationInfo.setOrgId(orgorgRelationHelper.getOrgId());
            orgOrgRelationInfo.setRelatedOrgId(orgorgRelationHelper.getRelatedOrgId());
            orgOrgRelationInfo.setType(orgorgRelationHelper.getOrgOrgRelationTypeKey());
        }
       
        
        orgOrgRelationInfo.setEffectiveDate(orgorgRelationHelper.getEffectiveDate());
        orgOrgRelationInfo.setExpirationDate(orgorgRelationHelper.getExpirationDate());
        if (isModified(orgorgRelationHelper.getData())) {
            if (isUpdated(orgorgRelationHelper.getData())) {
                MetaInfo metaInfo = new MetaInfo();
                orgOrgRelationInfo.setMetaInfo(metaInfo);
            } 
            else if (isDeleted(orgorgRelationHelper.getData())) {
            }
            else if (isCreated(orgorgRelationHelper.getData())) {
            }
        }
        if(orgOrgRelationInfo.getMetaInfo()!=null){
            orgOrgRelationInfo.getMetaInfo().setVersionInd(getVersionIndicator(orgorgRelationHelper.getData()));
        }
       
        return orgOrgRelationInfo;
    }
    
    private void addOrgOrgRelations(Data input) throws AssemblyException{
        if (input == null) {
            return;
        }
        DataModelDefinition def = new DataModelDefinition(metadata);
        orgOrgModel.setDefinition(def);
        QueryPath metaPath = QueryPath.concat(null, ORGORG_PATH);
        Metadata orgOrgMeta =orgOrgModel.getMetadata(metaPath);
        for (Iterator<Property> propIter = ((Data)input.get("orgOrgRelationInfo")).iterator();propIter.hasNext();) {
        	Property p = propIter.next();
            OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap((Data)p.getValue());
            if (isUpdated(orgOrgRelation.getData())) {
                if (orgOrgMeta.isCanEdit()) {
                    OrgOrgRelationInfo orgOrgRelationInfo = buildOrgOrgRelationInfo(orgOrgRelation);
                    orgOrgRelationInfo.setId(orgOrgRelation.getId());
                    try {
                        OrgOrgRelationInfo result = orgService.updateOrgOrgRelation(orgOrgRelationInfo.getId(), orgOrgRelationInfo);
                        addVersionIndicator(orgOrgRelation.getData(), OrgOrgRelationInfo.class.getName(), result.getId(), result.getMetaInfo().getVersionInd());
                    } catch (Exception e) {
                    	LOG.error(e);
                        throw (new AssemblyException());
                    }
                }
                AssemblerUtils.setUpdated(orgOrgRelation.getData(), false);
            }
            else if(isDeleted(orgOrgRelation.getData())&&orgOrgRelation.getId()!=null){
//              OrgOrgRelationInfo orgOrgRelationInfo = buildOrgOrgRelationInfo(orgOrgRelation);
//              orgOrgRelationInfo.setId(orgOrgRelation.getId());
              try{
                  if(orgOrgRelation.getId()!=null){
                      StatusInfo  result = orgService.removeOrgOrgRelation(orgOrgRelation.getId());
                      propIter.remove();
                  }
              }
              catch(Exception e ){
            	  LOG.error(e);
                  throw(new AssemblyException());
              }
          }
            else if(isCreated(orgOrgRelation.getData())){
                //Set the OrgId from the OrgInfo data Map which is set on saving the Org
                orgOrgRelation.setOrgId((OrgHelper.wrap((Data)input.get("orgInfo")).getId()));
                
                OrgOrgRelationInfo orgOrgRelationInfo = buildOrgOrgRelationInfo(orgOrgRelation);
                try{
                    OrgOrgRelationInfo  result = orgService.createOrgOrgRelation(orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(), orgOrgRelationInfo.getType(), orgOrgRelationInfo);
                    orgOrgRelation.setId(result.getId());
                    addVersionIndicator(orgOrgRelation.getData(),OrgOrgRelationInfo.class.getName(),result.getId(),result.getMetaInfo().getVersionInd());
                }
                catch(Exception e ){
                    LOG.error(e);
                    throw(new AssemblyException());
                }
                AssemblerUtils.setCreated(orgOrgRelation.getData(), false);
            }
          
          
            
            
        }

    }
    
    
    private Data buildOrgOrgRelationDataMap(List<OrgOrgRelationInfo> relations,List<OrgOrgRelationInfo> parentRelations) throws AssemblyException{
       
        Data orgOrgRelations = new Data();
        String relationTypeTranslation;
        DataModel orgProposalModel = new DataModel();
        DataModelDefinition def = new DataModelDefinition(metadata);
        orgProposalModel.setDefinition(def);
        int count = 0;
        try{
        for(OrgOrgRelationInfo relation:relations){
            Data relationMap = new Data();
            OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap(relationMap);
            orgOrgRelation.setId(relation.getId());
            orgOrgRelation.setOrgId(relation.getOrgId());
            orgOrgRelation.setRelatedOrgId(relation.getRelatedOrgId());
            
            
            //Set this for readonly permission
            QueryPath metaPath = QueryPath.concat(null, ORGORG_PATH);
            Metadata orgOrgMeta =orgProposalModel.getMetadata(metaPath);
            if(!orgOrgMeta.isCanEdit()){
                relationTypeTranslation =orgService.getOrgOrgRelationType(relation.getType()).getName();
                orgOrgRelation.setOrgOrgRelationTypeKey(relationTypeTranslation);
            }
            else{
                orgOrgRelation.setOrgOrgRelationTypeKey(relation.getType());
            }
            orgOrgRelation.setEffectiveDate(relation.getEffectiveDate());
            orgOrgRelation.setExpirationDate(relation.getExpirationDate());
            
            orgOrgRelations.set(count, orgOrgRelation.getData());
            addVersionIndicator(orgOrgRelation.getData(),OrgOrgRelationInfo.class.getName(),relation.getId(),relation.getMetaInfo().getVersionInd());
            count= count+1;
        }
        
        for(OrgOrgRelationInfo relation:parentRelations){
            Data relationMap = new Data();
            OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap(relationMap);
            orgOrgRelation.setId(relation.getId());
            orgOrgRelation.setRelatedOrgId(relation.getOrgId());
            orgOrgRelation.setOrgId(relation.getRelatedOrgId());
            
            
            //Set this for readonly permission
            QueryPath metaPath = QueryPath.concat(null, ORGORG_PATH);
            Metadata orgOrgMeta =orgProposalModel.getMetadata(metaPath);
            if(!orgOrgMeta.isCanEdit()){
                relationTypeTranslation =orgService.getOrgOrgRelationType(relation.getType()).getRevName();
                orgOrgRelation.setOrgOrgRelationTypeKey(relationTypeTranslation);
            }
            else{
                orgOrgRelation.setOrgOrgRelationTypeKey("REV_" +relation.getType());
            }
            orgOrgRelation.setEffectiveDate(relation.getEffectiveDate());
            orgOrgRelation.setExpirationDate(relation.getExpirationDate());
            
            orgOrgRelations.set(count, orgOrgRelation.getData());
            addVersionIndicator(orgOrgRelation.getData(),OrgOrgRelationInfo.class.getName(),relation.getId(),relation.getMetaInfo().getVersionInd());
            count= count+1;
        }
        
        
        }
        catch(Exception e){
            LOG.error(e);
            throw(new AssemblyException());
        }
        return orgOrgRelations;
    }

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

}
