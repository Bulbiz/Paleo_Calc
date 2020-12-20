package paleo.lib.token;
import paleo.lib.interpreter.OperationDictionary;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class SetOperandToken implements OperandToken {
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