package org.apache.torque.mojo;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import org.apache.torque.util.TransactionComparator;
import org.junit.Test;
import org.kuali.db.jdbc.Transaction;

public class TransactionComparatorTest {

	@Test
	public void testComparator() {
		Comparator<Transaction> comparator = new TransactionComparator<Transaction>("ks-embedded-db");
		Vector<Transaction> transactions = new Vector<Transaction>();
		transactions.add(getTransaction("003.sql"));
		transactions.add(getTransaction("004.sql"));
		transactions.add(getTransaction("001.sql"));
		transactions.add(getTransaction("sql/oracle/ks-embedded-db.sql"));
		transactions.add(getTransaction("002.sql"));
		transactions.add(getTransaction("sql/oracle/ks-embedded-db-constraints.sql"));
		transactions.add(getTransaction("001.sql"));
		transactions.add(getTransaction("000.sql"));
		transactions.add(getTransaction("zzz000.sql"));
		Collections.sort(transactions, comparator);
		showTransactions(transactions);
	}

	protected void showTransactions(Vector<Transaction> transactions) {
		for (Transaction t : transactions) {
			System.out.println(t.getResourceLocation());
		}
	}

	protected Transaction getTransaction(String location) {
		Transaction t = new Transaction();
		t.setResourceLocation(location);
		return t;
	}
}
