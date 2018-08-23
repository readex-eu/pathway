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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

@SuppressWarnings({ "all", "unchecked" })
public class HistoricalNotesCriteria extends AbstractORMCriteria {
	public final IntegerExpression ID;
	public final DateExpression noteDate;
	public final StringExpression notes;
	public final CollectionExpression experiment;
	
	public HistoricalNotesCriteria(Criteria criteria) {
		super(criteria);
		ID = new IntegerExpression("ID", this);
		noteDate = new DateExpression("noteDate", this);
		notes = new StringExpression("notes", this);
		experiment = new CollectionExpression("experiment", this);
	}
	
	public HistoricalNotesCriteria(PersistentSession session) {
		this(session.createCriteria(HistoricalNotes.class));
	}
	
	public HistoricalNotesCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public ExperimentCriteria createExperimentCriteria() {
		return new ExperimentCriteria(createCriteria("experiment"));
	}
	
	public HistoricalNotes uniqueHistoricalNotes() {
		return (HistoricalNotes) super.uniqueResult();
	}
	
	public HistoricalNotes[] listHistoricalNotes() {
		java.util.List list = super.list();
		return (HistoricalNotes[]) list.toArray(new HistoricalNotes[list.size()]);
	}
}

