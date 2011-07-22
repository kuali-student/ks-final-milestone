package org.kuali.student.enrollment.dictionary.util;

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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.kuali.student.r2.common.datadictionary.util.KSDictionaryDocMojo;

public class TestKradXmlFiles {

    public List<String> getInputFiles() {
        List<String> inputFiles = new ArrayList<String>();
        inputFiles.add("AcademicCalendarInfoUI.xml");
        inputFiles.add("KeyDateInfoUI.xml");
        inputFiles.add("TermInfoUI.xml");
        // these ones that are commented have validation problems.
        // they do not define a data type for their attributes!
//        inputFiles.add("HoldInfo.xml");
//        inputFiles.add("IssueInfo.xml");
//        inputFiles.add("TermInfo.xml");          
        return inputFiles;
    }

    @Test
    public void testDictionaries() {
        System.out.println("testing dictionary files");
        String projectUrl = "https://test.kuali.org/svn/student/branches/ks-1.3/ks-enroll/ks-enroll-ui/src/main/resources/";
        KSDictionaryDocMojo mojo = new KSDictionaryDocMojo();
        mojo.setHtmlDirectory(new File("target/site/services/dictionarydocs"));
        mojo.setProjectUrl(projectUrl);
        mojo.setInputFiles(this.getInputFiles());
        List<String> supportFiles = new ArrayList ();
        supportFiles.add ("classpath:org/kuali/rice/kns/bo/datadictionary/DataDictionaryBaseTypes.xml");
        supportFiles.add ("classpath:org/kuali/rice/kns/bo/datadictionary/AttributeReferenceDummy.xml");        
        supportFiles.add ("classpath:org/kuali/rice/kns/uif/UifControlDefinitions.xml");        
        supportFiles.add ("classpath:org/kuali/rice/kns/uif/UifMiscDefinitions.xml");          
        supportFiles.add ("classpath:org/kuali/rice/kns/uif/UifFieldDefinitions.xml");
        supportFiles.add ("classpath:org/kuali/rice/kns/uif/UifGroupDefinitions.xml");
        supportFiles.add ("classpath:org/kuali/rice/kns/uif/UifHeaderFooterDefinitions.xml");
        supportFiles.add ("classpath:org/kuali/rice/kns/uif/UifLayoutManagerDefinitions.xml");        
        supportFiles.add ("classpath:org/kuali/rice/kns/uif/UifViewPageDefinitions.xml");          
        mojo.setSupportFiles(supportFiles);
        mojo.execute();
    }
}
