import csv.CSVReader;
import data.Dataset;
import org.jfree.ui.RefineryUtilities;
import plot.PRPlot;
import plot.ROCPlot;
import test.Tester;
import tree.Node;

import java.io.FileNotFoundException;

public class Main{
    public static void main(String[] args) {
        try {
            Dataset objects = CSVReader.extractData("src/main/resources/data.csv", 12, 7);

            var split = objects.splitDataset(0.8, 13);
            var train = split[0];
            var test = split[1];

            Node root = new Node(train, null);
            Node.printTree(root);
            System.out.println('\n');

            var information = Tester.testTree(root, test, 1);
            System.out.println(information);

            ROCPlot roc = new ROCPlot("AUC-ROC", information);
            roc.pack();
            RefineryUtilities.centerFrameOnScreen(roc);
            roc.setVisible(true);

            PRPlot plot = new PRPlot("Precision-Recall curve", information);
            plot.pack();
            RefineryUtilities.centerFrameOnScreen(plot);
            plot.setVisible(true);


        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

}
