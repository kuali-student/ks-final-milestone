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
package org.kuali.student.datadictionary;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.Test;
import org.kuali.student.datadictinoary.DictionaryTesterHelper;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import static org.junit.Assert.*;

public class TestLuiPersonRelationDictionary {

    @Test
    public void testLuiPersonRelationDictionary() {
        System.out.println("testing ks-lui-person-relation-dictionary dictionary");
//        Set<String> startingClasses = new LinkedHashSet();
//        startingClasses.add(LuiPersonRelationInfo.class.getName());
//        String contextFile = "ks-lui-person-relation-dictionary";
//        String outFile = "target/" + contextFile + ".txt";
//        DictionaryTesterHelper helper = new DictionaryTesterHelper(outFile,
//                startingClasses, contextFile + ".xml", false);
//        List<String> errors = helper.doTest();
//        if (errors.size() > 0) {
//            fail("failed dictionary validation:\n" + formatAsString(errors));
//        }
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
