package titov;

import org.apache.catalina.LifecycleException;
import titov.utils.DataBaseConnection;
import titov.utils.TomcatRun;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws LifecycleException, FileNotFoundException {
        TomcatRun.start();
        DataBaseConnection.createTables();
    }

}