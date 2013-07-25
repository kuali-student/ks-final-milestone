package org.kuali.student.lum.lu.ui.course.client.views;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.r1.common.assembly.data.LookupMetadata;
import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.r1.common.assembly.data.QueryPath;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SortDirection;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.search.SearchResultsTable;
import org.kuali.student.common.ui.shared.IdAttributes.IdType;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.lu.ui.course.client.controllers.VersionsController;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;

public class SelectVersionsView extends ViewComposite{


	private VerticalFieldLayout layout = new VerticalFieldLayout();
	//private KSDocumentHeader header = new KSDocumentHeader();
	private ButtonLayout buttons = new ButtonLayout();
	SearchResultsTable table = new SearchResultsTable();
	MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
	private String id1;
	private String id2;
	
	private VersionsController parent;

	public SelectVersionsView(VersionsController controller, String name,
			Enum<?> viewType) {
		super(controller, name, viewType);
		this.initWidget(layout);
		layout.addStyleName("selectVersions");
		//layout.add(header);
		//header.addStyleName("Lum-DocumentHeader-Spacing");
		//header.setTitle(name);
		layout.addWidget(table);
		layout.addButtonLayout(buttons);
		parent = controller;
		//layout.addWidget(widget)
		layout.setLayoutTitle(SectionTitle.generateH2Title("Select Versions"));
		layout.setInstructions("Select two drafts to compare, or select one to view");
		buttons.addButton(new KSButton("Show Version(s)", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(table.getSelectedIds().size() == 1){
					id1 = table.getSelectedIds().get(0);
					id2 = null;
					ViewContext context = new ViewContext();
					context.setId(id1);
					getController().setViewContext(context);
					getController().showView(VersionsController.Views.VERSION_VIEW);
				}
				else if(table.getSelectedIds().size() == 2){
					id1 = table.getSelectedIds().get(0);
					id2 = table.getSelectedIds().get(1);
					ViewContext context = new ViewContext();
					context.setId(id1);
					context.setAttribute("docId2", id2);
					getController().setViewContext(context);
					getController().showView(VersionsController.Views.VERSION_COMPARE);
				}
				else{
					Window.alert("Please select either a single version to VIEW or two versions to COMPARE");
				}
			}
		}));
		buttons.addButton(new KSButton("Return to Current Course", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				ViewContext context = new ViewContext();
				context.setId(parent.getCurrentVersionId());
				context.setIdType(IdType.OBJECT_ID);
				HistoryManager.navigate(AppLocations.Locations.VIEW_COURSE.getLocation(), context);
			}
		}));
	}

	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
		
    	Metadata searchMetadata = parent.getDefinition().getMetadata(QueryPath.concat("searchCourseVersions"));
    	//List<LookupMetadata> additionalLookups = searchMetadata.getAdditionalLookups();
    	LookupMetadata versionSearch = searchMetadata.getInitialLookup();
/*    	for(int i = 0; i < additionalLookups.size(); i++){
    		if(additionalLookups.get(i).getWidget() == LookupMetadata.Widget.ADVANCED_LIGHTBOX){
    			versionSearch = additionalLookups.get(i);
    			break;
    		}
    	}*/
    	table.performSearch(generateRequest(versionSearch), versionSearch.getResults(), versionSearch.getResultReturnKey(), false);
		onReadyCallback.exec(true);

	}
	
	private SearchRequestInfo generateRequest(LookupMetadata versionSearch){
    	SearchRequestInfo sr = new SearchRequestInfo();
        List<SearchParamInfo> params = new ArrayList<SearchParamInfo>();
        SearchParamInfo param = new SearchParamInfo();
        param.setKey("lu.queryParam.cluVersionIndId");
        param.getValues().add(parent.getVersionIndId());
        params.add(param);
        sr.setSortDirection(SortDirection.DESC);
        sr.setParams(params);
        sr.setSearchKey(versionSearch.getSearchTypeId());
        if (versionSearch.getResultSortKey() != null) {
            sr.setSortColumn(versionSearch.getResultSortKey());
        }
        return sr;
	}

	
	public String getId1() {
		return id1;
	}

	public String getId2() {
		return id2;
	}
}
