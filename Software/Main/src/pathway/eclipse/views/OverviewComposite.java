package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.joda.time.format.DateTimeFormat;
import pathway.data.persistence.Experiment;




/** A composite that displays information about the currently selected experiment. */
@NonNullByDefault
class OverviewComposite extends ScrolledComposite implements ExperimentComposite {
    public OverviewComposite(Composite parent, int style) {
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

        // experiment id
        Label labelId = new Label(composite, 0);
        labelId.setText("Experiment ID:");
        textId = new Text(composite, 0);
        textId.setEditable(false);
        textId.setLayoutData(data);

        // date / time
        Label labelTime = new Label(composite, 0);
        labelTime.setText("Date / Time:");
        textTime = new Text(composite, 0);
        textTime.setEditable(false);
        textTime.setLayoutData(data);

        // comment
        Label labelComment = new Label(composite, 0);
        labelComment.setText("Comment:");
        textComment = new Text(composite, 0);
        textComment.setEditable(false);
        textComment.setLayoutData(data);

        // application
        Label labelApplication = new Label(composite, 0);
        labelApplication.setText("Application:");
        textApplication = new Text(composite, 0);
        textApplication.setEditable(false);
        textApplication.setLayoutData(data);

        // HPC system
        Label labelSystem = new Label(composite, 0);
        labelSystem.setText("HPC System:");
        textSystem = new Text(composite, 0);
        textSystem.setEditable(false);
        textSystem.setLayoutData(data);

        // tool
        Label labelTool = new Label(composite, 0);
        labelTool.setText("Tool:");
        textTool = new Text(composite, 0);
        textTool.setEditable(false);
        textTool.setLayoutData(data);

        // job id
        Label labelJobId = new Label(composite, 0);
        labelJobId.setText("Job ID:");
        textJobId = new Text(composite, 0);
        textJobId.setEditable(false);
        textJobId.setLayoutData(data);

        // results
        Label labelResults = new Label(composite, 0);
        labelResults.setText("Results URI:");
        textResults = new Text(composite, 0);
        textResults.setEditable(false);
        textResults.setLayoutData(data);

        setContent(composite);
        setExpandHorizontal(true);
        setExpandVertical(true);
        setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }


    @Override
    public void setShownExperiment(Experiment experiment) {
        textId.setText(experiment.getID());
        textTime.setText(DateTimeFormat.fullDateTime().print(experiment.getExpDate().getTime()));
        textComment.setText(experiment.getComment());
        textApplication.setText(experiment.getApplication().getName());
        textSystem.setText(experiment.getHPCSystem().getName());
        textTool.setText(experiment.getTool() == null ? "(none)" : experiment.getTool().getName() + " ver. " + experiment.getTool().getVersion());
        textJobId.setText(experiment.getJobId() + " (" + experiment.getJobState() + ")");

        String resultsURI = experiment.getResultsURI();
        textResults.setText(resultsURI == null ? "" : resultsURI);
        textResults.setEnabled(resultsURI != null);
    }


    private Text textId;
    private Text textTime;
    private Text textComment;
    private Text textApplication;
    private Text textSystem;
    private Text textTool;
    private Text textJobId;
    private Text textResults;
}
