package calculator.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {

    @Test
    @DisplayName("빈 문자열(공백)은 0으로 처리하는지 확인한다")
    void emptyTextTest() {
        // given
        String emptyText = "";
        String emptyCustomText = "//;\\n;;;,:";

        // when
        Expression expression1 = new Expression(emptyText);
        Expression expression2 = new Expression(emptyCustomText);
        long sum1 = expression1.calculateSum();
        long sum2 = expression2.calculateSum();

        // then
        assertEquals(0, sum1);
        assertEquals(0, sum2);
    }

    @Test
    @DisplayName("한 자릿수 이상의 수에 대해서도 올바르게 계산하는지 확인한다")
    void digitsTest() {
        // given
        String text = "1,200:30000";

        // when
        Expression expression = new Expression(text);
        long sum = expression.calculateSum();

        // then
        assertEquals(30201, sum);
    }

    @Test
    @DisplayName("기본 구분자와 커스텀 구분자 혼용이 가능한지 확인한다")
    void mixedDelimiterTest() {
        // given
        String text = "//;\n1,2;3:4";

        // when
        Expression expression = new Expression(text);
        long sum = expression.calculateSum();

        // then
        assertEquals(10, sum);
    }

    @Test
    @DisplayName("기본 구분자 이외의 문자가 나왔을 때 예외를 발생시키는지 확인한다")
    void defaultDelimiterTest() {
        // given
        String nonAllowedDelimiter = "1,2;3";
        String minusText = "-1,2:3";
        String spaceText = "1,2 3";

        // when - then
        assertThatThrownBy(() -> new Expression(nonAllowedDelimiter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자와 일치하지 않는 문자가 존재합니다.");

        assertThatThrownBy(() -> new Expression(minusText))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자와 일치하지 않는 문자가 존재합니다.");

        assertThatThrownBy(() -> new Expression(spaceText))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자와 일치하지 않는 문자가 존재합니다.");
    }

    @Test
    @DisplayName("커스텀 구분자 패턴과 맞지 않을 때 예외를 발생시키는지 확인한다")
    void customDelimiterTest() {
        // given
        String emptyCustom = "//\n1,23";
        String prefixWrong = "/;\n1,2;3";
        String suffixWrong = "//;\\1,2;3";
        String stringCustom = "//;;\n1,2;;3";
        String digitCustom = "//3\n132,33";

        // when - then
        assertThatThrownBy(() -> new Expression(emptyCustom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 커스텀 구분자 형식입니다.");

        assertThatThrownBy(() -> new Expression(prefixWrong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("구분자와 일치하지 않는 문자가 존재합니다.");

        assertThatThrownBy(() -> new Expression(suffixWrong))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 커스텀 구분자 형식입니다.");

        assertThatThrownBy(() -> new Expression(stringCustom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 커스텀 구분자 형식입니다.");

        assertThatThrownBy(() -> new Expression(digitCustom))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 커스텀 구분자 형식입니다.");
    }

    @Test
    @DisplayName("합을 계산할 떄 정수형 최대 범위를 넘으면 예외를 발생시키는지 확인한다.")
    void calculateSumTest() {
        // given
        String text = "2147483646,1:2";

        // when
        Expression expression = new Expression(text);

        // then
        assertThatThrownBy(expression::calculateSum)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("합계가 정수형 최대 범위를 초과했습니다.");
    }
}
