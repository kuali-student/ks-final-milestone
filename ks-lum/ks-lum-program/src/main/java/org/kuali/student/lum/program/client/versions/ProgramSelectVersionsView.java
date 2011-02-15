package org.kuali.student.lum.program.client.versions;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.LookupMetadata;
import org.kuali.student.common.assembly.data.Metadata;
import org.kuali.student.common.assembly.data.QueryPath;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.SectionTitle;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.mvc.history.HistoryManager;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSButtonAbstract.ButtonStyle;
import org.kuali.student.common.ui.client.widgets.field.layout.button.ButtonLayout;
import org.kuali.student.common.ui.client.widgets.field.layout.layouts.VerticalFieldLayout;
import org.kuali.student.common.ui.client.widgets.search.SearchResultsTable;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SortDirection;
import org.kuali.student.lum.common.client.widgets.AppLocations;
import org.kuali.student.lum.program.client.ProgramConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import org.kuali.student.lum.program.client.ProgramRegistry;

public class ProgramSelectVersionsView extends ViewComposite{

	private VerticalFieldLayout layout = new VerticalFieldLayout();
	private ButtonLayout buttons = new ButtonLayout();
	SearchResultsTable table = new SearchResultsTable();
	MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);

	private ProgramVersionsController parent;
	private DataModel programModel;

	public ProgramSelectVersionsView(DataModel model, ProgramVersionsController controller,String name, Enum<?> viewType) {
		super(controller, name, viewType);
		this.initWidget(layout);
		layout.addStyleName("selectVersions");
		layout.addWidget(table);
		table.setWithMslable(false);
		layout.addButtonLayout(buttons);
		parent = controller;
		this.programModel = model;
		layout.setLayoutTitle(SectionTitle.generateH2Title("Program Versions"));
		layout.setInstructions("Select a version to view");

		table.setMutipleSelect(false);
		buttons.addButton(new KSButton("Select", new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				if(table.getSelectedIds().size() == 1){
					String id = table.getSelectedIds().get(0);
			    	ViewContext viewContext = parent.getViewContext();
			    	viewContext.setId(id);
			    	navigateToProgramView(viewContext);
				}
			}
		}));

		buttons.addButton(new KSButton("Return to Selected Program", ButtonStyle.ANCHOR_LARGE_CENTERED, new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
		    	ViewContext viewContext = parent.getViewContext();
		    	viewContext.setId((String)programModel.get(ProgramConstants.ID));
		    	navigateToProgramView(viewContext);
			}
		}));
	}

	private void navigateToProgramView(ViewContext viewContext){
        ProgramRegistry.setCreateNew(true);
    	switch (parent.getType()){
			case MAJOR: HistoryManager.navigate(AppLocations.Locations.VIEW_PROGRAM.getLocation(), viewContext); break;
			case CORE: HistoryManager.navigate(AppLocations.Locations.VIEW_CORE_PROGRAM.getLocation(), viewContext); break;
			case CREDENTIAL: HistoryManager.navigate(AppLocations.Locations.VIEW_BACC_PROGRAM.getLocation(), viewContext); break;
    	}
	}

	@Override
	public void beforeShow(final Callback<Boolean> onReadyCallback) {
    	Metadata searchMetadata = programModel.getDefinition().getMetadata(QueryPath.concat("searchProgramVersions"));
		LookupMetadata versionSearch = searchMetadata.getInitialLookup();
		table.performSearch(generateRequest(versionSearch), versionSearch.getResults(), versionSearch.getResultReturnKey(), false);
    	onReadyCallback.exec(true);
	}

	private SearchRequest generateRequest(LookupMetadata versionSearch){
    	SearchRequest sr = new SearchRequest();
        List<SearchParam> params = new ArrayList<SearchParam>();
        SearchParam param = new SearchParam();
        param.setKey("lu.queryParam.cluVersionIndId");
        String versionIndId = programModel.get(ProgramConstants.VERSION_IND_ID);
        versionIndId = (versionIndId == null ? "":versionIndId);
        param.setValue(versionIndId);
        params.add(param);
        sr.setSortDirection(SortDirection.DESC);
        sr.setParams(params);
        sr.setSearchKey(versionSearch.getSearchTypeId());
        if (versionSearch.getResultSortKey() != null) {
            sr.setSortColumn(versionSearch.getResultSortKey());
        }
        return sr;
	}


}
