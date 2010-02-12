package com.google.gwt.sample.stockwatcher.client;

public class StockWatcherMessages_ implements com.google.gwt.sample.stockwatcher.client.StockWatcherMessages {
  public java.lang.String invalidSymbol(java.lang.String symbol) {
    return "'" + symbol + "' is not a valid symbol.";
  }
  
  public java.lang.String lastUpdate(java.util.Date timestamp) {
    return "Last update: " + com.google.gwt.i18n.client.DateTimeFormat.getMediumDateFormat().format(timestamp) + " " + com.google.gwt.i18n.client.DateTimeFormat.getMediumTimeFormat().format(timestamp);
  }
  
  }
