package edu.umd.ks.poc.lum.web.lutype.client.view;

import org.kuali.student.commons.ui.mvc.client.MVCEvent;
import org.kuali.student.commons.ui.mvc.client.MVCEventListener;
import org.kuali.student.commons.ui.widgets.RoundedComposite;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable.FlexCellFormatter;

import edu.umd.ks.poc.lum.lu.dto.LuAttributeTypeInfo;
import edu.umd.ks.poc.lum.web.core.client.GlobalEventDispatcher;
import edu.umd.ks.poc.lum.web.core.client.Authorization.User;
import edu.umd.ks.poc.lum.web.lutype.client.view.AddAttributePanel.AttributeCreateEvent;
import edu.umd.ks.poc.lum.web.scat.client.ScatSearch;

public class AttributeCreator extends Composite {

	boolean loaded = false;

	String luTypeId;
	RoundedComposite root = new RoundedComposite();

	FlexTable fTblTop = null;
	TextBox luTypeName = new TextBox();
	TextBox luTypeDesc = new TextBox();
	ListBox luAttrTypeGroupingCd = new ListBox();

	AttributeTypePicker typePicker =  new AttributeTypePicker();
	FlexCellFormatter cellFormatter = null;
	SimplePanel attributeFields = null;
	CheckBox isList = new CheckBox();
	VerticalPanel attributes = new VerticalPanel();
	// ScatSearch scatSearch = new ScatSearch();
	DropDownConfig dropDownWidget = new DropDownConfig();
	Button btnAdd = null;

	public AttributeCreator() {
		super();
		super.initWidget(root);
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		if (!loaded) {
			loaded = true;

			fTblTop = new FlexTable();
			cellFormatter = fTblTop.getFlexCellFormatter();
			attributeFields = new SimplePanel();

			luTypeName = new TextBox();
			luTypeDesc = new TextBox();
			luAttrTypeGroupingCd = new ListBox();
            for(User value:User.values()){
            	luAttrTypeGroupingCd.addItem(value.toString());	
			}
            
            // hack to make 'user' the default grouping code
            luAttrTypeGroupingCd.setSelectedIndex(1);
            
			typePicker = new AttributeTypePicker();
			isList = new CheckBox();
			btnAdd = new Button("Add");

			btnAdd.addClickListener(new ClickListener() {
				public void onClick(Widget sender) {
					saveAttributeType(luTypeId);
				}
			});

			fTblTop.setWidget(0, 0, new Label("Attribute Name:"));
			fTblTop.setWidget(0, 1, luTypeName);
			fTblTop.setWidget(0, 2, btnAdd);
			cellFormatter.setAlignment(0, 2,
					HasHorizontalAlignment.ALIGN_RIGHT,
					HasVerticalAlignment.ALIGN_MIDDLE);

			fTblTop.setWidget(1, 0, new Label("Attribute Description:"));
			fTblTop.setWidget(1, 1, luTypeDesc);

			fTblTop.setWidget(2, 0, new Label("Is List:"));
			fTblTop.setWidget(2, 1, isList);

			fTblTop.setWidget(3, 0, new Label("Grouping Code:"));
			fTblTop.setWidget(3, 1, luAttrTypeGroupingCd);

			fTblTop.setWidget(4, 0, new Label("Attribute Types:"));
			fTblTop.setWidget(4, 1, typePicker);
			cellFormatter.setColSpan(4, 1, 2);

			fTblTop.setWidget(5, 0, attributeFields);
			cellFormatter.setColSpan(5, 0, 3);

			fTblTop.setWidget(6, 0, attributes);
			// fTblTop.setWidget(5,0, scatSearch);
			cellFormatter.setColSpan(6, 0, 3);

			this.addListeners();

			root.setWidget(fTblTop);

		}

	}

	protected void addListeners() {
		GlobalEventDispatcher.getInstance().addListener(
				AttributeTypePicker.LuTypeAttributeSelector.class,
				new MVCEventListener() {
					public void onEvent(Class<? extends MVCEvent> event,
							Object data) {
						RadioButton rb = (RadioButton) data;
						getAttributeFields().setWidget(
								getAttributeFieldsWidget(rb.getText()));						
					}

				});

		GlobalEventDispatcher.getInstance().addListener(
				ScatSearch.ScatSelectEvent.class, new MVCEventListener() {
					public void onEvent(Class<? extends MVCEvent> event,
							Object data) {
						String rb = (String) data;
						DropDownConfig config = (DropDownConfig) getAttributeFields()
								.getWidget();
						config.getScatCode().setText(rb);
						config.dBox.hide();
					}

				});
		GlobalEventDispatcher.getInstance().addListener(
                ScatSearch.ScatCancelSearchEvent.class, new MVCEventListener() {
                    public void onEvent(Class<? extends MVCEvent> event,
                            Object data) {
                       
                        DropDownConfig config = (DropDownConfig) getAttributeFields()
                                .getWidget();
                        
                        config.dBox.hide();
                    }

                });
	}

	/**
	 * @return the attributeFields
	 */
	public SimplePanel getAttributeFields() {
		return attributeFields;
	}

	protected Widget getAttributeFieldsWidget(String attrType) {
		Widget wRet = null;

		if (AttributeTypePicker.AttributeTypes.DROP_DOWN.attributeType()
				.equalsIgnoreCase(attrType)) {
			wRet = dropDownWidget;
		}
		return wRet;
	}




	private void saveAttributeType(final String luTypeId) {
		final LuAttributeTypeInfo attrTypeInfo = new LuAttributeTypeInfo();
		attrTypeInfo.setName(luTypeName.getText());
		attrTypeInfo.setDataType(luTypeDesc.getText());
		attrTypeInfo.setDisplayType(typePicker.getValue());
		attrTypeInfo.setGroupingCd(luAttrTypeGroupingCd
				.getValue(luAttrTypeGroupingCd.getSelectedIndex()));
		attrTypeInfo.setList(isList.isChecked());
		attrTypeInfo.setScatId(dropDownWidget.getScatCode().getText());

		GlobalEventDispatcher.getInstance().fireEvent(
				AttributeCreateEvent.class, attrTypeInfo);

	}

	/**
	 * @return the luTypeId
	 */
	public String getLuTypeId() {
		return luTypeId;
	}

	/**
	 * @param luTypeId
	 *            the luTypeId to set
	 */
	public void setLuTypeId(String luTypeId) {
		this.luTypeId = luTypeId;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		luTypeName.setText("");
		luTypeDesc.setText("");
		typePicker.setValue("");
		isList.setChecked(false);
	}
}
