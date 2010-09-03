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
		// If loc1 is ks-embedded-db.sql, loc1 goes before loc2
		if (isSchemaSQL(loc1)) {
			return -1;
		}
		// If loc2 is ks-embedded-db.sql, loc1 goes after loc2
		if (isSchemaSQL(loc2)) {
			return 1;
		}
		// If loc1 is ks-embedded-db-constraints.sql, loc1 goes after loc2
		if (isSchemaConstraintsSQL(loc1)) {
			return 1;
		}
		// If loc2 is ks-embedded-db-constraints.sql, loc1 goes before loc2
		if (isSchemaConstraintsSQL(loc2)) {
			return -1;
		}
		// They are both empty, it is a tie
		if (isEmpty(loc1) && isEmpty(loc2)) {
			return 0;
		}
		// Loc2 is empty but loc1 is not, loc1 goes after loc2
		if (isEmpty(loc1) && !isEmpty(loc2)) {
			return 1;
		}
		// Loc1 is empty but loc2 is not, loc1 goes before loc2
		if (!isEmpty(loc1) && isEmpty(loc2)) {
			return -1;
		}
		// Fall through to the normal string compare
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
