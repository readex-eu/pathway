package pathway.eclipse.views;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.joda.time.format.DateTimeFormat;
import org.orm.PersistentException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import pathway.PAThWayPlugin;
import pathway.data.persistence.Application;
import pathway.data.persistence.Experiment;
import pathway.eclipse.ExecutionLog;
import pathway.eclipse.UIUtils;




/** Displays the provided experiments in a nice table. */
class ReadexExperimentTable extends Composite {
    public ReadexExperimentTable(Composite parent, int style) {
        super(parent, style);

        TableColumnLayout layout = new TableColumnLayout();
        
        setLayout(layout);
        table = new Table(this,  SWT.V_SCROLL | SWT.NO_REDRAW_RESIZE);
        dataProvider = new SetDataListener(table);
        addListener(SWT.SetData, dataProvider);
        table.addSelectionListener(new ExperimentSelectionListener());
        table.getVerticalBar().setVisible(true);
        createExperimentColumns(table, layout);
    }

    public void setInput(@Nullable Experiment[] input, ReadexIntraPhaseTable readexIntraPhaseT, ReadexInterPhaseTable readexInterPhaseT, Text dtaTuningModelT ) {
    	
    		readexIntraPhaseTable=readexIntraPhaseT;
    		readexInterPhaseTable=readexInterPhaseT;
    		dtaTuningModelText=dtaTuningModelT;
    	
    		dataProvider.setData(input);

    		table.setRedraw(false);
    		for( TableItem item: table.getItems() )
    			dataProvider.updateRow(item);
    		table.setRedraw(true);
    }


    public int getSelection() {
        return table.getSelectionIndex();
    }


    public void setSelection(int index) {
        table.setSelection(index);
    }

    private final Table table;
    private final SetDataListener dataProvider;

    private Text dtaTuningModelText;
    private ReadexIntraPhaseTable readexIntraPhaseTable;
    private ReadexInterPhaseTable readexInterPhaseTable;

    @NonNullByDefault
    private static void createExperimentColumns(Table table, TableColumnLayout layout) {
        for( int i = 0; i != titles.length; ++i ) {
            TableColumn column = new TableColumn(table, SWT.NONE);
            column.setText(titles[i]);
            column.setResizable(false);
            column.setMoveable(false);
            column.setAlignment(SWT.LEFT);

            layout.setColumnData(column, new ColumnWeightData(weights[i]));
        }

        table.setHeaderVisible(true);
        table.setLinesVisible(true);
    }


    private static void updateExperimentData(@Nullable Experiment experiment) {
        ReadexExperimentDetails view = (ReadexExperimentDetails)UIUtils.findView(PAThWayPlugin.VIEW_READEX_EXPERIMENT_DETAILS);

        if( view == null )
            return;

        view.setShownExperiment(experiment);

    }

    private static final String[] titles = { "Date", "MPI Processes", "OMP Threads", "Input Data File", "HPC System" };
    private static final int[] weights = { 1, 1, 1, 1, 1};

    private class ExperimentSelectionListener implements SelectionListener {
        @Override
        public void widgetSelected(SelectionEvent e) {
            int index = table.getSelectionIndex();
            Experiment experiment = dataProvider.data[index];
            
            TableItem ti = (TableItem) e.item;
            dataProvider.handleSelectionEvent(ti);
            
            updateExperimentData(experiment);
     }


        @Override
        public void widgetDefaultSelected(SelectionEvent e) {
            int index = table.getSelectionIndex();
            Experiment experiment = dataProvider.data[index];

            try {
                ReadexExperimentDetails view = (ReadexExperimentDetails)UIUtils.getView(PAThWayPlugin.VIEW_READEX_EXPERIMENT_DETAILS);
                view.setShownExperiment(experiment);
            }
            catch( PartInitException ex ) {
                ex.printStackTrace();
                UIUtils.showErrorMessage("Unable to display experiment details.", ex);
            }
        }
    }

    
    private final class SetDataListener implements Listener {
    	
        public SetDataListener(Table table) {
            this.table = table;
        }

        @Override
        public void handleEvent(Event event) {
        	
        	TableItem item = (TableItem)event.item;
            updateRow(item);
           
        }
        
        
        public void handleSelectionEvent(TableItem ti) {
        	
            updateRow(ti);
        }

        
        public void updateRow(TableItem item) {
        	
            Experiment experiment = data[table.indexOf(item)];
            try {
				experiment.refresh();
			} catch (PersistentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            item.setText(0, DateTimeFormat.shortDateTime().print(experiment.getExpDate().getTime()));
            item.setText(1, Integer.toString(experiment.getMpiProcs()));
            item.setText(2, Integer.toString(experiment.getOmpThreads()));
            item.setText(3, experiment.getInputDataFileName() == null ? "(default)" : experiment.getInputDataFileName());
            item.setText(4, experiment.getHPCSystem().getName());
            
    		readexIntraPhaseTable.setInput(experiment.getIntraPhaseDynamism());
    		readexInterPhaseTable.setInput(experiment.getInterPhaseDynamism());
    		
    		if (experiment.getTuningModel()==null)
    			dtaTuningModelText.setText("Information not available");
    		else {
    			String tuningMod=experiment.getTuningModel();
    			String prettyJson = toPrettyFormat(tuningMod);
    			dtaTuningModelText.setText(prettyJson);
    		}

        }
        
        
        /**
    	 * Convert a JSON string to pretty print version
    	 * @param jsonString
    	 * @return
    	 */
    	public String toPrettyFormat(String jsonString) 
    	{
    		JsonParser parser = new JsonParser();
    		JsonObject json = parser.parse(jsonString).getAsJsonObject();

    		Gson gson = new GsonBuilder().setPrettyPrinting().create();
    		String prettyJson = gson.toJson(json);

    		return prettyJson;
    	}


        public void setData(@Nullable Experiment[] data) {
            if( data == null )
                data = new Experiment[0];

            this.data = data;
            table.setItemCount(data.length);
        }

        private final Table table;
        private Experiment[] data;
    }
    
    /** Specifies the application to be shown in this view. */
    public void setShownApplication(@Nullable Application application) {
        
    }
}
