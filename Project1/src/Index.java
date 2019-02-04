import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

    public void writeFile(DLB<String> historyTries, String content) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            File file = new File("user_history.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);
            bw.write(content + "\n");
            historyTries.insert(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fw != null) {
                    try {
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
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
