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
public class ExperimentAddInfo implements Serializable {
	public ExperimentAddInfo() {
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByORMID(pathway.data.persistence.Experiment experiment, String name) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentAddInfoByORMID(session, experiment, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo getExperimentAddInfoByORMID(pathway.data.persistence.Experiment experiment, String name) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getExperimentAddInfoByORMID(session, experiment, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByORMID(pathway.data.persistence.Experiment experiment, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentAddInfoByORMID(session, experiment, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo getExperimentAddInfoByORMID(pathway.data.persistence.Experiment experiment, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getExperimentAddInfoByORMID(session, experiment, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByORMID(PersistentSession session, pathway.data.persistence.Experiment experiment, String name) throws PersistentException {
		try {
			ExperimentAddInfo experimentaddinfo = new pathway.data.persistence.ExperimentAddInfo();
			experimentaddinfo.setExperiment(experiment);
			experimentaddinfo.setName(name);
			
			return (ExperimentAddInfo) session.load(pathway.data.persistence.ExperimentAddInfo.class, experimentaddinfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo getExperimentAddInfoByORMID(PersistentSession session, pathway.data.persistence.Experiment experiment, String name) throws PersistentException {
		try {
			ExperimentAddInfo experimentaddinfo = new pathway.data.persistence.ExperimentAddInfo();
			experimentaddinfo.setExperiment(experiment);
			experimentaddinfo.setName(name);
			
			return (ExperimentAddInfo) session.get(pathway.data.persistence.ExperimentAddInfo.class, experimentaddinfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByORMID(PersistentSession session, pathway.data.persistence.Experiment experiment, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			ExperimentAddInfo experimentaddinfo = new pathway.data.persistence.ExperimentAddInfo();
			experimentaddinfo.setExperiment(experiment);
			experimentaddinfo.setName(name);
			
			return (ExperimentAddInfo) session.load(pathway.data.persistence.ExperimentAddInfo.class, experimentaddinfo, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo getExperimentAddInfoByORMID(PersistentSession session, pathway.data.persistence.Experiment experiment, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			ExperimentAddInfo experimentaddinfo = new pathway.data.persistence.ExperimentAddInfo();
			experimentaddinfo.setExperiment(experiment);
			experimentaddinfo.setName(name);
			
			return (ExperimentAddInfo) session.get(pathway.data.persistence.ExperimentAddInfo.class, experimentaddinfo, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryExperimentAddInfo(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryExperimentAddInfo(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryExperimentAddInfo(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryExperimentAddInfo(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo[] listExperimentAddInfoByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listExperimentAddInfoByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo[] listExperimentAddInfoByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listExperimentAddInfoByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryExperimentAddInfo(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.ExperimentAddInfo as ExperimentAddInfo");
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
	
	public static List queryExperimentAddInfo(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.ExperimentAddInfo as ExperimentAddInfo");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("ExperimentAddInfo", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo[] listExperimentAddInfoByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryExperimentAddInfo(session, condition, orderBy);
			return (ExperimentAddInfo[]) list.toArray(new ExperimentAddInfo[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo[] listExperimentAddInfoByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryExperimentAddInfo(session, condition, orderBy, lockMode);
			return (ExperimentAddInfo[]) list.toArray(new ExperimentAddInfo[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentAddInfoByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadExperimentAddInfoByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		ExperimentAddInfo[] experimentAddInfos = listExperimentAddInfoByQuery(session, condition, orderBy);
		if (experimentAddInfos != null && experimentAddInfos.length > 0)
			return experimentAddInfos[0];
		else
			return null;
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		ExperimentAddInfo[] experimentAddInfos = listExperimentAddInfoByQuery(session, condition, orderBy, lockMode);
		if (experimentAddInfos != null && experimentAddInfos.length > 0)
			return experimentAddInfos[0];
		else
			return null;
	}
	
	public static java.util.Iterator<ExperimentAddInfo> iterateExperimentAddInfoByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateExperimentAddInfoByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<ExperimentAddInfo> iterateExperimentAddInfoByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateExperimentAddInfoByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<ExperimentAddInfo> iterateExperimentAddInfoByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.ExperimentAddInfo as ExperimentAddInfo");
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
	
	public static java.util.Iterator<ExperimentAddInfo> iterateExperimentAddInfoByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.ExperimentAddInfo as ExperimentAddInfo");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("ExperimentAddInfo", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ExperimentAddInfo loadExperimentAddInfoByCriteria(ExperimentAddInfoCriteria experimentAddInfoCriteria) {
		ExperimentAddInfo[] experimentAddInfos = listExperimentAddInfoByCriteria(experimentAddInfoCriteria);
		if(experimentAddInfos == null || experimentAddInfos.length == 0) {
			return null;
		}
		return experimentAddInfos[0];
	}
	
	public static ExperimentAddInfo[] listExperimentAddInfoByCriteria(ExperimentAddInfoCriteria experimentAddInfoCriteria) {
		return experimentAddInfoCriteria.listExperimentAddInfo();
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof ExperimentAddInfo))
			return false;
		ExperimentAddInfo experimentaddinfo = (ExperimentAddInfo)aObj;
		if (getExperiment() == null) {
			if (experimentaddinfo.getExperiment() != null)
				return false;
		}
		else if (!getExperiment().equals(experimentaddinfo.getExperiment()))
			return false;
		if ((getName() != null && !getName().equals(experimentaddinfo.getName())) || (getName() == null && experimentaddinfo.getName() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		if (getExperiment() != null) {
			hashcode = hashcode + (getExperiment().getORMID() == null ? 0 : getExperiment().getORMID().hashCode());
		}
		hashcode = hashcode + (getName() == null ? 0 : getName().hashCode());
		return hashcode;
	}
	
	public static ExperimentAddInfo createExperimentAddInfo() {
		return new pathway.data.persistence.ExperimentAddInfo();
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
				getExperiment().getExperimentAddInfo().remove(this);
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
				getExperiment().getExperimentAddInfo().remove(this);
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
	
	private pathway.data.persistence.Experiment experiment;
	
	private String experimentId;
	
	private void setExperimentId(String value) {
		this.experimentId = value;
	}
	
	public String getExperimentId() {
		return experimentId;
	}
	
	private String name;
	
	private String value;
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setExperiment(pathway.data.persistence.Experiment value) {
		this.experiment = value;
	}
	
	public pathway.data.persistence.Experiment getExperiment() {
		return experiment;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(((getExperiment() == null) ? "" : String.valueOf(getExperiment().getORMID())) + " " + getName());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("ExperimentAddInfo[ ");
			if (getExperiment() != null)
				sb.append("Experiment.Persist_ID=").append(getExperiment().toString(true)).append(" ");
			else
				sb.append("Experiment=null ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("Value=").append(getValue()).append(" ");
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
	
	
	public static final String PROP_EXPERIMENT = "experiment";
	public static final String PROP_NAME = "name";
	public static final String PROP_VALUE = "value";
}
