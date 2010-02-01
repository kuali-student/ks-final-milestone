package org.kuali.student.core.organization.assembly;


import java.util.ArrayList;
import java.util.List;

import static org.kuali.student.core.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isCreated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isUpdated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isDeleted;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.assembly.data.client.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.client.org.OrgorgRelationHelper;
import org.kuali.student.core.organization.assembly.data.server.OrgInfoData.ModificationState;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgOrgRelationAssembler implements Assembler<Data, OrgorgRelationHelper>{

    private OrganizationService orgService;
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
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Metadata getMetadata(String type, String state) throws AssemblyException {
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
    public SearchResult search(SearchRequest searchRequest) {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<ValidationResultInfo> validate(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    
    private OrgOrgRelationInfo buildOrgOrgRelationInfo(OrgorgRelationHelper orgorgRelationHelper){
        OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();
        
        
        orgOrgRelationInfo.setOrgId(orgorgRelationHelper.getOrgId());
        orgOrgRelationInfo.setRelatedOrgId(orgorgRelationHelper.getRelatedOrgId());
        orgOrgRelationInfo.setType(orgorgRelationHelper.getOrgOrgRelationTypeKey());
        orgOrgRelationInfo.setEffectiveDate(orgorgRelationHelper.getEffectiveDate());
        orgOrgRelationInfo.setExpirationDate(orgorgRelationHelper.getExpirationDate());
        if (isModified(orgorgRelationHelper.getData())) {
            if (isCreated(orgorgRelationHelper.getData())) {
            } 
            else if (isUpdated(orgorgRelationHelper.getData())) {
                MetaInfo metaInfo = new MetaInfo();
                orgOrgRelationInfo.setMetaInfo(metaInfo);
            } else if (isDeleted(orgorgRelationHelper.getData())) {
            }
        }
        if(orgOrgRelationInfo.getMetaInfo()!=null){
            orgOrgRelationInfo.getMetaInfo().setVersionInd(getVersionIndicator(orgorgRelationHelper.getData()));
        }
       
        return orgOrgRelationInfo;
    }
    
    private void addOrgOrgRelations(Data input){
        if (input == null) {
            return;
        }
        for (Property p : (Data)input.get("orgOrgRelationInfo")) {
            OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap((Data)p.getValue());
            if(isCreated(orgOrgRelation.getData())){
                //Set the OrgId from the OrgInfo data Map which is set on saving the Org
                orgOrgRelation.setOrgId((OrgHelper.wrap((Data)input.get("orgInfo")).getId()));
                
                OrgOrgRelationInfo orgOrgRelationInfo = buildOrgOrgRelationInfo(orgOrgRelation);
                try{
                    OrgOrgRelationInfo  result = orgService.createOrgOrgRelation(orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(), orgOrgRelationInfo.getType(), orgOrgRelationInfo);
                    orgOrgRelation.setId(result.getId());
                }
                catch(Exception e ){
                    e.printStackTrace();
                }
            }
            if(isUpdated(orgOrgRelation.getData())){
                OrgOrgRelationInfo orgOrgRelationInfo = buildOrgOrgRelationInfo(orgOrgRelation);
                orgOrgRelationInfo.setId(orgOrgRelation.getId());
                try{
                    OrgOrgRelationInfo  result = orgService.updateOrgOrgRelation(orgOrgRelationInfo.getId(), orgOrgRelationInfo);
                }
                catch(Exception e ){
                    e.printStackTrace();
                }
            }
            if(isDeleted(orgOrgRelation.getData())){
//                OrgOrgRelationInfo orgOrgRelationInfo = buildOrgOrgRelationInfo(orgOrgRelation);
//                orgOrgRelationInfo.setId(orgOrgRelation.getId());
                try{
                    if(orgOrgRelation.getId()!=null){
                        StatusInfo  result = orgService.removeOrgOrgRelation(orgOrgRelation.getId());
                    }
                }
                catch(Exception e ){
                    e.printStackTrace();
                }
            }
            
            
        }

    }
    
    public Data fetchOrgOrgRelationInfo(String orgId){
        List<OrgOrgRelationInfo> relations = new ArrayList<OrgOrgRelationInfo>();
        Data orgOrgRelationMap = null;
        try{
            relations = orgService.getOrgOrgRelationsByOrg(orgId);
            orgOrgRelationMap = buildOrgOrgRelationDataMap(relations);
        }
        catch(DoesNotExistException dnee){
            return null;
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return orgOrgRelationMap;
    }
    
    private Data buildOrgOrgRelationDataMap(List<OrgOrgRelationInfo> relations){
        Data orgOrgRelations = new Data();
        int count = 1;
        for(OrgOrgRelationInfo relation:relations){
            Data relationMap = new Data();
            OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap(relationMap);
            orgOrgRelation.setId(relation.getId());
            orgOrgRelation.setOrgId(relation.getOrgId());
            orgOrgRelation.setRelatedOrgId(relation.getRelatedOrgId());
            orgOrgRelation.setOrgOrgRelationTypeKey(relation.getType());
            orgOrgRelation.setEffectiveDate(relation.getEffectiveDate());
            orgOrgRelation.setExpirationDate(relation.getExpirationDate());
            
            orgOrgRelations.set(count, orgOrgRelation.getData());
            addVersionIndicator(orgOrgRelation.getData(),OrgOrgRelationInfo.class.getName(),relation.getId(),relation.getMetaInfo().getVersionInd());
            count= count+1;
        }
        
        return orgOrgRelations;
    }

}
