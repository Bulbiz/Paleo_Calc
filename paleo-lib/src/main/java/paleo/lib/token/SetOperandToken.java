package paleo.lib.token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import paleo.lib.interpreter.OperationDictionary;

/**
 * Models a Set.
 */
public final class SetOperandToken implements OperandToken {

    /**
	 * Adds corresponding {@link OperationEvaluator} implementations
	 * to the {@link OperationDictionary}.
	 */
	{
		OperationDictionary.addEntry(
			OperationToken.INTER,
			operands -> {
				SetOperandToken op1 = (SetOperandToken) operands.pop();
				SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (inter(op1, op2));
			},
			List.of(SetOperandToken.class, SetOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.UNION,
			operands -> {
				SetOperandToken op1 = (SetOperandToken) operands.pop();
				SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (union(op1, op2));
			},
			List.of(SetOperandToken.class, SetOperandToken.class)
		);
		OperationDictionary.addEntry(
			OperationToken.DIFF,
			operands -> {
				SetOperandToken op1 = (SetOperandToken) operands.pop();
				SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (diff(op1, op2));
			},
			List.of(SetOperandToken.class, SetOperandToken.class)
		);
	}

    /*  Auxilary function
	 *  @return a new Set that is the result of op1 inter op2
	 */
	private static SetOperandToken inter(SetOperandToken op1, SetOperandToken op2) {
		List<OperandToken> element_op1 = op1.getElements();
		List<OperandToken> element_op2 = op2.getElements();
		List<OperandToken> element_inter = element_op1
			.stream()
			.filter(e -> element_op2.contains(e))
			.collect(Collectors.toList());
		SetBuilder builder = new SetBuilder();
		builder.addAll(element_inter);
		return builder.build();
	}

	/*  Auxilary function
	 *  @return a new Set that is the result of op1 union op2
	 */
	private static SetOperandToken union(SetOperandToken op1, SetOperandToken op2) {
		List<OperandToken> element_op1 = op1.getElements();
		List<OperandToken> element_op2 = op2.getElements();
		element_op1
			.stream()
			.filter(e -> !element_op2.contains(e))
			.forEach(e -> element_op2.add(e));
		SetBuilder builder = new SetBuilder();
		builder.addAll(element_op2);
		return builder.build();
	}

	/*  Auxilary function
	 *  @return a new Set that is the result of op1 diff op2
	 */
	private static SetOperandToken diff(SetOperandToken op1, SetOperandToken op2) {
		List<OperandToken> element_op1 = op1.getElements();
		List<OperandToken> element_op2 = op2.getElements();
		List<OperandToken> recuperation = element_op1
			.stream()
			.filter(e -> !element_op2.contains(e))
			.collect(Collectors.toList());
		SetBuilder builder = new SetBuilder();
		builder.addAll(recuperation);
		return builder.build();
    }
    
    /* List that contains all the elements */
	private ArrayList<OperandToken> elements;

	private SetOperandToken(List<OperandToken> ajout) {
		/* Defensive Copy */
		this.elements = new ArrayList<OperandToken>();
		this.elements.addAll(ajout);
	}

    /*  A Set is considered equals to another one if 
     *  all the element of set1 is in set2 and 
     *  all the element of set2 is in set1.
     */
	@Override
	public boolean equals(Object obj) {
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

	/*  @return a list of elements in the set */
	public List<OperandToken> getElements() {
		List<OperandToken> res = new ArrayList<OperandToken>();
		res.addAll(this.elements);
		return res;
	}

	/* A builder for Set Operand */
	public static final class SetBuilder {

		private List<OperandToken> storage;

        /* Constructor for builder */
		public SetBuilder() {
			storage = new ArrayList<OperandToken>();
		}

        /*  Add {element} to the builder,
         *  the element is accepted if it's not null
         *  and if the element is not already in the builder
         */
		public void add(OperandToken element) {
			if (element != null && !storage.contains(element)) storage.add(element);
		}

        /*  Add all element of {elements} to the builder,
         *  the element is accepted if it's not null
         *  and if the element is not already in the builder
         */
		public void addAll(List<OperandToken> elements) {
			elements.stream().forEach(e -> this.add(e));
		}

        /*  @return a new SetOperandToken from the builder */
		public SetOperandToken build() {
			return new SetOperandToken(storage);
		}
	}
}
