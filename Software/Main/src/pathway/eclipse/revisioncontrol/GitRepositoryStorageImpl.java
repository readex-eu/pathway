package pathway.eclipse.revisioncontrol;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;




public class GitRepositoryStorageImpl implements IRepositoryStorage {
    public GitRepositoryStorageImpl(String workDirPath, String metadataDirPath) {
        createNewRepository(workDirPath, metadataDirPath);
    }


    @Override
    public void createNewRepository(String workDirPath, String metadataDirPath) {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        File workDir = new File(workDirPath);

        if( workDir.exists() && workDir.isFile() ) {
            System.err.println("Cannot create GIT repository in a file " + workDir.getAbsolutePath());
        }
        builder.setWorkTree(workDir);

        if( metadataDirPath != null ) {
            File gitDir = new File(metadataDirPath);

            // Check if a subfolder or an absolute containerPath is given
            if( !gitDir.isAbsolute() ) {
                gitDir = new File(workDir, metadataDirPath);
            }

            if( workDir.exists() && workDir.isFile() ) {
                System.err.println("Cannot create GIT meta-data folder in a file " + workDir.getAbsolutePath());
            }
            builder.setGitDir(gitDir);
        }

        Repository repo = null;
        try {
            // Build a new repository using the specified paths
            repo = builder.build();

            // Create a full repository instead of a bare one
            repo.create(false);
        }
        catch( IOException ex ) {
            throw new RuntimeException(ex);
        }

        // If using a custom subfolder for the repository, create an .gitignore file to ignore it
        if( isSubDirectory(repo.getWorkTree(), repo.getDirectory()) && !FilenameUtils.getExtension(repo.getDirectory().getAbsolutePath()).endsWith(".git") ) {
            Writer out = null;
            try {
                File gitIgnoreFile = new File(repo.getWorkTree(), ".gitignore");
                if( !gitIgnoreFile.exists() )
                    gitIgnoreFile.createNewFile();

                out = new OutputStreamWriter(new FileOutputStream(gitIgnoreFile));
                out.append(FilenameUtils.getName(repo.getDirectory().getAbsolutePath()) + "\n");
                out.close();
            }
            catch( IOException e ) {
                e.printStackTrace();
            }
        }

        this.repository = repo;
    }


    @Override
    public Map<String, String> getAllCommits() {
        HashMap<String, String> commits = new HashMap<String, String>();

        Git git = new Git(repository);
        try {
            Iterable<RevCommit> log = git.log().call();
            for( RevCommit revCommit: log ) {
                commits.put(revCommit.abbreviate(8).name(), revCommit.getFullMessage());
            }
        }
        catch( NoHeadException e ) {
            e.printStackTrace();
        }
        catch( GitAPIException e ) {
            e.printStackTrace();
        }
        return commits;
    }


    @Override
    public Object exists() {
        return repository != null && repository.getObjectDatabase().exists();
    }


    @Override
    public File getWorkFolder() {
        return repository.getWorkTree();
    }


    @Override
    public File getMetaFolder() {
        return repository.getDirectory();
    }


    @Override
    public void close() {
        repository.close();
    }


    @Override
    public String commit(String commitMsg) {
        Git git = new Git(repository);
        CommitCommand commitCmd = git.commit();
        commitCmd.setMessage(commitMsg);
        commitCmd.setAll(true);
        try {
            RevCommit revCommit = commitCmd.call();
            return revCommit.getName();
        }
        catch( Exception e ) {
            e.printStackTrace();
            return "";
        }
    }


    @Override
    public boolean addFiles(String filePattern) {
        Git git = new Git(repository);
        AddCommand addCmd = git.add();
        addCmd.addFilepattern("testfile2");
        try {
            addCmd.call();
        }
        catch( NoFilepatternException e ) {
            e.printStackTrace();
            return false;
        }
        catch( GitAPIException e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean addAllFiles() {
        return addFiles(".");
    }


    @Override
    public boolean switchToRev(String targetRevision) {
        Git git = new Git(repository);
        CheckoutCommand checkoutCmd = git.checkout();
        checkoutCmd.setStartPoint(targetRevision);
        checkoutCmd.setForce(true);
        try {
            checkoutCmd.call();
        }
        catch( Exception e ) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public String getCurrentRev() {
        try {
            return repository.resolve(Constants.HEAD).getName();
        }
        catch( AmbiguousObjectException e ) {
            e.printStackTrace();
            return "";
        }
        catch( IOException e ) {
            e.printStackTrace();
            return "";
        }
    }


    private static boolean isSubDirectory(File base, File child) {
        try {
            base = base.getCanonicalFile();
            child = child.getCanonicalFile();
        }
        catch( IOException e ) {
            e.printStackTrace();
        }

        File parentFile = child;
        while( parentFile != null ) {
            if( base.equals(parentFile) ) {
                return true;
            }
            parentFile = parentFile.getParentFile();
        }
        return false;
    }


    private Repository repository;
}
