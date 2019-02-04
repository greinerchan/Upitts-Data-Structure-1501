import java.util.ArrayDeque;
import java.util.Queue;

public class DLB<T> implements DLBInterface<T> {
    private static final char end = '^';
    private Node<T> root;

    public DLB() {
        root = new Node<T>();
    }

    @Override
    public T searchPrefix(T toSearch) {
        // TODO Auto-generated method stub
        return null;
    }

    public void print() {
        System.out.println(root.child.peer.child.data);
    }
    public boolean searchWord(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        char[] chars = word.toCharArray();
        Node cur = root;
        int len = word.length();
        int count = 1;
        for (char c : chars) {
            //cur and peers no data
            if (cur.data != c && !isPeerContains(cur, c)) {
                return false;
            } else if (cur.data != c && isPeerContains(cur, c)) { // cur not have peers have
                while (cur.data != c) {
                    cur = cur.peer;
                }
                // reach the end
                if (len == count && cur.child.data == '^') {
                    return true;
                }
                cur = cur.child;
                if (cur == null) {
                    return false;
                }
            } else if (cur.data == c) { //cur have
                if (len == count && cur.child.data == '^') {
                    return true;
                }
                cur = cur.child;
            }
            count++;
        }
        return false;
    }
    @Override
    public void insert(T toInsert) {
        if (toInsert == null) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(toInsert).append(end);
        Node cur = root;
        char[] word = sb.toString().toCharArray();
        if (word.length == 0) {
            return;
        }
        for (char c : word) {
            cur = insertHelper(cur, c);
        }
    }
    private Node insertHelper(Node cur, char c) {
        //case 0, cur is empty
        if (cur.data == ' ') {
            cur.data = c;
            cur.child = new Node();
            cur = cur.child;
        }
        //case 1, cur is not empty and have same data
        else if (cur.data != ' ' && cur.data == c) {
            cur = cur.child;
        }
        //case 2, cur is not empty and have different data and have not peers
        else if (cur.data != ' ' && cur.data != c && cur.peer == null) {
            cur.peer = new Node(c);
            cur.peer.child = new Node();
            cur = cur.peer.child;
        }
        //case 3, cur is not empty and have different data and has peers and peers not same value.
        else if (cur.data != ' ' && cur.data != c && !isPeerContains(cur, c)) {
            cur.peer = addLastPear(cur.peer, c);
            while (cur.data != c) {
                cur = cur.peer;
            }
            cur.child = new Node();
            cur = cur.child;
            return cur;
        }
        //case 4, cur is not empty and have different data and has peers and peers has same value.
        else if (cur.data != ' ' && cur.data != c && isPeerContains(cur, c)) {
            while (cur.data != c) {
                cur = cur.peer;
            }
            cur = cur.child;
            return cur;
        }
        return cur;
    }
    private Node addLastPear(Node cur, char c) {
        if (cur == null) {
            cur = new Node(c);
            cur.child = new Node();
            return cur;
        }
        cur.peer = addLastPear(cur.peer, c);
        return cur;
    }
    private boolean isPeerContains(Node cur, char c) {
        while (cur != null) {
            if (cur.data == c) {
                return true;
            }
            cur = cur.peer;
        }
        return false;
    }
    public Node getLastChar(String prefix) {
        if (prefix == null || prefix.length() == 0) {
            return null;
        }
        Node cur = root;
        int count = 0;
        int len = prefix.length();
        char[] word = prefix.toCharArray();
        for (char c : word) {
          //case 0, cur is empty
            count++;
            if (cur.data == ' ') {
                return null;
            }
            //case 3, cur is not empty and have different data and have not peers
            else if (cur.data != ' ' && cur.data != c && cur.peer == null) {
                return null;
            }
            //case 4, cur is not empty and have different data and has peers and peers not same value.
            else if (cur.data != ' ' && cur.data != c && !isPeerContains(cur, c)) {
                return null;
            }
            else if (cur.data != ' ' && cur.data == c && count == len) {
                return cur;
            }
            //case 2, cur is not empty and have same data
            else if (cur.data != ' ' && cur.data == c) {
                cur = cur.child;
            }
            //case 5, cur is not empty and have different data and has peers and peers has same value.
            else if (cur.data != ' ' && cur.data != c && isPeerContains(cur, c)) {
                //System.out.println(count);
                while (cur.data != c) {
                    cur = cur.peer;
                    if (cur.data == c && count == len) {
                        return cur;
                    }
                    //System.out.println(cur.data);
                }
                cur = cur.child;
            }
        }
        return cur;
    }
    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new ArrayDeque<String>(5);

        if (prefix == null || prefix.length() == 0) {
            return null;
        }
        Node firstNode = getLastChar(prefix);
        if (firstNode == null) {
            return null;
        }
        collect(firstNode, prefix, results);
        results = deleteRedundant(results);
        return results;
    }
    private void collect(Node cur, String prefix, Queue<String> results) {
        // case 0, predictions shouldn't bigger than 5.
          if (results.size() > 15) {
              return;
          }
          // case 1, if this is last char of word.
          if (cur.child.data == '^') {
              results.offer(prefix.toString());
          }
          // case 2, if current char is not the last.
          if (cur.data != '^') {
              cur = cur.child;
              prefix = prefix + cur.data;
              collect(cur, prefix, results);
          }
          // case 3, if current char has peer.
          while (cur.peer != null) {
              cur = cur.peer;
              prefix = prefix.substring(0, prefix.length() - 1);
              prefix = prefix + cur.data;
              collect(cur, prefix, results);
          }
          return;
    }
    private Queue<String> deleteRedundant(Queue<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }
        String[] noRepeat = new String[26];
        int count = 0;
        while (!queue.isEmpty()) {
            noRepeat[count] = queue.poll();
            count++;
        }
        int num = noRepeat.length;
        for (int i = 0; i < num - 1; i++) {
            for (int j = i + 1; j < num; j++) {
                if (noRepeat[i] != null && noRepeat[j] != null && noRepeat[i].equals(noRepeat[j])) {
                    noRepeat = delete(noRepeat, j);
                    j--;
                    num--;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            if (noRepeat[i] != null) {
                queue.offer(noRepeat[i]);
            }
        }
        return queue;
    }
    public String[] delete(String[] data, int pos) {
        if (pos >= 0 && pos < data.length) {
            String[] tmp = new String[data.length - 1];
            System.arraycopy(data, 0, tmp, 0, pos);
            System.arraycopy(data, pos + 1, tmp, pos,data.length - pos - 1);
            return tmp;
            }
        return data;
    }
    private static class Node<T> {
        /**
         * data.
         */
        private char data;
        /**
         * next parent Node.
         */
        private Node<T> peer;
        /**
         * next child Node.
         */
        private Node<T> child;
        /**
         * constructor of the node with only value.
         * @param d data
         */
        Node() {
            this(' ', null, null);
        }
        Node(char d) {
            this(d, null, null);
        }
        /**
         * Node element arrtibute.
         * @param d data
         * @param p parent Node
         * @param c child Node
         * @param existed is char existed?
         */
        Node(char d, Node<T> p, Node<T> c) {
            data = d;
            peer = p;
            child = c;
        }
    }
}
