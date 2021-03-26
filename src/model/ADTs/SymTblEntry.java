package model.ADTs;

import javafx.beans.property.SimpleStringProperty;
import model.Value.Value;

public class SymTblEntry {
    public SimpleStringProperty name;
    public SimpleStringProperty value;
    public String originalName;
    public Value originalValue;

    public SymTblEntry(String name, Value value) {
        this.name = new SimpleStringProperty(String.valueOf(name));
        this.value = new SimpleStringProperty(value.toString());
        this.originalName = name;
        this.originalValue = value;
    }

    public String getName() {
        return this.originalName;
    }

    public Value getValue() {
        return this.originalValue;
    }
}
