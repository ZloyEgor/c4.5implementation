package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class DataObject {
    private int dataClass;
    private Map<String, Integer> values;


    public DataObject(int dataClass) {
        this.dataClass = dataClass;
        values = new HashMap<>();
    }

    public void addValue(String attributeName, Integer value) {
        values.put(attributeName, value);
    }

    public Integer getAttributeValue(String attributeName) {
        return values.get(attributeName);
    }
}
