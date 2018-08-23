package pathway.eclipse;

import java.util.concurrent.ConcurrentHashMap;
import org.drools.eclipse.ProcessInfo;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.drools.runtime.process.WorkflowProcessInstance;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.jbpm.workflow.instance.WorkflowRuntimeException;
import pathway.PAThWayPlugin;
import pathway.workflows.ProcessConsoleLogger;
import pathway.workflows.WorkflowErrorException;
import pathway.workflows.WorkflowUserAbortException;




/** Executes the specified workflow. Note that the knowledge session is not thread safe and gets disposed after the workflow completes! */
public class ExecuteWorkflowJob extends Job {
    /** Gets the workflow instance data for a specific workflow ID, or null. */
    @Nullable
    public static WorkflowInstanceData getInstanceData(@NonNull Long id) {
        return instanceData.get(id);
    }


    /** Initializes a new instance. */
    public ExecuteWorkflowJob(@NonNull StatefulKnowledgeSession session, @NonNull ProcessInfo info, String defaultSystem, String defaultApp,
            String defaultTool, String defaultMPI, String defaultOMP) {
        super("(executing workflow)");

        this.session = session;
        this.info = info;
        this.defaultSystem = defaultSystem;
        this.defaultApp = defaultApp;
        this.defaultTool = defaultTool;
        this.defaultMPI = defaultMPI;
        this.defaultOMP = defaultOMP;
        setJobName();
        setSchedulingRule();
    }


    @Override
    public IStatus run(IProgressMonitor monitor) {
        try {
            // add the logger in order to track process execution
            session.addEventListener(new ProcessConsoleLogger());

            // create a new process instance
            instance = (WorkflowProcessInstance)session.createProcessInstance(info.getProcessId(), null);
            instanceData.put(instance.getId(), new WorkflowInstanceData(instance, monitor, defaultSystem, defaultApp, defaultTool, defaultMPI, defaultOMP));

            // start the process - seems to be blocking until finished
            guardThread = new GuardThread(session, monitor, instance.getId());
            guardThread.start();
            ExecutionLog.get().writeLog("Starting workflow execution (id = " + instance.getId() + ")...");
            session.startProcessInstance(instance.getId());
        }
        catch( WorkflowRuntimeException ex ) {
            if( ex.getCause() instanceof WorkflowErrorException ) {
                errorOccured = true;
                return Status.OK_STATUS;
            }
            if( ex.getCause() instanceof WorkflowUserAbortException ) {
                userAbort = true;
                return Status.OK_STATUS;
            }

            ex.printStackTrace();
            ExecutionLog.get().writeErr(ERROR_RUNNING_WORKFLOW);
            ExecutionLog.get().writeErr(ex);
            return new Status(IStatus.ERROR, PAThWayPlugin.PLUGIN_ID, 1, ERROR_RUNNING_WORKFLOW, ex);
        }
        finally {
            cleanup();
        }

        return Status.OK_STATUS;
    }


    private void cleanup() {
        // join the guard thread
        if( guardThread != null )
            try {
                guardThread.interrupt();
                guardThread.join();
            }
            catch( InterruptedException ex ) {
            }

        // cleanup the batch system manager
        WorkflowInstanceData data = instanceData.get(instance.getId());
        data.setBatchManager(null);

        // cleanup the process instance
        if( instance != null ) {
            instanceData.remove(instance.getId());

            if( errorOccured )
                ExecutionLog.get().writeErr("Workflow execution has been terminated (id = " + instance.getId() + ").");
            else if( userAbort || instance.getState() == ProcessInstance.STATE_ABORTED )
                ExecutionLog.get().writeErr("Process execution has been aborted by the user (id = " + instance.getId() + ").");
            else
                ExecutionLog.get().writeLog("Process execution has finished (id = " + instance.getId() + ").");
        }

        // cleanup the session
        session.dispose();
    }


    private void setJobName() {
        String id = info.getProcessId();
        int index = id.lastIndexOf('.');
        if( index != -1 )
            id = id.substring(index + 1);

        setName("Executing workflow: " + id);
    }


    private void setSchedulingRule() {
        setRule(schedulingRule);
    }


    private final StatefulKnowledgeSession session;
    private final ProcessInfo info;
    private final String defaultSystem;
    private final String defaultApp;
    private final String defaultTool;
    private final String defaultMPI;
    private final String defaultOMP;
    private WorkflowProcessInstance instance;
    private GuardThread guardThread;
    private boolean errorOccured;
    private boolean userAbort;

    private static final String ERROR_RUNNING_WORKFLOW = "An error occurred while running the workflow!";
    private static final SchedulingRule schedulingRule = new SchedulingRule();
    private static final ConcurrentHashMap<Long, WorkflowInstanceData> instanceData = new ConcurrentHashMap<Long, WorkflowInstanceData>();


    private static class GuardThread extends Thread {
        public GuardThread(StatefulKnowledgeSession session, IProgressMonitor monitor, long id) {
            this.session = session;
            this.monitor = monitor;
            this.id = id;
        }


        @Override
        public void run() {
            try {
                while( true ) {
                    Thread.sleep(2000);
                    if( monitor.isCanceled() ) {
                        session.abortProcessInstance(id);
                        return;
                    }
                }
            }
            catch( InterruptedException ex ) {
            }
            catch( IllegalArgumentException ex ) {
            }
        }


        private final StatefulKnowledgeSession session;
        private final IProgressMonitor monitor;
        private final long id;
    }


    private static class SchedulingRule implements ISchedulingRule {
        @Override
        public boolean contains(ISchedulingRule rule) {
            return rule instanceof SchedulingRule;
        }


        @Override
        public boolean isConflicting(ISchedulingRule rule) {
            return rule instanceof SchedulingRule;
        }
    }
}
