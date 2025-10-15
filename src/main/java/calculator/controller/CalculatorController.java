package calculator.controller;

import calculator.domain.Expression;
import calculator.view.InputView;
import calculator.view.OutputView;

public class CalculatorController {

    private final InputView inputView;
    private final OutputView outputView;

    public CalculatorController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Expression expression = new Expression(inputView.readExpression());
        outputView.printResult(expression.calculateSum());
    }
}
