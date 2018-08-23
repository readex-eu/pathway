package pathway.data.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import pathway.data.persistence.Experiment;

public class ExperimentGroup extends AbstractUIModelObject {
	private List<Experiment> m_experiments = new ArrayList<Experiment>();
	private String m_name;

	public ExperimentGroup() {
	}

	public ExperimentGroup(String name) {
		m_name = name;
	}

	public String getName() {
		return m_name;
	}

	public void setName(String name) {
		String oldValue = m_name;
		m_name = name;
		firePropertyChange("name", oldValue, m_name);
	}

	public void addExperiment(Experiment experiment) {
		List<Experiment> oldValue = m_experiments;
		m_experiments = new ArrayList<Experiment>(m_experiments);
		m_experiments.add(experiment);
		firePropertyChange("experiments", oldValue, m_experiments);
		firePropertyChange("experimentsCount", oldValue.size(), m_experiments.size());
	}

	public void removeExperiment(Experiment experiment) {
		List<Experiment> oldValue = m_experiments;
		m_experiments = new ArrayList<Experiment>(m_experiments);
		m_experiments.remove(experiment);
		firePropertyChange("experiments", oldValue, m_experiments);
		firePropertyChange("experimentsCount", oldValue.size(), m_experiments.size());
	}

	public List<Experiment> getExperiments() {
		return m_experiments;
	}

	public int getExperimentCount() {
		return m_experiments.size();
	}

	@Override
	public String toString() {
		return getName() + " (" + getExperimentCount() + ")";
	}

	@Override
	public int hashCode()
	{
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj)
	{
		//check for self-comparison
		if ( this == obj ) return true;

		//use instanceof instead of getClass here for two reasons
		//1. if need be, it can match any supertype, and not just one class;
		//2. it renders an explict check for "that == null" redundant, since
		//it does the check for null already - "null instanceof [type]" always
		//returns false. (See Effective Java by Joshua Bloch.)
		if ( !(obj instanceof ExperimentGroup) ) return false;

		//cast to native object is now safe
		ExperimentGroup rhs = (ExperimentGroup)obj;

		//now a proper field-by-field evaluation can be made
		return new EqualsBuilder().appendSuper(super.equals(obj))
				.append(m_name, rhs.m_name)
				.append(m_experiments, rhs.m_experiments)
				.isEquals();
	}




}

