package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface StockPriceServiceAsync {

	void getPrices(StockPrice[] symbols, AsyncCallback<StockPrice[]> callback);

}
