package eg.edu.alexu.csd.oop.DBMS.conditionFilter;

public class Condition {

    private String firstOperand;
    private String operation;
    private String secondOperand;

    public Condition(String firstOperand,String operation,String secondOperand){
        this.firstOperand = firstOperand;
        this.operation = operation;
        this.secondOperand = secondOperand;
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand;
    }
}
