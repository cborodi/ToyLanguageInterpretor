package model.Value;

import model.Type.BoolType;
import model.Type.Type;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean v) {
        this.val = v;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof BoolValue) {
            return ((BoolValue) another).getVal() == this.getVal();
        }
        return false;
    }

    @Override
    public String toString() {
        return "bool " + val;
    }

}
