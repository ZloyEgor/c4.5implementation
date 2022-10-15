package test;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.text.DecimalFormat;

@Builder
public class TestInformation {

    private static final DecimalFormat df = new DecimalFormat("0.000");
    private int truePositive;
    private int trueNegative;
    private int falsePositive;
    private int falseNegative;
    private int keyClass;

    public double getAccuracy() {
        return (double) (truePositive + trueNegative) / (truePositive + trueNegative + falsePositive + falseNegative);
    }

    public double getPrecision() {
        return (double) truePositive / (truePositive + falsePositive);
    }

    public double getRecall() {
        return (double) truePositive / (truePositive + falseNegative);
    }

    public double getTruePositiveRate() {
        return getRecall();
    }

    public double getFalsePositiveRate() {
        return (double) falsePositive / (falsePositive + trueNegative);
    }

    @Override
    public String toString() {
        return  "Key class:\t\t" + keyClass + "\n" +
                "Precision:\t" + df.format(getPrecision()) + "\n" +
                "Recall:\t\t" + df.format(getRecall()) + "\n" +
                "Accuracy:\t" + df.format(getAccuracy());
    }
}
