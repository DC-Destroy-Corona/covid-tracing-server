package covid.tracing.common.datatype;

public class MutableInt {
    int value;

    public void increment () {
        ++value;
    }

    public int get () {
        return value;
    }

    public MutableInt() {
        this.value = 1;
    }
}

