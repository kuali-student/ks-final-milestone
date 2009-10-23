package org.kuali.student.common.assembly;

import java.util.List;
import java.util.Map;

public interface Metadata {
	List<Constraint> getConstraints();

	String getDataType(); // convert to ClassReference later?

	EnumerationMetadata getEnumerationMeta();

	Map<String, Metadata> getProperties();

	boolean isMasked();

	boolean isReadOnly();
}
