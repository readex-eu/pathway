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

import java.io.Serializable;
@SuppressWarnings({ "all", "unchecked" })
public class Application implements Serializable {
	public Application() {
	}
	
	public static Application loadApplicationByORMID(String name, String config) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadApplicationByORMID(session, name, config);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application getApplicationByORMID(String name, String config) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getApplicationByORMID(session, name, config);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application loadApplicationByORMID(String name, String config, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadApplicationByORMID(session, name, config, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application getApplicationByORMID(String name, String config, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getApplicationByORMID(session, name, config, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application loadApplicationByORMID(PersistentSession session, String name, String config) throws PersistentException {
		try {
			Application application = new pathway.data.persistence.Application();
			application.setName(name);
			application.setConfig(config);
			
			return (Application) session.load(pathway.data.persistence.Application.class, application);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application getApplicationByORMID(PersistentSession session, String name, String config) throws PersistentException {
		try {
			Application application = new pathway.data.persistence.Application();
			application.setName(name);
			application.setConfig(config);
			
			return (Application) session.get(pathway.data.persistence.Application.class, application);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application loadApplicationByORMID(PersistentSession session, String name, String config, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			Application application = new pathway.data.persistence.Application();
			application.setName(name);
			application.setConfig(config);
			
			return (Application) session.load(pathway.data.persistence.Application.class, application, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application getApplicationByORMID(PersistentSession session, String name, String config, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			Application application = new pathway.data.persistence.Application();
			application.setName(name);
			application.setConfig(config);
			
			return (Application) session.get(pathway.data.persistence.Application.class, application, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryApplication(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryApplication(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryApplication(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryApplication(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application[] listApplicationByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listApplicationByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application[] listApplicationByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listApplicationByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryApplication(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Application as Application");
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
	
	public static List queryApplication(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Application as Application");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Application", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application[] listApplicationByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryApplication(session, condition, orderBy);
			return (Application[]) list.toArray(new Application[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application[] listApplicationByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryApplication(session, condition, orderBy, lockMode);
			return (Application[]) list.toArray(new Application[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application loadApplicationByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadApplicationByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application loadApplicationByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadApplicationByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application loadApplicationByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Application[] applications = listApplicationByQuery(session, condition, orderBy);
		if (applications != null && applications.length > 0)
			return applications[0];
		else
			return null;
	}
	
	public static Application loadApplicationByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Application[] applications = listApplicationByQuery(session, condition, orderBy, lockMode);
		if (applications != null && applications.length > 0)
			return applications[0];
		else
			return null;
	}
	
	public static java.util.Iterator<Application> iterateApplicationByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateApplicationByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<Application> iterateApplicationByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateApplicationByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<Application> iterateApplicationByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Application as Application");
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
	
	public static java.util.Iterator<Application> iterateApplicationByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Application as Application");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Application", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Application loadApplicationByCriteria(ApplicationCriteria applicationCriteria) {
		Application[] applications = listApplicationByCriteria(applicationCriteria);
		if(applications == null || applications.length == 0) {
			return null;
		}
		return applications[0];
	}
	
	public static Application[] listApplicationByCriteria(ApplicationCriteria applicationCriteria) {
		return applicationCriteria.listApplication();
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof Application))
			return false;
		Application application = (Application)aObj;
		if ((getName() != null && !getName().equals(application.getName())) || (getName() == null && application.getName() != null))
			return false;
		if ((getConfig() != null && !getConfig().equals(application.getConfig())) || (getConfig() == null && application.getConfig() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getName() == null ? 0 : getName().hashCode());
		hashcode = hashcode + (getConfig() == null ? 0 : getConfig().hashCode());
		return hashcode;
	}
	
	public static Application createApplication() {
		return new pathway.data.persistence.Application();
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
			pathway.data.persistence.PscRegion[] lPscRegions = (pathway.data.persistence.PscRegion[])getPscRegion().toArray(new pathway.data.persistence.PscRegion[getPscRegion().size()]);
			for(int i = 0; i < lPscRegions.length; i++) {
				lPscRegions[i].setFK_Application(null);
			}
			pathway.data.persistence.Experiment[] lExperiments = (pathway.data.persistence.Experiment[])getExperiment().toArray(new pathway.data.persistence.Experiment[getExperiment().size()]);
			for(int i = 0; i < lExperiments.length; i++) {
				lExperiments[i].setApplication(null);
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
			pathway.data.persistence.PscRegion[] lPscRegions = (pathway.data.persistence.PscRegion[])getPscRegion().toArray(new pathway.data.persistence.PscRegion[getPscRegion().size()]);
			for(int i = 0; i < lPscRegions.length; i++) {
				lPscRegions[i].setFK_Application(null);
			}
			pathway.data.persistence.Experiment[] lExperiments = (pathway.data.persistence.Experiment[])getExperiment().toArray(new pathway.data.persistence.Experiment[getExperiment().size()]);
			for(int i = 0; i < lExperiments.length; i++) {
				lExperiments[i].setApplication(null);
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
	
	private String name;
	
	private String config;
	
	private String executable;
	
	private String inputDataFileNames;
	
	private String startArgs;
	
	private String currentCodeVersion;
	
	private String codeLocation;
	
	private String execLocation;
	
	private String reqModules;
	
	private String reqEnvVars;
	
	private String eclipseProject;
	
	private int wallclockLimit;
	
	private String interPhaseDynamism;
	
	private String intraPhaseDynamism;
	
	private String tuningPotential;
	
	private String DTATuningModel;
	
	private String RATSwitchingDecisions;
	
	private String calibratedTuningModel;
	
	private String phaseRegionName;
	
	private String tuneParamNames;
	
	private String CPUFreqParamValues;
	
	private String uncoreFreqParamValues;
	
	private boolean CPUFreqParamEnable = false;
	
	private boolean uncoreFreqParamEnable = false;
	
	private Integer info;
	
	private String selectiveInfo;
	
	private String OMPThreadsParamCount;
	
	private boolean OMPThreadsParamEnable;
	
	private boolean ATPlibParamEnable;
	
	private boolean ATPexhaustive;
	
	private boolean energy;
	
	private boolean time;
	
	private boolean EDP;
	
	private boolean ED2P;
	
	private boolean CPUEnergy;
	
	private boolean TCO;
	
	private String costPerJoule;
	
	private String costPerCoreHour;
	
	private boolean exhaustive;
	
	private boolean random;
	
	private boolean individual;
	
	private boolean genetic;
	
	private String samples;
	
	private String keep;
	
	private String population;
	
	private String maxGenerations;
	
	private String timer;
	
	private boolean normalizedEnergy;
	
	private boolean normalizedTime;
	
	private boolean normalizedEDP;
	
	private boolean normalizedED2P;
	
	private boolean normalizedCPUEnergy;
	
	private boolean normalizedTCO;
	
	private String energyPlugName;
	
	private String energyMetNames;
	
	private String ratInputDataFileName;
	
	private boolean ATPindividual;
	
	private java.util.Set<pathway.data.persistence.PscRegion> pscRegion = new java.util.HashSet<pathway.data.persistence.PscRegion>();
	
	private java.util.Set<pathway.data.persistence.Experiment> experiment = new java.util.HashSet<pathway.data.persistence.Experiment>();
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setInputDataFileNames(String value) {
		this.inputDataFileNames = value;
	}
	
	public String getInputDataFileNames() {
		return inputDataFileNames;
	}
	
	public void setStartArgs(String value) {
		this.startArgs = value;
	}
	
	public String getStartArgs() {
		return startArgs;
	}
	
	public void setReqModules(String value) {
		this.reqModules = value;
	}
	
	public String getReqModules() {
		return reqModules;
	}
	
	public void setReqEnvVars(String value) {
		this.reqEnvVars = value;
	}
	
	public String getReqEnvVars() {
		return reqEnvVars;
	}
	
	public void setCurrentCodeVersion(String value) {
		this.currentCodeVersion = value;
	}
	
	public String getCurrentCodeVersion() {
		return currentCodeVersion;
	}
	
	public void setConfig(String value) {
		this.config = value;
	}
	
	public String getConfig() {
		return config;
	}
	
	public void setExecutable(String value) {
		this.executable = value;
	}
	
	public String getExecutable() {
		return executable;
	}
	
	public void setCodeLocation(String value) {
		this.codeLocation = value;
	}
	
	public String getCodeLocation() {
		return codeLocation;
	}
	
	public void setExecLocation(String value) {
		this.execLocation = value;
	}
	
	public String getExecLocation() {
		return execLocation;
	}
	
	public void setEclipseProject(String value) {
		this.eclipseProject = value;
	}
	
	public String getEclipseProject() {
		return eclipseProject;
	}
	
	public void setWallclockLimit(int value) {
		this.wallclockLimit = value;
	}
	
	public int getWallclockLimit() {
		return wallclockLimit;
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
	
	public void setTuningPotential(String value) {
		this.tuningPotential = value;
	}
	
	public String getTuningPotential() {
		return tuningPotential;
	}
	
	public void setDTATuningModel(String value) {
		this.DTATuningModel = value;
	}
	
	public String getDTATuningModel() {
		return DTATuningModel;
	}
	
	public void setRATSwitchingDecisions(String value) {
		this.RATSwitchingDecisions = value;
	}
	
	public String getRATSwitchingDecisions() {
		return RATSwitchingDecisions;
	}
	
	public void setCalibratedTuningModel(String value) {
		this.calibratedTuningModel = value;
	}
	
	public String getCalibratedTuningModel() {
		return calibratedTuningModel;
	}
	
	public void setPhaseRegionName(String value) {
		this.phaseRegionName = value;
	}
	
	public String getPhaseRegionName() {
		return phaseRegionName;
	}
	
	public void setTuneParamNames(String value) {
		this.tuneParamNames = value;
	}
	
	public String getTuneParamNames() {
		return tuneParamNames;
	}
	
	public void setCPUFreqParamValues(String value) {
		this.CPUFreqParamValues = value;
	}
	
	public String getCPUFreqParamValues() {
		return CPUFreqParamValues;
	}
	
	public void setInfo(int value) {
		setInfo(new Integer(value));
	}
	
	public void setInfo(Integer value) {
		this.info = value;
	}
	
	public Integer getInfo() {
		return info;
	}
	
	public void setSelectiveInfo(String value) {
		this.selectiveInfo = value;
	}
	
	public String getSelectiveInfo() {
		return selectiveInfo;
	}
	
	public void setCPUFreqParamEnable(boolean value) {
		this.CPUFreqParamEnable = value;
	}
	
	public boolean getCPUFreqParamEnable() {
		return CPUFreqParamEnable;
	}
	
	public void setUncoreFreqParamEnable(boolean value) {
		this.uncoreFreqParamEnable = value;
	}
	
	public boolean getUncoreFreqParamEnable() {
		return uncoreFreqParamEnable;
	}
	
	public void setUncoreFreqParamValues(String value) {
		this.uncoreFreqParamValues = value;
	}
	
	public String getUncoreFreqParamValues() {
		return uncoreFreqParamValues;
	}
	
	public void setOMPThreadsParamCount(String value) {
		this.OMPThreadsParamCount = value;
	}
	
	public String getOMPThreadsParamCount() {
		return OMPThreadsParamCount;
	}
	
	public void setOMPThreadsParamEnable(boolean value) {
		this.OMPThreadsParamEnable = value;
	}
	
	public boolean getOMPThreadsParamEnable() {
		return OMPThreadsParamEnable;
	}
	
	public void setATPlibParamEnable(boolean value) {
		this.ATPlibParamEnable = value;
	}
	
	public boolean getATPlibParamEnable() {
		return ATPlibParamEnable;
	}
	
	public void setATPexhaustive(boolean value) {
		this.ATPexhaustive = value;
	}
	
	public boolean getATPexhaustive() {
		return ATPexhaustive;
	}
	
	public void setEnergy(boolean value) {
		this.energy = value;
	}
	
	public boolean getEnergy() {
		return energy;
	}
	
	public void setTime(boolean value) {
		this.time = value;
	}
	
	public boolean getTime() {
		return time;
	}
	
	public void setEDP(boolean value) {
		this.EDP = value;
	}
	
	public boolean getEDP() {
		return EDP;
	}
	
	public void setED2P(boolean value) {
		this.ED2P = value;
	}
	
	public boolean getED2P() {
		return ED2P;
	}
	
	public void setCPUEnergy(boolean value) {
		this.CPUEnergy = value;
	}
	
	public boolean getCPUEnergy() {
		return CPUEnergy;
	}
	
	public void setTCO(boolean value) {
		this.TCO = value;
	}
	
	public boolean getTCO() {
		return TCO;
	}
	
	public void setCostPerJoule(String value) {
		this.costPerJoule = value;
	}
	
	public String getCostPerJoule() {
		return costPerJoule;
	}
	
	public void setCostPerCoreHour(String value) {
		this.costPerCoreHour = value;
	}
	
	public String getCostPerCoreHour() {
		return costPerCoreHour;
	}
	
	public void setExhaustive(boolean value) {
		this.exhaustive = value;
	}
	
	public boolean getExhaustive() {
		return exhaustive;
	}
	
	public void setRandom(boolean value) {
		this.random = value;
	}
	
	public boolean getRandom() {
		return random;
	}
	
	public void setIndividual(boolean value) {
		this.individual = value;
	}
	
	public boolean getIndividual() {
		return individual;
	}
	
	public void setGenetic(boolean value) {
		this.genetic = value;
	}
	
	public boolean getGenetic() {
		return genetic;
	}
	
	public void setSamples(String value) {
		this.samples = value;
	}
	
	public String getSamples() {
		return samples;
	}
	
	public void setKeep(String value) {
		this.keep = value;
	}
	
	public String getKeep() {
		return keep;
	}
	
	public void setPopulation(String value) {
		this.population = value;
	}
	
	public String getPopulation() {
		return population;
	}
	
	public void setMaxGenerations(String value) {
		this.maxGenerations = value;
	}
	
	public String getMaxGenerations() {
		return maxGenerations;
	}
	
	public void setTimer(String value) {
		this.timer = value;
	}
	
	public String getTimer() {
		return timer;
	}
	
	public void setNormalizedEnergy(boolean value) {
		this.normalizedEnergy = value;
	}
	
	public boolean getNormalizedEnergy() {
		return normalizedEnergy;
	}
	
	public void setNormalizedTime(boolean value) {
		this.normalizedTime = value;
	}
	
	public boolean getNormalizedTime() {
		return normalizedTime;
	}
	
	public void setNormalizedEDP(boolean value) {
		this.normalizedEDP = value;
	}
	
	public boolean getNormalizedEDP() {
		return normalizedEDP;
	}
	
	public void setNormalizedED2P(boolean value) {
		this.normalizedED2P = value;
	}
	
	public boolean getNormalizedED2P() {
		return normalizedED2P;
	}
	
	public void setNormalizedCPUEnergy(boolean value) {
		this.normalizedCPUEnergy = value;
	}
	
	public boolean getNormalizedCPUEnergy() {
		return normalizedCPUEnergy;
	}
	
	public void setNormalizedTCO(boolean value) {
		this.normalizedTCO = value;
	}
	
	public boolean getNormalizedTCO() {
		return normalizedTCO;
	}
	
	public void setEnergyPlugName(String value) {
		this.energyPlugName = value;
	}
	
	public String getEnergyPlugName() {
		return energyPlugName;
	}
	
	public void setEnergyMetNames(String value) {
		this.energyMetNames = value;
	}
	
	public String getEnergyMetNames() {
		return energyMetNames;
	}
	
	public void setRatInputDataFileName(String value) {
		this.ratInputDataFileName = value;
	}
	
	public String getRatInputDataFileName() {
		return ratInputDataFileName;
	}
	
	public void setATPindividual(boolean value) {
		this.ATPindividual = value;
	}
	
	public boolean getATPindividual() {
		return ATPindividual;
	}
	
	public void setPscRegion(java.util.Set<pathway.data.persistence.PscRegion> value) {
		this.pscRegion = value;
	}
	
	public java.util.Set<pathway.data.persistence.PscRegion> getPscRegion() {
		return pscRegion;
	}
	
	
	public void setExperiment(java.util.Set<pathway.data.persistence.Experiment> value) {
		this.experiment = value;
	}
	
	public java.util.Set<pathway.data.persistence.Experiment> getExperiment() {
		return experiment;
	}
	
	
	private String OMPThreadsParamLowerValue;
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getName() + " " + getConfig());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("Application[ ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("Config=").append(getConfig()).append(" ");
			sb.append("Executable=").append(getExecutable()).append(" ");
			sb.append("InputDataFileNames=").append(getInputDataFileNames()).append(" ");
			sb.append("StartArgs=").append(getStartArgs()).append(" ");
			sb.append("CurrentCodeVersion=").append(getCurrentCodeVersion()).append(" ");
			sb.append("CodeLocation=").append(getCodeLocation()).append(" ");
			sb.append("ExecLocation=").append(getExecLocation()).append(" ");
			sb.append("ReqModules=").append(getReqModules()).append(" ");
			sb.append("ReqEnvVars=").append(getReqEnvVars()).append(" ");
			sb.append("EclipseProject=").append(getEclipseProject()).append(" ");
			sb.append("WallclockLimit=").append(getWallclockLimit()).append(" ");
			sb.append("InterPhaseDynamism=").append(getInterPhaseDynamism()).append(" ");
			sb.append("IntraPhaseDynamism=").append(getIntraPhaseDynamism()).append(" ");
			sb.append("TuningPotential=").append(getTuningPotential()).append(" ");
			sb.append("DTATuningModel=").append(getDTATuningModel()).append(" ");
			sb.append("RATSwitchingDecisions=").append(getRATSwitchingDecisions()).append(" ");
			sb.append("CalibratedTuningModel=").append(getCalibratedTuningModel()).append(" ");
			sb.append("PhaseRegionName=").append(getPhaseRegionName()).append(" ");
			sb.append("TuneParamNames=").append(getTuneParamNames()).append(" ");
			sb.append("CPUFreqParamValues=").append(getCPUFreqParamValues()).append(" ");
			sb.append("UncoreFreqParamValues=").append(getUncoreFreqParamValues()).append(" ");
			sb.append("CPUFreqParamEnable=").append(getCPUFreqParamEnable()).append(" ");
			sb.append("UncoreFreqParamEnable=").append(getUncoreFreqParamEnable()).append(" ");
			sb.append("Info=").append(getInfo()).append(" ");
			sb.append("SelectiveInfo=").append(getSelectiveInfo()).append(" ");
			sb.append("OMPThreadsParamCount=").append(getOMPThreadsParamCount()).append(" ");
			sb.append("OMPThreadsParamEnable=").append(getOMPThreadsParamEnable()).append(" ");
			sb.append("ATPlibParamEnable=").append(getATPlibParamEnable()).append(" ");
			sb.append("ATPexhaustive=").append(getATPexhaustive()).append(" ");
			sb.append("Energy=").append(getEnergy()).append(" ");
			sb.append("Time=").append(getTime()).append(" ");
			sb.append("EDP=").append(getEDP()).append(" ");
			sb.append("ED2P=").append(getED2P()).append(" ");
			sb.append("CPUEnergy=").append(getCPUEnergy()).append(" ");
			sb.append("TCO=").append(getTCO()).append(" ");
			sb.append("CostPerJoule=").append(getCostPerJoule()).append(" ");
			sb.append("CostPerCoreHour=").append(getCostPerCoreHour()).append(" ");
			sb.append("Exhaustive=").append(getExhaustive()).append(" ");
			sb.append("Random=").append(getRandom()).append(" ");
			sb.append("Individual=").append(getIndividual()).append(" ");
			sb.append("Genetic=").append(getGenetic()).append(" ");
			sb.append("Samples=").append(getSamples()).append(" ");
			sb.append("Keep=").append(getKeep()).append(" ");
			sb.append("Population=").append(getPopulation()).append(" ");
			sb.append("MaxGenerations=").append(getMaxGenerations()).append(" ");
			sb.append("Timer=").append(getTimer()).append(" ");
			sb.append("NormalizedEnergy=").append(getNormalizedEnergy()).append(" ");
			sb.append("NormalizedTime=").append(getNormalizedTime()).append(" ");
			sb.append("NormalizedEDP=").append(getNormalizedEDP()).append(" ");
			sb.append("NormalizedED2P=").append(getNormalizedED2P()).append(" ");
			sb.append("NormalizedCPUEnergy=").append(getNormalizedCPUEnergy()).append(" ");
			sb.append("NormalizedTCO=").append(getNormalizedTCO()).append(" ");
			sb.append("EnergyPlugName=").append(getEnergyPlugName()).append(" ");
			sb.append("EnergyMetNames=").append(getEnergyMetNames()).append(" ");
			sb.append("RatInputDataFileName=").append(getRatInputDataFileName()).append(" ");
			sb.append("ATPindividual=").append(getATPindividual()).append(" ");
			sb.append("PscRegion.size=").append(getPscRegion().size()).append(" ");
			sb.append("Experiment.size=").append(getExperiment().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	private boolean _saved = false;
	
	public void onSave() {
		_saved=true;
	}
	
	
	public void onLoad() {
		_saved=true;
	}
	
	
	public boolean isSaved() {
		return _saved;
	}
	
	
	public static final String PROP_NAME = "name";
	public static final String PROP_CONFIG = "config";
	public static final String PROP_EXECUTABLE = "executable";
	public static final String PROP_INPUT_DATA_FILE_NAMES = "inputDataFileNames";
	public static final String PROP_START_ARGS = "startArgs";
	public static final String PROP_CURRENT_CODE_VERSION = "currentCodeVersion";
	public static final String PROP_CODE_LOCATION = "codeLocation";
	public static final String PROP_EXEC_LOCATION = "execLocation";
	public static final String PROP_REQ_MODULES = "reqModules";
	public static final String PROP_REQ_ENV_VARS = "reqEnvVars";
	public static final String PROP_ECLIPSE_PROJECT = "eclipseProject";
	public static final String PROP_WALLCLOCK_LIMIT = "wallclockLimit";
	public static final String PROP_INTER_PHASE_DYNAMISM = "interPhaseDynamism";
	public static final String PROP_INTRA_PHASE_DYNAMISM = "intraPhaseDynamism";
	public static final String PROP_TUNING_POTENTIAL = "tuningPotential";
	public static final String PROP_DTATUNING_MODEL = "DTATuningModel";
	public static final String PROP_RATSWITCHING_DECISIONS = "RATSwitchingDecisions";
	public static final String PROP_CALIBRATED_TUNING_MODEL = "calibratedTuningModel";
	public static final String PROP_PHASE_REGION_NAME = "phaseRegionName";
	public static final String PROP_TUNE_PARAM_NAMES = "tuneParamNames";
	public static final String PROP_CPUFREQ_PARAM_VALUES = "CPUFreqParamValues";
	public static final String PROP_UNCORE_FREQ_PARAM_VALUES = "uncoreFreqParamValues";
	public static final String PROP_CPUFREQ_PARAM_ENABLE = "CPUFreqParamEnable";
	public static final String PROP_UNCORE_FREQ_PARAM_ENABLE = "uncoreFreqParamEnable";
	public static final String PROP_INFO = "info";
	public static final String PROP_SELECTIVE_INFO = "selectiveInfo";
	public static final String PROP_OMPTHREADS_PARAM_COUNT = "OMPThreadsParamCount";
	public static final String PROP_OMPTHREADS_PARAM_ENABLE = "OMPThreadsParamEnable";
	public static final String PROP_ATPLIB_PARAM_ENABLE = "ATPlibParamEnable";
	public static final String PROP_ATPEXHAUSTIVE = "ATPexhaustive";
	public static final String PROP_ENERGY = "energy";
	public static final String PROP_TIME = "time";
	public static final String PROP_EDP = "EDP";
	public static final String PROP_ED2_P = "ED2P";
	public static final String PROP_CPUENERGY = "CPUEnergy";
	public static final String PROP_TCO = "TCO";
	public static final String PROP_COST_PER_JOULE = "costPerJoule";
	public static final String PROP_COST_PER_CORE_HOUR = "costPerCoreHour";
	public static final String PROP_EXHAUSTIVE = "exhaustive";
	public static final String PROP_RANDOM = "random";
	public static final String PROP_INDIVIDUAL = "individual";
	public static final String PROP_GENETIC = "genetic";
	public static final String PROP_SAMPLES = "samples";
	public static final String PROP_KEEP = "keep";
	public static final String PROP_POPULATION = "population";
	public static final String PROP_MAX_GENERATIONS = "maxGenerations";
	public static final String PROP_TIMER = "timer";
	public static final String PROP_NORMALIZED_ENERGY = "normalizedEnergy";
	public static final String PROP_NORMALIZED_TIME = "normalizedTime";
	public static final String PROP_NORMALIZED_EDP = "normalizedEDP";
	public static final String PROP_NORMALIZED_ED2_P = "normalizedED2P";
	public static final String PROP_NORMALIZED_CPUENERGY = "normalizedCPUEnergy";
	public static final String PROP_NORMALIZED_TCO = "normalizedTCO";
	public static final String PROP_ENERGY_PLUG_NAME = "energyPlugName";
	public static final String PROP_ENERGY_MET_NAMES = "energyMetNames";
	public static final String PROP_RAT_INPUT_DATA_FILE_NAME = "ratInputDataFileName";
	public static final String PROP_ATPINDIVIDUAL = "ATPindividual";
	public static final String PROP_PSC_REGION = "pscRegion";
	public static final String PROP_EXPERIMENT = "experiment";
}
