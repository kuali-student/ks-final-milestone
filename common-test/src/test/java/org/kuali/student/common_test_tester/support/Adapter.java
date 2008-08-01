/**
 * 
 */
package org.kuali.student.common_test_tester.support;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter extends XmlAdapter<BaseImpl, Base> {
	public Base unmarshal(BaseImpl v) {
		if(v != null) {
		System.out.println("Adapter unmarshal " + v.getClass().getName());
		} else {
			System.out.println("Adapter unmarshal null");

		}
		return v;
	}
	public BaseImpl marshal(Base v) {
		if(v != null) {
			System.out.println("Adapter marshal " + v.getClass().getName());
		} else {
			System.out.println("Adapter marshal null");

		}
		return (BaseImpl)v;
	}
}