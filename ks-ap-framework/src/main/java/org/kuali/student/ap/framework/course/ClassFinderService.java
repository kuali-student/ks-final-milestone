package org.kuali.student.ap.framework.course;

public interface ClassFinderService {

	String saveCriteria(ClassFinderForm form);

	void restoreCriteria(String key, ClassFinderForm form);

}
