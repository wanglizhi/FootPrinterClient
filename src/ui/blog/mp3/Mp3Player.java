package ui.blog.mp3;
import java.util.*;
 import java.io.*;
import java.net.URL;

import javax.media.*;
 
 public class Mp3Player extends Thread{
     private Mp3State mp3;
     private int songlistLength;
     private Vector<String> songlist;
     private Player player = null;
     private double currentDuration;
     
     private String songName;
    
    public Mp3Player(Mp3State mp3, String name){
        this.mp3 = mp3;
		songName = name;
        
    }
    
    public int getCurrentDuration(){
    	return (int)currentDuration;
    }
    @Override
	public void run(){
        int currentIndex;
        double currentTime = 0;
        currentDuration = -1;
        
        while(true){
            if(mp3.getTriggerStart() && (currentTime >= currentDuration)){
                try{
                	 File currentSong = new File("blog/"+songName);
                	 System.out.println(currentSong.toURL());
                    player = Manager.createRealizedPlayer(currentSong.toURL());
                	
                    mp3.setPlayer(player);
                    if(mp3.getIsPaused()){
                        player.setMediaTime(mp3.getPauseTime());
                    }
                   player.start();
                    System.out.print(">>>");
                   mp3.setIsStarted(true);
                  currentDuration = player.getDuration().getSeconds();
                } catch(Exception e){
                    System.out.println(e);
                }
        
            }
            if(mp3.getIsStarted()){
                currentTime = player.getMediaTime().getSeconds();
            } else {
                currentTime = 0;
                currentDuration = -1;
            }
            try{
                Thread.sleep((int)(Math.random() * 00));
            } catch (InterruptedException e){}
       }
    }
}