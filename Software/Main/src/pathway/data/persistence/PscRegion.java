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
public class PscRegion {
	public PscRegion() {
	}
	
	public static PscRegion loadPscRegionByORMID(String ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscRegionByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion getPscRegionByORMID(String ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getPscRegionByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion loadPscRegionByORMID(String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscRegionByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion getPscRegionByORMID(String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getPscRegionByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion loadPscRegionByORMID(PersistentSession session, String ID) throws PersistentException {
		try {
			return (PscRegion) session.load(pathway.data.persistence.PscRegion.class, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion getPscRegionByORMID(PersistentSession session, String ID) throws PersistentException {
		try {
			return (PscRegion) session.get(pathway.data.persistence.PscRegion.class, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion loadPscRegionByORMID(PersistentSession session, String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (PscRegion) session.load(pathway.data.persistence.PscRegion.class, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion getPscRegionByORMID(PersistentSession session, String ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (PscRegion) session.get(pathway.data.persistence.PscRegion.class, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscRegion(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryPscRegion(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscRegion(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryPscRegion(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion[] listPscRegionByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listPscRegionByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion[] listPscRegionByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listPscRegionByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryPscRegion(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscRegion as PscRegion");
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
	
	public static List queryPscRegion(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscRegion as PscRegion");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("PscRegion", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion[] listPscRegionByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryPscRegion(session, condition, orderBy);
			return (PscRegion[]) list.toArray(new PscRegion[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion[] listPscRegionByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryPscRegion(session, condition, orderBy, lockMode);
			return (PscRegion[]) list.toArray(new PscRegion[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion loadPscRegionByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscRegionByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion loadPscRegionByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadPscRegionByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion loadPscRegionByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		PscRegion[] pscRegions = listPscRegionByQuery(session, condition, orderBy);
		if (pscRegions != null && pscRegions.length > 0)
			return pscRegions[0];
		else
			return null;
	}
	
	public static PscRegion loadPscRegionByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		PscRegion[] pscRegions = listPscRegionByQuery(session, condition, orderBy, lockMode);
		if (pscRegions != null && pscRegions.length > 0)
			return pscRegions[0];
		else
			return null;
	}
	
	public static java.util.Iterator<PscRegion> iteratePscRegionByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iteratePscRegionByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<PscRegion> iteratePscRegionByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iteratePscRegionByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<PscRegion> iteratePscRegionByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscRegion as PscRegion");
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
	
	public static java.util.Iterator<PscRegion> iteratePscRegionByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.PscRegion as PscRegion");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("PscRegion", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static PscRegion loadPscRegionByCriteria(PscRegionCriteria pscRegionCriteria) {
		PscRegion[] pscRegions = listPscRegionByCriteria(pscRegionCriteria);
		if(pscRegions == null || pscRegions.length == 0) {
			return null;
		}
		return pscRegions[0];
	}
	
	public static PscRegion[] listPscRegionByCriteria(PscRegionCriteria pscRegionCriteria) {
		return pscRegionCriteria.listPscRegion();
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof PscRegion))
			return false;
		PscRegion pscregion = (PscRegion)aObj;
		if ((getID() != null && !getID().equals(pscregion.getID())) || (getID() == null && pscregion.getID() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getID() == null ? 0 : getID().hashCode());
		return hashcode;
	}
	
	public static PscRegion createPscRegion() {
		return new pathway.data.persistence.PscRegion();
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
			if(getFK_Application() != null) {
				getFK_Application().getPscRegion().remove(this);
			}
			
			if(getPscRegionParent() != null) {
				getPscRegionParent().getPscRegion().remove(this);
			}
			
			pathway.data.persistence.PscProperty[] lPscPropertys = (pathway.data.persistence.PscProperty[])getPscProperty().toArray(new pathway.data.persistence.PscProperty[getPscProperty().size()]);
			for(int i = 0; i < lPscPropertys.length; i++) {
				lPscPropertys[i].setRegion(null);
			}
			pathway.data.persistence.PscRegion[] lPscRegions = (pathway.data.persistence.PscRegion[])getPscRegion().toArray(new pathway.data.persistence.PscRegion[getPscRegion().size()]);
			for(int i = 0; i < lPscRegions.length; i++) {
				lPscRegions[i].setPscRegionParent(null);
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
			if(getFK_Application() != null) {
				getFK_Application().getPscRegion().remove(this);
			}
			
			if(getPscRegionParent() != null) {
				getPscRegionParent().getPscRegion().remove(this);
			}
			
			pathway.data.persistence.PscProperty[] lPscPropertys = (pathway.data.persistence.PscProperty[])getPscProperty().toArray(new pathway.data.persistence.PscProperty[getPscProperty().size()]);
			for(int i = 0; i < lPscPropertys.length; i++) {
				lPscPropertys[i].setRegion(null);
			}
			pathway.data.persistence.PscRegion[] lPscRegions = (pathway.data.persistence.PscRegion[])getPscRegion().toArray(new pathway.data.persistence.PscRegion[getPscRegion().size()]);
			for(int i = 0; i < lPscRegions.length; i++) {
				lPscRegions[i].setPscRegionParent(null);
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
	
	private pathway.data.persistence.Application FK_Application;
	
	private String name;
	
	private String type;
	
	private String sourceFile;
	
	private Integer startLine;
	
	private Integer endLine;
	
	private String dataScope;
	
	private pathway.data.persistence.PscRegion pscRegionParent;
	
	private java.util.Set<pathway.data.persistence.PscProperty> pscProperty = new java.util.HashSet<pathway.data.persistence.PscProperty>();
	
	private java.util.Set<pathway.data.persistence.PscRegion> pscRegion = new java.util.HashSet<pathway.data.persistence.PscRegion>();
	
	public void setID(String value) {
		this.ID = value;
	}
	
	public String getID() {
		return ID;
	}
	
	public String getORMID() {
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
	
	public void setSourceFile(String value) {
		this.sourceFile = value;
	}
	
	public String getSourceFile() {
		return sourceFile;
	}
	
	public void setStartLine(int value) {
		setStartLine(new Integer(value));
	}
	
	public void setStartLine(Integer value) {
		this.startLine = value;
	}
	
	public Integer getStartLine() {
		return startLine;
	}
	
	public void setEndLine(int value) {
		setEndLine(new Integer(value));
	}
	
	public void setEndLine(Integer value) {
		this.endLine = value;
	}
	
	public Integer getEndLine() {
		return endLine;
	}
	
	public void setDataScope(String value) {
		this.dataScope = value;
	}
	
	public String getDataScope() {
		return dataScope;
	}
	
	public void setPscRegionParent(pathway.data.persistence.PscRegion value) {
		this.pscRegionParent = value;
	}
	
	public pathway.data.persistence.PscRegion getPscRegionParent() {
		return pscRegionParent;
	}
	
	public void setFK_Application(pathway.data.persistence.Application value) {
		this.FK_Application = value;
	}
	
	public pathway.data.persistence.Application getFK_Application() {
		return FK_Application;
	}
	
	public void setPscProperty(java.util.Set<pathway.data.persistence.PscProperty> value) {
		this.pscProperty = value;
	}
	
	public java.util.Set<pathway.data.persistence.PscProperty> getPscProperty() {
		return pscProperty;
	}
	
	
	public void setPscRegion(java.util.Set<pathway.data.persistence.PscRegion> value) {
		this.pscRegion = value;
	}
	
	public java.util.Set<pathway.data.persistence.PscRegion> getPscRegion() {
		return pscRegion;
	}
	
	
	private String application = "default app";
	
	public String toString() {
		return toString(false);
	}
	
	public String toString(boolean idOnly) {
		if (idOnly) {
			return String.valueOf(getID());
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("PscRegion[ ");
			sb.append("ID=").append(getID()).append(" ");
			if (getFK_Application() != null)
				sb.append("FK_Application.Persist_ID=").append(getFK_Application().toString(true)).append(" ");
			else
				sb.append("FK_Application=null ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("Type=").append(getType()).append(" ");
			sb.append("SourceFile=").append(getSourceFile()).append(" ");
			sb.append("StartLine=").append(getStartLine()).append(" ");
			sb.append("EndLine=").append(getEndLine()).append(" ");
			sb.append("DataScope=").append(getDataScope()).append(" ");
			if (getPscRegionParent() != null)
				sb.append("PscRegionParent.Persist_ID=").append(getPscRegionParent().toString(true)).append(" ");
			else
				sb.append("PscRegionParent=null ");
			sb.append("PscProperty.size=").append(getPscProperty().size()).append(" ");
			sb.append("PscRegion.size=").append(getPscRegion().size()).append(" ");
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
	
	
	public static final String PROP_ID = "ID";
	public static final String PROP_FK__APPLICATION = "FK_Application";
	public static final String PROP_NAME = "name";
	public static final String PROP_TYPE = "type";
	public static final String PROP_SOURCE_FILE = "sourceFile";
	public static final String PROP_START_LINE = "startLine";
	public static final String PROP_END_LINE = "endLine";
	public static final String PROP_DATA_SCOPE = "dataScope";
	public static final String PROP_PSC_REGION_PARENT = "pscRegionParent";
	public static final String PROP_PSC_PROPERTY = "pscProperty";
	public static final String PROP_PSC_REGION = "pscRegion";
}
