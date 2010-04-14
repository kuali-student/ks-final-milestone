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

import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluInstructorInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluInstructorInfoMetadata;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;

public class CluInstructorInfoDataAssembler implements Assembler<Data, CluInstructorInfo>{

	@Override
	public Data assemble(CluInstructorInfo input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluInstructorInfoHelper result = CluInstructorInfoHelper.wrap(new Data());
		result.setOrgId(input.getOrgId());
		result.setPersonId(input.getPersonId());
		return result.getData();
	}

	@Override
	public CluInstructorInfo disassemble(Data input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluInstructorInfo result = new CluInstructorInfo();
		CluInstructorInfoHelper hlp = CluInstructorInfoHelper.wrap(input);
		result.setOrgId(hlp.getOrgId());
		result.setPersonId(hlp.getPersonId());
		return result;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(CluInstructorInfoDataAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
		return new CluInstructorInfoMetadata().getMetadata(type, state);
	}

	@Override
	public SaveResult<Data> save(Data input)
			throws AssemblyException {
		throw new UnsupportedOperationException(CluInstructorInfoDataAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(Data input)
			throws AssemblyException {
		return null;
	}

	@Override
	public Metadata getDefaultMetadata() throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}
}
