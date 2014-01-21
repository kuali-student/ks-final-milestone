package org.kuali.i18n;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created with IntelliJ IDEA.
 * User: chmaurer
 * Date: 1/15/14
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface KualiResourceBundle {

    /**
     * Return string value for specified property in current locale specific ResourceBundle
     *
     * @param key
     *        property key to look up in current ResourceBundle
     * @return String value for specified property key
     * @throws MissingResourceException When the key is not found
     */
    String getString(String key) throws MissingResourceException;

    /**
     * Return string value for specified property in current locale specific ResourceBundle
     *
     * @param key
     *        property key to look up in current ResourceBundle
     * @param dflt
     *        the default value to be returned in case the property is missing
     * @return String value for specified property key
     */
    String getString(String key, String dflt);

    /**
     * Return formatted message based on locale-specific pattern
     *
     * @param key
     *        maps to locale-specific pattern in properties file
     * @param args
     *        parameters to format and insert according to above pattern
     * @return formatted message
     */
    String getFormattedMessage(String key, Object... args);
}
