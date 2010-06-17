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

package org.kuali.student.lum.lu.assembly;

import org.kuali.rice.kew.service.WorkflowInfo;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.student.core.assembly.BaseAssembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.SaveResult;

public class WorkflowAssembler extends BaseAssembler<Data, WorkflowInfo>{

	@Override
	protected String getDataType() {
		return "workflow";
	}

	@Override
	protected String getDocumentPropertyName() {
		return "workflow";
	}

	@Override
	protected String getDtoName() {
		return "";
	}

	@Override
	protected AttributeSet getQualification(String idType, String id) {
/*        String DOCUMENT_TYPE_NAME = "documentTypeName";
        AttributeSet qualification = new AttributeSet();
        qualification.put(DOCUMENT_TYPE_NAME, "workflow");
    
        qualification.put(idType, id);
        return qualification;*/
		return null;
	}

	@Override
	public Data assemble(WorkflowInfo input) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkflowInfo disassemble(Data input) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SaveResult<Data> save(Data input) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

}
