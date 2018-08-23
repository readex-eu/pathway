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
public class Tool {
	public Tool() {
	}
	
	public static Tool loadToolByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadToolByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool getToolByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getToolByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool loadToolByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadToolByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool getToolByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getToolByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool loadToolByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (Tool) session.load(pathway.data.persistence.Tool.class, new Integer(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool getToolByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (Tool) session.get(pathway.data.persistence.Tool.class, new Integer(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool loadToolByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Tool) session.load(pathway.data.persistence.Tool.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool getToolByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Tool) session.get(pathway.data.persistence.Tool.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTool(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryTool(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTool(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryTool(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool[] listToolByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listToolByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool[] listToolByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listToolByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTool(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Tool as Tool");
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
	
	public static List queryTool(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Tool as Tool");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Tool", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool[] listToolByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryTool(session, condition, orderBy);
			return (Tool[]) list.toArray(new Tool[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool[] listToolByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryTool(session, condition, orderBy, lockMode);
			return (Tool[]) list.toArray(new Tool[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool loadToolByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadToolByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool loadToolByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadToolByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool loadToolByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Tool[] tools = listToolByQuery(session, condition, orderBy);
		if (tools != null && tools.length > 0)
			return tools[0];
		else
			return null;
	}
	
	public static Tool loadToolByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Tool[] tools = listToolByQuery(session, condition, orderBy, lockMode);
		if (tools != null && tools.length > 0)
			return tools[0];
		else
			return null;
	}
	
	public static java.util.Iterator<Tool> iterateToolByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateToolByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<Tool> iterateToolByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateToolByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<Tool> iterateToolByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Tool as Tool");
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
	
	public static java.util.Iterator<Tool> iterateToolByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.Tool as Tool");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Tool", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Tool loadToolByCriteria(ToolCriteria toolCriteria) {
		Tool[] tools = listToolByCriteria(toolCriteria);
		if(tools == null || tools.length == 0) {
			return null;
		}
		return tools[0];
	}
	
	public static Tool[] listToolByCriteria(ToolCriteria toolCriteria) {
		return toolCriteria.listTool();
	}
	
	public static Tool createTool() {
		return new pathway.data.persistence.Tool();
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
				lExperiments[i].setTool(null);
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
				lExperiments[i].setTool(null);
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
	
	private int ID;
	
	private String name;
	
	private String version;
	
	private boolean profiling = false;
	
	private boolean tracing = false;
	
	private String instrumentCMD;
	
	private String instrSuffix;
	
	private String profileCMD;
	
	private String profileArgs;
	
	private String traceCMD;
	
	private String traceArgs;
	
	private String reqModules;
	
	private String reqEnvVars;
	
	private String website;
	
	private String unsetEnvVars;
	
	private Integer column;
	
	private java.util.Set<pathway.data.persistence.Experiment> experiment = new java.util.HashSet<pathway.data.persistence.Experiment>();
	
	private void setID(int value) {
		this.ID = value;
	}
	
	public int getID() {
		return ID;
	}
	
	public int getORMID() {
		return getID();
	}
	
	public void setVersion(String value) {
		this.version = value;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setProfiling(boolean value) {
		this.profiling = value;
	}
	
	public boolean getProfiling() {
		return profiling;
	}
	
	public void setTracing(boolean value) {
		this.tracing = value;
	}
	
	public boolean getTracing() {
		return tracing;
	}
	
	public void setProfileCMD(String value) {
		this.profileCMD = value;
	}
	
	public String getProfileCMD() {
		return profileCMD;
	}
	
	public void setTraceCMD(String value) {
		this.traceCMD = value;
	}
	
	public String getTraceCMD() {
		return traceCMD;
	}
	
	public void setWebsite(String value) {
		this.website = value;
	}
	
	public String getWebsite() {
		return website;
	}
	
	public void setInstrumentCMD(String value) {
		this.instrumentCMD = value;
	}
	
	public String getInstrumentCMD() {
		return instrumentCMD;
	}
	
	public void setProfileArgs(String value) {
		this.profileArgs = value;
	}
	
	public String getProfileArgs() {
		return profileArgs;
	}
	
	public void setTraceArgs(String value) {
		this.traceArgs = value;
	}
	
	public String getTraceArgs() {
		return traceArgs;
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
	
	public void setInstrSuffix(String value) {
		this.instrSuffix = value;
	}
	
	public String getInstrSuffix() {
		return instrSuffix;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setUnsetEnvVars(String value) {
		this.unsetEnvVars = value;
	}
	
	public String getUnsetEnvVars() {
		return unsetEnvVars;
	}
	
	public void setColumn(int value) {
		setColumn(new Integer(value));
	}
	
	public void setColumn(Integer value) {
		this.column = value;
	}
	
	public Integer getColumn() {
		return column;
	}
	
	public void setExperiment(java.util.Set<pathway.data.persistence.Experiment> value) {
		this.experiment = value;
	}
	
	public java.util.Set<pathway.data.persistence.Experiment> getExperiment() {
		return experiment;
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
			sb.append("Tool[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("Name=").append(getName()).append(" ");
			sb.append("Version=").append(getVersion()).append(" ");
			sb.append("Profiling=").append(getProfiling()).append(" ");
			sb.append("Tracing=").append(getTracing()).append(" ");
			sb.append("InstrumentCMD=").append(getInstrumentCMD()).append(" ");
			sb.append("InstrSuffix=").append(getInstrSuffix()).append(" ");
			sb.append("ProfileCMD=").append(getProfileCMD()).append(" ");
			sb.append("ProfileArgs=").append(getProfileArgs()).append(" ");
			sb.append("TraceCMD=").append(getTraceCMD()).append(" ");
			sb.append("TraceArgs=").append(getTraceArgs()).append(" ");
			sb.append("ReqModules=").append(getReqModules()).append(" ");
			sb.append("ReqEnvVars=").append(getReqEnvVars()).append(" ");
			sb.append("Website=").append(getWebsite()).append(" ");
			sb.append("UnsetEnvVars=").append(getUnsetEnvVars()).append(" ");
			sb.append("Column=").append(getColumn()).append(" ");
			sb.append("Experiment.size=").append(getExperiment().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final String PROP_ID = "ID";
	public static final String PROP_NAME = "name";
	public static final String PROP_VERSION = "version";
	public static final String PROP_PROFILING = "profiling";
	public static final String PROP_TRACING = "tracing";
	public static final String PROP_INSTRUMENT_CMD = "instrumentCMD";
	public static final String PROP_INSTR_SUFFIX = "instrSuffix";
	public static final String PROP_PROFILE_CMD = "profileCMD";
	public static final String PROP_PROFILE_ARGS = "profileArgs";
	public static final String PROP_TRACE_CMD = "traceCMD";
	public static final String PROP_TRACE_ARGS = "traceArgs";
	public static final String PROP_REQ_MODULES = "reqModules";
	public static final String PROP_REQ_ENV_VARS = "reqEnvVars";
	public static final String PROP_WEBSITE = "website";
	public static final String PROP_UNSET_ENV_VARS = "unsetEnvVars";
	public static final String PROP_COLUMN = "column";
	public static final String PROP_EXPERIMENT = "experiment";
}
