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

import static org.kuali.rice.core.api.criteria.PredicateFactory.and;
import static org.kuali.rice.core.api.criteria.PredicateFactory.equal;
import static org.kuali.rice.core.api.criteria.PredicateFactory.in;

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
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.identity.IdentityService;
import org.kuali.rice.kim.api.identity.entity.EntityDefault;
import org.kuali.rice.kim.api.identity.entity.EntityDefaultQueryResults;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.common.ui.server.gwt.old.AbstractBaseDataOrchestrationRpcGwtServlet;
import org.kuali.student.core.organization.dynamic.Field;
import org.kuali.student.core.organization.dynamic.Fields;
import org.kuali.student.core.organization.dynamic.MultipleField;
import org.kuali.student.core.organization.dynamic.Section;
import org.kuali.student.core.organization.dynamic.SectionConfig;
import org.kuali.student.core.organization.dynamic.SectionView;
import org.kuali.student.core.organization.ui.client.mvc.model.FieldInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.FieldInfoImpl;
import org.kuali.student.core.organization.ui.client.mvc.model.MembershipInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.MultipleFieldInfoImpl;
import org.kuali.student.core.organization.ui.client.mvc.model.OrgPositionPersonRelationInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionConfigInfo;
import org.kuali.student.core.organization.ui.client.mvc.model.SectionViewInfo;
import org.kuali.student.core.organization.ui.client.service.OrgRpcService;
import org.kuali.student.r1.common.assembly.data.AssemblyException;
import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.rice.authorization.PermissionType;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgHierarchyInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPersonRelationInfo;
import org.kuali.student.r2.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.r2.core.organization.dto.OrgTreeInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;

public class OrgRpcGwtServlet extends AbstractBaseDataOrchestrationRpcGwtServlet implements OrgRpcService{
	final Logger LOG = Logger.getLogger(OrgRpcGwtServlet.class);
	private static final long serialVersionUID = 1L;
	public static final String CONFIGURE_XML_PATH = "C:/org_configure.xml";
	private IdentityService identityServiceNonCached;
	private OrganizationService service;

	public void setIdentityServiceNonCached(IdentityService identityServiceNonCached) {
		this.identityServiceNonCached = identityServiceNonCached;
	}
	
	public void setService(OrganizationService service){
	    this.service=service;
	}
	
    @Override
    public StatusInfo deleteOrgPositionRestriction(String orgPositionRestrictionId){
        try {
        	return service.deleteOrgPositionRestriction(orgPositionRestrictionId, ContextUtils.getContextInfo());
        } catch (Exception e) {
			LOG.error(e);
		}
        return null;
    }
    
    @Override
    public StatusInfo removeOrgOrgRelation(String orgOrgRelationId){
        try {
        	return service.deleteOrgOrgRelation(orgOrgRelationId, ContextUtils.getContextInfo());
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }
    
    @Override
    public List<OrgHierarchyInfo> getOrgHierarchies() {
        try {
            List<OrgHierarchyInfo> orgHierarchies = new ArrayList<OrgHierarchyInfo>();
            orgHierarchies.addAll(service.getOrgHierarchies(ContextUtils.getContextInfo()));
        	return orgHierarchies;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;    
    }

    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByOrg(String orgId) {
        try {
            List<OrgOrgRelationInfo> orgOrgRelations = new ArrayList<OrgOrgRelationInfo>();
            orgOrgRelations.addAll(service.getOrgOrgRelationsByOrg(orgId,ContextUtils.getContextInfo()));
            return orgOrgRelations;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }
    
    @Override
    public List<OrgOrgRelationInfo> getOrgOrgRelationsByRelatedOrg(String orgId) {
        
        try {
            List<OrgOrgRelationInfo> orgOrgRelations = new ArrayList<OrgOrgRelationInfo>();
            orgOrgRelations.addAll(service.getOrgOrgRelationsByOrg(orgId, ContextUtils.getContextInfo()));
        	return orgOrgRelations;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<OrgInfo> getOrganizationsByIdList(List<String> orgIdList) {
        try {
            List<OrgInfo> orgs = new ArrayList<OrgInfo>();
            orgs.addAll(service.getOrgsByIds(orgIdList, ContextUtils.getContextInfo()));
        	return orgs;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<String> getAllDescendants(String orgId, String orgHierarchy) {
        try {
            List<String> descendants = new ArrayList<String>();
            descendants.addAll(service.getAllDescendants(orgId, orgHierarchy, ContextUtils.getContextInfo()));
        	return descendants;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgPositionRestrictionInfo addPositionRestrictionToOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        try {
        	return service.createOrgPositionRestriction(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo, ContextUtils.getContextInfo());
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgInfo createOrganization(OrgInfo orgInfo) {
        try {
            return service.createOrg(orgInfo.getTypeKey(), orgInfo, ContextUtils.getContextInfo());
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }

    @Override
    public OrgOrgRelationInfo createOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        try {
            return service.createOrgOrgRelation(orgOrgRelationInfo.getOrgId(), orgOrgRelationInfo.getRelatedOrgId(),
                    orgOrgRelationInfo.getTypeKey(), orgOrgRelationInfo,ContextUtils.getContextInfo());
        } catch (Exception e) {
            LOG.error(e);
        }
        return null;
    }


    public List<TypeInfo> getOrgPersonRelationTypes() {
        try {
            List<TypeInfo> types = new ArrayList<TypeInfo>();
            types.addAll(service.getOrgPersonRelationTypes(ContextUtils.getContextInfo()));
        	return types;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    public List<TypeInfo> getOrgTypes() {
        try {
            List<TypeInfo> types = new ArrayList<TypeInfo>();
        	types.addAll(service.getOrgTypes(ContextUtils.getContextInfo()));
        	return types;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public List<TypeInfo> getOrgOrgRelationTypes() {
        try {
            List<TypeInfo> types = new ArrayList<TypeInfo>();
            types.addAll(service.getOrgOrgRelationTypes(ContextUtils.getContextInfo()));
        	return types;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }
    
    @Override
    public TypeInfo getOrgOrgRelationType(String orgOrgRelationTypeKey) {
        try {
            List<TypeInfo> typeInfos = getOrgOrgRelationTypes();
            if(typeInfos != null){
                for(TypeInfo info : typeInfos){
                    if(info.getKey().equals(orgOrgRelationTypeKey)){
                        return info;
                    }

                }
            }

        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    public List<OrgTreeInfo> getOrgDisplayTree(String orgId, String orgHierarchy, int maxLevels) {
        try {
            List<OrgTreeInfo> orgTrees = new ArrayList<OrgTreeInfo>();
        	orgTrees.addAll(service.getOrgTree(orgId, orgHierarchy, maxLevels, ContextUtils.getContextInfo()));
        	return orgTrees;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    public OrgInfo getOrganization(String orgId) {
        try {
        	return service.getOrg(orgId, ContextUtils.getContextInfo());
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public List<OrgPositionRestrictionInfo> getPositionRestrictionsByOrg(String orgId) {
        try {
            List<String> ids = new ArrayList<String>();
            ids.add(orgId);
            
            List<OrgPositionRestrictionInfo> orgPosRestrictions = new ArrayList<OrgPositionRestrictionInfo>();
        	orgPosRestrictions.addAll(service.getOrgPositionRestrictionsByIds(ids, ContextUtils.getContextInfo()));
        	return orgPosRestrictions;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public OrgInfo updateOrganization(OrgInfo orgInfo) {
        try {
        	return service.updateOrg(orgInfo.getId(), orgInfo, ContextUtils.getContextInfo());
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }


    public OrgPositionRestrictionInfo updatePositionRestrictionForOrg(OrgPositionRestrictionInfo orgPositionRestrictionInfo) {
        try {
        	return service.updateOrgPositionRestriction(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo, ContextUtils.getContextInfo());
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public OrgOrgRelationInfo updateOrgOrgRelation(OrgOrgRelationInfo orgOrgRelationInfo) {
        try {
        	return service.updateOrgOrgRelation(orgOrgRelationInfo.getId(), orgOrgRelationInfo, ContextUtils.getContextInfo());
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
        	return service.createOrgPersonRelation(orgId, personId, orgPersonRelationTypeKey, orgPersonRelationInfo, ContextUtils.getContextInfo());
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<TypeInfo> getOrgPersonRelationTypesForOrgType(
            String orgTypeKey) {
        try {
            List<TypeInfo> types = new ArrayList<TypeInfo>();
            types.addAll(service.getOrgPersonRelationTypesForOrgType(orgTypeKey, ContextUtils.getContextInfo()));
            return types;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public List<OrgPersonRelationInfo> getOrgPersonRelationsByOrg(String orgId) {
        try {
            List<OrgPersonRelationInfo> orgPersonRelations = new ArrayList<OrgPersonRelationInfo>();
            orgPersonRelations.addAll(service.getOrgPersonRelationsByOrg(orgId, ContextUtils.getContextInfo()));
            return orgPersonRelations;
        } catch (Exception e) {
        	LOG.error(e);
		}
        return null;
    }

    @Override
    public StatusInfo removeOrgPersonRelation(String orgPersonRelationId) {
        try {
        	return service.deleteOrgPersonRelation(orgPersonRelationId, ContextUtils.getContextInfo());
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
        	return service.updateOrgPersonRelation(orgPersonRelationId, orgPersonRelationInfo, ContextUtils.getContextInfo());
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
            List<OrgPersonRelationInfo> orgPersonRelations = service.getOrgPersonRelationsByOrg(orgId, ContextUtils.getContextInfo());

            List<String> ids = new ArrayList<String>();
            ids.add(orgId);
            List<OrgPositionRestrictionInfo> orgPositionRestrictions = service.getOrgPositionRestrictionsByIds(ids, ContextUtils.getContextInfo());
            
            for(OrgPositionRestrictionInfo position:orgPositionRestrictions){
                personRelationInfo = new OrgPositionPersonRelationInfo();
                personRelationInfo.setOrgPersonRelationTypeKey(position.getOrgPersonRelationTypeKey());
                personRelationInfo.setTitle(position.getTitle());
                personRelationInfo.setDesc(position.getDescr().getPlain());
                personRelationInfo.setMinNumRelations(position.getMinNumRelations().toString());
                personRelationInfo.setMaxNumRelations(position.getMaxNumRelations());
                ArrayList<String> names = new ArrayList<String>();
                for(OrgPersonRelationInfo relation: orgPersonRelations){
                    if(position.getOrgPersonRelationTypeKey().equals(relation.getTypeKey())){
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
        Map<String, MembershipInfo> identities = new HashMap<String, MembershipInfo>();
        for(String pId:personIds ){
            EntityDefault entity = identityServiceNonCached.getEntityDefaultByPrincipalId(pId);
            MembershipInfo memeberEntity = new MembershipInfo();
            memeberEntity.setFirstName(entity.getName().getFirstName());
            memeberEntity.setLastName(entity.getName().getLastName());
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
    
    @Override
    public Boolean isAuthorized(PermissionType type, Map<String, String> attributes) {
        // TODO Auto-generated method stub
        return null;
    }

	public List<ValidationResultInfo> validate(Data data) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
