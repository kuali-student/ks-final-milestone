package org.kuali.student.common.assembly;

import java.util.List;
import java.util.Map;

import org.kuali.student.core.validation.dto.ValidationResultInfo;
public interface Assembler {
	void chain(Assembler assembler);

	Data createNew(String type, String state);

	Data get(String id);

	Metadata getMetadata();

	Metadata getMetadata(String type, String state);

	Map<Data, List<ValidationResultInfo>> save(Data data);

	Data transform(Data data);

	List<ValidationResultInfo> validate(Data data);
}