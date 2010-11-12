package org.kuali.student.lum.lu.ui.course.client.views;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.common.ui.client.application.KSAsyncCallback;
import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.service.MetadataRpcService;
import org.kuali.student.common.ui.client.service.MetadataRpcServiceAsync;
import org.kuali.student.common.ui.client.widgets.field.layout.element.SpanPanel;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.rice.authorization.PermissionType;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.main.client.configuration.CurriculumHomeConfigurer;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.widgets.KSErrorDialog;

public class CurriculumHomeView extends ViewComposite{
	
	private final SpanPanel container = new SpanPanel();
	MetadataRpcServiceAsync metadataServiceAsync = GWT.create(MetadataRpcService.class);
	CreditCourseProposalRpcServiceAsync cluProposalRpcServiceAsync = GWT.create(CreditCourseProposalRpcService.class);
	CurriculumHomeConfigurer configurer = GWT.create(CurriculumHomeConfigurer.class);
	
	public CurriculumHomeView(Controller controller, Enum<?> viewType) {
		super(controller, "", viewType);
		this.initWidget(container);
		setup();
	}

//  private String formatMetadata (Metadata md, String fieldKey) {
//  String msg = "metadata for fieldKey=" + fieldKey
////    + "\n Name=" + md.getName ()
//    + "\n LabelKey=" + md.getLabelKey ()
//    + "\n defaultValuePath=" + md.getDefaultValuePath ()
//    + "\n LookupContextPath="  + md.getLookupContextPath ()
////    + "\n maskForatter="  + md.getMaskFormatter ()
////    + "\n partialMaskFormatter="  + md.getPartialMaskFormatter ()
//    + "\n dataType="  + md.getDataType ()
//    + "\n defaultValue="  + md.getDefaultValue ()
//    + "\n WriteAccess="  + md.getWriteAccess ()
//    + "\n initialLookup="  + md.getInitialLookup ()
//    + "\n additionalLookups="  + md.getAdditionalLookups ()
//    ;
//  if (md.getProperties () != null) {
//   msg += "\n It has " + md.getProperties ().size () + " properties: \n";
//   for (String fk : md.getProperties ().keySet ()) {
//    msg += "\n" + formatMetadata (md.getProperties ().get (fk), fk);
//   }
//  }
//  return msg;
// }

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
	
	
	@Deprecated
    private void addIfPermitted(PermissionType permType, String searchType) {
    	addIfPermitted(permType, searchType, new HashMap<String,String>());
    }
    
    @Deprecated
    private void addIfPermitted(PermissionType permType, final String searchType, Map<String,String> permissionAttributes) {
        cluProposalRpcServiceAsync.isAuthorized(permType, permissionAttributes, new KSAsyncCallback<Boolean>() {
            @Override
            public void handleFailure(Throwable caught) {
                throw new RuntimeException("Could not verify authorization: " + caught.getMessage(), caught);
            }
            @Override
            public void onSuccess(Boolean result) {
                //NOTE: quick hack; does not matter because this all goes away with new Curriculum Management home screen
                if(result) {
/*                    if (searchType.equals("Courses")) {
                        addCourseSearchWindow(); 
                    } else if (searchType.equals("Majors")){
                        addMajorSearchWindow();
                    } else {
                        addProposalSearchWindow();
                    }*/
                }                
            }
        });
    }
	

}
