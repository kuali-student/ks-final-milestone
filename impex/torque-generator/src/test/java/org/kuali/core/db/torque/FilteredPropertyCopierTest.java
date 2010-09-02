package org.kuali.core.db.torque;

import static junit.framework.TestCase.*;

import org.junit.Test;
import org.kuali.core.db.torque.pojo.ForeignKey;

public class FilteredPropertyCopierTest {

	@Test
	public void test1() {
		ForeignKey dest = new ForeignKey();
		ForeignKey origin = new ForeignKey();
		origin.setOnDelete("foo");

		FilteredPropertyCopier fpc = new FilteredPropertyCopier();
		fpc.copyProperties(dest, origin);

		assertTrue(dest.getOnDelete().equals("foo"));
	}

	@Test
	public void test2() {
		ForeignKey dest = new ForeignKey();
		ForeignKey origin = new ForeignKey();
		origin.setOnDelete("foo");

		FilteredPropertyCopier fpc = new FilteredPropertyCopier();
		fpc.addExclude("onDelete");
		fpc.copyProperties(dest, origin);

		assertTrue(dest.getOnDelete() == null);
	}

	@Test
	public void test3() {
		ForeignKey dest = new ForeignKey();
		ForeignKey origin = new ForeignKey();
		origin.setOnDelete("foo");
		origin.setRefTableName("bar");

		FilteredPropertyCopier fpc = new FilteredPropertyCopier();
		fpc.addExclude("onDelete");
		fpc.copyProperties(dest, origin);

		assertTrue(dest.getOnDelete() == null);
		assertTrue(dest.getRefTableName().equals("bar"));
	}

	@Test
	public void test4() {
		ForeignKey dest = new ForeignKey();
		ForeignKey origin = new ForeignKey();
		origin.setOnDelete("foo");
		origin.setRefTableName("bar");

		FilteredPropertyCopier fpc = new FilteredPropertyCopier();
		fpc.addInclude("onDelete");
		fpc.copyProperties(dest, origin);

		assertTrue(dest.getOnDelete().equals("onDelete"));
		assertTrue(dest.getRefTableName() == null);
	}
}
