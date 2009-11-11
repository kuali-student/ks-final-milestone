package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.CluIdentifierInfoData;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;

public class CluIdentifierInfoAssembler implements Assembler<CluIdentifierInfoData, CluIdentifierInfo>{

	@Override
	public CluIdentifierInfoData assemble(CluIdentifierInfo input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluIdentifierInfoData result = new CluIdentifierInfoData();
		result.setCluId(input.getCluId());
		result.setCode(input.getCode());
		result.setDivision(input.getDivision());
		result.setId(input.getId());
		result.setLevel(input.getLevel());
		result.setLongName(input.getLongName());
		result.setOrgId(input.getOrgId());
		result.setShortName(input.getShortName());
		result.setState(input.getState());
		result.setSuffixCode(input.getSuffixCode());
		result.setType(input.getType());
		result.setVariation(input.getVariation());
		return result;
	}

	@Override
	public CluIdentifierInfo disassemble(CluIdentifierInfoData input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CluIdentifierInfo result = new CluIdentifierInfo();
		result.setCluId(input.getCluId());
		result.setCode(input.getCode());
		result.setDivision(input.getDivision());
		result.setId(input.getId());
		result.setLevel(input.getLevel());
		result.setLongName(input.getLongName());
		result.setOrgId(input.getOrgId());
		result.setShortName(input.getShortName());
		result.setState(input.getState());
		result.setSuffixCode(input.getSuffixCode());
		result.setType(input.getType());
		result.setVariation(input.getVariation());
		return result;
	}

	@Override
	public CluIdentifierInfoData get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(CluIdentifierInfoAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata() throws AssemblyException {
		return null;
	}

	@Override
	public SaveResult<CluIdentifierInfoData> save(CluIdentifierInfoData input)
			throws AssemblyException {
		throw new UnsupportedOperationException(CluIdentifierInfoAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(CluIdentifierInfoData input)
			throws AssemblyException {
		return null;
	}
}
