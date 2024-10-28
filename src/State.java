import java.util.*;

public class State implements Comparable<State>
{
    private int f, h, g;
    private State father;
    private int totalTime;

    public boolean goingLeft;

    public ArrayList<Integer> rightSide = new ArrayList<>();
    public ArrayList<Integer> leftSide = new ArrayList<>();

    //constructor - fill with arguments if necessary
    public State(boolean hasArgs, ArrayList<Integer> givenArgs)
    {
        this.f = 0;
        this.h = 0;
        this.g = 0;
        this.father = null;
        this.totalTime = 0;

        this.goingLeft = true; //true otan pane aristera, false otan pane deksia

        if(!hasArgs) {

            rightSide.add(1);
            rightSide.add(3);
            rightSide.add(6);
            rightSide.add(8);
            rightSide.add(12);


            //rightSide.add(8);
            //rightSide.add(12);
            setH(countInside());
            //rightSide.add(8);
            //rightSide.add(12);
        } else {
            for (int i : givenArgs) {
                rightSide.add(i);
            }
        }
    }

    // copy constructor
    public State(State s)
    {
        for (int i : s.rightSide) {
            this.rightSide.add(i);
        }
        for (int i : s.leftSide) {
            this.leftSide.add(i);
        }

        this.f = s.f;
        this.h = s.h;
        this.g = s.g;
        this.father = null;
        this.totalTime = s.totalTime;
        this.goingLeft = s.goingLeft;
    }

    public boolean getGoingLeft()
    {
        return this.goingLeft;
    }
    public ArrayList<Integer> getRightSide()
    {
        return this.rightSide;
    }
    public ArrayList<Integer> getLeftSide()
    {
        return this.leftSide;
    }


    public int getF()
    {
        return this.f;
    }

    public int getG()
    {
        return this.g;
    }

    public int getH()
    {
        return this.h;
    }

    public State getFather()
    {
        return this.father;
    }

    public void setF(int f)
    {
        this.f = f;
    }

    public void setG(int g)
    {
        this.g = g;
    }

    public void setH(int h)
    {
        this.h = h;
    }

    public void setFather(State f)
    {
        this.father = f;
    }

    public int getTotalTime()
    {
        return this.totalTime;
    }

    public void setTotalTime(int time)
    {
        this.totalTime = time;
    }



    public void print() {

        System.out.println("-------------------------------------");
        System.out.println("                  |                  ");
        if (leftSide.isEmpty()) {
            System.out.print("                  ");
            System.out.print("|");
        } else {
            for(int j = 1; j < 18 - (leftSide.size() * 2); j++)
                System.out.print(" ");
            for (int i : leftSide) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.print("|");
        }

        if (rightSide.isEmpty()) {
            System.out.println("                  ");
        } else {
            for (int i : rightSide) {
                System.out.print(" ");
                System.out.print(i);
            }
        }
        System.out.println(" ");




        System.out.println("                  |                  ");



        System.out.println("-------------------------------------");
    }

    public ArrayList<State> getChildren() {

        int x = 0;
        int y = 0;
        ArrayList<Integer> pairs = new ArrayList<>();
        ArrayList<State> children = new ArrayList<>();
        //System.out.println(this.makePairs());
        if(this.goingLeft) {
            pairs = this.makePairs();
            for (int k = 0; k < pairs.size(); k += 2) {
                State child = new State(this);  // very important to create a copy of current state before each move.
                x = pairs.get(k);
                y = pairs.get(k + 1);
                child.setFather(this);
                child.evaluate(x, y, this.goingLeft);
                children.add(child);
                //System.out.println(k);

            }
        } else {


            for (int k = 0; k < this.leftSide.size(); k++) {
                State child = new State(this);  // very important to create a copy of current state before each move.
                child.setFather(this);
                x = this.leftSide.get(k);
                //System.out.println(k + "," + x);
                child.evaluate(x, 0, this.goingLeft);
                children.add(child);
                //System.out.println(k);

            }
            /*System.out.println("ola pane good");
            child.setFather(this);
            child.evaluate(x, 0, this.goingLeft);*/
        }
        return children;
    }

    public void evaluate(int x, int y, boolean go_left)
    {
        int weight = 0;
        //System.out.println("mphka");
        //System.out.println(this.getFather().goingLeft);


        if (go_left) {

            weight = Math.max(x,y); //weight of the edge (ex: [1,3], Weight = 3)

            this.rightSide.remove(Integer.valueOf(x));
            this.rightSide.remove(Integer.valueOf(y));

            this.leftSide.add(x);
            this.leftSide.add(y);

            /*this.setG(this.getFather().getTotalTime() + weight); //tsekarei to edge me ton patera tou kai pairnei to totaltime poy eixe
            this.setH(this.countInside());
            this.setF(this.getH() + this.getG());
            this.setTotalTime(this.getG());*/

            //diorthwsh gia eksetash


            this.goingLeft = false;


        } else {

            //System.out.println("Good 2");

            //System.out.println("Arxika: " + this.leftSide);
            weight = x;

            this.leftSide.remove(Integer.valueOf(weight));
            this.rightSide.add(weight);

            //System.out.print("Meta: " + this.leftSide);
            //System.out.println(this.rightSide);

            this.goingLeft = true;


        }

        this.setG(this.getFather().getTotalTime() + weight); //tsekarei to edge me ton patera tou kai pairnei to totaltime poy eixe
        this.setH(this.countInside());
        this.setF(this.getH() + this.getG());
        this.setTotalTime(this.getG());



    }

    public int countInside()
    {
        int counter = 0;
        for (int i : this.rightSide) {
            counter += i;
        }
        return counter;
    }

    /* Dexetai mia lista */
    public ArrayList<Integer> makePairs() {
        ArrayList<Integer> pairs = new ArrayList<Integer>();
        for (int i = 0; i < this.rightSide.size(); i++) {
            for (int j = i + 1; j < this.rightSide.size(); j++) {
                pairs.add(rightSide.get(i));
                pairs.add(rightSide.get(j));
            }
        }
        return pairs;
    }


    public boolean isFinal() {

        if (this.rightSide.isEmpty()) {
            //System.out.println("its empty");
            return true;
        }

        return false;

    }

    @Override
    public boolean equals(Object obj) {

        if(this.getF() != ((State)obj).getF()) return false;
        if(this.getG() != ((State)obj).getG()) return false;
        if(this.getH() != ((State)obj).getH()) return false;
        if(this.getGoingLeft() != ((State)obj).getGoingLeft()) return false;
        if(this.getTotalTime() != ((State)obj).getTotalTime()) return false;

        for(int i = 0; i < this.rightSide.size(); i++)
        {
            if(this.rightSide.get(i) != ((State)obj).rightSide.get(i))
            {
                return false;
            }
        }

        for(int i = 0; i < this.leftSide.size(); i++)
        {
            if(this.leftSide.get(i) != ((State)obj).leftSide.get(i))
            {
                return false;
            }
        }
        return true;

    }

    @Override
    public int hashCode() {
        int min = 0;
        int max = 9999;
        int rand = (int)Math.floor(Math.random() * (max - min + 1) + min);
        return rand;
    }

    @Override
    public int compareTo(State s)
    {
        return Double.compare(this.f, s.getF()); // compare based on the heuristic score.
    }
}
