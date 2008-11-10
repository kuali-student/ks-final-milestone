package edu.umd.ks.poc.lum.web.clu.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.commons.ui.widgets.RoundedComposite;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.scat.dto.ScatInfo;
import edu.umd.ks.poc.lum.web.scat.client.service.ScatRpcService;


public class DynamicAttributePanel extends Composite {
    boolean loaded = false;
    //VerticalPanel root = new VerticalPanel();
    RoundedComposite root = new RoundedComposite();

    List<LuAttributeTypeInfo> attributes;

    List<AttributeDisplayFactory> attrFields = new ArrayList<AttributeDisplayFactory>();

    FlexTable   fTable = new FlexTable();
    Map<String,List<String>> attributeMap = new HashMap<String,List<String>>();
    boolean editable;
    public DynamicAttributePanel(boolean editable, List<LuAttributeTypeInfo> attributes, Map<String,List<String>> attributeMap){
        super();

        root.setWidth("100%");
        initWidget(root);
        this.editable = editable;
        if(attributeMap!=null){
        	this.attributeMap = attributeMap;
        }
    	this.attributes = attributes;
    }
    //TODO this panel needs to load a Map<String, List<String>> of existing attributes and set them
    //It also needs to load scat dropdowns


    public class myCallback<T> implements AsyncCallback<T>{

        private int myInt;
        private int offset;

        public myCallback(int i, int offsetCount) {
                        myInt = i;
                        offset = offsetCount;
        }

        /**
         * @return the offset
         */
        public int getOffset() {
            return offset;
        }

        public void onFailure(Throwable caught) {


        }
        public int getInt(){
            return this.myInt;
        }

        public void onSuccess(T result) {

        }

    }

    protected void onLoad() {
        super.onLoad();

        if(!loaded){
            loaded = true;

            root.setWidget(fTable);
            int i=0;
            int offsetCount = 0;
            for(final LuAttributeTypeInfo attrType:attributes){
                fTable.setWidget(i, offsetCount, new Label(attrType.getName()));
                fTable.getCellFormatter().setVerticalAlignment(i, offsetCount, HasVerticalAlignment.ALIGN_TOP);

            	if("Drop Down".equals(attrType.getDisplayType())){
            	    fTable.setWidget(i, offsetCount+1, new Image("images/loading.gif"));
            		ScatRpcService.Util.getInstance().findScats(attrType.getScatId(), new myCallback<List <ScatInfo>>(i, offsetCount) {

						public void onFailure(Throwable caught) {
                            Window.alert(caught.getMessage());
						}

						public void onSuccess(List<ScatInfo> result) {
							List<String> scatCodes = new ArrayList<String>();
							for(ScatInfo scatInfo:result){
								scatCodes.add(scatInfo.getCode());
							}
							AttributeDisplayFactory field = new AttributeDisplayFactory(editable,attrType.getName(), attrType.getDisplayType(), attrType.getGroupingCd(), attrType.isList(), attributeMap.get(attrType.getName()), scatCodes);
							attrFields.add(field);
							fTable.setWidget(this.getInt(),this.getOffset()+1, field.getTblValues());

						}
            		});
            	}else{
            	    AttributeDisplayFactory field = new AttributeDisplayFactory(editable,attrType.getName(), attrType.getDisplayType(),attrType.getGroupingCd(), attrType.isList(), attributeMap.get(attrType.getName()), null);
					attrFields.add(field);
            	    fTable.setWidget(i, offsetCount +1, field.getTblValues());
            	}

            	i++;
            	/* Uncomment if you want to test something scott was interested in.
            	if(i>5){
            	    i =0;
            	    offsetCount = 2;
            	}
            	*/

            }


        }
    }

    public Map<String, List<String>> getAttributesMap(){
    	Map<String, List<String>> attributes = new HashMap<String, List<String>>();

    	for(AttributeDisplayFactory field : attrFields){
    		attributes.put(field.getAttributeName(), field.getAttributeValues());
    	}
    	return attributes;
    }




}
