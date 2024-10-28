import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Main
{
    public static void main(String[] args)
    {
        int iter;
        int people = 0;
        //int time = 30;
        ArrayList<Integer> arguments = new ArrayList<>();
        boolean hasArgs = false;
        int correctArgs = 0;
        if (args.length <= 0) {
            System.out.println("This program has no args");
        } else {
            System.out.println("This program has args!");
            //time = 0;
            for(int l = 0; l < args.length; l++) {
                iter = Integer.parseInt(args[l]);
                if (l == 0)
                    people = iter;
                else {
                    correctArgs++;
                    //time += iter;
                    arguments.add(iter);
                }
            }
            if (correctArgs > people) {
                System.out.println("Incorrect args, the individuals are more than indicated...");
                return;
            }
            if (correctArgs < people) {
                System.out.println("Incorrect args, the individuals are not as many as indicated...");
                return;
            }
            //System.out.println("time is: " + time);
            hasArgs = true;
        }

        State initialState = new State(hasArgs, arguments);  // hasArgs = false tote arxikh katastash opws to paradeigma
        initialState.print();


        /* written code (ignore)

        State copytester = new State(initialState);

        for(int i = 0; i < initialState.rightSide.size(); i++)
        {
            copytester.rightSide.add(i);
        }


        for(int i = 0; i < initialState.rightSide.size(); i++)
        {
            if(initialState.rightSide.get(i) != copytester.rightSide.get(i))
            {
                System.out.println("Not Equal. First: " + initialState.rightSide.get(i) + "Second: " + copytester.rightSide.get(i));
            }
        }

        for(int i = 0; i < initialState.rightSide.size(); i++)
        {
            System.out.println(initialState.rightSide.get(i));
        }

        State copytester = new State(initialState);

        ArrayList<State> paidia = initialState.getChildren();
        Collections.sort(paidia);
        for (State i : paidia) {

            System.out.println(i.getF() + "<---- h F einai ");
        }


        ArrayList<State> paidia = initialState.getChildren(1);
        Collections.sort(paidia);
        Collections.reverse(paidia);
        for (State i : paidia) {
            System.out.println(i.getH());
        }


        ArrayList<Integer> goga = initialState.makePairs();
        System.out.println(goga);


        end of written code */


        SpaceSearcher searcher = new SpaceSearcher();
        long start = System.currentTimeMillis();
        // use the manhattan distance.
        State terminalState = searcher.Astar(initialState);
        long end = System.currentTimeMillis();
        if(terminalState == null) System.out.println("Could not find a solution.");
        else
        {
            // print the path from beggining to start.
            State temp = terminalState; // begin from the end.
            ArrayList<State> path = new ArrayList<>();
            path.add(terminalState);
            while(temp.getFather() != null) // if father is null, then we are at the root.
            {
                path.add(temp.getFather());
                temp = temp.getFather();
            }
            // reverse the path and print.
            Collections.reverse(path);
            for(State item: path)
            {
                item.print();
            }
            System.out.println();
            System.out.println("Search time:" + (double)(end - start) / 1000 + " sec.");  // total time of searching in seconds.
            System.out.println();
            //time = (time - terminalState.getTotalTime());
            System.out.print("Everyone crossed in " + terminalState.getTotalTime() + " Minutes");
        }
    }
}