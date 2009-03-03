package org.kuali.student.ui.kitchensink.client.kscommons.accordionmenu;

import static org.kuali.student.ui.kitchensink.client.KitchenSinkStyleConstants.STYLE_EXAMPLE;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSCheckBox;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class AccordionMenuExample extends Composite {

    private static final String NA = "North America";
    private static final String ROW= "Rest of World";
    private static final String CAN= "Canada";
    private static final String USA= "USA";
    private static final String EUR= "Europe";
    private static final String ASI= "Asia";
    private static final String AFR= "Africa";

    private static final String SEPARATOR = " ; ";

    private final VerticalPanel main = new VerticalPanel();
    private final HorizontalPanel examplePanel = new HorizontalPanel();
    private final SimplePanel leftPanel = new SimplePanel();
    private final SimplePanel rightPanel = new SimplePanel();   
    private final SimplePanel buttonPanel = new SimplePanel();

    private  VerticalPanel canPanel = new VerticalPanel();
    private  VerticalPanel usPanel  =  new VerticalPanel();
    private  VerticalPanel eurPanel =  new VerticalPanel();
    private  VerticalPanel asiPanel =  new VerticalPanel() ;
    private  VerticalPanel afrPanel =  new VerticalPanel() ;

    private KSButton submitButton ;
    private final KSLabel label = new KSLabel("Click on a menu item below to show available choices on the right: ", false);   
    private final KSAccordionMenu menu = new KSAccordionMenu();

    private List<KSMenuItemData> items = new ArrayList<KSMenuItemData>();

    private List<KSCheckBox> canList = new ArrayList<KSCheckBox>();
    private List<KSCheckBox> usList = new ArrayList<KSCheckBox>();
    private List<KSCheckBox> afrList = new ArrayList<KSCheckBox>();
    private List<KSCheckBox> asiList = new ArrayList<KSCheckBox>();
    private List<KSCheckBox> eurList = new ArrayList<KSCheckBox>();


    public AccordionMenuExample() {

        main.addStyleName(STYLE_EXAMPLE);

        buildMenu();
        buildCheckboxes();
        submitButton = new KSButton("Submit", new MyClickHandler() );
        buttonPanel.add(submitButton);

        leftPanel.add(menu);

        examplePanel.add(leftPanel);
        examplePanel.add(rightPanel);

        main.add(label);
        main.add(examplePanel);
        main.add(buttonPanel);

        super.initWidget(main);


    }


    private void buildMenu() {
        KSMenuItemData menu1 = new KSMenuItemData(NA);

        KSMenuItemData menu11 = new KSMenuItemData(CAN);
        KSMenuItemData menu12 = new KSMenuItemData(USA);
        menu1.addSubItem(menu11);
        menu1.addSubItem(menu12);

        KSMenuItemData menu2 = new KSMenuItemData(ROW);

        KSMenuItemData menu21 = new KSMenuItemData(AFR);
        KSMenuItemData menu22 = new KSMenuItemData(ASI);
        KSMenuItemData menu23 = new KSMenuItemData(EUR);
        menu2.addSubItem(menu21);
        menu2.addSubItem(menu22);
        menu2.addSubItem(menu23);

        menu1.setClickHandler(new MyClickHandler());
        menu11.setClickHandler(new MyClickHandler());
        menu12.setClickHandler(new MyClickHandler());
        menu2.setClickHandler(new MyClickHandler());
        menu21.setClickHandler(new MyClickHandler());
        menu22.setClickHandler(new MyClickHandler());
        menu23.setClickHandler(new MyClickHandler());

        items.add(menu1);
        items.add(menu2);

        menu.setItems(items);
    }



    private void buildCheckboxes() {

        canList.add(new KSCheckBox("University of British Columbia"));
        canList.add(new KSCheckBox("University of Toronto"));
        canList.add(new KSCheckBox("McGill University"));
        for ( KSCheckBox box : canList) {
            canPanel.add(box);
        }

        usList.add(new KSCheckBox("Florida State University"));
        usList.add(new KSCheckBox("University of Maryland, College Park"));
        usList.add(new KSCheckBox("University of Washington"));
        usList.add(new KSCheckBox("University of California, Berkeley"));
        usList.add(new KSCheckBox("Massachusetts Institute of Technology"));
        usList.add(new KSCheckBox("University of Southern California"));
        usList.add(new KSCheckBox("Carnegie Mellon University"));
        usList.add(new KSCheckBox("San Joaquin Delta College"));
        for ( KSCheckBox box : usList) {
            usPanel.add(box);
        }
        
        eurList.add(new KSCheckBox("University of Cambridge, UK"));
        eurList.add(new KSCheckBox("Paris-Sorbonne University, France"));
        for ( KSCheckBox box : eurList) {
            eurPanel.add(box);
        }     
        
        asiList.add(new KSCheckBox("University of Delhi, India"));
        for ( KSCheckBox box : asiList) {
            asiPanel.add(box);
        }    
        
        afrList.add(new KSCheckBox("University of Johannesburg, South Africa"));
        afrList.add(new KSCheckBox("Makarere University, Uganda"));
        for ( KSCheckBox box : afrList) {
            afrPanel.add(box);
        }
    }


    public class MyClickHandler implements ClickHandler  {

        @Override
        public void onClick(ClickEvent event) {

            if (event.getSource() instanceof FocusPanel) {
                handleMenu(event);          
            }
            else if (event.getSource() instanceof Button) {
                handleSubmit(event);
            }
        }

        private void handleSubmit(ClickEvent event) {
            StringBuffer sb = new StringBuffer("You selected: ");
            for (KSCheckBox b : canList) {
                if (b.getValue()) {
                    sb.append(b.getText());
                    sb.append(SEPARATOR);
                }
            }
            for (KSCheckBox b : usList) {
                if (b.getValue()) {
                    sb.append(b.getText());
                    sb.append(SEPARATOR);
                }
            }
            for (KSCheckBox b : eurList) {
                if (b.getValue()) {
                    sb.append(b.getText());
                    sb.append(SEPARATOR);
                }
            }
            for (KSCheckBox b : asiList) {
                if (b.getValue()) {
                    sb.append(b.getText());
                    sb.append(SEPARATOR);
                }
            }
            for (KSCheckBox b : afrList) {
                if (b.getValue()) {
                    sb.append(b.getText());
                    sb.append(SEPARATOR);
                }
            }
            Window.alert(sb.toString().trim());

        }

        private void handleMenu(ClickEvent event) {
            //TODO Probably not the best way to do this? 
            FocusPanel panel = (FocusPanel)event.getSource();
            String source = ((Label)panel.getWidget()).getText();

            if (source.equals(NA)) {
                rightPanel.clear();
            }
            else if (source.equals(ROW)) {
                rightPanel.clear();
            }
            else if (source.equals(CAN)) {

                rightPanel.clear();
                rightPanel.add(canPanel);                
            }
            else if (source.equals(USA)) {
                rightPanel.clear();
                rightPanel.add(usPanel);                
            }
            else if (source.equals(EUR)) {
                rightPanel.clear();
                rightPanel.add(eurPanel);              
            }
            else if (source.equals(ASI)) {
                rightPanel.clear();
                rightPanel.add(asiPanel);              
            }
            else if (source.equals(AFR)) {
                rightPanel.clear();
                rightPanel.add(afrPanel);              
            }
            else {
                rightPanel.clear();
                Window.alert("Unknown");

            }
        }     
    }
}
