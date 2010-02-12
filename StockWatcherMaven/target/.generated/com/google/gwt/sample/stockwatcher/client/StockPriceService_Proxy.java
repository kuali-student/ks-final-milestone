package com.google.gwt.sample.stockwatcher.client;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;

public class StockPriceService_Proxy extends RemoteServiceProxy implements com.google.gwt.sample.stockwatcher.client.StockPriceServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "com.google.gwt.sample.stockwatcher.client.StockPriceService";
  private static final String SERIALIZATION_POLICY ="BFDB40B4126702F0801741422168C4B1";
  private static final com.google.gwt.sample.stockwatcher.client.StockPriceService_TypeSerializer SERIALIZER = new com.google.gwt.sample.stockwatcher.client.StockPriceService_TypeSerializer();
  
  public StockPriceService_Proxy() {
    super(GWT.getModuleBaseURL(),
      "stockPrices", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void getPrices(java.lang.String[] symbols, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    int requestId = getNextRequestId();
    boolean toss = isStatsAvailable() && stats(timeStat("StockPriceService_Proxy.getPrices", requestId, "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getPrices");
      streamWriter.writeInt(1);
      streamWriter.writeString("[Ljava.lang.String;/2600011424");
      streamWriter.writeObject(symbols);
      String payload = streamWriter.toString();
      toss = isStatsAvailable() && stats(timeStat("StockPriceService_Proxy.getPrices", requestId, "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "StockPriceService_Proxy.getPrices", requestId, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
}
