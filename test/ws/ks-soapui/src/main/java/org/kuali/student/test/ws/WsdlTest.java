package org.kuali.student.test.ws;

import java.util.List;

import com.eviware.soapui.impl.wsdl.WsdlInterface;
import com.eviware.soapui.impl.wsdl.WsdlOperation;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.iface.Operation;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

public class WsdlTest {
	public static void main(String[] args) {
		new WsdlTest().run2();
	}

	public void run2() {
		try {
			SoapUITestCaseRunner runner = new SoapUITestCaseRunner();
			runner.setProjectFile("C:/workspace5/test/ws/ks-soapui/src/main/resources/KualiStudentMilestone4-soapui-project.xml");
			runner.run();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			// create new project
			WsdlProject project = new WsdlProject();

			// import wsdl
			WsdlInterface iface = project.importWsdl("http://localhost/ks-embedded/services/LuService?wsdl", true)[0];

			// Cycle through the available operations
			List<Operation> operations = iface.getOperationList();

			for (Operation operation : operations) {
				WsdlOperation op = (WsdlOperation) operation;
				System.out.println(getString(op));
			}
			// assertNotNull( content );
			// assertTrue( content.indexOf( "404 Not Found" ) > 0 );
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	protected String getString(WsdlOperation op) {
		StringBuffer sb = new StringBuffer();
		// sb.append(op.getAction() + " ");
		// sb.append(op.getAnonymous() + " ");
		sb.append(op.getBindingOperationName() + " ");
		// sb.append(op.getInputName() + " ");
		// sb.append(op.getOutputName() + " ");
		// sb.append(op.getStyle() + " ");
		// sb.append(op.getType() + " ");
		return sb.toString();
	}
}
