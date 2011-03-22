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
package org.kuali.student.datadictinoary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DictionaryTesterHelper {

    private String outputFileName;
    private File file;
    private OutputStream outputStream;
    private PrintStream out;
    private Set<String> startingClasses;
    private String dictFileName;
    private boolean processSubstructures = false;

    public DictionaryTesterHelper(String outputFileName,
            Set<String> startingClasses,
            String dictFileName,
            boolean processSubstructures) {
        this.outputFileName = outputFileName;
        this.startingClasses = startingClasses;
        this.dictFileName = dictFileName;
        this.processSubstructures = processSubstructures;
        // get printstream from file
        this.file = new File(this.outputFileName);
        try {
            outputStream = new FileOutputStream(file, false);
        } catch (FileNotFoundException ex) {
            throw new IllegalArgumentException(ex);
        }
        this.out = new PrintStream(outputStream);
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
        // First validate all the starting classes
        for (String className : startingClasses) {
            DataDictionaryObjectStructure os = null;
            os = objectStructures.get(className);
            if (os == null) {
                throw new RuntimeException("className is not defined in dictionary: " + className);
            }
            DictionaryValidator validator = new DictionaryValidator(os,
                    new HashSet(),
                    false);
            List<String> errors = validator.validate();
            if (errors.size() > 0) {
                return errors;
            }
        }


        Set<String> allStructures = new LinkedHashSet();
        for (String className : startingClasses) {
            allStructures.addAll(getComplexSubStructures(className));
        }
        Set<String> classesToProcess = null;
        if (this.processSubstructures) {
            classesToProcess = startingClasses;
//   System.out.println ("Processing just the starting classes but then processing their substructures in-line");
        } else {
            classesToProcess = allStructures;
//   System.out.println ("Processing all substructures as separate entitiies");
        }

        out.println("(!) This page was automatically generated on " + new Date());
        out.println("DO NOT UPDATE MANUALLY!");
        out.println("");
        out.print("This page represents a formatted view of [" + dictFileName
                + "|https://test.kuali.org/svn/student/trunk/ks-lum/ks-lum-impl/src/main/resources/"
                + dictFileName + "]");
        out.println(
                " and is compared to the following java classes (and their sub-classes) for discrepancies:");
        for (String className : startingClasses) {
            out.println("# " + className);
        }
        out.println("");
        out.println("----");
        out.println("{toc}");
        out.println("----");
        for (String className : classesToProcess) {
//   System.out.println ("processing class " + clazz.getSimpleName ());
            doTestOnClass(className, ac);
        }
        out.close();
        return new ArrayList();
    }

    private Set<String> getComplexSubStructures(String className) {
        return new ComplexSubstructuresHelper().getComplexStructures(className);
    }

    private void doTestOnClass(String className, ApplicationContext ac) {
        DataDictionaryObjectStructure os = os = objectStructures.get(className);
        String simpleName = calcSimpleName(className);
        if (os == null) {

            out.println("h1. " + simpleName);
            out.println("{anchor:" + simpleName + "}");
            out.println("h2. Error could not find a corresponding dictionary definition");
            return;
        }
        DictionaryFormatter formatter =
                new DictionaryFormatter(className,
                className,
                os,
                new HashSet(),
                1, // header level to start at
                this.processSubstructures);
        out.println(formatter.formatForWiki());
    }

    private String calcSimpleName(String name) {
        if (name.lastIndexOf(".") != -1) {
            name = name.substring(name.lastIndexOf(".") + 1);
        }
        return name;
    }
}
