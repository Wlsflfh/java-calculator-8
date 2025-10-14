package calculator.domain;

import java.util.ArrayList;
import java.util.List;

public class Expression {

    private final List<Integer> numbers;

    private static final String DEFAULT_DELIMITER = "[,:]";

    public Expression(String expressionText) {
        this.numbers = parse(expressionText);
    }

    private List<Integer> parse(String expressionText) {
        List<Integer> numbers = new ArrayList<>();

        String[] split = expressionText.split(DEFAULT_DELIMITER);
        for (String number : split) {
            numbers.add(Integer.parseInt(number));
        }

        return numbers;
    }

    public int calculateSum() {
        return numbers.stream().mapToInt(Integer::intValue).sum();
    }
}
