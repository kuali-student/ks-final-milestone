package org.kuali.student.common_test_tester.support;

public interface MyDao {
	public String findValue(String id);
	public String createValue(Value value);
	public Value findValueFromValue(String value);
	public boolean updateValue(String id, String value);
}
