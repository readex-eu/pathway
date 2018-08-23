package pathway.eclipse.revisioncontrol;

import java.io.File;
import java.util.Map;

public interface IRepositoryStorage {

	/**
	 * Create a new Git repository giving the option to separate the work and meta-data folders. If the repository exists, it will be reused.
	 * 
	 * @param workDirPath
	 *            Folder with the working copy of the repository (aka GIT_WORK_TREE)
	 * @param metadataDirPath
	 *            Folder with meta-data about the repository (aka GIT_DIR). Will be set to workDirPath/.git is null
	 * @return Shows workDirPath as the creation was successful
	 */
	public abstract void createNewRepository(String workDirPath, String metadataDirPath);

	/**
	 * Traverses the repository and creates a map of all commits and their corresponding messages
	 * 
	 * @return Map with the commit id as a key and the commit message as value
	 */
	public abstract Map<String, String> getAllCommits();

	public abstract Object exists();

	public abstract File getWorkFolder();

	public abstract File getMetaFolder();

	public abstract void close();

	public abstract String commit(String commitMsg);

	public abstract boolean addFiles(String filePattern);

	public abstract boolean addAllFiles();

	public abstract boolean switchToRev(String targetRevision);

	public abstract String getCurrentRev();

}