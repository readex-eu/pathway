package pathway.utils;

import java.io.Closeable;
import java.io.IOException;




/** Contains helper functions for managing resources in the odd Java-style. o_O */
public abstract class Resources {
    public static void close(Closeable closeable) {
        try {
            if( closeable != null )
                closeable.close();
        }
        catch( IOException ex ) {
            ex.printStackTrace();
        }
    }
}
