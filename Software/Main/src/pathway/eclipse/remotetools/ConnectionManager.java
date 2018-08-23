package pathway.eclipse.remotetools;

import java.io.IOException;
import java.util.Map;
import junit.framework.Assert;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.remote.core.IRemoteConnection;
import org.eclipse.remote.core.IRemoteConnectionManager;
import org.eclipse.remote.core.IRemoteFileManager;
import org.eclipse.remote.core.IRemoteServices;
import org.eclipse.remote.core.RemoteServices;
import org.eclipse.remote.core.exception.RemoteConnectionException;
import org.eclipse.ui.PlatformUI;
import pathway.data.persistence.HPCSystem;
import pathway.ui.dialogs.EditHPCSystems;
import pathway.workflows.WorkflowUserAbortException;




/**
 * The connection manager is responsible for establishing the connection to a specific HPC system. It is used by the batch manager to schedule jobs and monitor
 * them.
 */
@NonNullByDefault
public class ConnectionManager {
    public static final String REMOTE_SERVICE_ID = "org.eclipse.remote.JSch";


    public ConnectionManager(HPCSystem targetSystem) throws IOException {
        System.out.println("Trying to initialize the remote services: " + REMOTE_SERVICE_ID);
        IRemoteServices tempService = RemoteServices.getRemoteServices(REMOTE_SERVICE_ID, null);
        if( tempService == null )
            throw new IOException("Could not initialize the remote services for " + REMOTE_SERVICE_ID);

        batchSchedulerName = targetSystem.getBatchSystem();
        service = tempService;
        ConnectionOpener runnable = new ConnectionOpener(targetSystem.getName(), service);
        PlatformUI.getWorkbench().getDisplay().syncExec(runnable);

        if( runnable.error != null )
            throw new IOException(runnable.error);
        IRemoteConnection temp = runnable.connection;
        if( temp == null )
            throw new WorkflowUserAbortException();
        connection = temp;
    }


    public String getBatchSchedulerName() {
        return batchSchedulerName;
    }


    public IRemoteConnection getConnection() {
        return connection;
    }


    public IRemoteServices getConnectionService() {
        return service;
    }


    public IRemoteFileManager getFileManager() {
        return connection.getFileManager();
    }


    public String getHostName() {
        return connection.getAddress();
    }


    public String getUserName() {
        return connection.getUsername();
    }


    public int getPort() {
        return connection.getPort();
    }


    public String getCWD() {
        return connection.getWorkingDirectory();
    }


    public String getEnvVar(String envVar) {
        if( !connection.isOpen() ) {
            try {
                connection.open(null);
            }
            catch( RemoteConnectionException ex ) {
                ex.printStackTrace();
            }
        }

        Assert.assertEquals(true, connection.isOpen());
        return connection.getEnv(envVar);
    }


    public Map<String, String> getEnv() {
        if( !connection.isOpen() ) {
            try {
                connection.open(null);
            }
            catch( RemoteConnectionException ex ) {
                ex.printStackTrace();
            }
        }

        Assert.assertEquals(true, connection.isOpen());
        return connection.getEnv();
    }


    private final String batchSchedulerName;
    private final IRemoteServices service;
    private final IRemoteConnection connection;


    private static class ConnectionOpener implements Runnable {
        public ConnectionOpener(String connName, IRemoteServices service) {
            this.service = service;
            this.connName = connName;
        }


        @Override
        public void run() {
            IRemoteConnectionManager mgr = service.getConnectionManager();

            connection = mgr.getConnection(connName);
            try {
                if( connection == null )
                    connection = EditHPCSystems.editConnection(connName);
                if( connection != null )
                    connection.open(null);
            }
            catch( RemoteConnectionException ex ) {
                error = ex;
            }
        }


        public @Nullable IRemoteConnection connection;
        public @Nullable RemoteConnectionException error;

        private final IRemoteServices service;
        private final String connName;
    }
}
