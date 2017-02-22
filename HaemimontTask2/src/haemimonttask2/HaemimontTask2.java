/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package haemimonttask2;

import java.util.Random;

/**
 *
 * @author stanislav
 */
public class HaemimontTask2 {

    public static void main(String[] args) {
        final int MAX_SIZE=250;
        char[] lastIndexFound=new char[Character.MAX_VALUE];
        Random rn = new Random();
        
//        StringBuilder st=new StringBuilder();
//        for(int i=0;i<MAX_SIZE;i++){
//            st.append((char) ('A' + rn.nextInt(64)));
//        }
        StringBuilder st =new StringBuilder("vavgdacdxdbazqxdd");

        
        int leftPointer=1, rightPointer=1,maxSubStrLeft=1,maxSubStrRight=1;
        
        while(rightPointer<=st.length()){
            if (lastIndexFound[st.charAt(rightPointer-1)]<leftPointer){
                if (rightPointer-leftPointer>maxSubStrRight-maxSubStrLeft){
                    maxSubStrLeft=leftPointer;
                    maxSubStrRight=rightPointer;
                }                
            }else{
                leftPointer=lastIndexFound[st.charAt(rightPointer-1)]+1;
            }
            lastIndexFound[st.charAt(rightPointer-1)]=(char) rightPointer;
            
            rightPointer++;
        }
        System.out.println(st);
        System.out.println(st.substring(maxSubStrLeft-1,maxSubStrRight));
    }
    
}
