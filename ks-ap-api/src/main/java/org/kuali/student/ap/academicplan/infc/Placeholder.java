package org.kuali.student.ap.academicplan.infc;

import org.kuali.student.r2.common.infc.HasId;
import org.kuali.student.r2.common.infc.HasType;

/**
 * Placeholder
 *
 * @Author mguilla
 */
public interface Placeholder extends HasId, HasType {

	boolean isExclusion();
	String getParm1();
	String getParm2();
	String getParm3();
	String getParm4();
}
