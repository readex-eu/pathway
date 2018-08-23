package pathway.eclipse.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import pathway.PAThWayPlugin;
import pathway.ui.dialogs.tools.EditToolsDialog;




public class EditToolsHandler implements IHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        EditToolsDialog dialog = new EditToolsDialog(PAThWayPlugin.getShell());
        dialog.create();
        dialog.open();

        return null;
    }


    @Override
    public void dispose() {
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean isHandled() {
        return true;
    }


    @Override
    public void addHandlerListener(IHandlerListener handlerListener) {
    }


    @Override
    public void removeHandlerListener(IHandlerListener handlerListener) {
    }
}
