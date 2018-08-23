package pathway.ui.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.TransformerUtils;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ListDialog;
import org.orm.PersistentException;
import pathway.data.persistence.HPCSystem;

public class GetAvailHPCSystems {
	public static String showDialog()
	{
		List<HPCSystem> systems = new ArrayList<HPCSystem>();
		try {
			systems = HPCSystem.queryHPCSystem(null, null);
		} catch (PersistentException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "There was an error while querying the available HPC systems from the internal database:\n" + e.getLocalizedMessage(), "Error querying the internal database",
					JOptionPane.ERROR_MESSAGE);
		}

		final
		List<String> systemNames = (List<String>) CollectionUtils.collect(systems, TransformerUtils.invokerTransformer("getName"));
		Collections.sort(systemNames);
		//systemNames.add(0, "New...");


		//		String answer = (String) JOptionPane.showInputDialog(null, "Please choose the hpc system:", "HPC System Selection",
		//				JOptionPane.QUESTION_MESSAGE, null,  systemNames.toArray(), "New...");

		final String[] answer = new String[1];
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				Shell activeShell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
				ListDialog dlg = new ListDialog(activeShell);
				dlg.setTitle("HPC System Selection");
				dlg.setMessage("Please choose the hpc system:");
				dlg.setContentProvider(new ArrayContentProvider());
				dlg.setLabelProvider(new LabelProvider());
				dlg.setInput(systemNames.toArray());
				//dlg.setInitialSelections(new String[]{"New..."});

				if (Window.OK == dlg.open() && dlg.getResult().length > 0) {
					answer[0] = (String) dlg.getResult()[0];
				}
			}
		});
		return answer[0];
	}
}
