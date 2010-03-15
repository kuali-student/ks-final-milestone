package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.core.assembly.Assembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
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
	public Metadata getMetadata(String id, String type, String state) throws AssemblyException {
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
	public SearchResult search(SearchRequest searchRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}
