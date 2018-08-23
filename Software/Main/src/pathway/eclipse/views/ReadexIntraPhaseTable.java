package pathway.eclipse.views;
import java.util.StringTokenizer;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import pathway.eclipse.ExecutionLog;


/** Displays Intra-Phase information in a nice table. */
class ReadexIntraPhaseTable extends Composite {

	public ReadexIntraPhaseTable(Composite parent, int style) {
        super(parent, style);

        TableColumnLayout layout = new TableColumnLayout();
        setLayout(layout);
        
        table = new Table(this,  SWT.V_SCROLL | SWT.NO_REDRAW_RESIZE);
        
        dataProvider = new SetDataListener(table);
        addListener(SWT.SetData, dataProvider);
        
        table.getVerticalBar().setVisible(true);
        createColumns(table, layout);
    }
	
	public void setInput(@Nullable String intraPhase) {
    		
    		dataProvider.setData(intraPhase);
    		table.setRedraw(false);
    		
    		for(TableItem item: table.getItems())
    			dataProvider.updateRow(item);
    		table.setRedraw(true);
    }
    
    private final Table table;
    private final SetDataListener dataProvider;


    @NonNullByDefault
    private static void createColumns(Table table, TableColumnLayout layout) {
        for( int i = 0; i < titles.length; ++i ) {
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

    private static final String[] titles = { "Name", "Granularity", "Weight", "Time Variation Reg", "Time Variation Phase", "Compute Intensity" };
    private static final int[] weights = { 1, 1, 1, 1, 1, 1};

    
    private static final class SetDataListener implements Listener {
    	
        public SetDataListener(Table table) {
            this.table = table;
        }

        @Override
        public void handleEvent(Event event) {
            TableItem item = (TableItem)event.item;
            updateRow(item);
        }
        
        public void updateRow(TableItem item) { //TableItem item
        	
        	int ind_list = table.indexOf(item);
        	
        	int Initial_pos=6*ind_list;
        	for (int i=0;i<6;i++)
        		item.setText(i,data[Initial_pos+i]);
        		
        	
        }

        public void setData(@Nullable String intraPhase) {
        	
        	if (intraPhase==null){
        		data = new String[] {"empty","empty","empty","empty","empty","empty"};
        	}
        	
        	else {
        		
        		StringTokenizer st = new StringTokenizer(intraPhase, ",");
    			data = new String[st.countTokens()];
    			
    			int count=0;
    			while (st.hasMoreTokens()) {
    				data[count] = st.nextToken();
    				count++;
    			}
        	
        	}
        	
        	table.setItemCount(data.length/6);
        }
        
        private final Table table;
        private String [] data;
    }
    
}

