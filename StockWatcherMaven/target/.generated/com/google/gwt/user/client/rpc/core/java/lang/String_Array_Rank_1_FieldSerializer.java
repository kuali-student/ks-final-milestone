package com.google.gwt.user.client.rpc.core.java.lang;

@SuppressWarnings("deprecation")
public class String_Array_Rank_1_FieldSerializer {
  public static void deserialize(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader, java.lang.String[] instance) throws com.google.gwt.user.client.rpc.SerializationException{
    for (int i = 0, n = instance.length; i < n; ++i) {
      instance[i] = streamReader.readString();
    }
  }
  
  public static java.lang.String[] instantiate(com.google.gwt.user.client.rpc.SerializationStreamReader streamReader) throws com.google.gwt.user.client.rpc.SerializationException{
    int rank = streamReader.readInt();
    return new java.lang.String[rank];
  }
  
  public static void serialize(com.google.gwt.user.client.rpc.SerializationStreamWriter streamWriter, java.lang.String[] instance) throws com.google.gwt.user.client.rpc.SerializationException {
    streamWriter.writeInt(instance.length);
    
    for (int i = 0, n = instance.length; i < n; ++i) {
      streamWriter.writeString(instance[i]);
    }
  }
  
}
