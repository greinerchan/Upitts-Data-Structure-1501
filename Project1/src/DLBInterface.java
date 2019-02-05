/**
 * Project 1 - Dictionary tries.
 * @author Xi Chen
 *
 * @param <T> type
 */
public interface DLBInterface<T> {
    /**
     * Given the value (object), tries to find it.
     * @param prefix Object value to search
     * @return The value (object) of the search result. If not found, null.
     */
    Iterable<String> keysWithPrefix(String prefix);

    /**
     * Inserts a value (object) to the tries.
     * @param toInsert a value (object) to insert into the tries.
     */
    void insert(T toInsert);
}
