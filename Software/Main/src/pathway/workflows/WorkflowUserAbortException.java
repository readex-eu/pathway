package pathway.workflows;

/** This exceptions is thrown to abort workflow execution gracefully, as there seems to be no other mechanism in jBPM to do that. */
public class WorkflowUserAbortException extends RuntimeException {
    private static final long serialVersionUID = 5133461553796060192L;
}
