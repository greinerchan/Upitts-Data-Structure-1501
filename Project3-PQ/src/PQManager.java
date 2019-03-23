import java.util.HashMap;
import java.util.Map;

public class PQManager {
    private IndexMinPQ impq;
    private Map<String, IndexMinPQ> makeModelMap;
    private Map<String, Car> allCar;
    public PQManager() {
        impq = new IndexMinPQ();
        allCar = new HashMap<String, Car>();
        makeModelMap = new HashMap<String, IndexMinPQ>();
    }
    public void add(Car car) {
        if (car == null) {
            throw new AssertionError("Input Not Valid");
        }
        impq.insert(car);
        String MM = car.getMake() + car.getModel();
        IndexMinPQ mmpq = makeModelMap.get(MM);
        if (mmpq == null) {
            IndexMinPQ pqmm = new IndexMinPQ();
            makeModelMap.put(MM, pqmm);
            pqmm.insert(car);
        } else {
            mmpq.insert(car);
        }
        allCar.put(car.getVIN(), car);
    }
    public void updatePrice(String VIN, double price) {
        impq.changePrice(VIN, price);
        Car car = findCar(VIN);
        IndexMinPQ pq = getMMPQ(car);
        pq.changePrice(VIN, price);
    }
    public void updateMileage(String VIN, double mileage) {
        impq.changeMileage(VIN, mileage);
        Car car = findCar(VIN);
        IndexMinPQ pq = getMMPQ(car);
        pq.changeMileage(VIN, mileage);
    }
    public void updateColor(String VIN, String color) {
        impq.changeColor(VIN, color);
        Car car = findCar(VIN);
        IndexMinPQ pq = getMMPQ(car);
        pq.changeColor(VIN, color);
    }
    public void remove(String VIN) {
        impq.delete(VIN);
        Car car = findCar(VIN);
        IndexMinPQ pq = getMMPQ(car);
        pq.delete(VIN);
        allCar.remove(VIN);
    }
    public Car getMinPrice() {
        return impq.minPrice();
    }
    public Car getMinMileage() {
        return impq.minMileage();
    }
    public Car getMinPriceMM(String maker, String model) {
        String MM = maker + model;
        IndexMinPQ pq = makeModelMap.get(MM);
        if (pq == null) {
            System.out.println("Maker/Model Not Existed\n");
            return null;
        } else {
            return pq.minPrice();
        }
    }
    public Car getMinMileageMM(String maker, String model) {
        String MM = maker + model;
        IndexMinPQ pq = makeModelMap.get(MM);
        if (pq == null) {
            System.out.println("Maker/Model Not Existed\n");
            return null;
        } else {
            return pq.minMileage();
        }
    }
    public IndexMinPQ getMMPQ(Car car) {
        String MM = car.getMake() + car.getModel();
        IndexMinPQ pq = makeModelMap.get(MM);
        if (pq == null) {
            throw new AssertionError("Car Not Existed");
        }
        return pq;
    }
    public Car findCar(String VIN) {
        if (VIN == null || VIN.length() == 0) {
            throw new AssertionError("Input Not Valid");
        }
        Car car = allCar.get(VIN);
        return car;
    }
    public int allSize() {
        return impq.size();
    }
}
