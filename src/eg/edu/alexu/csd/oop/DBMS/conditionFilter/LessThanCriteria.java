package eg.edu.alexu.csd.oop.DBMS.conditionFilter;

public class LessThanCriteria implements Criteria {

    @Override
    public boolean meetCriteria(Condition conditions) {
        if (conditions.getSecondOperand().equals("")||conditions.getFirstOperand().equals(""))
            return false;
        return Integer.parseInt(conditions.getFirstOperand())<Integer.parseInt(conditions.getSecondOperand());
    }
}
