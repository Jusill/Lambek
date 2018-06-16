import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Boris on 16.10.2017.
 */
class Convert {
    /**
     * @param str
     *      Секвенция в обычном исчеслениии Ламбека L(\, /, *)
     *      Левое деление - /
     *      Правое деление - \
     *      Умножение - *
     *      Разделители между формулами в акцеденте - ,
     *      Импликация - ->
     * @return
     *      Возвращает секвенцию в MCLL
     *      Пар - &
     *      Тензор - +
     *      Отрицание - ~ (ставится перед типом)
     */
    static String convert(String str){

        StringBuilder ans = new StringBuilder();

        String[] as = str.split("->");
        String[] form = as[0].split(",");
        ArrayList<String> new_form = new ArrayList<>();

        for(String f:form){
            new_form.add(convert_form(f, true));
        }

        //Collections.reverse(new_form);

        if(new_form.size() > 1){
            ans.append(new_form.get(0));
            ans.insert(0, "&");
            for(int i = 1; i < new_form.size(); i++){
                ans.insert(0, new_form.get(i));
                ans.insert(0, "(");
                ans.insert(ans.length()-1, ")");
                if (i != new_form.size()-1){
                    ans.insert(0, "&");
                }
            }
        } else {
            ans.append(new_form.get(0));
        }

        ans.append("&");
        ans.append(convert_form(as[1], false));

        return ans.toString();
    }

    private static String convert_form(String str, boolean otr){

        int weight = 0;
        String s1, s2;

        StringBuilder s = new StringBuilder(str);
        if (s.charAt(0) == '(' && s.charAt(s.length()-1) == ')'){
            s.deleteCharAt(0);
            s.deleteCharAt(s.length()-1);
        }

        for(int i = 0; i < s.length(); i++){
            switch (s.charAt(i)){
                case '(':
                    weight++;
                    break;
                case ')':
                    weight--;
                    break;
                case '\\':
                    if(weight == 0){
                        if(otr){
                            s1 = convert_form(s.substring(0, i), false);
                            s2 = convert_form(s.substring(i+1, s.length()), true);
                            return "(" + s2 + "+" + s1 + ")";
                        } else {
                            s1 = convert_form(s.substring(0, i), true);
                            s2 = convert_form(s.substring(i+1, s.length()), false);
                            return "(" + s1 + "&" + s2 + ")";
                        }
                    }
                break;
                case '/':
                    if(weight == 0){
                        if(otr){
                            s1 = convert_form(s.substring(0, i), true);
                            s2 = convert_form(s.substring(i+1, s.length()), false);
                            return "(" + s2 + "+" + s1 + ")";
                        } else {
                            s1 = convert_form(s.substring(0, i), false);
                            s2 = convert_form(s.substring(i+1, s.length()), true);
                            return "(" + s1 + "&" + s2 + ")";
                        }
                    }
                    break;
                case '*':
                    if(weight == 0){
                        if(otr){
                            s1 = convert_form(s.substring(0, i), true);
                            s2 = convert_form(s.substring(i+1, s.length()), true);
                            return "(" + s2 + "&" + s1 + ")";
                        } else {
                            s1 = convert_form(s.substring(0, i), false);
                            s2 = convert_form(s.substring(i+1, s.length()), false);
                            return "(" + s1 + "&" + s2 + ")";
                        }
                    }
                    break;
            }
        }

        if(otr){
            return '~' + str;
        } else {
            return str;
        }
    }
}
