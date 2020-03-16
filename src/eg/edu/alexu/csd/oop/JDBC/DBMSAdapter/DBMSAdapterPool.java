package eg.edu.alexu.csd.oop.JDBC.DBMSAdapter;

import eg.edu.alexu.csd.oop.JDBC.DBMSAdapter.DBMSAdaptar;

public abstract class DBMSAdapterPool {

    private static DBMSAdaptar adabter = null;

    public static DBMSAdaptar getinstance(){
        if (adabter == null){
            adabter = new DBMSAdaptar();
            return adabter;
        }
        return adabter;
    }

    public static void remove(){
        adabter = null;
    }

}
