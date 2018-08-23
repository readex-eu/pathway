package pathway.data.beans;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.PredicateUtils;
import org.apache.commons.collections.Transformer;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;

import pathway.data.persistence.Experiment;
import pathway.data.persistence.ExperimentCriteria;
import pathway.data.persistence.HPCSystem;
import pathway.data.persistence.Tool;
import pathway.eclipse.ExecutionLog;




@NonNullByDefault
public class ExperimentGroups extends AbstractUIModelObject {
    public enum GROUP_BY {
        DATE, APPLICATION, SYSTEM, PERF_TOOL, USER;

        @Override
        public String toString() {
            String s = super.toString().replace("_", " ");
            return s.substring(0, 1) + s.substring(1).toLowerCase();
        }


        public static final int size = GROUP_BY.values().length;
    }


    public ExperimentGroups() {
        setExpGroupBy(GROUP_BY.DATE);
        m_expGroupBy = GROUP_BY.DATE;
    }


    public ExperimentGroups(boolean caching) {
        this();
        cachingDisabled = !caching;
    }


    public void setExpGroupBy(GROUP_BY expGroupBy) {
        if( expGroupBy != m_expGroupBy ) {
            GROUP_BY oldValue = m_expGroupBy;
            m_expGroupBy = expGroupBy;

            // check if this grouping already exists in the local cache
            if( !cache.containsKey(expGroupBy) || cachingDisabled )
                regroupExperiments(m_expGroupBy);

            firePropertyChange("groups", oldValue, m_expGroupBy);
        }
    }


    public GROUP_BY getExpGroupBy() {
        return m_expGroupBy;
    }


    public List<ExperimentGroup> getGroups() {
        if( !cache.containsKey(m_expGroupBy) )
            setExpGroupBy(m_expGroupBy);

        return cache.get(m_expGroupBy);
    }


    public void refresh() {
        cache.clear();
        regroupExperiments(m_expGroupBy);
    }


    public void regroupExperiments(GROUP_BY groupBy) {
        List<ExperimentGroup> groups = new ArrayList<ExperimentGroup>();

        switch( groupBy ) {
        case DATE:
            try {
                ExperimentCriteria expDatesCriteria = new ExperimentCriteria();
                List<Timestamp> expTimestamps = expDatesCriteria.setProjection(Projections.groupProperty("expDate")).list();
                Collection<Date> expDates = CollectionUtils.collect(expTimestamps, new Transformer() {
                    @Override
                    public Object transform(@Nullable Object arg) {
                        if( arg == null )
                            throw new IllegalArgumentException();

                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(((Timestamp)arg).getTime());
                        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        return cal.getTime();
                    }
                });
                Collection<Date> expDatesUniq = CollectionUtils.select(expDates, PredicateUtils.uniquePredicate());

                for( Date expDate: expDatesUniq ) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(expDate);
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    Date maxDate = cal.getTime();

                    ExperimentCriteria expCriteria = new ExperimentCriteria();
                    expCriteria.expDate.ge(expDate);
                    expCriteria.expDate.lt(maxDate);

                    ExperimentGroup group = new ExperimentGroup(DateFormat.getDateInstance(DateFormat.MEDIUM).format(expDate));
                    expCriteria.setReadOnly(true);
                    List<Experiment> expOnDate = expCriteria.list();
                    for( Experiment experiment: expOnDate ) {
                        group.addExperiment(experiment);
                    }
                    groups.add(group);
                }

                Collections.sort(groups, byDateComparer);

            }
            catch( PersistentException ex ) {
                ex.printStackTrace();
            }
            break;

        case APPLICATION:
            try {
                ExperimentCriteria expApplicationsCriteria = new ExperimentCriteria();
                List<String> applications = expApplicationsCriteria.setProjection(Projections.groupProperty("application.name")).list();

                for( String applicationName: applications ) {

                    ExperimentCriteria expCriteria = new ExperimentCriteria();
                    if( applicationName != null ) {
                        expCriteria.add(Restrictions.eq("application.name", applicationName));
                    }
                    else {
                        applicationName = "(default)";
                        expCriteria.add(Restrictions.isNull("application"));
                    }

                    ExperimentGroup group = new ExperimentGroup(applicationName);
                    for( Experiment experiment: expCriteria.listExperiment() ) {
                    	//ExecutionLog.get().writeLog("Input: " + experiment.getInputDataFileName() + ", Inter:" + experiment.getInterPhaseDynamism());
                        group.addExperiment(experiment);
                    }
                    groups.add(group);
                }
            }
            catch( PersistentException ex ) {
                ex.printStackTrace();
            }
            break;

        case SYSTEM:
            try {
                ExperimentCriteria expHPCSystemsCriteria = new ExperimentCriteria();
                List<String> hpcSystems = expHPCSystemsCriteria.setProjection(Projections.groupProperty(Experiment.PROP_HPCSYSTEM + "." + HPCSystem.PROP_NAME))
                        .list();

                for( String hpcSystem: hpcSystems ) {
                    ExperimentGroup group = new ExperimentGroup(hpcSystem);
                    ExperimentCriteria expCriteria = new ExperimentCriteria();
                    expCriteria.createHPCSystemCriteria().name.eq(hpcSystem);

                    for( Experiment experiment: expCriteria.listExperiment() )
                        group.addExperiment(experiment);

                    groups.add(group);
                }

            }
            catch( PersistentException ex ) {
                ex.printStackTrace();
            }
            break;

        case PERF_TOOL:
            try {
                ExperimentCriteria expToolsCriteria = new ExperimentCriteria();
                List<Tool> tools = expToolsCriteria.setProjection(Projections.groupProperty("tool")).list();

                for( Tool tool: tools ) {
                    if( tool == null )
                        continue;

                    String toolName = tool.getName();
                    ExperimentGroup group = new ExperimentGroup(toolName);

                    ExperimentCriteria expCriteria = new ExperimentCriteria();
                    expCriteria.createToolCriteria().name.eq(toolName);

                    for( Experiment experiment: expCriteria.listExperiment() )
                        group.addExperiment(experiment);

                    groups.add(group);
                }

            }
            catch( PersistentException ex ) {
                ex.printStackTrace();
            }
            break;

        case USER:
            try {
                ExperimentCriteria expUsersCriteria = new ExperimentCriteria();
                List<String> users = expUsersCriteria.setProjection(Projections.groupProperty("userName")).list();

                for( String user: users ) {
                    String userName = user == null ? "(default)" : user;
                    ExperimentGroup group = new ExperimentGroup(userName);
                    ExperimentCriteria expCriteria = new ExperimentCriteria();

                    if( user != null )
                        expCriteria.userName.eq(userName);
                    else
                        expCriteria.add(Restrictions.isNull("userName"));

                    for( Experiment experiment: expCriteria.listExperiment() )
                        group.addExperiment(experiment);

                    groups.add(group);
                }

            }
            catch( PersistentException ex ) {
                ex.printStackTrace();
            }
            break;

        default:
            throw new UnsupportedOperationException();
        }

        cache.put(m_expGroupBy, groups);
    }


    private final Map<GROUP_BY, List<ExperimentGroup>> cache = new HashMap<GROUP_BY, List<ExperimentGroup>>(GROUP_BY.size);
    private GROUP_BY m_expGroupBy;
    private boolean cachingDisabled = false;

    private static final ByDateComparer byDateComparer = new ByDateComparer();
}

class ByDateComparer implements Comparator<ExperimentGroup> {
    @Override
    public int compare(ExperimentGroup a, ExperimentGroup b) {
        return b.getExperiments().get(0).getExpDate().compareTo(a.getExperiments().get(0).getExpDate());
    }
}
