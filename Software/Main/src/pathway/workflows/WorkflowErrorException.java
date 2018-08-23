package pathway.workflows;

/** This exceptions is thrown to abort workflow execution gracefully, as there seems to be no other mechanism in jBPM to do that. */
public class WorkflowErrorException extends RuntimeException {
    private static final long serialVersionUID = -249515063313206941L;
}
