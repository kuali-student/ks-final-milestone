package org.kuali.student.common.ui.server.screenreport.jasper;

import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.data.JRAbstractBeanDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.kuali.student.common.ui.client.util.ExportElement;

/**
 * This class overwrites the common JRBeanCollectionDataSource in order to return a JRDataSource to be used on the subreport
 * when the field is called "subset".
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 */
@Deprecated
public class KSCollectionDataSource extends JRAbstractBeanDataSource {

    /**
    *
    */
    private Collection<ExportElement> data;
    private Iterator<ExportElement> iterator;
    private ExportElement currentBean;
    private ExportElement parent;

    public KSCollectionDataSource(Collection<ExportElement> elements, ExportElement parent) {
        super(true);

        this.parent = parent;
        this.data = elements;

        if (this.data != null) {
            this.iterator = this.data.iterator();
        }
    }

    /**
    *
    */
    public boolean next() {
        boolean hasNext = false;

        if (this.iterator != null) {
            hasNext = this.iterator.hasNext();

            if (hasNext) {
                this.currentBean = this.iterator.next();
                
                // Skip the empty elements in the collection.
                if (this.currentBean.isEmpty()){
                    return next();
                }
            }
        }

        return hasNext;
    }

    @Override
    public Object getFieldValue(JRField field) throws JRException {
        return getFieldValue(currentBean, field);
    }
    
    /**
    *
    */
    protected Object getFieldValue(ExportElement bean, JRField field) throws JRException {
        Object object = getBeanProperty(bean, getPropertyName(field));

        // Return a new datasource object with subset data for subreport.
        if ("subset".equals(field.getName())) {
            return new KSCollectionDataSource((Collection<ExportElement>) object, bean);
            
        // Do not repeat section name for subsets.
        } else if ("sectionName".equals(field.getName())) {
            if (parent != null && parent.getSectionName() != null && parent.getSectionName().equals(object)) {
                return null;
            } else {
                return object;
            }
        }
        return object;
    }

    /**
    *
    */
    public void moveFirst() {
        if (this.data != null) {
            this.iterator = this.data.iterator();
        }
    }

    /**
     * Returns the underlying bean collection used by this data source.
     * 
     * @return the underlying bean collection
     */
    public Collection getData() {
        return data;
    }

    /**
     * Returns the total number of records/beans that this data source contains.
     * 
     * @return the total number of records of this data source
     */
    public int getRecordCount() {
        return data == null ? 0 : data.size();
    }

    /**
     * Clones this data source by creating a new instance that reuses the same underlying bean collection.
     * 
     * @return a clone of this data source
     */
    public JRBeanCollectionDataSource cloneDataSource() {
        return new JRBeanCollectionDataSource(data);
    }

   

}
