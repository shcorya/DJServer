package org.corya.djserver;

import javafx.embed.swt.FXCanvas;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import org.corya.remotedj.models.Playlist;
import org.corya.remotedj.models.Track;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

public final class Player {
	volatile static boolean isPlaying = false;
	static FXCanvas canvas; // necessary for JavaFX
	static Media hit;
	static MediaPlayer mediaPlayer;
	static Playlist playlist = new Playlist();
	
	public static void setShell(Shell shell){
		canvas = new FXCanvas(shell, SWT.NONE); // necessary for JavaFX
	}
	
	public static void play(Track track){
		add(track);
		if(!isPlaying){
			hit = new Media(track.getUri().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
			isPlaying = true;
		}
	}
	
	public static void add(Track track){
		playlist.add(track);
	}
	
	public static void remove(int ind){
		playlist.remove(ind);
	}
	
	public static void goTo(int ind){
		if(isPlaying){
			stop();
		}
		if(!isPlaying){
			hit = new Media(playlist.getTrackAt(ind).getUri().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
			isPlaying = true;
		}
		playlist.setCurrentIndex(ind);
	}
	
	public static void stop(){
		if(isPlaying)
			mediaPlayer.stop();
		isPlaying = false;
	}
	

	
	public static boolean next(){
		Track track = playlist.next();
		if(track == null){
			return false;
		}
		if(isPlaying){
			stop();
		}
		if(!isPlaying){
			hit = new Media(track.getUri().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
			isPlaying = true;
		}
		return true;
	}
	
	public static boolean previous(){
		Track track = playlist.previous();
		if(track == null){
			return false;
		}
		if(isPlaying){
			stop();
		}
		if(!isPlaying){
			hit = new Media(track.getUri().toString());
			mediaPlayer = new MediaPlayer(hit);
			mediaPlayer.play();
			isPlaying = true;
		}
		return true;
	}
	
	public static void netFunction(int arg0, int arg1, int arg2){
		switch (arg0) {
			case 'r': playlist.remove(arg1);
				break;
			case 'g': goTo(arg1);
				break;
			case 'm': playlist.move(arg1, arg2);
				break;
			case 'n': next();
				break;
			case 'p': previous();
				break;
			case 's': stop();
				break;
		}
	}
}