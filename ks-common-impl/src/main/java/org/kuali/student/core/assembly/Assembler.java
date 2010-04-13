package org.kuali.student.core.assembly;

import java.util.List;

import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
/*
 *	ASSEMBLERREVIEW
 *  1) The combination of persistence and translation methods (assemble/disassemble vs get/save):
 *  	- has confused some developers
 *  	- after working through some of it, it looks like most assemblers would do one or other other
 *  	- TargetType vs SourceType is odd looking for persistence only assembers, ends up being Assembler<Whatever, Void>
 *  
 *  2) Need to come up with a clean way of copying properties that are passthrough without a lot of boilerplate code, but...
 *  	- many "passthrough" properties will still have a rename along the way, and possibly a transformation of their position within the graph, e.g.
 *  		cluInfo/officialIdentifier/shortName -> proposal/transcriptTitle  
 */
public interface Assembler<TargetType, SourceType> {
	
	TargetType get(String id) throws AssemblyException;

	Metadata getMetadata(String idType, String id, String type, String state) throws AssemblyException;
	
	Metadata getDefaultMetadata() throws AssemblyException;
	
	SaveResult<TargetType> save(TargetType input) throws AssemblyException;

	TargetType assemble(SourceType input) throws AssemblyException;

	SourceType disassemble(TargetType input) throws AssemblyException;

	List<ValidationResultInfo> validate(TargetType input) throws AssemblyException;

}