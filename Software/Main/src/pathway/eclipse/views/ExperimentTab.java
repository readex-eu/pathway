package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import pathway.data.persistence.Experiment;




/** Implements a tab page that either shows a specified experiment composite or a hint that no experiment is currently selected. */
@NonNullByDefault
class ExperimentTab extends Composite {
    /** Initializes a new instance. */
    public ExperimentTab(Composite parent, int style, Class<? extends ExperimentComposite> compositeClass) {
        super(parent, style);

        this.compositeClass = compositeClass;
        this.layout = new StackLayout();
        this.experimentView = createComposite();
        this.noExperimentView = new NoExperimentComposite(this, SWT.BORDER);

        layout.topControl = noExperimentView;
        setLayout(layout);
    }


    /** Specifies the experiment to be shown in this view. */
    public void setShownExperiment(@Nullable Experiment experiment) {
        if( experiment == null )
            layout.topControl = noExperimentView;
        else {
            layout.topControl = (Control)experimentView;
            experimentView.setShownExperiment(experiment);
        }

        layout();
    }


    private ExperimentComposite createComposite() {
        try {
            return compositeClass.getConstructor(Composite.class, int.class).newInstance(this, SWT.BORDER);
        }
        catch( Exception ex ) {
            throw new RuntimeException("This should not have happened.", ex);
        }
    }


    private final Class<? extends ExperimentComposite> compositeClass;
    private final StackLayout layout;
    private final ExperimentComposite experimentView;
    private final NoExperimentComposite noExperimentView;
}
