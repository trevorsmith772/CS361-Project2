package fa.nfa;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import fa.State;
import fa.dfa.DFA;

/**
 * Description: Implementation of a nondeterministic finite automoton. Used in
 * combination with NFAState to create an NFA and also convert to a DFA.
 * 
 * @author Trevor Smith (trevorsmith772)
 * @author Berto Cisneros (bertocisneros)
 * @author Brandon Mattaini (brandonmattaini) Date: April 11, 2021
 */
public class NFA implements NFAInterface {
    private LinkedHashSet<NFAState> nfaStates; // Q
    private LinkedHashSet<Character> alphabet; // sigma
    private NFAState initialState; // q0

    /**
     * NFA Constructor, initializes all instance variables
     */
    public NFA() {
        alphabet = new LinkedHashSet<Character>();
        nfaStates = new LinkedHashSet<NFAState>();
        initialState = null;
    }

    /**
     * Adds a start state to the NFA. Checks if the start already exists first.
     * 
     * @param name - the name of the start state
     */
    public void addStartState(String name) {
        /* Check if state exists */
        NFAState state = getState(name);
        if (state == null) {
            state = new NFAState(name);
            nfaStates.add(state);
        }
        initialState = state;
    }

    /**
     * Adds a state to the NFA
     * 
     * @param name - the name of the state being added
     */
    public void addState(String name) {
        nfaStates.add(new NFAState(name));
    }

    /**
     * Adds a final state to the NFA
     * 
     * @param name - the name of the final state to be added
     */
    public void addFinalState(String name) {
        NFAState state = new NFAState(name, true); //Using alternate constructor
        nfaStates.add(state);
    }

    /**
     * Adds a transition to the NFA
     * 
     * @param fromState - the state the transition is from
     * @param onSymb    - the alphabet character the transition occurs on
     * @param toState   - the state being transitioned to
     */
    public void addTransition(String fromState, char onSymb, String toState) {
        /* Convert state strings to actual state objects */
        NFAState from = getState(fromState);
        NFAState to = getState(toState);

        from.addTransition(onSymb, to);
        if (!alphabet.contains(onSymb) && onSymb != 'e') {  //Add symbol to alphabet if not already existing
            alphabet.add(onSymb);
        }
    }

    /**
     * Helper method to return a state object given a state name
     * 
     * @param stateName - the name of a state
     * @return the corresponding state object
     */
    private NFAState getState(String stateName) {
        NFAState state = null;
        for (NFAState nfaState : nfaStates) {
            if (stateName.equals(nfaState.getName())) {
                state = nfaState;
                break;
            }
        }
        return state;
    }

    /**
     * Getter for the states set
     * 
     * @return Set of NFA states
     */
    public Set<? extends State> getStates() {
        return nfaStates;
    }

    /**
     * Getter for final states
     * 
     * @return Set of NFA final states
     */
    public Set<? extends State> getFinalStates() {
        LinkedHashSet<NFAState> finalStates = new LinkedHashSet<>();
        for (NFAState state : nfaStates) {
            if (state.isFinal()) {
                finalStates.add(state);
            }
        }
        return finalStates;
    }

    /**
     * Getter for start state
     * 
     * @return the start state for the NFA
     */
    public State getStartState() {
        return initialState;
    }

    /**
     * Getter for NFA alphabet
     * 
     * @return the alphabet for the NFA
     */
    public Set<Character> getABC() {
        return alphabet;
    }

    /**
     * Computes an equivalent DFA for the given NFA and returns it. Uses a breadth
     * first search using a queue structure
     * 
     * @return an equivalent DFA
     */
    public DFA getDFA() {
        /* Initialize method variables */
        DFA dfa = new DFA();
        //keeps track of states that have already been passed
        Map<Set<NFAState>, String> passedStates = new LinkedHashMap<>();
        //Get eclosure for start state
        Set<NFAState> states = eClosure(initialState);
        //queue for BFS search
        LinkedList<Set<NFAState>> queue = new LinkedList<>();

        passedStates.put(states, states.toString());
        queue.add(states);
        dfa.addStartState(passedStates.get(states));

        while (!queue.isEmpty()) {
            states = queue.poll();  //retrieves and removes the head of the list
            
            for (char c : alphabet) { //explore each symbol transition for state
                LinkedHashSet<NFAState> tmp1 = new LinkedHashSet<>();
                for (NFAState state : states) {
                    tmp1.addAll(state.getTo(c));
                }
                LinkedHashSet<NFAState> tmp2 = new LinkedHashSet<>();
                for (NFAState state : tmp1) {
                    tmp2.addAll(eClosure(state));
                }
                if (!passedStates.containsKey(tmp2)) {
                    passedStates.put(tmp2, tmp2.toString());
                    queue.add(tmp2);
                    if (containsFinalState(tmp2)) {
                        dfa.addFinalState(passedStates.get(tmp2));
                    } else {
                        dfa.addState(passedStates.get(tmp2));
                    }
                }
                //add transition to dfa
                dfa.addTransition(passedStates.get(states), c, passedStates.get(tmp2));
            }
        }
        return dfa;
    }

    /**
     * Helper method to check if a set of states contains a final state
     * 
     * @param states - the set of states to check
     * @return a boolean whether or not the set contains a final state
     */
    private boolean containsFinalState(LinkedHashSet<NFAState> states) {
        boolean contains = false;
        for (NFAState state : states) {
            if (state.isFinal()) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
     * Returns states that will be transitioned to from a given state and alphabet
     * character
     * 
     * @param from   - the state being transitioned from
     * @param onSymb - the alphabet symbol being transitioned on
     * @return a set of states that will be transitioned to from the state on the
     *         character
     */
    public Set<NFAState> getToState(NFAState from, char onSymb) {

        return from.getTo(onSymb);
    }

    /**
     * Looks for states that can be reached from the start state using only epsilon
     * 
     * @param s - the start state for the NFA
     * @return a set of NFA states that can be reached from the start state with
     *         epsilon
     */
    public Set<NFAState> eClosure(NFAState s) {
        LinkedHashSet<NFAState> state = new LinkedHashSet<>();
        return search(state, s);
    }

    /**
     * Conducts a depth first search and recursion to search through the states for
     * the eclosure method.
     * 
     * @param fState - from state
     * @param tState - to state
     * @return a set of eclosure states
     */
    private Set<NFAState> search(LinkedHashSet<NFAState> fState, NFAState tState) {
        LinkedHashSet<NFAState> visitedStates = fState;
        LinkedHashSet<NFAState> eClosureStates = new LinkedHashSet<>();

        eClosureStates.add(tState);
        if (!tState.getTo('e').isEmpty() && !visitedStates.contains(tState)) {
            visitedStates.add(tState);
            for (NFAState nfaState : tState.getTo('e')) {
                eClosureStates.addAll(search(visitedStates, nfaState));
            }
        }
        return eClosureStates;
    }
}
