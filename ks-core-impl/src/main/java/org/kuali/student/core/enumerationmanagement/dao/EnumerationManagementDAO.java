package org.kuali.student.core.enumerationmanagement.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.core.enumerationmanagement.entity.EnumeratedValueEntity;
import org.kuali.student.core.enumerationmanagement.entity.EnumerationMetaEntity;

public interface EnumerationManagementDAO {

    public List<EnumerationMetaEntity> findEnumerationMetas();

    public EnumerationMetaEntity fetchEnumerationMeta(String enumerationKey);
    
    public EnumerationMetaEntity addEnumerationMeta(EnumerationMetaEntity entity);

    public boolean removeEnumerationMeta(String enumerationKey);
    
    public List<EnumeratedValueEntity> fetchEnumerationWithContextAndDate(String enumerationKey, String enumContextKey, String contextValue, Date contextDate);
   
    public List<EnumeratedValueEntity> fetchEnumerationWithContext(String enumerationKey, String enumContextKey, String contextValue);
   
    public List<EnumeratedValueEntity> fetchEnumerationWithDate(String enumerationKey, Date contextDate);
    
    public List<EnumeratedValueEntity> fetchEnumeration(String enumerationKey);
    
    public EnumeratedValueEntity addEnumeratedValue(String enumerationKey, EnumeratedValueEntity value);

    public EnumeratedValueEntity updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueEntity value);

    public boolean removeEnumeratedValue(String enumerationKey, String code);

    
}
