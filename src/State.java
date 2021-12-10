import java.util.HashMap;
import java.util.Objects;

public class State {
    private HashMap<Character, State> tr;
    private static State puit;
    private String name;

    /**
     * builder of state
     * @param name name of the state
     */
    public State(String name){
        this.name=name;
        this.tr=new HashMap<>();
    }

    /**
     * Override of the equals method
     * @return true / false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(name, state.name);
    }
    public boolean trans(Character c, State st){
        if(tr.isEmpty()){
            tr.put(c,st);
            return true;
        }
        else {
            if(tr.containsKey(c)){ return false; }
            else {
                tr.put(c,st);
                return true;
            }
        }
    }

    /**
     * Override of hashCode
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * return the next State
     * @param c
     * @return State
     */
    public State getNext(Character c){
        State etatsuiv=puit;
        if(this.getTr().keySet().contains(c)){
            etatsuiv=this.getTr().get(c);
        }
        return etatsuiv;
    }

    /**
     * getter of name
     * @return String
     */
    public String getName(){
        return this.name;
    }

    /**
     * getter of Tr
     * @return HashMap
     */
    public HashMap<Character, State> getTr() {return tr;}
}


