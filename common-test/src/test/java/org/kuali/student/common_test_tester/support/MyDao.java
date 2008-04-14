package org.kuali.student.common_test_tester.support;

public interface MyDao {
	public String findValue(Long id);
	public Long createValue(Value value);
	public Value findValue(String value);
}
