package calculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DigitTest {

    @Test
    @DisplayName("문자가 넘어왔을때 예외를 발생시키는지 확인한다.")
    void validateDigitTest() {
        // given
        String comma = ",";

        // when - then
        assertThatThrownBy(() -> new Digit(comma))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 입력 형식입니다.");
    }

    @Test
    @DisplayName("정수형 최대 범위를 넘어갔을 때 예외를 발생시키는지 확인한다.")
    void validateMaxIntegerTest() {
        // given
        String digit = "2147483648";

        // when - then
        assertThatThrownBy(() -> new Digit(digit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력값이 정수형 최대 범위를 초과했습니다.");
    }
}
