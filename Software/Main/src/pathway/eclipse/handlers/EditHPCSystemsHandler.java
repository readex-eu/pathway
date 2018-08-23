package pathway.eclipse.handlers;

import javax.swing.SwingUtilities;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

public class EditHPCSystemsHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener)
	{
	}

	@Override
	public void dispose()
	{
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				pathway.ui.dialogs.EditHPCSystems.showDialog();
			}
		});

		return null;
	}

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	@Override
	public boolean isHandled()
	{
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener)
	{
	}

}
