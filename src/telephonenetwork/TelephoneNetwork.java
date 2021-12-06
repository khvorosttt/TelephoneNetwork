/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telephonenetwork;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Lenovo
 */
public class TelephoneNetwork {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        List<Ribs> ribs = new ArrayList<>();
        int amount =IN(ribs);
        Collections.sort(ribs);
        Collections.reverse(ribs);
        List<Ribs> result = new ArrayList<>();
        result.add(ribs.get(0));
        ribs.remove(0);
        int[] set=new int[amount];//начальное положение вершин
        for (int i = 0; i < amount; i++) {//предки сами себе, начальное положение
            set[i] = i;
        }
        int[] form=new int[amount];
        result = Add(amount, ribs, result, set, form);
        System.out.println("Стоимость кабеля: "+showRibs(result));

    }

    public static int IN(List<Ribs> ribs) throws IOException {
        BufferedReader fin = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") + "\\Data.txt")));
        String[] data_Power = fin.readLine().split(" ");
        for (int i = 0; i < Integer.parseInt(data_Power[1]); i++) {
            String[] data = fin.readLine().split(" ");
            ribs.add(new Ribs(Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
        }
        return Integer.parseInt(data_Power[0]);
    }

    public static List<Ribs> Add(int index, List<Ribs> ribs, List<Ribs> result,int[] set, int[] form) {
        while (result.size() < index - 1) {
            if (DFS(index, ribs.get(0), set, form)) {
                result.add(ribs.get(0));
                ribs.remove(ribs.get(0));
            } else {
                ribs.remove(ribs.get(0));
            }
        }
        return result;
    }

    public static boolean DFS(int amount, Ribs rib, int[] set, int[] form) {
        if (set(rib.Out(), set) == set(rib.In(), set)) {//имеют общего предка
            return false;
        }
        if (form[rib.Out()] < form[rib.In()]) {
            set[rib.Out()] = rib.In();//установка нового предка
        } else {
            set[rib.In()] =rib.Out();//установка нового предка
            if (form[rib.Out()] == form[rib.In()]) {//
                form[rib.Out()]++;
            }
        }
        return true;

    }

    public static int set(int out, int[] set) {//поиск предка
        if (out == set[out]) {
            return out;
        } else {
            return set(set[out], set);
        }
    }

    public static int showRibs(List<Ribs> result) {
        int sum=0;
        for (Ribs r : result) {
            System.out.println(r.Out() + " " + r.In() + " " + r.Weight());
            sum+=r.Weight();
        }
        return sum;
    }
}
