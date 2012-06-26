package org.kuali.student.enrollment.courseoffering.dto;

import org.kuali.student.enrollment.courseoffering.infc.RegistrationGroupActiviesOptions;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;

import java.util.List;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class RegistrationGroupActiviesOptionsInfo  implements RegistrationGroupActiviesOptions {

    private List<String> activityOfferingIds;

    private EnumeratedValueInfo multiplicity;

    private boolean isMandatory;

}
