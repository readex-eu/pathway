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




/** A composite that displays information about the currently selected experiment. */
@NonNullByDefault
class ConfigurationComposite extends ScrolledComposite implements ExperimentComposite {
    public ConfigurationComposite(Composite parent, int style) {
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

        // number of MPI processes
        Label labelMPI = new Label(composite, 0);
        labelMPI.setText("# MPI processes:");
        textMPI = new Text(composite, 0);
        textMPI.setEditable(false);
        textMPI.setLayoutData(data);

        // number of OpenMP threads
        Label labelOMP = new Label(composite, 0);
        labelOMP.setText("# OpenMP threads:");
        textOMP = new Text(composite, 0);
        textOMP.setEditable(false);
        textOMP.setLayoutData(data);

        // startup folder
        Label labelStartupFolder = new Label(composite, 0);
        labelStartupFolder.setText("Startup folder:");
        textStartupFolder = new Text(composite, 0);
        textStartupFolder.setEditable(false);
        textStartupFolder.setLayoutData(data);

        // code version
        Label labelCodeVersion = new Label(composite, 0);
        labelCodeVersion.setText("Code version:");
        textCodeVersion = new Text(composite, 0);
        textCodeVersion.setEditable(false);
        textCodeVersion.setLayoutData(data);

        setContent(composite);
        setExpandHorizontal(true);
        setExpandVertical(true);
        setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
    }


    @Override
    public void setShownExperiment(Experiment experiment) {
        textMPI.setText(Integer.toString(experiment.getMpiProcs()));
        textOMP.setText(Integer.toString(experiment.getOmpThreads()));
        textStartupFolder.setText(experiment.getStartupFolder());
        textCodeVersion.setText(experiment.getCodeVersion());
    }


    private Text textMPI;
    private Text textOMP;
    private Text textStartupFolder;
    private Text textCodeVersion;
}
