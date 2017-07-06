package demons.communicationClass;

/**
 * Created by demons on 2017/6/26.
 */
public class IntegerWrapper {

    private Integer number;

    protected IntegerWrapper() {
    }

    public IntegerWrapper(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
