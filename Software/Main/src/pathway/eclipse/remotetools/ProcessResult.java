package pathway.eclipse.remotetools;

import org.eclipse.jdt.annotation.NonNullByDefault;




@NonNullByDefault
public final class ProcessResult {
    public ProcessResult(int status, String stdout, String stderr) {
        this.status = status;
        this.stdout = stdout;
        this.stderr = stderr;
    }


    public int status;
    public String stdout;
    public String stderr;
}
