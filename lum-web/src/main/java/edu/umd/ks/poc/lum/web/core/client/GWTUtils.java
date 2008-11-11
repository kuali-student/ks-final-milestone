package edu.umd.ks.poc.lum.web.core.client;

import java.util.HashMap;

import com.google.gwt.core.client.GWT;

public class GWTUtils {

    public static native String getParamString() /*-{
       return $wnd.location.search;
    }-*/;

    public static HashMap<String, String> parseParamString(String string) {
        HashMap<String, String> map = null;
            if(string != null && !"".equals(string)){
            String[] ray = string.substring(1, string.length()).split("&");
            map = new HashMap<String, String>();

            for (int i = 0; i < ray.length; i++) {
                GWT.log("ray[" + i + "]=" + ray[i], null);
                String[] substrRay = ray[i].split("=");
                map.put(substrRay[0], substrRay[1]);
            }
        }
        return map;
    }
}
