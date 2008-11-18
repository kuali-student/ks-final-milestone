package org.kuali.student.lum.atp.dao;

import org.kuali.student.core.entity.Type;
import org.kuali.student.lum.atp.entity.Atp;
import org.kuali.student.lum.atp.entity.DateRange;

public interface AtpDao {

	public Type createType(Type type);

	public Atp fetchAtp(String atpKey);

	public <T> T fetch(Class<T> clazz,
			String key);

	public void createDateRange(DateRange dateRange);
}
