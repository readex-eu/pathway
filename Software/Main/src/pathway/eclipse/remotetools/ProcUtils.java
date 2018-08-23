package pathway.eclipse.remotetools;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.remote.core.IRemoteProcess;
import org.eclipse.remote.core.IRemoteProcessBuilder;
import org.eclipse.remote.core.exception.RemoteConnectionException;

import pathway.eclipse.ExecutionLog;
import pathway.utils.Resources;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;



@NonNullByDefault
public abstract class ProcUtils {
    /** Runs a remote process and returns its result. */
    public static ProcessResult runProcess(ConnectionManager connMgr, @Nullable String workingDir, @Nullable Map<String, String> environment, String... cmdLine)
            throws RemoteConnectionException, IOException {

        return runProcess(connMgr, workingDir, environment, Arrays.asList(cmdLine));
    }


    /** Runs a remote process and returns its result. */
    public static ProcessResult runProcess(ConnectionManager connection, @Nullable String workingDir, @Nullable Map<String, String> environment,
            List<String> cmdLine) throws RemoteConnectionException, IOException {

        if( !connection.getConnection().isOpen() )
            connection.getConnection().open(null);

        // execute a sample test on the remote system
        String[] cmdArgs = trimArguments(cmdLine);
        IRemoteProcessBuilder builder = connection.getConnection().getProcessBuilder(cmdArgs);

        // if no working directory given, use the user's home folder
        if( workingDir == null )
            workingDir = connection.getCWD();
        builder.directory(connection.getFileManager().getResource(workingDir));

        // add the environment variables
        if( environment != null && environment.size() > 0 ) 
            builder.environment().putAll(environment);
        
        // create the new process
        IRemoteProcess process = builder.start(IRemoteProcessBuilder.ALLOCATE_PTY);
        waitForExit(process);

        // get the stdout of the new process so that we can store it
        InputStream procStdOut = process.getInputStream();
        InputStream procStdErr = process.getErrorStream();
        assert procStdOut != null;
        assert procStdErr != null;
        StringWriter writerStdOut = new StringWriter();
        StringWriter writerStdErr = new StringWriter();
        try {
            IOUtils.copy(procStdOut, writerStdOut);
            IOUtils.copy(procStdErr, writerStdErr);
        }
        catch( IOException ex ) {
            ex.printStackTrace();
        }

        Resources.close(procStdErr);
        Resources.close(procStdOut);
        return new ProcessResult(process.exitValue(), writerStdOut.toString(), writerStdErr.toString());
    }


    /** Trims all arguments as PTP does not ignore space but instead tries to explicitly use them as arguments. */
    private static String[] trimArguments(List<String> args) {
        String cmdArgs[] = new String[args.size()];
        for( int i = 0; i < args.size(); ++i )
            cmdArgs[i] = args.get(i).trim();

        return cmdArgs;
    }


    /** Waits for a remote process to exit. */
    private static void waitForExit(IRemoteProcess process) {
        while( true )
            try {
                process.waitFor();
                break;
            }
            catch( InterruptedException ex ) {
                ex.printStackTrace();
            }
    }
}
