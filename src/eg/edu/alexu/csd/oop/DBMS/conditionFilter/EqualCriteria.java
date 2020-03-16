package eg.edu.alexu.csd.oop.DBMS.conditionFilter;

public class EqualCriteria implements Criteria {

    @Override
    public boolean meetCriteria(Condition conditions) {
        if (conditions.getSecondOperand().equals("")||conditions.getFirstOperand().equals(""))
            return false;
        System.out.println(conditions.getFirstOperand() .equals(conditions.getSecondOperand()));
        return conditions.getFirstOperand() .equals(conditions.getSecondOperand()) ;
    }
}
