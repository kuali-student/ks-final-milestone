package org.kuali.student.enrollment.class2.grading.keyvalue;

import org.kuali.rice.core.util.ConcreteKeyValue;
import org.kuali.rice.kns.lookup.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.classII.grading.service.GradingServiceMockImpl;
import org.kuali.student.enrollment.grading.dto.GradeRosterInfo;
import org.kuali.student.enrollment.grading.service.GradingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.test.utilities.TestHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Venkat
 * Date: 8/2/11
 * Time: 4:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class GradeKeyValues  extends KeyValuesBase {

    public List getKeyValues() {
        List keyValues = new ArrayList();

        keyValues.add(new ConcreteKeyValue("", ""));
        keyValues.add(new ConcreteKeyValue("A", "A"));
        keyValues.add(new ConcreteKeyValue("B", "B"));
        keyValues.add(new ConcreteKeyValue("C", "C"));

        return keyValues;
    }
}
