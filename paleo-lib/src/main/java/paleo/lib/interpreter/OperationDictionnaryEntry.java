package paleo.lib.interpreter;

import java.util.Optional;
import java.util.HashMap;
import paleo.lib.token.OperandToken;
import paleo.lib.token.OperationToken;

public final class OperationDictionnaryEntry {
    private HashMap<Class <? extends OperandToken>,OperationDictionnaryEntry> evaluatorMap;
    private OperationEvaluator opEvaluator;

    public OperationDictionnaryEntry () {
        evaluatorMap = new HashMap<Class <? extends OperandToken>, OperationDictionnaryEntry>();
    }

    public boolean containsKey (Class <? extends OperandToken> entry){
        return evaluatorMap.containsKey(entry);
    }
    
    public void put (Class <? extends OperandToken> entry){
        evaluatorMap.put(entry,new OperationDictionnaryEntry ());
    }

    public OperationDictionnaryEntry next (Class <? extends OperandToken> entry){
        return evaluatorMap.get(entry);
    }
    
    public boolean isEvaluator (){
        return this.opEvaluator != null;
    }

    public void setEvaluator (OperationEvaluator evaluator){
        this.opEvaluator = evaluator;
    }

    public Optional<OperationEvaluator> getEvaluator (){
        if (opEvaluator == null)
            return Optional.empty();
        else
            return Optional.of(opEvaluator);
    }

}