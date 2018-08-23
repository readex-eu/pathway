package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import pathway.data.persistence.Experiment;




/** Interface for a composite that displays information about the currently selected experiment. */
@NonNullByDefault
interface ExperimentComposite {
    /** Sets the experiment to show in this view. */
    public abstract void setShownExperiment(Experiment experiment);
}
