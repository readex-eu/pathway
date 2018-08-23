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
public class ToolCriteria extends AbstractORMCriteria {
	public final IntegerExpression ID;
	public final StringExpression name;
	public final StringExpression version;
	public final BooleanExpression profiling;
	public final BooleanExpression tracing;
	public final StringExpression instrumentCMD;
	public final StringExpression instrSuffix;
	public final StringExpression profileCMD;
	public final StringExpression profileArgs;
	public final StringExpression traceCMD;
	public final StringExpression traceArgs;
	public final StringExpression reqModules;
	public final StringExpression reqEnvVars;
	public final StringExpression website;
	public final StringExpression unsetEnvVars;
	public final IntegerExpression column;
	public final CollectionExpression experiment;
	
	public ToolCriteria(Criteria criteria) {
		super(criteria);
		ID = new IntegerExpression("ID", this);
		name = new StringExpression("name", this);
		version = new StringExpression("version", this);
		profiling = new BooleanExpression("profiling", this);
		tracing = new BooleanExpression("tracing", this);
		instrumentCMD = new StringExpression("instrumentCMD", this);
		instrSuffix = new StringExpression("instrSuffix", this);
		profileCMD = new StringExpression("profileCMD", this);
		profileArgs = new StringExpression("profileArgs", this);
		traceCMD = new StringExpression("traceCMD", this);
		traceArgs = new StringExpression("traceArgs", this);
		reqModules = new StringExpression("reqModules", this);
		reqEnvVars = new StringExpression("reqEnvVars", this);
		website = new StringExpression("website", this);
		unsetEnvVars = new StringExpression("unsetEnvVars", this);
		column = new IntegerExpression("column", this);
		experiment = new CollectionExpression("experiment", this);
	}
	
	public ToolCriteria(PersistentSession session) {
		this(session.createCriteria(Tool.class));
	}
	
	public ToolCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public ExperimentCriteria createExperimentCriteria() {
		return new ExperimentCriteria(createCriteria("experiment"));
	}
	
	public Tool uniqueTool() {
		return (Tool) super.uniqueResult();
	}
	
	public Tool[] listTool() {
		java.util.List list = super.list();
		return (Tool[]) list.toArray(new Tool[list.size()]);
	}
}

