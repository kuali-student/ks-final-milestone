package org.apache.torque.mojo;

import java.util.Comparator;

import static org.apache.commons.lang.StringUtils.*;

import org.kuali.db.Transaction;

/**
 * 
 *
 */
public class TransactionComparator<T> implements Comparator<Transaction> {

	String suffix = ".sql";
	String constraints = "-constraints";
	String schema;

	public TransactionComparator() {
		this(null);
	}

	public TransactionComparator(String schema) {
		super();
		this.schema = schema;
	}

	@Override
	public int compare(Transaction one, Transaction two) {
		String loc1 = one.getResourceLocation();
		String loc2 = two.getResourceLocation();
		if (isSchemaSQL(loc1)) {
			return -1;
		}
		if (isSchemaSQL(loc2)) {
			return 1;
		}
		if (isSchemaConstraintsSQL(loc1)) {
			return 1;
		}
		if (isSchemaConstraintsSQL(loc2)) {
			return -1;
		}
		if (isEmpty(loc1) && isEmpty(loc2)) {
			return 0;
		}
		if (isEmpty(loc1) && !isEmpty(loc2)) {
			return 1;
		}
		if (!isEmpty(loc1) && isEmpty(loc2)) {
			return -1;
		}
		return loc1.compareTo(loc2);
	}

	protected boolean isSchemaSQL(String location) {
		if (isEmpty(location)) {
			return false;
		} else {
			return location.endsWith(getSchema() + getSuffix());
		}
	}

	protected boolean isSchemaConstraintsSQL(String location) {
		return location.endsWith(getSchema() + getConstraints() + getSuffix());
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getConstraints() {
		return constraints;
	}

	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}

}
