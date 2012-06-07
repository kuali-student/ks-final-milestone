/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by Daniel on 6/7/12
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.class2.courseoffering.service.CourseOfferingCodeGenerator;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;

import java.util.*;

/**
 * This class //TODO ...
 *
 * @author Kuali Student Team
 */
public class CourseOfferingCodeGeneratorImpl implements CourseOfferingCodeGenerator {


    @Override
    public String generateActivityOfferingCode(List<ActivityOfferingInfo> existingActivityOfferings) {

        //If this is the first code, send back "A"
        if(existingActivityOfferings==null||existingActivityOfferings.isEmpty()){
            return "A";
        }

        List<String> aoCodes = new ArrayList<String>();
        for(ActivityOfferingInfo aoInfo:existingActivityOfferings){
            aoCodes.add(aoInfo.getActivityCode());
        }

        //Always start with A if it's not there
        if(!aoCodes.contains("A")){
            return "A";
        }

        //Sort the list so we can fill in gaps
        Collections.sort(aoCodes,new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if(o1.length() == o2.length()){
                    return o1.compareTo(o2);
                }else{
                    return  o1.length() - o2.length();
                }
            }
        });

        //Fill in the gaps of letters
        for(String s:aoCodes){
            //For each existing code, find the next valid generated code and see if it exists
            String nextCode = getNextCode(s);
            if(!aoCodes.contains(nextCode)){
                return nextCode;
            }
        }

        //This should never be reached unless there is an infinite list of strings passed in
        throw new RuntimeException("Error generating codes");
    }

    //Gets the next letter of the alphabet in caps in the form A,B,C...Z,AA,AB,AC...AZ,AAA...
    private String getNextCode(String s){
        char lastLetter = s.charAt(s.length()-1);
        String beginningString = "";
        if ('Z' == lastLetter){
            //Add "A"s for the current length of the string
            for(int i=0;i<s.length();i++){
                beginningString += "A";
            }
            return beginningString + "A";
        }else{
            lastLetter++;
            if(s.length()>1){
                beginningString  = s.substring(0,s.length()-1);
            }
            return beginningString + lastLetter;
        }
    }

}
