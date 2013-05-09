package org.kuali.student.ap.course.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.course.ClassFinderForm;
import org.kuali.student.ap.framework.course.ClassFinderService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ks-ap-test-context.xml" })
@TransactionConfiguration(transactionManager = "JtaTxManager", defaultRollback = true)
@Transactional
public class ClassFinderServiceTest {

	private transient ClassFinderService classFinderService;

	private static class FormImpl implements ClassFinderForm {
		private String query;
		private String criteriaKey;
		private List<String> facet;
		private int start;
		private int length;
		private String sort;
		private boolean reverse;

		public String getQuery() {
			return query;
		}

		public void setQuery(String query) {
			this.query = query;
		}

		public String getCriteriaKey() {
			return criteriaKey;
		}

		public void setCriteriaKey(String criteriaKey) {
			this.criteriaKey = criteriaKey;
		}

		public List<String> getFacet() {
			return facet;
		}

		public void setFacet(List<String> facet) {
			this.facet = facet;
		}

		@Override
		public boolean isCriterion(String facet) {
			return facet != null && facet.startsWith("kuali.test.criteria.");
		}

		public int getStart() {
			return start;
		}

		public void setStart(int start) {
			this.start = start;
		}

		public int getCount() {
			return length;
		}

		public void setCount(int length) {
			this.length = length;
		}

		public String getSort() {
			return sort;
		}

		public void setSort(String sort) {
			this.sort = sort;
		}

		public boolean isReverse() {
			return reverse;
		}

		public void setReverse(boolean reverse) {
			this.reverse = reverse;
		}
	}

	@Before
	public void setup() {
		classFinderService = KsapFrameworkServiceLocator
				.getClassFinderService();
	}

	@Test
	public void testSanity() {
		assertNotNull(classFinderService);
	}

	@Test
	public void testLoadSaveCriteria() {
		List<String> facet = new java.util.LinkedList<String>();
		ClassFinderForm form = new FormImpl();
		form.setFacet(facet);
		assertEquals("wqfpbf", classFinderService.saveCriteria(form));
		facet.add("i.am.not.criteria.foo");
		assertEquals("wqfpbf", classFinderService.saveCriteria(form));
		facet.add("kuali.test.criteria.foo");
		assertEquals("dfeqzf", classFinderService.saveCriteria(form));
		facet.add("kuali.test.criteria.bar");
		assertEquals("zgeksq", classFinderService.saveCriteria(form));

		form.setFacet(new java.util.LinkedList<String>());
		classFinderService.restoreCriteria("zgeksq", form);
		assertEquals(2, form.getFacet().size());
		assertEquals("kuali.test.criteria.foo", form.getFacet().get(0));
		assertEquals("kuali.test.criteria.bar", form.getFacet().get(1));
		
	}

}
