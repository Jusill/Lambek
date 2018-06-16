import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Boris on 20.05.2018.
 */
public class Start {

    public static void main(String[] args){

        File file = new File(Config.filename);
        String str;

        try {
            if(!file.exists()){
                System.out.print("Такого файла не существует!");
                System.exit(-1);
            }

            try (BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
                str = in.readLine();
            }

        } catch(IOException e) {
            throw new RuntimeException(e);
        }

        //String mcll = Convert.convert(str);
        String mcll = "((~p+(((q&(~s+s))+~s)&s))&((((~s+s)&(~s&s))+~q)&p))";
        System.out.println("MCLL: " + mcll);

        Node root = Tree.create_tree(mcll);

        Function.make_V(root);
        Function.make_V_i(root);
        Function.make_Q(root);

        System.out.println("-----Q-----");
        //System.out.println(Function.Q.size());
        int count = 0;
        for(ArrayList<int[]> i : Function.Q){
            System.out.println(count);
            ++count;
            for(int[] ii : i){
                System.out.println(String.valueOf(ii[0]) + " " + String.valueOf(ii[1]));
            }
        }
        System.out.println("-----------");
        System.out.println(Tree.count_occur);
        System.out.println(Tree.count_atom);
        System.out.println("-----------");
        //System.out.println(String.valueOf(Tree.count_atom) + " " + String.valueOf(Tree.count_occur));
        Algorithm.isDerivable(root);
    }

}
