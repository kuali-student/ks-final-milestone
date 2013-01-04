package org.kuali.student.common.ui.server.screenreport.jasper;

import java.util.Collection;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * 
 * This class overwrites the common JRBeanCollectionDataSource in order
 * to return a JRDataSource to be used on the subreport when the field is
 * called "subset". 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class KSCollectionDataSource extends JRBeanCollectionDataSource {

    public KSCollectionDataSource(Collection beanCollection) {
        super(beanCollection);
    }

    protected Object getFieldValue(Object bean, JRField field) throws JRException {
        Object object = getBeanProperty(bean, getPropertyName(field));

        if ("subset".equals(field.getName())) {
            return new JRBeanCollectionDataSource((Collection) object);
        }
        return object;
    }

}
