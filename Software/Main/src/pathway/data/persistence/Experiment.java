/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: 
 * License Type: Evaluation
 */
package pathway.data.persistence;

import org.orm.*;
import org.hibernate.Query;
import org.hibernate.LockMode;
import java.util.List;

@SuppressWarnings({ "all", "unchecked" })
public class Experiment {
	public Experiment() {
	}
	
	public static Experiment loadExperimentByORMID(String ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment getExperimentByORMID(String ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getExperimentByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment loadExperimentByORMID(String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment getExperimentByORMID(String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getExperimentByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment loadExperimentByORMID(PersistentSession session, String ID) throws PersistentException {
		try {
			return (Experiment) session.load(pathway.data.persistence.Experiment.class, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment getExperimentByORMID(PersistentSession session, String ID) throws PersistentException {
		try {
			return (Experiment) session.get(pathway.data.persistence.Experiment.class, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment loadExperimentByORMID(PersistentSession session, String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Experiment) session.load(pathway.data.persistence.Experiment.class, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment getExperimentByORMID(PersistentSession session, String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Experiment) session.get(pathway.data.persistence.Experiment.class, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryExperiment(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryExperiment(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryExperiment(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryExperiment(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment[] listExperimentByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listExperimentByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment[] listExperimentByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listExperimentByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryExperiment(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Experiment as Experiment");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryExperiment(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Experiment as Experiment");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Experiment", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment[] listExperimentByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryExperiment(session, condition, orderBy);
			return (Experiment[]) list.toArray(new Experiment[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment[] listExperimentByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryExperiment(session, condition, orderBy, lockMode);
			return (Experiment[]) list.toArray(new Experiment[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment loadExperimentByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment loadExperimentByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment loadExperimentByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Experiment[] experiments = listExperimentByQuery(session, condition, orderBy);
		if (experiments != null && experiments.length > 0)
			return experiments[0];
		else
			return null;
	}
	
	public static Experiment loadExperimentByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Experiment[] experiments = listExperimentByQuery(session, condition, orderBy, lockMode);
		if (experiments != null && experiments.length > 0)
			return experiments[0];
		else
			return null;
	}
	
	public static java.util.Iterator<Experiment> iterateExperimentByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateExperimentByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<Experiment> iterateExperimentByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateExperimentByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<Experiment> iterateExperimentByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Experiment as Experiment");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<Experiment> iterateExperimentByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Experiment as Experiment");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Experiment", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Experiment loadExperimentByCriteria(ExperimentCriteria experimentCriteria) {
		Experiment[] experiments = listExperimentByCriteria(experimentCriteria);
		if(experiments == null || experiments.length == 0) {
			return null;
		}
		return experiments[0];
	}
	
	public static Experiment[] listExperimentByCriteria(ExperimentCriteria experimentCriteria) {
		return experimentCriteria.listExperiment();
	}
	
	public static Experiment createExperiment() {
		return new pathway.data.persistence.Experiment();
	}
	
	public boolean save() throws PersistentException {
		try {
			PathwayPersistentManager.instance().saveObject(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean delete() throws PersistentException {
		try {
			PathwayPersistentManager.instance().deleteObject(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh() throws PersistentException {
		try {
			PathwayPersistentManager.instance().getSession().refresh(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean evict() throws PersistentException {
		try {
			PathwayPersistentManager.instance().getSession().evict(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean deleteAndDissociate()throws PersistentException {
		try {
			if(getTool() != null) {
				getTool().getExperiment().remove(this);
			}
			
			if(getHPCSystem() != null) {
				getHPCSystem().getExperiment().remove(this);
			}
			
			if(getApplication() != null) {
				getApplication().getExperiment().remove(this);
			}
			
			if(getHistoricalNotes() != null) {
				getHistoricalNotes().getExperiment().remove(this);
			}
			
			pathway.data.persistence.PscProperty[] lPscPropertys = (pathway.data.persistence.PscProperty[])getPscProperty().toArray(new pathway.data.persistence.PscProperty[getPscProperty().size()]);
			for(int i = 0; i < lPscPropertys.length; i++) {
				lPscPropertys[i].setExperiment(null);
			}
			pathway.data.persistence.ExperimentAddInfo[] lExperimentAddInfos = (pathway.data.persistence.ExperimentAddInfo[])getExperimentAddInfo().toArray(new pathway.data.persistence.ExperimentAddInfo[getExperimentAddInfo().size()]);
			for(int i = 0; i < lExperimentAddInfos.length; i++) {
				lExperimentAddInfos[i].setExperiment(null);
			}
			return delete();
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean deleteAndDissociate(org.orm.PersistentSession session)throws PersistentException {
		try {
			if(getTool() != null) {
				getTool().getExperiment().remove(this);
			}
			
			if(getHPCSystem() != null) {
				getHPCSystem().getExperiment().remove(this);
			}
			
			if(getApplication() != null) {
				getApplication().getExperiment().remove(this);
			}
			
			if(getHistoricalNotes() != null) {
				getHistoricalNotes().getExperiment().remove(this);
			}
			
			pathway.data.persistence.PscProperty[] lPscPropertys = (pathway.data.persistence.PscProperty[])getPscProperty().toArray(new pathway.data.persistence.PscProperty[getPscProperty().size()]);
			for(int i = 0; i < lPscPropertys.length; i++) {
				lPscPropertys[i].setExperiment(null);
			}
			pathway.data.persistence.ExperimentAddInfo[] lExperimentAddInfos = (pathway.data.persistence.ExperimentAddInfo[])getExperimentAddInfo().toArray(new pathway.data.persistence.ExperimentAddInfo[getExperimentAddInfo().size()]);
			for(int i = 0; i < lExperimentAddInfos.length; i++) {
				lExperimentAddInfos[i].setExperiment(null);
			}
			try {
				session.delete(this);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	private String ID;
	
	private java.sql.Timestamp expDate;
	
	private pathway.data.persistence.Tool tool;
	
	private pathway.data.persistence.HPCSystem HPCSystem;
	
	private String userName;
	
	private String resultsURI;
	
	private pathway.data.persistence.Application application;
	
	private pathway.data.persistence.HistoricalNotes historicalNotes;
	
	private String jobId;
	
	private String jobState;
	
	private String inputDataFileName;
	
	private int mpiProcs;
	
	private int ompThreads;
	
	private String startupFolder;
	
	private String codeVersion;
	
	private String execCmd;
	
	private String stdOut;
	
	private String stdErr;
	
	private String compileLog;
	
	private String loadedModules;
	
	private String environment;
	
	private String comment;
	
	private String interPhaseDynamism;
	
	private String intraPhaseDynamism;
	
	private String tuningModel;
	
	private java.util.Set<pathway.data.persistence.PscProperty> pscProperty = new java.util.HashSet<pathway.data.persistence.PscProperty>();
	
	private java.util.Set<pathway.data.persistence.ExperimentAddInfo> experimentAddInfo = new java.util.HashSet<pathway.data.persistence.ExperimentAddInfo>();
	
	private void setID(String value) {
		this.ID = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getORMID() {
		return getID();
	}
	
	public void setExpDate(java.sql.Timestamp value) {
		this.expDate = value;
	}
	
	public java.sql.Timestamp getExpDate() {
		return expDate;
	}
	
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setResultsURI(String value) {
		this.resultsURI = value;
	}
	
	public String getResultsURI() {
		return resultsURI;
	}
	
	public void setJobId(String value) {
		this.jobId = value;
	}
	
	public String getJobId() {
		return jobId;
	}
	
	public void setJobState(String value) {
		this.jobState = value;
	}
	
	public String getJobState() {
		return jobState;
	}
	
	public void setInputDataFileName(String value) {
		this.inputDataFileName = value;
	}
	
	public String getInputDataFileName() {
		return inputDataFileName;
	}
	
	public void setMpiProcs(int value) {
		this.mpiProcs = value;
	}
	
	public int getMpiProcs() {
		return mpiProcs;
	}
	
	public void setOmpThreads(int value) {
		this.ompThreads = value;
	}
	
	public int getOmpThreads() {
		return ompThreads;
	}
	
	public void setStartupFolder(String value) {
		this.startupFolder = value;
	}
	
	public String getStartupFolder() {
		return startupFolder;
	}
	
	public void setCodeVersion(String value) {
		this.codeVersion = value;
	}
	
	public String getCodeVersion() {
		return codeVersion;
	}
	
	public void setExecCmd(String value) {
		this.execCmd = value;
	}
	
	public String getExecCmd() {
		return execCmd;
	}
	
	public void setStdOut(String value) {
		this.stdOut = value;
	}
	
	public String getStdOut() {
		return stdOut;
	}
	
	public void setStdErr(String value) {
		this.stdErr = value;
	}
	
	public String getStdErr() {
		return stdErr;
	}
	
	public void setCompileLog(String value) {
		this.compileLog = value;
	}
	
	public String getCompileLog() {
		return compileLog;
	}
	
	public void setLoadedModules(String value) {
		this.loadedModules = value;
	}
	
	public String getLoadedModules() {
		return loadedModules;
	}
	
	public void setEnvironment(String value) {
		this.environment = value;
	}
	
	public String getEnvironment() {
		return environment;
	}
	
	public void setComment(String value) {
		this.comment = value;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setInterPhaseDynamism(String value) {
		this.interPhaseDynamism = value;
	}
	
	public String getInterPhaseDynamism() {
		return interPhaseDynamism;
	}
	
	public void setIntraPhaseDynamism(String value) {
		this.intraPhaseDynamism = value;
	}
	
	public String getIntraPhaseDynamism() {
		return intraPhaseDynamism;
	}
	
	public void setTuningModel(String value) {
		this.tuningModel = value;
	}
	
	public String getTuningModel() {
		return tuningModel;
	}
	
	public void setHPCSystem(pathway.data.persistence.HPCSystem value) {
		this.HPCSystem = value;
	}
	
	public pathway.data.persistence.HPCSystem getHPCSystem() {
		return HPCSystem;
	}
	
	public void setTool(pathway.data.persistence.Tool value) {
		this.tool = value;
	}
	
	public pathway.data.persistence.Tool getTool() {
		return tool;
	}
	
	public void setApplication(pathway.data.persistence.Application value) {
		this.application = value;
	}
	
	public pathway.data.persistence.Application getApplication() {
		return application;
	}
	
	public void setHistoricalNotes(pathway.data.persistence.HistoricalNotes value) {
		this.historicalNotes = value;
	}
	
	public pathway.data.persistence.HistoricalNotes getHistoricalNotes() {
		return historicalNotes;
	}
	
	public void setPscProperty(java.util.Set<pathway.data.persistence.PscProperty> value) {
		this.pscProperty = value;
	}
	
	public java.util.Set<pathway.data.persistence.PscProperty> getPscProperty() {
		return pscProperty;
	}
	
	
	public void setExperimentAddInfo(java.util.Set<pathway.data.persistence.ExperimentAddInfo> value) {
		this.experimentAddInfo = value;
	}
	
	public java.util.Set<pathway.data.persistence.ExperimentAddInfo> getExperimentAddInfo() {
		return experimentAddInfo;
	}
	
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("Experiment[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("ExpDate=").append(getExpDate()).append(" ");
			if (getTool() != null)
				sb.append("Tool.Persist_ID=").append(getTool().toString(true)).append(" ");
			else
				sb.append("Tool=null ");
			if (getHPCSystem() != null)
				sb.append("HPCSystem.Persist_ID=").append(getHPCSystem().toString(true)).append(" ");
			else
				sb.append("HPCSystem=null ");
			sb.append("UserName=").append(getUserName()).append(" ");
			sb.append("ResultsURI=").append(getResultsURI()).append(" ");
			if (getApplication() != null)
				sb.append("Application.Persist_ID=").append(getApplication().toString(true)).append(" ");
			else
				sb.append("Application=null ");
			if (getHistoricalNotes() != null)
				sb.append("HistoricalNotes.Persist_ID=").append(getHistoricalNotes().toString(true)).append(" ");
			else
				sb.append("HistoricalNotes=null ");
			sb.append("JobId=").append(getJobId()).append(" ");
			sb.append("JobState=").append(getJobState()).append(" ");
			sb.append("InputDataFileName=").append(getInputDataFileName()).append(" ");
			sb.append("MpiProcs=").append(getMpiProcs()).append(" ");
			sb.append("OmpThreads=").append(getOmpThreads()).append(" ");
			sb.append("StartupFolder=").append(getStartupFolder()).append(" ");
			sb.append("CodeVersion=").append(getCodeVersion()).append(" ");
			sb.append("ExecCmd=").append(getExecCmd()).append(" ");
			sb.append("StdOut=").append(getStdOut()).append(" ");
			sb.append("StdErr=").append(getStdErr()).append(" ");
			sb.append("CompileLog=").append(getCompileLog()).append(" ");
			sb.append("LoadedModules=").append(getLoadedModules()).append(" ");
			sb.append("Environment=").append(getEnvironment()).append(" ");
			sb.append("Comment=").append(getComment()).append(" ");
			sb.append("InterPhaseDynamism=").append(getInterPhaseDynamism()).append(" ");
			sb.append("IntraPhaseDynamism=").append(getIntraPhaseDynamism()).append(" ");
			sb.append("TuningModel=").append(getTuningModel()).append(" ");
			sb.append("PscProperty.size=").append(getPscProperty().size()).append(" ");
			sb.append("ExperimentAddInfo.size=").append(getExperimentAddInfo().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final String PROP_ID = "ID";
	public static final String PROP_EXP_DATE = "expDate";
	public static final String PROP_TOOL = "tool";
	public static final String PROP_HPCSYSTEM = "HPCSystem";
	public static final String PROP_USER_NAME = "userName";
	public static final String PROP_RESULTS_URI = "resultsURI";
	public static final String PROP_APPLICATION = "application";
	public static final String PROP_HISTORICAL_NOTES = "historicalNotes";
	public static final String PROP_JOB_ID = "jobId";
	public static final String PROP_JOB_STATE = "jobState";
	public static final String PROP_INPUT_DATA_FILE_NAME = "inputDataFileName";
	public static final String PROP_MPI_PROCS = "mpiProcs";
	public static final String PROP_OMP_THREADS = "ompThreads";
	public static final String PROP_STARTUP_FOLDER = "startupFolder";
	public static final String PROP_CODE_VERSION = "codeVersion";
	public static final String PROP_EXEC_CMD = "execCmd";
	public static final String PROP_STD_OUT = "stdOut";
	public static final String PROP_STD_ERR = "stdErr";
	public static final String PROP_COMPILE_LOG = "compileLog";
	public static final String PROP_LOADED_MODULES = "loadedModules";
	public static final String PROP_ENVIRONMENT = "environment";
	public static final String PROP_COMMENT = "comment";
	public static final String PROP_INTER_PHASE_DYNAMISM = "interPhaseDynamism";
	public static final String PROP_INTRA_PHASE_DYNAMISM = "intraPhaseDynamism";
	public static final String PROP_TUNING_MODEL = "tuningModel";
	public static final String PROP_PSC_PROPERTY = "pscProperty";
	public static final String PROP_EXPERIMENT_ADD_INFO = "experimentAddInfo";
}
