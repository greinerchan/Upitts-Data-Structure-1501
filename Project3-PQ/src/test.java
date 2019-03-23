public class test {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        BuildPQ a = new BuildPQ();
        PQManager cars = a.buildIndex("cars.txt");
        System.out.println("Lowest Price: \n" + cars.getMinPrice());
        System.out.println("Lowest Mileage: \n" + cars.getMinMileage());
        System.out.println("Lowest Price for Honda Civic: \n" + cars.getMinPriceMM("Honda", "Civic"));
        System.out.println("Lowest Mileage for Ford Fiesta : \n" + cars.getMinMileageMM("Ford", "Fiesta"));
        cars.remove("AAAAAAAAAAAAAAAA1");
        System.out.println("Lowest Price: \n" + cars.getMinPrice());
        System.out.println("Lowest Mileage: \n" + cars.getMinMileage());
        System.out.println("Lowest Price for Honda Civic: \n" + cars.getMinPriceMM("Honda", "Civic"));
        System.out.println("Lowest Mileage for Ford Fiesta : \n" + cars.getMinMileageMM("Ford", "Fiesta"));
    }

}
