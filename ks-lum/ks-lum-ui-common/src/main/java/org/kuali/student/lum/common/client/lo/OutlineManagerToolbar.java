package org.kuali.student.lum.common.client.lo;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * @author Igor
 */
class OutlineManagerToolbar extends HorizontalPanel {
	Button moveUpButton = new Button();

	Button moveDownButton = new Button();

	Button indentButton = new Button();

	Button outdentButton = new Button();

	Button deleteButton = new Button();


	Button addPeerButton = new Button();

	Button addChildButton = new Button();

	OutlineNodeModel outlineModel;

	OutlineManagerToolbar() {

		HorizontalPanel buttonPanel = new HorizontalPanel();

		this.setStyleName("KS-LOOutlineManagerToolbar");
		super.add(buttonPanel);
		buttonPanel.add(moveUpButton);
		buttonPanel.add(moveDownButton);
		buttonPanel.add(indentButton);
		buttonPanel.add(outdentButton);
		buttonPanel.add(deleteButton);
		buttonPanel.addStyleName("KS-LOButtonPanel");

		sinkEvents(Event.ONMOUSEMOVE);
		sinkEvents(Event.ONMOUSEOUT);

		moveUpButton.sinkEvents(Event.ONMOUSEMOVE);
		moveUpButton.sinkEvents(Event.ONMOUSEOUT);
		moveUpButton.setTitle("Move learning objective up");
		moveUpButton.addStyleName("KS-LOMoveUpButton");
		moveDownButton.addStyleName("KS-LOMoveDownButton");
		moveDownButton.setTitle("Move learning objective down");
		deleteButton.setText("Delete");
		deleteButton.setTitle("Delete");
		deleteButton.addStyleName("KS-LODeleteButton");
		indentButton.addStyleName("KS-LOIndentButton");
		indentButton.setTitle("Indent learning objective");
		outdentButton.addStyleName("KS-LOOutdentButton");
		outdentButton.setTitle("Outdent learning objective");

		moveUpButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outlineModel.moveUpCurrent();

			}
		});
		moveDownButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outlineModel.moveDownCurrent();
			}
		});
		indentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outlineModel.indentCurrent();
			}
		});
		outdentButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outlineModel.outdentCurrent();
			}
		});
		deleteButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outlineModel.deleteCurrent();
			}
		});
		addPeerButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outlineModel.addPeer();
			}
		});
		addChildButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				outlineModel.addChild();
			}
		});
	}

	public void setModel(OutlineNodeModel model) {
		outlineModel = model;
	}

	public void updateButtonStates() {
		if(  this.outlineModel.isMoveUpable()){
			moveUpButton.setEnabled(true);
			moveUpButton.removeStyleName("KS-LOMoveUpButtonDisabled");
			moveUpButton.addStyleName("KS-LOMoveUpButton");
		}else{
			moveUpButton.setEnabled(false);
			moveUpButton.removeStyleName("KS-LOMoveUpButton");
			moveUpButton.addStyleName("KS-LOMoveUpButtonDisabled");
		}
		if(this.outlineModel.isDeletable()){
			deleteButton.setEnabled(true);
			deleteButton.removeStyleName("KS-LODeleteButtonDisabled");
			deleteButton.addStyleName("KS-LODeleteButton");
		} else{
			deleteButton.setEnabled(false);
			deleteButton.removeStyleName("KS-LODeleteButton");
			deleteButton.addStyleName("KS-LODeleteButtonDisabled");
		}
		if(this.outlineModel.isIndentable()){
			indentButton.setEnabled(true);
			indentButton.removeStyleName("KS-LOIndentButtonDisabled");
			indentButton.addStyleName("KS-LOIndentButton");
		}else{
			indentButton.setEnabled(false);
			indentButton.removeStyleName("KS-LOIndentButton");
			indentButton.addStyleName("KS-LOIndentButtonDisabled");
		}
		if(this.outlineModel.isMoveDownable()){
			moveDownButton.setEnabled(true);
			moveDownButton.removeStyleName("KS-LOMoveDownButtonDisabled");
			moveDownButton.addStyleName("KS-LOMoveDownButton");
		}else{
			moveDownButton.setEnabled(false);
			moveDownButton.removeStyleName("KS-LOMoveDownButton");
			moveDownButton.addStyleName("KS-LOMoveDownButtonDisabled");
		}
		if(this.outlineModel.isOutdentable()){
			outdentButton.setEnabled(true);
			outdentButton.removeStyleName("KS-LOOutdentButtonDisabled");
			outdentButton.addStyleName("KS-LOOutdentButton");
		}else{
			outdentButton.setEnabled(false);
			outdentButton.removeStyleName("KS-LOOutdentButton");
			outdentButton.addStyleName("KS-LOOutdentButtonDisabled");
		}
	}
}
