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
package org.kuali.student.datadictionary.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.kuali.student.datadictionary.DataDictionaryObjectStructure;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryTesterHelper {

    private String outputFileName;
    private String className;
    private String dictFileName;
    private String projectUrl;

    public DictionaryTesterHelper(String outputFileName,
            String className,
            String projectUrl,
            String dictFileName) {
        this.outputFileName = outputFileName;
        this.className = className;
        this.projectUrl = projectUrl;
        this.dictFileName = dictFileName;
    }
    private transient Map<String, DataDictionaryObjectStructure> objectStructures;

    public List<String> doTest() {
        ApplicationContext ac = new ClassPathXmlApplicationContext(
                "classpath:" + dictFileName);
        objectStructures = new HashMap();
        Map<String, DataDictionaryObjectStructure> beansOfType =
                (Map<String, DataDictionaryObjectStructure>) ac.getBeansOfType(DataDictionaryObjectStructure.class);
        for (DataDictionaryObjectStructure objStr : beansOfType.values()) {
            objectStructures.put(objStr.getFullClassName(), objStr);
            System.out.println("Loading object structure: " + objStr.getFullClassName());
        }
        DataDictionaryObjectStructure os = null;
        os = objectStructures.get(className);
        if (os == null) {
            throw new RuntimeException("className is not defined in dictionary: " + className);
        }
        DictionaryValidator validator = new DictionaryValidator(os, new HashSet());
        List<String> errors = validator.validate();
        if (errors.size() > 0) {
            return errors;
        }
        DictionaryFormatter formatter = new DictionaryFormatter(os, projectUrl, dictFileName, outputFileName);
        formatter.formatForHtml();
        return new ArrayList<String>();
    }
}
