package fa.nfa;

import java.util.Set;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

public class NFA implements NFAInterface{
    private Set<NFAState> totalStates; // Q
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
		} else {
			System.out.println("WARNING: A state with name " + name + " already exists in the NFA");
		}
		initialState = s;
        
    }

    @Override
    public void addState(String name) {
        totalStates.add(new NFAState(name));
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
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<? extends State> getFinalStates() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public State getStartState() {
        return initialState;
    }

    @Override
    public Set<Character> getABC() {
        // TODO Auto-generated method stub
        return null;
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
		for(NFAState s : totalStates){
			if(s.getName().equals(name)){
				ret = s;
				break;
			}
		}
		return ret;
	}
    
}
