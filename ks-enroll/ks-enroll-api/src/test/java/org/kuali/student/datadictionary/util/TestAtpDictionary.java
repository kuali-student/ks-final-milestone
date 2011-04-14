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

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.atp.dto.MilestoneInfo;
import static org.junit.Assert.*;

@Ignore
public class TestAtpDictionary {

    @Test
    public void testAtpDictionary() {
        System.out.println("testing ks-atp-dictionary dictionary");
        String projectUrl = "https://test.kuali.org/svn/student/sandbox/ks-r2-poc/trunk/ks-services/ks-services-api/src/main/resources";
        String className = AtpInfo.class.getName();
        String contextFile = "ks-atp-dictionary";
        String outFile = "target/" + contextFile + ".html";
        DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
                className, projectUrl, contextFile + ".xml");
        List<String> errors = helper.doTest();
        if (errors.size() > 0) {
            fail("failed dictionary validation:\n" + formatAsString(errors));
        }
    }

        @Test
    public void testMilestoneDictionary() {
        System.out.println("testing ks-milestone-dictionary dictionary");
        String projectUrl = "https://test.kuali.org/svn/student/sandbox/ks-r2-poc/trunk/ks-services/ks-services-api/src/main/resources";
        String className = MilestoneInfo.class.getName();
        String contextFile = "ks-milestone-dictionary";
        String outFile = "target/" + contextFile + ".html";
        DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
                className, projectUrl, contextFile + ".xml");
        List<String> errors = helper.doTest();
        if (errors.size() > 0) {
            fail("failed dictionary validation:\n" + formatAsString(errors));
        }
    }

    private String formatAsString(List<String> errors) {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        for (String error : errors) {
            i++;
            builder.append(i + ". " + error + "\n");
        }
        return builder.toString();
    }
}
