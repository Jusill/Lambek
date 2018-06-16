import java.util.ArrayList;

import static java.lang.Character.isLetter;

/**
 * Created by Boris on 15.06.2018.
 */
public class FLambek {

    private int count_occur = 0, count_atom = 0;
    private Node root = null;

    private ArrayList<Integer> V = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> V_i = new ArrayList<>();
    private ArrayList<ArrayList<int[]>> Q = new ArrayList<>();

    public FLambek(String str){
        Node root = create_tree(str);
        make_V(root);
        make_V_i();
        make_Q();
    }

    public int getCount_occur(){ return count_occur; }
    public int getCount_atom(){ return count_atom; }
    public Node getRoot(){ return root; }
    public Node getOccurrence(int count){ return getOccurrence_rec(this.root, count); }
    public Node getAtom(int count) { return getAtom_rec(this.root, count); }
    public ArrayList<Integer> getV(int i){ return V_i.get(i); }
    public ArrayList<Integer> getV(){ return V; }
    public ArrayList<int[]> getQ(int i){ return Q.get(i); }

    public int b(final int d){

        Node node = getOccurrence(d);

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

    private Node getAtom_rec(final Node root, final int count) {

        Node atom = new Node(root);

        if(atom.getCount() == count && atom.isLeaf()){
            return atom;
        }
        else {
            if(atom.getCount() <= count){
                if(atom.getRight() != null){
                    atom = getAtom_rec(atom.getRight(), count);
                }
            }
            else {
                if(atom.getLeft() != null){
                    atom = getAtom_rec(atom.getLeft(), count);
                }
            }
        }

        return atom;
    }

    private Node getOccurrence_rec(final Node root, final int count){

        Node occurrence = new Node(root);

        if(occurrence.getCount() == count){
            return occurrence;
        }
        else {
            if(occurrence.getCount() < count){
                if(occurrence.getRight() != null){
                    occurrence = getOccurrence_rec(occurrence.getRight(), count);
                }
            }
            else {
                if(occurrence.getLeft() != null){
                    occurrence = getOccurrence_rec(occurrence.getLeft(), count);
                }
            }
        }

        return occurrence;
    }

    private Node create_tree(final String str){
        Node root = new Node();
        root.set("&", 0);
        root.createRight(create_tree_rec(convert_lin(str)));
        this.root = root;
        return root;
    }

    static private Node create_tree_rec(final ArrayList<Linear> str){

        Node left, right, n = new Node();
        int weight = 0;

        if (str.get(0).str.equals("(") && str.get(str.size()-1).str.equals(")")){
            str.remove(0);
            str.remove(str.size()-1);
        }

        for(int i = 0; i < str.size(); i++) {
            if (str.get(i).str.equals("(")) {
                weight++;
            } else if (str.get(i).str.equals(")")){
                weight--;
            } else if(str.get(i).str.equals("&")) {
                if (weight == 0) {

                    n.set("&", str.get(i).num);

                    ArrayList<Linear> left_l = new ArrayList<>();
                    ArrayList<Linear> right_r = new ArrayList<>();

                    for(int j = 0; j < i; j++){
                        left_l.add(str.get(j));
                    }

                    for(int j = i + 1; j < str.size(); j++){
                        right_r.add(str.get(j));
                    }

                    left = create_tree_rec(left_l);
                    right = create_tree_rec(right_r);
                    n.createLeft(left);
                    n.createRight(right);
                    return n;
                }
            } else if(str.get(i).str.equals("+")) {
                if (weight == 0) {

                    n.set("+", str.get(i).num);

                    ArrayList<Linear> left_l = new ArrayList<>();
                    ArrayList<Linear> right_r = new ArrayList<>();

                    for(int j = 0; j < i; j++){
                        left_l.add(str.get(j));
                    }

                    for(int j = i + 1; j < str.size(); j++){
                        right_r.add(str.get(j));
                    }

                    left = create_tree_rec(left_l);
                    right = create_tree_rec(right_r);
                    n.createLeft(left);
                    n.createRight(right);
                    return n;
                }
            }
        }
        n.set(str.get(0).str, str.get(0).num);
        return n;
    }

    private class Linear {
        String str;
        int num = -1;

        Linear(String str, int num){
            this.str = str;
            this.num = num;
        }
    }

    private ArrayList<Linear> convert_lin(final String str){

        ArrayList<Linear> l = new ArrayList<>();
        StringBuilder s = new StringBuilder(str);
        StringBuilder ss = new StringBuilder("");

        for(int i = 0; i < str.length(); i++){

            if(s.charAt(i) == '(' || s.charAt(i) == ')'){
                if(ss.length() == 0){
                    l.add(new Linear(String.valueOf(s.charAt(i)), -1));
                } else {
                    l.add(new Linear(String.valueOf(ss.toString()), this.count_atom++));
                    l.add(new Linear(String.valueOf(s.charAt(i)), -1));
                    ss.delete(0, ss.length());
                }
            } else if(s.charAt(i) == '~' || isLetter(s.charAt(i))) {
                ss.append(s.charAt(i));
            } else if(ss.length() == 0){
                l.add(new Linear(String.valueOf(s.charAt(i)), ++this.count_occur));
            } else {
                l.add(new Linear(String.valueOf(ss.toString()), this.count_atom++));
                l.add(new Linear(String.valueOf(s.charAt(i)), ++this.count_occur));
                ss.delete(0, ss.length());
            }
        }

        if(ss.length() != 0){
            l.add(new Linear(ss.toString(), this.count_atom));
        }
        count_occur++;

        return l;
    }

    private void make_V(final Node root){

        if(!root.isRoot()){
            if(root.getType() == 2 && root.getParent().getType() != 2){
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

    private void make_V_i(){

        ArrayList<Integer> ar = new ArrayList<>();

        for(int i = 0; i < count_occur; i++){
            Node node = getOccurrence(i);

            while(!node.isRoot()){
                for(int item:V){
                    if(node.getCount() == item){
                        ar.add(node.getCount());
                    }
                }
                node = node.getParent();
            }

            V_i.add((ArrayList<Integer>) ar.clone());
            ar.clear();
        }
    }

    private void make_Q(){

        ArrayList<int[]> Q_i = new ArrayList<>();
        //Q.add((ArrayList<int[]>) Q_i.clone());

        for (ArrayList<Integer> aV_i : V_i) {
            for (int item1 : aV_i) {
                Node node1 = getOccurrence(item1);
                for (int item2 : aV_i) {
                    Node node2 = getOccurrence(item2);
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

    /**
     * Algorithm solving derivable in Lambek calculus
     * @return
     */
    public boolean isDerivable(){

        boolean isDer = true;

        ArrayList<ArrayList<int[]>>[][][] f = (ArrayList<ArrayList<int[]>>[][][])
                new ArrayList[count_occur][count_occur][count_occur];


        for(int ii = 0; ii < count_occur; ii++){
            for(int jj = 0; jj < count_occur; jj++){
                for(int kk = 0; kk < count_occur; kk++){
                    f[ii][jj][kk] = new ArrayList<>();
                }
            }
        }

        for(int ii = 0; ii < count_occur; ii++){
            System.out.println(getOccurrence(ii).getValue());
            if(getOccurrence(ii).getType() == 1){
                f[ii][ii][ii].add(getQ(ii));
            }
        }

        ArrayList<int[]> m = new ArrayList<>();
        ArrayList<int[]> rr = new ArrayList<>();
        ArrayList<int[]> ff = new ArrayList<>();

        for(int step = 0; step < count_occur; step=step+2){
            for (int i = 0; i < count_occur; i++){
                int k = i+step;
                if(k >= count_occur){
                    break;
                }
                for (int j = i; j <= k; j = j + 2){

                    m.clear();
                    rr.clear();
                    ff.clear();

                    if(i == j && j == k){
                        continue;
                    }

                    if(getOccurrence(j).getType() == 2){
                        continue;
                    } else if(getOccurrence(i).getType() == 1 && getOccurrence(k).getType() == 1){
                        break;
                    }

                    //situation of the first kind
                    if(getOccurrence(i).getType() == 1){
                        System.out.println("tTTTTTTTTT");
                        for(int h = i+1; h <= j; h = h+2){
                            if((getAtom(i+1).getValue().charAt(0) == '~'
                                    && getAtom(h).getValue().charAt(0) != '~') ||
                                    (getAtom(i+1).getValue().charAt(0) != '~'
                                            && getAtom(h).getValue().charAt(0) == '~')) {
                                System.out.println("HHHHHHH");
                                for (int jj = i + 1; jj <= h - 1; jj = jj + 2) {
                                    //R'
                                    ArrayList<ArrayList<int[]>> r1 = f[h][j][k];
                                    ArrayList<ArrayList<int[]>> r2 = f[i+1][jj][h-1];

                                    m.addAll(getQ(i));

                                    for(int item : getV(k)){
                                        Node node = getOccurrence(j);
                                        if(node.getCount() == item){
                                            continue;
                                        }

                                        while (node.getParent() != null){
                                            if(node.getParent().getCount() == item){
                                                m.add(new int[]{b(i), item});
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
                    if(getOccurrence(k).getType() == 1){

                        for(int h = j; h <= k - 1; h = h + 2){
                            if((getAtom(k).getValue().charAt(0) == '~'
                                    && getAtom(h).getValue().charAt(0) != '~') ||
                                    (getAtom(k).getValue().charAt(0) != '~'
                                            && getAtom(h).getValue().charAt(0) == '~')) {
                                for (int jj = h + 1; jj <= k - 1; jj = jj + 2) {
                                    //R'
                                    ArrayList<ArrayList<int[]>> r1 = f[i][j][h];
                                    ArrayList<ArrayList<int[]>> r2 = f[h+1][jj][k-1];

                                    for(int ii = 0; ii < getQ(k).size(); ii++){
                                        m.add(getQ(k).get(ii));
                                    }

                                    for(int item : getV(i)){
                                        Node node = getOccurrence(j);
                                        if(node.getCount() == item){
                                            continue;
                                        }

                                        while (node.getParent() != null){
                                            if(node.getParent().getCount() == item){
                                                m.add(new int[]{b(k), item});
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

     /* if(getOccurrence(j).getType() == 2){
                            m.addAll(getQ(j));
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
                        */

}
