package org.kuali.student.lum.program.client.major;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.mvc.DataModelDefinition;
import org.kuali.student.lum.program.client.ProgramRegistry;
import org.kuali.student.lum.program.client.ProgramUtils;
import org.kuali.student.lum.program.client.events.ProgramViewEvent;
import org.kuali.student.lum.program.client.major.edit.MajorEditController;
import org.kuali.student.lum.program.client.major.view.MajorViewController;
import org.kuali.student.lum.program.client.variation.edit.VariationEditController;
import org.kuali.student.lum.program.client.variation.view.VariationViewController;
import org.kuali.student.lum.program.client.versions.ProgramVersionsController;
import org.kuali.student.lum.program.client.widgets.ProgramSideBar;

import com.google.gwt.event.shared.HandlerManager;

/**
 * @author Igor
 */
public class MajorManager {

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
        variationViewController = new VariationViewController(variationModel, viewContext, eventBus, majorViewController);
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
        definition.setMetadata (programModel.getDefinition ().getMetadata ("variations/*"));
//        KSErrorDialog.show (new NullPointerException
//     ("metada for: "
//     +  formatMetadata (definition.getMetadata (), "variations/*")));
        variationModel.setDefinition(definition);
        variationModel.setRoot(ProgramRegistry.getData());
        ProgramUtils.unregisterUnusedHandlers(eventBus);
        variationEditController = new VariationEditController(variationModel, viewContext, eventBus, majorEditController);
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

    private MajorEditController getMajorEditController() {
        if (majorEditController == null) {
            majorEditController = new MajorEditController(programModel, viewContext, eventBus);
        }
        return majorEditController;
    }

    private MajorViewController getMajorViewController() {
        if (majorViewController == null) {
            majorViewController = new MajorViewController(programModel, viewContext, eventBus);
        }
        return majorViewController;
    }
}
