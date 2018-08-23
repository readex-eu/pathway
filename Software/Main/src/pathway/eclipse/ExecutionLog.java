package pathway.eclipse;

import java.io.PrintStream;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;




/** Allows Pathway to print progress information while execution a workflow. This class is thread safe. */
@NonNullByDefault
public final class ExecutionLog {
    public static void open() throws PartInitException {
        IConsole console = getConsole(CONSOLE_NAME);
        IConsoleView view = (IConsoleView)UIUtils.activateView(IConsoleConstants.ID_CONSOLE_VIEW);
        view.display(console);
    }


    public static ExecutionLog get() {
        synchronized( CONSOLE_NAME ) {
            ExecutionLog temp = instance;
            if( temp == null ) {
                temp = new ExecutionLog();
                instance = temp;
            }

            return temp;
        }
    }


    public void writeLog(String message) {
        writeStream(message, outStream);
    }


    public void writeErr(String message) {
        writeStream(message, errStream);
    }


    public void writeErr(final Exception ex) {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                ex.printStackTrace(new PrintStream(errStream));
            }
        });
    }


    private static void writeStream(final String message, final MessageConsoleStream stream) {
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {
                stream.println(message);
            }
        });
    }


    private static MessageConsole getConsole(String name) {
        // retrieve existing console, if found
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager mgr = plugin.getConsoleManager();
        for( IConsole c: mgr.getConsoles() )
            if( name.equals(c.getName()) )
                return (MessageConsole)c;

        // no console found, so create a new one
        MessageConsole console = new MessageConsole(name, null);
        mgr.addConsoles(new IConsole[] { console });
        return console;
    }


    private static MessageConsoleStream makeStream(String name, Color color) {
        MessageConsole console = getConsole(name);
        MessageConsoleStream out = console.newMessageStream();
        out.setColor(color);

        return out;
    }


    private ExecutionLog() {
    }


    private final Color normal = Display.getCurrent().getSystemColor(SWT.COLOR_BLACK);
    private final Color error = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
    private final MessageConsoleStream outStream = makeStream(CONSOLE_NAME, normal);
    private final MessageConsoleStream errStream = makeStream(CONSOLE_NAME, error);

    private static final String CONSOLE_NAME = "Workflow execution";
    private static @Nullable ExecutionLog instance;
}
