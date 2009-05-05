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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.configurable.ConfigurableField;
import org.kuali.student.common.ui.client.configurable.PropertyBinding;
import org.kuali.student.common.ui.client.dto.HelpInfo;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.forms.KSFormField;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.validator.Validator;
import org.kuali.student.core.atp.dto.AtpInfo;
import org.kuali.student.core.dictionary.dto.FieldDescriptor;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.ui.course.client.configuration.DefaultCreateUpdateLayout;
import org.kuali.student.lum.lu.ui.course.client.configuration.LUConstants;
import org.kuali.student.lum.lu.ui.course.client.configuration.SimpleConfigurableSection;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.SampleDataHelper.CampusLocation;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.SampleDataHelper.CurriculumOversight;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.SampleDataHelper.LuDuration;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.SampleDataHelper.Organization;
import org.kuali.student.lum.lu.ui.course.client.configuration.sectionmanager.SampleDataHelper.Person;

/**
 * This is a description of what this class does - hjohnson don't forget to fill this in. 
 * 
 * @author Kuali Student Team (kuali-student@googlegroups.com)
 *
 */
public class ProposalInformationLayoutManager {

    private DefaultCreateUpdateLayout<CluInfo> layout;
    private Map<String, FieldDescriptor> fields;
    private Validator validator;

    //Author and Collaborator lists
    private ListItems originatorList ;
    private ListItems delegatorList ;

    //Governance lists
    private ListItems curriculumOversightList ;
    private ListItems campusLocationList ;
    private ListItems organizationList ;

    //Course Format lists
    private ListItems atpList ;
    private ListItems durationList ;

    public ProposalInformationLayoutManager() {
        super();
        loadData();
    }

    public ProposalInformationLayoutManager(DefaultCreateUpdateLayout<CluInfo> layout,
            Map<String, FieldDescriptor> fields, Validator validator) {
        super();
        loadData();
        this.layout = layout;
        this.fields = fields;
        this.validator = validator;
    }

    public DefaultCreateUpdateLayout<CluInfo> addSection(String type, String state) {

        addAuthorAndCollaboratorsSection();
        addGovernanceSection();
        addCourseFormatSection();       

        return layout;
    }

    private void addAuthorAndCollaboratorsSection() {

        KSDropDown originatorDropDown = new KSDropDown();
        originatorDropDown.setListItems(originatorList);

        KSDropDown delegatorDropDown = new KSDropDown();
        delegatorDropDown.setListItems(delegatorList);

        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION, 
                LUConstants.SECTION_AUTHORS_AND_COLLABORATORS}, 
                new SimpleConfigurableSection<CluInfo>()
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // TODO figure out how to get the originating faculty member
                                return "Bob";
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                // TODO figure out which field is the originating faculty member
                            }
                        })
                        .setFormField(new KSFormField("originatingFacultyMember", "Originating Faculty Member")
                        .setWidget(originatorDropDown)
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("originatingFacultyMember")))
                        )
                )
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // TODO figure out how to get the administrative delegate
                                return "Jim";
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                // TODO figure out which field is the administrative delegate
                            }
                        })
                        .setFormField(new KSFormField("administrativeDelegate", "Administrative Delegate")
                        .setWidget(delegatorDropDown)
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("administrativeDelegate")))
                        )
                )
                .setSectionTitle(LUConstants.SECTION_AUTHORS_AND_COLLABORATORS)
                .setInstructions("Instructions go here...")
                .setParentLayout(layout)

        );
    }

    private void addGovernanceSection() {

        KSCheckBoxList curriculumCheckBox = new KSCheckBoxList();
        curriculumCheckBox.setMultipleSelect(false);
        curriculumCheckBox.setListItems(curriculumOversightList);

        KSDropDown locationDropDown = new KSDropDown();
        locationDropDown.setListItems(campusLocationList);

        KSDropDown organizationDropDown = new KSDropDown();
        organizationDropDown.setListItems(organizationList);

        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION, 
                LUConstants.SECTION_GOVERNANCE}, 
                new SimpleConfigurableSection<CluInfo>()
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // TODO figure out how to get the curriculum oversight
                                return "graduate";
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                // TODO figure out which field is the curriculum oversight
                            }
                        })
                        .setFormField(new KSFormField("curriculumOversight", "Curriculum Oversight")
                        .setWidget(curriculumCheckBox)
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("curriculumOversight")))
                        )
                )
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // TODO figure out how to get values
                                return "South campus";
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                // TODO figure out how to set value
                            }
                        })
                        .setFormField(new KSFormField("campusLocation", "Campus Location")
                        // Wireframe shows this as a group of checkboxes
                        .setWidget(locationDropDown)
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("curriculumOversight")))
                        )
                )                
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // finally, a field with an obvious cluinfo mapping
                                return object.getAdminOrg();
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                object.setAdminOrg(value.toString());
                            }
                        })
                        .setFormField(new KSFormField("adminOrg", "Administering Organization")
                        .setWidget(organizationDropDown)
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("adminOrg")))
                        )
                )
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // TODO figure out how to get values
                                return new Boolean(false);
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                // TODO figure out how to set values
                            }
                        })
                        .setFormField(new KSFormField("crossListed", "Cross Listed")
                        .setWidget(new KSCheckBox())
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("adminOrg")))
                        )
                )                
                .setSectionTitle(LUConstants.SECTION_GOVERNANCE)
                .setInstructions("Instructions go here...")
                .setParentLayout(layout)      
        );
    }

    private void addCourseFormatSection() {

        KSDropDown atpDropDown = new KSDropDown();
        atpDropDown.setListItems(atpList);

        KSDropDown durationDropDown = new KSDropDown();
        durationDropDown.setListItems(durationList);



        layout.addSection(new String[] {LUConstants.SECTION_PROPOSAL_INFORMATION, 
                LUConstants.SECTION_COURSE_FORMAT}, 
                new SimpleConfigurableSection<CluInfo>()
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // TODO figure out how to get values
                                return "Summer";
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                // TODO figure out how to set values
                            }
                        })
                        .setFormField(new KSFormField("term", "Term")
                        .setWidget(atpDropDown)
                        .setHelpInfo(new HelpInfo("helpid"))
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("adminOrg")))
                        )
                )
                .addField(new ConfigurableField<CluInfo>()
                        .setBinding(new PropertyBinding<CluInfo>() {
                            @Override
                            public Object getValue(CluInfo object) {
                                // TODO figure out how to get values
                                return "13 week";
                            }
                            @Override
                            public void setValue(CluInfo object, Object value) {
                                // TODO figure out how to set values
//                              object.setStdDuration();

                            }
                        })
                        .setFormField(new KSFormField("duration", "Duration")
                        .setWidget(durationDropDown)
                        .setHelpInfo(new HelpInfo("helpid")
                        )
//                      .addConstraint(new DictionaryConstraint(validator, fields.get("adminOrg")))
                        )
                )

                .setSectionTitle(LUConstants.SECTION_COURSE_FORMAT)
                .setInstructions("Instructions go here...")
                .setParentLayout(layout)  
        );
    }

    private void loadData() {
        loadOriginators();
        loadDelegators();

        loadCurriculumOversights();
        loadCampusLocations();
        loadOrganizations();

        loadAtps();
        loadDurations();
    }

    private void loadOriginators() {
        final List<Person> namesList = SampleDataHelper.getOriginators();

        originatorList = new ListItems() {

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

    }

    private void loadDelegators() {

        final List<Person> namesList = SampleDataHelper.getDelegators();

        delegatorList = new ListItems() {

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

    }

    private void loadCurriculumOversights() {

        final List<CurriculumOversight> oversightList = SampleDataHelper.getCurriculumOversights();

        curriculumOversightList = new ListItems() {

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

    }
    private void loadCampusLocations() {

        final List<CampusLocation> locationList = SampleDataHelper.getCampusLocations();

        campusLocationList = new ListItems() {

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

    }

    private void loadOrganizations() {


        List<Organization> orgList = SampleDataHelper.getOrganizations();


        final Map<String,String> map = new LinkedHashMap<String, String>();
        for(Organization org : orgList) {
            map.put(org.getId(), org.getName());
        }
        organizationList = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                return null; 
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                return null; 
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
//      }
//      });
    }

    private void loadAtps() {


        List<AtpInfo> atpInfoList = SampleDataHelper.getAtps();


        final Map<String,String> map = new LinkedHashMap<String, String>();
        for(AtpInfo info : atpInfoList) {
            map.put(info.getId(), info.getName());
        }
        atpList = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                return null; 
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                return null; 
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
//      }
//      });
    }

    private void loadDurations() {


        final List<LuDuration> durations = SampleDataHelper.getDurations();

        durationList = new ListItems() {

            @Override
            public List<String> getAttrKeys() {
                List<String> attributes = new ArrayList<String>();
                attributes.add("Name");
                return attributes;
            }

            @Override
            public String getItemAttribute(String id, String attrkey) {
                String value = null;
                for(LuDuration d : durations){
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
                return durations.size();
            }

            @Override
            public List<String> getItemIds() {
                List<String> ids = new ArrayList<String>();
                for(LuDuration d: durations){
                    ids.add(d.getId());
                }
                return ids;     
            }

            @Override
            public String getItemText(String id) {
                String value = null;
                for(LuDuration d: durations){
                    if(d.getId().equals(id)){
                        value = d.getName();
                        break;
                    }
                }
                return value;
            }
        };

    }


}
