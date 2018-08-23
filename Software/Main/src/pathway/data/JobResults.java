package pathway.data;

import java.util.List;
import java.util.Map;
import org.eclipse.jdt.annotation.NonNullByDefault;
import pathway.data.persistence.Experiment;




@NonNullByDefault
public class JobResults {
    public Map<String, String> getEnvironment() {
        return environment.getEnvironment();
    }


    public List<String> getModules() {
        return environment.getModules();
    }


    public Experiment storeResultsToExperiment(Experiment experiment) {
        experiment.setStdOut(stdout);
        experiment.setStdErr(stderr);
        experiment.setEnvironment(DataUtils.EnvironmentVars2Str(getEnvironment()));
        experiment.setLoadedModules(DataUtils.modulesList2Json(getModules()));
        experiment.setJobState(status);

        return experiment;
    }


    public String status = "Unknown";
    public EnvFile environment = new EnvFile();
    public String stdout = "";
    public String stderr = "";
    public String cpu = "";
    public String uname = "";
}
