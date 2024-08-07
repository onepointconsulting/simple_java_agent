package com.onepointltd.tools;

import static com.onepointltd.config.Logging.logger;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;
import java.util.logging.Level;

public class Calculator extends AbstractTool {

  public static final String NAME = "calculator";

  @Override
  public String name() {
    return NAME;
  }

  @Override
  public String description() {
    return "Calculator tool to perform arithmetic operations for expressions like 2+2, 3*4, 5*6/2, etc. "
        + "The calculator accepts no text like `kilometers` or `meters` as input. It only accepts arithmetic expressions.";
  }

  @Override
  public String execute(String query) {
    Expression expression = new Expression(query);
    try {
      EvaluationValue result = expression.evaluate();
      return "The result is " + result.getNumberValue();
    } catch (EvaluationException e) {
      logger.log(Level.SEVERE, "Failed to evaluate expression", e);
      return e.getMessage();
    } catch (ParseException e) {
      logger.log(Level.SEVERE, "Failed to parse expression", e);
      return e.getMessage();
    }
  }

  public String example() {
    return String.format("%s: The result is 4", name());
  }
}
