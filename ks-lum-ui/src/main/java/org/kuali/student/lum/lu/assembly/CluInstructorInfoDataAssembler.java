package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.CluInstructorInfoData;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;

public class CluInstructorInfoDataAssembler implements Assembler<CluInstructorInfoData, CluInstructorInfo>{

	@Override
	public CluInstructorInfoData assemble(CluInstructorInfo input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluInstructorInfoData result = new CluInstructorInfoData();
		result.setOrgId(input.getOrgId());
		result.setPersonId(input.getPersonId());
		return result;
	}

	@Override
	public CluInstructorInfo disassemble(CluInstructorInfoData input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluInstructorInfo result = new CluInstructorInfo();
		result.setOrgId(input.getOrgId());
		result.setPersonId(input.getPersonId());
		return result;
	}

	@Override
	public CluInstructorInfoData get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(CluInstructorInfoDataAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata() throws AssemblyException {
		return null;
	}

	@Override
	public SaveResult<CluInstructorInfoData> save(CluInstructorInfoData input)
			throws AssemblyException {
		throw new UnsupportedOperationException(CluInstructorInfoDataAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(CluInstructorInfoData input)
			throws AssemblyException {
		return null;
	}
}
