import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class DictionaryDriver {

    private static Queue<String> getPredictions(DLB<String> dictionaryTries, DLB<String> historyTries, String str) {
        Queue<String> predictions = new ArrayDeque<String>();
        if (str == null && str.length() == 0) {
            return null;
        }
        if (dictionaryTries.keysWithPrefix(str) == null && historyTries.keysWithPrefix(str) == null) {
            return null;
        }
        if (historyTries.keysWithPrefix(str) == null) {
            predictions = (Queue<String>) dictionaryTries.keysWithPrefix(str);
            return predictions;
        }
        Queue<String> dictPredictions = (Queue<String>) dictionaryTries.keysWithPrefix(str);
        Queue<String> userPredictions = (Queue<String>) historyTries.keysWithPrefix(str);
        int dictSize = 0;
        int userSize = 0;
        if (dictPredictions == null) {
            dictSize = 0;
        } else {
            dictSize = dictPredictions.size();
        }
        if (userPredictions == null) {
            userSize = 0;
        } else {
            userSize = userPredictions.size();
        }
        if (userPredictions != null && userSize >= 5) {
            while (predictions.size() < 5) {
                predictions.offer(userPredictions.poll());
            }

            return predictions;
        }
        if (userPredictions != null && userSize < 5) {
            if (dictPredictions == null) {
                while (!userPredictions.isEmpty()) {
                    predictions.offer(userPredictions.poll());
                }
                return predictions;
            } else {
                while (predictions.size() < 5 && !dictPredictions.isEmpty()) {
                    while (!userPredictions.isEmpty()) {
                        predictions.offer(userPredictions.poll());
                    }
                    if (predictions.contains(dictPredictions.peek())) {
                        dictPredictions.poll();
                    } else {
                        predictions.offer(dictPredictions.poll());
                    }
                }
            }
        }
        return predictions;
    }
    public static void main(String[] args) throws IOException {
                Index index = new Index();
                String inputFile = "dictionary.txt";
                String historyFile = "user_history.txt";
                DLB<String> dictionaryTries = index.buildIndex(inputFile);
                File history = new File("user_history.txt");
                history.createNewFile();
                Scanner scanner = new Scanner(System.in);
                DLB<String> historyTries = index.buildIndex(historyFile);
                StringBuilder word = new StringBuilder();
                String choose;
                Queue<String> predictions = new ArrayDeque<String>();
                String[] predicts = new String[6];
                //Queue<String> predictionss = getPredictions(dictionaryTries, historyTries, "aahr");
                //Queue<String> predictionss = (Queue<String>) dictionaryTries.keysWithPrefix("aar");

                boolean firstTime = true;
                boolean nextTime = false;
                boolean quit = false;
                boolean enter = false;
                boolean complete = false;

                while (true) {
                    if (firstTime) {
                        System.out.print("\nEnter your first character: ");
                        firstTime = false;
                        word.delete(0, word.length());
                    }
                    if (nextTime) {
                        System.out.print("\nEnter first character of the next word: ");
                        nextTime = false;
                        word.delete(0, word.length());
                    }
                    if (enter) {
                        System.out.print("\nEnter the next character: ");
                    }

                    char userInput = scanner.next().charAt(0);

                    switch(userInput) {

                    case '1':
                        choose = predicts[0];
                        System.out.println("\nWORD COMPLETED:   " + choose);
                        index.writeFile(historyTries, choose);
                        nextTime = true;
                        break;

                    case '2':
                        choose = predicts[1];
                        System.out.println("\nWORD COMPLETED:   " + choose);
                        index.writeFile(historyTries, choose);
                        nextTime = true;
                        break;

                    case '3':
                        choose = predicts[2];
                        System.out.println("\nWORD COMPLETED:   " + choose);
                        index.writeFile(historyTries, choose);
                        nextTime = true;
                        break;

                    case '4':
                        choose = predicts[3];
                        System.out.println("\nWORD COMPLETED:   " + choose);
                        index.writeFile(historyTries, choose);
                        nextTime = true;
                        break;

                    case '5':
                        choose = predicts[4];
                        System.out.println("\nWORD COMPLETED:   " + choose);
                        index.writeFile(historyTries, choose);
                        nextTime = true;
                        break;

                    case '$':
                        enter = false;
                        System.out.println("\nWORD COMPLETED:   " + word.toString());
                        index.writeFile(historyTries, word.toString());
                        nextTime = true;
                        break;

                    case '!':
                        System.out.println("\nBye!");
                        System.exit(0);
                        break;

                    default:
                        enter = true;
                        word.append(userInput);

                        predicts = new String[6];
                        if (getPredictions(dictionaryTries, historyTries, word.toString()) != null) {
                            double start = System.nanoTime();
                            predictions = getPredictions(dictionaryTries, historyTries, word.toString());
                            double end = System.nanoTime();
                            int i = 0;
                            while (!predictions.isEmpty()) {
                                predicts[i] = predictions.poll();
                                i++;
                            }
                            double last = (end - start) / 1000000000;
                            StringBuilder time = new StringBuilder();
                            DecimalFormat format = new DecimalFormat("#.######");
                            String s = format.format(last);
                            time.append("(").append(s).append(" s").append(")");
                            System.out.println(time.toString());

                            int count = 0;
                            System.out.println("Predictions:");
                            while (predicts[count] != null) {
                                StringBuilder sb = new StringBuilder();
                                sb.append("(").append(count + 1).append(") ").append(predicts[count]).append("    ");
                                System.out.print(sb.toString());
                                count++;
                            }
                            System.out.println();
                        } else {
                            System.out.println("\nThere are no predicitons for this character");
                        }
                        break;
                    }
                }

    }

}
