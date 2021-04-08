package fa.nfa;

import java.util.LinkedHashMap;
import java.util.Set;

import fa.State;

public class NFAState extends State{
    
    /* Instance Variables */
    private LinkedHashMap<Character, Set<NFAState>> transitions; //delta

    public NFAState(String name){
        this.name = name;
    }

}
