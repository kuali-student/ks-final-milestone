package org.kuali.student.common.ui.server.screenreport.jasper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.assembly.data.Data.Property;

/**
 * This is a custom data source class to convert a datamodel to a Jasper report data source object.
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
public class KSCustomDataSource implements JRDataSource {

    private Iterator<Property> iterator;

    private Property property;

    private DateFormat format;

    /**
	 *
	 */
    public KSCustomDataSource(final Iterator<Property> source) {
        this.iterator = source;
        this.format = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
	 *
	 */
    public boolean next() throws JRException {
        try {
            property = (Property) iterator.next();
            if (("metaInfo".equals(property.getKey().toString())) || ("id".equals(property.getKey().toString())) || ("_runtimeData".equals(property.getKey().toString()))) {
                return this.next();
            }
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
	 *
	 */
    public Object getFieldValue(JRField field) throws JRException {
        Object value = null;

        String fieldName = field.getName();
        if ("key".equals(fieldName)) {
            value = property.getKey().toString();
        } else {
            if (property.getValueType().equals(Data.class)) {
                if ("sub".equals(fieldName)) {
                    value = new Boolean(true);
                } else if ("value".equals(fieldName)) {
                    value = null;
                } else if ("subset".equals(fieldName)) {
                    value = new KSCustomDataSource(((Data) property.getValue()).iterator());
                }
            } else {
                if ("sub".equals(fieldName)) {
                    value = new Boolean(false);
                } else if ("value".equals(fieldName)) {
                    if (property.getValue() instanceof Date) {
                        value = format.format((Date) property.getValue());
                    } else {
                        if (property.getValue() != null) {
                            value = property.getValue().toString();
                        }
                    }
                } else if ("subset".equals(fieldName)) {
                    value = null;
                }
            }
        }
        return value;
    }

}
