package calculator.domain;

public class Digit {

    private static final int INTEGER_MAX_VALUE = Integer.MAX_VALUE;

    private final int digit;

    public Digit(String digit) {
        validateDigit(digit);
        this.digit = parseDigit(digit);
    }

    private int parseDigit(String digit) {
        return Integer.parseInt(digit);
    }

    private void validateDigit(String digit) {
        try {
            validateMaxInteger(Long.parseLong(digit));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }

    private static void validateMaxInteger(long value) {
        if (value > INTEGER_MAX_VALUE) {
            throw new IllegalArgumentException("입력값이 정수형 최대 범위를 초과했습니다.");
        }
    }

    public int getDigit() {
        return digit;
    }
}
