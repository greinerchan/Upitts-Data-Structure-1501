import java.text.DecimalFormat;

public class Car implements Comparable<Car> {
    private String VIN;
    private String make;
    private String model;
    private double price;
    private double mileage;
    private String color;

    public Car() {};
    public Car (String vin, String mk, String md, double pr, double ml, String cl) {
        String upperVIN = vin.toUpperCase();
        if (checkValid(upperVIN)) {
            VIN = upperVIN;
        } else {
            throw new AssertionError("VIN Is String of Numbers and Capital Letters,"
                    + "Cannot Contain I, O, Q");
        }
        make = mk;
        model = md;
        price = pr;
        mileage = ml;
        color = cl;
    }
    /**
     * @return the vIN
     */
    public String getVIN() {
        return VIN;
    }
    /**
     * @param vIN the vIN to set
     */
    public void setVIN(String vin) {
        String upperVIN = vin.toUpperCase();
        if (checkValid(upperVIN)) {
            VIN = upperVIN;
        } else {
            throw new AssertionError("VIN Is String of Numbers and Capital Letters,"
                    + "Cannot Contain I, O, Q");
        }
    }
    /**
     * @return the make
     */
    public String getMake() {
        return make;
    }
    /**
     * @param make the make to set
     */
    public void setMake(String make) {
        this.make = make;
    }
    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }
    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * @return the mileage
     */
    public double getMileage() {
        return mileage;
    }
    /**
     * @param mileage the mileage to set
     */
    public void setMileage(double mileage) {
        this.mileage = mileage;
    }
    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }
    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }
    private boolean checkValid(String s) {
        if (!s.matches("^[A-Z0-9_.]+$")) {
            return false;
        }
        if (s.length() != 17) {
            return false;
        }
        char[] sa = s.toCharArray();
        for (char c : sa) {
            if (c == 'I' || c == 'O' || c == 'Q') {
                return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        DecimalFormat format =
                new DecimalFormat("#,###.000");
        String pp = format.format(price);
        String mg = format.format(mileage);
        StringBuilder sb = new StringBuilder();
        sb.append("VIN: " + VIN + "\n");
        sb.append("make: " + make + "\n");
        sb.append("model: " + model + "\n");
        sb.append("price: " + pp + "\n");
        sb.append("mileage: " + mg + "\n");
        sb.append("color: " + color + "\n");
        return sb.toString();
    }
    @Override
    public int compareTo(Car o) {
        return Double.compare(price, o.getPrice());
    }

}
