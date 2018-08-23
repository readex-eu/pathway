package pathway.workflows.workitems;

import java.util.Map;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.ptp.core.jobs.IJobStatus;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;
import pathway.eclipse.WorkflowInstanceData;
import pathway.eclipse.remotetools.BatchSystemManager;
import pathway.eclipse.remotetools.FileUtils;
import pathway.workflows.WorkflowErrorException;


public class RunExperimentWorkItem extends PathwayWorkItem {
    @Override
    public Map<String, Object> execute(WorkItem workItem, WorkflowInstanceData data) {
        BatchSystemManager batchSysManager = getParameter(workItem, "BatchSysManager");
        Experiment experiment = getParameter(workItem, "Experiment");

        try {
            runExperiment(batchSysManager, experiment, data.instance);
        }
        catch( Exception ex ) {
            ex.printStackTrace();
            String message = "Unable to start experiment: " + ex.getMessage();
            ExecutionLog.get().writeErr(message);
            UIUtils.showErrorMessage(message);
            throw new WorkflowErrorException();
        }

        return null;
    }


    @NonNullByDefault
    public static void runExperiment(BatchSystemManager batchSysManager, Experiment experiment, WorkflowProcessInstance instance) throws CoreException {
        // if the executable path has not yet been set (by instrumentation step, for example), use the configured path
        if( experiment.getExecCmd().isEmpty() ) {
            String location = Helper.fillPlaceholders(experiment.getApplication().getExecLocation(), experiment);
            String executable = Helper.fillPlaceholders(experiment.getApplication().getExecutable(), experiment);
            String path = FileUtils.combinePath(location, executable);

            experiment.setExecCmd(path);
        }

        // submit job
        storeExperimentWithStatus(experiment, "Starting");
        IJobStatus jobStatus = batchSysManager.startJob(experiment, instance);
        ExecutionLog.get().writeLog(
                "Submitted job to " + experiment.getHPCSystem().getName() + ". (id = " + jobStatus.getJobId() + ")\n        application:        "
                        + experiment.getApplication().getName() + "\n        configuration:      " + experiment.getApplication().getConfig());
        experiment.setJobId(jobStatus.getJobId());

        // store information about the ongoing experiment to the database
        storeExperimentWithStatus(experiment, "Running");
    }
}
