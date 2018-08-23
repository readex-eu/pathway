package pathway.workflows.workitems;

import java.util.Collection;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecuteWorkflowJob;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.workflows.WorkflowErrorException;




public class RunAllExperimentsWorkItem implements WorkItemHandler {
    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        BatchSystemManager batchSysManager = (BatchSystemManager)workItem.getParameter("BatchSysManager");
        if( batchSysManager == null )
            throw new IllegalArgumentException("The work item's batch system manager parameter cannot be a null reference.");

        Collection<Experiment> experiments = (Collection<Experiment>)workItem.getParameter("Experiments");
        if( experiments == null )
            throw new IllegalArgumentException("The experiments collection cannot be a null reference.");

        int numExperiments = experiments.size();
        ExecutionLog.get().writeLog(numExperiments == 1 ? "Starting experiment..." : "Starting " + numExperiments + " experiments...");
        
        for( Experiment experiment: experiments )
            try {
                WorkflowInstanceData instanceData = ExecuteWorkflowJob.getInstanceData(workItem.getProcessInstanceId());
                if( instanceData == null )
                    throw new RuntimeException("Process instance data is unexpectedly a null reference.");

                RunExperimentWorkItem.runExperiment(batchSysManager, experiment, instanceData.instance);
            }
            catch( Exception ex ) {
                ex.printStackTrace();
                String message = "Unable to start experiment: " + ex.getMessage();
                ExecutionLog.get().writeErr(message);
                UIUtils.showErrorMessage(message);
                throw new WorkflowErrorException();
            }

        manager.completeWorkItem(workItem.getId(), null);
    }


    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        manager.abortWorkItem(workItem.getId());
    }
}
