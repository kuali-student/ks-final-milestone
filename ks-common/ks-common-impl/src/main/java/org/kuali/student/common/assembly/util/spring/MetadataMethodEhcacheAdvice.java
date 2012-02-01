package org.kuali.student.common.assembly.util.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.util.spring.MethodArgsToObjectEhcacheAdvice;

public class MetadataMethodEhcacheAdvice extends MethodArgsToObjectEhcacheAdvice {

	@Override
	public Object getFromCache(ProceedingJoinPoint pjp) throws Throwable {
		try{
			//Return a copy so that if the data is mutated the original remains
			Metadata result = (Metadata) super.getFromCache(pjp);
			return new Metadata(result);
		}catch(ClassCastException e){
			throw new RuntimeException("This cache should only be used on methods that return Metadata", e);
		}
	}

	
}
