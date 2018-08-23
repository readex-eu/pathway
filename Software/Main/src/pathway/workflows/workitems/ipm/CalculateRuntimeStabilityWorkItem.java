package pathway.workflows.workitems.ipm;

import java.io.File;
import java.util.List;
import java.util.Map;




/** Based on MPI log files, computes a stability value for the given experiments. */
public class CalculateRuntimeStabilityWorkItem extends IPMWorkItem {
    @Override
    protected void calculate(List<File> ipmFiles, Map<String, Float> results) {
        // TODO: to be implemented by Andrey; read the XML files and return performance key-value properties, e.g. "stability" <-> 0.87

        for( File file: ipmFiles )
            System.out.println(file.getAbsolutePath());
    }
}
