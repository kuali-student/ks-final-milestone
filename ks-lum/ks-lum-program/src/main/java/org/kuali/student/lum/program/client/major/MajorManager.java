package org.kuali.student.lum.program.client.major;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramUtils;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.proposal.MajorProposalController;
import org.kuali.student.lum.program.client.major.view.MajorViewController;
import org.kuali.student.lum.program.client.variation.edit.VariationEditController;
import org.kuali.student.lum.program.client.variation.view.VariationViewController;
import org.kuali.student.lum.program.client.versions.ProgramVersionsController;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;

/**
 * @author Igor
 */
public class MajorManager {

    private MajorProposalController majorProposalController;
    
    private MajorViewController majorViewController;

    private MajorEditController majorEditController;

    private VariationViewController variationViewController;

    private VariationEditController variationEditController;

    private ProgramVersionsController programVersionsController;

    protected DataModel programModel;

    private ViewContext viewContext = new ViewContext();

    private static HandlerManager eventBus;

    public MajorManager() {
        eventBus = new HandlerManager(null);
        programModel = new DataModel();
        ProgramRegistry.getSpecializationHandlers().clear();
    }

    public MajorViewController getProgramViewController() {
        programModel.resetRoot();
        getMajorViewController();
        eventBus.fireEvent(new ProgramViewEvent());
        return majorViewController;
    }

    public VariationViewController getVariationViewController() {
        DataModel variationModel = new DataModel();
//        variationModel.setDefinition(programModel.getDefinition());
        DataModelDefinition definition = new DataModelDefinition ();
        definition.setMetadata (programModel.getDefinition ().getMetadata ("variations/*"));
        variationModel.setDefinition(definition);
        variationModel.setRoot(ProgramRegistry.getData());
       
        // Determine if we are coming from a link to a proposal
        // If so, we need to instantiate the variation controller using an
        // instance of the proposal controller, otherwise we should just
        // use the major discipline controller
        MajorController theController = null;
        
        // Look in model to see if we have passed a flag to say we are working with
        // a proposal (passed from VariationsBinding.java, search for isProposal)
        boolean comingFromProposal = variationModel != null && variationModel.get("isProposal") != null && majorProposalController!=null;
           
        if (comingFromProposal){
 	       // We are coming from a link to a proposal, so use the proposal controller
            theController = majorProposalController;
        }
        else {
        	// This is not a link to a proposal, so use the normal major discipline controller
        	 theController = (MajorController)majorViewController;
        } 
        
        // Fix for 2417
        // A case exists where we are coming from majorEditController
        // TODO: we really need to think about how this works rather than
        // add these if/then cases, but we are out of time 
        if (theController == null && majorEditController != null){
            theController = majorEditController;
        }
        
    	// Instantiate a controller used to work with the specialization, passing it
    	// either the proposal controller or the major discipline controller
        variationViewController = new VariationViewController(variationModel, viewContext, eventBus, theController);
   
        return variationViewController;
    }

//  private String formatMetadata (Metadata md, String fieldKey)
//  {
//   String msg = "metadata for fieldKey=" + fieldKey
//                //    + "\n Name=" + md.getName ()
//                + "\n LabelKey=" + md.getLabelKey ()
//                + "\n defaultValuePath=" + md.getDefaultValuePath ()
//                + "\n LookupContextPath=" + md.getLookupContextPath ()
//                //    + "\n maskForatter="  + md.getMaskFormatter ()
//                //    + "\n partialMaskFormatter="  + md.getPartialMaskFormatter ()
//                + "\n dataType=" + md.getDataType ()
//                + "\n defaultValue=" + md.getDefaultValue ()
//                + "\n WriteAccess=" + md.getWriteAccess ()
//                + "\n initialLookup=" + md.getInitialLookup ()
//                + "\n additionalLookups=" + md.getAdditionalLookups ();
//   if (md.getProperties () != null)
//   {
//    msg += "\n It has " + md.getProperties ().size () + " properties: \n";
//    for (String fk : md.getProperties ().keySet ())
//    {
//     msg += "\n" + formatMetadata (md.getProperties ().get (fk), fk);
//    }
//   }
//   return msg;
//  }


    public VariationEditController getVariationEditController() {
        DataModel variationModel = new DataModel();
//        variationModel.setDefinition(programModel.getDefinition());
        DataModelDefinition definition = new DataModelDefinition ();
        //What if the program model is not ready yet??
        if(programModel==null||programModel.getDefinition()==null){
        	Window.alert("Debug: null programModel or definition (MajorManager.getVariationEditController()");
        }
        definition.setMetadata(programModel.getDefinition().getMetadata("variations/*"));
//        KSErrorDialog.show (new NullPointerException
//     ("metada for: "
//     +  formatMetadata (definition.getMetadata (), "variations/*")));
        variationModel.setDefinition(definition);
        variationModel.setRoot(ProgramRegistry.getData());
        ProgramUtils.unregisterUnusedHandlers(eventBus);
 
        // Determine if we are coming from a link to a proposal
        // If so, we need to instantiate the variation controller using an
        // instance of the proposal controller, otherwise we should just
        // use the major discipline controller
        MajorController theController = null;
        
        // Look in model to see if we have passed a flag to say we are working with
        // a proposal (passed from VariationsBinding.java, search for isProposal)
        boolean comingFromProposal = variationModel != null && variationModel.get("isProposal") != null && majorProposalController!=null;
           
        if (comingFromProposal){
 	       // We are coming from a link to a proposal, so use the proposal controller
            theController = majorProposalController;
        }
        else {
        	// This is not a link to a proposal, so use the normal major discipline controller
        	 theController = (MajorController)majorViewController;
        } 
    	
        // Fix for 2417
        // A case exists where we are coming from majorEditController
        // TODO: we really need to think about how this works rather than
        // add these if/then cases, but we are out of time 
        if (theController == null && majorEditController != null){
            theController = majorEditController;
        }
       
        
        
    	// Instantiate a controller used to work with the specialization, passing it
    	// either the proposal controller or the major discipline controller
        variationEditController = new VariationEditController(variationModel, viewContext, eventBus, theController);
        return variationEditController;
    }

    public MajorEditController getProgramEditController() {
        programModel.resetRoot();
        return getMajorEditController();
    }
    
    public MajorEditController getProgramSpecEditController() {
        return getMajorEditController();
    }
    
    public ProgramVersionsController getProgramVersionsController() {
        if (programVersionsController == null) {
            programVersionsController = new ProgramVersionsController(programModel, ProgramSideBar.Type.MAJOR, viewContext, eventBus);
        }
        return programVersionsController;
    }

    public static HandlerManager getEventBus() {
        return eventBus;
    }

    public MajorProposalController getMajorProposalController() {
        if (majorProposalController == null) {
            majorProposalController = new MajorProposalController(programModel, viewContext, eventBus);
        }
        return majorProposalController;
    }
    
    
    public MajorEditController getMajorEditController() {
        eventBus = new HandlerManager(null);
        majorEditController = new MajorEditController(programModel, viewContext, eventBus);

        return majorEditController;
    }

    private MajorViewController getMajorViewController() {
        majorViewController = new MajorViewController(programModel, viewContext, eventBus);

        return majorViewController;
    }

	public DataModel getProgramModel() {
		return programModel;
	}
}
