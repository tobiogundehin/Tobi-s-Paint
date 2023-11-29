package com.mycompany.imageviewer;

import java.util.Random;

public class JavaPuns {
    /**
     * Create an array of JavaPuns and randomly return one
     */
    public String returnPun(){
        String[] puns = {
                "Why do Java developers wear glasses? Because they don't C#!",
                "What's a Java developer's favorite candy? Java beans!",
                "Why was the Java developer so good at basketball? Because they had great Java skills!",
                "Why don't Java developers like to swim? Because they don't like to be in 'floats'!",
                "Why did the Java developer go broke? Because they used up all their cache!",
                "Why do Java developers make good detectives? Because they're excellent at finding bugs!",
                "Why do Java developers make terrible secret agents? Because they can't keep anything private!",
                "Why did the Java developer keep their computer in the fridge? Because they wanted to keep it cool!",
                "Why was the Java programmer cold? Because they left all the windows open!",
                "Why did the Java developer go broke? Because they lost their JavaBeans in the stock market!"
        };
        Random rand = new Random();
        int upperBound = puns.length - 1;
        int randomPunNumber = rand.nextInt(upperBound);
        return puns[randomPunNumber];

    }
}
