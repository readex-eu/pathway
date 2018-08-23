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
public class PscPropAddInfoCriteria extends AbstractORMCriteria {
	public final LongExpression pscPropertyId;
	public final AssociationExpression pscProperty;
	public final StringExpression name;
	public final StringExpression value;
	
	public PscPropAddInfoCriteria(Criteria criteria) {
		super(criteria);
		pscPropertyId = new LongExpression("ORM_PscProperty.ID", this);
		pscProperty = new AssociationExpression("ORM_PscProperty", this);
		name = new StringExpression("name", this);
		value = new StringExpression("value", this);
	}
	
	public PscPropAddInfoCriteria(PersistentSession session) {
		this(session.createCriteria(PscPropAddInfo.class));
	}
	
	public PscPropAddInfoCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public PscPropertyCriteria createPscPropertyCriteria() {
		return new PscPropertyCriteria(createCriteria("ORM_PscProperty"));
	}
	
	public PscPropAddInfo uniquePscPropAddInfo() {
		return (PscPropAddInfo) super.uniqueResult();
	}
	
	public PscPropAddInfo[] listPscPropAddInfo() {
		java.util.List list = super.list();
		return (PscPropAddInfo[]) list.toArray(new PscPropAddInfo[list.size()]);
	}
}

