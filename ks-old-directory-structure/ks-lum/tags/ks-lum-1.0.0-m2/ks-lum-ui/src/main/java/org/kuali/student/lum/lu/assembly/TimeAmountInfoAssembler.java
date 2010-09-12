package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData;

public class TimeAmountInfoAssembler implements Assembler<TimeAmountInfoData, TimeAmountInfo>{

	@Override
	public TimeAmountInfoData assemble(TimeAmountInfo input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		TimeAmountInfoData result = new TimeAmountInfoData();
		result.setAtpDurationTypeKey(input.getAtpDurationTypeKey());
		result.setTimeQuantity(input.getTimeQuantity());
		return result;
	}

	@Override
	public TimeAmountInfo disassemble(TimeAmountInfoData input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		TimeAmountInfo result = new TimeAmountInfo();
		result.setAtpDurationTypeKey(input.getAtpDurationTypeKey());
		result.setTimeQuantity(input.getTimeQuantity());
		return result;
	}

	@Override
	public TimeAmountInfoData get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(TimeAmountInfoAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata() throws AssemblyException {
		return null;
	}

	@Override
	public SaveResult<TimeAmountInfoData> save(TimeAmountInfoData input)
			throws AssemblyException {
		throw new UnsupportedOperationException(TimeAmountInfoAssembler.class.getName() + " does not support persistence");
	}

	@Override
	public List<ValidationResultInfo> validate(TimeAmountInfoData input)
			throws AssemblyException {
		return null;
	}

}
