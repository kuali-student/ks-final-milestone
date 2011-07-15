/*
 * Copyright 2011 The Kuali Foundation
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
package org.kuali.student.r2.common.datadictionary.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.kuali.rice.kns.datadictionary.DataObjectEntry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryTesterHelper {

    private String outputFileName;
    private String dictFileName;
    private String projectUrl;

    public DictionaryTesterHelper(String outputFileName,
            String projectUrl,
            String dictFileName) {
        this.outputFileName = outputFileName;
        this.projectUrl = projectUrl;
        this.dictFileName = dictFileName;
    }

    public List<String> doTest() {
//        if (!new File(dictFileName).exists()) {
//            throw new IllegalArgumentException(dictFileName + " does not exist");
//        }
//        ApplicationContext ac = new FileSystemXmlApplicationContext(dictFileName);
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:" + dictFileName);
        Map<String, DataObjectEntry> beansOfType =
                (Map<String, DataObjectEntry>) ac.getBeansOfType(DataObjectEntry.class);
        for (DataObjectEntry doe : beansOfType.values()) {
            System.out.println("Loading object structure: " + doe.getFullClassName());
            DictionaryValidator validator = new DictionaryValidator(doe, new HashSet());
            List<String> errors = validator.validate();
            if (errors.size() > 0) {
                return errors;
            }
            DictionaryFormatter formatter = new DictionaryFormatter(doe, projectUrl, dictFileName, outputFileName);
            formatter.formatForHtml();
            return new ArrayList<String>();
        }
        return null;
    }
}
