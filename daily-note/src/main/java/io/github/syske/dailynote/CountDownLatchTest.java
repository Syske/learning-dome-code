package io.github.syske.dailynote;

import java.util.concurrent.atomic.AtomicInteger;

public class CountDownLatchTest{
        static AtomicInteger count=new AtomicInteger(0);
        private static finalintSIZE=100;

        publicstaticvoidmain(String[]args){
        longstartTime=System.currentTimeMillis();
        for(inti=0;i<SIZE;i++){
        newThread(newTaskPortion(startTime)).start();
        }
        }

        staticclassTaskPortionimplementsRunnable{
        privatelongstartTime;
        publicTaskPortion(){
        }
        publicTaskPortion(longstartTime){
        this.startTime=startTime;
        }

@Override
publicvoidrun(){
        try{
        Thread.sleep(1000);
synchronized(count){
        System.out.println(count.getAndAdd(1));
        if(count.get()==99){
        System.out.println("用时:"+(System.currentTimeMillis()-startTime));
        }
        }
        }catch(InterruptedExceptione){
        e.printStackTrace();
        }
        }
        }
        }