package model.Type;

import model.Value.Value;

public interface Type {
    boolean equals(Object another);
    Value defaultValue();
}
