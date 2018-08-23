package pathway.eclipse.handlers;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.swt.widgets.Display;
import pathway.eclipse.UIUtils;




/** Called when the user chooses to open the experiment browser. */
public class OpenBrowserHandler implements IHandler {
    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        Display.getDefault().syncExec(new Runnable() {
            @Override
            public void run() {
                UIUtils.openBrowsingPerspective();
            }
        });

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
