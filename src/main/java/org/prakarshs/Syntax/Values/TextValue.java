package org.prakarshs.Syntax.Values;

public class TextValue extends ComparableValue<String> {
    public TextValue(String value) {
        super(value);
    }

    public Value<?> getValue(int index) {
        if (getValue().length() > index)
            return new TextValue(getValue().substring(index, index + 1));
        return NullValue.NULL_INSTANCE;
    }

    public void setValue(int index, Value<?> value) {
        if (getValue().length() > index) {
            String val = getValue();
            super.setValue(val.substring(0, index) + value.toString() + val.substring(index));
        }
    }
}
