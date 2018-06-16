import java.util.ArrayList;

import static java.lang.Character.isLetter;

/**
 * Created by Boris on 16.10.2017.
 */
public class Tree{

    static int count_occur = 1, count_atom = 0;

    static class Lin {
        public String str;
        public int num = -1;

        Lin(String str, int num){
            this.str = str;
            this.num = num;
        }
    }

    static Node find_atom(Node root, int count) {

        if(root.getCount() == count && root.isLeaf()){
            return root;
        }
        else {
            if(root.getCount() <= count){
                if(root.getRight() != null){
                    root = find_atom(root.getRight(), count);
                }
            }
            else {
                if(root.getLeft() != null){
                    root = find_atom(root.getLeft(), count);
                }
            }
        }

        return root;
    }

    static Node find(Node root, int count){

        if(root.getCount() == count){
            return root;
        }
        else {
            if(root.getCount() < count){
                if(root.getRight() != null){
                    root = find(root.getRight(), count);
                }
            }
            else {
                if(root.getLeft() != null){
                    root = find(root.getLeft(), count);
                }
            }
        }

        return root;
    }

    static public Node create_tree(String str){
        Node root = new Node();
        root.set("&", 0);
        root.createRight(create_tree_rec(convert_lin(str)));
        //Node root = create_tree_rec(convert_lin(str));
        return root;
    }

    static private Node create_tree_rec(ArrayList<Lin> str){

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

                    ArrayList<Lin> left_l = new ArrayList<Tree.Lin>();
                    ArrayList<Lin> right_r = new ArrayList<Tree.Lin>();

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

                    ArrayList<Lin> left_l = new ArrayList<Tree.Lin>();
                    ArrayList<Lin> right_r = new ArrayList<Tree.Lin>();

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

    static private ArrayList<Lin> convert_lin(String str){

        ArrayList<Lin> l = new ArrayList<Tree.Lin>();
        StringBuilder s = new StringBuilder(str);
        StringBuilder ss = new StringBuilder("");

        for(int i = 0; i < str.length(); i++){

            if(s.charAt(i) == '(' || s.charAt(i) == ')'){
                if(ss.length() == 0){
                    l.add(new Lin(String.valueOf(s.charAt(i)), -1));
                } else {
                    l.add(new Lin(String.valueOf(ss.toString()), count_atom++));
                    l.add(new Lin(String.valueOf(s.charAt(i)), -1));
                    ss.delete(0, ss.length());
                }
            } else if(s.charAt(i) == '~' || isLetter(s.charAt(i))) {
                ss.append(s.charAt(i));
            } else if(ss.length() == 0){
                l.add(new Lin(String.valueOf(s.charAt(i)), ++count_occur));
            } else {
                l.add(new Lin(String.valueOf(ss.toString()), count_atom++));
                l.add(new Lin(String.valueOf(s.charAt(i)), ++count_occur));
                ss.delete(0, ss.length());
            }
        }

        if(ss.length() != 0){
            l.add(new Lin(ss.toString(), count_atom));
        }

        return l;
    }
}
