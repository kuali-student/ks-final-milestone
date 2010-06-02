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

package org.kuali.student.lum.lu.assembly.poc;

import java.util.List;

import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.assembly.BaseAssembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.DataBeanMapper;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.dictionary.poc.MetadataServiceImpl;
import org.kuali.student.core.assembly.util.DefaultDataBeanMapper;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.service.CourseService;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={Throwable.class})
public class CreditCourseProposalAssembler extends BaseAssembler<Data, Void> {
    public static final String CREDIT_COURSE_PROPOSAL_DATA_TYPE = "org.kuali.student.lum.course.dto.CourseInfo";
    
    private CourseService courseService;
    private DataBeanMapper dataMapper = new DefaultDataBeanMapper();
    private MetadataServiceImpl metadataService;
    
	@Override
    public Data get(String id) throws AssemblyException {
        try {
			CourseInfo course = courseService.getCourse(id);
			
			Data courseData = dataMapper.convertFromBean(course);
			LuData luData = new LuData(courseData);
			
	        return luData;
        } catch (Exception e) {
			throw new AssemblyException("Unable to retreive course");
		}        
    }

	@Override
    public SaveResult<Data> save(Data data) throws AssemblyException {
    	try {
            CourseInfo course = (CourseInfo)dataMapper.convertFromData(data, CourseInfo.class);
            if (course.getId() == null || course.getId().isEmpty()){
            	course = courseService.createCourse(course);
            } else {
            	course = courseService.updateCourse(course);
            }
            
            Data persistedData = dataMapper.convertFromBean(course);
            SaveResult<Data> result = new SaveResult<Data>();
            result.setValue(persistedData);
            
            return result;
        } catch (Exception e) {
            throw new AssemblyException("Unable to save proposal", e);
        }
    }
    
    @Override
    public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
        Metadata metadata = metadataService.getMetadata(getDataType());
        super.applyPermissionsToMetadata(metadata,idType, id);
        
        return metadata;
    }


    @Override
    public List<ValidationResultInfo> validate(Data data)
            throws AssemblyException {
        // TODO validate CreditCourseProposal
        return null;
    }

    @Override
    public Data assemble(Void input) throws AssemblyException {
        throw new UnsupportedOperationException("CreditCourseProposalAssember does not support assembly from source type");
    }

    @Override
    public Void disassemble(Data input)
            throws AssemblyException {
        throw new UnsupportedOperationException("CreditCourseProposalAssember does not support disassembly to source type");
    }
   
    @Override
    protected String getDataType() {
        return CREDIT_COURSE_PROPOSAL_DATA_TYPE;
    }

    @Override
    protected String getDocumentPropertyName() {
        return "course";
    }

    @Override
    protected String getDtoName() {
        return "kuali.lu.type.CreditCourse";
    }

    @Override
    public boolean checkDocumentLevelPermissions() {
    	return true;
    }

    @Override
    protected AttributeSet getQualification(String idType, String id) {
        AttributeSet qualification = new AttributeSet();
    	// FIXME: change this value to use constants from rice
        String DOCUMENT_TYPE_NAME = "documentTypeName";
        qualification.put(DOCUMENT_TYPE_NAME, "CluCreditCourseProposal");
        qualification.put(idType, id);
        return qualification;
    }

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
    public void setMetadataService(MetadataServiceImpl metadataService) {
		this.metadataService = metadataService;
	}

}
