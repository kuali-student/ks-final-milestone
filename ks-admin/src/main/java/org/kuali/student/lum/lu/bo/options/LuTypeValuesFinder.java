package org.kuali.student.lum.lu.bo.options;

import org.kuali.student.core.bo.KsTypeBusinessObject;
import org.kuali.student.core.bo.options.TypeValuesFinder;
import org.kuali.student.lum.lu.bo.LuType;

public class LuTypeValuesFinder extends TypeValuesFinder {

	@Override
	public Class<? extends KsTypeBusinessObject> getBusinessObjectClass() {
		return LuType.class;
	}

}
