public class KaratsubaMultiply {
    private final String x;
    private final String y;

    public KaratsubaMultiply(String x, String y) {
        Pair pair = preProcess(x, y);
        this.x = pair.a;
        this.y = pair.b;
    }

    public static void main(String[] args) {
        KaratsubaMultiply karatsubaMultiply = new KaratsubaMultiply("4312", "234");
        System.out.println(karatsubaMultiply.compute());
        System.out.println(4312*234);
    }

    private Pair preProcess(String x, String y) {
        if (x.length() == y.length() && isEven(x.length())) return new Pair(x, y);
        StringBuilder bigString;
        StringBuilder smallString;
        int maxLength = Integer.max(x.length(), y.length());
        if (maxLength == x.length()) {
            bigString = new StringBuilder(x);
            smallString = new StringBuilder(y);
        } else {
            bigString = new StringBuilder(y);
            smallString = new StringBuilder(x);
        }
        for (int i = 0; i < bigString.length() - smallString.length(); i++) {
            smallString.insert(0, "0");
        }
        if (isEven(bigString.length())) return new Pair(bigString.toString(), smallString.toString());
        smallString.insert(0, "0");
        bigString.insert(0, "0");
        return new Pair(bigString.toString(), smallString.toString());
    }

    private boolean isEven(int i) {
        return (i & 1) == 0;
    }

    private String multiply(String x, String y) {
        if ((x.length() <= 2) && (y.length() <= 2)) return String.valueOf(Integer.parseInt(x) * Integer.parseInt(y));
        String a = x.substring(0, x.length() / 2);
        String b = x.substring(x.length() / 2);
        String c = y.substring(0, y.length() / 2);
        String d = y.substring(y.length() / 2);
        StringBuilder ac = new StringBuilder(multiply(a, c));
        String bd = multiply(b, d);
        String ad = multiply(a, d);
        String bc = multiply(b, c);
        StringBuilder compositeSum = new StringBuilder(add(ad, bc));
        for (int i = 0; i < x.length(); i++) {
            ac.append("0");
        }
        for (int i = 0; i < x.length() / 2; i++) {
            compositeSum.append("0");
        }
        return add(add(ac.toString(), compositeSum.toString()), bd);
    }

    private String add(String x, String y) {
        String bigString;
        StringBuilder smallString;
        int maxLength = Integer.max(x.length(), y.length());
        if (maxLength == x.length()) {
            bigString = x;
            smallString = new StringBuilder(y);
        } else {
            bigString = y;
            smallString = new StringBuilder(x);
        }
        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = 0; i < smallString.length(); i++) {
            int sum = carry + Character.getNumericValue(bigString.charAt(bigString.length() - i - 1)) + Character.getNumericValue(smallString.charAt(smallString.length() - i - 1));
            if (sum >= 10) {
                result.append(sum - 10);
                carry = (sum / 10);
            } else {
                result.append(sum);
                carry = 0;
            }
        }

        for (int i = bigString.length() - smallString.length() - 1; i >= 0; i--) {
            int sum = carry + Character.getNumericValue(bigString.charAt(i));
            if (sum >= 10) {
                result.append(sum - 10);
                carry = (sum / 10);
            } else {
                result.append(sum);
                carry = 0;
            }
        }
        if (carry != 0) result.append(carry);
        return result.reverse().toString();
    }

    public String compute() {
        return multiply(this.x, this.y);
    }

    private static class Pair {
        private final String a, b;

        public Pair(String a, String b) {
            this.a = a;
            this.b = b;
        }
    }
}
