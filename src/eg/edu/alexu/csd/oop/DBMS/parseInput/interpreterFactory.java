package eg.edu.alexu.csd.oop.DBMS.parseInput;

import eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class interpreterFactory {

    public Interpreter getInterpreter(String query) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        query = className(query).trim();
        Class loadedClass = Class.forName("eg.edu.alexu.csd.oop.DBMS.parseInput.Interpreter." + query);
        return (Interpreter)loadedClass.getDeclaredConstructor().newInstance();
    }

    public String className(String query){
        query = query.trim();
        String[] splitted = query.split("\\s");
        String claasName = splitted[0].trim();
        splitted = removeSpaces(splitted);
        claasName = claasName.substring(0, 1).toUpperCase() + claasName.substring(1).toLowerCase();
        if (query.startsWith("c")||query.startsWith("d")||query.startsWith("C")||query.startsWith("D") && !claasName.equalsIgnoreCase("delete") ) {
            splitted[1] = splitted[1].trim().substring(0, 1).toUpperCase() + splitted[1].trim().substring(1).toLowerCase();
            if(!splitted[0].equalsIgnoreCase("delete"))
                claasName = claasName.concat(splitted[1]);
            System.out.println("CLAAAS : "+claasName);
        }
        claasName = claasName.concat("Interpreter");
        return claasName;
    }
    private String[] removeSpaces(String[] split){
        ArrayList<String> arr = new ArrayList<>();
        for (int i =0;i<split.length;i++)
            if (split[i].length()!=0)
                arr.add(split[i]);
        String[] removed = arr.toArray(new String[arr.size()]);
        return removed;
    }

}