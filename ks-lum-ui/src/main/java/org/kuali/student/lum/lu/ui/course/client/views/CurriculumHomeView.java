package org.kuali.student.lum.lu.ui.course.client.views;

import org.kuali.student.r1.common.assembly.data.Metadata;
import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.configuration.CurriculumHomeConfigurer;

import com.google.gwt.core.client.GWT;

/**
 * CurriculumHomeView for the Curriculum Management landing page.  This view uses a configurer to set up its
 * layout (CurriculumHomeConfigurer).
 * 
 * @author Kuali Student Team
 * @see CurriculumHomeConfigurer
 */
public class CurriculumHomeView extends ViewComposite{
	
	private final SpanPanel container = new SpanPanel();
	MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
	CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
	protected CurriculumHomeConfigurer configurer = GWT.create(CurriculumHomeConfigurer.class);
	
	public CurriculumHomeView(Controller controller, Enum<?> viewType) {
		super(controller, "", viewType);
		this.initWidget(container);
		setup();
	}

	protected void setup(){
        metadataServiceAsync.getMetadata("search", null, null, new KSAsyncCallback<Metadata>() {
            @Override
            public void handleFailure(Throwable caught) {
            	container.add(configurer.configure(null));
//             			KSErrorDialog.show (caught);
                throw new RuntimeException("Could not retreive metadata: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Metadata metadata) {
//			           KSErrorDialog.show (new NullPointerException
//              ("metadata."
//             + formatMetadata (metadata, "search")));
            	container.add(configurer.configure(metadata));
            }
        });       
	}
	

}
