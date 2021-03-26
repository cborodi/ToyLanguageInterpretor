package model.Value;

import model.Type.IntType;
import model.Type.Type;

public class IntValue implements Value {
    int val;

    public IntValue(int v) {
        this.val = v;
    }

    public int getVal() {
        return val;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof IntValue) {
            return ((IntValue) another).getVal() == this.getVal();
        }
        return false;
    }

    @Override
    public String toString() {
        return "int " + val;
    }

}
