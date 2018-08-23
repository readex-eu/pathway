package pathway.data;

import org.orm.PersistentException;
import org.orm.PersistentManager;
import org.orm.PersistentTransaction;

import pathway.data.persistence.Application;
import pathway.data.persistence.HPCSystem;
import pathway.data.persistence.HPCSystem_CPU;
import pathway.data.persistence.PathwayPersistentManager;
import pathway.data.persistence.Tool;




public class InitData {
    public static void createInitialData() throws PersistentException {
        addHPCSystem(                      "Taurus", "TU Dresden", "https://doc.zih.tu-dresden.de/hpc-wiki/bin/view/Compendium/SystemTaurus", "SLURM", null, null, null, null, null, null, null, null);
        HPCSystem migration = addHPCSystem("SuperMIG",           "Leibniz-Rechenzentrum (LRZ)", "http://www.lrz.de/services/compute/supermuc/",      "LoadLeveler (SuperMUC)",        8200, 205, 4, 78f, 6.4f, "NetApp NAS", "Infiniband QDR", "non-blocking Tree");
        HPCSystem fatNode = addHPCSystem(  "SuperMUC Fat Node",  "Leibniz-Rechenzentrum (LRZ)", "http://www.lrz.de/services/compute/supermuc/",      "LoadLeveler (SuperMUC)",        8200, 205, 4, 78f, 6.4f, "IBM GPFS", "Infiniband FDR10", "non-blocking Tree");
        HPCSystem thinNode = addHPCSystem( "SuperMUC Thin Node", "Leibniz-Rechenzentrum (LRZ)", "http://www.lrz.de/services/compute/supermuc/",      "LoadLeveler (SuperMUC)",        147456, 9216, 2, 2900f, 2f, "IBM GPFS", "Infiniband FDR10", "non-blocking Tree");
        addHPCSystem(                      "Local",              "Personal",                    null,                                                "MPICH2",                        null, null, null, null, null, null, null, null);
        addHPCSystem(                      "LRZ Linux Cluster",  "Leibniz-Rechenzentrum (LRZ)", "http://www.lrz.de/services/compute/linux-cluster/", "SLURM (Leibniz-Rechenzentrum)", 2848, 178, 16, 22.7f, 2f, null, null, null);

        addCPU(migration, "Intel Xeon Westmere-EX", "E7-4870", "Nehalem", 10, 2400, 9.6f, 32, 256, 30000, 64, 513, 2600000000L, 4, (Float)null, "http://ark.intel.com/products/53579");
        addCPU(fatNode, "Intel Xeon Westmere-EX", "E7-4870", "Nehalem", 10, 2400, 9.6f, 32, 256, 30000, 64, 513, 2600000000L, 4, (Float)null, "http://ark.intel.com/products/53579");
        addCPU(thinNode, "Intel Xeon Sandy Bridge-EP", "E5-2680", "Sandy Bridge", 8, 2700, null, 32, 256, 20000, 64, 435, 2270000000L, 4, 51.2f, "http://ark.intel.com/products/53579");

        addTool("Periscope", "1.4", true, false, "psc_instrument", ".periscope", "psc_frontend", "{#TOOL_CMD#} --apprun=\"{#APP_NAME#} {#APP_ARGS#}\" --mpinumprocs={#MPI_PROCS#} --ompnumthreads={#OMP_THREADS#} --propfile={#OUTPUT_LOC#}.psc", "", "", "[\"periscope/1.4\"]", "{}", "http://www.lrr.in.tum.de/periscope");
        addTool("Scalasca", "1.4.2", true, true, "scalasca -instrument", ".scalasca", "scalasca", "{#TOOL_CMD#} -analyze {#MPI_EXEC#} -n {#MPI_PROCS#} {#APP_NAME#} {#APP_ARGS#}", "scalasca", "-analyze -t {#MPI_EXEC#} -n {#MPI_PROCS#} {#APP_NAME#} {#APP_ARGS#}", "[\"scalasca/1.4.2\"]", "{}", "http://www.scalasca.org");
        addTool("Score-P", "1.1", true, true, "scorep --user", ".scorep", "{#APP_NAME#}", "SCOREP_ENABLE_PROFILING=1 SCOREP_EXPERIMENT_DIRECTORY={#OUTPUT_LOC#} {#MPI_EXEC#} -n {#MPI_PROCS#} {#TOOL_CMD#} {#APP_ARGS#}", "{#APP_NAME#}", "SCOREP_ENABLE_TRACING=1 SCOREP_EXPERIMENT_DIRECTORY={#OUTPUT_LOC#} {#MPI_EXEC#} -n {#MPI_PROCS#} {#TOOL_CMD#} {#APP_ARGS#}", "[\"scorep/1.1\", \"cube\", \"png\"]", "{}", "http://www.score-p.org");
        addTool("Periscope", "1.5", true, false, "scorep --user", ".scorep", "psc_frontend", "{#TOOL_CMD#} --apprun=\"{#APP_NAME#} {#APP_ARGS#}\" --mpinumprocs={#MPI_PROCS#} --ompnumthreads={#OMP_THREADS#} --propfile={#OUTPUT_LOC#}.psc", "", "", "[\"gcc/4.4\",\"periscope/1.5\",\"scorep/1.1\",\"cube\"]", "{}", "http://www.lrr.in.tum.de/periscope");
        addTool("IPM", "SuperMUC", true, false, "", "", "mpiexec", "LD_PRELOAD=$IPM_LIBDIR/libipm.so {#TOOL_CMD#} -n {#MPI_PROCS#} {#APP_NAME#} {#APP_ARGS#}", "", "", "[\"papi\", \"ipm\"]", "{}", "http://ipm-hpc.sourceforge.net/");
        addTool("IPM", "Endavour", true, false, "", "", "mpirun", "LD_PRELOAD=$IPM_LIBDIR/libipm.so {#TOOL_CMD#} -n {#MPI_PROCS#} {#APP_NAME#} {#APP_ARGS#}", "", "", "[\"papi\", \"ipm\"]", "{\"IPM_LOGFILE\":\"pathway_ipm_{#EXPERIMENT_ID#}.xml\"}", "http://ipm-hpc.sourceforge.net/");
        
        addTool("READEX PTF", "2.0", true, false, "psc_frontend", "", "psc_frontend", "{#TOOL_CMD#} --apprun=\"{#APP_NAME#} {#APP_ARGS#}\" --mpinumprocs={#MPI_PROCS#} --ompnumthreads={#OMP_THREADS#} --phase={#PHASE_REGION_NAME#} --tune={#TUNING_LIBRARY#} --config-file={#READEX_CONFIG_FILE_NAME#} --info={#INFO#} --selective-info={#SELECTIVE_INFO#}", "", "", "[\"readex/ci_readex_bullxmpi1.2.8.4_gcc6.3.0\" , \"scorep-hdeem/sync-xmpi-gcc6.3\"]", "{\"SCOREP_METRIC_PLUGINS_SEP\":\"\\\";\\\"\", \"LD_LIBRARY_PATH\":\"$LD_LIBRARY_PATH:/usr/local/lib\", \"SCOREP_METRIC_HDEEM_SYNC_PLUGIN_CONNECTION\":\"\\\"INBAND\\\"\", \"SCOREP_METRIC_HDEEM_SYNC_PLUGIN_STATS_TIMEOUT_MS\":\"1000\", \"SCOREP_METRIC_HDEEM_SYNC_PLUGIN_VERBOSE\":\"\\\"WARN\\\"\", \"SCOREP_METRIC_PLUGINS\":\"hdeem_sync_plugin\", \"SCOREP_RRL_PLUGINS\":\"cpu_freq_plugin,uncore_freq_plugin,OpenMPTP\",  \"SCOREP_RRL_VERBOSE\":\"\\\"WARN\\\"\", \"SCOREP_SUBSTRATE_PLUGINS\":\"rrl\",\"SCORE_MPI_ENABLE_GROUPS\":\"ENV\", \"ATP_EXECUTION_MODE\":\"DTA\"}", "");
        addTool("READEX RAT", "1.0", true, false, "mpirun", "", "mpirun", "-np {#MPI_PROCS#} {#APP_NAME#} {#APP_ARGS#}", "", "", "[\"readex/ci_readex_bullxmpi1.2.8.4_gcc6.3.0\", \"scorep-hdeem/sync-xmpi-gcc6.3\"]", "{\"LD_LIBRARY_PATH\":\"$LD_LIBRARY_PATH:/usr/local/lib\", \"SCOREP_ENABLE_PROFILING\":\"false\", \"SCOREP_ENABLE_TRACING\":\"false\", \"SCOREP_SUBSTRATE_PLUGINS\":\"rrl\", \"SCOREP_RRL_PLUGINS\":\"cpu_freq_plugin, uncore_freq_plugin, OpenMPTP\", \"SCOREP_RRL_TMM_PATH\":\"tuning_model.json\", \"SCORE_MPI_ENABLE_GROUPS\":\"ENV\", \"ATP_EXECUTION_MODE\":\"RAT\"}", "");
        addTool("READEX bullxmpi", "1.2.8.4", true, false, "mpirun", "", "mpirun", "-np {#MPI_PROCS#} {#APP_NAME#} {#APP_ARGS#}", "", "", "[\"readex/ci_readex_bullxmpi1.2.8.4_gcc6.3.0\"]","{\"LD_LIBRARY_PATH\":\"$LD_LIBRARY_PATH:/usr/local/lib\"}", "");
        addTool("READEX SCOREP", "1.0", true, false, "scorep --user", "", "mpirun", "-np {#MPI_PROCS#} {#APP_NAME#} {#APP_ARGS#}", "", "", "[\"readex/ci_readex_bullxmpi1.2.8.4_gcc6.3.0\"]", "{\"SCOREP_PROFILING_MAX_CALLPATH_DEPTH\":\"200\", \"LD_LIBRARY_PATH\":\"$LD_LIBRARY_PATH:/usr/local/lib\"}", "");
        addTool("READEX scorep-autofilter", "1.0", true, false, "scorep-autofilter", "", "scorep-autofilter", "-t {#ARG_t#} {#PATH_TO_CUBEX_FILE#}/profile.cubex", "", "", "[\"readex/ci_readex_bullxmpi1.2.8.4_gcc6.3.0\"]", "{\"SCOREP_PROFILING_MAX_CALLPATH_DEPTH\":\"200\", \"LD_LIBRARY_PATH\":\"$LD_LIBRARY_PATH:/usr/local/lib\"}", "");
        addTool("READEX readex-dyn-detect", "1.0", true, false, "readex-dyn-detect", "", "readex-dyn-detect", "-p {#ARG_p#} -t {#ARG_t#} -c {#ARG_c#} -v {#ARG_v#} -w {#ARG_w#} {#PATH_TO_CUBEX_FILE#}/profile.cubex", "", "", "[\"readex/ci_readex_bullxmpi1.2.8.4_gcc6.3.0\"]", "{\"SCOREP_PROFILING_MAX_CALLPATH_DEPTH\":\"200\",\"LD_LIBRARY_PATH\":\"$LD_LIBRARY_PATH:/usr/local/lib\"}", "");

        addApplication("miniMD", "1.0", "miniMD_openmpi", "-i {#APP_INPUT#} ", "in.lj.miniMD","in.lj.miniMD", "INTEGRATE_RUN_LOOP", true, "1200000,2400000,1200000", true, "1500000,3000000,1500000", true, "1,1", true, false, true, false, true, false, true, false, true, false, true, false, "8.0000000000000002e-08", "1", "hdeem_sync_plugin", "hdeem/BLADE/E, hdeem/CPU0/E, hdeem/CPU1/E", true, false, "2", false, "2", false, "10", "10", "20", true, true, false, "2", "AutotuneAll,AutotunePlugins", "/home/carreras/miniMD_10_ref_alpha_prototype", "/home/carreras/miniMD_10_ref_alpha_prototype", 30, "");
    }
    
    

    public static HPCSystem addHPCSystem(String name, String organisation, String website, String batchSystem, Integer totalCores, Integer nodes, Integer processorsPerNode, Float systemPeakPerformance, Float memoryPerCore, String fileSystem, String netTechnology, String netTopology) throws PersistentException {
        HPCSystem system = new HPCSystem();
        system.setName(name);
        system.setOrganisation(organisation);
        system.setWebsite(website);
        system.setBatchSystem(batchSystem);
        system.setTotalCores(totalCores);
        system.setNodes(nodes);
        system.setProcessorsPerNode(processorsPerNode);
        system.setSystemPeakPerformance(systemPeakPerformance);
        system.setMemoryPerCore(memoryPerCore);
        system.setFileSystem(fileSystem);
        system.setNetTechnology(netTechnology);
        system.setNetTopology(netTopology);
        system.setAvailableModules(null);
        system.save();

        return system;
    }


    public static HPCSystem_CPU addCPU(HPCSystem system, String processorType, String model, String microarch, int coresPerSocket, int peakFreq, Float peakPerfPerCore, int l1, int l2, int l3, int process, int dieSize, long transistors, int memChannels, Float memBandwidth, String moreInfo) throws PersistentException {
        PersistentManager mgr = PathwayPersistentManager.instance();
        
            HPCSystem_CPU cpu = new HPCSystem_CPU();
            cpu.setName(system);
            cpu.setProcessorType(processorType);
            cpu.setModel(model);
            cpu.setMicroarchitecture(microarch);
            cpu.setCoresPerSocket(coresPerSocket);
            cpu.setPeakFrequencyPerCore(peakFreq);
            cpu.setPeakPerformancePerCore(peakPerfPerCore);
            cpu.setL1Cache(l1);
            cpu.setL2Cache(l2);
            cpu.setL3Cache(l3);
            cpu.setProcess(process);
            cpu.setDieSize(dieSize);
            cpu.setTransistors(transistors);
            cpu.setMemoryChannels(memChannels);
            cpu.setMemoryBandwidth(memBandwidth);
            cpu.setMoreInfo(moreInfo);
            
            mgr.saveObject(cpu);
            return cpu;
    }


    public static Tool addTool(String name, String version, boolean profiling, boolean tracing, String instrumentCMD, String instrumentSuffix, String profileCMD, String profileArgs, String traceCMD, String traceArgs, String requiredModules, String requiredEnvVars, String website) throws PersistentException {
        PersistentManager mgr = PathwayPersistentManager.instance();

        Tool tool = new Tool();
        tool.setName(name);
        tool.setVersion(version);
        tool.setProfiling(profiling);
        tool.setTracing(tracing);
        tool.setInstrumentCMD(instrumentCMD);
        tool.setInstrSuffix(instrumentSuffix);
        tool.setProfileCMD(profileCMD);
        tool.setProfileArgs(profileArgs);
        tool.setTraceCMD(traceCMD);
        tool.setTraceArgs(traceArgs);
        tool.setReqModules(requiredModules);
        tool.setReqEnvVars(requiredEnvVars);
        tool.setWebsite(website);

        mgr.saveObject(tool);
        
        return tool;
    }
    
    public static Application addApplication(String name, String configuration, String executableName, String arguments, String inputFiles, String ratInputFile, String phaseRegionName, boolean CPUFreqParamEnable, String CPUFreqParamValues,  boolean uncoreFreqParamEnable, String uncoreFreqParamValues, boolean ompThreadsParamEnable, String ompThreadsParamCount,boolean energyObjectiveEnable, boolean nEnergyObjectiveEnable, boolean timeObjectiveEnable, boolean nTimeObjectiveEnable, boolean edpObjectiveEnable, boolean nEdpObjectiveEnable, boolean edpsObjectiveEnable, boolean nEdpsObjectiveEnable, boolean cpuEnergyObjectiveEnable, boolean nCpuEnergyObjectiveEnable, boolean tcoObjectiveEnable, boolean nTcoObjectiveEnable, String costPerJoule, String costPerCoreHour, String energyPlugName, String energyMetricsName, boolean exhaustiveEnable, boolean individualEnable, String keep, boolean randomEnable, String samples, boolean geneticEnable, String population, String generations, String timer, boolean atpLibraryEnable, boolean atpExhaustiveEnable, boolean atpIndividualEnable, String info, String selectiveInfo, String codeLocation, String execLocation, Integer wallTime, String requiredModules) throws PersistentException {
        Application application = new Application();
        application.setName(name);
        application.setConfig(configuration);
        application.setExecutable(executableName);
        application.setStartArgs(arguments);
        application.setInputDataFileNames(inputFiles);
        application.setRatInputDataFileName(ratInputFile);
        application.setPhaseRegionName(phaseRegionName);
        application.setCPUFreqParamEnable(CPUFreqParamEnable);
        application.setCPUFreqParamValues(CPUFreqParamValues);
        application.setUncoreFreqParamEnable(uncoreFreqParamEnable);
        application.setUncoreFreqParamValues(uncoreFreqParamValues);
        application.setOMPThreadsParamEnable(ompThreadsParamEnable);
        application.setOMPThreadsParamCount(ompThreadsParamCount);
        application.setEnergy(energyObjectiveEnable);
        application.setNormalizedEnergy(nEnergyObjectiveEnable);
        application.setTime(timeObjectiveEnable);
        application.setNormalizedTime(nTimeObjectiveEnable);
        application.setEDP(edpObjectiveEnable);
        application.setNormalizedEDP(nEdpObjectiveEnable);
        application.setED2P(edpsObjectiveEnable);
        application.setNormalizedED2P(nEdpsObjectiveEnable);
        application.setCPUEnergy(cpuEnergyObjectiveEnable);
        application.setNormalizedCPUEnergy(nCpuEnergyObjectiveEnable);
        application.setTCO(tcoObjectiveEnable);
        application.setNormalizedTCO(nTcoObjectiveEnable);
        application.setCostPerJoule(costPerJoule);
        application.setCostPerCoreHour(costPerCoreHour);
        application.setEnergyPlugName(energyPlugName);
        application.setEnergyMetNames(energyMetricsName);
        application.setExhaustive(exhaustiveEnable);
        application.setIndividual(individualEnable);
        application.setKeep(keep);
        application.setRandom(randomEnable);
        application.setSamples(samples);
        application.setGenetic(geneticEnable);
        application.setPopulation(population);
        application.setMaxGenerations(generations);
        application.setTimer(timer);
        application.setATPlibParamEnable(atpLibraryEnable);
        application.setATPexhaustive(atpExhaustiveEnable);
        application.setATPindividual(atpIndividualEnable);
        application.setInfo(Integer.parseInt(info));
        application.setSelectiveInfo(selectiveInfo);
        application.setCodeLocation(codeLocation);
        application.setExecLocation(execLocation);
        application.setWallclockLimit(wallTime);
        application.setReqModules(requiredModules);

        application.save();
        return application;
    }
}
