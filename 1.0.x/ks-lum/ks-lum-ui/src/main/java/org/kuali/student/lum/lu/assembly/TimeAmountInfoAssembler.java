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
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.TimeAmountInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.TimeAmountInfoMetadata;

public class TimeAmountInfoAssembler implements Assembler<Data, TimeAmountInfo>{

	@Override
	public Data assemble(TimeAmountInfo input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		TimeAmountInfoHelper result = TimeAmountInfoHelper.wrap(new Data());
		result.setAtpDurationTypeKey(input.getAtpDurationTypeKey());
		result.setTimeQuantity(input.getTimeQuantity());
		return result.getData();
	}

	@Override
	public TimeAmountInfo disassemble(Data input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		TimeAmountInfo result = new TimeAmountInfo();
		TimeAmountInfoHelper hlp = TimeAmountInfoHelper.wrap(input);
		result.setAtpDurationTypeKey(hlp.getAtpDurationTypeKey());
		result.setTimeQuantity(hlp.getTimeQuantity());
		return result;
	}

	@Override
	public Data get(String id) throws AssemblyException {
		throw new UnsupportedOperationException(TimeAmountInfoAssembler.class.getName() + " does not support retrieval");
	}

	@Override
	public Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException {
		return new TimeAmountInfoMetadata().getMetadata(type, state);
	}

	@Override
	public SaveResult<Data> save(Data input)
			throws AssemblyException {
		throw new UnsupportedOperationException(TimeAmountInfoAssembler.class.getName() + " does not support persistence");
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
