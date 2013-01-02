/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.kim.identity.mock;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import org.kuali.rice.core.api.config.property.Config;

/**
 *
 * @author nwright
 */
public class MockConfig implements Config {

    @Override
    public String getAlternateOJBFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getAlternateSpringFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getBaseWebServiceURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getBaseWebServiceWsdlPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean getBatchMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getClientWSDLFullPathAndFileName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDailyEmailFirstDeliveryDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDefaultKewNoteClass() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean getDevMode() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDocumentLockTimeout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getEDLConfigLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getEmailConfigurationPath() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean getEmailReminderLifecycleEnabled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getEmbeddedPluginLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getEndPointUrl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getEnvironment() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKENBaseURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKEWBaseURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKIMBaseURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKRBaseURL() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKeystoreAlias() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKeystoreFile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getKeystorePassword() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getLog4jFileLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getLog4jReloadInterval() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getObject(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, Object> getObjects() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean getOutBoxOn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Properties getProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, String> getPropertiesWithPrefix(String prefix, boolean stripPrefix) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    protected String encryptionKey = "7IC64w6ksLU";
    
    @Override
    public String getProperty(String key) {
        if ("encryption.key".equals(key)) {
            return encryptionKey;
        }
        return null;
    }

    @Override
    public Integer getRefreshRate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getTransactionTimeout() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getWebServicesConnectRetry() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getWeeklyEmailFirstDeliveryDate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean getXmlPipelineLifeCycleEnabled() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseConfig() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putConfig(Config config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putObject(String key, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putObjects(Map<String, Object> objects) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putProperties(Properties properties) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void putProperty(String key, String value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeObject(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeProperty(String key) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getApplicationName() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getApplicationVersion() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Boolean getBooleanProperty(String arg0) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public Long getNumericProperty(String arg0) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public long getNumericProperty(String arg0, long arg1) {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return 0;
    }

    @Override
    public String getProductionEnvironmentCode() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public String getRiceVersion() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public boolean isProductionEnvironment() {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return false;
    }
    
}
