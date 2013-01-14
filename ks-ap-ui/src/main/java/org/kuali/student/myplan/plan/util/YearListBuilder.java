package org.kuali.student.myplan.plan.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;

/**
 *  Assembles a list of published terms.
 */
public class YearListBuilder extends KeyValuesBase {

    private final Logger logger = Logger.getLogger(YearListBuilder.class);

    private int yearCount = 5;

    /**
     *  Build and returns the list of available terms.
     *
     * @return A List of available terms as KeyValue items.
     */
    @Override
    public List<KeyValue> getKeyValues() {

        List<KeyValue> keyValues = new ArrayList<KeyValue>();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);

        for (int i = 0; i < yearCount; i++) {
            String y = String.valueOf(year);
            keyValues.add(new ConcreteKeyValue(y, y));
            year = year + 1;
        }
        return keyValues;
    }
}
