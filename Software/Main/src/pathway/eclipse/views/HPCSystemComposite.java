package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.HPCSystem;




/** A composite that displays information about the currently selected experiment. */
@NonNullByDefault
class HPCSystemComposite extends ScrolledComposite implements ExperimentComposite {
    public HPCSystemComposite(Composite parent, int style) {
        super(parent, style | SWT.V_SCROLL);

        Composite composite = new Composite(this, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 2;
        layout.horizontalSpacing = 40;
        layout.verticalSpacing = 10;
        composite.setLayout(layout);
        GridData data = new GridData(SWT.FILL, SWT.BEGINNING, true, false);

        // host name
        Label labelHostname = new Label(composite, 0);
        labelHostname.setText("Host name:");
        textHostname = new Text(composite, 0);
        textHostname.setEditable(false);
        textHostname.setLayoutData(data);

        // organization
        Label labelOrganization = new Label(composite, 0);
        labelOrganization.setText("Organization:");
        textOrganisation = new Text(composite, 0);
        textOrganisation.setEditable(false);
        textOrganisation.setLayoutData(data);

        // web site
        Label labelWebsite = new Label(composite, 0);
        labelWebsite.setText("Web site:");
        textWebsite = new Text(composite, 0);
        textWebsite.setEditable(false);
        textWebsite.setLayoutData(data);

        // batch system
        Label labelBatchSystem = new Label(composite, 0);
        labelBatchSystem.setText("Batch system:");
        textBatchSystem = new Text(composite, 0);
        textBatchSystem.setEditable(false);
        textBatchSystem.setLayoutData(data);

        // processor
        Label labelProcessor = new Label(composite, 0);
        labelProcessor.setText("Processor:");
        textProcessor = new Text(composite, 0);
        textProcessor.setEditable(false);
        textProcessor.setLayoutData(data);

        // nodes
        Label labelNodes = new Label(composite, 0);
        labelNodes.setText("# Nodes:");
        textNodes = new Text(composite, 0);
        textNodes.setEditable(false);
        textNodes.setLayoutData(data);

        // total cores
        Label labelCores = new Label(composite, 0);
        labelCores.setText("# Cores:");
        textCores = new Text(composite, 0);
        textCores.setEditable(false);
        textCores.setLayoutData(data);

        // processors per node
        Label labelPPN = new Label(composite, 0);
        labelPPN.setText("# Procs/Node:");
        textPPN = new Text(composite, 0);
        textPPN.setEditable(false);
        textPPN.setLayoutData(data);

        // peak performance
        Label labelPeakPerformance = new Label(composite, 0);
        labelPeakPerformance.setText("Peak performance:");
        textPeakPerformance = new Text(composite, 0);
        textPeakPerformance.setEditable(false);
        textPeakPerformance.setLayoutData(data);

        // memory per node
        Label labelMemoryPerNode = new Label(composite, 0);
        labelMemoryPerNode.setText("Memory/Node:");
        textMemoryPerNode = new Text(composite, 0);
        textMemoryPerNode.setEditable(false);
        textMemoryPerNode.setLayoutData(data);

        setContent(composite);
        setExpandHorizontal(true);
        setExpandVertical(true);
        setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }


    @Override
    public void setShownExperiment(Experiment experiment) {
        final HPCSystem system = experiment.getHPCSystem();

        textOrganisation.setText(system.getOrganisation());
        textWebsite.setText(system.getWebsite());
        textBatchSystem.setText(system.getBatchSystem());
        textProcessor.setText(system.getHPCSystems_CPU() == null ? "" : system.getHPCSystems_CPU().getProcessorType());
        textNodes.setText(system.getNodes() == null ? "" : system.getNodes().toString());
        textCores.setText(system.getTotalCores() == null ? "" : system.getTotalCores().toString());
        textPPN.setText(system.getProcessorsPerNode() == null ? "" : system.getProcessorsPerNode().toString());
        textPeakPerformance.setText(system.getSystemPeakPerformance() == null ? "" : system.getSystemPeakPerformance().toString());
        textMemoryPerNode.setText(system.getMemoryPerCore() == null ? "" : system.getMemoryPerCore().toString());
    }


    private Text textHostname;
    private Text textOrganisation;
    private Text textWebsite;
    private Text textBatchSystem;
    private Text textProcessor;
    private Text textNodes;
    private Text textCores;
    private Text textPPN;
    private Text textPeakPerformance;
    private Text textMemoryPerNode;
}
