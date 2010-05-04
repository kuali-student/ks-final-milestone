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

package org.kuali.student.core.organization.ui.server.gwt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.kuali.rice.kim.bo.entity.dto.KimEntityNamePrincipalNameInfo;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.common.ui.server.gwt.BaseRpcGwtServletAbstract;
import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.dictionary.MetadataServiceImpl;
import org.kuali.student.core.dictionary.dto.ObjectStructure;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;

import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.assembly.OrgProposalAssembler;
import org.kuali.student.core.organization.assembly.data.client.org.OrgSearchHelper;
import org.kuali.student.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.core.organization.dto.OrgPersonRelationTypeInfo;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.dto.OrgTreeInfo;
import org.kuali.student.core.organization.dto.OrgTypeInfo;
import org.kuali.student.core.organization.dynamic.Field;
import org.kuali.student.core.organization.dynamic.Fields;
import org.kuali.student.core.organization.dynamic.MultipleField;
import org.kuali.student.core.organization.dynamic.Section;
import org.kuali.student.core.organization.dynamic.SectionConfig;
import org.kuali.student.core.organization.dynamic.SectionView;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.organization.ui.client.mvc.model.FieldInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.FieldInfoImpl;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.MultipleFieldInfoImpl;
import org.kuali.student.core.organization.ui.client.mvc.model.OrgPositionPersonRelationInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionConfigInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionViewInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.core.search.dto.SearchCriteriaTypeInfo;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultTypeInfo;
import org.kuali.student.core.search.dto.SearchTypeInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgRpcGwtServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements OrgRpcService{

	private static final long serialVersionUID = 1L;
	public static final String CONFIGURE_XML_PATH = "C:/org_configure.xml";
	private IdentityService identityService;
	private OrganizationService service;

	public void setIdentityService(IdentityService identityService){
	    this.identityService=identityService;
	}
	
	public void setService(OrganizationService service){
	    this.service=service;
	}
	
    @Override
    public StatusInfo removePositionRestrictionFromOrg(String orgId, String orgPersonRelationTypeKey){
        try {
            return service.removePositionRestrictionFromOrg(orgId, orgPersonRelationTypeKey);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public StatusInfo removeOrgOrgRelation(String orgOrgRelationId){
        try {
            return service.removeOrgOrgRelation(orgOrgRelationId);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<OrgHierarchyInfo> getOrgHierarchies() {
        try {
            return service.getOrgHierarchies();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;    
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId) {
        // TODO Auto-generated method stub
        try {
            return service.getOrgOrgRelationsByOrg(orgId);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(String orgId) {
        
        try {
            return service.getOrgOrgRelationsByRelatedOrg(orgId);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList) {
        // TODO Auto-generated method stub
        try {
            return service.getOrganizationsByIdList(orgIdList);
        } catch (DoesNotExistException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getAllDescendants(String orgId, String orgHierarchy) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.getAllDescendants(orgId, orgHierarchy);
        } catch (InvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MissingParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationFailedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgPositionRestrictionInfo addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.addPositionRestrictionToOrg(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgInfo createOrganization(OrgInfo orgInfo) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.createOrganization(orgInfo.getType(), orgInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgOrgRelationInfo createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        // TODO Will Gomes - THIS METHOD NEEDS JAVADOCS
        try {
            return service.createOrgOrgRelation(orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(),
                    orgOrgRelationInfo.getType(), orgOrgRelationInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes() {
        try {
            return service.getOrgPersonRelationTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } 
        return null;
    }

    public List<OrgTypeInfo> getOrgTypes() {
        try {
            return service.getOrgTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes() {
        try {
            return service.getOrgOrgRelationTypes();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public OrgOrgRelationTypeInfo getOrgOrgRelationType(String orgOrgRelationTypeKey) {
        try {
            return service.getOrgOrgRelationType(orgOrgRelationTypeKey);
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<OrgTreeInfo> getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels) {
        try {
            return service.getOrgTree(orgId, orgHierarchy, maxLevels);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrgInfo getOrganization(String orgId) {
        try {
            return service.getOrganization(orgId);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId) {
        try {
            return service.getPositionRestrictionsByOrg(orgId);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public OrgInfo updateOrganization(OrgInfo orgInfo) {
        try {
            return service.updateOrganization(orgInfo.getId(), orgInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        } 
        return null;
    }


    public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        try {
            return service.updatePositionRestrictionForOrg(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        } 
        return null;
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        try {
            return service.updateOrgOrgRelation(orgOrgRelationInfo.getId(), orgOrgRelationInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId,
            String personId, String orgPersonRelationTypeKey,
            OrgPersonRelationInfo orgPersonRelationInfo) {

        try {
            return service.createOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey, orgPersonRelationInfo);
        } catch (AlreadyExistsException e) {
            e.printStackTrace();
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(
            String orgTypeKey) {
        try {
            return service.getOrgPersonRelationTypesForOrgType(orgTypeKey);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId) {
        try {
            return service.getOrgPersonRelationsByOrg(orgId);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public StatusInfo removeOrgPersonRelation(String orgPersonRelationId) {
        try {
            return service.removeOrgPersonRelation(orgPersonRelationId);
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public OrgPersonRelationInfo updateOrgPersonRelation(
            String orgPersonRelationId,
            OrgPersonRelationInfo orgPersonRelationInfo) {
        try {
            return service.updateOrgPersonRelation(orgPersonRelationId, orgPersonRelationInfo);
        } catch (DataValidationErrorException e) {
            e.printStackTrace();
        } catch (DoesNotExistException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        } catch (MissingParameterException e) {
            e.printStackTrace();
        } catch (OperationFailedException e) {
            e.printStackTrace();
        } catch (PermissionDeniedException e) {
            e.printStackTrace();
        } catch (VersionMismatchException e) {
            e.printStackTrace();
        }
        return null;
    }	
//    private Assembler assembler;
    
//    public void setOrgProposalAssembler(Assembler assembler){
//            assembler=assembler;
//    }
    
    private synchronized void initAssemblers() {
//        if (assembler == null) {
//            assembler = new OrgProposalAssembler();
//        }       
        
    }
    
// TODO rewrite this method to use the metadata structures from the assembler
//    /**
//     * This method should return a an empty model with associated model definition for 
//     * requested model
//     */
//    public DataModel getOrgProposalModelDefinition(String modelId){
//        DataModel model = null;
//        if (OrgConfigurerFactory.ORG_PROPOSAL_MODEL.equals(modelId)){
//            final SimpleModelDefinition def = new SimpleModelDefinition();
//            def.define("orgInfo", "org.kuali.student.core.organization.assembly.data.client.org.Org");
//            def.define("orgOrgRelInfo", "org.kuali.student.core.organization.assembly.data.client.org.OrgorgRelation");
//            model = new DataModel(def, new Data());
//            
//        }
//        return model;
//    }
    
    @Override
    public DataSaveResult saveOrgProposal(Data proposal) throws AssemblyException, org.kuali.student.common.ui.client.service.exceptions.OperationFailedException {

        try {
            initAssemblers();
            DataSaveResult s = this.saveData(proposal);
            if (s == null) {
                return null;
            } else {
//                DataSaveResult dsr = new DataSaveResult(s.getValidationResults(), s.getValue());
                Data sampdata = new Data();
                sampdata = s.getValue();
                List<ValidationResultInfo> vsr =  new ArrayList<ValidationResultInfo>();
                DataSaveResult dsr = new DataSaveResult(vsr,sampdata);
                return dsr;
            }
        } catch(Exception e){
            throw new org.kuali.student.common.ui.client.service.exceptions.OperationFailedException("Unable to save");
            
        }
     
    }

    @Override
    public Metadata getOrgMetaData() throws org.kuali.student.common.ui.client.service.exceptions.OperationFailedException {
        try {
            initAssemblers();
            //FIXME: where to get the ID?
//            return this.getMetadata("orgProposal", null);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new org.kuali.student.common.ui.client.service.exceptions.OperationFailedException("Unable to retrieve metadata for org");
        }
        return null;
    }


    @Override
    public SectionConfigInfo getSectionConfig() throws org.kuali.student.common.ui.client.service.exceptions.OperationFailedException {
        // Move this into Common UI
        SectionConfigInfo sectionConfigInfo = new SectionConfigInfo();
        String packageName = SectionConfig.class.getPackage().getName();
        JAXBContext jc;
        try {
            jc = JAXBContext.newInstance( packageName );
            Unmarshaller u = jc.createUnmarshaller();
            SectionConfig sectionConfig = null;
            try{
                sectionConfig = (SectionConfig)((JAXBElement)u.unmarshal(
                    new FileInputStream( CONFIGURE_XML_PATH ))).getValue();
            }
            catch(FileNotFoundException e){
                InputStream in = OrgRpcGwtServlet.class.getResourceAsStream("/org_configure.xml");
                sectionConfig = (SectionConfig)((JAXBElement)u.unmarshal(in)).getValue();
            }
            List<SectionView> sectionViews = sectionConfig.getSectionView();
            List<SectionViewInfo> sectionViewInfoList = new ArrayList<SectionViewInfo>();
            for(SectionView sectionView:sectionViews){
                SectionViewInfo sectionViewInfo = new SectionViewInfo();
                sectionViewInfo.setViewName(sectionView.getViewName());
                sectionViewInfo.setTab(sectionView.getTab());
                sectionViewInfo.setMenu(sectionView.getMenu());
                Section section = sectionView.getSection();
                sectionViewInfo.setSectionName(section.getSectionName());
                sectionViewInfo.setSectionEnum(section.getEnum());
                Fields fields = sectionView.getFields();
                if (fields != null) {
                    List<Field> fieldList = fields.getField();

                    List<MultipleField> multipleFieldList = fields.getMultipleField();
                    List<FieldInfo> fieldInfoList = new ArrayList<FieldInfo>();
                    for (Field field : fieldList) {
                        FieldInfo fieldInfo = new FieldInfoImpl();
                        fieldInfo.setLabel(field.getLabel());
                        fieldInfo.setKey(field.getKey());
                        fieldInfo.setWidget(field.getWidget());
                        fieldInfoList.add(fieldInfo);
                    }
                    for (MultipleField multipleField : multipleFieldList) {
                        MultipleFieldInfoImpl fieldInfo = new MultipleFieldInfoImpl();
                        fieldInfo.setKey(multipleField.getKey());
                        fieldInfo.setLabel(multipleField.getItemLabel());
                        fieldInfo.setAddItemLabel(multipleField.getAddItemLabel());
                        List<Field> fieldMultiList = multipleField.getFields().getField();
                        List<FieldInfo> fieldMultiInfoList = new ArrayList<FieldInfo>();
                        for (Field field : fieldMultiList) {
                            FieldInfo fieldMultiInfo = new FieldInfoImpl();
                            fieldMultiInfo.setLabel(field.getLabel());
                            fieldMultiInfo.setKey(field.getKey());
                            fieldMultiInfo.setWidget(field.getWidget());
                            fieldMultiInfoList.add(fieldMultiInfo);
                        }
                        fieldInfo.setFields(fieldMultiInfoList);
                        fieldInfoList.add(fieldInfo);
                    }

                    sectionViewInfo.setfields(fieldInfoList);
                }
                sectionViewInfoList.add(sectionViewInfo);
                
            }
           sectionConfigInfo.setSectionViewInfoList(sectionViewInfoList);
            
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new org.kuali.student.common.ui.client.service.exceptions.OperationFailedException("Org Screen XML Cnfig file: recources/org_configure.xml parse exception");
        } 

        return sectionConfigInfo;
    }

    @Override
    public Data fetchOrg(String orgId) {
        try {
            initAssemblers();
            //OrgSearchHelper orgSearchHelper = OrgSearchHelper.wrap((Data)orgSearch.get("orgSearchInfo"));
            //String orgId = orgSearchHelper.getOrgId();
            return (Data)this.getData(orgId);
//            return orgProposalAssembler.getMetadata(null,"draft");
        }
        catch(Exception e){
            e.printStackTrace();
//            throw new OperationFailedException("Unable to retrieve metadata for org");
        }
        return null;

    }

    @Override
    public List<OrgPositionPersonRelationInfo> getOrgPositionPersonRelation(String orgId) {
        ArrayList<OrgPositionPersonRelationInfo> relations = new ArrayList<OrgPositionPersonRelationInfo>();
        OrgPositionPersonRelationInfo personRelationInfo = null;
        try{
            List<OrgPersonRelationInfo> orgPersonRelations = service.getAllOrgPersonRelationsByOrg(orgId);
            List<OrgPositionRestrictionInfo> orgPositionRestrictions = service.getPositionRestrictionsByOrg(orgId);
            
            for(OrgPositionRestrictionInfo position:orgPositionRestrictions){
                personRelationInfo = new OrgPositionPersonRelationInfo();
                personRelationInfo.setOrgPersonRelationTypeKey(position.getOrgPersonRelationTypeKey());
                personRelationInfo.setTitle(position.getTitle());
                personRelationInfo.setDesc(position.getDesc());
                personRelationInfo.setMinNumRelations(position.getMinNumRelations().toString());
                personRelationInfo.setMaxNumRelations(position.getMaxNumRelations());
                ArrayList<String> names = new ArrayList<String>();
                for(OrgPersonRelationInfo relation: orgPersonRelations){
                    if(position.getOrgPersonRelationTypeKey().equals(relation.getType())){
                        names.add(relation.getPersonId());
                    }
                }
                personRelationInfo.setPersonId(names);
                relations.add(personRelationInfo);
            }
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return relations;
    }


    @Override
    public Map<String, MembershipInfo> getNamesForPersonIds(List<String> personIds) {
        Map<String, KimEntityNamePrincipalNameInfo> kimIdentities = identityService.getDefaultNamesForPrincipalIds(personIds);
        Map<String, MembershipInfo> identities = new HashMap<String, MembershipInfo>();
        for(String pId:personIds ){
            KimEntityNamePrincipalNameInfo kimEntity = kimIdentities.get(pId);
            MembershipInfo memeberEntity = new MembershipInfo();
            memeberEntity.setFirstName(kimEntity.getDefaultEntityName().getFirstName());
            memeberEntity.setLastName(kimEntity.getDefaultEntityName().getLastName());
            identities.put(pId, memeberEntity);
        }
        
        return identities;
    }

    @Override
    protected String deriveAppIdFromData(Data data) {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    protected String deriveDocContentFromData(Data data) {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    protected String getDefaultMetaDataType() {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    protected String getDefaultWorkflowDocumentType() {
        // TODO Neerav Agrawal - THIS METHOD NEEDS JAVADOCS
        return null;
    }




    
}
