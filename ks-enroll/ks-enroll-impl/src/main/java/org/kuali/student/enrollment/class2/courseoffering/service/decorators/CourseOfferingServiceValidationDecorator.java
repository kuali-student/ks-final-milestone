package org.kuali.student.enrollment.class2.courseoffering.service.decorators;

import org.kuali.student.enrollment.courseoffering.service.CourseOfferingServiceDecorator;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.datadictionary.service.DataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsDataDictionaryService;
import org.kuali.student.r2.common.infc.HoldsValidator;

public class CourseOfferingServiceValidationDecorator extends CourseOfferingServiceDecorator  implements HoldsValidator, HoldsDataDictionaryService{
	private DataDictionaryValidator validator;
	private DataDictionaryService dataDictionaryService;
	
    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;        
    }

	@Override
	public DataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	@Override
	public void setDataDictionaryService(
			DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;		
	}

}
