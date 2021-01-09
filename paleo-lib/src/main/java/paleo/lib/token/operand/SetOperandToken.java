package paleo.lib.token.operand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import paleo.lib.interpreter.OperationDictionary;
import paleo.lib.interpreter.OperationEvaluator;
import paleo.lib.token.operation.*;

/**
 * Models a mathematical set.
 */
public final class SetOperandToken implements OperandToken {
	/**
	 * Adds corresponding {@link OperationEvaluator} implementations
	 * to the {@link OperationDictionary}.
	 */
	{
		OperationDictionary.addEntry(
			new InterOperationToken(),
			operands -> {
				final SetOperandToken op1 = (SetOperandToken) operands.pop();
				final SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (getIntersection(op1, op2));
			},
			List.of(SetOperandToken.class, SetOperandToken.class)
		);
		OperationDictionary.addEntry(
			new UnionOperationToken(),
			operands -> {
				final SetOperandToken op1 = (SetOperandToken) operands.pop();
				final SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (getUnion(op1, op2));
			},
			List.of(SetOperandToken.class, SetOperandToken.class)
		);
		OperationDictionary.addEntry(
			new DiffOperationToken(),
			operands -> {
				final SetOperandToken op1 = (SetOperandToken) operands.pop();
				final SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (getDiff(op1, op2));
			},
			List.of(SetOperandToken.class, SetOperandToken.class)
		);
	}

	/**
	 * Returns the intersection of two given {@link SetOperandToken}.
	 *
	 * @param op1 is the first {@link SetOperandToken}.
	 * @param op2 is the second {@link SetOperandToken}.
	 *
	 * @return a new instance of {@link OperandToken} corresponding to the intersection.
	 */
	private static SetOperandToken getIntersection(
		final SetOperandToken op1,
		final SetOperandToken op2
	) {
		final SetOperandToken resElements = new SetOperandToken();
		final List<OperandToken> element_op1 = op1.getElements();
		final List<OperandToken> element_op2 = op2.getElements();

		final List<OperandToken> element_inter = element_op1
			.stream()
			.filter(e -> element_op2.contains(e))
			.collect(Collectors.toList());
		resElements.addAll(element_inter);

		return resElements;
	}

	/**
	 * Returns the union of two given {@link SetOperandToken}.
	 *
	 * @param op1 is the first {@link SetOperandToken}.
	 * @param op2 is the second {@link SetOperandToken}.
	 *
	 * @return a new instance of {@link OperandToken} corresponding to the union.
	 */
	private static SetOperandToken getUnion(
		final SetOperandToken op1,
		final SetOperandToken op2
	) {
		final SetOperandToken resElements = new SetOperandToken();
		final List<OperandToken> element_op1 = op1.getElements();
		final List<OperandToken> element_op2 = op2.getElements();

		element_op1
			.stream()
			.filter(e -> !element_op2.contains(e))
			.forEach(e -> element_op2.add(e));
		resElements.addAll(element_op2);

		return resElements;
	}

	/**
	 * Returns the difference between two given {@link SetOperandToken}.
	 *
	 * @param op1 is the first {@link SetOperandToken}.
	 * @param op2 is the second {@link SetOperandToken}.
	 *
	 * @return a new instance of {@link OperandToken} corresponding to the difference.
	 */
	private static SetOperandToken getDiff(
		final SetOperandToken op1,
		final SetOperandToken op2
	) {
		final List<OperandToken> element_op1 = op1.getElements();
		final List<OperandToken> element_op2 = op2.getElements();
		final SetOperandToken resElements = new SetOperandToken();

		final List<OperandToken> recuperation = element_op1
			.stream()
			.filter(e -> !element_op2.contains(e))
			.collect(Collectors.toList());
		resElements.addAll(recuperation);

		return resElements;
	}

	/** {@link ArrayList} containing the set {@link OperandToken} elements. */
	private final ArrayList<OperandToken> elements;

	/** {@link SetOperandToken} constructor. */
	public SetOperandToken() {
		this.elements = new ArrayList<OperandToken>();
	}

	/**
	 * Adds one element to the set.
	 *
	 * @param element is the operand that have to be added in the set.
	 */
	public void add(final OperandToken element) {
		if (element != null && !this.elements.contains(element)) {
			this.elements.add(element);
		}
	}

	/**
	 * Adds multiple elements to the set.
	 *
	 * @param listElements is a list of {@link OperandToken} that have to be added in the set.
	 */
	public void addAll(final List<OperandToken> listElements) {
		listElements.stream().forEach(e -> this.add(e));
	}

	@Override
	public boolean equals(final Object obj) {
		List<OperandToken> operandSet;

		if (!(obj instanceof SetOperandToken)) {
			return false;
		}
		operandSet = ((SetOperandToken) obj).getElements();

		return (
			operandSet.containsAll(this.getElements()) &&
			this.getElements().containsAll(operandSet)
		);
	}

	@Override
	public String toString() {
		return (
			"{" +
			elements.stream().map(e -> e.toString()).collect(Collectors.joining("; ")) +
			"}"
		);
	}

	/**
	 * @return a copy of the set elements.
	 */
	public List<OperandToken> getElements() {
		final List<OperandToken> res = new ArrayList<OperandToken>();

		res.addAll(this.elements);

		return res;
	}
}
