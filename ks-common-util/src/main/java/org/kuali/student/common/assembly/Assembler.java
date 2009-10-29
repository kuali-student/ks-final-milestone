package org.kuali.student.common.assembly;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.validation.dto.ValidationResultInfo;
public interface Assembler {
	void chain(Assembler assembler) throws AssemblyException;

	Data createNew(String type, String state) throws AssemblyException;

	Data get(String id) throws AssemblyException;

	Metadata getMetadata() throws AssemblyException;

	Metadata getMetadata(String type, String state) throws AssemblyException;

	Map<Data, List<ValidationResultInfo>> save(Data data) throws AssemblyException;

	Data transform(Data data) throws AssemblyException;

	List<ValidationResultInfo> validate(Data data) throws AssemblyException;
}