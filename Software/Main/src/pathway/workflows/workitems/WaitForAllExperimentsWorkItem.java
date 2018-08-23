package pathway.workflows.workitems;

import java.util.Collection;
import java.util.Map;
import org.drools.runtime.process.WorkItem;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecuteWorkflowJob;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;




public class WaitForAllExperimentsWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        BatchSystemManager batchSysManager = getParameter(workItem, "BatchSysManager");
        Collection<Experiment> experiments = getParameter(workItem, "Experiments");

        int numExperiments = experiments.size();
        ExecutionLog.get().writeLog(numExperiments == 1 ? "Waiting for 1 experiment..." : "Waiting for " + numExperiments + " experiments...");
        WorkflowInstanceData instanceData = ExecuteWorkflowJob.getInstanceData(workItem.getProcessInstanceId());
        if( instanceData == null )
            throw new RuntimeException("Process instance data is unexpectedly a null reference.");

        for( Experiment experiment: experiments )
            WaitForExperimentWorkItem.waitForExperiment(batchSysManager, experiment, instanceData.monitor);

        return null;
    }
}
