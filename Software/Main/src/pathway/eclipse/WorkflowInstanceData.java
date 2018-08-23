package pathway.eclipse;

import org.drools.runtime.process.WorkflowProcessInstance;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import pathway.eclipse.remotetools.BatchSystemManager;




/** Stores some Pathway-specific data for a running workflow instance. */
@NonNullByDefault
public class WorkflowInstanceData {
    /** Initializes a new instance. */
    public WorkflowInstanceData(WorkflowProcessInstance instance, IProgressMonitor monitor, @Nullable String defaultSystem, @Nullable String defaultApp,
            @Nullable String defaultTool, @Nullable String defaultMPI, @Nullable String defaultOMP) {
        this.instance = instance;
        this.monitor = monitor;
        this.defaultSystem = defaultSystem;
        this.defaultApp = defaultApp;
        this.defaultTool = defaultTool;
        this.defaultMPI = defaultMPI;
        this.defaultOMP = defaultOMP;
    }


    /** References the workflow engine's process instance associated with the job. */
    public final WorkflowProcessInstance instance;

    /** References the progress monitor associated with the job. */
    public final IProgressMonitor monitor;

    /** The name of the HPC system configured as default. */
    @Nullable public final String defaultSystem;

    /** The name of the application configured as default. */
    @Nullable public final String defaultApp;

    /** The name of the performance tool configured as default. */
    @Nullable public final String defaultTool;

    /** The MPI processes configuration set as default. */
    @Nullable public final String defaultMPI;

    /** The OMP threads configuration set as default. */
    @Nullable public final String defaultOMP;


    /** Sets a new batch system manager, removing the previous one. */
    public void setBatchManager(@Nullable BatchSystemManager mgr) {
        if( batchMgr != null )
            ExecutionLog.get().writeLog("The old batch system manager is being removed.");

        batchMgr = mgr;
    }


    /** The batch system manager to dispose of after the workflow finishes. */
    @Nullable private BatchSystemManager batchMgr;
}
