package edu.umd.ks.poc.lum.web.scat.client;

import java.util.List;

import org.kuali.student.commons.ui.mvc.client.EventTypeHierarchy;
import org.kuali.student.commons.ui.mvc.client.EventTypeRegistry;
import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatTableInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;

public class ScatSearch extends VerticalPanel {

    Label lblSearch = new Label("SCAT Search:");
    TextBox searchString = new TextBox();
    FlexTable fTable = new FlexTable();
    Button btnSearch = new Button("Search");
    Button btnCancel = new Button("Cancel");

    FlexTable resultList = new FlexTable();
    
    boolean loaded = false;

    public ScatSearch() {

    }

    public static class ScatSearchEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(ScatSearchEvent.class, new ScatSearchEvent().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(ScatSearchEvent.class);
        }
    } 
    public static class ScatCancelSearchEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(ScatCancelSearchEvent.class, new ScatCancelSearchEvent().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(ScatCancelSearchEvent.class);
        }
    }

    static {
        new ScatSearchEvent();
        new ScatCancelSearchEvent();
    }

    public static class ScatSelectEvent extends MVCEvent {
        static {
            EventTypeRegistry.register(ScatSelectEvent.class, new ScatSelectEvent().getHierarchy());
        }

        public EventTypeHierarchy getHierarchy() {
            return super.getHierarchy().add(ScatSelectEvent.class);
        }
    }

    static {
        new ScatSelectEvent();
    }

    protected void onLoad() {
        super.onLoad();

        if (!loaded) {
            loaded = true;

            btnSearch.addClickListener(new ClickListener() {

                public void onClick(Widget sender) {
                    addListeners();
                    GlobalEventDispatcher.getInstance().fireEvent(ScatSearchEvent.class, searchString.getText());

                }
            });
            btnCancel.addClickListener(new ClickListener() {

                public void onClick(Widget sender) {
                    addListeners();
                    GlobalEventDispatcher.getInstance().fireEvent(ScatCancelSearchEvent.class, null);

                }
            });

            fTable.setWidget(0, 0, lblSearch);
            fTable.setWidget(0, 1, searchString);
            fTable.setWidget(0, 2, btnSearch);
            fTable.setWidget(0, 3, btnCancel);
            resultList.setWidget(0, 0, new HTML("<B>ScatCode</B>"));
            resultList.setWidget(0, 1, new HTML("<B>Description</B>"));

            this.add(fTable);
            this.add(resultList);
        }
        // this.addListeners();
    }

    protected void addListeners() {

        GlobalEventDispatcher.getInstance().addListener(ScatSearch.ScatSearchEvent.class, new MVCEventListener() {
            public void onEvent(Class<? extends MVCEvent> event, Object data) {
                String searchStr = (String) data;
                ScatRpcService.Util.getInstance().searchScats(searchStr, new AsyncCallback<List<ScatTableInfo>>() {

                    public void onFailure(Throwable caught) {

                    }

                    public void onSuccess(List<ScatTableInfo> result) {
                        resultList.clear();
                        resultList.setWidget(0, 0, new HTML("<B>ScatCode</B>"));
                        resultList.setWidget(0, 1, new HTML("<B>Description</B>"));
                        int i = 1;
                        for (final ScatTableInfo info : result) {
                            final SimplePanel sp = new SimplePanel();
                            final Hyperlink link = new Hyperlink(String.valueOf(info.getTableId()), "ScatTableId");
                            final Hyperlink preview = new Hyperlink("preview", "preview");
                            sp.setWidget(preview);
                            link.addClickListener(new ClickListener() {
                                
                                public void onClick(Widget sender) {                                    
                                    GlobalEventDispatcher.getInstance().fireEvent(ScatSelectEvent.class, link.getText());
                                }

                            });
                            preview.addClickListener(new ClickListener(){
                                public void onClick(Widget sender) {
                                    sp.setWidget(new Image("images/loading.gif"));
                                    ScatRpcService.Util.getInstance().findScats(info.getTableId(), 
                                            new AsyncCallback<List<ScatInfo>>(){

                                        public void onFailure(Throwable caught) {
                                          Window.alert(caught.getMessage());
                                        }

                                      
                                        public void onSuccess(List<ScatInfo> result) {
                                            ListBox lBox = new ListBox();
                                            for(ScatInfo info: result){
                                                lBox.addItem(info.getCode());
                                            }
                                            sp.setWidget(lBox);
                                            
                                        }});
                                }
                                
                            });
                            
                            resultList.setWidget(i, 0, link);
                            resultList.setWidget(i, 1, new Label(info.getTableDescription()));
                            resultList.setWidget(i, 2, sp);
                            i++;
                        }

                    }
                });

            }

        });
    }

}
