package csv;

import data.DataObject;
import data.Dataset;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CSVReader {
    private static final String CSVDelimiter = "([;,])(?=\\S)";

    public static Dataset extractData(String fileName, int attributeAmount, long randomSeed) throws FileNotFoundException {

        ArrayList<DataObject> objects = new ArrayList<>();
//        Set<Integer> classes = new HashSet<>();

        Scanner scanner = new Scanner(new File(fileName));
        String[] headers = scanner.nextLine().split(CSVDelimiter);
        headers = Arrays.copyOfRange(headers, 1, headers.length);

        int classIndex = headers.length - 1;
        List<Integer> attributes = new ArrayList<>();
        for (int i = 0; i < headers.length - 1; i++) {
            attributes.add(i);
        }
        Collections.shuffle(attributes, new Random(randomSeed));
        Set<Integer> attributesToHandle = new HashSet<>(attributes.subList(0, attributeAmount));



        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineData = line.split(CSVDelimiter);
            lineData = Arrays.copyOfRange(lineData, 1, lineData.length);

            //Add class
            int objectClass = Integer.parseInt(lineData[classIndex]) > 4 ? 1 : 0;
//            classes.add(objectClass);

            lineData = Arrays.copyOfRange(lineData, 0, lineData.length - 1);
            DataObject object = new DataObject(objectClass);
            for (int i = 0; i < lineData.length; i++) {
                if(attributesToHandle.contains(i))
                    object.addValue(headers[i], Integer.parseInt(lineData[i]));
            }
            objects.add(object);
        }

        return new Dataset(objects);
    }
}
