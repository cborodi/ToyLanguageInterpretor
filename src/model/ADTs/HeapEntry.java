package model.ADTs;

import javafx.beans.property.SimpleStringProperty;
import model.Value.Value;

public class HeapEntry {
    private SimpleStringProperty address;
    private SimpleStringProperty value;
    private Integer originalAddress;
    private Value originalValue;

    public HeapEntry(Integer address, Value value) {
        this.address = new SimpleStringProperty(String.valueOf(address));
        this.value = new SimpleStringProperty(value.toString());
        this.originalAddress = address;
        this.originalValue = value;
    }

    public Integer getAddress() { return this.originalAddress; }
    public Value getValue() { return this.originalValue; }
}
