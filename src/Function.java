import java.util.ArrayList;

/**
 * Created by Boris on 20.10.2017.
 */
public class Function {

    int count = 0;
    static ArrayList<Integer> V = new ArrayList<Integer>();
    static ArrayList<ArrayList<Integer>> V_i = new ArrayList<>();
    static ArrayList<ArrayList<int[]>> Q = new ArrayList<>();

    static int b(Node root, int d){

        Node node = Tree.find(root, d);

        if(node.getType() == 1){
            return -1;
        }

        while(true) {
            if(node.getType() == 2 && node.getParent().getType() != 2){
                return node.getCount();
            }
            node = node.getParent();
        }
    }

    private int count(Node root){

        int ans = 0;

        count(root.getLeft());
        count(root.getRight());

        return ans;
    }

    static void make_V(Node root){

        if(!root.isRoot()){
            if(root.getType() == 2 && root.getParent().getType() != 2){
                //System.out.println(" " + root.getCount());
                V.add(root.getCount());
            }
        }

        if(!root.isLeaf() && root.getRight() != null){
            make_V(root.getRight());
        }
        if(!root.isLeaf() && root.getLeft() != null){
            make_V(root.getLeft());
        }
    }

    static void make_V_i(Node root){

        ArrayList<Integer> ar = new ArrayList<>();

        for(int i = 0; i < Tree.count_occur; i++){
            Node node = Tree.find(root, i);

            while(!node.isRoot()){
                for(int item:V){
                    if(node.getCount() == item){
                        //System.out.println(String.valueOf(i) + " " + String.valueOf(node.getCount()));
                        ar.add(node.getCount());
                        //System.out.print(ar.get(0));
                    }
                }
                node = node.getParent();
            }

            V_i.add((ArrayList<Integer>) ar.clone());
            //System.out.println(V_i.get(i).size());
            ar.clear();
        }
    }

    static void make_Q(Node root){

        ArrayList<int[]> Q_i = new ArrayList<>();
        //Q.add((ArrayList<int[]>) Q_i.clone());

        for (ArrayList<Integer> aV_i : V_i) {
            for (int item1 : aV_i) {
                Node node1 = Tree.find(root, item1);
                for (int item2 : aV_i) {
                    Node node2 = Tree.find(root, item2);
                    if(node1.getCount() == node2.getCount()){
                        continue;
                    }

                    while (!node2.isRoot()) {
                        node2 = node2.getParent();
                        if (node2.getCount() == node1.getCount()) {
                            Q_i.add(new int[]{item2, item1});
                            break;
                        }
                    }
                }
            }

            Q.add((ArrayList<int[]>) Q_i.clone());
            Q_i.clear();
        }

    }

}
