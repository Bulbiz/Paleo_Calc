package paleo.lib.interpreter;

import paleo.lib.token.OperandToken;

/**
* OperationEvaluator
*/
@FunctionalInterface
public interface OperationEvaluator {

	 public OperandToken evaluateOperation(OperandToken op1, OperandToken op2);

}
