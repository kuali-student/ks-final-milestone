package com.google.gwt.i18n.client;

import com.google.gwt.i18n.client.impl.CurrencyDataImpl;
import com.google.gwt.i18n.client.CurrencyList;

public class CurrencyList_ extends com.google.gwt.i18n.client.CurrencyList {
  
  private void loadSuperCurrencyMap() {
    super.loadCurrencyMap();
  }
  
  @Override
  protected native void loadCurrencyMap() /*-{
    this.@com.google.gwt.i18n.client.CurrencyList_::loadSuperCurrencyMap()();
    this.@com.google.gwt.i18n.client.CurrencyList_::overrideCurrencyMap(Lcom/google/gwt/core/client/JavaScriptObject;)({
      // BRL
      "BRL": [ "BRL", "R$", 2, "R$"],
      // EUR
      "EUR": [ "EUR", "€", 2, "€"],
      // GBP
      "GBP": [ "GBP", "UK£", 2, "GB£"],
      // INR
      "INR": [ "INR", "Rs.", 2, "Rs"],
      // ITL
      "ITL": [ "ITL", "IT₤", 128],
      // JPY
      "JPY": [ "JPY", "JP¥", 0, "JP¥"],
      // USD
      "USD": [ "USD", "US$", 2, "US$"],
    });
  }-*/;
  
  @Override
  public native CurrencyData getDefault() /*-{
    return [ "USD", "US$", 2, "US$"];
  }-*/;
}
