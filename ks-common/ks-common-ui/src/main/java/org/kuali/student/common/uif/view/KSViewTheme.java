/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by haroon on 2013-08-15
 */
package org.kuali.student.common.uif.view;

import org.apache.log4j.Logger;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.view.ViewTheme;

import java.io.IOException;
import java.util.Properties;

/**
 * This class was written to provide better error reporting.
 *
 * @author Kuali Student Team
 */
public class KSViewTheme extends ViewTheme {
    private static final Logger LOG = Logger.getLogger(KSViewTheme.class);

    /**
     * Sets the {@link #getMinScriptSourceFiles()} and {@link #getMinCssSourceFiles()} lists from the
     * corresponding properties in the theme properties file
     */
    @Override
    protected void setMinFileLists() {
        Properties themeProperties = null;
        try {
            themeProperties = getThemeProperties();
        } catch (IOException e) {
            throw new RuntimeException("Unable to retrieve theme properties for theme: " + getName() + " exception occurred: " + e.getMessage());
        }

        if (themeProperties == null) {
            LOG.warn("No theme properties file found for theme with name: " + getName());

            return;
        }

        String[] cssFiles = getPropertyValue(themeProperties, UifConstants.THEME_CSS_FILES);

        if (cssFiles != null) {
            for (String cssFile : cssFiles) {
                getMinCssSourceFiles().add(cssFile);
            }
        }

        String[] jsFiles = getPropertyValue(themeProperties, UifConstants.THEME_JS_FILES);

        if (jsFiles != null) {
            for (String jsFile : jsFiles) {
                getMinScriptSourceFiles().add(jsFile);
            }
        }
    }
}