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
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.old.Assembler;
import org.kuali.student.core.assembly.old.data.SaveResult;
import org.kuali.student.core.assembly.util.AssemblerUtils;
import org.kuali.student.core.dto.MetaInfo;
import org.kuali.student.core.dto.StatusInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.organization.assembly.data.server.org.OrgHelper;
import org.kuali.student.core.organization.assembly.data.server.org.OrgPositionHelper;
import org.kuali.student.core.organization.dto.OrgPositionRestrictionInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class OrgPositionRestrictionAssembler implements Assembler<Data, OrgPositionHelper>{
	final Logger LOG = Logger.getLogger(OrgPositionRestrictionAssembler.class);
    private OrganizationService orgService;
    private Metadata metadata;
    public static final String POSITION_PATH                  = "OrgPositionRestrictionInfo";
    private DataModel orgPositionModel = new DataModel();
    public void setMetaData(Metadata metadata){
        this.metadata=metadata;
    }
    
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
        List<OrgPositionRestrictionInfo> positions = new ArrayList<OrgPositionRestrictionInfo>();
        Data orgPositionMap = null;
        try{
            positions = orgService.getPositionRestrictionsByOrg(id);
            orgPositionMap = buildOrgPositionMap(positions);
        }
        catch(DoesNotExistException dnee){
            return null;
            
        }
        catch(Exception e){
            LOG.error(e);
        }
        return orgPositionMap;
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
        addPositionRestriction(input);
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
    
    private void addPositionRestriction(Data input) throws AssemblyException{
        if (input == null) {
            return;
        }
        DataModelDefinition def = new DataModelDefinition(metadata);
        orgPositionModel.setDefinition(def);
        QueryPath metaPath = QueryPath.concat(null, POSITION_PATH);
        Metadata orgPersonMeta =orgPositionModel.getMetadata(metaPath);
        
        for (Iterator<Property> propIter=((Data)input.get(POSITION_PATH)).iterator();propIter.hasNext();) {
        	Property p = propIter.next(); 
            OrgPositionHelper orgPositionHelper=  OrgPositionHelper.wrap((Data)p.getValue());
            if (isUpdated(orgPositionHelper.getData())) {
                if (orgPersonMeta.isCanEdit()) {
                    OrgPositionRestrictionInfo orgPositionRestrictionInfo = buildOrgPositionRestrictionInfo(orgPositionHelper);
                    orgPositionRestrictionInfo.setId(orgPositionHelper.getId());
                    try {
                        OrgPositionRestrictionInfo result = orgService.updatePositionRestrictionForOrg(orgPositionRestrictionInfo.getOrgId(), orgPositionRestrictionInfo.getOrgPersonRelationTypeKey(), orgPositionRestrictionInfo);
                        addVersionIndicator(orgPositionHelper.getData(), OrgPositionRestrictionInfo.class.getName(), result.getId(), result.getMetaInfo().getVersionInd());
                    } catch (Exception e) {
                        throw new AssemblyException();
                    }
                    //Clear flag to avoid multiple updates
                    AssemblerUtils.setUpdated(orgPositionHelper.getData(),false);
                }
            }
            else if(isDeleted(orgPositionHelper.getData())&&orgPositionHelper.getId()!=null){
                try{
                    if(orgPositionHelper.getId()!=null){
                        StatusInfo  result = orgService.removePositionRestrictionFromOrg(orgPositionHelper.getOrgId(), orgPositionHelper.getPersonRelationType());
                        propIter.remove();
                    }
                }
                catch(Exception e ){
                    LOG.error(e);
                    throw(new AssemblyException());
                }
            }
            else if(isCreated(orgPositionHelper.getData())){
                orgPositionHelper.setOrgId((OrgHelper.wrap((Data)input.get("orgInfo")).getId()));
                OrgPositionRestrictionInfo orgPositionRestrictionInfo = buildOrgPositionRestrictionInfo(orgPositionHelper);
                try{
                    OrgPositionRestrictionInfo  result = orgService.addPositionRestrictionToOrg(orgPositionHelper.getOrgId(), 
                            orgPositionHelper.getPersonRelationType(), orgPositionRestrictionInfo);
                    orgPositionHelper.setId(result.getId());
                    addVersionIndicator(orgPositionHelper.getData(),OrgPositionRestrictionInfo.class.getName(),result.getId(),result.getMetaInfo().getVersionInd());
                }
                catch(Exception e ){
                    LOG.error(e);
                    throw new AssemblyException();
                }
                //Clear flag to avoid multiple creates
                AssemblerUtils.setCreated(orgPositionHelper.getData(),false);
            }
           
          
        }
        
    }
    
    
    private Data buildOrgPositionMap( List<OrgPositionRestrictionInfo> positions){
        Data orgPositions = new Data();
        int count =0;
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
            addVersionIndicator(orgPositionHelper.getData(),OrgPositionRestrictionInfo.class.getName(),position.getId(),position.getMetaInfo().getVersionInd());
            orgPositions.set(count,orgPositionHelper.getData());
            count = count +1;
        }
        return orgPositions;
    }
    
    private OrgPositionRestrictionInfo buildOrgPositionRestrictionInfo(OrgPositionHelper orgPositionHelper){
        OrgPositionRestrictionInfo orgPositionRestrictionInfo = new OrgPositionRestrictionInfo();
        orgPositionRestrictionInfo.setOrgPersonRelationTypeKey(orgPositionHelper.getPersonRelationType());
        orgPositionRestrictionInfo.setTitle(orgPositionHelper.getTitle());
        orgPositionRestrictionInfo.setDesc(orgPositionHelper.getDesc());
        orgPositionRestrictionInfo.setMinNumRelations(orgPositionHelper.getMinNumRelations());
        orgPositionRestrictionInfo.setMaxNumRelations(orgPositionHelper.getMaxNumRelations());
        orgPositionRestrictionInfo.setOrgId(orgPositionHelper.getOrgId());
        if (isModified(orgPositionHelper.getData())) {
            if (isUpdated(orgPositionHelper.getData())) {
                MetaInfo metaInfo = new MetaInfo();
                orgPositionRestrictionInfo.setMetaInfo(metaInfo);
                orgPositionRestrictionInfo.setId(orgPositionHelper.getId());
            }
            else if (isDeleted(orgPositionHelper.getData())) {
            }
            else if (isCreated(orgPositionHelper.getData())) {
            } 
        }
        if(orgPositionRestrictionInfo.getMetaInfo()!=null){
            orgPositionRestrictionInfo.getMetaInfo().setVersionInd(getVersionIndicator(orgPositionHelper.getData()));
        }
        return orgPositionRestrictionInfo;
    }

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

}
