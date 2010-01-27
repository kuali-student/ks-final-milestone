package org.kuali.student.core.organization.assembly;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.kuali.student.core.assembly.util.AssemblerUtils.isCreated;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.assembly.data.client.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.client.org.OrgorgRelationHelper;
import org.kuali.student.core.organization.assembly.data.server.OrgOrgRelationInfoData;
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
    
    
//    private OrgOrgRelationInfoData buildOrgOrgRelationInfo(OrgorgRelationHelper orgorgRelationHelper){
//        OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();
//        OrgOrgRelationInfoData result = new OrgOrgRelationInfoData();
//        
//        orgOrgRelationInfo.setOrgId(orgorgRelationHelper.getOrgId());
//        orgOrgRelationInfo.setRelatedOrgId(orgorgRelationHelper.getRelatedOrgId());
//        orgOrgRelationInfo.setType(orgorgRelationHelper.getOrgOrgRelationTypeKey());
//        orgOrgRelationInfo.setEffectiveDate(orgorgRelationHelper.getEffectiveDate());
//        orgOrgRelationInfo.setExpirationDate(orgorgRelationHelper.getExpirationDate());
//        
//        result.setOrgOrgRelationInfo(orgOrgRelationInfo);
//        return result;
//    }
    
    private void addOrgOrgRelations(Data input){
        if (input == null) {
            return;
        }
        for (Property p : (Data)input.get("orgOrgRelationInfo")) {
            OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap((Data)p.getValue());
            if(isCreated(orgOrgRelation.getData())){
                //Set the OrgId from the OrgInfo data Map which is set on saving the Org
                orgOrgRelation.setOrgId((OrgHelper.wrap((Data)input.get("orgInfo")).getId()));
                
                
                OrgOrgRelationInfo orgOrgRelationInfo = new OrgOrgRelationInfo();
                orgOrgRelationInfo.setOrgId(orgOrgRelation.getOrgId());
                orgOrgRelationInfo.setRelatedOrgId(orgOrgRelation.getRelatedOrgId());
                orgOrgRelationInfo.setType(orgOrgRelation.getOrgOrgRelationTypeKey());
                orgOrgRelationInfo.setEffectiveDate(orgOrgRelation.getEffectiveDate());
                orgOrgRelationInfo.setExpirationDate(orgOrgRelation.getExpirationDate());
                try{
                    OrgOrgRelationInfo  result = orgService.createOrgOrgRelation(orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(), orgOrgRelationInfo.getType(), orgOrgRelationInfo);
                    orgOrgRelation.setId(result.getId());
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
            count= count+1;
        }
        
        return orgOrgRelations;
    }

}
