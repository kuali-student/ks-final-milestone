package edu.umd.ks.poc.lum.web.lum.client.view;

import org.kuali.student.commons.ui.mvc.client.Controller;
import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVC;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.web.lum.client.LumConstants;

public class LeftMenu extends Composite {


	public static String CREATE_LU_TYPE = "createLuType";
    public static String ADD_ATTRIBUTES = "addAttributes";
    public static String FIND_LU_TYPE = "findLuType";

    public static String CREATE_CLU = "createClu";
    public static String FIND_CLU = "findClu";

    public static String CREATE_SCAT = "createScat";
    public static String SEARCH_SCAT = "searchScat";

    public static String ACTION_LIST = "actionList";
    public static String WORKFLOW_ADMIN = "../en/";
    public static String DOCUMENT_SEARCH_LINK = "../en/DocumentSearch.do";
    public static String EMBEDDED_WORKFLOW = "embeddedWorkflow";

    public static class LuTypeMenuClick extends MVCEvent {
        static {
            EventTypeRegistry.register(LuTypeMenuClick.class, new LuTypeMenuClick().getHierarchy());
        }
        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(LuTypeMenuClick.class);
        }
    }
    static {
        new LuTypeMenuClick();
    }


    boolean loaded = false;
    StackPanel  sMenu = new StackPanel();

    public LeftMenu() {

        super.initWidget(sMenu);
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        if(!loaded){
            loaded = true;


            sMenu.add(this.createLumItem(), "Lu Type");
            sMenu.add(this.crateCluItem(), "CLU");
            sMenu.add(this.crateUtilItem(), "Utilities");
            sMenu.add(this.createWorkflowItem(), "Workflow");
        }
    }

    private Widget createWorkflowItem() {
        VerticalPanel vPanel = new VerticalPanel();
        Hyperlink actionList = new Hyperlink("Action List", ACTION_LIST);
        actionList.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(ACTION_LIST);
            }});
        vPanel.add(actionList);

        /* Adds dynamic links */

        LumConstants    constants = (LumConstants)GWT.create(LumConstants.class);
        String s = constants.iFrameLinks();

        String[] nameVal = s.split(",");
        for(String str: nameVal){
            final String[] nv = str.split("\\|");

            Hyperlink embeddedWorkflowLink = new Hyperlink(nv[0], nv[0]);
            embeddedWorkflowLink.addClickListener(new ClickListener(){
                public void onClick(Widget sender) {
                    fireMenuClick(nv[0]);
                }});
            vPanel.add(embeddedWorkflowLink);

        }


        vPanel.add(new Label("--------"));
        HTML workflowLink = new HTML("<a href=" + GWT.getHostPageBaseURL() + WORKFLOW_ADMIN + " target=\"_blank\">KEW</a>");
        vPanel.add(workflowLink);


        return vPanel;
	}

	private VerticalPanel crateUtilItem(){
        VerticalPanel vPanel = new VerticalPanel();

        Hyperlink createScat = new Hyperlink("Create SCAT", CREATE_SCAT);
        createScat.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(CREATE_SCAT);
            }});
        vPanel.add(createScat);

        Hyperlink searchScat = new Hyperlink("Search SCAT", SEARCH_SCAT);
        searchScat.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(SEARCH_SCAT);
            }});
        vPanel.add(searchScat);

        return vPanel;
    }

    private VerticalPanel crateCluItem(){
        VerticalPanel vPanel = new VerticalPanel();

        Hyperlink createLuType = new Hyperlink("Create CLU", CREATE_CLU);
        createLuType.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(CREATE_CLU);
            }});

        Hyperlink findClu = new Hyperlink("Find CLU", FIND_CLU);
        findClu.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(FIND_CLU);
            }});
                /*
        Hyperlink findLuType = new Hyperlink("Find Lu Types", FIND_LU_TYPE);
        findLuType.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(FIND_LU_TYPE);
            }});

        Hyperlink detailLuType = new Hyperlink("Add Attributes", ADD_ATTRIBUTES);
        detailLuType.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(ADD_ATTRIBUTES);
            }});
        vPanel.add(createLuType);
        vPanel.add(findLuType);
        */
        vPanel.add(createLuType);
        vPanel.add(findClu);

        return vPanel;
    }

    private VerticalPanel createLumItem(){
        VerticalPanel lumPanel = new VerticalPanel();

        Hyperlink createLuType = new Hyperlink("Create Lu Type", CREATE_LU_TYPE);
        createLuType.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(CREATE_LU_TYPE);
            }});

        Hyperlink findLuType = new Hyperlink("Find Lu Types", FIND_LU_TYPE);
        findLuType.addClickListener(new ClickListener(){
            public void onClick(Widget sender) {
                fireMenuClick(FIND_LU_TYPE);
            }});

//        Hyperlink detailLuType = new Hyperlink("Add Attributes", ADD_ATTRIBUTES);
//        detailLuType.addClickListener(new ClickListener(){
//            public void onClick(Widget sender) {
//                fireMenuClick(ADD_ATTRIBUTES);
//            }});
        lumPanel.add(createLuType);
        lumPanel.add(findLuType);
        //lumPanel.add(detailLuType);


        return lumPanel;


    }

    protected void fireMenuClick(String menuClicked){
        Controller c = MVC.findParentController(this);
        c.getEventDispatcher().fireEvent(LuTypeMenuClick.class, menuClicked);
    }
}
