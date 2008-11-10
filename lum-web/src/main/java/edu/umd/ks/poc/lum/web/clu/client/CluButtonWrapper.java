package edu.umd.ks.poc.lum.web.clu.client;

import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.web.clu.client.CluPanel.CreateCluEvent;
import edu.umd.ks.poc.lum.web.clu.client.CluPanel.LuTypeSelectEvent;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;

public class CluButtonWrapper extends Composite{

    VerticalPanel   root = new VerticalPanel();
    HorizontalPanel       buttonPanel = new HorizontalPanel();
    CluPanel        center      = null;

    Button          create      = new Button("Create");
    Button          approve     = new Button("Approve");
    Button          disapprove  = new Button("Disapprove");

    boolean         loaded = false;


    public CluButtonWrapper(String cluId, String routeId){

        center = new CluPanel(cluId, routeId);

        root.add(center);
        root.add(buttonPanel);
        root.setHorizontalAlignment(root.ALIGN_LEFT);

        super.initWidget(root);


    }

    protected void onLoad() {
        super.onLoad();

        if(!loaded){

            loaded = true;
            Button createButton = new Button("Create");
            createButton.addClickListener(new ClickListener(){
                public void onClick(Widget sender) {
                    GlobalEventDispatcher.getInstance().fireEvent(CreateCluEvent.class, center.getCluInfo());
                }
            });

            buttonPanel.add(createButton);
            if(center.getCluId() !=null){
                createButton.setText("Update");
                center.populateCluInfo();

            }

            GlobalEventDispatcher.getInstance().addListener(LuTypeSelectEvent.class, new MVCEventListener() {
                public void onEvent(Class<? extends MVCEvent> event, Object data) {

                    if(center.getRouteId()!=null&&"R".equals(center.getStatus().substring(0, 1))){
                        buttonPanel.add(center.getActionsWidget(center.getRouteId(), center.getCluId())); //Removed, a better way to get the workflow routeId should be used
                    }
                }
            });


        }

    }



}
