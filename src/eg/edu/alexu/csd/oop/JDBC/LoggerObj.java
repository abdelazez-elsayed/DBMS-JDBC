package eg.edu.alexu.csd.oop.JDBC;
import org.apache.log4j.*;
public class LoggerObj {
    private  static Logger logger = null;

   public static Logger getLogger() {
       if (logger == null){
           logger = LogManager.getLogger("JDBC");
           PropertyConfigurator.configure("log4j.properties");
   }

       return  logger;
   }

}
