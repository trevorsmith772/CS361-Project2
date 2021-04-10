package fa.nfa;

import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface{
    private LinkedHashSet<NFAState> nfaStates; 
    private LinkedHashSet<Character> alphabet; // Q
    private NFAState initialState; // q0

    public NFA(){
        // TODO Constructor
    }

    @Override
    public void addStartState(String name) {
        NFAState s = checkIfExists(name);
		if(s == null){
			s = new NFAState(name);
			addState(s.getName());
		}
		initialState = s;
    }

    @Override
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

    @Override
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

        
    }

    @Override
    public Set<? extends State> getStates() {
        return nfaStates;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        LinkedHashSet<NFAState> finalStates = new LinkedHashSet<>();
        for(NFAState state : nfaStates){
            if(state.isFinal()){
                finalStates.add(state);
            }
        }
        return finalStates;
    }

    @Override
    public State getStartState() {
        return initialState;
    }

    @Override
    public Set<Character> getABC() {
        return alphabet;
    }

    @Override
    public DFA getDFA() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<NFAState> getToState(NFAState from, char onSymb) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
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
