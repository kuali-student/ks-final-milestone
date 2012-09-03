package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.class2.courseoffering.service.RegistrationGroupCodeGenerator;

/**
 * Helpful when you require a stateful code generator
 */
public interface RegistrationGroupCodeGeneratorFactory {
    RegistrationGroupCodeGenerator makeCodeGenerator();
}
