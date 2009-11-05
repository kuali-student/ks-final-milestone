package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.RichTextInfoData;

public class RichTextInfoAssembler implements Assembler<RichTextInfoData, RichTextInfo>{

	@Override
	public RichTextInfoData assemble(RichTextInfo input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		RichTextInfoData result = new RichTextInfoData();
		result.setFormatted(input.getFormatted());
		result.setPlain(input.getPlain());
		return result;
	}

	@Override
	public RichTextInfo disassemble(RichTextInfoData input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		RichTextInfo result = new RichTextInfo();
		result.setFormatted(input.getFormatted());
		result.setPlain(input.getPlain());
		return result;
	}

	@Override
	public RichTextInfoData get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(RichTextInfoAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata() throws AssemblyException {
		return null;
	}

	@Override
	public SaveResult<RichTextInfoData> save(RichTextInfoData input)
			throws AssemblyException {
		throw new UnsupportedOperationException(RichTextInfoAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(RichTextInfoData input)
			throws AssemblyException {
		return null;
	}

}
