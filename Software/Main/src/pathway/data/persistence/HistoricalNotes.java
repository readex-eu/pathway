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
public class HistoricalNotes {
	public HistoricalNotes() {
	}
	
	public static HistoricalNotes loadHistoricalNotesByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHistoricalNotesByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes getHistoricalNotesByORMID(int ID) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getHistoricalNotesByORMID(session, ID);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes loadHistoricalNotesByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHistoricalNotesByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes getHistoricalNotesByORMID(int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return getHistoricalNotesByORMID(session, ID, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes loadHistoricalNotesByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (HistoricalNotes) session.load(pathway.data.persistence.HistoricalNotes.class, new Integer(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes getHistoricalNotesByORMID(PersistentSession session, int ID) throws PersistentException {
		try {
			return (HistoricalNotes) session.get(pathway.data.persistence.HistoricalNotes.class, new Integer(ID));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes loadHistoricalNotesByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (HistoricalNotes) session.load(pathway.data.persistence.HistoricalNotes.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes getHistoricalNotesByORMID(PersistentSession session, int ID, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (HistoricalNotes) session.get(pathway.data.persistence.HistoricalNotes.class, new Integer(ID), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHistoricalNotes(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryHistoricalNotes(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHistoricalNotes(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return queryHistoricalNotes(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes[] listHistoricalNotesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listHistoricalNotesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes[] listHistoricalNotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return listHistoricalNotesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryHistoricalNotes(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HistoricalNotes as HistoricalNotes");
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
	
	public static List queryHistoricalNotes(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HistoricalNotes as HistoricalNotes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("HistoricalNotes", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes[] listHistoricalNotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryHistoricalNotes(session, condition, orderBy);
			return (HistoricalNotes[]) list.toArray(new HistoricalNotes[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes[] listHistoricalNotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryHistoricalNotes(session, condition, orderBy, lockMode);
			return (HistoricalNotes[]) list.toArray(new HistoricalNotes[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes loadHistoricalNotesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHistoricalNotesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes loadHistoricalNotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return loadHistoricalNotesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes loadHistoricalNotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		HistoricalNotes[] historicalNoteses = listHistoricalNotesByQuery(session, condition, orderBy);
		if (historicalNoteses != null && historicalNoteses.length > 0)
			return historicalNoteses[0];
		else
			return null;
	}
	
	public static HistoricalNotes loadHistoricalNotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		HistoricalNotes[] historicalNoteses = listHistoricalNotesByQuery(session, condition, orderBy, lockMode);
		if (historicalNoteses != null && historicalNoteses.length > 0)
			return historicalNoteses[0];
		else
			return null;
	}
	
	public static java.util.Iterator<HistoricalNotes> iterateHistoricalNotesByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateHistoricalNotesByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<HistoricalNotes> iterateHistoricalNotesByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = PathwayPersistentManager.instance().getSession();
			return iterateHistoricalNotesByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator<HistoricalNotes> iterateHistoricalNotesByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HistoricalNotes as HistoricalNotes");
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
	
	public static java.util.Iterator<HistoricalNotes> iterateHistoricalNotesByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From pathway.data.persistence.HistoricalNotes as HistoricalNotes");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("HistoricalNotes", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static HistoricalNotes loadHistoricalNotesByCriteria(HistoricalNotesCriteria historicalNotesCriteria) {
		HistoricalNotes[] historicalNoteses = listHistoricalNotesByCriteria(historicalNotesCriteria);
		if(historicalNoteses == null || historicalNoteses.length == 0) {
			return null;
		}
		return historicalNoteses[0];
	}
	
	public static HistoricalNotes[] listHistoricalNotesByCriteria(HistoricalNotesCriteria historicalNotesCriteria) {
		return historicalNotesCriteria.listHistoricalNotes();
	}
	
	public static HistoricalNotes createHistoricalNotes() {
		return new pathway.data.persistence.HistoricalNotes();
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
				lExperiments[i].setHistoricalNotes(null);
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
				lExperiments[i].setHistoricalNotes(null);
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
	
	private java.util.Date noteDate;
	
	private String notes;
	
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
	
	public void setNoteDate(java.util.Date value) {
		this.noteDate = value;
	}
	
	public java.util.Date getNoteDate() {
		return noteDate;
	}
	
	public void setNotes(String value) {
		this.notes = value;
	}
	
	public String getNotes() {
		return notes;
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
			sb.append("HistoricalNotes[ ");
			sb.append("ID=").append(getID()).append(" ");
			sb.append("NoteDate=").append(getNoteDate()).append(" ");
			sb.append("Notes=").append(getNotes()).append(" ");
			sb.append("Experiment.size=").append(getExperiment().size()).append(" ");
			sb.append("]");
			return sb.toString();
		}
	}
	
	public static final String PROP_ID = "ID";
	public static final String PROP_NOTE_DATE = "noteDate";
	public static final String PROP_NOTES = "notes";
	public static final String PROP_EXPERIMENT = "experiment";
}
