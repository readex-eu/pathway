package pathway.workflows.workitems;

import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.orm.PersistentException;
import pathway.data.DataUtils;
import pathway.data.JobResults;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;




/** Waits for an experiment to complete and stores its results in the database. */
public class WaitForExperimentWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        Experiment experiment = getParameter(workItem, "Experiment");
        BatchSystemManager batchSysManager = getParameter(workItem, "BatchSysManager");
        waitForExperiment(batchSysManager, experiment, data.monitor);
        return null;
    }


    @NonNullByDefault
    public static void waitForExperiment(BatchSystemManager mgr, Experiment experiment, IProgressMonitor monitor) {
        String jobId = experiment.getJobId();
        ExecutionLog.get().writeLog("Waiting for job to finish on HPC system... (id = " + jobId + ")");

        try {
            JobResults jobResults = mgr.getJobResultsBlocking(jobId, monitor);
            ExecutionLog.get().writeLog("Job finished. (id = " + jobId + ")");
            ExecutionLog.get().writeLog("WaitForExperiment: stdout = (" + jobResults.stdout + ")");
            ExecutionLog.get().writeLog("WaitForExperiment: stderr = (" + jobResults.stderr + ")");
            jobResults.storeResultsToExperiment(experiment);
        }
        catch( CoreException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to retrieve job status from the HPC system.", ex);
        }

        try {
            DataUtils.StoreExperiment(experiment);
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to store experiment in the database.", ex);
        }
    }
}
