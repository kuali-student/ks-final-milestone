package org.kuali.student.enumeration.dao;

import java.util.Date;
import java.util.List;

import org.kuali.student.enumeration.dto.EnumeratedValueList;
import org.kuali.student.enumeration.entity.EnumeratedValueDAO;
import org.kuali.student.enumeration.entity.EnumerationMetaDAO;

public interface EnumerationManagementDAO {

    public List<EnumerationMetaDAO> findEnumerationMetas();

    public EnumerationMetaDAO fetchEnumerationMeta(String enumerationKey);

    public List<EnumeratedValueDAO> fetchEnumeration(String enumerationKey, String enumContextKey, String contextValue, Date contextDate);

    public EnumeratedValueDAO addEnumeratedValue(String enumerationKey, EnumeratedValueDAO value);

    public EnumeratedValueDAO updateEnumeratedValue(String enumerationKey, String code, EnumeratedValueDAO value);

    public void removeEnumeratedValue(String enumerationKey, String code);

}
