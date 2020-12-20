package paleo.lib.token;
import paleo.lib.interpreter.OperationDictionary;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class SetOperandToken implements OperandToken {

    {
        OperationDictionary.addEntry(
            OperationToken.INTER,
            (operands) -> {
                SetOperandToken op1 = (SetOperandToken) operands.pop();
                SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (inter(op1,op2));
			},
            List.of(SetOperandToken.class,SetOperandToken.class)
        );
        OperationDictionary.addEntry(
            OperationToken.UNION,
            (operands) -> {
                SetOperandToken op1 = (SetOperandToken) operands.pop();
                SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (union(op1,op2));
			},
            List.of(SetOperandToken.class,SetOperandToken.class)
        );
        OperationDictionary.addEntry(
            OperationToken.DIFF,
            (operands) -> {
                SetOperandToken op1 = (SetOperandToken) operands.pop();
                SetOperandToken op2 = (SetOperandToken) operands.pop();
				return (diff(op1,op2));
			},
            List.of(SetOperandToken.class,SetOperandToken.class)
        );
    }
    private ArrayList <String> elements;

    private SetOperandToken (List <String> ajout){
        /* Defensive Copy */
        this.elements = new ArrayList<String> ();
        this.elements.addAll(ajout);
    }

    // TODO: Needs to find a better design.
	@Override
	public boolean isAnOperandToken() {
		return true;
    }
    
    @Override
	public boolean equals(Object obj) {
        return true;
    }
    
    @Override
	public String toString() {
        return "{ " + elements.stream().collect(Collectors.joining(" ; ")) + " }";
	}

    public List<String> getElements (){
        List<String> res = new ArrayList<String> ();
        res.addAll(this.elements);
        return res;
    }

    private static SetOperandToken inter (SetOperandToken op1, SetOperandToken op2){
        List<String> element_op1 = op1.getElements();
        List<String> element_op2 = op2.getElements();
        List<String> element_inter = element_op1.stream().filter(e -> element_op2.contains(e)).collect(Collectors.toList());
        return new SetOperandToken (element_inter);
    }

    private static SetOperandToken union (SetOperandToken op1, SetOperandToken op2){
        List<String> element_op1 = op1.getElements();
        List<String> element_op2 = op2.getElements();
        element_op2.stream().filter(e -> !element_op1.contains(e)).forEach(e -> element_op1.add(e));
        return new SetOperandToken (element_op1);
    }

    private static SetOperandToken diff (SetOperandToken op1, SetOperandToken op2){
        List<String> element_op1 = op1.getElements();
        List<String> element_op2 = op2.getElements();
        element_op1.stream().filter(e -> !element_op2.contains(e)).collect(Collectors.toList());
        return new SetOperandToken (element_op1);
    }

    /******* SetOperandToken Factory ***************/
    private static List <String> storage = new ArrayList <String> ();

    /*TODO: Reduce all the space to one space and delete the start space and the end space (    1   2    ) -> (1 2) */
    public static void addElement (String element){
        storage.add(element);
    }

    public static SetOperandToken build (){
        SetOperandToken res = new SetOperandToken (storage);
        flush();
        return res;
    }

    public static void flush () {
        storage.clear();
    }
    
    
}