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

import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.entity.dto.KimEntityNamePrincipalNameInfo;
import org.kuali.rice.kim.service.IdentityService;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.dto.StatusInfo;
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
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgRpcGwtServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements OrgRpcService{
	final Logger LOG = Logger.getLogger(OrgRpcGwtServlet.class);
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
        } catch (Exception e) {
			LOG.error(e);
		}
        return null;
    }
    
    @Override
    public StatusInfo removeOrgOrgRelation(String orgOrgRelationId){
        try {
            return service.removeOrgOrgRelation(orgOrgRelationId);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }
    
    @Override
    public List<OrgHierarchyInfo> getOrgHierarchies() {
        try {
            return service.getOrgHierarchies();
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;    
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId) {
        try {
            return service.getOrgOrgRelationsByOrg(orgId);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }
    
    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(String orgId) {
        
        try {
            return service.getOrgOrgRelationsByRelatedOrg(orgId);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList) {
        try {
            return service.getOrganizationsByIdList(orgIdList);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<String> getAllDescendants(String orgId, String orgHierarchy) {
        try {
            return service.getAllDescendants(orgId, orgHierarchy);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgPositionRestrictionInfo addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        try {
            return service.addPositionRestrictionToOrg(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgInfo createOrganization(OrgInfo orgInfo) {
        try {
            return service.createOrganization(orgInfo.getType(), orgInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgOrgRelationInfo createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        try {
            return service.createOrgOrgRelation(orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(),
                    orgOrgRelationInfo.getType(), orgOrgRelationInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypes() {
        try {
            return service.getOrgPersonRelationTypes();
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    public List<OrgTypeInfo> getOrgTypes() {
        try {
            return service.getOrgTypes();
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public List<OrgOrgRelationTypeInfo> getOrgOrgRelationTypes() {
        try {
            return service.getOrgOrgRelationTypes();
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }
    
    @Override
    public OrgOrgRelationTypeInfo getOrgOrgRelationType(String orgOrgRelationTypeKey) {
        try {
            return service.getOrgOrgRelationType(orgOrgRelationTypeKey);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    public List<OrgTreeInfo> getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels) {
        try {
            return service.getOrgTree(orgId, orgHierarchy, maxLevels);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    public OrgInfo getOrganization(String orgId) {
        try {
            return service.getOrganization(orgId);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId) {
        try {
            return service.getPositionRestrictionsByOrg(orgId);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public OrgInfo updateOrganization(OrgInfo orgInfo) {
        try {
            return service.updateOrganization(orgInfo.getId(), orgInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        try {
            return service.updatePositionRestrictionForOrg(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        try {
            return service.updateOrgOrgRelation(orgOrgRelationInfo.getId(), orgOrgRelationInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgPersonRelationInfo createOrgPersonRelation(String orgId,
            String personId, String orgPersonRelationTypeKey,
            OrgPersonRelationInfo orgPersonRelationInfo) {

        try {
            return service.createOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey, orgPersonRelationInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<OrgPersonRelationTypeInfo> getOrgPersonRelationTypesForOrgType(
            String orgTypeKey) {
        try {
            return service.getOrgPersonRelationTypesForOrgType(orgTypeKey);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId) {
        try {
            return service.getOrgPersonRelationsByOrg(orgId);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public StatusInfo removeOrgPersonRelation(String orgPersonRelationId) {
        try {
            return service.removeOrgPersonRelation(orgPersonRelationId);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgPersonRelationInfo updateOrgPersonRelation(
            String orgPersonRelationId,
            OrgPersonRelationInfo orgPersonRelationInfo) {
        try {
            return service.updateOrgPersonRelation(orgPersonRelationId, orgPersonRelationInfo);
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }	
    
    @Override
    public DataSaveResult saveOrgProposal(Data proposal) throws AssemblyException, org.kuali.student.common.ui.client.service.exceptions.OperationFailedException {

        try {
            DataSaveResult s = this.saveData(proposal);
            if (s == null) {
                return null;
            } else {
                Data sampdata;
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
        	LOG.error(e);
            throw new org.kuali.student.common.ui.client.service.exceptions.OperationFailedException("Org Screen XML Cnfig file: recources/org_configure.xml parse exception");
        } 

        return sectionConfigInfo;
    }

    @Override
    public Data fetchOrg(String orgId) {
        try {
            return (Data)this.getData(orgId);
        }
        catch(Exception e){
        	LOG.error(e);
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
        	LOG.error(e);
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
        return null;
    }

    @Override
    protected String deriveDocContentFromData(Data data) {
        return null;
    }

    @Override
    protected String getDefaultMetaDataState() {
        return null;
    }

    @Override
    protected String getDefaultMetaDataType() {
        return null;
    }

    @Override
    protected String getDefaultWorkflowDocumentType() {
        return null;
    }
    
}
