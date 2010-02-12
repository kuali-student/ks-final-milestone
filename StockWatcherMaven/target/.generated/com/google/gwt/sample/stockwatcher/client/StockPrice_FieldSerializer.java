package com.google.gwt.sample.stockwatcher.client;

@SuppressWarnings("deprecation")
public class StockPrice_FieldSerializer {
  private static native double getChange(com.google.gwt.sample.stockwatcher.client.StockPrice instance) /*-{
    return instance.@com.google.gwt.sample.stockwatcher.client.StockPrice::change;
  }-*/;
  
  private static native void  setChange(com.google.gwt.sample.stockwatcher.client.StockPrice instance, double value) /*-{
    instance.@com.google.gwt.sample.stockwatcher.client.StockPrice::change = value;
  }-*/;
  
  private static native double getPrice(com.google.gwt.sample.stockwatcher.client.StockPrice instance) /*-{
    return instance.@com.google.gwt.sample.stockwatcher.client.StockPrice::price;
  }-*/;
  
  private static native void  setPrice(com.google.gwt.sample.stockwatcher.client.StockPrice instance, double value) /*-{
    instance.@com.google.gwt.sample.stockwatcher.client.StockPrice::price = value;
  }-*/;
  
  private static native java.lang.String getSymbol(com.google.gwt.sample.stockwatcher.client.StockPrice instance) /*-{
    return instance.@com.google.gwt.sample.stockwatcher.client.StockPrice::symbol;
  }-*/;
  
  private static native void  setSymbol(com.google.gwt.sample.stockwatcher.client.StockPrice instance, java.lang.String value) /*-{
    instance.@com.google.gwt.sample.stockwatcher.client.StockPrice::symbol = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, com.google.gwt.sample.stockwatcher.client.StockPrice instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setChange(instance, streamReader.readDouble());
    setPrice(instance, streamReader.readDouble());
    setSymbol(instance, streamReader.readString());
    
  }
  
  public static native com.google.gwt.sample.stockwatcher.client.StockPrice instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @com.google.gwt.sample.stockwatcher.client.StockPrice::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, com.google.gwt.sample.stockwatcher.client.StockPrice instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeDouble(getChange(instance));
    streamWriter.writeDouble(getPrice(instance));
    streamWriter.writeString(getSymbol(instance));
    
  }
  
}
