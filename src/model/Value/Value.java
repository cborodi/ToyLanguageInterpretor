package model.Value;

import model.Type.Type;

public interface Value {
    Type getType();
    boolean equals(Object another);
}
