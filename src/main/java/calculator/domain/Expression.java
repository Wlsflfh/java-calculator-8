package calculator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Expression {

    private static final String REGEX_OR = "|";
    private static final String DEFAULT_DELIMITER = ",|:";
    private static final String CUSTOM_DELIMITER_PREFIX = "//";
    private static final String CUSTOM_DELIMITER_SUFFIX = "\n";
    private static final String INPUT_DELIMITER_SUFFIX = "\\n";
    private static final String INVALID_CUSTOM_DELIMITER_REGEX = "[0-9]";
    private static final String CUSTOM_DELIMITER_FORMAT_REGEX = "//(.)\n(.*)";
    public static final String ALLOWED_DELIMITER_PREFIX = "[^0-9";
    public static final String ALLOWED_DELIMITER_SUFFIX = "]";

    private final List<Digit> numbers;

    public Expression(String text) {
        this.numbers = parseExpression(text);
    }

    public long calculateSum() {
        long sum = 0L;
        for (Digit digit : numbers) {
            sum += digit.getDigit();
        }
        if (sum > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("합계가 정수형 최대 범위를 초과했습니다.");
        }
        return sum;
    }

    private List<Digit> parseExpression(String text) {
        if (text.isEmpty()) return new ArrayList<>();

        String delimiter = DEFAULT_DELIMITER;
        String numbersPart = text;

        if (hasCustomDelimiter(text)) {
            Matcher matcher = createCustomDelimiterMatcher(text);
            delimiter = addCustomDelimiter(matcher);
            numbersPart = extractNumbersPart(matcher);
        }

        validateAllowedDelimiter(numbersPart, delimiter);
        return convertToDigits(numbersPart.split(delimiter));
    }

    private boolean hasCustomDelimiter(String text) {
        return text.startsWith(CUSTOM_DELIMITER_PREFIX);
    }

    private Matcher createCustomDelimiterMatcher(String text) {
        text = text.replace(INPUT_DELIMITER_SUFFIX, CUSTOM_DELIMITER_SUFFIX);
        return Pattern.compile(CUSTOM_DELIMITER_FORMAT_REGEX).matcher(text);
    }

    private String addCustomDelimiter(Matcher matcher) {
        if (!matcher.find()) {
            throw new IllegalArgumentException("잘못된 커스텀 구분자 형식입니다.");
        }

        String customDelimiter = matcher.group(1);
        validateCustomDelimiter(customDelimiter);
        return DEFAULT_DELIMITER + REGEX_OR + customDelimiter;
    }

    private String extractNumbersPart(Matcher matcher) {
        return matcher.group(2);
    }

    private List<Digit> convertToDigits(String[] splitNumbers) {
        List<Digit> digits = new ArrayList<>();
        for (String number : splitNumbers) {
            if (number.trim().isEmpty()) continue;
            digits.add(new Digit(number));
        }
        return digits;
    }

    private void validateAllowedDelimiter(String expressionText, String delimiter) {
        String pattern = ALLOWED_DELIMITER_PREFIX + Pattern.quote(delimiter) + ALLOWED_DELIMITER_SUFFIX;

        if (Pattern.compile(pattern).matcher(expressionText).find()) {
            throw new IllegalArgumentException("구분자와 일치하지 않는 문자가 존재합니다.");
        }
    }

    private void validateCustomDelimiter(String customDelimiter) {
        if (Pattern.matches(INVALID_CUSTOM_DELIMITER_REGEX, customDelimiter)) {
            throw new IllegalArgumentException("잘못된 커스텀 구분자 형식입니다.");
        }
    }
}
