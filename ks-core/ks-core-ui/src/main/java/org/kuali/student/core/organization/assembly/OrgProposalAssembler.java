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
import org.kuali.student.common.conversion.util.R1R2ConverterUtil;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.core.organization.assembly.data.server.OrgInfoData;
import org.kuali.student.core.organization.assembly.data.server.OrgInfoData.ModificationState;
import org.kuali.student.core.organization.assembly.data.server.org.OrgHelper;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r1.common.assembly.old.BaseAssembler;
import org.kuali.student.r1.common.assembly.old.data.SaveResult;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.MetaInfo;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.setCreated;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.isDeleted;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.r1.common.assembly.util.AssemblerUtils.setUpdated;

@Transactional(readOnly=true,rollbackFor={Throwable.class})
@Deprecated
public class OrgProposalAssembler extends BaseAssembler<Data, OrgHelper> {
	final Logger LOG = Logger.getLogger(OrgProposalAssembler.class);
    private OrganizationService orgService;
    public static  String PROPOSAL_TYPE_CREATE_ORG = "kuali.proposal.type.org.create";
    public static  String ORG_PROPOSAL_DATA_TYPE = "OrgProposal";
    public static final String ORG_INFO_PATH                  = "orgInfo";
    public static final String QUALIFICATION_ORG_ID                 = "orgId";
    public static final String PERM_DTO_NAME                 = "Organization";
    private Metadata metadata;
    private DataModel orgProposalModel = new DataModel();
    public OrgProposalAssembler(){

    }
//    public OrgProposalAssembler(OrganizationService orgService,MetadataServiceImpl metadataService){
//        this.orgService=orgService;
//        this.metadataService=metadataService;
//    }
    
    private void setMetaInfodata(String orgId){
        try {
            this.metadata=getMetadata(QUALIFICATION_ORG_ID, orgId, null, null);
        } catch (AssemblyException e) {
            LOG.error(e);
        }
    }
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
        if(metadata==null){
            setMetaInfodata(id);
        }
        OrgInfo orgInfo = new OrgInfo();
        List<OrgPositionRestrictionInfo> positions = new ArrayList<OrgPositionRestrictionInfo>();
        List<OrgOrgRelationInfo> relations = new ArrayList<OrgOrgRelationInfo>();
        Data result = new Data();
//      SaveResult<Data> result = new SaveResult<Data>();
        try{
            orgInfo = orgService.getOrg(id, ContextUtils.getContextInfo());
            OrgInfoData orgInfoData = new OrgInfoData();
            orgInfoData.setOrgInfo(orgInfo);
            OrgHelper resultOrg = buildOrgDataMap(orgInfoData);
            OrgOrgRelationAssembler orgOrgRelationAssembler = new OrgOrgRelationAssembler();
            orgOrgRelationAssembler.setMetaData(metadata);
            orgOrgRelationAssembler.setOrgService(orgService);
            OrgPositionRestrictionAssembler orgPositionRestrictionAssembler= new OrgPositionRestrictionAssembler();
            orgPositionRestrictionAssembler.setOrgService(orgService);
            orgPositionRestrictionAssembler.setMetaData(metadata);
            OrgPersonRelationAssembler orgPersonRelationAssembler = new OrgPersonRelationAssembler();
            orgPersonRelationAssembler.setMetaData(metadata);
            orgPersonRelationAssembler.setOrgService(orgService);
            Data orgOrgRelationMap = orgOrgRelationAssembler.get(id);
            Data orgPositionMap = orgPositionRestrictionAssembler.get(id);
            Data orgRelationMap = orgPersonRelationAssembler.get(id);
            result.set("orgInfo", resultOrg.getData());
            result.set("orgOrgRelationInfo", orgOrgRelationMap);
            result.set("OrgPositionRestrictionInfo", orgPositionMap);
            result.set("orgPersonRelationInfo", orgRelationMap);
            
        }
        catch(Exception e){
        	LOG.error(e);
        }
        
        return result;
    }


    @Override
	@Transactional(readOnly=false,noRollbackFor={DoesNotExistException.class},rollbackFor={Throwable.class})
    public SaveResult<Data> save(Data input) throws AssemblyException {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        OrgHelper orgHelper = OrgHelper.wrap((Data)input.get("orgInfo"));
        if(metadata==null){
            setMetaInfodata(orgHelper.getId());
        }
        OrgInfoData orgInfoData = buildOrgInfo(orgHelper);
        SaveResult<Data> result = new SaveResult<Data>();
        List<ValidationResultInfo> validationResults = validate(input);
//        result.setValidationResults(validationResults);
        try {
            saveOrg(orgInfoData, ContextUtils.getContextInfo());   //orgInfoData contains the dto for OrgInfo
//            OrgHelper resultOrg = buildOrgDataMap(orgInfoData);   //this will create the Data Map for the returned OrgInfo dto
//            Data samp = new Data();
//            Data resultData = new Data();
//            resultData.set("orgInfo", resultOrg.getData());       //Set the map to the key "orgInfo"
            String orgId = orgInfoData.getOrgInfo().getId();
            addVersionIndicator(orgHelper.getData(),OrgInfo.class.getName(),orgId,orgInfoData.getOrgInfo().getMeta().getVersionInd());
            
            orgHelper.setId(orgId);
            if(orgId!=null&&input.get("orgOrgRelationInfo")!=null){
//                OrgorgRelationHelper orgOrgRelation=  OrgorgRelationHelper.wrap(input);
                OrgOrgRelationAssembler orgOrgRelationAssembler = new OrgOrgRelationAssembler();
                orgOrgRelationAssembler.setMetaData(metadata);
                orgOrgRelationAssembler.setOrgService(orgService);
                Data relationData = orgOrgRelationAssembler.save(input).getValue();

            }
            if(orgId!=null&&input.get("OrgPositionRestrictionInfo")!=null){
                OrgPositionRestrictionAssembler orgPositionRestrictionAssembler= new OrgPositionRestrictionAssembler();
                orgPositionRestrictionAssembler.setMetaData(metadata);
                orgPositionRestrictionAssembler.setOrgService(orgService);
                Data positionData = orgPositionRestrictionAssembler.save(input).getValue();
            }
            if(orgId!=null&&input.get("orgPersonRelationInfo")!=null){
                OrgPersonRelationAssembler orgPersonRelationAssembler= new OrgPersonRelationAssembler();
                orgPersonRelationAssembler.setMetaData(metadata);
                orgPersonRelationAssembler.setOrgService(orgService);
                Data relationData = orgPersonRelationAssembler.save(input).getValue();
            }
            
            result.setValue((orgId == null) ? null : get(orgId));
            
        } catch (Exception e) {
            LOG.error(e);
            throw(new AssemblyException());
        }
        
        return result;
    }
    
    public void setOrgService(OrganizationService service){
        orgService = service;
    }
    
    
    private OrgInfoData buildOrgInfo(OrgHelper org){
        OrgInfo orgInfo = new OrgInfo();
        OrgInfoData result = new OrgInfoData();
                
        orgInfo.setTypeKey(org.getType());
        RichTextInfo richText = new RichTextInfo();
        richText.setFormatted(org.getDescription());
        richText.setPlain(org.getDescription());
        orgInfo.setLongDescr(richText);
        orgInfo.setShortName(org.getAbbreviation());
        orgInfo.setLongName(org.getName());
        orgInfo.setEffectiveDate(org.getEffectiveDate());
        orgInfo.setExpirationDate(org.getExpirationDate());
        if(org.getId()!=null){
            orgInfo.setId(org.getId());
        }
        
        if (isModified(org.getData())) {
//           if (isUpdated(org.getData())) {
                MetaInfo metaInfo = new MetaInfo();
                orgInfo.setMeta(metaInfo);
                result.setModificationState(ModificationState.UPDATED);
//            } else if (isDeleted(org.getData())) {
//                result.setModificationState(ModificationState.DELETED);
//            }
        }
        else{
            setCreated(org.getData(), true);
            result.setModificationState(ModificationState.CREATED);
        }
        if(orgInfo.getMeta()!=null){
            orgInfo.getMeta().setVersionInd(getVersionIndicator(org.getData()));
        }
//        result.setModificationState(ModificationState.CREATED);
        result.setOrgInfo(orgInfo);
        return result;
    }

    private void saveOrg(OrgInfoData input, ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException, ReadOnlyException {
        OrgInfo result = null;
        OrgInfo orgInfo = input.getOrgInfo();
        DataModelDefinition def = new DataModelDefinition(metadata);
        orgProposalModel.setDefinition(def);
        QueryPath metaPath = QueryPath.concat(null, ORG_INFO_PATH);
        Metadata orgProposalMeta =orgProposalModel.getMetadata(metaPath);
        if (input.getModificationState() != null) {
            if (orgProposalMeta.isCanEdit()) {
                OrgInfo orgInfoR2  = orgInfo;
                switch (input.getModificationState()) {
                    case CREATED:
                        result = orgService.createOrg(orgInfo.getTypeKey(), orgInfoR2, context);
                        break;
                    case UPDATED:
                        result = orgService.updateOrg(orgInfo.getId(), orgInfoR2, context);
                    default:
                }
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
        org.setType(orgInfoData.getOrgInfo().getTypeKey());
        org.setName(orgInfoData.getOrgInfo().getLongName());
        org.setAbbreviation(orgInfoData.getOrgInfo().getShortName());
        org.setDescription(orgInfoData.getOrgInfo().getLongDescr().getPlain());
        org.setEffectiveDate(orgInfoData.getOrgInfo().getEffectiveDate());
        org.setExpirationDate(orgInfoData.getOrgInfo().getExpirationDate());
        setUpdated(org.getData(), true);
        addVersionIndicator(org.getData(),OrgInfo.class.getName(),orgInfoData.getOrgInfo().getId(),orgInfoData.getOrgInfo().getMeta().getVersionInd());

        return org;
    }
    
	@Override
    protected String getDataType() {
        return ORG_PROPOSAL_DATA_TYPE;
    }

    @Override
    protected String getDocumentPropertyName() {
        return "course";
    }

    @Override
    protected String getDtoName() {
        return PERM_DTO_NAME;
    }

    @Override
    protected Map<String,String> getQualification(String idType, String id) {
        Map<String,String> qualification = null;
        if(id!=null&&!id.isEmpty()){
         qualification = new LinkedHashMap<String,String>();
        /*String DOCUMENT_TYPE_NAME = "documentTypeName";
        //FIXME: should this be something like org.proposal?
        qualification.put(DOCUMENT_TYPE_NAME, "Organization");*/
        qualification.put(idType, id);
        }
        
        return qualification;
    }
   
}
