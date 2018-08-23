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
public class PscProperty {
	public PscProperty() {
	}
	
	public static PscProperty loadPscPropertyByORMID(long ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropertyByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty getPscPropertyByORMID(long ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getPscPropertyByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty loadPscPropertyByORMID(long ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropertyByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty getPscPropertyByORMID(long ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getPscPropertyByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty loadPscPropertyByORMID(PersistentSession session, long ID) throws PersistentException {
		try {
			return (PscProperty) session.load(pathway.data.persistence.PscProperty.class, new Long(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty getPscPropertyByORMID(PersistentSession session, long ID) throws PersistentException {
		try {
			return (PscProperty) session.get(pathway.data.persistence.PscProperty.class, new Long(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty loadPscPropertyByORMID(PersistentSession session, long ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (PscProperty) session.load(pathway.data.persistence.PscProperty.class, new Long(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty getPscPropertyByORMID(PersistentSession session, long ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (PscProperty) session.get(pathway.data.persistence.PscProperty.class, new Long(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscProperty(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryPscProperty(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscProperty(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryPscProperty(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty[] listPscPropertyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listPscPropertyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty[] listPscPropertyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listPscPropertyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscProperty(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscProperty as PscProperty");
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
	
	public static List queryPscProperty(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscProperty as PscProperty");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("PscProperty", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty[] listPscPropertyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryPscProperty(session, condition, orderBy);
			return (PscProperty[]) list.toArray(new PscProperty[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty[] listPscPropertyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryPscProperty(session, condition, orderBy, lockMode);
			return (PscProperty[]) list.toArray(new PscProperty[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty loadPscPropertyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropertyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty loadPscPropertyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropertyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty loadPscPropertyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		PscProperty[] pscPropertys = listPscPropertyByQuery(session, condition, orderBy);
		if (pscPropertys != null && pscPropertys.length > 0)
			return pscPropertys[0];
		else
			return null;
	}
	
	public static PscProperty loadPscPropertyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		PscProperty[] pscPropertys = listPscPropertyByQuery(session, condition, orderBy, lockMode);
		if (pscPropertys != null && pscPropertys.length > 0)
			return pscPropertys[0];
		else
			return null;
	}
	
	public static java.util.Iterator<PscProperty> iteratePscPropertyByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iteratePscPropertyByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<PscProperty> iteratePscPropertyByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iteratePscPropertyByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<PscProperty> iteratePscPropertyByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscProperty as PscProperty");
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
	
	public static java.util.Iterator<PscProperty> iteratePscPropertyByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscProperty as PscProperty");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("PscProperty", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscProperty loadPscPropertyByCriteria(PscPropertyCriteria pscPropertyCriteria) {
		PscProperty[] pscPropertys = listPscPropertyByCriteria(pscPropertyCriteria);
		if(pscPropertys == null || pscPropertys.length == 0) {
			return null;
		}
		return pscPropertys[0];
	}
	
	public static PscProperty[] listPscPropertyByCriteria(PscPropertyCriteria pscPropertyCriteria) {
		return pscPropertyCriteria.listPscProperty();
	}
	
	public static PscProperty createPscProperty() {
		return new pathway.data.persistence.PscProperty();
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
			if(getExperiment() != null) {
				getExperiment().getPscProperty().remove(this);
			}
			
			if(getRegion() != null) {
				getRegion().getPscProperty().remove(this);
			}
			
			pathway.data.persistence.PscPropAddInfo[] lPscPropAddInfos = (pathway.data.persistence.PscPropAddInfo[])getPscPropAddInfo().toArray(new pathway.data.persistence.PscPropAddInfo[getPscPropAddInfo().size()]);
			for(int i = 0; i < lPscPropAddInfos.length; i++) {
				lPscPropAddInfos[i].setPscProperty(null);
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
			if(getExperiment() != null) {
				getExperiment().getPscProperty().remove(this);
			}
			
			if(getRegion() != null) {
				getRegion().getPscProperty().remove(this);
			}
			
			pathway.data.persistence.PscPropAddInfo[] lPscPropAddInfos = (pathway.data.persistence.PscPropAddInfo[])getPscPropAddInfo().toArray(new pathway.data.persistence.PscPropAddInfo[getPscPropAddInfo().size()]);
			for(int i = 0; i < lPscPropAddInfos.length; i++) {
				lPscPropAddInfos[i].setPscProperty(null);
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
	
	private long ID;
	
	private pathway.data.persistence.Experiment experiment;
	
	private pathway.data.persistence.PscRegion region;
	
	private String name;
	
	private String type;
	
	private Boolean clustered;
	
	private String configuration;
	
	private Integer process;
	
	private Integer thread;
	
	private Double severity;
	
	private Double confidence;
	
	private Integer fileID;
	
	private String fileName;
	
	private Integer RFL;
	
	private String regionType;
	
	private java.util.Set<pathway.data.persistence.PscPropAddInfo> pscPropAddInfo = new java.util.HashSet<pathway.data.persistence.PscPropAddInfo>();
	
	private void setID(long value) {
		this.ID = value;
	}
	
	public long getID() {
		return ID;
	}
	
	public long getORMID() {
		return getID();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setType(String value) {
		this.type = value;
	}
	
	public String getType() {
		return type;
	}
	
	public void setClustered(boolean value) {
		setClustered(new Boolean(value));
	}
	
	public void setClustered(Boolean value) {
		this.clustered = value;
	}
	
	public Boolean getClustered() {
		return clustered;
	}
	
	public void setProcess(int value) {
		setProcess(new Integer(value));
	}
	
	public void setProcess(Integer value) {
		this.process = value;
	}
	
	public Integer getProcess() {
		return process;
	}
	
	public void setThread(int value) {
		setThread(new Integer(value));
	}
	
	public void setThread(Integer value) {
		this.thread = value;
	}
	
	public Integer getThread() {
		return thread;
	}
	
	public void setSeverity(double value) {
		setSeverity(new Double(value));
	}
	
	public void setSeverity(Double value) {
		this.severity = value;
	}
	
	public Double getSeverity() {
		return severity;
	}
	
	public void setConfidence(double value) {
		setConfidence(new Double(value));
	}
	
	public void setConfidence(Double value) {
		this.confidence = value;
	}
	
	public Double getConfidence() {
		return confidence;
	}
	
	public void setFileID(int value) {
		setFileID(new Integer(value));
	}
	
	public void setFileID(Integer value) {
		this.fileID = value;
	}
	
	public Integer getFileID() {
		return fileID;
	}
	
	public void setFileName(String value) {
		this.fileName = value;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setRFL(int value) {
		setRFL(new Integer(value));
	}
	
	public void setRFL(Integer value) {
		this.RFL = value;
	}
	
	public Integer getRFL() {
		return RFL;
	}
	
	public void setRegionType(String value) {
		this.regionType = value;
	}
	
	public String getRegionType() {
		return regionType;
	}
	
	public void setConfiguration(String value) {
		this.configuration = value;
	}
	
	public String getConfiguration() {
		return configuration;
	}
	
	public void setRegion(pathway.data.persistence.PscRegion value) {
		this.region = value;
	}
	
	public pathway.data.persistence.PscRegion getRegion() {
		return region;
	}
	
	public void setExperiment(pathway.data.persistence.Experiment value) {
		this.experiment = value;
	}
	
	public pathway.data.persistence.Experiment getExperiment() {
		return experiment;
	}
	
	public void setPscPropAddInfo(java.util.Set<pathway.data.persistence.PscPropAddInfo> value) {
		this.pscPropAddInfo = value;
	}
	
	public java.util.Set<pathway.data.persistence.PscPropAddInfo> getPscPropAddInfo() {
		return pscPropAddInfo;
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
			sb.append("PscProperty[ ");
			sb.append("ID=").append(getID()).append(" ");
			if (getExperiment() != null)
				sb.append("Experiment.Persist_ID=").append(getExperiment().toString(true)).append(" ");
			else
				sb.append("Experiment=null ");
			if (getRegion() != null)
				sb.append("Region.Persist_ID=").append(getRegion().toString(true)).append(" ");
			else
				sb.append("Region=null ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("Type=").append(getType()).append(" ");
			sb.append("Clustered=").append(getClustered()).append(" ");
			sb.append("Configuration=").append(getConfiguration()).append(" ");
			sb.append("Process=").append(getProcess()).append(" ");
			sb.append("Thread=").append(getThread()).append(" ");
			sb.append("Severity=").append(getSeverity()).append(" ");
			sb.append("Confidence=").append(getConfidence()).append(" ");
			sb.append("FileID=").append(getFileID()).append(" ");
			sb.append("FileName=").append(getFileName()).append(" ");
			sb.append("RFL=").append(getRFL()).append(" ");
			sb.append("RegionType=").append(getRegionType()).append(" ");
			sb.append("PscPropAddInfo.size=").append(getPscPropAddInfo().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final String PROP_ID = "ID";
	public static final String PROP_EXPERIMENT = "experiment";
	public static final String PROP_REGION = "region";
	public static final String PROP_NAME = "name";
	public static final String PROP_TYPE = "type";
	public static final String PROP_CLUSTERED = "clustered";
	public static final String PROP_CONFIGURATION = "configuration";
	public static final String PROP_PROCESS = "process";
	public static final String PROP_THREAD = "thread";
	public static final String PROP_SEVERITY = "severity";
	public static final String PROP_CONFIDENCE = "confidence";
	public static final String PROP_FILE_ID = "fileID";
	public static final String PROP_FILE_NAME = "fileName";
	public static final String PROP_RFL = "RFL";
	public static final String PROP_REGION_TYPE = "regionType";
	public static final String PROP_PSC_PROP_ADD_INFO = "pscPropAddInfo";
}
