package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface{
    private LinkedHashSet<NFAState> nfaStates; 
    private LinkedHashSet<Character> alphabet; // Q
    private NFAState initialState; // q0

    /**
     * NFA Constructor, initializes all 
     * instance variables
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
     * being added
     */
    public void addState(String name) {
        nfaStates.add(new NFAState(name));
    }

    /**
     * Adds a final state to the NFA
     * @param name - the name of the final 
     * state to be added
     */
    public void addFinalState(String name) {
        NFAState state = new NFAState(name, true);
        nfaStates.add(state);
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
        from = getState(fromState);
        to = getState(toState);
        from.addTransition(onSymb,to);
        if(!alphabet.contains(onSymb) && onSymb != 'e'){
            alphabet.add(onSymb);
        }
    }

    /**
     * Helper method to return a state object
     *  given a state name
     * @param stateName - the name of a state
     * @return the corresponding state object
     */
    private NFAState getState(String stateName){
        NFAState state = null;
        for(NFAState nfaState : nfaStates){
            if(stateName.equals(nfaState.getName())){
                state = nfaState;
                break;
            }
        }
        return state;
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
        /* Initialize method variables */
        DFA dfa = new DFA();
        Map<Set<NFAState>, String> passedStates = new LinkedHashMap<>();
        Set<NFAState> states = eClosure(initialState);
        LinkedList<Set<NFAState>> queue = new LinkedList<>();

        passedStates.put(states, states.toString());
        queue.add(states);
        dfa.addStartState(passedStates.get(states));
        while(!queue.isEmpty()){
            states = queue.poll();
            for(char c : alphabet){
                LinkedHashSet<NFAState> tmp1 = new LinkedHashSet<>();
                for(NFAState state : tmp1){
                    tmp1.addAll(state.getTo(c));
                }
                LinkedHashSet<NFAState> tmp2 = new LinkedHashSet<>();
                for(NFAState state : tmp1){
                    tmp2.addAll(eClosure(state));
                }
                if(!passedStates.containsKey(tmp2)){
                    passedStates.put(tmp2, tmp2.toString());
                    queue.add(tmp2);
                    if(containsFinalState(tmp2)){
                        dfa.addFinalState(passedStates.get(tmp2));
                    }
                    else {
                        dfa.addState(passedStates.get(tmp2));
                    }
                }
                dfa.addTransition(passedStates.get(states), c, passedStates.get(tmp2));
            }
        }
        return dfa;
    }

    private boolean containsFinalState(LinkedHashSet<NFAState> states) {
        boolean contains = false;
        for(NFAState state : states){
            if(state.isFinal()){
                contains = true;
                break;
            }
        }
        return contains;
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
        
        return from.getTo(onSymb);
    }

    /**
     * Searches for states that can be reached
     *  from the start state with an empty string.
     *  This method uses an internal depth-first
     *  search.
     * @param s - the start state for the NFA
     * @return a set of NFA states that can be
     *  reached from the start state with epsilon
     */
    public Set<NFAState> eClosure(NFAState s) {
        LinkedHashSet<NFAState> state = new LinkedHashSet<>();
        return search(state, s);
    }


    private Set<NFAState> search(LinkedHashSet<NFAState> fState, NFAState tState){
        LinkedHashSet<NFAState> visitedStates = fState;
        LinkedHashSet<NFAState> eClosureStates = new LinkedHashSet<>();

        eClosureStates.add(tState);
        if(!tState.getTo('e').isEmpty() && !visitedStates.contains(tState)){
            visitedStates.add(tState);
            for(NFAState nfaState : tState.getTo('e')){
                eClosureStates.addAll(search(visitedStates, nfaState));
            }
        }
        return eClosureStates;
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
