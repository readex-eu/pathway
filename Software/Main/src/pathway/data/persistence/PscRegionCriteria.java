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
public class PscRegionCriteria extends AbstractORMCriteria {
	public final StringExpression ID;
	public final AssociationExpression FK_Application;
	public final StringExpression name;
	public final StringExpression type;
	public final StringExpression sourceFile;
	public final IntegerExpression startLine;
	public final IntegerExpression endLine;
	public final StringExpression dataScope;
	public final StringExpression pscRegionParentId;
	public final AssociationExpression pscRegionParent;
	public final CollectionExpression pscProperty;
	public final CollectionExpression pscRegion;
	
	public PscRegionCriteria(Criteria criteria) {
		super(criteria);
		ID = new StringExpression("ID", this);
		FK_Application = new AssociationExpression("ORM_FK_Application", this);
		name = new StringExpression("name", this);
		type = new StringExpression("type", this);
		sourceFile = new StringExpression("sourceFile", this);
		startLine = new IntegerExpression("startLine", this);
		endLine = new IntegerExpression("endLine", this);
		dataScope = new StringExpression("dataScope", this);
		pscRegionParentId = new StringExpression("ORM_PscRegionParent.ID", this);
		pscRegionParent = new AssociationExpression("ORM_PscRegionParent", this);
		pscProperty = new CollectionExpression("pscProperty", this);
		pscRegion = new CollectionExpression("pscRegion", this);
	}
	
	public PscRegionCriteria(PersistentSession session) {
		this(session.createCriteria(PscRegion.class));
	}
	
	public PscRegionCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public ApplicationCriteria createFK_ApplicationCriteria() {
		return new ApplicationCriteria(createCriteria("ORM_FK_Application"));
	}
	
	public PscRegionCriteria createPscRegionParentCriteria() {
		return new PscRegionCriteria(createCriteria("ORM_PscRegionParent"));
	}
	
	public PscPropertyCriteria createPscPropertyCriteria() {
		return new PscPropertyCriteria(createCriteria("pscProperty"));
	}
	
	public PscRegionCriteria createPscRegionCriteria() {
		return new PscRegionCriteria(createCriteria("pscRegion"));
	}
	
	public PscRegion uniquePscRegion() {
		return (PscRegion) super.uniqueResult();
	}
	
	public PscRegion[] listPscRegion() {
		java.util.List list = super.list();
		return (PscRegion[]) list.toArray(new PscRegion[list.size()]);
	}
}

