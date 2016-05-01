package ui.blog.mp3;
import javax.media.*;


public class Mp3Controller {
	private Mp3State mp3;

	public Mp3Controller(Mp3State mp3) {
		this.mp3 = mp3;
	}

	public void mp3Start() {
		if (!mp3.getIsStarted()) {
			mp3.setTriggerStart(true);
		} else {
			System.out
					.println("Error: A song is playing now, thus player cannot start another");
		}
	}

	public void mp3Stop() {
		if (mp3.getIsStarted()) {
			Player player = mp3.getPlayer();
			player.stop();
			player.deallocate();
			player.close();
			mp3.setIsStarted(false);
			mp3.setTriggerStart(false);
		//	mp3.setCurrentIndex(mp3.getCurrentIndex() - 1);
		} else if (mp3.getIsPaused()) {
			mp3.setIsPaused(false);
			mp3.setPauseTime(null);
		} else {
			System.out
					.println("Error: No song has been playing yet, thus can not been stopped");
		}
	}
/*
	public void mp3Next() {
		if (mp3.getIsStarted() || mp3.getIsPaused()) {
			this.mp3Stop();
		}
		int currentIndex = mp3.getCurrentIndex();
		if (currentIndex < (mp3.getSonglist().size() - 1)) {
			mp3.setCurrentIndex(currentIndex + 1);
			this.mp3Start();
		} else {
			System.out.println("Error: End of the songlist");
		}
	}

	public void mp3Previous() {
		if (mp3.getIsStarted()) {
			this.mp3Stop();
		}
		if (mp3.getIsPaused()) {
			mp3.setPauseTime(null);
			mp3.setIsPaused(false);
		}
		int currentIndex = mp3.getCurrentIndex();
		if (currentIndex > 0) {
			mp3.setCurrentIndex(currentIndex - 1);
			this.mp3Start();
		} else {
			System.out.println("Error: Start of the songlist");
		}
	}
*/
	public void mp3Pause() {
		if (mp3.getIsStarted()) {
			Player player = mp3.getPlayer();
			mp3.setPauseTime(player.getMediaTime());
			player.stop();
			player.deallocate();
			player.close();
			mp3.setIsStarted(false);
			mp3.setIsPaused(true);
			mp3.setTriggerStart(false);
			//mp3.setCurrentIndex(mp3.getCurrentIndex() - 1);
		} else {
			System.out
					.println("Error: No song has been playing yet, thus can not paused");
		}
	}
	
	public Time getCurrentTime(){
		if (mp3.getIsStarted()) {
			return mp3.getPlayer().getMediaTime();
		}
		else return null;
	}
}