package pathway.eclipse.wizards;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;




public class NewWorkflowPage1 extends WizardPage {
    @NonNullByDefault
    public NewWorkflowPage1(NewWorkflowData data) {
        super("createWorkflowPage");

        this.data = data;
        setTitle("Create workflow");
        setDescription("Create a new workflow");
    }


    @Override
    public void createControl(Composite parent) {
        // create the container for all the widgets
        final GridLayout layout = new GridLayout();
        layout.numColumns = numberColumns;
        final Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(layout);

        // button for empty workflow
        /*emptyButton = new Button(composite, SWT.RADIO);
        emptyButton.setText("Empty workflow");
        emptyButton.setSelection(true);

        // button for scalability workflow
        scalabilityButton = new Button(composite, SWT.RADIO);
        scalabilityButton.setText("Simple scalability analysis");

        // button for cross-platform workflow
        crossPlatformButton = new Button(composite, SWT.RADIO);
        crossPlatformButton.setText("Cross-platform analysis");

        // button for remote process
        remoteProcessButton = new Button(composite, SWT.RADIO);
        remoteProcessButton.setText("Execute remote process");

        // button for Score-P
        scorepButton = new Button(composite, SWT.RADIO);
        scorepButton.setText("Optimization with Score-P");*/

        // button for READEX
        readexButton = new Button(composite, SWT.RADIO);
        readexButton.setText("READEX");

        // button for READEX DTA
        /*readexDTAButton = new Button(composite, SWT.RADIO);
        readexDTAButton.setText("READEX DTA");

        // button for READEX TPA
        readexTPAButton = new Button(composite, SWT.RADIO);
        readexTPAButton.setText("READEX TPA");

        // button for READEX DTA minus TPA
        readexDTAminusTPAButton = new Button(composite, SWT.RADIO);
        readexDTAminusTPAButton.setText("READEX DTA minus TPA");

        // button for READEX DTA and  RTA
        readexDTARATButton = new Button(composite, SWT.RADIO);
        readexDTARATButton.setText("READEX DTA and  RAT");*/
        
        // add the container to the wizard page
        setControl(composite);
        setPageComplete(true);
    }


    public void finish() {
        data.template = getTemplateString();
    }


    private String getTemplateString() {
        /*if( emptyButton.getSelection() )
            return "empty";
        if( scalabilityButton.getSelection() )
            return "scalability";
        if( crossPlatformButton.getSelection() )
            return "crossplatform";
        if( remoteProcessButton.getSelection() )
            return "remote";
        if( scorepButton.getSelection() )
            return "score-p";*/
        if( readexButton.getSelection() )
        	return "readex";
        /*if( readexDTAButton.getSelection() )
        	return "readex_dta";
        if( readexTPAButton.getSelection() )
        	return "readex_tpa";
        if( readexDTAminusTPAButton.getSelection() )
        	return "readex_dtaminustpa";
        if( readexDTARATButton.getSelection() )
        	return "readex_dta_rat";*/

        throw new RuntimeException("This cannot happen.");
    }


    private final NewWorkflowData data;
    private Button emptyButton;
    private Button scalabilityButton;
    private Button crossPlatformButton;
    private Button remoteProcessButton;
    private Button scorepButton;
    private Button readexButton;
    private Button readexDTAButton;
    private Button readexTPAButton;
    private Button readexDTAminusTPAButton;
    private Button readexDTARATButton;


    private static final int numberColumns = 1;
}
