/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class MyLZW {
    private static final int BEGIN_LENGTH = 9;         // codeword width
    private static final int END_LENGTH = 16;         // codeword width
    private static final double COMPRESSION_RATIO = 1.1; //ratio
    private static final int R = 256;        // number of input chars
    private static int L = 512;       // number of codewords = 2^W
    private static int CURRENT_WIDTH = 9;         // codeword width
    private static final int MAX_INDEX = (int) Math.pow(2, END_LENGTH);


    public static void compressModeN() {
        //String testKey = null;
        //int testVal = 0;

        BinaryStdOut.write('n');
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
//            if (code == L - 1 && CURRENT_WIDTH < END_LENGTH) {
//                System.err.println(testVal + ":" + testKey);
//            }
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), CURRENT_WIDTH);      // Print s's encoding.
            if (code >= L && CURRENT_WIDTH < END_LENGTH) {
                //System.err.println(testVal + ":" + testKey);
                L = (int) Math.pow(2, ++CURRENT_WIDTH);
            }
            int t = s.length();
            if (t < input.length() && code < L) {    // Add s to symbol table.
                //testVal = code;
                //testKey = input.substring(0, t + 1);
                st.put(input.substring(0, t + 1), code++);
            }
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, CURRENT_WIDTH);
        BinaryStdOut.close();
    }


    public static void expandModeN() {
        //String testKey = null;
        //int testVal = 0;

        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(CURRENT_WIDTH);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            if (i >= L && CURRENT_WIDTH < END_LENGTH) {
                //System.err.println(testVal + ":" + testKey);
                L = (int) Math.pow(2, ++CURRENT_WIDTH);
                String[] tmp = st.clone();
                st = new String[L];
                for (int j = 0; j < tmp.length; j++) {
                    st[j] = tmp[j];
                }
            }
            codeword = BinaryStdIn.readInt(CURRENT_WIDTH);
            if (codeword == R) break;
            String s = st[codeword];
            //testVal = i;
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            //testKey = st[testVal];
            val = s;
        }
        BinaryStdOut.close();
    }
    public static void compressModeR() {
        //String testKey = null;
        //int testVal = 0;
        BinaryStdOut.write('r');
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), CURRENT_WIDTH);      // Print s's encoding.
            if (code >= MAX_INDEX) {
                //System.err.println(testVal + ":" + testKey);
                CURRENT_WIDTH = BEGIN_LENGTH;
                L = 512;
                st = new TST<Integer>();
                for (int i = 0; i < R; i++)
                    st.put("" + (char) i, i);
                code = R+1;  // R is codeword for EOF
            }
            if (code >= L && CURRENT_WIDTH < END_LENGTH) {
                L = (int) Math.pow(2, ++CURRENT_WIDTH);
            }
            int t = s.length();
            if (t < input.length() && code < L) {    // Add s to symbol table.
                //testKey = input.substring(0, t + 1);
                //testVal = code;
                st.put(input.substring(0, t + 1), code++);
            }
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, CURRENT_WIDTH);
        BinaryStdOut.close();
    }
    public static void expandModeR() {
        //String testKey = null;
        //int testVal = 0;

        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(CURRENT_WIDTH);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            BinaryStdOut.write(val);
            if (i >= L && CURRENT_WIDTH < END_LENGTH) {
                L = (int) Math.pow(2, ++CURRENT_WIDTH);
                String[] tmp = st.clone();
                st = new String[L];
                for (int j = 0; j < tmp.length; j++) {
                    st[j] = tmp[j];
                }
            }
            if (i >= MAX_INDEX) {
                //System.err.println(testVal + ":" + testKey);
                BinaryStdOut.write(val);
                CURRENT_WIDTH = BEGIN_LENGTH;
                L = 512;
                st = new String[L];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
            }
            codeword = BinaryStdIn.readInt(CURRENT_WIDTH);
            if (codeword == R) break;
            String s = st[codeword];
            //testVal = i;
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            //testKey = st[testVal];
            val = s;
        }
        BinaryStdOut.close();
    }
    public static void compressModeM() {
        double oldRatio = 0;
        double curRatio = 0;
        double ratio = 0;
        long uncompressedSize = 0;
        long compressedSize = 0;
        int charBits = 16;
        boolean overwrite = false;

        BinaryStdOut.write('m');
        String input = BinaryStdIn.readString();
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        while (input.length() > 0) {
//            if (code == MAX_INDEX - 1) {
//                oldRatio = (double) uncompressedSize / compressedSize;
//                System.err.println("old1" + oldRatio);
//            }
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), CURRENT_WIDTH);      // Print s's encoding.
            int t = s.length();
            uncompressedSize = uncompressedSize + charBits * t;
            compressedSize = compressedSize + CURRENT_WIDTH;

            if (code >= L && CURRENT_WIDTH < END_LENGTH) {
                L = (int) Math.pow(2, ++CURRENT_WIDTH);
            }
            if (code >= MAX_INDEX) {   //when codebook is full
                //System.err.println(code);
                curRatio = (double) uncompressedSize / compressedSize;
                if (!overwrite) {
                    oldRatio = (double) uncompressedSize / compressedSize;
                    overwrite = true;
                    //System.err.println("old1" + oldRatio);
                }
                ratio = oldRatio / curRatio;
                if (ratio > COMPRESSION_RATIO) {  //bigger than 1.1
                    CURRENT_WIDTH = BEGIN_LENGTH;
                    L = 512;
                    st = new TST<Integer>();
                    for (int i = 0; i < R; i++)
                        st.put("" + (char) i, i);
                    code = R+1;  // R is codeword for EOF
                    overwrite = false;
                }
            }

            if (t < input.length() && code < L) {    // Add s to symbol table.
                //testKey = input.substring(0, t + 1);
                //testVal = code;
                st.put(input.substring(0, t + 1), code++);
                //oldRatio = (double) uncompressedSize / compressedSize;
            }
            input = input.substring(t);            // Scan past s in input.
        }
        BinaryStdOut.write(R, CURRENT_WIDTH);
        BinaryStdOut.close();
    }
    public static void expandModeM() {
        double oldRatio = 0;
        double curRatio = 0;
        double ratio = 0;
        long uncompressedSize = 0;
        long compressedSize = 0;
        int charBits = 16;
        boolean overwrite = false;

        String[] st = new String[L];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(CURRENT_WIDTH);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        while (true) {
            compressedSize = compressedSize + CURRENT_WIDTH;
            uncompressedSize = uncompressedSize + charBits * val.length();
            BinaryStdOut.write(val);
            if (i >= L && CURRENT_WIDTH < END_LENGTH) {
                //oldRatio = uncompressedSize / compressedSize;
                L = (int) Math.pow(2, ++CURRENT_WIDTH);
                String[] tmp = st.clone();
                st = new String[L];
                for (int j = 0; j < tmp.length; j++) {
                    st[j] = tmp[j];
                }
            }
            if (i >= MAX_INDEX) {
                //System.err.println(testVal + ":" + testKey);
                curRatio = (double)uncompressedSize / compressedSize;
                if (!overwrite) {
                    oldRatio = curRatio;
                    //System.err.println("old2:" + oldRatio);
                    overwrite = true;
                    //System.err.println("old2" + oldRatio);
                //System.err.println(ratio);
                }
                ratio = oldRatio / curRatio;
                if (ratio > COMPRESSION_RATIO) {
                    CURRENT_WIDTH = BEGIN_LENGTH;
                    //codeword = BinaryStdIn.readInt(CURRENT_WIDTH);
                    L = 512;
                    st = new String[L];
                    for (i = 0; i < R; i++)
                        st[i] = "" + (char) i;
                    st[i++] = "";
                    overwrite = false;
                }
            }
            codeword = BinaryStdIn.readInt(CURRENT_WIDTH);
            if (codeword == R) break;
            String s = st[codeword];
            //testVal = i;
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) st[i++] = val + s.charAt(0);
            //testKey = st[testVal];
            val = s;
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) {
        if (args[0].equals("-")) {
            if (args[1].equals("n")) {
                compressModeN();
            }
            else if (args[1].equals("r")) {
                compressModeR();
            }
            else if (args[1].equals("m")) {
                compressModeM();
            }
        }

        else if (args[0].equals("+")) {
            char type = BinaryStdIn.readChar();
            if (type == 'n') {
                expandModeN();
            }
            else if (type == 'r') {
                expandModeR();
            }
            else if (type == 'm') {
                expandModeM();
            }
        }
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}
