////////////////////////////////////////////////////////
// Portfólio: Estudo Android						  // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////

package com.androidgame;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class Audio {
	
	private MediaPlayer mMediaPlayer = null;

	private Boolean mIsPaused = false;
	private int mPlaybackPosition = 0;
	
	public Audio()
	{
	}
	
	public void play(String filename)
	{
		release();
		
		try
		{
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setDataSource(filename);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			
			mIsPaused = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void playFromRes(Context context, int res)
	{
		release();
		
		try
		{
			mMediaPlayer = MediaPlayer.create(context, res);
			mMediaPlayer.start();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void playFromRes(AssetFileDescriptor assetFileDescriptor)
	{
		release();
		
		if( assetFileDescriptor != null )
		{
			try
			{
				mMediaPlayer = new MediaPlayer();
				mMediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
						assetFileDescriptor.getStartOffset(),
						assetFileDescriptor.getLength());
				assetFileDescriptor.close();
				assetFileDescriptor = null;
				mMediaPlayer.prepare();
				mMediaPlayer.start();
				
				mIsPaused = false;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public void pause()
	{
		if( mMediaPlayer != null )
		{
			mPlaybackPosition = mMediaPlayer.getCurrentPosition();
			mMediaPlayer.pause();
			mIsPaused = true;
		}
	}
	
	public void resume()
	{
		if( mMediaPlayer != null )
		{
			mMediaPlayer.seekTo(mPlaybackPosition);
			mMediaPlayer.start();
			mIsPaused = false;
		}
	}
	
	public void stop()
	{
		if( mMediaPlayer != null )
		{
			mIsPaused = false;
			mMediaPlayer.stop();
		}
	}
	
	public void release()
	{
		if( mMediaPlayer != null )
		{
			try
			{
				mMediaPlayer.release();
				mMediaPlayer = null;
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public boolean isPaused()
	{
		return mIsPaused;
	}
	
	public void Looping(boolean looping)
	{
		if (mMediaPlayer != null)
		{
			mMediaPlayer.setLooping(looping);
		}
	}

}
