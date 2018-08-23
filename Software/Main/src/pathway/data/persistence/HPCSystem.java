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
public class HPCSystem {
	public HPCSystem() {
	}
	
	public static HPCSystem loadHPCSystemByORMID(String name) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHPCSystemByORMID(session, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem getHPCSystemByORMID(String name) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getHPCSystemByORMID(session, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem loadHPCSystemByORMID(String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHPCSystemByORMID(session, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem getHPCSystemByORMID(String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getHPCSystemByORMID(session, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem loadHPCSystemByORMID(PersistentSession session, String name) throws PersistentException {
		try {
			return (HPCSystem) session.load(pathway.data.persistence.HPCSystem.class, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem getHPCSystemByORMID(PersistentSession session, String name) throws PersistentException {
		try {
			return (HPCSystem) session.get(pathway.data.persistence.HPCSystem.class, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem loadHPCSystemByORMID(PersistentSession session, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (HPCSystem) session.load(pathway.data.persistence.HPCSystem.class, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem getHPCSystemByORMID(PersistentSession session, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (HPCSystem) session.get(pathway.data.persistence.HPCSystem.class, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHPCSystem(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryHPCSystem(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHPCSystem(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryHPCSystem(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem[] listHPCSystemByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listHPCSystemByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem[] listHPCSystemByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listHPCSystemByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHPCSystem(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem as HPCSystem");
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
	
	public static List queryHPCSystem(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem as HPCSystem");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("HPCSystem", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem[] listHPCSystemByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryHPCSystem(session, condition, orderBy);
			return (HPCSystem[]) list.toArray(new HPCSystem[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem[] listHPCSystemByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryHPCSystem(session, condition, orderBy, lockMode);
			return (HPCSystem[]) list.toArray(new HPCSystem[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem loadHPCSystemByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHPCSystemByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem loadHPCSystemByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHPCSystemByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem loadHPCSystemByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		HPCSystem[] hPCSystems = listHPCSystemByQuery(session, condition, orderBy);
		if (hPCSystems != null && hPCSystems.length > 0)
			return hPCSystems[0];
		else
			return null;
	}
	
	public static HPCSystem loadHPCSystemByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		HPCSystem[] hPCSystems = listHPCSystemByQuery(session, condition, orderBy, lockMode);
		if (hPCSystems != null && hPCSystems.length > 0)
			return hPCSystems[0];
		else
			return null;
	}
	
	public static java.util.Iterator<HPCSystem> iterateHPCSystemByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateHPCSystemByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<HPCSystem> iterateHPCSystemByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateHPCSystemByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<HPCSystem> iterateHPCSystemByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem as HPCSystem");
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
	
	public static java.util.Iterator<HPCSystem> iterateHPCSystemByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem as HPCSystem");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("HPCSystem", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem loadHPCSystemByCriteria(HPCSystemCriteria hPCSystemCriteria) {
		HPCSystem[] hPCSystems = listHPCSystemByCriteria(hPCSystemCriteria);
		if(hPCSystems == null || hPCSystems.length == 0) {
			return null;
		}
		return hPCSystems[0];
	}
	
	public static HPCSystem[] listHPCSystemByCriteria(HPCSystemCriteria hPCSystemCriteria) {
		return hPCSystemCriteria.listHPCSystem();
	}
	
	public static HPCSystem createHPCSystem() {
		return new pathway.data.persistence.HPCSystem();
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
			pathway.data.persistence.Experiment[] lExperiments = (pathway.data.persistence.Experiment[])getExperiment().toArray(new pathway.data.persistence.Experiment[getExperiment().size()]);
			for(int i = 0; i < lExperiments.length; i++) {
				lExperiments[i].setHPCSystem(null);
			}
			if(getHPCSystems_CPU() != null) {
				getHPCSystems_CPU().setName(null);
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
			pathway.data.persistence.Experiment[] lExperiments = (pathway.data.persistence.Experiment[])getExperiment().toArray(new pathway.data.persistence.Experiment[getExperiment().size()]);
			for(int i = 0; i < lExperiments.length; i++) {
				lExperiments[i].setHPCSystem(null);
			}
			if(getHPCSystems_CPU() != null) {
				getHPCSystems_CPU().setName(null);
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
	
	private String organisation;
	
	private String website;
	
	private String batchSystem;
	
	private Integer totalCores;
	
	private Integer nodes;
	
	private Integer processorsPerNode;
	
	private Float systemPeakPerformance;
	
	private Float memoryPerCore;
	
	private String fileSystem;
	
	private String netTechnology;
	
	private String netTopology;
	
	private String availableModules;
	
	private java.util.Set<pathway.data.persistence.Experiment> experiment = new java.util.HashSet<pathway.data.persistence.Experiment>();
	
	private pathway.data.persistence.HPCSystem_CPU HPCSystems_CPU;
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public String getORMID() {
		return getName();
	}
	
	public void setOrganisation(String value) {
		this.organisation = value;
	}
	
	public String getOrganisation() {
		return organisation;
	}
	
	public void setWebsite(String value) {
		this.website = value;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setBatchSystem(String value) {
		this.batchSystem = value;
	}
	
	public String getBatchSystem() {
		return batchSystem;
	}
	
	public void setTotalCores(int value) {
		setTotalCores(new Integer(value));
	}
	
	public void setTotalCores(Integer value) {
		this.totalCores = value;
	}
	
	public Integer getTotalCores() {
		return totalCores;
	}
	
	public void setNodes(int value) {
		setNodes(new Integer(value));
	}
	
	public void setNodes(Integer value) {
		this.nodes = value;
	}
	
	public Integer getNodes() {
		return nodes;
	}
	
	public void setProcessorsPerNode(int value) {
		setProcessorsPerNode(new Integer(value));
	}
	
	public void setProcessorsPerNode(Integer value) {
		this.processorsPerNode = value;
	}
	
	public Integer getProcessorsPerNode() {
		return processorsPerNode;
	}
	
	/**
	 * Peak performance in TFlop/s
	 */
	public void setSystemPeakPerformance(float value) {
		setSystemPeakPerformance(new Float(value));
	}
	
	/**
	 * Peak performance in TFlop/s
	 */
	public void setSystemPeakPerformance(Float value) {
		this.systemPeakPerformance = value;
	}
	
	/**
	 * Peak performance in TFlop/s
	 */
	public Float getSystemPeakPerformance() {
		return systemPeakPerformance;
	}
	
	/**
	 * Available memory per core in GBytes
	 */
	public void setMemoryPerCore(float value) {
		setMemoryPerCore(new Float(value));
	}
	
	/**
	 * Available memory per core in GBytes
	 */
	public void setMemoryPerCore(Float value) {
		this.memoryPerCore = value;
	}
	
	/**
	 * Available memory per core in GBytes
	 */
	public Float getMemoryPerCore() {
		return memoryPerCore;
	}
	
	public void setFileSystem(String value) {
		this.fileSystem = value;
	}
	
	public String getFileSystem() {
		return fileSystem;
	}
	
	public void setNetTechnology(String value) {
		this.netTechnology = value;
	}
	
	public String getNetTechnology() {
		return netTechnology;
	}
	
	public void setNetTopology(String value) {
		this.netTopology = value;
	}
	
	public String getNetTopology() {
		return netTopology;
	}
	
	public void setAvailableModules(String value) {
		this.availableModules = value;
	}
	
	public String getAvailableModules() {
		return availableModules;
	}
	
	public void setExperiment(java.util.Set<pathway.data.persistence.Experiment> value) {
		this.experiment = value;
	}
	
	public java.util.Set<pathway.data.persistence.Experiment> getExperiment() {
		return experiment;
	}
	
	
	public void setHPCSystems_CPU(pathway.data.persistence.HPCSystem_CPU value) {
		this.HPCSystems_CPU = value;
	}
	
	public pathway.data.persistence.HPCSystem_CPU getHPCSystems_CPU() {
		return HPCSystems_CPU;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getName());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("HPCSystem[ ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("Organisation=").append(getOrganisation()).append(" ");
			sb.append("Website=").append(getWebsite()).append(" ");
			sb.append("BatchSystem=").append(getBatchSystem()).append(" ");
			sb.append("TotalCores=").append(getTotalCores()).append(" ");
			sb.append("Nodes=").append(getNodes()).append(" ");
			sb.append("ProcessorsPerNode=").append(getProcessorsPerNode()).append(" ");
			sb.append("SystemPeakPerformance=").append(getSystemPeakPerformance()).append(" ");
			sb.append("MemoryPerCore=").append(getMemoryPerCore()).append(" ");
			sb.append("FileSystem=").append(getFileSystem()).append(" ");
			sb.append("NetTechnology=").append(getNetTechnology()).append(" ");
			sb.append("NetTopology=").append(getNetTopology()).append(" ");
			sb.append("AvailableModules=").append(getAvailableModules()).append(" ");
			sb.append("Experiment.size=").append(getExperiment().size()).append(" ");
			if (getHPCSystems_CPU() != null)
				sb.append("HPCSystems_CPU.Persist_ID=").append(getHPCSystems_CPU().toString(true)).append(" ");
			else
				sb.append("HPCSystems_CPU=null ");
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
	public static final String PROP_ORGANISATION = "organisation";
	public static final String PROP_WEBSITE = "website";
	public static final String PROP_BATCH_SYSTEM = "batchSystem";
	public static final String PROP_TOTAL_CORES = "totalCores";
	public static final String PROP_NODES = "nodes";
	public static final String PROP_PROCESSORS_PER_NODE = "processorsPerNode";
	public static final String PROP_SYSTEM_PEAK_PERFORMANCE = "systemPeakPerformance";
	public static final String PROP_MEMORY_PER_CORE = "memoryPerCore";
	public static final String PROP_FILE_SYSTEM = "fileSystem";
	public static final String PROP_NET_TECHNOLOGY = "netTechnology";
	public static final String PROP_NET_TOPOLOGY = "netTopology";
	public static final String PROP_AVAILABLE_MODULES = "availableModules";
	public static final String PROP_EXPERIMENT = "experiment";
	public static final String PROP_HPCSYSTEMS__CPU = "HPCSystems_CPU";
}
