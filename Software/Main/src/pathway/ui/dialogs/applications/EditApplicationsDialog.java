package pathway.ui.dialogs.applications;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.gtk.GdkColor;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.orm.PersistentException;

import com.sun.prism.paint.Color;

import pathway.PAThWayPlugin;
import pathway.data.persistence.Application;
import pathway.eclipse.UIUtils;
import pathway.eclipse.views.ExecutionParameters;




/** Represents a dialog that can be used to configure HPC applications. */
public class EditApplicationsDialog extends Dialog {
    /** Initializes a new instance. */
    public EditApplicationsDialog(Shell parentShell) {
        super(parentShell);
        updateCache();
    }


    @Override
    protected Control createDialogArea(Composite parent) {
    	
        // add the controls
        Composite cont = (Composite)super.createDialogArea(parent);
        cont.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        cont.setLayout(new GridLayout());
    	 
        ScrolledComposite sc = new ScrolledComposite(cont, SWT.H_SCROLL | SWT.V_SCROLL);
        sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true)); 
        sc.setLayout(new GridLayout());
        sc.setExpandVertical(true); 
        sc.setExpandHorizontal(true); 
        sc.setMinSize(1000, 1800);

        Composite container = new Composite(sc, SWT.NONE);
        sc.setContent(container);
        container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true)); 
        container.setLayout(createGridLayout());
        
        final GridData fillData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
        final Composite selectionArea = new Composite(container, SWT.NONE);
        selectionArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        selectionArea.setLayout(new GridLayout(3, false));

        Composite nameComposite = new Composite(selectionArea, SWT.NONE);
        nameComposite.setLayout(new GridLayout(2, false));
        nameComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        Label nameLabel = new Label(nameComposite, SWT.NONE);
        nameLabel.setText("Application:");
        nameCombo = new Combo(nameComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
        nameCombo.setItems(cache.keySet().toArray(new String[cache.size()]));
        nameCombo.select(0);
        nameCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Composite versionComposite = new Composite(selectionArea, SWT.NONE);
        versionComposite.setLayout(new GridLayout(2, false));
        versionComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        Label versionLabel = new Label(versionComposite, SWT.NONE);
        versionLabel.setText("Configuration:");
        configCombo = new Combo(versionComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
        configCombo.select(0);
        configCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        Composite buttonComposite = new Composite(selectionArea, SWT.NONE);
        buttonComposite.setLayout(new FillLayout());
        Button newButton = new Button(buttonComposite, SWT.NONE);
        newButton.setText("New...");
        removeButton = new Button(buttonComposite, SWT.NONE);
        removeButton.setText("Remove");

        // spacer before executableName, inputDataFileNames, ratInputDataFileName and arguments.
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label executableNameLabel = new Label(container, SWT.NONE);
        executableNameLabel.setText("Executable name:");
        executableNameText = new Text(container, SWT.BORDER);
        executableNameText.setLayoutData(fillData);

        Label inputDataFileNamesLabel = new Label(container, SWT.NONE);
        inputDataFileNamesLabel.setText("Application inputs for DTA:");
        inputDataFileNamesText = new Text(container, SWT.BORDER);
        inputDataFileNamesText.setLayoutData(fillData);
        
        Label ratInputDataFileNameLabel = new Label(container, SWT.NONE);
        ratInputDataFileNameLabel.setText("Application input for RAT:");
        ratInputDataFileNameText = new Text(container, SWT.BORDER);
        ratInputDataFileNameText.setLayoutData(fillData);

        Label argumentsLabel = new Label(container, SWT.NONE);
        argumentsLabel.setText("Arguments:");
        argumentsText = new Text(container, SWT.BORDER);
        argumentsText.setLayoutData(fillData);
        
        // spacer before phaseRegionName
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label phaseRegionNameLabel = new Label(container, SWT.NONE);
        phaseRegionNameLabel.setText("Phase region name:");
        phaseRegionNameText = new Text(container, SWT.BORDER);
        phaseRegionNameText.setLayoutData(fillData);
        
        //spacer before tuneParamNames
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        
        CPUFreqParamButton = new Button(container, SWT.CHECK);
        CPUFreqParamButton.setText("CPU Freq Plugin");
        CPUFreqParamButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        

        Label CPUFreqParamValuesLabel = new Label(container, SWT.NONE);
        CPUFreqParamValuesLabel.setText("CPU freq values (min, max, step) KHz:");
        CPUFreqParamValuesText = new Text(container, SWT.BORDER);
        CPUFreqParamValuesText.setLayoutData(fillData);
        
        
        uncoreFreqParamButton = new Button(container, SWT.CHECK);
        uncoreFreqParamButton.setText("Uncore Freq Plugin");
        uncoreFreqParamButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        

        Label uncoreFreqParamValuesLabel = new Label(container, SWT.NONE);
        uncoreFreqParamValuesLabel.setText("Uncore frequency values (min, max, step) KHz:");
        uncoreFreqParamValuesText = new Text(container, SWT.BORDER);
        uncoreFreqParamValuesText.setLayoutData(fillData);
        
        ompThreadsButton = new Button(container, SWT.CHECK);
        ompThreadsButton.setText("OMP Threads Plugin");
        ompThreadsButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        
        Label ompThreadsCountLabel = new Label(container, SWT.NONE);
        ompThreadsCountLabel.setText("OMP Threads count (lower_value, step):");
        ompThreadsCountText = new Text(container, SWT.BORDER);
        ompThreadsCountText.setLayoutData(fillData);
        
        // spacer before Objectives
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
 
        Label objectivesLabel = new Label(container, SWT.NONE);
        objectivesLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        objectivesLabel.setText("Objectives:");
        
        final Composite energyArea = new Composite(container, SWT.NONE);
        energyArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        energyArea.setLayout(new GridLayout(2, false));
        energyObjectiveButton = new Button(container, SWT.CHECK);
        energyObjectiveButton.setText("Energy");
        energyObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        nEnergyObjectiveButton = new Button(container, SWT.CHECK);
        nEnergyObjectiveButton.setText("Normalized Energy");
        nEnergyObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite timeArea = new Composite(container, SWT.NONE);
        timeArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        timeArea.setLayout(new GridLayout(2, false));
        timeObjectiveButton = new Button(container, SWT.CHECK);
        timeObjectiveButton.setText("Time");
        timeObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        nTimeObjectiveButton = new Button(container, SWT.CHECK);
        nTimeObjectiveButton.setText("Normalized Time");
        nTimeObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite edpArea = new Composite(container, SWT.NONE);
        edpArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        edpArea.setLayout(new GridLayout(2, false));
        edpObjectiveButton = new Button(container, SWT.CHECK);
        edpObjectiveButton.setText("Energy Delay Product");
        edpObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        nEdpObjectiveButton = new Button(container, SWT.CHECK);
        nEdpObjectiveButton.setText("Normalized Energy Delay Product");
        nEdpObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite edpsArea = new Composite(container, SWT.NONE);
        edpsArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        edpsArea.setLayout(new GridLayout(2, false));
        edpsObjectiveButton = new Button(container, SWT.CHECK);
        edpsObjectiveButton.setText("Energy Delay Product Squared");
        edpsObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        nEdpsObjectiveButton = new Button(container, SWT.CHECK);
        nEdpsObjectiveButton.setText("Normalized Energy Delay Product Squared");
        nEdpsObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite cpuArea = new Composite(container, SWT.NONE);
        cpuArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        cpuArea.setLayout(new GridLayout(2, false));
        cpuEnergyObjectiveButton = new Button(container, SWT.CHECK);
        cpuEnergyObjectiveButton.setText("CPU Energy");
        cpuEnergyObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        nCpuEnergyObjectiveButton = new Button(container, SWT.CHECK);
        nCpuEnergyObjectiveButton.setText("Normalized CPU Energy");
        nCpuEnergyObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite tcoArea = new Composite(container, SWT.NONE);
        tcoArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        tcoArea.setLayout(new GridLayout(2, false));
        tcoObjectiveButton = new Button(container, SWT.CHECK);
        tcoObjectiveButton.setText("Total Cost of Ownership");
        tcoObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        nTcoObjectiveButton = new Button(container, SWT.CHECK);
        nTcoObjectiveButton.setText("Normalized Total Cost of Ownership");
        nTcoObjectiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite tcoSelection = new Composite(container, SWT.NONE);
        tcoSelection.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 4, 1));
        tcoSelection.setLayout(new GridLayout(4, false));
        
        Label tcoLab=new Label(tcoSelection, SWT.NONE);
        tcoLab.setText("..");
        tcoLab.setSize(100, 20);
        tcoLab.setVisible(false);
        
        Composite jouleComposite = new Composite(tcoSelection, SWT.NONE);
        jouleComposite.setLayout(new GridLayout(2, false));
        Label costPerJouleLabel= new Label(jouleComposite, SWT.NONE);
        costPerJouleLabel.setText("Cost Per Joule:");
        costPerJouleLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        costPerJouleLabel.setVisible(true);
        costPerJouleText = new Text(jouleComposite, SWT.BORDER);
        costPerJouleText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        costPerJouleText.setVisible(true);
        
        Label tcoLab1=new Label(tcoSelection, SWT.NONE);
        tcoLab1.setText("Alignment test ............. ");
        tcoLab1.setSize(100, 20);
        tcoLab1.setVisible(false);
        
        Composite costComposite = new Composite(tcoSelection, SWT.NONE);
        costComposite.setLayout(new GridLayout(2, false));
        Label costPerCoreHourLabel= new Label(costComposite, SWT.NONE);
        costPerCoreHourLabel.setText("Cost Per Core Hour:");
        costPerCoreHourLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        costPerCoreHourLabel.setVisible(true);
        costPerCoreHourText = new Text(costComposite, SWT.BORDER);
        costPerCoreHourText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        costPerCoreHourText.setVisible(true);
        
        //spacer before energy metrics
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label energyMetrics = new Label(container, SWT.NONE);
        energyMetrics.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        energyMetrics.setText("Energy metrics:");
        
        Label pluginName = new Label(container, SWT.NONE);
        pluginName.setText("Plugin name: ");
        energyPlugName = new Text(container, SWT.BORDER);
        energyPlugName.setLayoutData(fillData);

        Label metricsNames = new Label(container, SWT.NONE);
        metricsNames.setText("Metrics names: ");
        energyMetricsNames = new Text(container, SWT.BORDER);	
        energyMetricsNames.setLayoutData(fillData);
        
        //spacer before PTF search algorithm
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        
        Label ptfSearchLabel = new Label(container, SWT.NONE);
        ptfSearchLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        ptfSearchLabel.setText("PTF Search algorithm:");
        
        final Composite exhaustiveArea = new Composite(container, SWT.NONE);
        exhaustiveArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        exhaustiveArea.setLayout(new GridLayout(2, false));
        exhaustiveButton = new Button(exhaustiveArea, SWT.RADIO);
        exhaustiveButton.setText("Exhaustive");
        exhaustiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite individualArea = new Composite(container, SWT.NONE);
        individualArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 4, 1));
        individualArea.setLayout(new GridLayout(4, false));
        
        individualButton = new Button(individualArea, SWT.RADIO);
        individualButton.setText("Individual");
        individualButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        Label iLab=new Label(individualArea, SWT.NONE);
        iLab.setText("Alignment test ...................................");
        iLab.setSize(30, 20);
        iLab.setVisible(false);
        
        final Composite individualComp = new Composite(individualArea, SWT.NONE);
        individualComp.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        individualComp.setLayout(new GridLayout(2, false));
        Label keepLabel= new Label(individualComp, SWT.NONE);
        keepLabel.setText("Keep:");
        keepLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        keepLabel.setVisible(true);
        keepText = new Text(individualComp, SWT.BORDER);
        keepText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        keepText.setVisible(true);
        
        final Composite randomArea = new Composite(container, SWT.NONE);
        randomArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 4, 1));
        randomArea.setLayout(new GridLayout(4, false));
        randomButton = new Button(randomArea, SWT.RADIO);
        randomButton.setText("Random");
        randomButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        Label rLab=new Label(randomArea, SWT.NONE);
        rLab.setText("Alignment test .....................................");
        rLab.setSize(30, 20);
        rLab.setVisible(false);
        
        final Composite randomComp = new Composite(randomArea, SWT.NONE);
        randomComp.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        randomComp.setLayout(new GridLayout(2, false));
        Label samplesLabel= new Label(randomComp, SWT.NONE);
        samplesLabel.setText("Samples:");
        samplesLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        samplesLabel.setVisible(true);
        samplesText = new Text(randomComp, SWT.BORDER);
        samplesText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        samplesText.setVisible(true);
        
        final Composite geneticArea = new Composite(container, SWT.NONE);
        geneticArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 10, 1));
        geneticArea.setLayout(new GridLayout(10, false));
        geneticButton = new Button(geneticArea, SWT.RADIO);
        geneticButton.setText("Genetic");
        geneticButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        Label gLab1=new Label(geneticArea, SWT.NONE);
        gLab1.setText("Alignment test ......................................");
        gLab1.setSize(30, 20);
        gLab1.setVisible(false);
        
        final Composite geneticComp = new Composite(geneticArea, SWT.NONE);
        geneticComp.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 8, 1));
        geneticComp.setLayout(new GridLayout(8, false));
        
        Label populationLabel= new Label(geneticComp, SWT.NONE);
        populationLabel.setText("Population size:");
        populationLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        populationLabel.setVisible(true);
        populationText = new Text(geneticComp, SWT.BORDER);
        populationText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        populationText.setVisible(true);
        
        Label gLab2=new Label(geneticComp, SWT.NONE);
        gLab2.setText(".....");
        gLab2.setSize(30, 20);
        gLab2.setVisible(false);
        
        Label generationsLabel= new Label(geneticComp, SWT.NONE);
        generationsLabel.setText("Max. Generations:");
        generationsLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        generationsLabel.setVisible(true);
        generationsText = new Text(geneticComp, SWT.BORDER);
        generationsText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        generationsText.setVisible(true);
        
        Label gLab3=new Label(geneticComp, SWT.NONE);
        gLab3.setText(".....");
        gLab3.setSize(30, 20);
        gLab3.setVisible(false);
        
        Label timerLabel= new Label(geneticComp, SWT.NONE);
        timerLabel.setText("Timer:");
        timerLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        timerLabel.setVisible(true);
        timerText = new Text(geneticComp, SWT.BORDER);
        timerText.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        timerText.setVisible(true);
        
        // spacer before ATP Library
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));
        
        final Composite atpArea = new Composite(container, SWT.NONE);
        atpArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        atpArea.setLayout(new GridLayout(2, false));
        ATPlibraryButton = new Button(container, SWT.CHECK);
        ATPlibraryButton.setText("ATP library:");
        ATPlibraryButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        final Composite atpExhaustiveArea = new Composite(container, SWT.NONE);
        atpExhaustiveArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        atpExhaustiveArea.setLayout(new GridLayout(2, false));
        ATPexhaustiveButton = new Button(atpExhaustiveArea, SWT.RADIO);
        ATPexhaustiveButton.setText("ATP Exhaustive");
        ATPexhaustiveButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        
        final Composite atpIndividualArea = new Composite(container, SWT.NONE);
        atpIndividualArea.setLayoutData(new GridData(SWT.FILL, SWT.BEGINNING, true, false, 2, 1));
        atpIndividualArea.setLayout(new GridLayout(2, false));
        
        ATPindividualButton = new Button(atpIndividualArea, SWT.RADIO);
        ATPindividualButton.setText("ATP Individual");
        ATPindividualButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 1, 1));
        
        
        // spacer before info and selectiveInfo
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label infoLabel = new Label(container, SWT.NONE);
        infoLabel.setText("Info:");
        infoText = new Text(container, SWT.BORDER);
        infoText.setLayoutData(fillData);

        Label selectiveInfoLabel = new Label(container, SWT.NONE);
        selectiveInfoLabel.setText("Selective info:");
        selectiveInfoText = new Text(container, SWT.BORDER);
        selectiveInfoText.setLayoutData(fillData);
       
        // spacer before reqModules
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label reqModulesLabel = new Label(container, SWT.NONE);
        reqModulesLabel.setText("Required Modules:");
        reqModulesText = new Text(container, SWT.BORDER);
        reqModulesText.setLayoutData(fillData);

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label suffixLabel = new Label(container, SWT.NONE);
        suffixLabel.setText("Make directory:");
        makeDirText = new Text(container, SWT.BORDER);
        makeDirText.setLayoutData(fillData);

        Label executionDirLabel = new Label(container, SWT.NONE);
        executionDirLabel.setText("Execution directory:");
        executionDirText = new Text(container, SWT.BORDER);
        executionDirText.setLayoutData(fillData);

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        Label wallTimeLabel = new Label(container, SWT.NONE);
        wallTimeLabel.setText("Wall-time limit (minutes):");
        wallTimeText = new Text(container, SWT.BORDER);
        wallTimeText.setLayoutData(fillData);
        wallTimeText.setEnabled(false);

        // spacer
        new Label(container, SWT.NONE).setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false, 2, 1));

        // add the listeners
        nameCombo.addModifyListener(new AppSelectionListener());
        configCombo.addModifyListener(versionListener);
        newButton.addListener(SWT.Selection, newListener);
        removeButton.addListener(SWT.Selection, removeListener);
        
        CPUFreqParamButton.addListener(SWT.Selection, checkListener);
        uncoreFreqParamButton.addListener(SWT.Selection, checkListener);
        ompThreadsButton.addListener(SWT.Selection, checkListener);
        energyObjectiveButton.addListener(SWT.Selection, checkListener);
        nEnergyObjectiveButton.addListener(SWT.Selection, checkListener);
        timeObjectiveButton.addListener(SWT.Selection, checkListener);
        nTimeObjectiveButton.addListener(SWT.Selection, checkListener);
        edpObjectiveButton.addListener(SWT.Selection, checkListener);
        nEdpObjectiveButton.addListener(SWT.Selection, checkListener);
        edpsObjectiveButton.addListener(SWT.Selection, checkListener);
        nEdpsObjectiveButton.addListener(SWT.Selection, checkListener);
        cpuEnergyObjectiveButton.addListener(SWT.Selection, checkListener);
        nCpuEnergyObjectiveButton.addListener(SWT.Selection, checkListener);
        tcoObjectiveButton.addListener(SWT.Selection, checkListener);
        nTcoObjectiveButton.addListener(SWT.Selection, checkListener); 
        ATPlibraryButton.addListener(SWT.Selection, checkListener);
        
        exhaustiveButton.addSelectionListener(rl);
        individualButton.addSelectionListener(rl);
        randomButton.addSelectionListener(rl);
        geneticButton.addSelectionListener(rl);
        ATPexhaustiveButton.addSelectionListener(rl);
        ATPindividualButton.addSelectionListener(rl);
        
        onAppSelected();
        onConfigSelected();

        container.setSize(container.computeSize(SWT.NONE, SWT.NONE, true)); 
        sc.setVisible(true);
        sc.setEnabled(true);
        sc.setAlwaysShowScrollBars(true);
        
        return cont;
    }


    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText("HPC Applications");
    }


    @Override
    protected boolean isResizable() {
        return true;
    }
    
    @Override
    protected Point getInitialSize() {
        return new Point(680, 400);
    }


    @Override
    protected void okPressed() {
        if( isDirty() )
            saveCurrentApp();

        ExecutionParameters.updateComboBoxes();
        super.okPressed();
    }


    @NonNullByDefault
    private void onAppSelected() {
        final int selectionIndex = nameCombo.getSelectionIndex();
        if( selectionIndex == -1 ) {
            configCombo.setItems(new String[0]);
            return;
        }

        ArrayList<Application> sameName = cache.get(nameCombo.getItem(selectionIndex));
        if( sameName == null )
            throw new IllegalArgumentException();

        ArrayList<String> items = new ArrayList<String>();
        for( Application app: sameName )
            items.add(app.getConfig());

        configCombo.setItems(items.toArray(new String[items.size()]));
        configCombo.select(items.size() - 1);
    }


    /** Updates all the text fields, when a new application and configuration is selected. */
    private void onConfigSelected() {
        if( isDirty() && askForSave() )
            saveCurrentApp();

        final int appIndex = nameCombo.getSelectionIndex();
        if( appIndex == -1 ) {
            selected = null;

            executableNameText.setText("");
            inputDataFileNamesText.setText("");
            ratInputDataFileNameText.setText("");
            argumentsText.setText("");
            phaseRegionNameText.setText("");
            CPUFreqParamButton.setSelection(false);
            uncoreFreqParamButton.setSelection(false);
            ompThreadsButton.setSelection(false);
            CPUFreqParamValuesText.setText("");
            uncoreFreqParamValuesText.setText("");
            ompThreadsCountText.setText("");
            energyObjectiveButton.setSelection(false);
            nEnergyObjectiveButton.setSelection(false);
            timeObjectiveButton.setSelection(false);
            nTimeObjectiveButton.setSelection(false);
            edpObjectiveButton.setSelection(false);
            nEdpObjectiveButton.setSelection(false);
            edpsObjectiveButton.setSelection(false);
            nEdpsObjectiveButton.setSelection(false);
            cpuEnergyObjectiveButton.setSelection(false);
            nCpuEnergyObjectiveButton.setSelection(false);
            tcoObjectiveButton.setSelection(false);
            nTcoObjectiveButton.setSelection(false);
            costPerJouleText.setText("");
            costPerCoreHourText.setText("");
            exhaustiveButton.setSelection(false);
            individualButton.setSelection(false);
            randomButton.setSelection(false);
            geneticButton.setSelection(false);
            keepText.setText("");
            samplesText.setText("");
            populationText.setText("");
            generationsText.setText("");
            timerText.setText("");
            energyPlugName.setText("");
            energyMetricsNames.setText("");
            ATPlibraryButton.setSelection(false);
            ATPexhaustiveButton.setSelection(false);
            ATPindividualButton.setSelection(false);
            infoText.setText("");
            selectiveInfoText.setText("");
            reqModulesText.setText("");
            makeDirText.setText("");
            executionDirText.setText("");
            wallTimeText.setText("");

            executableNameText.setEnabled(false);
            inputDataFileNamesText.setEnabled(false);
            ratInputDataFileNameText.setEnabled(false);
            argumentsText.setEnabled(false);
            phaseRegionNameText.setEnabled(false);
            CPUFreqParamButton.setEnabled(false);
            uncoreFreqParamButton.setEnabled(false);
            ompThreadsButton.setEnabled(false);
            CPUFreqParamValuesText.setEnabled(false);
            ompThreadsCountText.setEnabled(false);
            energyObjectiveButton.setEnabled(false);
            nEnergyObjectiveButton.setEnabled(false);
            timeObjectiveButton.setEnabled(false);
            nTimeObjectiveButton.setEnabled(false);
            edpObjectiveButton.setEnabled(false);
            nEdpObjectiveButton.setEnabled(false);
            edpsObjectiveButton.setEnabled(false);
            nEdpsObjectiveButton.setEnabled(false);
            cpuEnergyObjectiveButton.setEnabled(false);
            nCpuEnergyObjectiveButton.setEnabled(false);
            tcoObjectiveButton.setEnabled(false);
            nTcoObjectiveButton.setEnabled(false);
            costPerJouleText.setEnabled(false);
            costPerCoreHourText.setEnabled(false);
            exhaustiveButton.setEnabled(false);
            individualButton.setEnabled(false);
            randomButton.setEnabled(false);
            geneticButton.setEnabled(false);
            keepText.setEnabled(false);
            samplesText.setEnabled(false);
            populationText.setEnabled(false);
            generationsText.setEnabled(false);
            timerText.setEnabled(false);
            energyPlugName.setEnabled(false);
            energyMetricsNames.setEnabled(false);
            ATPlibraryButton.setEnabled(false);
            ATPexhaustiveButton.setEnabled(false);
            ATPindividualButton.setEnabled(false);
            uncoreFreqParamValuesText.setEnabled(false);
            infoText.setEnabled(false);
            selectiveInfoText.setEnabled(false);
            reqModulesText.setEnabled(false);
            makeDirText.setEnabled(false);
            executionDirText.setEnabled(false);
            wallTimeText.setEnabled(false);
            removeButton.setEnabled(false);
        }
        else {
            // happens as an intermediate state after setting the list of versions and before selecting one of them
            final int versionIndex = configCombo.getSelectionIndex();
            if( versionIndex == -1 )
                return;

            ArrayList<Application> sameName = cache.get(nameCombo.getItem(appIndex));
            selected = sameName.get(versionIndex);

            executableNameText.setText(selected.getExecutable() == null ? "" : selected.getExecutable());
            inputDataFileNamesText.setText(selected.getInputDataFileNames() == null ? "" : selected.getInputDataFileNames());
            ratInputDataFileNameText.setText(selected.getRatInputDataFileName()== null ? "" : selected.getRatInputDataFileName());
            argumentsText.setText(selected.getStartArgs() == null ? "" : selected.getStartArgs());
            phaseRegionNameText.setText(selected.getPhaseRegionName() == null ? "" : selected.getPhaseRegionName());
            CPUFreqParamButton.setSelection(selected.getCPUFreqParamEnable());
            uncoreFreqParamButton.setSelection(selected.getUncoreFreqParamEnable());
            ompThreadsButton.setSelection(selected.getOMPThreadsParamEnable());
            energyObjectiveButton.setSelection(selected.getEnergy());
            nEnergyObjectiveButton.setSelection(selected.getNormalizedEnergy());
            timeObjectiveButton.setSelection(selected.getTime());
            nTimeObjectiveButton.setSelection(selected.getNormalizedTime());
            edpObjectiveButton.setSelection(selected.getEDP());
            nEdpObjectiveButton.setSelection(selected.getNormalizedEDP());
            edpsObjectiveButton.setSelection(selected.getED2P());
            nEdpsObjectiveButton.setSelection(selected.getNormalizedED2P());
            cpuEnergyObjectiveButton.setSelection(selected.getCPUEnergy());
            nCpuEnergyObjectiveButton.setSelection(selected.getNormalizedCPUEnergy());
            tcoObjectiveButton.setSelection(selected.getTCO());
            nTcoObjectiveButton.setSelection(selected.getNormalizedTCO());
            exhaustiveButton.setSelection(selected.getExhaustive());
            individualButton.setSelection(selected.getIndividual());
            randomButton.setSelection(selected.getRandom());
            geneticButton.setSelection(selected.getGenetic());
            ATPlibraryButton.setSelection(selected.getATPlibParamEnable());
            ATPexhaustiveButton.setSelection(selected.getATPexhaustive());
            ATPindividualButton.setSelection(selected.getATPindividual());
            CPUFreqParamValuesText.setText(selected.getCPUFreqParamValues() == null ? "" : selected.getCPUFreqParamValues());
            uncoreFreqParamValuesText.setText(selected.getUncoreFreqParamValues() == null ? "" : selected.getUncoreFreqParamValues());
            ompThreadsCountText.setText(selected.getOMPThreadsParamCount()==null ? "" : selected.getOMPThreadsParamCount());
            costPerJouleText.setText(selected.getCostPerJoule()==null? "" : selected.getCostPerJoule());
            costPerCoreHourText.setText(selected.getCostPerCoreHour()==null? "" : selected.getCostPerCoreHour());
            keepText.setText(selected.getKeep()==null ? "" : selected.getKeep());
            samplesText.setText(selected.getSamples()==null ? "" : selected.getSamples());
            populationText.setText(selected.getPopulation()==null ? "" : selected.getPopulation());
            generationsText.setText(selected.getMaxGenerations()==null ? "" : selected.getMaxGenerations());
            timerText.setText(selected.getTimer()==null ? "" : selected.getTimer());
            energyPlugName.setText(selected.getEnergyPlugName() == null ? "" : selected.getEnergyPlugName());
            energyMetricsNames.setText(selected.getEnergyMetNames() == null ? "" : selected.getEnergyMetNames());
            infoText.setText(selected.getInfo()== null ? "" : selected.getInfo().toString());
            selectiveInfoText.setText(selected.getSelectiveInfo()== null ? "" : selected.getSelectiveInfo());
            reqModulesText.setText(selected.getReqModules() == null ? "" : selected.getReqModules());
            makeDirText.setText(selected.getCodeLocation() == null ? "" : selected.getCodeLocation());
            executionDirText.setText(selected.getExecLocation() == null ? "" : selected.getExecLocation());
            wallTimeText.setText(Integer.toString(selected.getWallclockLimit()));

            executableNameText.setEnabled(true);
            inputDataFileNamesText.setEnabled(true);
            ratInputDataFileNameText.setEnabled(true);
            argumentsText.setEnabled(true);
            phaseRegionNameText.setEnabled(true);
            CPUFreqParamButton.setEnabled(true);
            uncoreFreqParamButton.setEnabled(true);
            ompThreadsButton.setEnabled(true);
            ATPlibraryButton.setEnabled(true);
            ATPexhaustiveButton.setEnabled(true);
            ATPindividualButton.setEnabled(true);
            CPUFreqParamValuesText.setEnabled(true);
            uncoreFreqParamValuesText.setEnabled(true);
            ompThreadsCountText.setEnabled(true);
            energyObjectiveButton.setEnabled(true);
            nEnergyObjectiveButton.setEnabled(true);
            timeObjectiveButton.setEnabled(true);
            nTimeObjectiveButton.setEnabled(true);
            edpObjectiveButton.setEnabled(true);
            nEdpObjectiveButton.setEnabled(true);
            edpsObjectiveButton.setEnabled(true);
            nEdpsObjectiveButton.setEnabled(true);
            cpuEnergyObjectiveButton.setEnabled(true);
            nCpuEnergyObjectiveButton.setEnabled(true);
            tcoObjectiveButton.setEnabled(true);
            nTcoObjectiveButton.setEnabled(true);
            costPerJouleText.setEnabled(true);
            costPerCoreHourText.setEnabled(true);
            exhaustiveButton.setEnabled(true);
            individualButton.setEnabled(true);
            randomButton.setEnabled(true);
            geneticButton.setEnabled(true);
            keepText.setEnabled(true);
            samplesText.setEnabled(true);
            populationText.setEnabled(true);
            generationsText.setEnabled(true);
            timerText.setEnabled(true);
            energyPlugName.setEnabled(true);
            energyMetricsNames.setEnabled(true);
            infoText.setEnabled(true);
            selectiveInfoText.setEnabled(true);
            reqModulesText.setEnabled(true);
            makeDirText.setEnabled(true);
            executionDirText.setEnabled(true);
            wallTimeText.setEnabled(true);
            removeButton.setEnabled(true);
        }
        checkListener.handleEvent(null);

    }


    /** Saves the currently selected application to the database. */
    private void saveCurrentApp() {
        selected.setExecutable(executableNameText.getText());
        selected.setInputDataFileNames(inputDataFileNamesText.getText());
        selected.setRatInputDataFileName(ratInputDataFileNameText.getText());
        selected.setStartArgs(argumentsText.getText());
        selected.setPhaseRegionName(phaseRegionNameText.getText());
        selected.setCPUFreqParamEnable(CPUFreqParamButton.getSelection());
        selected.setUncoreFreqParamEnable(uncoreFreqParamButton.getSelection());
        selected.setOMPThreadsParamEnable(ompThreadsButton.getSelection());
        selected.setEnergy(energyObjectiveButton.getSelection());
        selected.setNormalizedEnergy(nEnergyObjectiveButton.getSelection());
        selected.setTime(timeObjectiveButton.getSelection());
        selected.setNormalizedTime(nTimeObjectiveButton.getSelection());
        selected.setEDP(edpObjectiveButton.getSelection());
        selected.setNormalizedEDP(nEdpObjectiveButton.getSelection());
        selected.setED2P(edpsObjectiveButton.getSelection());
        selected.setNormalizedED2P(nEdpsObjectiveButton.getSelection());
        selected.setCPUEnergy(cpuEnergyObjectiveButton.getSelection());
        selected.setNormalizedCPUEnergy(nCpuEnergyObjectiveButton.getSelection());
        selected.setTCO(tcoObjectiveButton.getSelection());
        selected.setNormalizedTCO(nTcoObjectiveButton.getSelection());
        selected.setATPlibParamEnable(ATPlibraryButton.getSelection());
        selected.setATPexhaustive(ATPexhaustiveButton.getSelection());
        selected.setATPindividual(ATPindividualButton.getSelection());
        selected.setCPUFreqParamValues(CPUFreqParamValuesText.getText());
        selected.setUncoreFreqParamValues(uncoreFreqParamValuesText.getText());
        selected.setOMPThreadsParamCount(ompThreadsCountText.getText());
        selected.setCostPerJoule(costPerJouleText.getText());
        selected.setCostPerCoreHour(costPerCoreHourText.getText());
        selected.setExhaustive(exhaustiveButton.getSelection());
        selected.setIndividual(individualButton.getSelection());
        selected.setGenetic(geneticButton.getSelection());
        selected.setRandom(randomButton.getSelection());
        selected.setKeep(keepText.getText());
        selected.setSamples(samplesText.getText());
        selected.setPopulation(populationText.getText());
        selected.setMaxGenerations(generationsText.getText());
        selected.setTimer(timerText.getText());
        selected.setEnergyPlugName(energyPlugName.getText());
        selected.setEnergyMetNames(energyMetricsNames.getText());
        selected.setInfo(Integer.parseInt(infoText.getText()));
        selected.setSelectiveInfo(selectiveInfoText.getText());
        selected.setReqModules(reqModulesText.getText());
        selected.setCodeLocation(makeDirText.getText());
        selected.setExecLocation(executionDirText.getText());
        selected.setWallclockLimit(Integer.parseInt(wallTimeText.getText()));

        try {
            selected.save();
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to save the application configuration.", ex);
        }
        updateCache();
    }


    /** Updates the application cache. */
    private void updateCache() {
        try {
            pathway.data.persistence.PathwayPersistentManager.instance().getSession().flush();
            List<Application> temp = Application.queryApplication(null, null);

            cache.clear();
            for( Application app: temp ) {
                ArrayList<Application> sameName = cache.get(app.getName());
                if( sameName == null ) {
                    sameName = new ArrayList<Application>();
                    cache.put(app.getName(), sameName);
                }

                sameName.add(app);
            }
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            throw new RuntimeException("Unable to query the applications from the database.", ex);
        }
    }


    /** Indicates whether the currently selected application has been changed. */
    private boolean isDirty() {
        return !isClean();
    }


    /** Indicates whether the currently selected application has NOT been changed. */
    private boolean isClean() {
        if( selected == null )
            return true;

        return executableNameText.getText().equals(selected.getExecutable()) && 
        	   inputDataFileNamesText.getText().equals(selected.getInputDataFileNames()) && 
        	   ratInputDataFileNameText.getText().equals(selected.getRatInputDataFileName()) &&
        	   argumentsText.getText().equals(selected.getStartArgs()) && 
        	   phaseRegionNameText.getText().equals(selected.getPhaseRegionName()) &&
        	   CPUFreqParamButton.getSelection() == selected.getCPUFreqParamEnable()&&
        	   uncoreFreqParamButton.getSelection() == selected.getUncoreFreqParamEnable()&&
        	   ompThreadsButton.getSelection() == selected.getOMPThreadsParamEnable()&&
        	   ATPlibraryButton.getSelection() ==selected.getATPlibParamEnable() &&
        	   energyObjectiveButton.getSelection()==selected.getEnergy() &&
        	   nEnergyObjectiveButton.getSelection()==selected.getNormalizedEnergy() &&
        	   timeObjectiveButton.getSelection()==selected.getTime() &&
               nTimeObjectiveButton.getSelection()==selected.getNormalizedTime() &&
               edpObjectiveButton.getSelection()==selected.getEDP() &&
               nEdpObjectiveButton.getSelection()==selected.getNormalizedEDP() &&
               edpsObjectiveButton.getSelection()==selected.getED2P() &&
               nEdpsObjectiveButton.getSelection()==selected.getNormalizedED2P() &&
               cpuEnergyObjectiveButton.getSelection()==selected.getCPUEnergy() &&
               nCpuEnergyObjectiveButton.getSelection()==selected.getNormalizedCPUEnergy() &&
               tcoObjectiveButton.getSelection()==selected.getTCO() &&
               nTcoObjectiveButton.getSelection()==selected.getNormalizedTCO() &&
               ATPexhaustiveButton.getSelection()==selected.getATPexhaustive() &&
               ATPindividualButton.getSelection()==selected.getATPindividual() &&
        	   CPUFreqParamValuesText.getText().equals(selected.getCPUFreqParamValues()) &&
        	   uncoreFreqParamValuesText.getText().equals(selected.getUncoreFreqParamValues()) &&
        	   ompThreadsCountText.getText().equals(selected.getOMPThreadsParamCount()) &&
        	   costPerJouleText.getText().equals(selected.getCostPerJoule()) &&
        	   costPerCoreHourText.getText().equals(selected.getCostPerCoreHour()) &&
        	   exhaustiveButton.getSelection() == selected.getExhaustive() &&
        	   individualButton.getSelection() == selected.getIndividual() &&
        	   geneticButton.getSelection() == selected.getGenetic() &&
        	   randomButton.getSelection() == selected.getRandom() &&
        	   keepText.getText().equals(selected.getKeep()) &&
        	   samplesText.getText().equals(selected.getSamples()) &&
        	   populationText.getText().equals(selected.getPopulation()) &&
        	   generationsText.getText().equals(selected.getMaxGenerations()) &&
        	   timerText.getText().equals(selected.getTimer()) &&
        	   energyPlugName.getText().equals(selected.getEnergyPlugName()) &&
        	   energyMetricsNames.getText().equals(selected.getEnergyMetNames()) &&
        	   infoText.getText().equals(selected.getInfo().toString()) &&
        	   selectiveInfoText.getText().equals(selected.getSelectiveInfo()) &&
        	   reqModulesText.getText().equals(selected.getReqModules()) && 
        	   makeDirText.getText().equals(selected.getCodeLocation()) && 
        	   executionDirText.getText().equals(selected.getExecLocation()) && 
        	   wallTimeText.getText().equals(Integer.toString(selected.getWallclockLimit()));
    }


    private boolean askForSave() {
        MessageBox messageDialog = new MessageBox(getShell(), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
        messageDialog.setText("Application configuration");
        messageDialog.setMessage("The application \"" + selected.getName() + "\" has been modified. Do you want to save the changes?");

        return messageDialog.open() == SWT.YES;
    }


    private void addApp(String name, String config) {
        try {
            Application newApp = Application.createApplication();
            newApp.setName(name);
            newApp.setConfig(config);
            newApp.setExecutable("");
            newApp.setInputDataFileNames("");
            newApp.setStartArgs("");
            newApp.setPhaseRegionName("");
            newApp.setTuneParamNames("");
            newApp.setCPUFreqParamValues("");
            newApp.setInfo(0);
            newApp.setSelectiveInfo("");
            newApp.setReqModules("[]");
            newApp.setCodeLocation("");
            newApp.setExecLocation("");
            newApp.setWallclockLimit(30);
            newApp.setReqEnvVars("{}");
            newApp.save();
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to save the new application.", ex);
        }

        updateCache();
        nameCombo.setItems(cache.keySet().toArray(new String[cache.size()]));
        selectApp(name, config);
    }


    private void removeApp() {
        if( selected == null )
            throw new IllegalArgumentException();

        try {
            selected.delete();
        }
        catch( PersistentException ex ) {
            ex.printStackTrace();
            UIUtils.showErrorMessage("Unable to remove the application configuration.", ex);
        }

        String name = selected.getName();
        updateCache();
        nameCombo.setItems(cache.keySet().toArray(new String[cache.size()]));
        selectAppName(name);
        ExecutionParameters.updateComboBoxes();
    }


    private void selectApp(String name, String config) {
        selectAppName(name);
        selectAppConfig(config);
    }


    private void selectAppName(String name) {
        String[] apps = nameCombo.getItems();
        for( int i = 0; i != apps.length; ++i )
            if( apps[i].equals(name) ) {
                nameCombo.select(i);
                return;
            }

        // fallback, even valid when there are no items
        nameCombo.select(0);
    }


    private void selectAppConfig(String config) {
        String[] versions = configCombo.getItems();
        for( int i = 0; i != versions.length; ++i )
            if( versions[i].equals(config) ) {
                configCombo.select(i);

                // this is a workaround; when the first application is added, it is automatically selected and selecting it here will not trigger the change
                // listener
                if( versions.length == 1 )
                    onConfigSelected();

                return;
            }

        // fallback, even valid when there are no items
        configCombo.select(0);
    }


    private boolean appExists(String name, String config) {
        ArrayList<Application> apps = cache.get(name);
        if( apps == null )
            return false;

        for( Application app: apps )
            if( app.getConfig().equals(config) )
                return true;

        return false;
    }
    

    private final HashMap<String, ArrayList<Application>> cache = new HashMap<String, ArrayList<Application>>();
    private final ConfigSelectionListener versionListener = new ConfigSelectionListener();
    private final NewListener newListener = new NewListener();
    private final RemoveListener removeListener = new RemoveListener();
    private final CheckListener checkListener = new CheckListener();
    private final RadioSelectionListener rl = new RadioSelectionListener();
    private Application selected;
    private Combo nameCombo;
    private Combo configCombo;
    private Text executableNameText;
    private Text inputDataFileNamesText;
    private Text ratInputDataFileNameText;
    private Text argumentsText;
    private Text phaseRegionNameText;
    private Button CPUFreqParamButton;
    private Button uncoreFreqParamButton;
    private Text CPUFreqParamValuesText;
    private Text uncoreFreqParamValuesText;
    private Text infoText;
    private Text selectiveInfoText;
    private Text reqModulesText;
    private Text makeDirText;
    private Text executionDirText;
    private Text wallTimeText;
    private Button removeButton;
    private Button ompThreadsButton;
    private Text ompThreadsCountText;
    private Button ATPlibraryButton;
    private Button ATPexhaustiveButton;
    private Button ATPindividualButton;
    private Button energyObjectiveButton;
    private Button timeObjectiveButton;
    private Button edpObjectiveButton;
    private Button edpsObjectiveButton;
    private Button cpuEnergyObjectiveButton;
    private Button tcoObjectiveButton;
    private Text costPerJouleText;
    private Text costPerCoreHourText;
    private Button nEnergyObjectiveButton;
    private Button nTimeObjectiveButton;
    private Button nEdpObjectiveButton;
    private Button nEdpsObjectiveButton;
    private Button nCpuEnergyObjectiveButton;
    private Button nTcoObjectiveButton;
    private Button exhaustiveButton;
    private Button individualButton;
    private Button geneticButton;
    private Button randomButton;
    private Text keepText;
    private Text samplesText;
    private Text populationText;
    private Text generationsText;
    private Text timerText;
    private Text energyPlugName;
    private Text energyMetricsNames;

    @NonNullByDefault
    private static GridLayout createGridLayout() {
        GridLayout layout = new GridLayout();
        layout.marginHeight = 5;
        layout.marginWidth = 5;
        layout.numColumns = 2;
        layout.horizontalSpacing = 40;
        layout.verticalSpacing = 10;

        return layout;
    }


    /** Calls onAppSelected(), when the user selects another application in the combo box. */
    private class AppSelectionListener implements ModifyListener {
        @Override
        public void modifyText(ModifyEvent evt) {
            onAppSelected();
        }
    }


    /** Calls onConfigSelected(), when the user selects another configuration in the combo box. */
    private class ConfigSelectionListener implements ModifyListener {
        @Override
        public void modifyText(ModifyEvent evt) {
            onConfigSelected();
        }
    }

    /** Creates a new application configuration. */
    private class NewListener implements Listener {
        @Override
        public void handleEvent(Event event) {
            AddApplicationDialog dialog;

            while( true ) {
                dialog = new AddApplicationDialog(PAThWayPlugin.getShell());
                dialog.create();
                if( dialog.open() != Window.OK )
                    return;

                if( appExists(dialog.appName, dialog.appConfig) )
                    UIUtils.showErrorMessage("This application configuration already exists.");
                else
                    break;
            }

            addApp(dialog.appName, dialog.appConfig);
        }
    }


    /** Removes an application configuration. */
    private class RemoveListener implements Listener {
        @Override
        public void handleEvent(Event event) {
            if( !MessageDialog.openConfirm(PAThWayPlugin.getShell(), "Removing application", "Are you sure you want to remove this application configuration?") )
                return;

            removeApp();
        }
    }
    
    /** Enables and disables the text fields when the check boxes are changed. */
    private class CheckListener implements Listener {
    	@Override
    	public void handleEvent(Event event) {

    		boolean CPUFreqParam = CPUFreqParamButton.getSelection();
    		CPUFreqParamValuesText.setEnabled(CPUFreqParam);
    		if (CPUFreqParam==false)
    			CPUFreqParamValuesText.setText("");

    		boolean uncoreFreqParam = uncoreFreqParamButton.getSelection();
    		uncoreFreqParamValuesText.setEnabled(uncoreFreqParam);
    		if (uncoreFreqParam==false)
    			uncoreFreqParamValuesText.setText("");

    		boolean ompThreadsParam=ompThreadsButton.getSelection();
    		ompThreadsCountText.setEnabled(ompThreadsParam);
    		if (ompThreadsParam==false) 
    			ompThreadsCountText.setText("");

    		boolean tcoParams=tcoObjectiveButton.getSelection();
    		boolean nTcoParams=nTcoObjectiveButton.getSelection();

    		if (tcoParams==true || nTcoParams==true) {
    			costPerJouleText.setEnabled(true);
    			costPerCoreHourText.setEnabled(true);
    		} else {
    			costPerJouleText.setText("");
    			costPerCoreHourText.setText("");
    			costPerJouleText.setEnabled(false);
    			costPerCoreHourText.setEnabled(false);
    		}

    		if (individualButton.getSelection()==false && randomButton.getSelection()==false && geneticButton.getSelection()==false && exhaustiveButton.getSelection()==false) {
    			keepText.setText("");
    			keepText.setEnabled(false);
    			samplesText.setText("");
    			samplesText.setEnabled(false);
    			populationText.setText("");
    			generationsText.setText("");
    			timerText.setText("");
    			populationText.setEnabled(false);
    			generationsText.setEnabled(false);
    			timerText.setEnabled(false);
    		}

    		if (ATPlibraryButton.getSelection()==true) {
    			ATPlibraryButton.setSelection(true); 
    			ATPexhaustiveButton.setEnabled(true);
    			ATPindividualButton.setEnabled(true);

    		} else {

    			ATPexhaustiveButton.setEnabled(false);
    			ATPindividualButton.setEnabled(false);

    		}				

    	}

    }


    /** Enables and disables the radio buttons associated with PTF algorithm and ATP Library when are changed by the user */
    private class RadioSelectionListener implements SelectionListener {

    	@Override
    	public void widgetSelected(SelectionEvent e) {

    		if (e.getSource() instanceof Button) {
    			Button b = (Button) e.getSource();
    			String text = b.getText();

    			if (text.equalsIgnoreCase("Exhaustive")) { 
    				individualButton.setSelection(false);
    				geneticButton.setSelection(false);
    				randomButton.setSelection(false);
    			}

    			if (text.equalsIgnoreCase("Individual")) { 
    				exhaustiveButton.setSelection(false);
    				geneticButton.setSelection(false);
    				randomButton.setSelection(false);
    				keepText.setEnabled(true);
    			} 

    			if (text.equalsIgnoreCase("Random")) { 
    				individualButton.setSelection(false);
    				geneticButton.setSelection(false);
    				exhaustiveButton.setSelection(false);
    				samplesText.setEnabled(true);
    			} 

    			if (text.equalsIgnoreCase("Genetic")) { 
    				individualButton.setSelection(false);
    				exhaustiveButton.setSelection(false);
    				randomButton.setSelection(false);

    				populationText.setEnabled(true);
    				generationsText.setEnabled(true);
    				timerText.setEnabled(true);
    			}

    			if (individualButton.getSelection()==false) { 
    				keepText.setText("");
    				keepText.setEnabled(false);
    			}

    			if (randomButton.getSelection()==false) {
    				samplesText.setText("");
    				samplesText.setEnabled(false);
    			}

    			if (geneticButton.getSelection()==false) {
    				populationText.setText("");
    				generationsText.setText("");
    				timerText.setText("");
    				populationText.setEnabled(false);
    				generationsText.setEnabled(false);
    				timerText.setEnabled(false);
    			}

    			if (text.equalsIgnoreCase("ATP Exhaustive")) {
    				ATPexhaustiveButton.setEnabled(true);
    				ATPexhaustiveButton.setSelection(true);
    				ATPindividualButton.setEnabled(true);
    				ATPindividualButton.setSelection(false);
    			} 

    			if (text.equalsIgnoreCase("ATP Individual")) {
    				ATPexhaustiveButton.setEnabled(true);
    				ATPexhaustiveButton.setSelection(false);
    				ATPindividualButton.setEnabled(true);
    				ATPindividualButton.setSelection(true);
    			}

    		} 

    	}


    	@Override
    	public void widgetDefaultSelected(SelectionEvent e) {

    	}
    }

}



	
    
