/*
 * Copyright 2007 The Kuali Foundation
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
package org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.person.dto.PersonInfo;
import org.kuali.student.core.person.dto.PersonNameInfo;


/**
 * This is a temp class used for demos of the Configurable Layout Managers.
 * 
 *  TODO: replace with appropriate service calls 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public  class SampleDataHelper {

    protected static List<Person> getOriginators() {
        
        SampleDataHelper x = new SampleDataHelper();
        
        Person person1 = x.new Person("9988", "Joe Bloggs");
        Person person2 = x.new Person("955988", "Janet Biggs");

       
        List<Person> personList = new ArrayList<Person>(); 
        personList.add(person1);
        personList.add(person2);
        return personList;

    }
    
    protected static List<Person> getDelegators() {
        
        SampleDataHelper x = new SampleDataHelper();
        
        Person person1 = x.new Person("56gfh", "Anne Smith");
        Person person2 = x.new Person("95ghdg8", "Alan Scott");

       
        List<Person> personList = new ArrayList<Person>(); 
        personList.add(person1);
        personList.add(person2);
        return personList;

    }
    
    protected static List<CurriculumOversight> getCurriculumOversights() {
        //TODO This will be an RPC call but for now just hard code it

        SampleDataHelper x = new SampleDataHelper();

        CurriculumOversight oversight1 = x.new CurriculumOversight("2", "Undergraduate");
        CurriculumOversight oversight2 = x.new CurriculumOversight("29", "Graduate");
        CurriculumOversight oversight3 = x.new CurriculumOversight("82", "Extended Studies");
        List<CurriculumOversight> list = new ArrayList<CurriculumOversight>();
        list.add(oversight1);
        list.add(oversight2);
        list.add(oversight3);

        return list;


    }

    protected static List<Organization> getOrganizations() {
        //TODO This will be an RPC call but for now just hard code it
        SampleDataHelper x = new SampleDataHelper();

        Organization org1 = x.new Organization("9933","Faculty of Arts" );
        Organization org2 = x.new Organization("577", "Department of Chemistry");

        List<Organization> orgList = new ArrayList<Organization>(); 
        orgList.add(org1);
        orgList.add(org2);
        return orgList;


    }


    protected static List<CampusLocation> getCampusLocations() {
        //TODO This will be an RPC call but for now just hard code it
        SampleDataHelper x = new SampleDataHelper();

        CampusLocation location1 = x.new CampusLocation("2", "North Campus");
        CampusLocation location2 = x.new CampusLocation("29", "South Campus");
        CampusLocation location3 = x.new CampusLocation("82", "Singleton Park Campus");
        List<CampusLocation> list = new ArrayList<CampusLocation>();
        list.add(location1);
        list.add(location2);
        list.add(location3);

        return list;
    }

    protected static List<AtpInfo> getAtps() {
        //TODO This will be an RPC call but for now just hard code it

//      TODO What dates to choose?
//      Calendar startCal = Calendar.getInstance();
//      startCal.set(2009, 1, 1);

//      Calendar endCal = Calendar.getInstance();
//      endCal.set(2011, 1, 1);

//      AtpRpcService.Util.getInstance().getAtpsByDates(startCal.getTime(), endCal.getTime(), new AsyncCallback<List<OrgPersonRelationTypeInfo>>(){
//      public void onFailure(Throwable caught) {
//      Window.alert(caught.getMessage());
//      }

//      public void onSuccess(List<AtpInfo> atpInfoList) {

        AtpInfo atpInfo1 = new AtpInfo();
        atpInfo1.setId("43");
        atpInfo1.setDesc(new RichTextInfo());
        atpInfo1.getDesc().setFormatted("Atp for Fall 2008 semester");
        atpInfo1.getDesc().setPlain("Atp for Fall 2008 semester");
        atpInfo1.setName("Fall 2008 Semester");
        atpInfo1.setEffectiveDate(new Date());
        atpInfo1.setExpirationDate(new Date());
        atpInfo1.setState("new");


        AtpInfo atpInfo2 = new AtpInfo();
        atpInfo2.setId("9543");
        atpInfo2.setDesc(new RichTextInfo());
        atpInfo2.getDesc().setFormatted("Atp for Summer 2009 semester");
        atpInfo2.getDesc().setPlain("Atp for Summer 2009 semester");
        atpInfo2.setName("Spring 2009 Semester");
        atpInfo2.setEffectiveDate(new Date());
        atpInfo2.setExpirationDate(new Date());
        atpInfo2.setState("new");

        List<AtpInfo> atpInfoList = new ArrayList<AtpInfo>(); 
        atpInfoList.add(atpInfo1);
        atpInfoList.add(atpInfo2);
        return atpInfoList;
    }

    protected static List<LuDuration> getDurations() {

        //TODO This will be an RPC call but for now just hard code it

        SampleDataHelper x = new SampleDataHelper();

        LuDuration duration1 = x.new LuDuration("2", "1 day");
        LuDuration duration2 = x.new LuDuration("29", "1 week");
        LuDuration duration3 = x.new LuDuration("82", "5 weeks");
        LuDuration duration4 = x.new LuDuration("65", "10 weeks");
        LuDuration duration5 = x.new LuDuration("43", "15 weeks");
        LuDuration duration6 = x.new LuDuration("872", "25 weeks");
        List<LuDuration> list = new ArrayList<LuDuration>();
        list.add(duration1);
        list.add(duration2);
        list.add(duration3);
        list.add(duration4);
        list.add(duration5);
        list.add(duration6);

        return list;




    }

    //TODO Use real CampusLocation once its created
    protected class CampusLocation  {
        private String id;
        private String name;

        public CampusLocation(String id, String name) {
            super();
            this.name = name;
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    //TODO Use real LuDuration once its created
    protected class LuDuration  {
        private String id;
        private String name;

        public LuDuration(String id, String name) {
            super();
            this.name = name;
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

    //TODO Use real CurriculumOversight once its created
    protected class CurriculumOversight  {
        private String id;
        private String name;

        public CurriculumOversight(String id, String name) {
            super();
            this.name = name;
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    
    //TODO Use real OrgInfo
    protected class Organization  {
        private String id;
        private String name;

        public Organization(String id, String name) {
            super();
            this.name = name;
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    
    //TODO Use real PersonInfo
    protected class Person  {
        private String id;
        private String name;

        public Person(String id, String name) {
            super();
            this.name = name;
            this.id = id;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}
