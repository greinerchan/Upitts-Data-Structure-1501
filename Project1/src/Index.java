import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Index {
    public DLB<String> buildIndex(String fileName) {
        DLB<String> tries = new DLB<String>();
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(fileName));
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                if (!checkString(word)) {
                    continue;
                }
                tries.insert(word);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        return tries;
    }
    /**
     * find the input word is valid.
     * @param word word
     * @return valid
     */
    private boolean checkString(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        return true;
    }
}
