package com.askdynamics.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StringUtil{

    public static List<String> getAllCombination(String searchStr){

        if(searchStr==null){
            return null;
        }

        List<String> combinations = new ArrayList<String>();
        getConfigurations(0, searchStr.split(" "), combinations, new StringBuffer());

        Comparator<String> StringComparator = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        };
        Collections.sort(combinations, StringComparator);

        for (String str : combinations){
            System.out.println(str);
        }
        //  return arrangeCombinations(combinations);
        return combinations;

    }




    private static void getConfigurations (int index, String[] input, List<String> output, StringBuffer buffer) {
        //  for (index; index < )
        if (index >= input.length)
            return;

        for (int cnt = index; cnt < input.length; cnt++){
            if (cnt > index)
                buffer.append(" ");

            buffer.append(input[cnt]);

            output.add(buffer.toString().toLowerCase());
            //System.out.println(buffer.toString().toLowerCase());
        }

        getConfigurations(index+1, input, output, new StringBuffer());
    }

    public static void main(String[] args) {
        getAllCombination("Agent Configuraion Setup");
    }
}