package tree;

import data.Dataset;
import information.Information;
import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Node {
    private String attribute;
    private Integer parentAttributeValue;
    private ArrayList<Node> children;

    private boolean isLeaf;

    private int classLabel;
    private Dataset dataset;

    public Node(Dataset dataset, Integer parentAttributeValue) {
        this.dataset = dataset;
        this.parentAttributeValue = parentAttributeValue;
        children = new ArrayList<>();
        if (Information.getMaxGainRatio(dataset) > 0.0000001) {
            isLeaf = false;
            this.attribute = Information.getAttributeWithMaxGainRatio(dataset);
            var datasets = dataset.splitDatasetByAttributeValues(attribute);
            datasets.forEach(d -> {
                children.add(new Node(d, d.getObjects().get(0).getAttributeValue(attribute)));
            });
        } else {
            isLeaf = true;
            classLabel = dataset.getCommonClass();
        }
    }

    public static void printTree(Node treeRoot) {
        System.out.println(treeRoot.attribute);
        for (Node child : treeRoot.children) {
            Node.print(child, 1);
        }

    }

    private static void print(Node node, int tabCounter) {
        for (int i = 0; i < tabCounter - 1; i++) {
            System.out.print("|\t");
        }
        System.out.print("|---" + node.parentAttributeValue + ":\n");

        if (node.isLeaf) {
            for (int i = 0; i < tabCounter; i++) {
                System.out.print("|\t");
            }
            System.out.print("Result: " + node.classLabel + "\n");
        } else {
            for (int i = 0; i < tabCounter; i++) {
                System.out.print("|\t");
            }
            System.out.println(node.attribute);
            for (Node child: node.children) {
                Node.print(child, tabCounter + 1);
            }
        }
    }
}