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
package org.kuali.student.lum.lu.ui.course.client.configuration.typemanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.widgets.list.ListItems;
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
public  class CreditCourseDataManager {

    //TODO These will all be RPC calls but for now just hard code them


    public static ListItems getOriginators() {

        CreditCourseDataManager x = new CreditCourseDataManager();

        Person person1 = x.new Person("9988", "Joe Bloggs");
        Person person2 = x.new Person("955988", "Janet Biggs");

        final List<Person> namesList = new ArrayList<Person>(); 
        namesList.add(person1);
        namesList.add(person2);


        ListItems originatorList = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(Person n : namesList){
                    if(n.getId().equals(id)){
                        if(attrkey.equals("Name")){
                            value = n.getName();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return namesList.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(Person n: namesList){
                    ids.add(n.getId());
                }
                return ids; 
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(Person n: namesList){
                    if(n.getId().equals(id)){
                        value = n.getName();
                        break;
                    }
                }
                return value;
            }
        };
        return originatorList;

    }

    public static ListItems getDelegators() {

        CreditCourseDataManager x = new CreditCourseDataManager();

        Person person1 = x.new Person("56gfh", "Anne Smith");
        Person person2 = x.new Person("95ghdg8", "Alan Scott");

        final List<Person> personList = new ArrayList<Person>(); 
        personList.add(person1);
        personList.add(person2);     
        
        ListItems list = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(Person n : personList){
                    if(n.getId().equals(id)){
                        if(attrkey.equals("Name")){
                            value = n.getName();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return personList.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(Person n: personList){
                    ids.add(n.getId());
                }
                return ids; 
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(Person n: personList){
                    if(n.getId().equals(id)){
                        value = n.getName();
                        break;
                    }
                }
                return value;
            }

        };
        return list;

    }

    public static ListItems getCurriculumOversights() {

        CreditCourseDataManager x = new CreditCourseDataManager();

        CurriculumOversight oversight1 = x.new CurriculumOversight("2", "Undergraduate");
        CurriculumOversight oversight2 = x.new CurriculumOversight("29", "Graduate");
        CurriculumOversight oversight3 = x.new CurriculumOversight("82", "Extended Studies");
        
        final List<CurriculumOversight> oversightList = new ArrayList<CurriculumOversight>();
        oversightList.add(oversight1);
        oversightList.add(oversight2);
        oversightList.add(oversight3);
        
        
        ListItems list = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(CurriculumOversight c : oversightList){
                    if(c.getId().equals(id)){
                        if(attrkey.equals("Name")){
                            value = c.getName();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return oversightList.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(CurriculumOversight c: oversightList){
                    ids.add(c.getId());
                }
                return ids;     
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(CurriculumOversight c: oversightList){
                    if(c.getId().equals(id)){
                        value = c.getName();
                        break;
                    }
                }
                return value;
            }
        };


        return list;


    }

    public static ListItems getOrganizations() {

        CreditCourseDataManager x = new CreditCourseDataManager();

        Organization org1 = x.new Organization("9933","Faculty of Arts" );
        Organization org2 = x.new Organization("577", "Department of Chemistry");

        final List<Organization> orgList = new ArrayList<Organization>(); 
        orgList.add(org1);
        orgList.add(org2);
        
        ListItems list = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(Organization o : orgList){
                    if(o.getId().equals(id)){
                        if(attrkey.equals("Name")){
                            value = o.getName();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return orgList.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(Organization o: orgList){
                    ids.add(o.getId());
                }
                return ids; 
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(Organization o: orgList){
                    if(o.getId().equals(id)){
                        value = o.getName();
                        break;
                    }
                }
                return value;
            }
        };
        return list;


    }


    public static ListItems getCampusLocations() {

        CreditCourseDataManager x = new CreditCourseDataManager();

        CampusLocation location1 = x.new CampusLocation("2", "North Campus");
        CampusLocation location2 = x.new CampusLocation("29", "South Campus");
        CampusLocation location3 = x.new CampusLocation("82", "Singleton Park Campus");
        
        final List<CampusLocation> locationList = new ArrayList<CampusLocation>();
        locationList.add(location1);
        locationList.add(location2);
        locationList.add(location3);
        
        ListItems list = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(CampusLocation c : locationList){
                    if(c.getId().equals(id)){
                        if(attrkey.equals("Name")){
                            value = c.getName();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return locationList.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(CampusLocation c: locationList){
                    ids.add(c.getId());
                }
                return ids;     
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(CampusLocation c: locationList){
                    if(c.getId().equals(id)){
                        value = c.getName();
                        break;
                    }
                }
                return value;
            }
        };

        return list;
    }

    public static ListItems getAtps() {
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
        
        final Map<String,String> map = new LinkedHashMap<String, String>();
        for(AtpInfo info : atpInfoList) {
            map.put(info.getId(), info.getName());
        }
        ListItems list = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                return map.get(id);
            }

            @Override
            public int getItemCount() {
                return map.size();
            }

            @Override
            public List<String> getItemIds() {
                return new ArrayList<String>(map.keySet());
            }

            @Override
            public String getItemText(String id) {
                return map.get(id);
            }
        };
        return list;
    }

    public static ListItems getDurations() {

        //TODO This will be an RPC call but for now just hard code it

        CreditCourseDataManager x = new CreditCourseDataManager();

        LuDuration duration1 = x.new LuDuration("2", "1 day");
        LuDuration duration2 = x.new LuDuration("29", "1 week");
        LuDuration duration3 = x.new LuDuration("82", "5 weeks");
        LuDuration duration4 = x.new LuDuration("65", "10 weeks");
        LuDuration duration5 = x.new LuDuration("43", "15 weeks");
        LuDuration duration6 = x.new LuDuration("872", "25 weeks");
        final List<LuDuration> durationList = new ArrayList<LuDuration>();
        durationList.add(duration1);
        durationList.add(duration2);
        durationList.add(duration3);
        durationList.add(duration4);
        durationList.add(duration5);
        durationList.add(duration6);
        
        ListItems list = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(LuDuration d : durationList){
                    if(d.getId().equals(id)){
                        if(attrkey.equals("Name")){
                            value = d.getName();
                        }
                        break;
                    }
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return durationList.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(LuDuration d: durationList){
                    ids.add(d.getId());
                }
                return ids;     
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(LuDuration d: durationList){
                    if(d.getId().equals(id)){
                        value = d.getName();
                        break;
                    }
                }
                return value;
            }
        };


        return list;
    }

    //TODO Use real CampusLocation once its created
    public class CampusLocation  {
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
    public class LuDuration  {
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
    public class CurriculumOversight  {
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
    public class Organization  {
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
    public class Person  {
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
