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
public class PscPropAddInfo implements Serializable {
	public PscPropAddInfo() {
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByORMID(pathway.data.persistence.PscProperty pscProperty, String name) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropAddInfoByORMID(session, pscProperty, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo getPscPropAddInfoByORMID(pathway.data.persistence.PscProperty pscProperty, String name) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getPscPropAddInfoByORMID(session, pscProperty, name);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByORMID(pathway.data.persistence.PscProperty pscProperty, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropAddInfoByORMID(session, pscProperty, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo getPscPropAddInfoByORMID(pathway.data.persistence.PscProperty pscProperty, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getPscPropAddInfoByORMID(session, pscProperty, name, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByORMID(PersistentSession session, pathway.data.persistence.PscProperty pscProperty, String name) throws PersistentException {
		try {
			PscPropAddInfo pscpropaddinfo = new pathway.data.persistence.PscPropAddInfo();
			pscpropaddinfo.setPscProperty(pscProperty);
			pscpropaddinfo.setName(name);
			
			return (PscPropAddInfo) session.load(pathway.data.persistence.PscPropAddInfo.class, pscpropaddinfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo getPscPropAddInfoByORMID(PersistentSession session, pathway.data.persistence.PscProperty pscProperty, String name) throws PersistentException {
		try {
			PscPropAddInfo pscpropaddinfo = new pathway.data.persistence.PscPropAddInfo();
			pscpropaddinfo.setPscProperty(pscProperty);
			pscpropaddinfo.setName(name);
			
			return (PscPropAddInfo) session.get(pathway.data.persistence.PscPropAddInfo.class, pscpropaddinfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByORMID(PersistentSession session, pathway.data.persistence.PscProperty pscProperty, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PscPropAddInfo pscpropaddinfo = new pathway.data.persistence.PscPropAddInfo();
			pscpropaddinfo.setPscProperty(pscProperty);
			pscpropaddinfo.setName(name);
			
			return (PscPropAddInfo) session.load(pathway.data.persistence.PscPropAddInfo.class, pscpropaddinfo, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo getPscPropAddInfoByORMID(PersistentSession session, pathway.data.persistence.PscProperty pscProperty, String name, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PscPropAddInfo pscpropaddinfo = new pathway.data.persistence.PscPropAddInfo();
			pscpropaddinfo.setPscProperty(pscProperty);
			pscpropaddinfo.setName(name);
			
			return (PscPropAddInfo) session.get(pathway.data.persistence.PscPropAddInfo.class, pscpropaddinfo, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscPropAddInfo(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryPscPropAddInfo(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscPropAddInfo(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryPscPropAddInfo(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo[] listPscPropAddInfoByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listPscPropAddInfoByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo[] listPscPropAddInfoByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listPscPropAddInfoByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscPropAddInfo(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscPropAddInfo as PscPropAddInfo");
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
	
	public static List queryPscPropAddInfo(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscPropAddInfo as PscPropAddInfo");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("PscPropAddInfo", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo[] listPscPropAddInfoByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryPscPropAddInfo(session, condition, orderBy);
			return (PscPropAddInfo[]) list.toArray(new PscPropAddInfo[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo[] listPscPropAddInfoByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryPscPropAddInfo(session, condition, orderBy, lockMode);
			return (PscPropAddInfo[]) list.toArray(new PscPropAddInfo[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropAddInfoByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscPropAddInfoByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		PscPropAddInfo[] pscPropAddInfos = listPscPropAddInfoByQuery(session, condition, orderBy);
		if (pscPropAddInfos != null && pscPropAddInfos.length > 0)
			return pscPropAddInfos[0];
		else
			return null;
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		PscPropAddInfo[] pscPropAddInfos = listPscPropAddInfoByQuery(session, condition, orderBy, lockMode);
		if (pscPropAddInfos != null && pscPropAddInfos.length > 0)
			return pscPropAddInfos[0];
		else
			return null;
	}
	
	public static java.util.Iterator<PscPropAddInfo> iteratePscPropAddInfoByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iteratePscPropAddInfoByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<PscPropAddInfo> iteratePscPropAddInfoByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iteratePscPropAddInfoByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<PscPropAddInfo> iteratePscPropAddInfoByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscPropAddInfo as PscPropAddInfo");
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
	
	public static java.util.Iterator<PscPropAddInfo> iteratePscPropAddInfoByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscPropAddInfo as PscPropAddInfo");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("PscPropAddInfo", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscPropAddInfo loadPscPropAddInfoByCriteria(PscPropAddInfoCriteria pscPropAddInfoCriteria) {
		PscPropAddInfo[] pscPropAddInfos = listPscPropAddInfoByCriteria(pscPropAddInfoCriteria);
		if(pscPropAddInfos == null || pscPropAddInfos.length == 0) {
			return null;
		}
		return pscPropAddInfos[0];
	}
	
	public static PscPropAddInfo[] listPscPropAddInfoByCriteria(PscPropAddInfoCriteria pscPropAddInfoCriteria) {
		return pscPropAddInfoCriteria.listPscPropAddInfo();
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof PscPropAddInfo))
			return false;
		PscPropAddInfo pscpropaddinfo = (PscPropAddInfo)aObj;
		if (getPscProperty() == null) {
			if (pscpropaddinfo.getPscProperty() != null)
				return false;
		}
		else if (!getPscProperty().equals(pscpropaddinfo.getPscProperty()))
			return false;
		if ((getName() != null && !getName().equals(pscpropaddinfo.getName())) || (getName() == null && pscpropaddinfo.getName() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		if (getPscProperty() != null) {
			hashcode = hashcode + (int) getPscProperty().getORMID();
		}
		hashcode = hashcode + (getName() == null ? 0 : getName().hashCode());
		return hashcode;
	}
	
	public static PscPropAddInfo createPscPropAddInfo() {
		return new pathway.data.persistence.PscPropAddInfo();
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
			if(getPscProperty() != null) {
				getPscProperty().getPscPropAddInfo().remove(this);
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
			if(getPscProperty() != null) {
				getPscProperty().getPscPropAddInfo().remove(this);
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
	
	private pathway.data.persistence.PscProperty pscProperty;
	
	private long pscPropertyId;
	
	private void setPscPropertyId(long value) {
		this.pscPropertyId = value;
	}
	
	public long getPscPropertyId() {
		return pscPropertyId;
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
	
	public void setPscProperty(pathway.data.persistence.PscProperty value) {
		this.pscProperty = value;
	}
	
	public pathway.data.persistence.PscProperty getPscProperty() {
		return pscProperty;
	}
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(((getPscProperty() == null) ? "" : String.valueOf(getPscProperty().getORMID())) + " " + getName());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("PscPropAddInfo[ ");
			if (getPscProperty() != null)
				sb.append("PscProperty.Persist_ID=").append(getPscProperty().toString(true)).append(" ");
			else
				sb.append("PscProperty=null ");
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
	
	
	public static final String PROP_PSC_PROPERTY = "pscProperty";
	public static final String PROP_NAME = "name";
	public static final String PROP_VALUE = "value";
}
