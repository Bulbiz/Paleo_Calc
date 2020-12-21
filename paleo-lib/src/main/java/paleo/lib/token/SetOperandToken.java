package paleo.lib.token;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import paleo.lib.interpreter.OperationDictionary;

/**
 * Model a Set.
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

    /**
	 * Auxilary function
	 * Do the intersection of two set
     * @param op1 is the first Set parameter.
	 * @param op2 is the second Set parameter.
	 * @return a new Set that correspond to op1 inter op2.
	 */
	private static SetOperandToken inter(SetOperandToken op1, SetOperandToken op2) {
        SetOperandToken retour = new SetOperandToken();
		List<OperandToken> element_op1 = op1.getElements();
        List<OperandToken> element_op2 = op2.getElements();
        
		List<OperandToken> element_inter = element_op1
			.stream()
			.filter(e -> element_op2.contains(e))
			.collect(Collectors.toList());
		retour.addAll(element_inter);
        
		return retour;
	}

	/**
	 * Auxilary function
	 * Do the union of two set
     * @param op1 is the first Set parameter.
	 * @param op2 is the second Set parameter.
	 * @return a new Set that correspond to op1 union op2.
	 */
	private static SetOperandToken union(SetOperandToken op1, SetOperandToken op2) {
        SetOperandToken retour = new SetOperandToken();
		List<OperandToken> element_op1 = op1.getElements();
        List<OperandToken> element_op2 = op2.getElements();
        
		element_op1
			.stream()
			.filter(e -> !element_op2.contains(e))
            .forEach(e -> element_op2.add(e));
		retour.addAll(element_op2);
        
		return retour;
	}

	/**
	 * Auxilary function
	 * Do the difference of two set
     * @param op1 is the first Set parameter.
	 * @param op2 is the second Set parameter.
	 * @return a new Set that correspond to op1 diff op2.
	 */
	private static SetOperandToken diff(SetOperandToken op1, SetOperandToken op2) {
		List<OperandToken> element_op1 = op1.getElements();
		List<OperandToken> element_op2 = op2.getElements();
		SetOperandToken retour = new SetOperandToken();
		
		List<OperandToken> recuperation = element_op1
			.stream()
			.filter(e -> !element_op2.contains(e))
            .collect(Collectors.toList());
		retour.addAll(recuperation);
		
		return retour;
    }
    
    /** 
     * List that contains all the elements 
     */
	private ArrayList<OperandToken> elements;

    /**
	 * {@link SetOperandToken} constructor.
	 * @param ajout is the list of operand that have to be added in the set.
	 */
	public SetOperandToken() {
        this.elements = new ArrayList<OperandToken>();
	}

	/**
	 * add one element to the set
	 * @param element is the operand that have to be added in the set.
	 */
	public void add(OperandToken element){
		if (element != null && !this.elements.contains(element)) this.elements.add(element);
	}

	/**
	 * add a bunch of element to the Set
	 * @param listElements is a list of operand that have to be added in the set.
	 */
	public void addAll(List<OperandToken> listElements){
		listElements.stream().forEach(e -> this.add(e));
	}
    /**
     * Look if the set is equals to the second set.
     * Order doesn't count here
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

	/** 
	 * @return a list that contains all the elements of the set.
	 */
	public List<OperandToken> getElements() {
        List<OperandToken> res = new ArrayList<OperandToken>();
        
        res.addAll(this.elements);
        
		return res;
	}
}
