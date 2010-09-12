package org.kuali.student.core.assembly.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.kuali.student.core.dictionary.poc.dto.CommonLookup;


public class UILookupData extends CommonLookup{

	private static final long serialVersionUID = 1L;
	
	   private String id;				//unique ID of this lookup
	    private String name;			//name of this search
	    private String desc;
	    private String title;   		//advanced search window title

//		@XmlElement(name="param")
//	    @XmlElementWrapper
//	    private List<LookupParamMetadata> params;
	    
	    @XmlElement(name="result")
	    @XmlElementWrapper
	    private List<LookupResultMetadata> results;
	    
	    private LookupQosMetadata qosMetadata;
	    
	     
	    private String resultDisplayKey;
	    
	    private String resultSortKey;

	    // how a search criteria will be used. ADVANCED_CUSTOM is shown on both advanced
		// and custom screens of the advanced search lightbox
	    //TODO is DEFAULT needed? it has 0 references
	    public enum Usage {
	        DEFAULT, ADVANCED, CUSTOM, ADVANCED_CUSTOM
	    }

	    private Usage usage;
	    
	    //TODO BUTTON has 0 references. Is it needed?
	    public enum Widget {
	        NO_WIDGET, SUGGEST_BOX, ADVANCED_LIGHTBOX, DROP_DOWN, BUTTON, CHECKBOX_LIST
	    }
	    private Widget widget;    

	    public enum WidgetOption {
	        ADVANCED_LIGHTBOX_PREVIEW_MODE, ADVANCED_LIGHTBOX_ACTION_LABEL
	    }
	    
	    private Map<WidgetOption, String> widgetOptions;
	    
	    public Map<WidgetOption, String> getWidgetOptions() {
	        return widgetOptions;
	    }
	    
	    public void setWidgetOptions(Map<WidgetOption, String> widgetOptions) {
	        this.widgetOptions = widgetOptions;
	    }
	    
	    public String getWidgetOptionValue(WidgetOption widgetOption) {
	        if (widgetOptions == null) return null;
	        return widgetOptions.get(widgetOption);
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

	    public String getDesc() {
	        return desc;
	    }

	    public void setDesc(String desc) {
	        this.desc = desc;
	    }

	    public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}
	    
//	    public List<LookupParamMetadata> getParams() {
//	        if (params == null) {
//	            params = new ArrayList<LookupParamMetadata>();
//	        }
//	        return params;
//	    }
//
//	    public void setParams(List<LookupParamMetadata> params) {
//	        this.params = params;
//	    }

	    public List<LookupResultMetadata> getResults() {
	        if (results == null) {
	            results = new ArrayList<LookupResultMetadata>();
	        }
	        return results;
	    }

	    public void setResults(List<LookupResultMetadata> results) {
	        this.results = results;
	    }



	    public String getResultDisplayKey() {
	        return resultDisplayKey;
	    }

	    public void setResultDisplayKey(String resultDisplayKey) {
	        this.resultDisplayKey = resultDisplayKey;
	    }

	    public String getResultSortKey() {
	        return resultSortKey;
	    }

	    public void setResultSortKey(String resultSortKey) {
	        this.resultSortKey = resultSortKey;
	    }

	    public LookupQosMetadata getQosMetadata() {
	        return qosMetadata;
	    }

	    public void setQosMetadata(LookupQosMetadata qosMetadata) {
	        this.qosMetadata = qosMetadata;
	    }
	    
	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public Usage getUsage() {
	        return usage;
	    }

	    public void setUsage(Usage usage) {
	        this.usage = usage;
	    }
	    
	    public Widget getWidget() {
			return widget;
		}

		public void setWidget(Widget widget) {
			this.widget = widget;
		}

	    public String toString(){
	        StringBuffer sb = new StringBuffer();
	        sb.append(id);
	        sb.append(",");        
	        sb.append(",");
	        sb.append(name);
	        sb.append(",");
	        sb.append(desc);
	        return sb.toString();
	    }

}
