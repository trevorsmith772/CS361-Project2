package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import fa.State;

/**
 * Description: Implementation of an NFA state. Used by the NFA class.
 * 
 * @author Trevor Smith (trevorsmith772)
 * @author Berto Cisneros (bertocisneros)
 * @author Brandon Mattaini (brandonmattaini) Date: April 11, 2021
 */
public class NFAState extends State {

    /* Instance Variables */
    private LinkedHashMap<Character, Set<NFAState>> transitions; // delta
    private boolean isFinal; // boolean for if the state is final

    /**
     * Default constructor, sets isFinal to false
     * 
     * @param name - name of the state
     */
    public NFAState(String name) {
        this.name = name;
        transitions = new LinkedHashMap<Character, Set<NFAState>>();
        isFinal = false;
    }

    /**
     * Constructor to create final state
     * 
     * @param name    - name of the state
     * @param isFinal - boolean value for if the state is final or not
     */
    public NFAState(String name, boolean isFinal) {
        this.name = name;
        transitions = new LinkedHashMap<Character, Set<NFAState>>();
        this.isFinal = isFinal;
    }

    /**
     * Getter method for isFinal variable
     * 
     * @return the boolean isFinal variable
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Adds a transition to a state
     * 
     * @param onSymb  - the alphabet symbol in which the transition occurs on
     * @param toState - the state being transitioned to
     */
    public void addTransition(char onSymb, NFAState toState) {
        if (transitions.containsKey(onSymb)) {
            transitions.get(onSymb).add(toState);
        } else {
            Set<NFAState> tmp = new LinkedHashSet<>();
            tmp.add(toState);
            transitions.put(onSymb, tmp);
        }
    }

    /**
     * Gets the state that would be transitioned to given a start state and a
     * transition symbol
     * 
     * @param onSymb - the alphabet symbol in which the transition occurs on
     * @return the state that would be transitioned to
     */
    public Set<NFAState> getTo(char onSymb) {
        if (transitions.containsKey(onSymb)) {
            return transitions.get(onSymb);
        } else {
            return new LinkedHashSet<>();
        }
    }
}
