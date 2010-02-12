package com.google.gwt.sample.stockwatcher.client;

@SuppressWarnings("deprecation")
public class DelistedException_FieldSerializer {
  private static native java.lang.String getSymbol(com.google.gwt.sample.stockwatcher.client.DelistedException instance) /*-{
    return instance.@com.google.gwt.sample.stockwatcher.client.DelistedException::symbol;
  }-*/;
  
  private static native void  setSymbol(com.google.gwt.sample.stockwatcher.client.DelistedException instance, java.lang.String value) /*-{
    instance.@com.google.gwt.sample.stockwatcher.client.DelistedException::symbol = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, com.google.gwt.sample.stockwatcher.client.DelistedException instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setSymbol(instance, streamReader.readString());
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static native com.google.gwt.sample.stockwatcher.client.DelistedException instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @com.google.gwt.sample.stockwatcher.client.DelistedException::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, com.google.gwt.sample.stockwatcher.client.DelistedException instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeString(getSymbol(instance));
    
    com.google.gwt.user.client.rpc.core.java.lang.Exception_FieldSerializer.serialize(streamWriter, instance);
  }
  
}
