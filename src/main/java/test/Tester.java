package test;

import data.DataObject;
import data.Dataset;
import exception.UnknownClassException;
import tree.Node;

import java.util.Comparator;

public class Tester {

    public static TestInformation testTree(Node treeRoot, Dataset testData, int keyClass) {
        int TP = 0, TN = 0, FP = 0, FN = 0;
        for (DataObject object : testData.getObjects()) {
            int predictedClass = getTreeClassPrediction(treeRoot, object);

            if (predictedClass == object.getDataClass()) {
                if (predictedClass == keyClass)
                    TP++;
                else
                    TN++;
            } else {
                if (predictedClass == keyClass)
                    FP++;
                else
                    FN++;
            }
        }
        return TestInformation
                .builder()
                .keyClass(keyClass)
                .truePositive(TP)
                .trueNegative(TN)
                .falsePositive(FP)
                .falseNegative(FN)
                .build();
    }

    private static int getTreeClassPrediction(Node treeRoot, DataObject object) throws UnknownClassException{
        Node node = treeRoot;
        while (node != null && !node.isLeaf()) {
           int objectAttributeValue = object.getAttributeValue(node.getAttribute());

            node = node.getChildren()
                    .stream()
                    .filter(n -> n.getParentAttributeValue() == objectAttributeValue)
                    .findFirst()
                    .orElse(
                            node.getChildren()
                                    .stream()
                                    .min(Comparator.comparing(a -> Math.abs(objectAttributeValue - a.getParentAttributeValue())))
                                    .orElse(null)
                    );
        }
    if(node != null)
        return node.getClassLabel();
    else
        throw new UnknownClassException("Ooops");
    }
}
