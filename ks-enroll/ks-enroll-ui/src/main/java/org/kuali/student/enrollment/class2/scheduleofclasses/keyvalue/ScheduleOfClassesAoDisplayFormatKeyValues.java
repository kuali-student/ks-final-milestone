package org.kuali.student.enrollment.class2.scheduleofclasses.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.keyvalues.KeyValuesBase;
import org.kuali.student.enrollment.class2.scheduleofclasses.form.ScheduleOfClassesSearchForm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to manage all of the possible values for how the AOs will be grouped on the SchOC view.
 * (ie: "Flat", "By Cluster", "By Registration Group")
 */
public class ScheduleOfClassesAoDisplayFormatKeyValues extends KeyValuesBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues() {

        List<KeyValue> aoDisplayFormats = new ArrayList<KeyValue>();

        for( ScheduleOfClassesSearchForm.AoDisplayFormat displayFormat : ScheduleOfClassesSearchForm.AoDisplayFormat.values() ) {
            aoDisplayFormats.add( new ConcreteKeyValue( displayFormat.name(), displayFormat.getText() ) );
        }

        return aoDisplayFormats;
    }
}
