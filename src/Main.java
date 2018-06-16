import java.util.ArrayList;

/**
 * Created by Boris on 16.06.2018.
 */
public class Main {

    private static void print1(ArrayList<int[]> m) {

        System.out.print("{");

        for(int i = 0; i < m.size(); i++) {
            System.out.print("(" + m.get(i)[0] + "," + m.get(i)[1] + ")");
            if(i != m.size()-1){
                System.out.print(", ");
            }
        }

        System.out.print("}");

    }

    private static void print2(ArrayList<Integer> m) {

        System.out.print("{");

        for(int i = 0; i < m.size(); i++) {
            System.out.print(m.get(i));
            if(i != m.size()-1){
                System.out.print(", ");
            }
        }

        System.out.print("}");

    }

    /*
    private static void print(ArrayList<ArrayList<int[]>> m) {

        System.out.print("{");

        for(int i = 0; i < m.size(); i++) {
            System.out.print("{");
            for(int j = 0; j < m.get(i).size(); j++){
                System.out.print("(" + m.get(j).get(i)[0] + "," + m.get(j).get(i)[1] + ")");
                if(j != m.get(i).size()-1){
                    System.out.print(", ");
                }
            }
            if(i != m.size()-1){
                System.out.print(", ");
            }
        }

        System.out.print("}");

    }
    */
    public static void main(String[] args){
        String mcll = "((~p+(((q&(~s+s))+~s)&s))&((((~s+s)&(~s&s))+~q)&p))";
        FLambek lambek = new FLambek(mcll);

        System.out.print(lambek.isDerivable());
    }

}
