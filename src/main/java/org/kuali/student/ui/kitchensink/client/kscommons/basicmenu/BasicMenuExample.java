package org.kuali.student.ui.kitchensink.client.kscommons.basicmenu;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BasicMenuExample extends Composite {
    
    private VerticalPanel main = new VerticalPanel();

    public BasicMenuExample() {
        
        final ClickHandler handler = new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Does Something");
            }
        };

        final List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();
        KSMenuItemData proposalInfo = new KSMenuItemData("Proposal Information");
        proposalInfo.addSubItem(new KSMenuItemData("Author + Collaborators"){{
            setClickHandler(handler);
        }});
        final KSMenuItemData governance = new KSMenuItemData("Governance"){{
            setClickHandler(handler);
        }};
        proposalInfo.addSubItem(governance);
        proposalInfo.addSubItem(new KSMenuItemData("Course Format"){{
            setClickHandler(handler);
        }});
        KSMenuItemData academicContent = new KSMenuItemData("Academic Content");
        academicContent.addSubItem(new KSMenuItemData("Course Information"){{
            setClickHandler(handler);
        }});
        academicContent.addSubItem(new KSMenuItemData("Learning Objectives"){{
            setClickHandler(handler);
        }});
        final KSMenuItemData syllabus = new KSMenuItemData("Syllabus"){{
            setClickHandler(handler);
        }};
        syllabus.addSubItem(new KSMenuItemData("Syllabus Stuff 1"){{
            setClickHandler(handler);
        }});
        syllabus.addSubItem(new KSMenuItemData("Syllabus Stuff 2"){{
            setClickHandler(handler);
        }});
        academicContent.addSubItem(syllabus);
        academicContent.addSubItem(new KSMenuItemData("Learning Results"){{
            setClickHandler(handler);
        }});
        
        items.add(proposalInfo);
        items.add(academicContent);
        
        KSBasicMenu menu = new KSBasicMenu();
        menu.setItems(items);
        menu.setTitle("Proposal Sections");
        menu.setDescription("complete sections to submit");
        main.add(menu);
        super.initWidget(main);
    }

}
