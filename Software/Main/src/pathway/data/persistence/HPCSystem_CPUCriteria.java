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
public class HPCSystem_CPUCriteria extends AbstractORMCriteria {
	public final StringExpression nameId;
	public final AssociationExpression name;
	public final StringExpression processorType;
	public final StringExpression model;
	public final StringExpression microarchitecture;
	public final IntegerExpression coresPerSocket;
	public final FloatExpression peakFrequencyPerCore;
	public final FloatExpression peakPerformancePerCore;
	public final IntegerExpression l1Cache;
	public final IntegerExpression l2Cache;
	public final IntegerExpression l3Cache;
	public final IntegerExpression process;
	public final IntegerExpression dieSize;
	public final LongExpression transistors;
	public final IntegerExpression memoryChannels;
	public final FloatExpression memoryBandwidth;
	public final StringExpression moreInfo;
	
	public HPCSystem_CPUCriteria(Criteria criteria) {
		super(criteria);
		nameId = new StringExpression("name.name", this);
		name = new AssociationExpression("name", this);
		processorType = new StringExpression("processorType", this);
		model = new StringExpression("model", this);
		microarchitecture = new StringExpression("microarchitecture", this);
		coresPerSocket = new IntegerExpression("coresPerSocket", this);
		peakFrequencyPerCore = new FloatExpression("peakFrequencyPerCore", this);
		peakPerformancePerCore = new FloatExpression("peakPerformancePerCore", this);
		l1Cache = new IntegerExpression("l1Cache", this);
		l2Cache = new IntegerExpression("l2Cache", this);
		l3Cache = new IntegerExpression("l3Cache", this);
		process = new IntegerExpression("process", this);
		dieSize = new IntegerExpression("dieSize", this);
		transistors = new LongExpression("transistors", this);
		memoryChannels = new IntegerExpression("memoryChannels", this);
		memoryBandwidth = new FloatExpression("memoryBandwidth", this);
		moreInfo = new StringExpression("moreInfo", this);
	}
	
	public HPCSystem_CPUCriteria(PersistentSession session) {
		this(session.createCriteria(HPCSystem_CPU.class));
	}
	
	public HPCSystem_CPUCriteria() throws PersistentException {
		this(PathwayPersistentManager.instance().getSession());
	}
	
	public HPCSystemCriteria createNameCriteria() {
		return new HPCSystemCriteria(createCriteria("name"));
	}
	
	public HPCSystem_CPU uniqueHPCSystem_CPU() {
		return (HPCSystem_CPU) super.uniqueResult();
	}
	
	public HPCSystem_CPU[] listHPCSystem_CPU() {
		java.util.List list = super.list();
		return (HPCSystem_CPU[]) list.toArray(new HPCSystem_CPU[list.size()]);
	}
}

