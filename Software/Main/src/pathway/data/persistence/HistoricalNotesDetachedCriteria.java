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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

@SuppressWarnings({ "all", "unchecked" })
public class HistoricalNotesDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression ID;
	public final DateExpression noteDate;
	public final StringExpression notes;
	public final CollectionExpression experiment;
	
	public HistoricalNotesDetachedCriteria() {
		super(pathway.data.persistence.HistoricalNotes.class, pathway.data.persistence.HistoricalNotesCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		noteDate = new DateExpression("noteDate", this.getDetachedCriteria());
		notes = new StringExpression("notes", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
	}
	
	public HistoricalNotesDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, pathway.data.persistence.HistoricalNotesCriteria.class);
		ID = new IntegerExpression("ID", this.getDetachedCriteria());
		noteDate = new DateExpression("noteDate", this.getDetachedCriteria());
		notes = new StringExpression("notes", this.getDetachedCriteria());
		experiment = new CollectionExpression("experiment", this.getDetachedCriteria());
	}
	
	public ExperimentDetachedCriteria createExperimentCriteria() {
		return new ExperimentDetachedCriteria(createCriteria("experiment"));
	}
	
	public HistoricalNotes uniqueHistoricalNotes(PersistentSession session) {
		return (HistoricalNotes) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public HistoricalNotes[] listHistoricalNotes(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (HistoricalNotes[]) list.toArray(new HistoricalNotes[list.size()]);
	}
}

