package org.kuali.student.common.ui.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.kuali.student.common.ui.client.css.KSCommonResources;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSDropDown;
import org.kuali.student.common.ui.client.widgets.KSListBox;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.list.KSCheckBoxList;
import org.kuali.student.common.ui.client.widgets.list.KSPickList;
import org.kuali.student.common.ui.client.widgets.list.KSRadioButtonList;
import org.kuali.student.common.ui.client.widgets.list.KSSelectItemWidgetAbstract;
import org.kuali.student.common.ui.client.widgets.list.KSSelectableTableList;
import org.kuali.student.common.ui.client.widgets.list.ModelListItems;
import org.kuali.student.common.ui.client.widgets.list.SelectionChangeHandler;
import org.kuali.student.common.ui.client.widgets.list.impl.KSSelectableTableListImpl;
import org.kuali.student.common.ui.client.widgets.list.testData.Color;
import org.kuali.student.common.ui.client.widgets.menus.KSAccordionMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSBasicMenu;
import org.kuali.student.common.ui.client.widgets.menus.KSMenuItemData;
import org.kuali.student.common.ui.client.widgets.menus.impl.KSBasicMenuImpl;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBox;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSListItemsSuggestOracle;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBoxPicker;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSSuggestBoxWAdvSearch;
import org.kuali.student.common.ui.client.widgets.suggestbox.KSAdvancedSearchWindow.SearchParameter;
import org.kuali.student.core.dto.Idable;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ResourcePrototype;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.Widget;

public class CommonUITest implements EntryPoint {
    FlowPanel flowpanel;
    FlexTable flextable;
    public StyleElement commonStyle;
    int pan;
    
    
    
    //private List<Color> colors = new ArrayList<Color>();
    private Model<Color> colors = new Model<Color>();
    
    private ModelListItems<Color> tableItems = new ModelListItems<Color>(){
        @Override
        public List<String> getAttrKeys() {
            List<String> attributes = new ArrayList<String>();
            attributes.add("Color");
            attributes.add("Warmth");
            attributes.add("Type");
            return attributes;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String value = null;
            for(Color c: colors.getValues()){
                if(c.getId().equals(id)){
                    if(attrkey.equals("Color")){
                        value = c.getColor();
                    }
                    else if(attrkey.equals("Warmth")){
                        value = c.getWarmth();
                    }
                    else if(attrkey.equals("Type")){
                        value = c.getType();
                    }
                    break;
                }
            }
            return value;
        }

        @Override
        public String getItemText(String id) {
            String value = null;
            for(Color c: colors.getValues()){
                if(c.getId().equals(id)){
                    value = c.getColor();
                    break;
                }
            }
            return value;
        }
    };

	public void onModuleLoad() {
/*
	    final ClickHandler handler = new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                Window.alert("Does Something!");
                
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
		
		//final KSAccordionMenu haha = new KSAccordionMenu();
		//haha.setItems(items);
		KSBasicMenu haha2 = new KSBasicMenu();
		haha2.setItems(items);
		haha2.setTitle("Proposal Sections");
		haha2.setDescription("complete sections to submit");
		
		RootPanel.get().add(haha2);
		final HTML html = new HTML();
		KSButton button0 = new KSButton("Refresh Item data");
		RootPanel.get().add(html);
		
        button0.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                html.setText(printItems(items, ""));
                
            }
            
            public String printItems(List<KSMenuItemData> theItems, String indent){
                String returnString = "";
                for(KSMenuItemData i: theItems){
                    returnString = returnString + indent + i.getLabel() + " S: " + i.isSelected() + " I: " + (i.getShownIcon() != null) + "\n";
                    if(!(i.getSubItems().isEmpty())){
                        returnString = returnString + printItems(i.getSubItems(), indent + "  ");
                    }
                }
                return returnString;
            }
        });
        RootPanel.get().add(button0);
		//RootPanel.get().add(haha2);
        haha2.selectMenuItem(new String[] {"Academic Content", "Syllabus"});
		KSButton button1 = new KSButton("Select Syllabus");
		button1.addClickHandler(new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                syllabus.setSelected(true);
                //haha.selectMenuItem(new String[] {"Academic Content", "Syllabus"});
            }});
		KSButton button2 = new KSButton("Select Governance");
          button2.addClickHandler(new ClickHandler(){
    
                @Override
                public void onClick(ClickEvent event) {
                   governance.setSelected(true);
                    
                }
          });
          KSButton button3 = new KSButton("Change Governance->GoverNANCE");
          button3.addClickHandler(new ClickHandler(){
    
                @Override
                public void onClick(ClickEvent event) {
                   governance.setLabel("GoverNANCE");
                    
                }
          });
          RootPanel.get().add(button1);
          RootPanel.get().add(button2);
          RootPanel.get().add(button3);
		
		items.add(mi3);
		KSAccordionMenu am = new KSAccordionMenu();
		am.setItems(items);
		RootPanel.get().add(am);
		
		KSAccordionPanel ap = new KSAccordionPanel();
		ap.addPanel("thing1", new KSDatePicker());
		ap.addPanel("thing2", ds);
		ap.addPanel("thing3", new KSTextArea());
		RootPanel.get().add(ap);
		Label label = new Label("Add");
		RootPanel.get().add(icons.okIcon().createImage());
		RootPanel.get().add(icons.okIcon().createImage());
		RootPanel.get().add(icons.okIcon().createImage());
		RootPanel.get().add(icons.okIcon().createImage());
		RootPanel.get().add(icons.okIcon().createImage());
		RootPanel.get().add(icons.errorIcon().createImage());
		RootPanel.get().add(icons.errorIcon().createImage());
		RootPanel.get().add(icons.errorIcon().createImage());
		RootPanel.get().add(icons.errorIcon().createImage());
		RootPanel.get().add(icons.errorIcon().createImage());
		RootPanel.get().add(icons.defaultIcon().createImage());
		RootPanel.get().add(icons.defaultIcon().createImage());
		RootPanel.get().add(icons.defaultIcon().createImage());
		RootPanel.get().add(icons.defaultIcon().createImage());
		RootPanel.get().add(icons.defaultIcon().createImage());
		RootPanel.get().add(new KSRichEditor());
		RootPanel.get().add(new KSRichEditor());
		RootPanel.get().add(new KSConfirmButtonPanel());
		
		KSConfirmationDialog hello = new KSConfirmationDialog();
		hello.setMessageTitle("Hello");
		hello.setWidget(new KSLabel ("Hello There and Welcome"));
		hello.show();
		KSSidebar sb = new KSSidebar(FloatLocation.FLOAT_RIGHT);
		//KSSidebar sb = new KSSidebar();
		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		vp.add(new Label("HAHAHAHAHA"));
		sb.addTab(vp, "Laughing");
		sb.addTab(new Label("BAAAAAWW BAAAAWWWW"), "Crying");
		sb.addTab(new Label("AAAAAHHHHHHH"), "Screaming");
		sb.show();
		*/
        //final KSDropDown list = new KSDropDown();
        //list.setBlankFirstItem(false);
        colors.add(new Color("1", "Blue", "Cool", "Primary"));
        colors.add(new Color("2", "Red", "Warm", "Primary"));
        colors.add(new Color("3", "Orange", "Warm", "Secondary"));
        colors.add(new Color("4", "Yellow", "Warm", "Primary"));
        colors.add(new Color("5", "Green", "Cool", "Secondary"));
        colors.add(new Color("6", "Purple", "Cool", "Secondary"));
        colors.add(new Color("7", "Black", "Neutral", "None"));
        tableItems.setModel(colors);
        tableItems.setComparator(new Comparator<Color>(){

            @Override
            public int compare(Color c1, Color c2) {
                return c1.getColor().compareToIgnoreCase(c2.getColor());
            }
        });
        
        KSListItemsSuggestOracle oracle = new KSListItemsSuggestOracle();
        oracle.setListItems(tableItems);
        //SuggestBox sb = new KSSuggestBox(oracle);
        //sb.setLimit(2);
        KSSelectableTableList stl = new KSSelectableTableList();
        KSSuggestBox sb = new KSSuggestBox(oracle);
        List<SearchParameter> params = new ArrayList<SearchParameter>();
        params.add(new SearchParameter("Color", "Color"));
        params.add(new SearchParameter("Warmth", "Warmth"));
        List<String> enumeratedValues = new ArrayList<String>();
        enumeratedValues.add("Primary");
        enumeratedValues.add("Secondary");
        enumeratedValues.add("None");
        params.add(new SearchParameter("Type", "Type", enumeratedValues));
        
        KSSuggestBoxWAdvSearch suggest = new KSSuggestBoxWAdvSearch(sb, new KSAdvancedSearchWindow(params));
        KSSuggestBoxPicker sbp = new KSSuggestBoxPicker(suggest, stl);
        RootPanel.get().add(sbp);
       /* list.setListItems(tableItems);
        list.addSelectionChangeHandler(new SelectionChangeHandler(){

            @Override
            public void onSelectionChange(KSSelectItemWidgetAbstract w) {
               List<String> selectedItems = w.getSelectedItems();
               String selectedString = "SELECTED COLOR: \n";
               
               if(selectedItems.size() > 0){
                   for(String s: selectedItems){
                       selectedString = selectedString + tableItems.getItemText(s) + "\n";
                   }
               }
               
               Window.alert(selectedString);
                
            }
        });
        
        RootPanel.get().add(new KSButton("Reverse Sort", new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                tableItems.setComparator(new Comparator<Color>(){

                    @Override
                    public int compare(Color c1, Color c2) {
                        return c1.getColor().compareToIgnoreCase(c2.getColor()) * -1;
                    }
                });
              //redraw goes here
                list.setListItems(tableItems);
            }
        }));
        
        RootPanel.get().add(new KSButton("Sort", new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                tableItems.setComparator(new Comparator<Color>(){

                    @Override
                    public int compare(Color c1, Color c2) {
                        return c1.getColor().compareToIgnoreCase(c2.getColor());
                    }
                });
                //redraw goes here
                list.setListItems(tableItems);
            }
        }));
        
        final KSTextBox tb = new KSTextBox();
        RootPanel.get().add(tb);
        RootPanel.get().add(new KSButton("Add Color", new ClickHandler(){
            
            @Override
            public void onClick(ClickEvent event) {
                colors.add(new Color("" + Random.nextInt(), tb.getText(), "Neutral", "None"));
            }
        }));
        
        RootPanel.get().add(new KSButton("Remove", new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                colors.remove("" + (Random.nextInt(6) + 1));
            }
        }));
        
        RootPanel.get().add(new KSButton("Update Green<->Pink", new ClickHandler(){

            @Override
            public void onClick(ClickEvent event) {
                Color theColor = colors.get("5");
                if(theColor.getColor().equals("Green")){
                    theColor.setColor("Pink");
                }
                else{
                    theColor.setColor("Green");
                }
                colors.update(theColor);
            }
        }));
        
        RootPanel.get().add(list);
        
        final KSListBox list2 = new KSListBox();
        list2.setListItems(tableItems);
        RootPanel.get().add(list2);
        
        final KSRadioButtonList list3 = new KSRadioButtonList("Name");
        list3.setColumnSize(4);
        list3.setListItems(tableItems);   
        RootPanel.get().add(list3);
        
        final KSCheckBoxList list4 = new KSCheckBoxList("Name2");
        list4.setColumnSize(4);
        list4.setListItems(tableItems);  
        RootPanel.get().add(list4);
        
        final KSSelectableTableList list5 = new KSSelectableTableList();
        list5.setListItems(tableItems);
        RootPanel.get().add(list5);
        
        final KSPickList list6 = new KSPickList();
        list6.setListItems(tableItems);
        RootPanel.get().add(list6);*/
        /*
        KSPickList newList = new KSPickList();
        newList.setListItems(tableItems);
        RootPanel.get().add(newList);
		//RootPanel.get().add(sb);
		
		
		
*/
	}
	
	public String getCssString(){
	       String injectString = "";
	        for(ResourcePrototype r: KSCommonResources.INSTANCE.getResources()){
	            if(r instanceof CssResource){
	                if(((CssResource)r).getText() != null){
	                    injectString = injectString + "\n" + (((CssResource)r).getText());
	                }
	            }
	        }
	        return injectString;
	        
	}
	void setup()
	{
	    flextable.clear();
	    
	    flextable.setWidget(3, 0, new Label("You lose"));
	    
	    //flowpanel.addStyleName("demo-panel");

	    widgetButton(0, 0, "100px", "100px", "yellow");
	    widgetButton(1, 0, "40px", "80px", "blue");
	    widgetButton(2, 0, "95px", "95px", "cyan");

	    flextable.setWidget(4, 0, new Button("Reset", new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
	            flowpanel.clear();
	            pan = 0;
	        }
	    }));
	    pan = 0;
	}
	void widgetButton(int row, int col, final String width,
	    final String height, final String colour)
	{
	    flextable.setWidget(row, col, new Button(
	        width + " x " + height + " " + colour, new ClickListener()
	    {
	        public void onClick(Widget sender)
	        {
	            Button label = new Button(++pan + "");
	            DOM.setStyleAttribute(label.getElement(),
	                "border", "1px solid #00f");
	            DOM.setStyleAttribute(label.getElement(),
	                "backgroundColor", colour);
	            label.setSize(width, height);
	            flowpanel.add(label);
	        }
	    }));
	   // flextable.getCellFormatter().setHorizontalAlignment(
	    //    row, col, HasAlignment.ALIGN_CENTER);
	}



	
	private void addTask(int i) {
/*		final BlockingTask t = new BlockingTask("Task " + i);
		KSBlockingProgressIndicator.addTask(t);
		Timer timer = new Timer() {
			public void run() {
				KSBlockingProgressIndicator.removeTask(t);
			}
		};
		timer.schedule(Random.nextInt(30) * 100);*/
	}

}
