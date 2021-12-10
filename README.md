# Automata
## How to use
### Summary
- [launch](#launch)
- [Creating a automaton file](#Structure-of-an-Automaton-file)
- [Add a new Automaton](#Create-and-use-a-new-automaton)

### launch
Compile all the class in a terminal or load the project on a IDE, and launch the Appli.java file,
it will display the menu
```java
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
```
### Structure of an Automaton file
```
State
.
. a state (ex : E0) one by line, a letter and a number without spaces
.
Init
. Initial State (ex : E0) only one (one of the ones in Init)
Transition
.
. a transiton (ex : E0 1 E1 // E0 0..9 E1)one by line, 2 type : State1 X State2 or State1 X..Y State2 (X/Y : letter/number/symbol)
.
Final
. a state (ex : E5)one or more (one by line)
```
#### Example (smiley automaton file):
```
State
E0
E1
E2
E3
E4
E5
Init
E0
Transition
E0 : E1
E0 : E1
E1 - E3
E1 = E3
E2 - E3
E3 ) E4
E3 ( E4
Final
E4
```
### Create and use a new automaton
coming soon