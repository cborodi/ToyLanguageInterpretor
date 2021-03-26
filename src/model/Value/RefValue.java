package model.Value;

import model.Type.RefType;
import model.Type.Type;

public class RefValue implements Value {
    int address;
    Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    public Type getLocationType() {
        return this.locationType;
    }

    public int getAddress() {
        return this.address;
    }

    @Override
    public boolean equals(Object another) {
        if (another instanceof RefValue) {
            return ((RefValue) another).address == this.getAddress();
        }
        return false;
    }

    @Override
    public String toString() {
        return "(" + this.address + ", " + this.locationType.toString() + ")";
    }
}
