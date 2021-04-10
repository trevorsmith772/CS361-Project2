package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface{
    private LinkedHashSet<NFAState> nfaStates; 
    private LinkedHashSet<Character> alphabet; // Q
    private NFAState initialState; // q0

    /**
     * NFA Constructor
     */
    public NFA(){
        alphabet = new LinkedHashSet<Character>();
        nfaStates = new LinkedHashSet<NFAState>();
        initialState = null;
    }

    /**
     * Adds a start state to the NFA.
     *  Checks if the start already exists
     *  first.
     * @param name - the name of the start state
     */
    public void addStartState(String name) {
        NFAState s = checkIfExists(name);
		if(s == null){
			s = new NFAState(name);
			addState(s.getName());
		}
		initialState = s;
    }

    /**
     * Adds a state to the NFA
     * @param name - the name of the state
     *              being added
     */
    public void addState(String name) {
        nfaStates.add(new NFAState(name));
    }

    @Override
    public void addFinalState(String name) {
        NFAState s = checkIfExists(name);
		if( s == null){
			s = new NFAState(name);
			addState(s.getName());
		} else {
			System.out.println("WARNING: A state with name " + name + " already exists in the DFA");
		}
        
    }

    /**
     * Adds a transition to the NFA
     * @param fromState - the state the transition is from
     * @param onSymb - the alphabet character the transition occurs on
     * @param toState - the state being transitioned to
     */
    public void addTransition(String fromState, char onSymb, String toState) {
        NFAState from = checkIfExists(fromState);
        NFAState to = checkIfExists(toState);
        if(from == null){
            System.err.println("ERROR: No NFA state exists with name " + fromState);
            System.exit(2);
        }else if (to == null) {
            System.err.println("ERROR: No DFA state exists with name " + toState);
            System.exit(2);
        }
        //TODO
    }

    /**
     * Getter for the states set
     * @return Set of NFA states
     */
    public Set<? extends State> getStates() {
        return nfaStates;
    }

    /**
     * Getter for final states
     * @return Set of NFA final states
     */
    public Set<? extends State> getFinalStates() {
        LinkedHashSet<NFAState> finalStates = new LinkedHashSet<>();
        for(NFAState state : nfaStates){
            if(state.isFinal()){
                finalStates.add(state);
            }
        }
        return finalStates;
    }

    /**
     * Getter for start state
     * @return the start state for the NFA
     */
    public State getStartState() {
        return initialState;
    }

    /**
     * Getter for NFA alphabet
     * @return the alphabet for the NFA
     */
    public Set<Character> getABC() {
        return alphabet;
    }

    /**
     * Computes an equivalent DFA for
     * the given NFA and returns it.
     * @return an equivalent DFA
     */
    public DFA getDFA() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Returns states that will be
     * transitioned to from a given 
     * state and alphabet character
     * @param from - the state being transitioned from
     * @param onSymb - the alphabet symbol being transitioned on
     * @return a set of states that will be transitioned to
     *  from the state on the character
     */
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Searches for states that can be reached
     *  from the start state with an empty string
     * @param s - the start state for the NFA
     * @return a set of NFA states that can be
     *  reached from the start state with epsilon
     */
    public Set<NFAState> eClosure(NFAState s) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
	 * Check if a state with such name already exists
	 * @param name
	 * @return null if no state exist, or DFAState object otherwise.
	 */
	private NFAState checkIfExists(String name){
		NFAState ret = null;
		for(NFAState s : nfaStates){
			if(s.getName().equals(name)){
				ret = s;
				break;
			}
		}
		return ret;
	}   
}
