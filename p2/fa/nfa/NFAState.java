package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;

public class NFAState extends State{
    
    /* Instance Variables */
    private LinkedHashMap<Character, Set<NFAState>> transitions; //delta
    private boolean isFinal; //boolean for if the state is final

    public NFAState(String name){
        this.name = name;
        transitions = new LinkedHashMap<Character, Set<NFAState>>();
        isFinal = false;
    }

    public NFAState(String name, boolean isFinal){
        this.name = name;
        transitions = new LinkedHashMap<Character, Set<NFAState>>();
        this.isFinal = isFinal;
    }

    public boolean isFinal(){
        return isFinal;
    }

    public void addTransition(char onSymb, NFAState toState){
        if(transitions.containsKey(onSymb)){
            transitions.get(onSymb).add(toState);
        }
        else {
            Set<NFAState> tmp = new LinkedHashSet<>();
            tmp.add(toState);
            transitions.put(onSymb, tmp);
        }
    }

    public Set<NFAState> getTo(char onSymb){
        if(transitions. containsKey(onSymb)){
            return transitions.get(onSymb);
        }
        else {
            return new LinkedHashSet<>();
        }
    }
}
