package pathway.eclipse.views;

import java.util.List;
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
import org.jdom2.Element;

import pathway.eclipse.ExecutionLog;


/** Displays Inter-Phase information in a nice table. */
class ReadexInterPhaseTable extends Composite {

	public ReadexInterPhaseTable(Composite parent, int style) {
        super(parent, style);

        TableColumnLayout layout = new TableColumnLayout();
        setLayout(layout);
        
        table = new Table(this,  SWT.V_SCROLL | SWT.NO_REDRAW_RESIZE);
        
        dataProvider = new SetDataListener(table);
        addListener(SWT.SetData, dataProvider);
        
        table.getVerticalBar().setVisible(true);
        createColumns(table, layout);
    }

    //update items
    public void setInput(@Nullable String interPhase) {
    		
    		dataProvider.setData(interPhase);
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

    private static final String[] titles = { "Name", "Variation" };
    private static final int[] weights = { 1, 1};

    
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
        	int Initial_pos=2*ind_list;
        	
        	for (int i=0;i<2;i++)
        		item.setText(i,data[Initial_pos+i]);
        }

        public void setData(@Nullable String interPhase) {
        	
        	if (interPhase==null) {
        		data = new String[] {"empty","empty"};
        	}
        	
        	else {
        		
        		StringTokenizer st = new StringTokenizer(interPhase, ",");
    			data = new String[st.countTokens()];
    			
    			int count=0;
    			while (st.hasMoreTokens()) {
    				data[count] = st.nextToken();
    				count++;
    			}
        	
        	}
        	
        	table.setItemCount(data.length/2);
        }
        
        private final Table table;
        private String [] data;
    }
    
}

