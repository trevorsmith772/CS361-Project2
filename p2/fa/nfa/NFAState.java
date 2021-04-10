package fa.nfa;

import java.util.LinkedHashMap;
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

}
