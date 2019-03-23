import java.util.Scanner;

public class CarTracker {
    private static Scanner scanner = new Scanner(System.in);
    private static PQManager carsPQ;
    public static void main(String[] args) {
        BuildPQ build = new BuildPQ();
        String inputFile = "cars.txt";
        carsPQ = build.buildIndex(inputFile);
        String choose = null;
        do {
            StringBuilder sb = new StringBuilder();
            sb.append("     Menu \n").append("1. Add a car\n").append("2. Update a car \n")
                .append("3. Remove a specific car from consideration \n")
                .append("4. Retrieve the lowest price car \n").append("5. Retrieve the lowest mileage car \n")
                .append("6. Retrieve the lowest price car by make and model \n")
                .append("7. Retrieve the lowest mileage car by make and model \n")
                .append("0. Quit");
            System.out.println(sb.toString());
            choose = scanner.nextLine();
            switch (choose) {
            case "1":
                addCar();
                break;
            case "2":
                updateCarEnter();
                break;
            case "3":
                removeCar();
                break;
            case "4":
                getLowestPrice();
                break;
            case "5":
                getLowestMileage();
                break;
            case "6":
                getLowestPriceMM();
                break;
            case "7":
                getLowestMileageMM();
                break;
            case "0":
                System.out.println("Program Exit!");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid Choice");
                break;
            }
        } while (!choose.equals("0"));
    }

    private static void getLowestMileageMM() {
        String enter = "";
        String make = "";
        String model = "";
        do {
            System.out.println("Please Enter The Make or Enter 0 Back to Menu");
            make = scanner.nextLine();
            if (make.equals("0")) {
                return;
            }
            System.out.println("Please Enter The Model or Enter 0 Back to Menu");
            model = scanner.nextLine();
            if (model.equals("0")) {
                return;
            }
        } while (carsPQ.getMinMileageMM(make, model) == null);
        System.out.println(carsPQ.getMinMileageMM(make, model));
        while (!enter.equals("0")) {
            System.out.println("Enter 0 Back to Menu");
            enter = scanner.nextLine();
        }
    }

    private static void getLowestPriceMM() {
        String enter = "";
        String make = "";
        String model = "";
        do {
            System.out.println("Please Enter The Make or Enter 0 Back to Menu");
            make = scanner.nextLine();
            if (make.equals("0")) {
                return;
            }
            System.out.println("Please Enter The Model or Enter 0 Back to Menu");
            model = scanner.nextLine();
            if (model.equals("0")) {
                return;
            }
        } while (carsPQ.getMinPriceMM(make, model) == null);
        System.out.println(carsPQ.getMinPriceMM(make, model));
        while (!enter.equals("0")) {
            System.out.println("Enter 0 Back to Menu");
            enter = scanner.nextLine();
        }
    }

    private static void getLowestMileage() {
        String enter = "";
        Car car = carsPQ.getMinMileage();
        System.out.println(car.toString());
        while (!enter.equals("0")) {
            System.out.println("Enter 0 Back to Menu");
            enter = scanner.nextLine();
        }
    }

    private static void getLowestPrice() {
        String enter = "";
        Car car = carsPQ.getMinPrice();
        System.out.println(car.toString());
        while (!enter.equals("0")) {
            System.out.println("Enter 0 Back to Menu");
            enter = scanner.nextLine();
        }
    }

    private static void removeCar() {
        String VIN, ans;
        System.out.println("Please Enter The VIN or Enter 0 Back to Menu");
        VIN = scanner.nextLine();
        while (true) {
            if (VIN.equals("0")) {
                return;
            }
            if (!checkValid(VIN)) {
                System.out.println("The VIN Entered Not Valid, Please Enter Again,"
                        + "or Enter 0 Back to Menu");
                VIN = scanner.nextLine();
                continue;
            }
            if (carsPQ.findCar(VIN) == null) {
                System.out.println("Car Not Existed, Please Enter Again, "
                        + "or Enter 0 Back to Menu");
                VIN = scanner.nextLine();
                continue;
            }
            break;
        }
        System.out.println("Are You Sure to Delete this Car? Y/N");
        ans = scanner.nextLine();
        ans = ans.toUpperCase();
        if (ans.equals("Y") || ans.equals("YES")) {
            carsPQ.remove(VIN);
            System.out.println("Delete Successful");
        } else {
            System.out.println("Delete Aborted");
            return;
        }
    }
    private static void updateCarEnter() {
        String VIN = null;
        System.out.println("Please Enter The VIN or Enter 0 Back to Menu");
        VIN = scanner.nextLine();
        while (true) {
            if (VIN.equals("0")) {
                return;
            }
            if (!checkValid(VIN)) {
                System.out.println("The VIN Entered Not Valid, Please Enter Again, "
                        + "or Enter 0 Back to Menu");
                VIN = scanner.nextLine();
                continue;
            }
            if (carsPQ.findCar(VIN) == null) {
                System.out.println("Car Not Existed, Please Enter Again, "
                        + "or Enter 0 Back to Menu");
                VIN = scanner.nextLine();
                continue;
            }
            break;
        }
        updateCar(VIN);
        return;
    }
    private static void updateCar(String VIN) {
        String choose = null;
        do {
            StringBuilder sb = new StringBuilder();
            sb.append("  Update Menu \n").append("1. Update the price of the car\n")
                .append("2. Update the mileage of the car \n")
                .append("3. Update the color of the car\n")
                .append("0. Back to Menu");
            System.out.println(sb.toString());
            choose = scanner.nextLine();
            switch (choose) {
            case "1":
                updatePrice(VIN);
                break;
            case "2":
                updateMileage(VIN);
                break;
            case "3":
                updateColor(VIN);
                break;
            case "0":
                return;
            default:
                System.out.println("Invalid Choice");
                break;
            }
        } while (!choose.equals("0"));
    }

    private static void updateColor(String VIN) {
        String color, ans;
        Car car = carsPQ.findCar(VIN);
        System.out.println("Please Enter The Color to Change");
        color = scanner.nextLine();
        System.out.println("Are You Sure to Change Color? Y/N");
        ans = scanner.nextLine();
        ans = ans.toUpperCase();
        if (ans.equals("Y") || ans.equals("YES")) {
            carsPQ.updateColor(VIN, color);;
            System.out.println("Update Successful");
        } else {
            System.out.println("Update Aborted");
            return;
        }
    }

    private static void updateMileage(String VIN) {
        double mileage = 0;
        String mile, ans;
        Car car = carsPQ.findCar(VIN);
        System.out.println("Please Enter The Mileage to Change");
        mile = scanner.nextLine();
        while (!isDouble(mile)) {
            mile = scanner.nextLine();
        }
        mileage = Double.parseDouble(mile);
        System.out.println("Are You Sure to Change Mileage? Y/N");
        ans = scanner.nextLine();
        ans = ans.toUpperCase();
        if (ans.equals("Y") || ans.equals("YES")) {
            carsPQ.updateMileage(VIN, mileage);;
            System.out.println("Update Successful");
        } else {
            System.out.println("Update Aborted");
            return;
        }
    }

    private static void updatePrice(String VIN) {
        double price = 0;
        String money, ans;
        Car car = carsPQ.findCar(VIN);
        System.out.println("Please Enter The Price to Change");
        money = scanner.nextLine();
        while (!isDouble(money)) {
            money = scanner.nextLine();
        }
        price = Double.parseDouble(money);
        System.out.println("Are You Sure to Change Price? Y/N");
        ans = scanner.nextLine();
        ans = ans.toUpperCase();
        if (ans.equals("Y")|| ans.equals("YES")) {
            carsPQ.updatePrice(VIN, price);
            System.out.println("Update Successful");
        } else {
            System.out.println("Update Aborted");
            return;
        }
    }

    private static void addCar() {
        String VIN;
        String make;
        String model;
        double price = 0;
        double mileage = 0;
        String color;
        String choice;
        System.out.println("Please Enter The VIN or Enter 0 Back to Menu");
        VIN = scanner.nextLine();
        while (true) {
            if (VIN.equals("0")) {
                return;
            }
            if (!checkValid(VIN)) {
                System.out.println("The VIN Entered Not Valid, Please Enter Again, "
                        + "or Enter 0 Back to Menu");
                VIN = scanner.nextLine();
                continue;
            }
            if (carsPQ.findCar(VIN) != null) {
                System.out.println("Car Already Existed, Please Enter Again,"
                        + "or Enter 0 Back to Menu");
                VIN = scanner.nextLine();
                continue;
            }
            break;
        }
        System.out.println("Please Enter Make");
        make = scanner.nextLine();
        System.out.println("Please Enter Model");
        model = scanner.nextLine();
        System.out.println("Please Enter Price");
        String money;
        money = scanner.nextLine();
        while (!isDouble(money)) {
            money = scanner.nextLine();
        }
        price = Double.parseDouble(money);
        System.out.println("Please Enter Mileage");
        String miles;
        miles = scanner.nextLine();
        while (!isDouble(miles)) {
            miles = scanner.nextLine();
        }
        mileage = Double.parseDouble(miles);
        System.out.println("Please Enter Color");
        color = scanner.nextLine();
        Car car = new Car(VIN, make, model, price, mileage, color);
        System.out.println("Are You Sure to Add this Car? Y/N ");
        String chooseAdd = null;
        chooseAdd = scanner.nextLine();
        chooseAdd = chooseAdd.toUpperCase();
        if (chooseAdd.equals("Y") || chooseAdd.equals("YES")) {
            carsPQ.add(car);
            System.out.println("The Car Has Been Added!");
        } else {
            System.out.println("Add Car Aborted");
        }
        System.out.println("Want Add Another Car? Y/N ");
        choice = scanner.nextLine();
        choice = choice.toUpperCase();
        if (choice.toUpperCase().equals("Y") || choice.equals("YES")) {
            addCar();
        } else {
            System.out.println("Back To Menu!");
        }
    }
    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        }
        catch(NumberFormatException ex){
            System.out.println("Please Enter Valid Number: ");
        }
        return false;
    }
    private static boolean checkValid(String s) {
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
}
