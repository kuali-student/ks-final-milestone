package org.kuali.student.lum.lu.ui.course.client.widgets;

import java.util.List;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.DataModel;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.common.assembly.client.HasChangeCallbacks.ChangeCallback;
import org.kuali.student.common.assembly.client.HasChangeCallbacks.ChangeType;
import org.kuali.student.common.ui.client.configurable.mvc.LayoutController;
import org.kuali.student.common.ui.client.configurable.mvc.SectionView;
import org.kuali.student.common.ui.client.mvc.Callback;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.assembly.data.client.RichTextInfoData;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourse;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseActivity;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseFormat;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseProposal;
import org.kuali.student.lum.lu.assembly.data.client.proposal.ProposalInfoData;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcService;
import org.kuali.student.lum.lu.ui.course.client.service.CluProposalRpcServiceAsync;
import org.kuali.student.lum.lu.ui.course.client.service.DataSaveResult;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;


/**
 * Only used for testing out new proposal assembler code, to be deleted after testing
 * @author wilj
 *
 */
public class AssemblerTestSection extends SectionView {
	private static final CluProposalRpcServiceAsync service = GWT.create(CluProposalRpcService.class);
    
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
	public void validate(Callback<ErrorLevel> callback) {
		callback.exec(ErrorLevel.OK);
	}

	@Override
	public void updateModel() {
		// do nothing
	}
	
	public static class AssemblerTestComposite extends Composite {
		private final FlowPanel panel = new FlowPanel();
		private final HorizontalPanel buttonPanel = new HorizontalPanel();
		private final Label status = new Label();
		private final TextArea output = new TextArea();
		private String lastCreatedId = null;
		public AssemblerTestComposite() {
			super.initWidget(panel);
			panel.add(buttonPanel);
			panel.add(status);
			panel.add(output);
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
			
						
		}
		
		
		
		private void createNew() {
			output.setText("");
			status.setText("Creating new proposal");
			CreditCourseProposal prop = createCreditCourseProposal();
			status.setText("Saving new proposal");
			service.saveCreditCourseProposal(prop, new AsyncCallback<DataSaveResult>() {

				@Override
				public void onFailure(Throwable caught) {
					status.setText("Save failed: " + caught.toString());
				}

				@Override
				public void onSuccess(DataSaveResult result) {
					CreditCourseProposal created = (CreditCourseProposal) result.getValue();
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
			mods.set("isUpdated", true);
		}
		private void modifyLastSaved() {
			status.setText("Retrieving saved proposal");
			service.getCreditCourseProposal(lastCreatedId, new AsyncCallback<Data>() {

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
					service.saveCreditCourseProposal(result, new AsyncCallback<DataSaveResult>() {

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
				service.getCreditCourseProposal(lastCreatedId, new AsyncCallback<Data>() {
	
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
			dumpProposal((CreditCourseProposal)result.getValue());
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
		
		private CreditCourseProposal createCreditCourseProposal() {
			int idx = Random.nextInt();
			CreditCourseProposal result = new CreditCourseProposal();
			result.addChangeCallback(new ChangeCallback() {
				@Override
				public void onChange(ChangeType type, QueryPath path) {
					GWT.log(type.toString() + " " + path.toString(), null);
				}
			});
			ProposalInfoData prop = new ProposalInfoData();
			prop.setState("draft");
			prop.setTitle("Assembler Test Proposal " + idx);
			prop.setRationale("rationale " + idx);
			prop.getModifications().setCreated(true);
			result.setProposal(prop);
			
			CreditCourse course = new CreditCourse();
			course.setSubjectArea("SUBJ");
			course.setCourseNumberSuffix(getSuffix(idx));
			course.setTranscriptTitle("transcript title " + idx);
			course.setState("draft");
			course.setDescription(asRichText("description blah blah blah"));
			course.setCourseTitle("course title " + idx);
			course.getModifications().setCreated(true);
			result.setCourse(course);
			
			CreditCourseFormat format = new CreditCourseFormat();
			format.setState("draft");
			format.getModifications().setCreated(true);
			course.addFormat(format);
			
			CreditCourseActivity activity = new CreditCourseActivity();
			activity.setActivityType("kuali.lu.type.activity.Lecture");
			activity.setState("draft");
			activity.getModifications().setCreated(true);
			format.addActivity(activity);
			
			activity = new CreditCourseActivity();
			activity.setActivityType("kuali.lu.type.activity.Lab");
			activity.setState("draft");
			activity.getModifications().setCreated(true);
			format.addActivity(activity);
			
			return result;
		}
		private RichTextInfoData asRichText(String s) {
			RichTextInfoData result = new RichTextInfoData();
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
	


}
