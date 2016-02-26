package coincidelcs;

import java.util.ArrayList;
import java.util.HashMap;

public class CoincideLCS {

    public static void main(String[] args) {

        ArrayList<String> arrL = new ArrayList<String>();
        arrL.add("abcd");
        arrL.add("cbda");
        arrL.add("dkbj");
        arrL.add("danvb");
        arrL.add("aalaaban;banb'anb ;ar ;a b;ar b;kar;ar ;as bcd");
        arrL.add("aadvbk");
        arrL.add("vadaadvl");

        String exemplar = "bdjck";
        try {
            System.out.println(coincide(arrL, exemplar));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static ArrayList<Double> coincideCharacters(ArrayList<String> arrL, String exemplar) {

        ArrayList<Double> coincides = new ArrayList<Double>();

        HashMap<Character, Integer> hm = new HashMap<Character, Integer>();

        for (char c : exemplar.toCharArray()) {
            if (hm.containsKey(c)) {
                hm.replace(c, hm.get(c) + 1);
            } else {
                hm.put(c, 1);
            }
        }

        for (String s : arrL) {
            HashMap<Character, Integer> tmpHM = new HashMap<Character, Integer>();
            for (char c : s.toCharArray()) {
                if (tmpHM.containsKey(c)) {
                    tmpHM.replace(c, tmpHM.get(c) + 1);
                } else {
                    tmpHM.put(c, 1);
                }
            }
            double d = 0.0;
            for (char c : tmpHM.keySet()) {
                int x = tmpHM.get(c);
                int y = hm.containsKey(c) ? hm.get(c) : 0;
                d += (x > y) ? y : x;
            }
            d *= 2;
            d = d / (exemplar.length() + s.length()) * 100;
            coincides.add(d);
        }

        return coincides;

    }

    public static ArrayList<Double> lcs(ArrayList<String> arrL, String exemplar) {

        ArrayList<Double> coincides = new ArrayList<Double>();

        int lcsMatrix[][] = new int[100][100];

        for (int k = 0; k < arrL.size(); k++) {

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    lcsMatrix[i][j] = 0;
                }
            }

            for (int i = 1; i <= exemplar.length(); i++) {
                for (int j = 1; j <= arrL.get(k).length(); j++) {
                    if (exemplar.charAt(i - 1) == arrL.get(k).charAt(j - 1)) {
                        lcsMatrix[i][j] = lcsMatrix[i - 1][j - 1] + 1;
                    } else {
                        lcsMatrix[i][j] = (lcsMatrix[i - 1][j] > lcsMatrix[i][j - 1]) ? lcsMatrix[i - 1][j] : lcsMatrix[i][j - 1];
                    }
                }
            }

            double d = lcsMatrix[exemplar.length()][arrL.get(k).length()];
            d *= 2;
            d = d / (exemplar.length() + arrL.get(k).length()) * 100;
            coincides.add(d);

        }

        return coincides;

    }

    public static String coincide(ArrayList<String> arrL, String exemplar) throws Exception {

        ArrayList<Double> ans1 = coincideCharacters(arrL, exemplar);
        ArrayList<Double> ans2 = lcs(arrL, exemplar);

        ArrayList<Double> ans = new ArrayList<Double>();

        for (int i = 0; i < ans1.size(); i++) {
            double d = (ans1.get(i) * 20 + ans2.get(i) * 80) / 100;
            ans.add(d);
        }

        int index = 0;
        for (int i = 1; i < ans.size(); i++) {
            if (ans.get(i) > ans.get(index)) {
                index = i;
            }
        }

        if (ans.get(index) < 50) {
            throw new Exception("არ მოიძებნა 50%-ზე მეტი დამთხვევა");
        }
        System.out.println(ans.get(index));
        return arrL.get(index);

    }

}
