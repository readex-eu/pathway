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
public class HPCSystem_CPU {
	public HPCSystem_CPU() {
	}
	
	public static List queryHPCSystem_CPU(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryHPCSystem_CPU(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHPCSystem_CPU(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryHPCSystem_CPU(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU[] listHPCSystem_CPUByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listHPCSystem_CPUByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU[] listHPCSystem_CPUByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listHPCSystem_CPUByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHPCSystem_CPU(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem_CPU as HPCSystem_CPU");
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
	
	public static List queryHPCSystem_CPU(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem_CPU as HPCSystem_CPU");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("HPCSystem_CPU", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU[] listHPCSystem_CPUByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryHPCSystem_CPU(session, condition, orderBy);
			return (HPCSystem_CPU[]) list.toArray(new HPCSystem_CPU[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU[] listHPCSystem_CPUByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryHPCSystem_CPU(session, condition, orderBy, lockMode);
			return (HPCSystem_CPU[]) list.toArray(new HPCSystem_CPU[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU loadHPCSystem_CPUByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHPCSystem_CPUByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU loadHPCSystem_CPUByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHPCSystem_CPUByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU loadHPCSystem_CPUByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		HPCSystem_CPU[] hPCSystem_CPUs = listHPCSystem_CPUByQuery(session, condition, orderBy);
		if (hPCSystem_CPUs != null && hPCSystem_CPUs.length > 0)
			return hPCSystem_CPUs[0];
		else
			return null;
	}
	
	public static HPCSystem_CPU loadHPCSystem_CPUByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		HPCSystem_CPU[] hPCSystem_CPUs = listHPCSystem_CPUByQuery(session, condition, orderBy, lockMode);
		if (hPCSystem_CPUs != null && hPCSystem_CPUs.length > 0)
			return hPCSystem_CPUs[0];
		else
			return null;
	}
	
	public static java.util.Iterator<HPCSystem_CPU> iterateHPCSystem_CPUByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateHPCSystem_CPUByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<HPCSystem_CPU> iterateHPCSystem_CPUByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateHPCSystem_CPUByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<HPCSystem_CPU> iterateHPCSystem_CPUByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem_CPU as HPCSystem_CPU");
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
	
	public static java.util.Iterator<HPCSystem_CPU> iterateHPCSystem_CPUByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HPCSystem_CPU as HPCSystem_CPU");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("HPCSystem_CPU", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HPCSystem_CPU loadHPCSystem_CPUByCriteria(HPCSystem_CPUCriteria hPCSystem_CPUCriteria) {
		HPCSystem_CPU[] hPCSystem_CPUs = listHPCSystem_CPUByCriteria(hPCSystem_CPUCriteria);
		if(hPCSystem_CPUs == null || hPCSystem_CPUs.length == 0) {
			return null;
		}
		return hPCSystem_CPUs[0];
	}
	
	public static HPCSystem_CPU[] listHPCSystem_CPUByCriteria(HPCSystem_CPUCriteria hPCSystem_CPUCriteria) {
		return hPCSystem_CPUCriteria.listHPCSystem_CPU();
	}
	
	public static HPCSystem_CPU createHPCSystem_CPU() {
		return new pathway.data.persistence.HPCSystem_CPU();
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
			if(getName() != null) {
				getName().setHPCSystems_CPU(null);
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
			if(getName() != null) {
				getName().setHPCSystems_CPU(null);
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
	
	private pathway.data.persistence.HPCSystem name;
	
	private String nameId;
	
	private void setNameId(String value) {
		this.nameId = value;
	}
	
	public String getNameId() {
		return nameId;
	}
	
	private String processorType;
	
	private String model;
	
	private String microarchitecture;
	
	private Integer coresPerSocket;
	
	private Float peakFrequencyPerCore;
	
	private Float peakPerformancePerCore;
	
	private Integer l1Cache;
	
	private Integer l2Cache;
	
	private Integer l3Cache;
	
	private Integer process;
	
	private Integer dieSize;
	
	private Long transistors;
	
	private Integer memoryChannels;
	
	private Float memoryBandwidth;
	
	private String moreInfo;
	
	public void setProcessorType(String value) {
		this.processorType = value;
	}
	
	public String getProcessorType() {
		return processorType;
	}
	
	public void setModel(String value) {
		this.model = value;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setMicroarchitecture(String value) {
		this.microarchitecture = value;
	}
	
	public String getMicroarchitecture() {
		return microarchitecture;
	}
	
	public void setCoresPerSocket(int value) {
		setCoresPerSocket(new Integer(value));
	}
	
	public void setCoresPerSocket(Integer value) {
		this.coresPerSocket = value;
	}
	
	public Integer getCoresPerSocket() {
		return coresPerSocket;
	}
	
	/**
	 * Peak frequency per core in MHz
	 */
	public void setPeakFrequencyPerCore(float value) {
		setPeakFrequencyPerCore(new Float(value));
	}
	
	/**
	 * Peak frequency per core in MHz
	 */
	public void setPeakFrequencyPerCore(Float value) {
		this.peakFrequencyPerCore = value;
	}
	
	/**
	 * Peak frequency per core in MHz
	 */
	public Float getPeakFrequencyPerCore() {
		return peakFrequencyPerCore;
	}
	
	/**
	 * Peak Performance per Core in GFlop/s
	 */
	public void setPeakPerformancePerCore(float value) {
		setPeakPerformancePerCore(new Float(value));
	}
	
	/**
	 * Peak Performance per Core in GFlop/s
	 */
	public void setPeakPerformancePerCore(Float value) {
		this.peakPerformancePerCore = value;
	}
	
	/**
	 * Peak Performance per Core in GFlop/s
	 */
	public Float getPeakPerformancePerCore() {
		return peakPerformancePerCore;
	}
	
	/**
	 * Cache size in KBytes
	 */
	public void setL1Cache(int value) {
		setL1Cache(new Integer(value));
	}
	
	/**
	 * Cache size in KBytes
	 */
	public void setL1Cache(Integer value) {
		this.l1Cache = value;
	}
	
	/**
	 * Cache size in KBytes
	 */
	public Integer getL1Cache() {
		return l1Cache;
	}
	
	public void setL2Cache(int value) {
		setL2Cache(new Integer(value));
	}
	
	public void setL2Cache(Integer value) {
		this.l2Cache = value;
	}
	
	public Integer getL2Cache() {
		return l2Cache;
	}
	
	public void setL3Cache(int value) {
		setL3Cache(new Integer(value));
	}
	
	public void setL3Cache(Integer value) {
		this.l3Cache = value;
	}
	
	public Integer getL3Cache() {
		return l3Cache;
	}
	
	/**
	 * Min. feature size in nm
	 */
	public void setProcess(int value) {
		setProcess(new Integer(value));
	}
	
	/**
	 * Min. feature size in nm
	 */
	public void setProcess(Integer value) {
		this.process = value;
	}
	
	/**
	 * Min. feature size in nm
	 */
	public Integer getProcess() {
		return process;
	}
	
	/**
	 * Die size in mm2
	 */
	public void setDieSize(int value) {
		setDieSize(new Integer(value));
	}
	
	/**
	 * Die size in mm2
	 */
	public void setDieSize(Integer value) {
		this.dieSize = value;
	}
	
	/**
	 * Die size in mm2
	 */
	public Integer getDieSize() {
		return dieSize;
	}
	
	public void setTransistors(long value) {
		setTransistors(new Long(value));
	}
	
	public void setTransistors(Long value) {
		this.transistors = value;
	}
	
	public Long getTransistors() {
		return transistors;
	}
	
	public void setMoreInfo(String value) {
		this.moreInfo = value;
	}
	
	public String getMoreInfo() {
		return moreInfo;
	}
	
	public void setMemoryChannels(int value) {
		setMemoryChannels(new Integer(value));
	}
	
	public void setMemoryChannels(Integer value) {
		this.memoryChannels = value;
	}
	
	public Integer getMemoryChannels() {
		return memoryChannels;
	}
	
	public void setMemoryBandwidth(float value) {
		setMemoryBandwidth(new Float(value));
	}
	
	public void setMemoryBandwidth(Float value) {
		this.memoryBandwidth = value;
	}
	
	public Float getMemoryBandwidth() {
		return memoryBandwidth;
	}
	
	public void setName(pathway.data.persistence.HPCSystem value) {
		this.name = value;
	}
	
	public pathway.data.persistence.HPCSystem getName() {
		return name;
	}
	
	public pathway.data.persistence.HPCSystem getORMID() {
		return getName();
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(((getName() == null) ? "" : String.valueOf(getName().getORMID())));
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("HPCSystem_CPU[ ");
			if (getName() != null)
				sb.append("Name.Persist_ID=").append(getName().toString(true)).append(" ");
			else
				sb.append("Name=null ");
			sb.append("ProcessorType=").append(getProcessorType()).append(" ");
			sb.append("Model=").append(getModel()).append(" ");
			sb.append("Microarchitecture=").append(getMicroarchitecture()).append(" ");
			sb.append("CoresPerSocket=").append(getCoresPerSocket()).append(" ");
			sb.append("PeakFrequencyPerCore=").append(getPeakFrequencyPerCore()).append(" ");
			sb.append("PeakPerformancePerCore=").append(getPeakPerformancePerCore()).append(" ");
			sb.append("L1Cache=").append(getL1Cache()).append(" ");
			sb.append("L2Cache=").append(getL2Cache()).append(" ");
			sb.append("L3Cache=").append(getL3Cache()).append(" ");
			sb.append("Process=").append(getProcess()).append(" ");
			sb.append("DieSize=").append(getDieSize()).append(" ");
			sb.append("Transistors=").append(getTransistors()).append(" ");
			sb.append("MemoryChannels=").append(getMemoryChannels()).append(" ");
			sb.append("MemoryBandwidth=").append(getMemoryBandwidth()).append(" ");
			sb.append("MoreInfo=").append(getMoreInfo()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final String PROP_NAME = "name";
	public static final String PROP_PROCESSOR_TYPE = "processorType";
	public static final String PROP_MODEL = "model";
	public static final String PROP_MICROARCHITECTURE = "microarchitecture";
	public static final String PROP_CORES_PER_SOCKET = "coresPerSocket";
	public static final String PROP_PEAK_FREQUENCY_PER_CORE = "peakFrequencyPerCore";
	public static final String PROP_PEAK_PERFORMANCE_PER_CORE = "peakPerformancePerCore";
	public static final String PROP_L1_CACHE = "l1Cache";
	public static final String PROP_L2_CACHE = "l2Cache";
	public static final String PROP_L3_CACHE = "l3Cache";
	public static final String PROP_PROCESS = "process";
	public static final String PROP_DIE_SIZE = "dieSize";
	public static final String PROP_TRANSISTORS = "transistors";
	public static final String PROP_MEMORY_CHANNELS = "memoryChannels";
	public static final String PROP_MEMORY_BANDWIDTH = "memoryBandwidth";
	public static final String PROP_MORE_INFO = "moreInfo";
}
