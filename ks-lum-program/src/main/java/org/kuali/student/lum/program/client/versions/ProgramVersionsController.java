package org.kuali.student.lum.program.client.versions;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.configurable.mvc.layouts.BasicLayoutWithContentHeader;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar.Type;

import com.google.gwt.event.shared.HandlerManager;

public class ProgramVersionsController extends BasicLayoutWithContentHeader{
	
	public static enum Views{VERSION_SELECT}
	
	private ProgramSelectVersionsView programSelectVersionView;
	private DataModel programModel;

    protected HandlerManager eventBus;
	private boolean initialized = false;

	private Type type;
	
	public ProgramVersionsController(DataModel dataModel, Type type, ViewContext viewContext, HandlerManager eventBus) {
		super(ProgramVersionsController.class.toString());
        this.setDefaultView(Views.VERSION_SELECT);
        this.viewContainer.addStyleName("standard-content-padding");
        this.eventBus = eventBus;
        this.programModel = dataModel;
        this.type = type;
        setViewContext(viewContext);
        initialize();
    }	
	
	private void initialize() {
        super.setDefaultModelId("Model");
        programSelectVersionView = new ProgramSelectVersionsView(programModel, this, "Versions", Views.VERSION_SELECT);
		this.addView(programSelectVersionView);
	}
	 
	
    @Override
    public void showDefaultView(final Callback<Boolean> onReadyCallback) {
        init(new Callback<Boolean>() {

            @Override
            public void exec(Boolean result) {
                if (result) {
                	ProgramVersionsController.super.showDefaultView(onReadyCallback);
                } else {
                    onReadyCallback.exec(false);
                }
            }
        });
    }
    
    @Override
    public void beforeShow(Callback<Boolean> onReadyCallback) {
    	this.getHeader().showPrint(false);
    	onReadyCallback.exec(true);
    }
    
    private void init(final Callback<Boolean> onReadyCallback) {
    	onReadyCallback.exec(true);
    }
	
	@Override
	public <V extends Enum<?>> void showView(V viewType, Callback<Boolean> onReadyCallback) {
    	String programTitle = programModel.get(ProgramConstants.LONG_TITLE);
		this.setName(programTitle);    	
    	this.getHeader().setTitle(programTitle);
		super.showView(viewType, onReadyCallback);		
	}

	public void setCurrentTitle(String currentTitle) {
    	this.getHeader().setTitle(currentTitle);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
		
}
