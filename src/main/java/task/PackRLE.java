package task;

public class PackRLE {

    public String pack (String input) {

        if (input.isEmpty()) return "";

        if (input.matches("[0-9]+")) {
            throw new IllegalArgumentException("The input string cannot contain only digits");
        }

        StringBuilder result = new StringBuilder();
        char[] arr = input.toCharArray();
        int sequenceLength = 0;
        char symbol = arr[0];

        for (char ch : arr) {
            if (Character.isDigit(ch)) {
                throw new IllegalArgumentException("The input string cannot contain digits");
            }
            if (ch == symbol) sequenceLength += 1;
            else if (sequenceLength > 1) {
                result.append(sequenceLength);
                result.append(symbol);
                sequenceLength = 1;
                symbol = ch;
            } else {
                result.append(symbol);
                symbol = ch;
            }
        }

        if (sequenceLength > 1) result.append(sequenceLength);
        result.append(symbol);

        return result.toString();
    }

    public String unpack (String input) {

        if (input.isEmpty()) return "";

        if (input.matches("[0-9]+")) {
            throw new IllegalArgumentException("The input string cannot contain only digits");
        }

        StringBuilder result = new StringBuilder();
        char[] arr = input.toCharArray();

        if (Character.isDigit(arr[arr.length - 1])) {
            throw new IllegalArgumentException("The last character cannot be a digit");
        }

        boolean flag = false;
        StringBuilder quantity = new StringBuilder();
        int iQuantity;

        for (char ch : arr) {
            if (Character.isDigit(ch)) {
                flag = true;
                quantity.append(ch);
            } else if (flag) {
                flag = false;
                iQuantity = Integer.parseInt(quantity.toString());
                while (iQuantity > 0) {
                    result.append(ch);
                    iQuantity--;
                }
                quantity.setLength(0);
            } else {
                result.append(ch);
            }
        }

        return result.toString();
    }
}
