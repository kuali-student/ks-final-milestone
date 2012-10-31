package org.kuali.student.lum.common.client.lo;

import org.kuali.student.r1.common.assembly.data.Data;
import org.kuali.student.r1.common.assembly.helper.PropertyEnum;

/**
 * @author Igor
 */
public class HelperUtil {
    public static void setDataField(PropertyEnum property, Data data, Data value) {
        data.set(property.getKey(), value);
    }

    public static Data getDataField(PropertyEnum property, Data data) {
        if (data.get(property.getKey()) == null) {
            setDataField(property, data, new Data());
        }
        return data.get(property.getKey());
    }
}
