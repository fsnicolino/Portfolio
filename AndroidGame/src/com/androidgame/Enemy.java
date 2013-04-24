package com.androidgame;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Enemy 
{
	
	private int previousState;
	
	private int currentState;
	
	private boolean isWalkingLeft = false;
	private boolean isNpcIdle = true;
	private boolean dWalk;
	
	private boolean facesTop;
	private boolean facesDown;
	private boolean isFacingLeft = false;
	private boolean isFacingRight = false;
	private boolean isFacingBottom = false;
	private boolean isFacingTop = false;
	
	
		
	int NPC_VELOCITY;
	
	private long timer;
	
	private Sprite sEnemy;
	private Sprite sFoView;
	
	WindowManager wm;
	DisplayMetrics display = new DisplayMetrics();
	
	private int screenHeight, screenWidth;
	
	public Enemy(Context context, int npc)
	{
		if (sEnemy == null)
		{
			sEnemy = new Sprite();
		}
		
		if (sFoView == null)
		{
			sFoView = new Sprite();
		}
		
		SetImage(context, npc);
		
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(display);
		
		screenHeight = display.heightPixels;
		screenWidth = display.widthPixels;
		
		NPC_VELOCITY = (screenWidth / 100) + 1;
		
	}

	public void SetFieldofView(Context context, int npcfov) 
	{		
		SetFImage(context, npcfov);
	}

	
	public void DestroyEnemy() 
	{		
		sEnemy = null;
		sFoView = null;
	}

	public void ResetEnemy(Context context, int npc, int npcState, boolean idle, int face, boolean top, boolean down, boolean walk) 
	{		
		sEnemy = new Sprite();
		SetImage(context, npc);
		
		timer = System.currentTimeMillis();
		
		currentState = npcState;
		
		SetIdle(idle);	
		
		currentState = face;
		
		facesTop = top;
		
		facesDown = down;
		
		dWalk = walk;
		
		previousState = CONSTANTS.NPC_STATE_IDLE;
		
		switch(currentState)
		{
			case CONSTANTS.NPC_STATE_FACING_DOWN:
				isFacingBottom = true;
				isFacingLeft = false;
				isFacingRight = false;
				isFacingTop = false;
			break;
				
			case CONSTANTS.NPC_STATE_FACING_LEFT:
				isFacingBottom = false;
				isFacingLeft = true;
				isFacingRight = false;
				isFacingTop = false;
			break;
				
			case CONSTANTS.NPC_STATE_FACING_RIGHT:
				isFacingBottom = false;
				isFacingLeft = false;
				isFacingRight = true;
				isFacingTop = false;
			break;
				
			case CONSTANTS.NPC_STATE_FACING_TOP:
				isFacingBottom = false;
				isFacingLeft = false;
				isFacingRight = false;
				isFacingTop = true;
			break;		
		}
		
		
	}

	public void ResetFoView(Context context, int npcfov) 
	{		
		sFoView = new Sprite();
		SetFImage(context, npcfov);		
	}

	public int GetWidth() 
	{		
		return sEnemy.getWidth();
	}

	
	public float GetPositionX() 
	{
		return sEnemy.getPositionX();
	}
	
	public float GetPositionY() 
	{
		return sEnemy.getPositionY();
	}

	public int GetHeight() 
	{
		return sEnemy.getHeight();
	}
	
	public void SetNpcPosition(float i, float j) 
	{
		sEnemy.setPosition(i, j);		
	}

	public void SetFieldofViewPos(float x, float y) 
	{		
		sFoView.setPosition(x, y);
	}

	public int GetState() 
	{
		return currentState;
	}

	public void SetImage(Context context, int npc) 
	{
		sEnemy.setImage(BitmapFactory.decodeResource(context.getResources(), npc));
	}
	
	public void SetFImage(Context context, int npcfov) 
	{
		sFoView.setImage(BitmapFactory.decodeResource(context.getResources(), npcfov));
	}
	
	public void DrawEnemy(Canvas canvas) 
	{
		sEnemy.draw(canvas);
		sFoView.draw(canvas);
	}
		
	public void UpdateNpc() 
	{
		if (isFacingBottom)
		{				
			sFoView.setPosition(GetPositionX(), GetPositionY() + GetHeight());						
		}
		
		else if (isFacingLeft)
		{						
			sFoView.setPosition(GetPositionX() - sFoView.getWidth(), GetPositionY());						
		}
		
		else if (isFacingRight)
		{							
			sFoView.setPosition(GetPositionX() + GetWidth(), GetPositionY());						
		}
		
		else if (isFacingTop)
		{
			sFoView.setPosition(GetPositionX(), GetPositionY() - sFoView.getHeight());
		}
		
		if (!dWalk)
		{
			switch (currentState)
			{
            	case CONSTANTS.NPC_STATE_IDLE:
            		if (!isNpcIdle)
            		{	
            			if (!facesTop)
            			{
            				if (  System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            				{
            					currentState = CONSTANTS.NPC_STATE_FACING_LEFT;
            					previousState = CONSTANTS.NPC_STATE_FACING_DOWN;
            					isFacingBottom = false;
            					isFacingLeft = true;
            					timer = System.currentTimeMillis();
            				}
            			}
            		
            			if (facesTop)
            			{
            				if (facesDown)
            				{
            					if (  System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            					{
            						currentState = CONSTANTS.NPC_STATE_FACING_RIGHT;
            						previousState = CONSTANTS.NPC_STATE_FACING_TOP;
            						isFacingTop = false;
            						isFacingLeft = true;
            						timer = System.currentTimeMillis();
            					}
            				}
            			
            				else if (!facesDown)
            				{
            					if ( System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            					{
            						currentState = CONSTANTS.NPC_STATE_FACING_LEFT;
            						previousState = CONSTANTS.NPC_STATE_FACING_TOP;
            						isFacingTop = false;
            						isFacingRight = true;
            						timer = System.currentTimeMillis();
            					}
            				}
            			}
            		}
            		break;

            	case CONSTANTS.NPC_STATE_FACING_DOWN:	                	
            	
            		if (System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            		{	        				
            			if (previousState == CONSTANTS.NPC_STATE_FACING_LEFT)
            			{
            				currentState = CONSTANTS.NPC_STATE_FACING_RIGHT;
            				isFacingRight = true;
            				isFacingBottom = false;
            				timer = System.currentTimeMillis();
            			}       				
            			else if (previousState == CONSTANTS.NPC_STATE_FACING_RIGHT || previousState == CONSTANTS.NPC_STATE_IDLE)
            			{
            				currentState = CONSTANTS.NPC_STATE_FACING_LEFT;
            				isFacingLeft = true;
            				isFacingBottom = false;
            				timer = System.currentTimeMillis();
            			}    				
            			
            		}	                
                break;                      		
             
            	case CONSTANTS.NPC_STATE_FACING_LEFT:
            	
            		if (!facesTop)
            		{
            			if (System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            			{
            				isFacingBottom = true;
            				currentState = CONSTANTS.NPC_STATE_FACING_DOWN;
            				previousState = CONSTANTS.NPC_STATE_FACING_LEFT;
            				timer = System.currentTimeMillis();
            				isFacingLeft = false;
            			}
            		}
            	
            		else if (facesTop)
            		{
            			if (!facesDown)
            			{
            				if (System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            				{
            					isFacingTop = true;
            					currentState = CONSTANTS.NPC_STATE_FACING_TOP;
            					previousState = CONSTANTS.NPC_STATE_FACING_LEFT;
            					timer = System.currentTimeMillis();
            					isFacingLeft = false;
            				}
            			}
            		}
                break;
                
            	case CONSTANTS.NPC_STATE_FACING_RIGHT:
            	
            		if (!facesTop)
            		{
            			if (System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            			{
            				isFacingBottom = true;
            				timer = System.currentTimeMillis();
            				isFacingRight = false;
            				currentState = CONSTANTS.NPC_STATE_FACING_DOWN;
            				previousState = CONSTANTS.NPC_STATE_FACING_RIGHT;            		
            			}
            		}
            	
            		if (facesTop)
            		{
            			if (!facesDown)
            			{
            				if (System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            				{
            					isFacingTop = true;
            					timer = System.currentTimeMillis();
            					isFacingRight = false;
            					currentState = CONSTANTS.NPC_STATE_FACING_TOP;
            					previousState = CONSTANTS.NPC_STATE_FACING_RIGHT;            		
            				}
            			}
            		
            			else if (facesDown)
            			{
            				if (System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            				{
            					isFacingBottom = true;
            					timer = System.currentTimeMillis();
            					isFacingRight = false;
            					currentState = CONSTANTS.NPC_STATE_FACING_DOWN;
            					previousState = CONSTANTS.NPC_STATE_FACING_RIGHT;            		
            				}
            			}
            		}            	
            	
                break;
                
            	case CONSTANTS.NPC_STATE_FACING_TOP:	                	
            	
            		if (  System.currentTimeMillis() - timer > CONSTANTS.NPC_FACING_TIME)
            		{      				
    					if (previousState == CONSTANTS.NPC_STATE_FACING_LEFT || previousState == CONSTANTS.NPC_STATE_IDLE)
    					{
    						currentState = CONSTANTS.NPC_STATE_FACING_RIGHT;
    						isFacingRight = true;
    						isFacingTop = false;
    						timer = System.currentTimeMillis();
    					}
            		}
    				if (!facesDown)
    				{
    					if (previousState == CONSTANTS.NPC_STATE_FACING_RIGHT)
    					{
    						currentState = CONSTANTS.NPC_STATE_FACING_LEFT;
    						isFacingLeft = true;
    						isFacingTop = false;
    						timer = System.currentTimeMillis();
    					}
    				}
    				
    				else if (facesDown)
    				{
    					if (previousState == CONSTANTS.NPC_STATE_FACING_LEFT)
    					{
    						currentState = CONSTANTS.NPC_STATE_FACING_RIGHT;
    						//previousState = CONSTANTS.NPC_STATE_FACING_TOP;
    						isFacingRight = true;
    						isFacingTop = false;
    						timer = System.currentTimeMillis();
    					}
    				}               
               	}
         	}
		
		else if (dWalk)
		{
			if (isWalkingLeft)
			{
				sEnemy.setPosition(GetPositionX() - NPC_VELOCITY, sEnemy.getPositionY()); 
            	sFoView.setPosition(GetPositionX() - GetWidth(), sFoView.getPositionY());
    			    			
    			if (GetPositionX()  >= screenWidth - GetWidth())
    			{
    				isWalkingLeft = false;
    			}    			
			}			
			else if (!isWalkingLeft)
			{
				sEnemy.setPosition(GetPositionX() + NPC_VELOCITY, sEnemy.getPositionY()); 
            	sFoView.setPosition(GetPositionX() + GetWidth(), sFoView.getPositionY());
            	
    			if (GetPositionX() <= 0)
    			{
    				isWalkingLeft = true;    				
    			}	                           
				
			}
		}
		
	}

	public void SetIdle(boolean idle) 
	{
		isNpcIdle = idle;		
	}
	
	public void SetWalkLeft (boolean walk)
	{
		isWalkingLeft = walk;
	}
	
	

}
