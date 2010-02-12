package com.google.gwt.sample.stockwatcher.client;

import java.beans.XMLEncoder;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class StockWatcherTest extends GWTTestCase {
	public static void main(String[] args) {
		try {
			StockPrice[] stockPrices = new StockPrice[2];
			stockPrices[0] = new StockPrice();
			stockPrices[1] = new StockPrice();
			stockPrices[0].setChange(0.02);
			stockPrices[0].setPrice(112.0);
			stockPrices[0].setSymbol("AMZN");
			stockPrices[1].setChange(0.22);
			stockPrices[1].setPrice(47.0);
			stockPrices[1].setSymbol("V");

			Method method = new StockWatcherTest().getClass().getMethods()[0];
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			XMLEncoder e = new XMLEncoder(out);
			e.writeObject(method);
			e.close();
			String s = out.toString();
			System.out.println(s);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "com.google.gwt.sample.stockwatcher.StockWatcher";
	}

	/**
	 * Add as many tests as you like.
	 */
	public void testSimple() {
		assertTrue(true);
	}

}