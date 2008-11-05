package org.kuali.student.enumeration.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.entity.EnumeratedValue;
import org.kuali.student.enumeration.entity.EnumerationMeta;

public interface EnumerationManagementDAO {

    public List<EnumerationMeta> findEnumerationMetas();

    public EnumerationMeta fetchEnumerationMeta(String enumerationKey);

    public List<EnumeratedValue> fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate);

    public EnumeratedValue addEnumeratedValue(String enumerationKey, EnumeratedValue value);

    public EnumeratedValue updateEnumeratedValue(String enumerationKey, String code, EnumeratedValue value);

    public void removeEnumeratedValue(String enumerationKey, String code);
    /*
     * public Enumerations createEnumerations(Enumerations entry); public boolean deleteEnumerations(Enumerations entry);
     * public Enumerations updateEnumerations(Enumerations entry); public Enumerations lookupEnumerations(String id); public
     * EnumeratedValue createEnumeratedValue(EnumeratedValue value); // Web service public boolean
     * deleteEnumeratedValue(EnumeratedValue value); public EnumeratedValue updateEnumeratedValue(EnumeratedValue value);
     * public List<EnumeratedValue> getEnumeratedValues(String id); // Web service public EnumeratedValueContext
     * createEnumeratedValueContext(EnumeratedValueContext context); public boolean
     * deleteEnumeratedValueContext(EnumeratedValueContext context); public EnumeratedValueContext
     * updateEnumeratedValueContext(EnumeratedValueContext context); public EnumeratedValueContext
     * lookupEnumeratedValueContext(String id); public List<EnumeratedValueContext> getEnumeratedValueContexts(String
     * enumeratedValueId);
     */
}
