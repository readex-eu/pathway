package pathway.eclipse.remotetools;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.remote.ui.IRemoteUIFileManager;
import org.eclipse.remote.ui.IRemoteUIServices;
import org.eclipse.remote.ui.RemoteUIServices;

import pathway.eclipse.ExecutionLog;




@NonNullByDefault
public abstract class FileUtils {
    public static String browseForFolder(ConnectionManager connMgr) {
        Assert.assertNotNull(connMgr.getConnectionService());

        IRemoteUIServices uiService = RemoteUIServices.getRemoteUIServices(connMgr.getConnectionService());
        IRemoteUIFileManager fileUiMgr = uiService.getUIFileManager();

        Assert.assertNotNull(connMgr.getConnection());

        fileUiMgr.setConnection(connMgr.getConnection());
        String result = fileUiMgr.browseDirectory(null, "Please choose folder", connMgr.getCWD(), 0);

        assert result != null;
        return result;
    }


    public static String browseForFile(ConnectionManager connMgr) {
        Assert.assertNotNull(connMgr.getConnectionService());

        IRemoteUIServices uiService = RemoteUIServices.getRemoteUIServices(connMgr.getConnectionService());
        IRemoteUIFileManager fileUiMgr = uiService.getUIFileManager();

        Assert.assertNotNull(connMgr.getConnection());

        fileUiMgr.setConnection(connMgr.getConnection());
        String result = fileUiMgr.browseFile(null, "Please choose a file", connMgr.getCWD(), 0);

        assert result != null;
        return result;
    }


    public static List<String> browseForFiles(ConnectionManager connMgr) {
        Assert.assertNotNull(connMgr.getConnectionService());

        IRemoteUIServices uiService = RemoteUIServices.getRemoteUIServices(connMgr.getConnectionService());
        IRemoteUIFileManager fileUiMgr = uiService.getUIFileManager();

        Assert.assertNotNull(connMgr.getConnection());

        fileUiMgr.setConnection(connMgr.getConnection());
        List<String> result = fileUiMgr.browseFiles(null, "Please choose files", connMgr.getCWD(), 0);

        assert result != null;
        return result;
    }


    public static void copyResourceToLocal(ConnectionManager connMgr, String remotePath, String localPath, boolean overwrite, @Nullable IProgressMonitor monitor)
            throws IOException {
        IFileStore remoteFileStore = connMgr.getFileManager().getResource(remotePath);
        IFileStore localFileStore = EFS.getLocalFileSystem().getStore(new Path(localPath));
       
        try {
            remoteFileStore.copy(localFileStore, overwrite ? EFS.OVERWRITE : EFS.NONE, monitor);
        }
        catch( CoreException ex ) {
            throw new IOException("Unable to copy the file from the remote location.", ex);
        }
    }


    public static void copyResourceToRemote(ConnectionManager connMgr, String localPath, String remotePath, boolean overwrite,
            @Nullable IProgressMonitor monitor) throws IOException {
        IFileStore local = EFS.getLocalFileSystem().getStore(new Path(localPath));
        IFileStore remote = connMgr.getFileManager().getResource(remotePath);

        try {
            remote.getParent().mkdir(EFS.NONE, monitor);
            local.copy(remote, overwrite ? EFS.OVERWRITE : EFS.NONE, monitor);
        }
        catch( CoreException ex ) {
            throw new IOException("Unable to copy the file to the remote location.", ex);
        }
    }


    public static String getRemoteTextFileContents(ConnectionManager connMgr, String remotePath) {
        return getRemoteTextFileContents(connMgr, remotePath, null);
    }


    public static String getRemoteTextFileContents(ConnectionManager connMgr, String remotePath, @Nullable IProgressMonitor monitor) {
        final IFileStore remoteFileStore = connMgr.getFileManager().getResource(remotePath);
        final StringWriter writer = new StringWriter();
        InputStream istream = null;

        try {
            istream = remoteFileStore.openInputStream(EFS.NONE, monitor);
            IOUtils.copy(istream, writer);
        }
        catch( IOException ex ) {
            System.err.println("Error reading from file " + remotePath + ": " + ex.getMessage());
            return "";
        }
        catch( CoreException ex ) {
            System.err.println("Error reading from file " + remotePath + ": " + ex.getMessage());
            return "";
        }
        finally {
            if( istream != null )
                try {
                    istream.close();
                }
                catch( IOException ex ) {
                }
        }

        return writer.toString();
    }


    public static long getResourceSize(ConnectionManager connMgr, String path) {
        return getResourceSize(connMgr, path, false);
    }


    public static long getResourceSize(ConnectionManager connMgr, String path, boolean remote) {
        IFileStore fileStore = null;
        if( remote )
            fileStore = connMgr.getFileManager().getResource(path);
        else
            fileStore = EFS.getLocalFileSystem().getStore(new org.eclipse.core.runtime.Path(path));

        return fileStore.fetchInfo().getLength();
    }


    public static boolean checkExists(ConnectionManager connMgr, String path, boolean remote) {
        IFileStore fileStore = null;
        if( remote )
            fileStore = connMgr.getFileManager().getResource(path);
        else
            fileStore = EFS.getLocalFileSystem().getStore(new org.eclipse.core.runtime.Path(path));

        return fileStore.fetchInfo().exists();
    }


    public static void deleteResource(ConnectionManager connMgr, String resourcePath) {
        IFileStore fileStore = connMgr.getFileManager().getResource(resourcePath);

        try {
            fileStore.delete(EFS.NONE, null);
        }
        catch( CoreException ex ) {
            ex.printStackTrace();
        }
    }


    /**
     * Combines a directory path and a file name (which may itself be a sub-directory). A UNIX-like path is assumed (with forward-slashes), because the path is
     * meant to be valid on the HPC system, not on the local machine.
     */
    public static String combinePath(String directory, String filename) {
        if( !directory.endsWith("/") )
            directory += "/";

        return directory + filename;
    }


    private FileUtils() {
    }
}
