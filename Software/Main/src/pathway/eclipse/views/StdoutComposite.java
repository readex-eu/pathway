package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import pathway.data.persistence.Experiment;




/** A composite that displays information about the currently selected experiment. */
class StdoutComposite extends Composite implements ExperimentComposite {
    @NonNullByDefault
    public StdoutComposite(Composite parent, int style) {
        super(parent, style);

        setLayout(new FillLayout());
        text = new Text(this, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        text.setFont(JFaceResources.getFont(JFaceResources.TEXT_FONT));
    }


    @Override
    public void setShownExperiment(Experiment experiment) {
        text.setText(experiment.getStdOut());
    }


    private final Text text;
}
