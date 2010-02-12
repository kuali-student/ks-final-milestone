package com.google.gwt.user.client.rpc.core.java.lang;

@SuppressWarnings("deprecation")
public class Throwable_FieldSerializer {
  private static native java.lang.String getDetailMessage(java.lang.Throwable instance) /*-{
    return instance.@java.lang.Throwable::detailMessage;
  }-*/;
  
  private static native void  setDetailMessage(java.lang.Throwable instance, java.lang.String value) /*-{
    instance.@java.lang.Throwable::detailMessage = value;
  }-*/;
  
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, java.lang.Throwable instance) throws com.google.gwt.user.client.rpc.SerializationException{
    setDetailMessage(instance, streamReader.readString());
    
  }
  
  public static native java.lang.Throwable instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException/*-{
    return @java.lang.Throwable::new()();
  }-*/;
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, java.lang.Throwable instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeString(getDetailMessage(instance));
    
  }
  
}
