package pathway.data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.joda.time.DateTime;
import pathway.data.persistence.Application;
import pathway.data.persistence.Experiment;
import pathway.data.persistence.ExperimentAddInfo;
import pathway.data.persistence.HPCSystem;
import pathway.data.persistence.PscProperty;
import pathway.data.persistence.Tool;




@NonNullByDefault
public final class ExperimentBuilder implements Builder<Experiment> {
	
    public ExperimentBuilder addProperty(PscProperty property) {
        properties.add(property);
        return this;
    }


    public ExperimentBuilder mpiProcs(Integer mpiProcs) {
        numMpiProcs = mpiProcs;
        return this;
    }


    public ExperimentBuilder ompThreads(Integer ompThreads) {
        numOmpThreads = ompThreads;
        return this;
    }


    public ExperimentBuilder application(Application application) {
        appl = application;
        return this;
    }


    public ExperimentBuilder expDateTime(DateTime experimentDate) {
        expDate = experimentDate;
        return this;
    }


    public ExperimentBuilder expDate(DateTime experimentDate) {
        if( expDate == null )
            expDate = experimentDate;
        else {
            final DateTime local = expDate;
            assert local != null;
            expDate = local.withDate(experimentDate.getYear(), experimentDate.getMonthOfYear(), experimentDate.getDayOfMonth());
        }

        return this;
    }


    public ExperimentBuilder expTime(DateTime experimentTime) {
        if( expDate == null )
            expDate = experimentTime;
        else {
            DateTime local = expDate;
            assert local != null;
            expDate = local.withTime(experimentTime.getHourOfDay(), experimentTime.getMinuteOfHour(), experimentTime.getSecondOfMinute(),
                    experimentTime.getMillisOfSecond());
        }

        return this;
    }


    public ExperimentBuilder workingDir(String workingDir) {
        this.workingDir = workingDir;
        return this;
    }


    public ExperimentBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }


    public ExperimentBuilder resultsURI(String resultsURI) {
        this.resultsURI = resultsURI;
        return this;
    }


    public ExperimentBuilder tool(@Nullable Tool tool) {
        this.tool = tool;
        return this;
    }


    public ExperimentBuilder hpcSystem(HPCSystem hpcSystem) {
        this.hpcSystem = hpcSystem;
        return this;
    }


    public ExperimentBuilder environment(Map<String, String> envVars) {
        this.envVars = envVars;
        return this;
    }


    public ExperimentBuilder expComment(String comment) {
        this.expComment = comment;
        return this;
    }


    public ExperimentBuilder appCodeVersion(String codeVersion) {
        this.codeVersion = codeVersion;
        return this;
    }


    public ExperimentBuilder addAddInfo(String name, String value) {
        ExperimentAddInfo addInfo = new ExperimentAddInfo();
        addInfo.setName(name);
        addInfo.setValue(value);

        experimentAddInfo.add(addInfo);
        return this;
    }


    @Override
    public Experiment build() {
        if( expDate == null )
            throw new RuntimeException("Experiment date must be set.");

        DateTime localExpDate = expDate;
        assert localExpDate != null;

        Experiment experiment = new Experiment();
        experiment.setExpDate(new Timestamp(localExpDate.getMillis()));
        experiment.setUserName(userName == null ? "Unknown" : userName);

        experiment.setApplication(appl);
        experiment.setTool(tool);
        experiment.setHPCSystem(hpcSystem);

        addExperimentConfig(experiment);
        experiment.setResultsURI(resultsURI);

        for( PscProperty property: properties ) {
            property.setExperiment(experiment);
        }
        experiment.setPscProperty(properties);

        for( ExperimentAddInfo addInfo: experimentAddInfo ) {
            addInfo.setExperiment(experiment);
        }
        experiment.setExperimentAddInfo(experimentAddInfo);

        return experiment;
    }


    private void addExperimentConfig(Experiment experiment) {
        experiment.setMpiProcs(numMpiProcs);
        experiment.setOmpThreads(numOmpThreads);
        experiment.setStartupFolder(workingDir);
        experiment.setExecCmd(""); // replaced later either in "make" or "run" activity
        experiment.setCodeVersion(codeVersion != null ? codeVersion : "UNKNOWN");
        experiment.setStdOut("");
        experiment.setStdErr("");
        Map<String, String> localEnvVars = envVars; // work-around for poor null-reference analysis
        experiment.setEnvironment((localEnvVars != null ? DataUtils.EnvironmentVars2Str(localEnvVars) : ""));
        experiment.setComment(expComment);
    }


    private final Set<PscProperty> properties = new HashSet<PscProperty>();
    private final Set<ExperimentAddInfo> experimentAddInfo = new HashSet<ExperimentAddInfo>();
    private Integer numMpiProcs = 1;
    private Integer numOmpThreads = 1;
    private @Nullable DateTime expDate;
    private @Nullable String workingDir;
    private @Nullable String userName;
    private @Nullable String resultsURI;
    private @Nullable Tool tool;
    private @Nullable HPCSystem hpcSystem;
    private @Nullable Application appl;
    private @Nullable Map<String, String> envVars;
    private @Nullable String expComment;
    private @Nullable String codeVersion;
}
