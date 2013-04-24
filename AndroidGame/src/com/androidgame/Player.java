package com.androidgame;

import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.WindowManager;


public class Player 
{
	private Sprite sPlayer;
	
	Random random;
	
	WindowManager wm;
	DisplayMetrics display = new DisplayMetrics();
	
	int rand;
	int screenHeight, screenWidth;
	
	public int  PLAYER_VELOCITY;
	
	float PLAYER_START_POSITION1;
	float PLAYER_START_POSITION2;
	float PLAYER_START_POSITION3;
	
	float PLAYER_START_POSITION;
	
	private Collisions collide = new Collisions();
	
	public Player(Context context) 
	{
		if (sPlayer == null)
		{
			sPlayer = new Sprite();
		}
		
		sPlayer.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.player));
		
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(display);
		
		screenHeight = display.heightPixels;
		screenWidth = display.widthPixels;
		
		PLAYER_VELOCITY = (screenHeight / 100) * 3;
		
		PLAYER_START_POSITION1 = sPlayer.getWidth() * 2;
		  PLAYER_START_POSITION2 = (screenWidth / 2) - sPlayer.getWidth();
		  PLAYER_START_POSITION3 = screenWidth - sPlayer.getWidth();
		
	}

	public void DestroyPlayer() 
	{
		sPlayer = null;		
	}

	public void SetTarget(Vector2 mousePos) 
	{		
		sPlayer.setTarget(mousePos);
	}

	public void ResetPlayer(Context context) 
	{
		random = new Random(System.currentTimeMillis());
		
		rand = random.nextInt(3);
		
		switch(rand)
		{
			case 0:
			{
				PLAYER_START_POSITION = PLAYER_START_POSITION1;
				break;
			}
			
			case 1:
			{
				PLAYER_START_POSITION = PLAYER_START_POSITION2;
				break;
			}
			
			case 2:
			{
				PLAYER_START_POSITION = PLAYER_START_POSITION3;
				break;
			}
		}
		
		sPlayer = new Sprite();
		sPlayer.setImage(BitmapFactory.decodeResource(context.getResources(), R.drawable.player ));	
		
		sPlayer.setPosition(PLAYER_START_POSITION, 0);		
	}

	public void Update() 
	{
		sPlayer.update();		
	}

	public boolean ReachedPositionAt(Vector2 position) 
	{
		return sPlayer.reachedPositionAt(position);
	}

	public void SetVelocity(float f, float g) 
	{
		sPlayer.setVelocity(f, g);		
	}

	public int GetWidth()
	{
		
		return sPlayer.getWidth();
	}

	public int GetHeight() 
	{
		
		return sPlayer.getHeight();
	}

	public float GetPositionX() 
	{
		return sPlayer.getPositionX();
	}

	public float GetPositionY()
	{
		
		return sPlayer.getPositionY();
	}

	public void SetPosition(float getPositionX, float getPositionY) 
	{
		sPlayer.setPosition(getPositionX, getPositionY);		
	}

	public void Draw(Canvas canvas) 
	{
		sPlayer.draw(canvas);		
	}

	public boolean IsColliding(Sprite sB) 
	{
		
		return collide.IsColliding(sPlayer, sB);
	}
		 
}
