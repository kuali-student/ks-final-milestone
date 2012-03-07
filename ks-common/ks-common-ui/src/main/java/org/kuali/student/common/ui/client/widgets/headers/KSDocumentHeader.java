package org.kuali.student.common.ui.client.widgets.headers;

import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.util.ExportUtils;
import org.kuali.student.common.ui.client.util.PrintUtils;
import org.kuali.student.common.ui.client.widgets.ApplicationPanel;
import org.kuali.student.common.ui.client.widgets.dialog.ReportExportDialog;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

@Deprecated
public class KSDocumentHeader extends Composite {

    private static KSDocumentHeaderUiBinder uiBinder = GWT.create(KSDocumentHeaderUiBinder.class);

    private ReportExportDialog exportDialog = null;
    
    private PopupPanel hoverPopup = new PopupPanel();
    
    private PopupPanel hoverPopup1 = new PopupPanel();

    interface KSDocumentHeaderUiBinder extends UiBinder<Widget, KSDocumentHeader> {}

    @UiField
    HTML headerHTML;

    @UiField
    HTML infoLabel;

    @UiField
    SpanPanel widgetPanel;

    @UiField
    Image printImage;

    @UiField
    Image exportImage;
    
    private Widget printContent = null;  // Widget to be printed. 

    private boolean hasSeparator = true;

    public KSDocumentHeader() {
        initWidget(uiBinder.createAndBindUi(this));
        setupPrint();
        setupExportPrint();
    }

    public KSDocumentHeader(boolean hasContentWidgetSeparator) {
        this.hasSeparator = hasContentWidgetSeparator;
        initWidget(uiBinder.createAndBindUi(this));
        setupPrint();
        setupExportPrint();
    }
    
    /**
     * returns the widget that will be printed
     * 
     * @return
     */
    public Widget getPrintContent(){
    	return this.printContent;
    }
    
    
    /**
     * Takes a widget to be printed. This is useful when you want to 
     * print only a certain section of a page
     * 
     * @param pContent
     */
    public void setPrintContent(Widget pContent){
    	this.printContent = pContent;
    }

    private void setupPrint() {
    	
    	// Default to the old way of printing the entire page
    	if(this.printContent == null){
    		this.setPrintContent(ApplicationPanel.get().getWidget(0));
    	}
    	hoverPopup.add(new HTMLPanel("Print Page"));
		hoverPopup.setStyleName("ks-help-popup");
		
		printImage.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				hoverPopup.setPopupPosition(printImage.getAbsoluteLeft()-10 , 
						printImage.getAbsoluteTop()-exportImage.getOffsetWidth()-15);
				hoverPopup.show();
			}
		});
		printImage.addMouseOutHandler(new MouseOutHandler(){

			@Override
			public void onMouseOut(MouseOutEvent event) {
				hoverPopup.hide();
			}
		});
        printImage.setVisible(false);
        printImage.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                PrintUtils.print(getPrintContent());
            }
        });
    }

    private void setupExportPrint() {
        exportDialog = new ReportExportDialog();
        exportImage.setVisible(false);
        hoverPopup1.add(new HTMLPanel("Export Summary to File"));
		hoverPopup1.setStyleName("ks-help-popup");
		exportImage.addMouseOverHandler(new MouseOverHandler(){
			@Override
			public void onMouseOver(MouseOverEvent event) {
				hoverPopup1.setPopupPosition(exportImage.getAbsoluteLeft()-55, 
						exportImage.getAbsoluteTop()-exportImage.getOffsetWidth()-15);
				hoverPopup1.show();
			}
		});
		exportImage.addMouseOutHandler(new MouseOutHandler(){

			@Override
			public void onMouseOut(MouseOutEvent event) {
				hoverPopup1.hide();
			}
		});
        
        exportImage.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (exportDialog != null) {
                	//for firefox the roll over message was not being hidden when
                	//export was clicked
                	hoverPopup1.hide();
                    exportDialog.show();
                }

            }
        });

        exportDialog.addSelectCompleteCallback(new Callback<String>() {
            @Override
            public void exec(String format) {
                Controller currController = ExportUtils.getController(KSDocumentHeader.this);
                String title = null;
                if (currController instanceof LayoutController) {
                    title = ((LayoutController) currController).getName();
                }
                ExportUtils.handleExportClickEvent(currController, format, title);
            }
        });
    }

    public void setTitle(String header) {
        headerHTML.setHTML("<h2>" + header + "</h2>");
    }

    public void addWidget(Widget w) {
        if (w != null) {
            if (hasSeparator) {
                if (widgetPanel.getElement().hasChildNodes()) {
                    HTML separatorWrapper = new HTML("<span style='float: left; margin-left: .7em; margin-right: .7em'>|</span>");
                    separatorWrapper.addStyleName("ks-documentHeader-widgetPanel");
                    widgetPanel.add(separatorWrapper);
                }
            }
            w.addStyleName("ks-documentHeader-widgetPanel");
            widgetPanel.add(w);
        }
    }

    public HTML getInfoLabel() {
        return infoLabel;
    }

    public void showPrint(boolean show) {
        printImage.setVisible(true);
}

    /**
     * This method set the visibility of the export button to the value of the parameter
     * 
     * @param show
     */
    public void showExport(boolean show) {
    	exportImage.setVisible(show);
		
	}

    public Image getExportImage() {
        return exportImage;
    }
}
