/**
 * Ce programme permet de vérifier à l'aide d'automates si une chaine de caractère entré en paramètre est syntaxiquement correct
 *
 * @author Avril Corentin
 *
 * @version 1.0
 */

//imports
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Appli {
    /**
     * read a file and take its data line by line in an Arraylist
     * @param path the path to the file
     * @return An Arraylist with the instructions for the automaton
     */
    public static ArrayList readFile(String path){
        ArrayList<String>  instructions=new ArrayList<String>();
        try{ //try to read the file if success add to the Arraylist line by line the instructions
            FileInputStream file = new FileInputStream(path);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                instructions.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) { //if fail return a error
            e.printStackTrace();
        }
        return instructions; //return an Arraylist
    }

    /**
     * build the automaton with the contents of the file
     * @param instructions Arraylist who came from the file
     * @param chain the chain to test
     * @param name the name for the automaton
     * @return true if the chain is well formed or false if not
     */
    public static boolean CreateAutomata(ArrayList<String> instructions,String chain,String name){
        int type=0;
        Automata automata=new Automata(name); //create the automata
        for(String s: instructions){ //read the instructions
            if(s.equals("State"))          { type=1; } //if the instructions is State, it create states with the next lines until the instruction is Init
            else if(s.equals("Init"))      { type=2; } //
            else if(s.equals("Transition")){ type=3; } //same
            else if(s.equals("Final"))     { type=4; } //
            else if(type!=0) {
                switch (type) { //according to the type, it perform the corresponding action
                    case 1: //add a state
                        automata.createState(s);
                        break;
                    case 2: //set the initial state
                        for(State st : automata.getS()){
                            if(st.getName().equals(s)){ automata.setInitialState(st); }
                        }
                        break;
                    case 3: //add a transition (with the function recoTrans()
                        recoTrans(automata,s);
                        break;
                    case 4: //add a final state
                        for(State st : automata.getS()){
                            if(st.getName().equals(s)){ automata.addFinalState(st); }
                        }
                        break;
                    default: //else do nothing
                        break;
                }
            } else { //if the type is still 0, the file isn't good
                System.out.println("The "+name+" automaton file is badly written ");
                return false;
            }
        }
        return automata.verif(chain); //return the result of the verif : true -> well formed / false -> poorly formed
    }

    /**
     * Add a transition/ transitions according to the type of transition given (simple or enumeration)
     * @param automata the automaton
     * @param s the transition instruction
     */
    public static void recoTrans(Automata automata,String s){
        int type=0;
        String separateur=" ";
        String []para=s.split(separateur); //separate the string in 3 pieces (ex : E0 / 0..9 / E1)
        //3 char list for the 3 possibles types of enumeration
        char []ALPHA={'A','B','D','C','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        char []alpha={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        char []number={'0','1','2','3','4','5','6','7','8','9'};
        int index1=0; //position in the list of the first cara (2..9 -> 3)
        int index2=0; //position in the lit of the second cara (2..9 -> 10)
        if(para[1].length()>1){ //if  the transition is an enumeration
            for(int i=0;i<ALPHA.length;i++) { //test if is (X..Y)
                if(para[1].charAt(0)==ALPHA[i]){ type=1;index1=i; }
                if(para[1].charAt(3)==ALPHA[i]){ index2=i; }
            }
            for(int i=0;i<alpha.length;i++) { //test if is (x..y)
                if(para[1].charAt(0)==alpha[i]){ type=2;index1=i; }
                if(para[1].charAt(3)==alpha[i]){ index2=i; }
            }
            for(int i=0;i<number.length;i++) { //test if is (0..9) or other number
                if(para[1].charAt(0)==number[i]){ type=3;index1=i; }
                if(para[1].charAt(3)==number[i]){ index2=i; }
            }
        }
        for(State st : automata.getS()){ //create a transition for each of the element af the enumeration according to the type of enumeration (ALPHA/alpha/number) with the switch
            if(st.getName().equals(para[0])){
                for(State st1 : automata.getS()){
                    if(st1.getName().equals(para[2])){
                        switch (type){
                            case 0:
                                automata.addTransition(st,para[1].charAt(0),st1);
                                break;
                            case 1:
                                for(int i=index1;i<=index2;i++){ automata.addTransition(st,ALPHA[i],st1); }
                                break;
                            case 2:
                                for(int i=index1;i<=index2;i++){ automata.addTransition(st,alpha[i],st1); }
                                break;
                            case 3:
                                for(int i=index1;i<=index2;i++){ automata.addTransition(st,number[i],st1); }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     * show the menu when call
     */
    public static void menu(){
        System.out.println(
                "--------------- Menu of mu TP ---------------\n"+
                "1. Smiley \n"+
                "2. HH:MM \n"+
                "3. JJ/MM/AAAA\n"+
                "4. email address\n"+
                "5. polynomial\n"+
                "6. stop\n"+
                "Your choice (1-6) ?\n"+
                "I ask you after the string to analyze\n"+
                "----------------------------------------------\n");
    }

    /**
     * call the different fonctions and display the informations
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        int i=-1;
        boolean out=false;
        while(out!=true){ //loop to allow the user to try multiple time
            while (i < 0 || i > 6) { //loop for the scanner -> choice of the type of automaton
                menu();
                Scanner sc = new Scanner(System.in);
                try {
                    i = sc.nextInt();
                } catch (Exception e) { System.out.println("Wrong number, try again"); } //if the number is not between 1 and 6 display an error message and return to the beginning of th loop
                if(i>6){
                    System.out.println("Wrong number, try again");
                    Thread.sleep(2*1000);
                }
            }
            if(i==6)break;//if 6 quit
            Scanner sc = new Scanner(System.in); //new scanner for the string
            System.out.println("Please enter the string you want to test\n");
            String chaine = sc.nextLine();
            boolean result = false;
            String text="";
            switch (i) { //switch to launch the good automaton
                case 1:
                    result = CreateAutomata(readFile("data/smiley_automata.txt"),chaine,"smiley");
                    text="Your Smiley need to be one of : :-) // :-( // ;-) // :=) // :=(";
                    break;
                case 2:
                    result = CreateAutomata(readFile("data/time_automata.txt"),chaine,"hour");
                    text="Your time need to be in this form : HH:MM  (ex : 15:12)";
                    break;
                case 3:
                    result = CreateAutomata(readFile("data/date_automata.txt"),chaine,"date");
                    text="Your date need to be in this form : JJ/MM/AAAA  (ex : 18/02/2005)";
                    break;
                case 4:
                    result = CreateAutomata(readFile("data/address_automata.txt"),chaine,"address");
                    text="Your address need to be in this form :  FirstName.Name@DomainName // String@DomainName (ex : paul-annie.martin44@univ-nantes.fr)";
                    break;
                case 5:
                    result = CreateAutomata(readFile("data/polynomial_automata.txt"),chaine.replaceAll("\\s+",""),"polynomial");
                    text="Your polynomial need to be in this form : aX**2⊕bX⊕c (⊕ : + or -) spaces are allowed  (ex : 8X**2-15X+3)";
                    break;
                case 6:
                    out=true;
                    break;
                default:
                    break;
            }
            String affiche;
            if(result){
                System.out.println("Votre automate est bien formée\n\n");//display the result of the automaton
            }else{
                System.out.println("Votre automate est mal formée\n"+text+"\n\n");//display the result of the automaton
            }
            Thread.sleep(2*1000); //sleep for 2 seconds before display the menu
            i=-1;
        }
    }
}