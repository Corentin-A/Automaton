import java.util.ArrayList;
import java.util.List;

public class Automata {
    private List<State> s;
    private List<Character>a;
    private State sO;
    private List<State> sf;
    private State delta;
    private String name;
    private static State puit;

    /**
     * Builder of an automaton
     * @param name
     */
    public Automata(String name){
        this.name=name;
        s=new ArrayList<>();
        a=new ArrayList<>();
        sf=new ArrayList<>();
    }

    /**
     * create a state
     * @param state name of the state
     * @return a state
     */
    public State createState(String state){
        State st = new State(state);
        s.add(st);
        return st;
    }

    /**
     * add a state to s
     * @param state State
     * @return boolean
     */
    public boolean addState(State state){
        if(s.contains(state)){ return false; }
        return s.add(state);
    }

    /**
     * add a transition
     * @param s0 State
     * @param cara Character of the transition
     * @param s1 State
     * @return boolean
     */
    public boolean addTransition(State s0,Character cara,State s1){
        if(!s.contains(s0) || !s.contains(s1) ){ return false; }
        if(!a.contains(cara)){a.add(cara);}
        return s0.trans(cara,s1);
    }

    /**
     * set the Initial State
     * @param state State
     * @return boolean
     */
    public boolean setInitialState(State state){
        if(sO==state){ return false; }
        sO=state;
        return true;
    }

    /**
     * add a final state
     * @param state State
     * @return boolean
     */
    public boolean addFinalState(State state){
        if(sf.contains(state)){ return false; }
        return sf.add(state);
    }

    /**
     * verify if the string is well formed with the automaton
     * @param chaine String
     * @return boolean
     */
    public boolean verif(String chaine){
        State etatc=sO;
        int i = 0;
        while((!sf.contains(etatc) && etatc != puit) && i<chaine.length()){
            etatc=etatc.getNext(chaine.charAt(i));
            i++;
        }
        if(sf.contains(etatc)){ return true; }
        return false;
    }

    public List<State> getS() { return s; }

    public List<Character> getA() { return a; }

    public State getsO() { return sO; }

    public List<State> getSf() { return sf; }

    public State getDelta() { return delta; }

    public String getName() { return name; }
}
