package org.kuali.student.common.assembly;

import java.util.List;

import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
public interface Assembler<TargetType, SourceType> {

	TargetType get(String id) throws AssemblyException;

	Metadata getMetadata() throws AssemblyException;

	SaveResult<TargetType> save(TargetType input) throws AssemblyException;

	TargetType assemble(SourceType input) throws AssemblyException;

	SourceType disassemble(TargetType input) throws AssemblyException;

	List<ValidationResultInfo> validate(TargetType input) throws AssemblyException;
}