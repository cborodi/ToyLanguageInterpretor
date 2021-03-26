package model.Value;

import model.Type.StringType;
import model.Type.Type;

public class StringValue implements Value {
    String val;

    public StringValue(String v) {
        this.val = v;
    }

    public String getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof StringValue) {
            return ((StringValue) another).getVal().equals(this.getVal());
        }
        return false;
    }

    @Override
    public String toString() {
        return "String " + val;
    }

}
