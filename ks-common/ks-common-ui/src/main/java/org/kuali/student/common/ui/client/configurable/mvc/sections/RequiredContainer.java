package org.kuali.student.common.ui.client.configurable.mvc.sections;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.assembly.data.MetadataInterrogator;
import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.widgets.KSLabel;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This class creates a toggle that is used to hide or show all non-required fields.
 * 
 * @author SW Genis
 */
public class RequiredContainer extends WarnContainer {

    private boolean showAll = false;
    private Section mainSection;

    // Labels
    private String showingRequiredFields = "Showing only required fields.     ";
    private String showingAllFields = "Showing required and optional fields.     ";
    private KSLabel label = new KSLabel(showingRequiredFields);

    // Link
    private String showAllFields = "Show all fields";
    private String showRequiredFields = "Show only required fields";
    private Anchor link = new Anchor(showAllFields);

    private List<Callback<Boolean>> callbacks = new ArrayList<Callback<Boolean>>();

    public RequiredContainer() {
        super();

        this.showWarningLayout(true);
        this.addHandler();

        this.addWarnWidget(label);
        this.addWarnWidget(link);
    }

    private void addHandler() {

        link.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                if (showAll) {
                    label.setText(showingRequiredFields);
                    link.setText(showAllFields);
                    showAll = false;
                } else {
                    label.setText(showingAllFields);
                    link.setText(showRequiredFields);
                    showAll = true;
                }
                processMainSection();
            }
        });
    }

    public boolean isShowAll() {
        return showAll;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public Section getMainSection() {
        return mainSection;
    }

    public void setMainSection(Section mainSection) {
        this.mainSection = mainSection;
        this.processMainSection();
    }

    public void addCallback(Callback<Boolean> callback) {
        callbacks.add(callback);
    }

    public List<Callback<Boolean>> getCallbacks() {
        return callbacks;
    }

    /**
     * This method loop through all the sections on the main section, process them accordingly and sets the "Show All" link
     * to visible if there is no components displayed on the section and visa versa.
     */
    public void processMainSection() {

        for (Section innerSection : mainSection.getSections()) {
            boolean hasComponents = processInnerSection(innerSection, showAll);
            setLinkVisibility(innerSection, !hasComponents);
        }

        for (Callback<Boolean> c : callbacks) {
            c.exec(showAll);
        }

    }

    /**
     * This method loop through all the fields and sections on a section and sets the visibility accordingly.
     * 
     * @param section
     * @param showAll
     * @return
     */
    public boolean processInnerSection(Section section, boolean showAll) {
        boolean hasComponents = false;
        for (FieldDescriptor field : section.getUnnestedFields()) {
            if (processFieldDescriptor(field, showAll)) {
                hasComponents = true;
            }
        }

        for (Section innerSection : section.getSections()) {
            if (processInnerSection(innerSection, showAll)) {
                hasComponents = true;
                setSectionVisibility(innerSection, true);
            } else {
                setSectionVisibility(innerSection, false);
            }
        }

        return hasComponents;
    }

    /**
     * This method hides or show the "Show All" link.
     * 
     * @param section
     * @param visibility
     */
    private void setLinkVisibility(Section section, boolean visibility) {
        if (section instanceof VerticalSection) {
            if (((VerticalSection) section).getShowAllLink() != null) {
                ((VerticalSection) section).getShowAllLink().setVisible(visibility);
            }
        }
    }

    /**
     * This method hide or show a section. Used for sections within other sections that does not have any required components
     * to show.
     * 
     * @param section
     * @param visibility
     */
    private void setSectionVisibility(Section section, boolean visibility) {
        if (section instanceof BaseSection) {
            ((BaseSection) section).getLayout().setVisible(visibility);
        }
    }

    /**
     * This method check if the component is required or not to set all non-required fields to the required visibility.
     * Returns true if widget is required.
     * 
     * @param descriptor
     * @param showAll
     * @return boolean
     */
    private boolean processFieldDescriptor(FieldDescriptor descriptor, boolean showAll) {
        if (!MetadataInterrogator.isRequired(descriptor.getMetadata()) && (!MetadataInterrogator.isRequiredForNextState(descriptor.getMetadata()))) {
            descriptor.getFieldElement().setVisible(showAll);
            return showAll;
        }
        return true;
    }

    public Composite createShowAllLink(ClickHandler handler) {
        return new ShowAllLink(handler);
    }

    public class ShowAllLink extends Composite {
        private FlowPanel layout = new FlowPanel();
        private KSLabel label = new KSLabel("No required fields.     ");
        private Anchor link = new Anchor("Show all fields");

        public ShowAllLink(ClickHandler handler) {
            initialize();
            link.addClickHandler(handler);            
        }
        
        public void initialize(){
            layout.addStyleName("ks-message-static-margin");
            add(label);
            add(link);
            this.initWidget(layout);
        }

        public void add(Widget w) {
            layout.add(w);
            w.getElement().setAttribute("style", "display: inline");
        }
    }
}
