package org.kuali.student.ap.course.service;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import org.kuali.student.ap.course.dao.ClassFinderDao;
import org.kuali.student.ap.course.model.ClassFinderCriteriaEntity;
import org.kuali.student.ap.framework.course.ClassFinderForm;
import org.kuali.student.ap.framework.course.ClassFinderService;
import org.springframework.transaction.annotation.Transactional;

@Singleton
@Transactional(noRollbackFor = { Throwable.class })
public class ClassFinderServiceImpl implements ClassFinderService, Serializable {

	private static final long serialVersionUID = -992006040285011026L;

	private static final char[] BASE31 = new char[] { 'f', 'g', 'x', '5', 'b',
			'c', '7', 'z', '2', 'd', '6', '3', 'y', 'm', '4', 'e', '8', 'h',
			'j', 'k', 'a', 'n', 'p', '9', 'q', 'r', 's', 't', 'u', 'v', 'w', };

	@EJB
	private transient ClassFinderDao classFinderDao;

	private String generateKey(List<String> facet) {
		StringBuilder sb = new StringBuilder();
		for (String f : facet)
			sb.append(f);
		long hash = 4294967291L;
		for (char c : sb.toString().toCharArray())
			hash = 37L * hash + ((long) c);
		StringBuilder rv = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			rv.append(BASE31[(int) (Math.abs(hash + i) % 31L)]);
			hash /= 31L;
		}
		return rv.toString();
	}

	@Override
	public String saveCriteria(ClassFinderForm form) {
		List<String> fc = form.getFacet() == null ? new java.util.ArrayList<String>()
				: new java.util.ArrayList<String>(form.getFacet());
		Iterator<String> fi = fc.iterator();
		while (fi.hasNext())
			if (!form.isCriterion(fi.next()))
				fi.remove();
		String key = generateKey(fc);
		ClassFinderCriteriaEntity cfc = new ClassFinderCriteriaEntity();
		cfc.setKey(key);
		cfc.setLastSearchDate(new Date());
		cfc.setFacet(fc);
		classFinderDao.update(cfc);
		return key;
	}

	@Override
	public void restoreCriteria(String key, ClassFinderForm form) {
		ClassFinderCriteriaEntity cfc = classFinderDao.find(key);
		if (cfc == null)
			return;
		List<String> ofc = form.getFacet();
		if (ofc == null)
			form.setFacet(cfc.getFacet());
		else
			ofc.addAll(cfc.getFacet());
	}

}
