/*
 * Copyright 2014 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary;

import org.kuali.rice.krad.datadictionary.DataDictionary;
import org.kuali.rice.krad.service.DataDictionaryService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.service.impl.DataDictionaryServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class extends DataDictionaryServiceImpl to enable additional setup
 * and parsing of DD files.
 */
public class StudentDataDictionaryServiceImpl extends DataDictionaryServiceImpl implements DataDictionaryService {
    private Map<String, List<String>> uifAdditionalDictionaryFiles;

    private Map<String, List<String>> additionalDictionaryFiles;

    public void init() throws IOException {
        DataDictionary dataDictionary = KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary();

        // Add Uif files to the "behind the scenes" dataDictionary (the one found by internal service locator
        // during DD post processing). This will avoid errors from UifDefaultingServiceImpl when it is trying
        // to instantiate controls.
        for (Map.Entry<String, List<String>> entry : uifAdditionalDictionaryFiles.entrySet()) {
            String namespaceCode = entry.getKey();
            List<String> locations = entry.getValue();
            for (String location : locations) {
                dataDictionary.addConfigFileLocation(namespaceCode, location);
            }
        }
        dataDictionary.parseDataDictionaryConfigurationFiles(false);

        // add KS DD files to this data dictionary
        super.setAdditionalDictionaryFiles(additionalDictionaryFiles);
        // parse data dictionary config files
        super.getDataDictionary().parseDataDictionaryConfigurationFiles(false);
    }

    @Override
    public void setAdditionalDictionaryFiles(Map<String, List<String>> additionalDictionaryFiles) {
        this.additionalDictionaryFiles = additionalDictionaryFiles;
    }

    public void setUifAdditionalDictionaryFiles(Map<String, List<String>> uifAdditionalDictionaryFiles) {
        this.uifAdditionalDictionaryFiles = uifAdditionalDictionaryFiles;
    }
}
