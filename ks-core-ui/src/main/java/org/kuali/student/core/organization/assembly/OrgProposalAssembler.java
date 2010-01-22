package org.kuali.student.core.organization.assembly;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.organization.assembly.data.client.OrgMetadata;
import org.kuali.student.core.organization.assembly.data.client.OrgProposalMetadata;
import org.kuali.student.core.organization.assembly.data.client.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.client.org.OrgorgRelationHelper;
import org.kuali.student.core.organization.assembly.data.server.OrgInfoData;
import org.kuali.student.core.organization.assembly.data.server.OrgInfoData.ModificationState;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.newdto.SearchRequest;
import org.kuali.student.core.search.newdto.SearchResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgProposalAssembler implements Assembler<Data, OrgHelper>{
    
    private OrganizationService orgService;
    private MetadataServiceImpl metadataService;
    public static final String PROPOSAL_TYPE_CREATE_ORG = "kuali.proposal.type.org.create";
    public static final String ORG_PROPOSAL_DATA_TYPE = "OrgProposal";

    public OrgProposalAssembler(){

    }
//    public OrgProposalAssembler(OrganizationService orgService,MetadataServiceImpl metadataService){
//        this.orgService=orgService;
//        this.metadataService=metadataService;
//    }
    
    @Override
    public Data assemble(OrgHelper input) throws AssemblyException {
        if (input == null) {
            return null;
        }
        Data result = new Data();
        
        return result;
    }

    @Override
    public OrgHelper disassemble(Data input) throws AssemblyException {
        if (input == null) {
            return null;
        }
//        OrgHelper result = new OrgHelper();
//        copyIfExists(input,result,"orgInfo");
        return null;
    }

    @Override
    public Data get(String id) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Metadata getMetadata(String type, String state) throws AssemblyException {
        // 
//        Metadata metadata = new OrgProposalMetadata().getMetadata("", state);
        Metadata metadata = null;
        try{

        
        Unmarshaller um;
        JAXBContext context;
        context = JAXBContext.newInstance(Metadata.class);
        um = context.createUnmarshaller();
        InputStream is = OrgProposalAssembler.class.getResourceAsStream( "/org-metadata.xml" ); 
//        metadata = (Metadata) um.unmarshal(is);
        metadata = metadataService.getMetadata(ORG_PROPOSAL_DATA_TYPE,PROPOSAL_TYPE_CREATE_ORG,state);

        }
        catch(Exception e ){
            e.printStackTrace();
        }
        return metadata;
    }

    @Override
    public SaveResult<Data> save(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        OrgHelper orgHelper = OrgHelper.wrap((Data)input.get("orgInfo"));
        OrgInfoData orgInfoData = buildOrgInfo(orgHelper);
        SaveResult<Data> result = new SaveResult<Data>();
        List<ValidationResultInfo> validationResults = validate(input);
//        result.setValidationResults(validationResults);
        try {
            saveOrg(orgInfoData);   //orgInfoData contains the dto for OrgInfo
            OrgHelper resultOrg = buildOrgDataMap(orgInfoData);   //this will create the Data Map for the returned OrgInfo dto
            Data samp = new Data();
            Data resultData = new Data();
            resultData.set("orgInfo", resultOrg.getData());       //Set the map to the key "orgInfo"
            
            String orgId = resultOrg.getId();
            orgHelper.setId(orgId);
            if(orgId!=null&&input.get("orgOrgRelationInfo")!=null){
//                OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap(input);
                OrgOrgRelationAssembler orgOrgRelationAssembler = new OrgOrgRelationAssembler();
                orgOrgRelationAssembler.setOrgService(orgService);
                Data relationData = orgOrgRelationAssembler.save(input).getValue();
                System.out.println("-- -- --");
//                resultData.set("", value)

            }
            if(orgId!=null&&input.get("OrgPositionRestrictionInfo")!=null){
                OrgPositionRestrictionAssembler orgPositionRestrictionAssembler= new OrgPositionRestrictionAssembler();
                orgPositionRestrictionAssembler.setOrgService(orgService);
                orgPositionRestrictionAssembler.save(input);
                Data relationData = orgPositionRestrictionAssembler.save(input).getValue();
                System.out.println("-- -- --");
            }
           
            result.setValue(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public void setOrgService(OrganizationService service){
        orgService = service;
    }
    
    public void setMetadataService(MetadataServiceImpl metadataService) {
        this.metadataService = metadataService;
    }
    
    private OrgInfoData buildOrgInfo(OrgHelper org){
        OrgInfo orgInfo = new OrgInfo();
        OrgInfoData result = new OrgInfoData();
                
        orgInfo.setType(org.getType());
        orgInfo.setLongDesc(org.getDescription());
        orgInfo.setShortName(org.getAbbreviation());
        orgInfo.setLongName(org.getName());
        orgInfo.setEffectiveDate(org.getEffectiveDate());
        orgInfo.setExpirationDate(org.getExpirationDate());
        
        result.setModificationState(ModificationState.CREATED);
        result.setOrgInfo(orgInfo);
        return result;
    }

    private void saveOrg(OrgInfoData input) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        OrgInfo result = null;
        OrgInfo orgInfo = input.getOrgInfo();
        if (input.getModificationState() != null) {
            switch (input.getModificationState()) {
                case CREATED:
                    result = orgService.createOrganization(orgInfo.getType(), orgInfo);
                    break;
                default:
            }
        }
        if (result != null) {
            input.setOrgInfo(result);
        }
    }

    @Override
    public List<ValidationResultInfo> validate(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    private <T extends Data> T copyIfExists(Data source, T target, String key) {
        Data d = source.get(key);
        if (d == null) {
            return null;
        } else {
            d.copy(target, false);
            source.set(key, target);
            return target;
        }
    }
    
    private OrgHelper buildOrgDataMap(OrgInfoData orgInfoData){
        OrgHelper org =  OrgHelper.wrap(new Data());
        org.setId(orgInfoData.getOrgInfo().getId());
        org.setType(orgInfoData.getOrgInfo().getType());
        org.setName(orgInfoData.getOrgInfo().getLongName());
        org.setAbbreviation(orgInfoData.getOrgInfo().getShortName());
        org.setDescription(orgInfoData.getOrgInfo().getLongDesc());
        org.setEffectiveDate(orgInfoData.getOrgInfo().getEffectiveDate());
        org.setExpirationDate(orgInfoData.getOrgInfo().getExpirationDate());

        return org;
    }

	@Override
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}
   
}
