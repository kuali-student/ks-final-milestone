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
import org.kuali.student.core.organization.dto.OrgInfo;
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

        PersonInfo person1 = new PersonInfo();
        PersonNameInfo name1 = new PersonNameInfo();
        name1.setGivenName("Joe");
        name1.setSurname("Bloggs");
        List<PersonNameInfo> namesList1 = new ArrayList<PersonNameInfo>();
        namesList1.add(name1);

        person1.setId("9988");
        person1.setPersonNameInfoList(namesList1);


        PersonInfo person2 = new PersonInfo();
        PersonNameInfo name2 = new PersonNameInfo();
        name2.setGivenName("Janet");
        name2.setSurname("Biggs");
        List<PersonNameInfo> namesList2 = new ArrayList<PersonNameInfo>();
        namesList2.add(name2);

        person2.setId("993488");
        person2.setPersonNameInfoList(namesList2);

        final List<PersonInfo> personList = new ArrayList<PersonInfo>(); 
        personList.add(person1);
        personList.add(person2);


        return buildPersonNameList(personList);

    }



    public static ListItems getDelegators() {

        PersonInfo person1 = new PersonInfo();
        PersonNameInfo name1 = new PersonNameInfo();
        name1.setGivenName("Anne");
        name1.setSurname("Smith");
        List<PersonNameInfo> namesList1 = new ArrayList<PersonNameInfo>();
        namesList1.add(name1);

        person1.setId("6fghh");
        person1.setPersonNameInfoList(namesList1);


        PersonInfo person2 = new PersonInfo();
        PersonNameInfo name2 = new PersonNameInfo();
        name2.setGivenName("Alan");
        name2.setSurname("Scott");
        List<PersonNameInfo> namesList2 = new ArrayList<PersonNameInfo>();
        namesList2.add(name2);

        person2.setId("dfgh7u67");
        person2.setPersonNameInfoList(namesList2);

        final List<PersonInfo> personList = new ArrayList<PersonInfo>(); 
        personList.add(person1);
        personList.add(person2);


        return buildPersonNameList(personList);

    }

    public static ListItems getCurriculumOversights() {

        CreditCourseDataManager x = new CreditCourseDataManager();

        CurriculumOversight oversight1 = x.new CurriculumOversight("2", "Undergraduate");
        CurriculumOversight oversight2 = x.new CurriculumOversight("29", "Graduate");
        CurriculumOversight oversight3 = x.new CurriculumOversight("82", "Extended Studies");

        final List<CurriculumOversight> result = new ArrayList<CurriculumOversight>();
        result.add(oversight1);
        result.add(oversight2);
        result.add(oversight3);

        final Map<String,CurriculumOversight> ids = new LinkedHashMap<String,CurriculumOversight>();
        for(CurriculumOversight o : result){
            ids.put(o.getId(),o);
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
                String value = null;
                CurriculumOversight c = ids.get(id);
                if(attrkey.equals("Name")){
                    value = c.getName();
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return result.size();
            }

            @Override
            public List<String> getItemIds() {
                return new ArrayList<String>(ids.keySet());   
            }

            @Override
            public String getItemText(String id) {
                CurriculumOversight c = ids.get(id);
                return c.getName();
            }
        };

        return list;
    }

    public static ListItems getOrganizations() {

        OrgInfo org1 = new OrgInfo();
        org1.setId("9933");
        org1.setShortName("Faculty of Arts" );

        OrgInfo org2 = new OrgInfo();
        org2.setId("577");
        org2.setShortName("Department of Chemistry");

        final List<OrgInfo> result = new ArrayList<OrgInfo>(); 
        result.add(org1);
        result.add(org2);

//      OrgRpcService.Util.getInstance().getOrgHierarchies(new AsyncCallback<List<OrgHierarchyInfo>>(){

//      public void onFailure(Throwable caught) {
//      Window.alert(caught.getMessage());
//      }

//      public void onSuccess(final List<OrgHierarchyInfo> result) {              
        final Map<String,OrgInfo> ids = new LinkedHashMap<String,OrgInfo>();
        for(OrgInfo o : result){
            ids.put(o.getId(),o);
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
                String value = null;
                OrgInfo o = ids.get(id);
                if(attrkey.equals("Name")){
                    value = o.getShortName();
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return result.size();
            }

            @Override
            public List<String> getItemIds() {
                return new ArrayList<String>(ids.keySet());

            }

            @Override
            public String getItemText(String id) {
                OrgInfo o = ids.get(id);
                return o.getShortName();
            }
        };
        //      }
        //      });    
        return list;

    }

    public static ListItems getCampusLocations() {

        CreditCourseDataManager x = new CreditCourseDataManager();

        CampusLocation location1 = x.new CampusLocation("2", "North Campus");
        CampusLocation location2 = x.new CampusLocation("29", "South Campus");
        CampusLocation location3 = x.new CampusLocation("82", "Singleton Park Campus");

        final List<CampusLocation> result = new ArrayList<CampusLocation>();
        result.add(location1);
        result.add(location2);
        result.add(location3);

        final Map<String,CampusLocation> ids = new LinkedHashMap<String,CampusLocation>();
        for(CampusLocation o : result){
            ids.put(o.getId(),o);
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
                String value = null;
                CampusLocation c = ids.get(id);
                if(attrkey.equals("Name")){
                    value = c.getName();
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return result.size();
            }

            @Override
            public List<String> getItemIds() {
                return new ArrayList<String>(ids.keySet());    
            }

            @Override
            public String getItemText(String id) {
                CampusLocation c = ids.get(id);
                return c.getName();
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

        List<AtpInfo> result = new ArrayList<AtpInfo>(); 
        result.add(atpInfo1);
        result.add(atpInfo2);

        final Map<String,AtpInfo> ids = new LinkedHashMap<String, AtpInfo>();
        for(AtpInfo info : result) {
            ids.put(info.getId(), info);
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
                String value = null;
                AtpInfo a = ids.get(id);
                if(attrkey.equals("Name")){
                    value = a.getName();
                }
                return value;
            }

            @Override
            public int getItemCount() {
                return ids.size();
            }

            @Override
            public List<String> getItemIds() {
                return new ArrayList<String>(ids.keySet());
            }

            @Override
            public String getItemText(String id) {
                AtpInfo a = ids.get(id);
                return a.getName();
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


    private static ListItems buildPersonNameList(final List<PersonInfo> personList) {


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
                for(PersonInfo p : personList){
                    PersonNameInfo n = p.getPersonNameInfoList().get(0); 
                    if(n.getId().equals(id)){
                        if(attrkey.equals("Name")){
                            value = n.getSurname() + ", " + n.getGivenName();
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
                for(PersonInfo p: personList){
                    ids.add(p.getId());
                }
                return ids; 
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(PersonInfo p: personList){
                    if(p.getId().equals(id)){
                        PersonNameInfo n = p.getPersonNameInfoList().get(0); 
                        value = n.getSurname() + ", " + n.getGivenName();
                        break;
                    }
                }
                return value;
            }
        };
        return originatorList;
    }
//  TODO Use real CampusLocation once its created
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

//  TODO Use real LuDuration once its created
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

//  TODO Use real CurriculumOversight once its created
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

}
