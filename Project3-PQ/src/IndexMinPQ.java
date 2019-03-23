import java.util.HashMap;
import java.util.NoSuchElementException;

public class IndexMinPQ {
    private int n;
    private Car[] PricePQ;
    private Car[] MileagePQ;
    private HashMap<String, Integer> indexPricePQ;
    private HashMap<String, Integer> indexMileagePQ;
    /**
     * Initializes an empty indexed priority queue with indices between {@code 0}
     * and {@code maxN - 1}.
     *
     * @param  maxN the keys on this priority queue are index from {@code 0} to {@code maxN - 1}
     * @throws IllegalArgumentException if {@code maxN < 0}
     */
    public IndexMinPQ(int maxN) {
        if (maxN < 0) throw new IllegalArgumentException();
        n = 0;
        indexPricePQ = new HashMap<String, Integer>(maxN + 1);    // make this of length maxN??
        indexMileagePQ = new HashMap<String, Integer>(maxN + 1);
        PricePQ   = new Car[maxN + 1];
        MileagePQ   = new Car[maxN + 1];                   // make this of length maxN??
    }
    /**
     * Initializes an empty priority queue.
     */
    public IndexMinPQ() {
        this(16);
    }
    private void resize(int capacity) {
        assert capacity > n;
        Car[] temp = new Car[capacity];
        Car[] temp2 = new Car[capacity];
        System.arraycopy(PricePQ, 1, temp, 1, n);
        System.arraycopy(MileagePQ, 1, temp2, 1, n);
        PricePQ = temp;
        MileagePQ = temp2;
    }

    /**
     * Returns true if this priority queue is empty.
     *
     * @return {@code true} if this priority queue is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Is {@code i} an index on this priority queue?
     *
     * @param  i an index
     * @return {@code true} if {@code i} is an index on this priority queue;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
     */
    public boolean contains(String VIN) {
        return indexPricePQ.containsKey(VIN);
    }
    /**
     * Returns the number of keys on this priority queue.
     *
     * @return the number of keys on this priority queue
     */
    public int size() {
        return n;
    }
    /**
     * Associate key with index i.
     *
     * @param  i an index
     * @param  key the key to associate with index {@code i}
     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
     * @throws IllegalArgumentException if there already is an item
     *         associated with index {@code i}
     */
    public void insert(Car car) {
        if (contains(car.getVIN())) throw new IllegalArgumentException("index is already in the priority queue");
        if (n == PricePQ.length - 1) resize(2 * PricePQ.length);
        n++;
        PricePQ[n] = car;
        MileagePQ[n] = car;
        indexPricePQ.put(car.getVIN(), n);
        indexMileagePQ.put(car.getVIN(), n);
        swimPrice(n);
        swimMileage(n);
    }
    private void swimMileage(int k) {
        while (k > 1 && largeMileage(k/2, k)) {
            exchMileagePQ(k, k/2);
            k = k/2;
        }
    }
    private boolean largeMileage(int i, int k) {
        return Double.compare(MileagePQ[i].getMileage(), MileagePQ[k].getMileage()) > 0;
    }

    private void swimPrice(int k) {
        while (k > 1 && largePrice(k/2, k)) {
            exchPricePQ(k, k/2);
            k = k/2;
        }
    }
    private boolean largePrice(int i, int k) {
        return PricePQ[i].compareTo(PricePQ[k]) > 0;
    }

    /**
     * Returns an index associated with a maximum key.
     *
     * @return an index associated with a maximum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Car minPrice() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return PricePQ[1];
    }
    /**
     * Returns an index associated with a maximum key.
     *
     * @return an index associated with a maximum key
     * @throws NoSuchElementException if this priority queue is empty
     */
    public Car minMileage() {
        if (n == 0) throw new NoSuchElementException("Priority queue underflow");
        return MileagePQ[1];
    }
    /**
     * Change the key associated with index {@code i} to the specified value.
     *
     * @param  i the index of the key to change
     * @param  key change the key associated with index {@code i} to this key
     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
     */
    public void changePrice(String VIN, double price) {
        if (!contains(VIN)) throw new NoSuchElementException("index is not in the priority queue");
        int i = indexPricePQ.get(VIN);
        PricePQ[i].setPrice(price);
        swimPrice(i);
        sinkPrice(i);
    }
    private void sinkPrice(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && largePrice(j, j+1)) j++;
            if (!largePrice(k, j)) break;
            exchPricePQ(k, j);
            k = j;
        }
    }

    /**
     * Change the key associated with index {@code i} to the specified value.
     *
     * @param  i the index of the key to change
     * @param  key change the key associated with index {@code i} to this key
     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
     */
    public void changeMileage(String VIN, double mileage) {
        if (!contains(VIN)) throw new NoSuchElementException("index is not in the priority queue");
        int i = indexMileagePQ.get(VIN);
        MileagePQ[i].setMileage(mileage);
        swimMileage(i);
        sinkMileage(i);
    }
    private void sinkMileage(int k) {
        while (2*k <= n) {
            int j = 2*k;
            if (j < n && largeMileage(j, j+1)) j++;
            if (!largeMileage(k, j)) break;
            exchMileagePQ(k, j);
            k = j;
        }
    }
    /**
     * Change the key associated with index {@code i} to the specified value.
     *
     * @param  i the index of the key to change
     * @param  key change the key associated with index {@code i} to this key
     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
     */
    public void changeColor(String VIN, String color) {
        if (!contains(VIN)) throw new NoSuchElementException("index is not in the priority queue");
        int i = indexMileagePQ.get(VIN);
        MileagePQ[i].setColor(color);;
    }
    /**
     * Remove the key on the priority queue associated with index {@code i}.
     *
     * @param  i the index of the key to remove
     * @throws IllegalArgumentException unless {@code 0 <= i < maxN}
     * @throws NoSuchElementException no key is associated with index {@code i}
     */
    public void delete(String VIN) {
        if (!contains(VIN)) throw new NoSuchElementException("index is not in the priority queue");
        int tmp = n;
        int priceIndex = indexPricePQ.get(VIN);
        exchPricePQ(priceIndex, n--);
        swimPrice(priceIndex);
        sinkPrice(priceIndex);
        PricePQ[tmp] = null;
        indexPricePQ.remove(VIN);

        int mileageIndex = indexMileagePQ.get(VIN);
        exchMileagePQ(mileageIndex, tmp);
        swimMileage(mileageIndex);
        sinkMileage(mileageIndex);
        MileagePQ[tmp] = null;
        indexMileagePQ.remove(VIN);


        if ((n > 0) && (n == (PricePQ.length - 1) / 4)) resize(PricePQ.length / 2);
    }

    private void exchMileagePQ(int i, int k) {
        Car tmp = MileagePQ[i];
        MileagePQ[i] = MileagePQ[k];
        MileagePQ[k] = tmp;
        indexMileagePQ.put(MileagePQ[i].getVIN(), i);
        indexMileagePQ.put(MileagePQ[k].getVIN(), k);
    }

    private void exchPricePQ(int i, int k) {
        Car tmp = PricePQ[i];
        PricePQ[i] = PricePQ[k];
        PricePQ[k] = tmp;
        indexPricePQ.put(PricePQ[i].getVIN(), i);
        indexPricePQ.put(PricePQ[k].getVIN(), k);
    }
}
