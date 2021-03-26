package model.Type;

import model.Value.StringValue;
import model.Value.Value;

public class StringType implements Type {

    @Override
    public boolean equals(Object another) {
        return another instanceof StringType;
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }

    @Override
    public String toString() {
        return "String";
    }
}
