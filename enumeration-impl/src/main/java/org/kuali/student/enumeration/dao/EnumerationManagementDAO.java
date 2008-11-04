package org.kuali.student.enumeration.dao;

import java.util.List;

import javax.jws.WebParam;

import org.kuali.student.enumeration.dto.EnumerationMetaList;
import org.kuali.student.enumeration.entity.EnumerationMeta;


public interface EnumerationManagementDAO {
    
    public List<EnumerationMeta> findEnumerationMetas();
    
    public EnumerationMeta fetchEnumerationMeta(String enumerationKey);
    
/*    public Enumerations createEnumerations(Enumerations entry);
    
    public boolean deleteEnumerations(Enumerations entry);

    public Enumerations updateEnumerations(Enumerations entry);

    public Enumerations lookupEnumerations(String id);

    
    
    
    
    public EnumeratedValue createEnumeratedValue(EnumeratedValue value); // Web service
    
    public boolean deleteEnumeratedValue(EnumeratedValue value);
    
    public EnumeratedValue updateEnumeratedValue(EnumeratedValue value);
    
    public List<EnumeratedValue> getEnumeratedValues(String id); // Web service
    
    
    
    
    public EnumeratedValueContext createEnumeratedValueContext(EnumeratedValueContext context);
    
    public boolean deleteEnumeratedValueContext(EnumeratedValueContext context);
    
    public EnumeratedValueContext updateEnumeratedValueContext(EnumeratedValueContext context);
    
    public EnumeratedValueContext lookupEnumeratedValueContext(String id);
    
    public List<EnumeratedValueContext> getEnumeratedValueContexts(String enumeratedValueId);
  */  
}
