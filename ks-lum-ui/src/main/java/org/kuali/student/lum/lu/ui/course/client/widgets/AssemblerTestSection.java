package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;
import java.util.Map.Entry;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.sections.BaseSection;
import org.kuali.student.common.ui.client.configurable.mvc.views.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.common.ui.client.service.DataSaveResult;
import org.kuali.student.core.assembly.data.ConstraintMetadata;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.core.assembly.data.Data.Property;
import org.kuali.student.core.assembly.data.HasChangeCallbacks.ChangeCallback;
import org.kuali.student.core.assembly.data.HasChangeCallbacks.ChangeType;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.RichTextInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalInfoHelper;
//import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
//import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CreditCourseProposalRpcServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;


/**
 * Only used for testing out new proposal assembler code, to be deleted after testing
 * @author wilj
 *
 */
public class AssemblerTestSection extends SectionView {
	private static final CreditCourseProposalRpcServiceAsync service = GWT.create(CreditCourseProposalRpcService.class);
    
	private final AssemblerTestComposite widget = new AssemblerTestComposite();
	public AssemblerTestSection(Enum<?> viewEnum, String viewName) {
		super(viewEnum, viewName);
		init();
	}

	public AssemblerTestSection(LayoutController controller, Enum<?> viewEnum,
			String viewName) {
		super(controller, viewEnum, viewName);
		init();
	}
	
	private void init() {
		super.initWidget(widget);
	}

	@Override
	public void redraw() {
		// do nothing
	}

	@Override
	public void updateModel() {
		// do nothing
	}
	
	public static class AssemblerTestComposite extends Composite {
		private final TabPanel panel = new TabPanel();
		private final FlowPanel dataPanel = new FlowPanel();
		private final HorizontalPanel buttonPanel = new HorizontalPanel();
		private final Label status = new Label();
		private final TextArea output = new TextArea();
		private String lastCreatedId = null;
		public AssemblerTestComposite() {
			super.initWidget(panel);
			panel.add(dataPanel, "Data");
			dataPanel.add(buttonPanel);
			dataPanel.add(status);
			dataPanel.add(output);
			output.setSize("1280px", "800px");
			buttonPanel.add(new Button("Create new", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					createNew();
				}
			}));
			buttonPanel.add(new Button("Retrieve", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					retrieveLastSaved();
				}
			}));
			buttonPanel.add(new Button("Modify", new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					modifyLastSaved();
				}
			}));
			
			service.getMetadata("", "",new AsyncCallback<Metadata>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}

				@Override
				public void onSuccess(Metadata result) {
					MetadataBrowser browser = new MetadataBrowser();
					browser.setMetadata(result);
					panel.add(browser, "Metadata");
				}
				
			});
			
			panel.selectTab(0);
		}
		
		
		
		private void createNew() {
			output.setText("");
			status.setText("Creating new proposal");
			CreditCourseProposalHelper prop = createCreditCourseProposal();
			status.setText("Saving new proposal");
			service.saveData(prop.getData(), new AsyncCallback<DataSaveResult>() {

				@Override
				public void onFailure(Throwable caught) {
					status.setText("Save failed: " + caught.toString());
				}

				@Override
				public void onSuccess(DataSaveResult result) {
					CreditCourseProposalHelper created = CreditCourseProposalHelper.wrap(result.getValue());
					if (created != null) {
						lastCreatedId = created.getProposal().getId();
						output.setText("");
						dumpSaveResult(result);
					}
					if (lastCreatedId != null) {
						status.setText("Successfully saved new proposal");
					} else {
						status.setText("Failed to save new proposal");
					}
				}

				
			});
		}
		
		private void setUpdated(Data d) {
			Data mods = d.get("modifications");
			if (mods == null) {
				mods = new Data();
				d.set("modifications", mods);
			}
			mods.set("updated", true);
		}
		private void modifyLastSaved() {
			status.setText("Retrieving saved proposal");
			service.getData(lastCreatedId, new AsyncCallback<Data>() {

				@Override
				public void onFailure(Throwable caught) {
					status.setText("Modify failed: " + caught.toString());
				}

				@Override
				public void onSuccess(Data result) {
					status.setText("Retrieved proposal");
					result.addChangeCallback(new ChangeCallback() {
						@Override
						public void onChange(ChangeType type, QueryPath path) {
							GWT.log(type.toString() + " " + path.toString(), null);
						}
					});
					Data prop = result.get("proposal");
					prop.set("title", "MODIFIED " + (String) prop.get("title"));
					setUpdated(prop);
					
					Data course = result.get("course");
					course.set("transcriptTitle", "MODIFIED " + (String) course.get("transcriptTitle"));
					setUpdated(course);
					
					status.setText("Saving modified proposal");
					service.saveData(result, new AsyncCallback<DataSaveResult>() {

						@Override
						public void onFailure(Throwable caught) {
							status.setText("Failed to save modified proposal: " + caught.getMessage());
						}

						@Override
						public void onSuccess(DataSaveResult result2) {
							status.setText("Modified proposal saved");
							dumpSaveResult(result2);
						}
						
					});
					output.setText("");
					dumpProposal(result);
				}
			});
		}

		
		private void retrieveLastSaved() {
			if (lastCreatedId == null){
				status.setText("No previous proposal to retrieve");
			} else {
				status.setText("Retrieving saved proposal");
				service.getData(lastCreatedId, new AsyncCallback<Data>() {
	
					@Override
					public void onFailure(Throwable caught) {
						status.setText("Retrieval failed: " + caught.toString());
					}
	
					@Override
					public void onSuccess(Data result) {
						status.setText("Retrieved proposal");
						output.setText("");
						dumpProposal(result);
					}
				});
			}
		}
		

		private void dumpSaveResult(
				DataSaveResult result) {
			List<ValidationResultInfo> val = result.getValidationResults();
			StringBuilder sb = new StringBuilder();
			if (val != null) {
				for (ValidationResultInfo v : val) {
					sb.append(v.getLevel().toString());
					sb.append("\t");
					sb.append(v.getMessage());
					sb.append("\n");
				}
				output.setText(output.getText() + sb.toString());
			}
			dumpProposal(result.getValue());
		}
		private void dumpProposal(Data prop) {
			if (prop== null) {
				output.setText(output.getText() + "proposal is null");
			} else {
				StringBuilder sb = new StringBuilder();
				dumpData(prop, 0, sb);
				output.setText(output.getText() + sb.toString());
			}
		}
		
		
		private void dumpData(Data data, int indent, StringBuilder sb) {
			String pad = makeIndent(indent);
			sb.append(pad);
			sb.append("*");
			sb.append(data.getClassName());
			sb.append("* \n");
			
			pad = makeIndent(++indent);
			for (Property p : data) {
				sb.append(pad);
				sb.append(p.getKey().toString());
				sb.append(" = ");
				Object o = p.getValue();
				if (o instanceof Data) {
					dumpData((Data) o, indent+1, sb);
				} else if (o == null) {
					sb.append("null\n");
				} else {
					sb.append(o.toString());
					sb.append("\n");
				}
			}
			sb.append("\n");
		}
		private String makeIndent(int indent) {
			String result = "";
			for (int i=0; i<indent; i++) {
				result += "  ";
			}
			return result;
		}
		
		private CreditCourseProposalHelper createCreditCourseProposal() {
			int idx = Random.nextInt();
			CreditCourseProposalHelper result = CreditCourseProposalHelper.wrap(new Data());
			result.getData().addChangeCallback(new ChangeCallback() {
				@Override
				public void onChange(ChangeType type, QueryPath path) {
					GWT.log(type.toString() + " " + path.toString(), null);
				}
			});

			result.setState("draft");

			CreditCourseProposalInfoHelper prop = CreditCourseProposalInfoHelper.wrap(new Data());
			prop.setTitle("Assembler Test Proposal " + idx);
			prop.setRationale("rationale " + idx);
			setRuntimeFlag(prop.getData(), "created", true);
			result.setProposal(prop);
			
			CreditCourseHelper course = CreditCourseHelper.wrap(new Data());
			course.setSubjectArea("SUBJ");
			course.setCourseNumberSuffix(getSuffix(idx));
			course.setTranscriptTitle("transcript title " + idx);
			course.setState("draft");
			course.setDescription(asRichText("description blah blah blah"));
			course.setCourseTitle("course title " + idx);
			setRuntimeFlag(course.getData(), "created", true);
			result.setCourse(course);
			
			CreditCourseFormatHelper format = CreditCourseFormatHelper.wrap(new Data());
			format.setState("draft");
			setRuntimeFlag(format.getData(), "created", true);
			// TODO helpers should probably have an addXYZ() method for each list, so we can lazily create the lists under the hood
			course.setFormats(new Data());
			course.getFormats().add(format.getData());
			
			CreditCourseActivityHelper activity = CreditCourseActivityHelper.wrap(new Data());
			activity.setActivityType("kuali.lu.type.activity.Lecture");
			activity.setState("draft");
			setRuntimeFlag(activity.getData(), "created", true);
			format.setActivities(new Data());
			format.getActivities().add(activity.getData());
			
			activity = CreditCourseActivityHelper.wrap(new Data());
			activity.setActivityType("kuali.lu.type.activity.Lab");
			activity.setState("draft");
			setRuntimeFlag(activity.getData(), "created", true);
			format.getActivities().add(activity.getData());
			
			return result;
		}
		private RichTextInfoHelper asRichText(String s) {
			RichTextInfoHelper result = RichTextInfoHelper.wrap(new Data());
			result.setPlain(s);
			result.setFormatted("<b>" + s + "</b>");
			return result;
		}
		private String getSuffix(int idx) {
			String result = String.valueOf(Math.abs(idx) % 1000);
			while (result.length() < 3) { 
				result += "0";
			}
			return result;
		}
	}
	
	public static class MetadataBrowser extends Composite {
		private final HorizontalPanel panel = new HorizontalPanel();
		private final Tree tree = new Tree();
		private final SimplePanel content = new SimplePanel();
		private Metadata meta = null;
		
		public MetadataBrowser() {
			super.initWidget(panel);
			panel.add(tree);
			panel.add(content);
		}
		
		public void setMetadata(Metadata meta) {
			this.meta = meta;
			redraw();
		}
		
		private void redraw() {
			clear();
			addBranch(null, "ROOT", meta);
		}
		
		private void addBranch(final TreeItem parent, final String name, final Metadata data) {
			Label widget = new Label(name);
			widget.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					content.setWidget(new MetadataDetail(data));
				}
			});

			TreeItem item = new TreeItem(widget);
			if (parent == null) {
				tree.addItem(item);
			} else {
				parent.addItem(item);
			}
			for (Entry<String, Metadata> e : data.getProperties().entrySet()) {
				addBranch(item, e.getKey(), e.getValue());
			}
		}
		private void clear() {
			tree.clear();
			content.clear();
		}
		
	}
	
	public static class MetadataDetail extends Composite {
		private final TabPanel panel = new TabPanel();
		private final FlexTable table = new FlexTable();
		
		public MetadataDetail(Metadata meta) {
			addSummary(meta);
			addConstraints(meta);
			super.initWidget(panel);
			panel.selectTab(0);
		}
		private void addSummary(Metadata meta) {
			addRow("Data Type", meta.getDataType());
			addRow("Write Access", meta.getWriteAccess());
			addRow("Default Value", meta.getDefaultValue());
			
			panel.add(table, "Summary");
		}
		private void addConstraints(Metadata meta) {
			if (meta.getConstraints() != null) {
				panel.add(new ConstraintBrowser(meta.getConstraints()), "Constraints");
			}
		}
		private void addRow(String label, Object value) {
			int row = table.getRowCount();
			table.setText(row, 0, label);
			String s = (value == null) ? "" : value.toString();
			table.setText(row, 1, s);
		}
	}
	public static class ConstraintBrowser extends Composite {
		private final HorizontalPanel panel = new HorizontalPanel();
		private final Tree tree = new Tree();
		private final FlexTable table = new FlexTable();
		private final List<ConstraintMetadata> constraints;
		
		public ConstraintBrowser(List<ConstraintMetadata> constraints) {
			this.constraints = constraints;
			super.initWidget(panel);
			panel.add(tree);
			panel.add(table);
			redraw();
		}
		
		
		
		private void redraw() {
			clear();
			if (constraints != null) {
				for (ConstraintMetadata c : constraints) {
					addBranch(null, c);
				}
			}
		}
		
		private void addRow(String label, Object value) {
			int row = table.getRowCount();
			table.setText(row, 0, label);
			String s = (value == null) ? "" : value.toString();
			table.setText(row, 1, s);
		}
		private void addBranch(final TreeItem parent, final ConstraintMetadata constraint) {
			if (constraint != null) {
				Label widget = new Label(constraint.getId());
				widget.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						clearTable();
						addRow("ID", constraint.getId());
						addRow("Description", constraint.getDesc());
						addRow("Comments", constraint.getComments());
						addRow("Message ID", constraint.getMessageId());
						addRow("Min Length", constraint.getMinLength());
						addRow("Max Length", constraint.getMaxLength());
						addRow("Min Value", constraint.getMinValue());
						addRow("Max Value", constraint.getMaxValue());
						addRow("Min Occurs", constraint.getMinOccurs());
						addRow("Max Occurs", constraint.getMaxOccurs());
						addRow("Server Side", constraint.getServerSide());
						addRow("Special Validator", constraint.getSpecialValidator());
						addRow("Valid Characters", constraint.getValidChars());
					}
				});
	
				TreeItem item = new TreeItem(widget);
				if (parent == null) {
					tree.addItem(item);
				} else {
					parent.addItem(item);
				}
				if (constraint.getChildConstraints() != null) {
					for (ConstraintMetadata c : constraint.getChildConstraints()) {
						addBranch(item, c);;
					}
				}
			}
		}
		private void clear() {
			tree.clear();
			clearTable();
		}
		private void clearTable() {
			while (table.getRowCount() > 0) {
				table.removeRow(table.getRowCount()-1);
			}
		}
	}

	
	private static void setRuntimeFlag(Data d, String key, Boolean value) {
		Data runtime = d.get("_runtimeData");
		if (runtime == null) {
			runtime = new Data();
			d.set("_runtimeData", runtime);
		}
		runtime.set(key, value);
	}

	@Override
	protected void addFieldToLayout(FieldDescriptor f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addSectionToLayout(BaseSection s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void addWidgetToLayout(Widget w) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void removeSectionFromLayout(BaseSection section) {
	}
	
}
