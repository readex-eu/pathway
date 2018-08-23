package pathway.workflows.workitems.ipm;

import java.io.File;
import java.util.List;
import java.util.Map;




/** Based on MPI log files, computes a recommendation of whether to continue with CPU- or MPI- profiling. */
public class CalculateRecommendationWorkItem extends IPMWorkItem {
    @Override
    protected void calculate(List<File> ipmFiles, Map<String, Float> results) {
        // TODO: to be implemented by Andrey; read the XML files and return performance key-value properties, that give a recommendation of whether to proceed
        // with CPU- or MPI- profiling

        for( File file: ipmFiles )
            System.out.println(file.getAbsolutePath());
    }
}
