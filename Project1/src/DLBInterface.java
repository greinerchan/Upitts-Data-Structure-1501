
public interface DLBInterface<T> {
    /**
     * Given the value (object), tries to find it.
     * @param toSearch Object value to search
     * @return The value (object) of the search result. If not found, null.
     */
    T searchPrefix(T toSearch);

    /**
     * Inserts a value (object) to the tries.
     * @param toInsert a value (object) to insert into the tries.
     */
    void insert(T toInsert);
}
