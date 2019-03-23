import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BuildPQ {
    /**
    *
    * @param fileName fileName
    * @param comparator comparator
    * @return return the words BST
    */
   public PQManager buildIndex(String fileName) {
       PQManager cars = new PQManager();
       Scanner scanner = null;
       try {
           scanner = new Scanner(new File(fileName));
           while (scanner.hasNextLine()) {
               String parameters = scanner.nextLine();
               if(parameters.startsWith("#")) {
                   continue;
               }
               String[] parameter = parameters.split(Pattern.quote(":"));
               String VIN = parameter[0];
               String make = parameter[1];
               String model = parameter[2];
               double price = Double.valueOf(parameter[3]);
               double mileage =  Double.valueOf(parameter[4]);
               String color = parameter[5];
               Car car = new Car(VIN, make, model, price, mileage,color);
               cars.add(car);
           }
       } catch (FileNotFoundException e) {
           System.err.println("Cannot find the file");
       } finally {
           if (scanner != null) {
               scanner.close();
           }
       }
       return cars;
   }
}
