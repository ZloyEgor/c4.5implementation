package data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@AllArgsConstructor
@Getter
public class Dataset {
    private List<DataObject> objects;


    public int rowAmount() {
        return objects.size();
    }

    public int getClassCount() {
        Set<Integer> classes = new HashSet<>();
        for (DataObject object : objects) {
            classes.add(object.getDataClass());
        }
        return classes.size();
    }

    public Set<String> getObjectAttributes() {
        return objects.get(0).getValues().keySet();
    }

    public List<Dataset> splitDatasetByAttributeValues(String attributeName) {
        Map<Integer, List<DataObject>> datasets = new HashMap<>();
        for (DataObject object: objects) {
            int attributeValue = object.getAttributeValue(attributeName);

            if (!datasets.containsKey(attributeValue))
                datasets.put(attributeValue, new ArrayList<>());

            var objects = datasets.get(attributeValue);
            objects.add(object);
            datasets.put(attributeValue, objects);
        }

        List<Dataset> datasetList = new ArrayList<>();
        for (int key : datasets.keySet()) {
            datasetList.add(new Dataset(datasets.get(key)));
        }
        return datasetList;
    }

    public int getCommonClass() {
        Map<Integer, Integer> classCounts = new HashMap<>();

        objects.forEach(object -> classCounts
                .merge(object.getDataClass(), 1, Integer::sum));

        var max = classCounts.entrySet().stream().max(Map.Entry.comparingByValue());

        return max.get().getKey();
    }

    public Dataset[] splitDataset(double trainToTestRatio, long randomSeed) {
        int trainObjectAmount = (int) (objects.size() * trainToTestRatio);

        Collections.shuffle(objects);

        Dataset train = new Dataset(new ArrayList<>(objects.subList(0, trainObjectAmount)));
        Dataset test = new Dataset(new ArrayList<>(objects.subList(trainObjectAmount + 1, objects.size())));

        return new Dataset[]{train, test};



    }
}
