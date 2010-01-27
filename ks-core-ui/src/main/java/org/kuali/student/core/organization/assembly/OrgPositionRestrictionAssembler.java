package org.kuali.student.core.organization.assembly;

import static org.kuali.student.core.assembly.util.AssemblerUtils.isCreated;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.assembly.data.client.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.client.org.OrgPositionHelper;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgPositionRestrictionAssembler implements Assembler<Data, OrgPositionHelper>{
    private OrganizationService orgService;
    @Override
    public Data assemble(OrgPositionHelper input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public OrgPositionHelper disassemble(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
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
        addPositionRestriction(input);
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
    
    private void addPositionRestriction(Data input){
        if (input == null) {
            return;
        }
        for (Property p : (Data)input.get("OrgPositionRestrictionInfo")) {
            OrgPositionHelper orgPositionHelper=  OrgPositionHelper.wrap((Data)p.getValue());
            if(isCreated(orgPositionHelper.getData())){
                orgPositionHelper.setOrgId((OrgHelper.wrap((Data)input.get("orgInfo")).getId()));
                OrgPositionRestrictionInfo orgPositionRestrictionInfo = new OrgPositionRestrictionInfo();
                orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPositionHelper.getPersonRelationType());
                orgPositionRestrictionInfo.setTitle(orgPositionHelper.getTitle());
                orgPositionRestrictionInfo.setDesc(orgPositionHelper.getDesc());
                orgPositionRestrictionInfo.setMinNumRelations(orgPositionHelper.getMinNumRelations());
                orgPositionRestrictionInfo.setMaxNumRelations(orgPositionHelper.getMaxNumRelations());
                try{
                    OrgPositionRestrictionInfo  result = orgService.addPositionRestrictionToOrg(orgPositionHelper.getOrgId(), orgPositionHelper.getPersonRelationType(), orgPositionRestrictionInfo);
                    orgPositionHelper.setId(result.getId());
                }
                catch(Exception e ){
                    e.printStackTrace();
                }
            }
        }
        
    }
    
    public Data fetchOrgPositions(String orgId){
        List<OrgPositionRestrictionInfo> positions = new ArrayList<OrgPositionRestrictionInfo>();
        Data orgPositionMap = null;
        try{
            positions = orgService.getPositionRestrictionsByOrg(orgId);
            orgPositionMap = buildOrgPositionMap(positions);
        }
        catch(DoesNotExistException dnee){
            return null;
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return orgPositionMap;
    }
    
    private Data buildOrgPositionMap( List<OrgPositionRestrictionInfo> positions){
        Data orgPositions = new Data();
        int count =1;
        for(OrgPositionRestrictionInfo position:positions){
            Data positionMap = new Data();
            OrgPositionHelper orgPositionHelper = OrgPositionHelper.wrap(positionMap);
            orgPositionHelper.setId(position.getId());
            orgPositionHelper.setOrgId(position.getOrgId());
            orgPositionHelper.setPersonRelationType(position.getOrgPersonRelationTypeKey());
            orgPositionHelper.setTitle(position.getTitle());
            orgPositionHelper.setDesc(position.getTitle());
            orgPositionHelper.setMinNumRelations(position.getMinNumRelations());
            orgPositionHelper.setMaxNumRelations(position.getMaxNumRelations());
            
            orgPositions.set(count,orgPositionHelper.getData());
            count = count +1;
        }
        return orgPositions;
    }

}
