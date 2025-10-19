package calculator.view;

public class OutputView {

    private static final String RESULT_MESSAGE = "결과 : %d\n";

    public void printResult(long result) {
        System.out.printf(RESULT_MESSAGE, result);
    }
}
