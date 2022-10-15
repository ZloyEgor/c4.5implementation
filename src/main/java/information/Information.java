package information;

import data.Dataset;

import java.util.HashMap;
import java.util.Map;

public class Information {

    private static double getInfo(Dataset dataset) {
        Map<Integer, Integer> objectOfClassAmount = new HashMap<>();

        dataset.getObjects().forEach(object -> objectOfClassAmount
                .merge(object.getDataClass(), 1, Integer::sum));

        double info = 0;
        for (int className : objectOfClassAmount.keySet()) {
            info -= (double) objectOfClassAmount.get(className) / dataset.rowAmount()
                    * (Math.log((double) objectOfClassAmount.get(className) / dataset.rowAmount()) / Math.log(2));
        }

        return info;
    }

    public static double getGainRatio(Dataset dataset, String attribute) {
        Map<Integer, Integer> objectOfAttributeAmount = new HashMap<>();
        //Map to every attribute, contains Map of every class amount
        Map<Integer, Map<Integer, Integer>> attributeClassDistribution = new HashMap<>();

        dataset.getObjects().forEach(object -> {
            objectOfAttributeAmount.merge(object.getAttributeValue(attribute), 1, Integer::sum);


            if (!attributeClassDistribution.containsKey(object.getAttributeValue(attribute)))
                attributeClassDistribution.put(object.getAttributeValue(attribute), new HashMap<>());

            var objectsOfAttribute = attributeClassDistribution.get(object.getAttributeValue(attribute));
            objectsOfAttribute.merge(object.getDataClass(), 1, Integer::sum);
            attributeClassDistribution.put(object.getAttributeValue(attribute), objectsOfAttribute);
        });

        double infoAttribute = 0;
        double splitInfo = 0;

        for (int attributeValue : objectOfAttributeAmount.keySet()) {
            double coefficient = (double) objectOfAttributeAmount.get(attributeValue) / dataset.rowAmount();
            splitInfo -= (double) objectOfAttributeAmount.get(attributeValue) / dataset.rowAmount()
                           * (Math.log((double) objectOfAttributeAmount.get(attributeValue) / dataset.rowAmount()) / Math.log(2)) ;
            double innerPart = 0;

            var classValues = attributeClassDistribution.get(attributeValue);
            for (int classValue : classValues.keySet()) {
                innerPart -= (double) classValues.get(classValue) / objectOfAttributeAmount.get(attributeValue)
                        * (Math.log((double) classValues.get(classValue) / objectOfAttributeAmount.get(attributeValue)) / Math.log(2));
            }
            infoAttribute += coefficient * innerPart;
        }
        return (getInfo(dataset) - infoAttribute ) / splitInfo;
    }

    public static String getAttributeWithMaxGainRatio(Dataset dataset) {
        double maxGainRatio = -1;
        String maxGainRatioAttribute = null;
        for (String attributeName : dataset.getObjectAttributes()) {
            if(getGainRatio(dataset, attributeName) > maxGainRatio) {
                maxGainRatio = getGainRatio(dataset, attributeName);
                maxGainRatioAttribute = attributeName;
            }
        }
        return maxGainRatioAttribute;
    }

    public static double getMaxGainRatio(Dataset dataset) {
        double maxGainRatio = -1;
        for (String attributeName : dataset.getObjectAttributes()) {
            if(getGainRatio(dataset, attributeName) > maxGainRatio) {
                maxGainRatio = getGainRatio(dataset, attributeName);
            }
        }
        return maxGainRatio;
    }
}
