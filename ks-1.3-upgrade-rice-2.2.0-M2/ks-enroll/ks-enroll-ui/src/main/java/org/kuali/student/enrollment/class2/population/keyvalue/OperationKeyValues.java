package org.kuali.student.enrollment.class2.population.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class OperationKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;


    public List<KeyValue> getKeyValues() {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_UNION_KEY, "Union"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY, "Intersection"));
        keyValues.add(new ConcreteKeyValue(PopulationServiceConstants.POPULATION_RULE_TYPE_EXCLUSION_KEY, "Exclusion"));

        return keyValues;
    }
}
