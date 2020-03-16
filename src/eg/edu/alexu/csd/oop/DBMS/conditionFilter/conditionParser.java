package eg.edu.alexu.csd.oop.DBMS.conditionFilter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class conditionParser {
    String EQUAL_REGEX = "([A-Za-z_][A-Za-z0-9_]*) *= *(('[^']+')|(\"[^\"]+\")|(\\d+))";
    String GREATERTHAN_REGEX = "([A-Za-z_][A-Za-z0-9_]*) *> *(('[^']+')|(\"[^\"]+\")|(\\d+))";
    String LESSTHAN_REGEX = "([A-Za-z_][A-Za-z0-9_]*) *< *(('[^']+')|(\"[^\"]+\")|(\\d+))";
    String[] ConditionRegex = {EQUAL_REGEX,GREATERTHAN_REGEX,LESSTHAN_REGEX};

    public Condition parseCondition(String conditionS){
        Condition conditionC = null;
        for(int i=0;i<ConditionRegex.length;i++){
            Pattern pattern = Pattern.compile(ConditionRegex[i], Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(conditionS);
            if(matcher.matches()){
                String[] splittedC = conditionS.split("=|<|>");
                if (conditionS.contains("="))
                    return new Condition(splittedC[0],"=",splittedC[1]);
                else if (conditionS.contains(">"))
                    return new Condition(splittedC[0],">",splittedC[1]);
                else if (conditionS.contains("<"))
                    return new Condition(splittedC[0],"<",splittedC[1]);
            }
        }
        return conditionC;
    }

}
