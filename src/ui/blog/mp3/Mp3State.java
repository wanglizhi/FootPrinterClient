package ui.blog.mp3;
  import javax.media.*;
  
  public class Mp3State {
    //  private Vector<String> songlist = new Vector<String>();
   //   private int currentIndex = 0 ;
      private Player player = null;
      private boolean isStarted = false;
      private boolean isPaused = false;
      private Time pauseTime = null;
      private boolean triggerStart = false;
      /**
       * @author SongShuo
       * 用于显示歌曲名字
       */
    
      public Mp3State(){
//          try{
//              BufferedReader in = new BufferedReader(new FileReader("songlist.txt"));
//              String eachline;
//              while((eachline = in.readLine()) != null){
//                  songlist.addElement(eachline);
//              }
//              in.close();
//          } catch(IOException e){
//             System.out.println(e.getMessage());
//              System.exit(0);
//          }
      }
      
//      public Vector<String> getSonglist(){
//          return songlist;
//     }
     
     public void setTriggerStart(boolean state){
       triggerStart = state;
    }
     
     public boolean getTriggerStart(){
         return triggerStart;
     }    
     
//      public void setCurrentIndex(int index){
//          currentIndex = index;
//      }
//     
//      public int getCurrentIndex(){
//          return currentIndex;
//      }
      
      public void setPlayer(Player player){
          this.player = player;
      }
      
      public Player getPlayer() {
         return player;
      }
      
      public void setIsStarted(boolean state){
          isStarted = state;
      }
      
      public boolean getIsStarted(){
          return isStarted;
      }
     
      public void setIsPaused(boolean state){
          isPaused = state;
      }
      
      public boolean getIsPaused(){
          return isPaused;
      }
      
      public void setPauseTime(Time time){
         pauseTime = time;
      }
      
      public Time getPauseTime(){
          return pauseTime;
      }
    /*  
      public String getSongName(){
    	  String songName = songlist.elementAt(currentIndex);
    	  return songName;
      }
      */
  }

