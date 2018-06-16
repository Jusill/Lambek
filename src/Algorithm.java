import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 29.05.2018.
 */
public class Algorithm {

    static boolean isDerivable(Node root){

        boolean isDer = true;

        ArrayList<ArrayList<int[]>>[][][] f = (ArrayList<ArrayList<int[]>>[][][])
                new ArrayList[Tree.count_occur+1][Tree.count_occur+1][Tree.count_occur+1];


        for(int ii = 0; ii < Tree.count_occur+1; ii++){
            for(int jj = 0; jj < Tree.count_occur+1; jj++){
                for(int kk = 0; kk < Tree.count_occur+1; kk++){
                    f[ii][jj][kk] = new ArrayList<ArrayList<int[]>>();
                }
            }
        }

        ArrayList<int[]> m = new ArrayList<>();
        ArrayList<int[]> rr = new ArrayList<>();
        ArrayList<int[]> ff = new ArrayList<>();

        for(int step = 0; step < Tree.count_occur+1; step=step+2){
            for (int i = 0; i < Tree.count_occur+1; i++){
                int k = i+step;
                if(k > Tree.count_occur){
                    break;
                }
                for (int j = i; j <= k; j=j+2){

                    m.clear();
                    rr.clear();
                    ff.clear();

                    if(i == j && j == k){
                        if(Tree.find(root, j).getType() == 2){
                            m.addAll(Function.Q.get(j));
                        }
                        if(m.size() > 0){
                            f[i][j][k].add(m);
                        }
                        m.clear();

                        System.out.println("IJK: " + String.valueOf(i) + ", " + String.valueOf(j) + ", " + String.valueOf(k));
                        System.out.print("{");
                        for(ArrayList<int[]> item1 : f[i][j][k]){
                            System.out.print("{");
                            for (int[] item2 : item1){
                                System.out.println("(" + String.valueOf(item2[0]) + ", " + String.valueOf(item2[1]) + "), ");
                            }
                            System.out.print("}, ");
                        }
                        System.out.println("}\n");

                        continue;
                    }

                    if(Tree.find(root, j).getType() == 2){
                        continue;
                    } else if(Tree.find(root, i).getType() == 1 && Tree.find(root, k).getType() == 1){
                        break;
                    }

                    //situation of the first kind
                    if(Tree.find(root, i).getType() == 1){
                        System.out.println("tTTTTTTTTT");
                        for(int h = i+1; h <= j; h = h+2){
                            if((Tree.find_atom(root, i+1).getValue().charAt(0) == '~'
                                    && Tree.find_atom(root, h).getValue().charAt(0) != '~') ||
                                    (Tree.find_atom(root, i+1).getValue().charAt(0) != '~'
                                            && Tree.find_atom(root, h).getValue().charAt(0) == '~')) {
                                System.out.println("HHHHHHH");
                                for (int jj = i + 1; jj <= h - 1; jj = jj + 2) {
                                    //R'
                                    ArrayList<ArrayList<int[]>> r1 = f[h][j][k];
                                    ArrayList<ArrayList<int[]>> r2 = f[i+1][jj][h-1];

                                    m.addAll(Function.Q.get(i));

                                    for(int item : Function.V_i.get(k)){
                                        Node node = Tree.find(root, j);
                                        if(node.getCount() == item){
                                            continue;
                                        }

                                        while (node.getParent() != null){
                                            if(node.getParent().getCount() == item){
                                                m.add(new int[]{Function.b(root, i), item});
                                                break;
                                            }
                                            node = node.getParent();
                                        }
                                    }

                                    if(r1.size() > 0 && r2.size() > 0){
                                        for(int l1 = 0; l1 < r1.size(); l1++){
                                            for(int l2 = 0; l2 < r2.size(); l2++){
                                                rr.addAll(r1.get(l1));
                                                rr.addAll(r1.get(l2));
                                            }
                                            ff.addAll(rr);
                                            ff.addAll(m);
                                            f[i][j][k].add(ff);
                                            ff.clear();
                                            rr.clear();
                                        }
                                    }
                                    else if(r1.size() > 0 && r2.size() == 0){
                                        for(int l = 0; l < r1.size(); l++) {
                                            rr.addAll(r1.get(l));
                                            ff.addAll(rr);
                                            ff.addAll(m);
                                            f[i][j][k].add(ff);
                                        }
                                        ff.clear();
                                        rr.clear();
                                    }
                                    else if(r1.size() == 0 && r2.size() > 0){
                                        for(int l = 0; l < r2.size(); l++) {
                                            rr.addAll(r2.get(l));
                                            ff.addAll(rr);
                                            ff.addAll(m);
                                            f[i][j][k].add(ff);
                                        }
                                        ff.clear();
                                        rr.clear();
                                    }
                                    else if(r1.size() == 0 && r2.size() == 0){
                                        f[i][j][k].add(m);
                                        ff.clear();
                                        rr.clear();
                                    }
                                }
                            }
                        }
                    }

                    m.clear();
                    rr.clear();
                    ff.clear();

                    //situation of the second kind
                    if(Tree.find(root, k).getType() == 1){

                        for(int h = j; h <= k - 1; h = h + 2){
                            if((Tree.find_atom(root, k).getValue().charAt(0) == '~'
                                    && Tree.find_atom(root, h).getValue().charAt(0) != '~') ||
                                    (Tree.find_atom(root, k).getValue().charAt(0) != '~'
                                            && Tree.find_atom(root, h).getValue().charAt(0) == '~')) {
                                for (int jj = h + 1; jj <= k - 1; jj = jj + 2) {
                                    //R'
                                    ArrayList<ArrayList<int[]>> r1 = f[i][j][h];
                                    ArrayList<ArrayList<int[]>> r2 = f[h+1][jj][k-1];

                                    for(int ii = 0; ii < Function.Q.get(k).size(); ii++){
                                        m.add(Function.Q.get(k).get(ii));
                                    }

                                    for(int item : Function.V_i.get(i)){
                                        Node node = Tree.find(root, j);
                                        if(node.getCount() == item){
                                            continue;
                                        }

                                        while (node.getParent() != null){
                                            if(node.getParent().getCount() == item){
                                                m.add(new int[]{Function.b(root, k), item});
                                                break;
                                            }
                                            node = node.getParent();
                                        }
                                    }

                                    for(int l1 = 0; l1 < r1.size(); l1++){
                                        for(int l2 = 0; l2 < r2.size(); l2++){
                                            for(int ii = 0; ii < r1.get(l1).size(); ii++){
                                                rr.add(r1.get(l1).get(ii));
                                            }
                                            for(int ii = 0; ii < r1.get(l2).size(); ii++){
                                                rr.add(r1.get(l2).get(ii));
                                            }
                                        }
                                        for(int ii = 0; ii < rr.size(); ii++){
                                            ff.add(rr.get(ii));
                                        }
                                        for(int ii = 0; ii < m.size(); ii++){
                                            ff.add(m.get(ii));
                                        }
                                        f[i][j][k].add(ff);
                                        ff.clear();
                                        rr.clear();
                                    }

                                }
                            }
                        }

                    }
                    System.out.println("IJK: " + String.valueOf(i) + ", " + String.valueOf(j) + ", " + String.valueOf(k));
                    System.out.print("{");
                    for(ArrayList<int[]> item1 : f[i][j][k]){
                        System.out.print("{");
                        for (int[] item2 : item1){
                            System.out.println("(" + String.valueOf(item2[0]) + ", " + String.valueOf(item2[1]) + "), ");
                        }
                        System.out.print("}, ");
                    }
                    System.out.println("}\n");
                }
            }
        }

        return isDer;
    }

}
