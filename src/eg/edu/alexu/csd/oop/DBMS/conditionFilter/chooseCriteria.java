package eg.edu.alexu.csd.oop.DBMS.conditionFilter;

import java.sql.SQLException;

public class chooseCriteria {
    private EqualCriteria equal = new EqualCriteria();
    private LessThanCriteria lessThan = new LessThanCriteria();
    private GreaterThanCriteria greaterThan = new GreaterThanCriteria();
    private Condition condition;

    public chooseCriteria (Condition condition){
        this.condition = condition;
    }
    public boolean returnCriteria() throws SQLException {
        if (condition.getOperation().equals("="))
            return equal.meetCriteria(condition);
        else if (condition.getOperation().equals("<"))
            return lessThan.meetCriteria(condition);
        else if (condition.getOperation().equals(">"))
            return greaterThan.meetCriteria(condition);

        return false;
    }
}
