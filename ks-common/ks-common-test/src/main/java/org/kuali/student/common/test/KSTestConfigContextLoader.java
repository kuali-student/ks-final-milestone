package org.kuali.student.common.test;

import org.kuali.rice.core.api.config.property.Config;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.rice.core.impl.config.property.JAXBConfigImpl;

import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gtaylor
 * Date: 11/21/12
 * Time: 3:08 PM
 * This class is used to initialize the config context from a spring beans file. It was originally created
 * to allow us to load Rice validation config files for our validation tests.
 *
 * In the spring file create a bean with this name and pass in via spring constructor args a list of
 * rice config file locations. it will load those into the spring context.
 */
public class KSTestConfigContextLoader {

    List<String> configLocations = null;

    public KSTestConfigContextLoader() {

        Config config = getTestHarnessConfig();
        ConfigContext.init(config);
    }

    public KSTestConfigContextLoader(List<String> configLocations) {
        this.configLocations = configLocations;

        Config config = getTestHarnessConfig();
        ConfigContext.init(config);
    }

    protected Config getTestHarnessConfig() {
        Config config = new JAXBConfigImpl(getConfigLocations(), System.getProperties());
        try {
            config.parseConfig();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return config;
    }

    /**
     * Subclasses may override this method to customize the location(s) of the Rice configuration.
     * By default it is: classpath:META-INF/" + getModuleName().toLowerCase() + "-test-config.xml"
     *
     * @return List of config locations to add to this tests config location.
     */
    protected List<String> getConfigLocations() {
        //List<String> configLocations = new ArrayList<String>();
        //configLocations.add("classpath:META-INF/course-offering-test-config.xml");
        return configLocations;
    }

    public void setConfigLocations(List<String> configLocations){
        this.configLocations = configLocations;
    }
}


