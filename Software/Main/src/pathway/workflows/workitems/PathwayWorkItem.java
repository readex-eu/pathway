package pathway.workflows.workitems;

import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.orm.PersistentException;
import pathway.data.DataUtils;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecuteWorkflowJob;
import pathway.eclipse.WorkflowInstanceData;




/**
 * Base class for work item handlers provided by Pathway. This class implements various helpers and a minimum execution latency so that execution of this work
 * item becomes visible to the user.
 */
public abstract class PathwayWorkItem implements WorkItemHandler {
    @Override
    public final void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        // provide the instance data from pathway
        WorkflowInstanceData instanceData = ExecuteWorkflowJob.getInstanceData(workItem.getProcessInstanceId());
        if( instanceData == null )
            throw new RuntimeException("Process instance data is unexpectedly a null reference.");

        // do the actual execution
        long start = System.nanoTime();
        Map<String, Object> results = execute(workItem, instanceData);

        // make sure execution lasts for at least 250 ms
        long latency = System.nanoTime() - start;
        int missing = 250 - (int)(latency / 1000 / 1000);
        if( missing > 0 )
            try {
                Thread.sleep(missing);
            }
            catch( InterruptedException ex ) {
            }

        // complete the work item
        manager.completeWorkItem(workItem.getId(), results);
    }


    @Override
    public final void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        manager.abortWorkItem(workItem.getId());
    }


    /** Implements the actual work item. */
    @NonNullByDefault
    public abstract @Nullable Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data);


    @NonNullByDefault
    protected static <T> T getParameter(WorkItem workItem, String name) {
        T result = (T)workItem.getParameter(name);
        if( result == null )
            throw new IllegalArgumentException("The parameter \"" + name + "\" cannot be a null reference.");

        return result;
    }


    @NonNullByDefault
    protected static void storeExperimentWithStatus(Experiment experiment, String status) {
        try {
            experiment.setJobState(status);
            DataUtils.StoreExperiment(experiment);
        }
        catch( PersistentException ex ) {
            throw new RuntimeException(ex);
        }
    }
}
