package com.androidgame;



import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class MainGame extends SurfaceView implements SurfaceHolder.Callback  {
	
	private Audio audioSfx = null;
	private Audio audioBg = null;
	
	private Rect rectwindow;
	
	private SurfaceHolder surfaceHolder;
	
	private WindowManager wm;
	private DisplayMetrics display = new DisplayMetrics();
	
	private int screenHeight, screenWidth;
	
	private Context context;

	private GameThread gameThread;
	
	
	//booleans
	private boolean Start;
	private boolean hasSurface;
	private boolean isNewGame;
	private boolean pressed;
	private boolean isGameOver;
	//private boolean audioOn;
	private boolean isPaused;
	
	private Vector2 mousePos;
	
	private Node[] path;
	private int currentNode;
	private int addedNode;
	
	private Paint paint;
	
	private int currentScreen;
	
	//Sprites
	private Sprite wallTop;
	private Sprite wallBottom;
	private Sprite exit;
	
	private Sprite gameOver;
	private Sprite pause;
	private Sprite highScore;
	
	
	//player
	private Player mPlayer;
	
	//enemy
	private Enemy mNpc1;
	private Enemy mNpc2;
	private Enemy mNpc3;
	private Enemy mNpc4;
	private Enemy mNpc5;
	private Enemy mNpc6;
	private Enemy mNpc7;
	private Enemy mNpc8;
	private Enemy mNpc9;
	
	public MainGame(Context context) {
		super(context);
		Log.v( this.getClass().getName(), "public Game1(Context context)" );

    	init( context );
	}
	
	public MainGame(Context context, AttributeSet attrs) 
	{
		super( context, attrs );

		Log.v( this.getClass().getName(), "public Game1(Context context, AttributeSet attrs)" );

		init( context );

	}

	public MainGame(Context context, AttributeSet attrs, int defStyle) 
	{
		super( context, attrs, defStyle );

		Log.v( this.getClass().getName(), "public Game1(Context context, AttributeSet attrs, int defStyle)" );

		init( context );


	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) 
	{
		Log.v( this.getClass().getName(), "public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)" );

		// Surface or window had its size changed (e.g. device orientation changed)
		if( gameThread != null ) {
			gameThread.onWindowResize( width, height );
		}		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) 
	{
		Log.v( this.getClass().getName(), "public void surfaceCreated(SurfaceHolder holder)" );

		// Surface created, so we do have a surface
		hasSurface = true;
		isNewGame = true;
		
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(display);
		
		screenHeight = display.heightPixels;
		screenWidth = display.widthPixels;
		
		beginRun();
		

		if (audioBg == null)
		{
			audioBg = new Audio();
		}
		
		
		if(audioSfx == null)
		{
			audioSfx = new Audio();
		}
		
		if (wallTop == null)
		{
			wallTop = new Sprite();
		}    	
		wallTop.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
		
		if (wallBottom == null)
		{
			wallBottom = new Sprite();
		}    	
		wallBottom.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
		
		if (exit == null)
		{
			exit = new Sprite();
		}    	
		exit.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.exit ));
	
		
		if (highScore == null)
		{
			highScore = new Sprite();
		}    	
		highScore.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.highscore ));
		
		if (mPlayer == null)
		{
			mPlayer = new Player(context);
		}
		
		if (mNpc1 == null)
		{
			mNpc1 = new Enemy(context, R.drawable.npc1);
			
			mNpc1.SetFieldofView(context,R.drawable.npcfov1v);
		}
		
		if (mNpc2 == null)
		{
			mNpc2 = new Enemy(context, R.drawable.npc1);
			
			mNpc2.SetFieldofView(context,R.drawable.npcfov1v);
		}
		
		if (mNpc3 == null)
		{
			mNpc3 = new Enemy(context, R.drawable.npc2);
			
			mNpc3.SetFieldofView(context,R.drawable.npcfov2v);
		}
		
		if (mNpc4 == null)
		{
			mNpc4 = new Enemy(context, R.drawable.npc2);
			
			mNpc4.SetFieldofView(context,R.drawable.npcfov2v);
		}
		
		if (mNpc5 == null)
		{
			mNpc5 = new Enemy(context, R.drawable.npc3);
			
			mNpc5.SetFieldofView(context,R.drawable.npcfov3v);
		}
		
		if (mNpc6 == null)
		{
			mNpc6 = new Enemy(context, R.drawable.npc4r);
			
			mNpc6.SetFieldofView(context,R.drawable.npcfov4);
		}
		
		if (mNpc7 == null)
		{
			mNpc7 = new Enemy(context, R.drawable.npc4r);
			
			mNpc7.SetFieldofView(context,R.drawable.npcfov4);
		}
		
		if (mNpc8 == null)
		{
			mNpc8 = new Enemy(context, R.drawable.npc4l);
			
			mNpc8.SetFieldofView(context,R.drawable.npcfov4);
		}
		
		if (mNpc9 == null)
		{
			mNpc9 = new Enemy(context, R.drawable.npc4l);
			
			mNpc9.SetFieldofView(context,R.drawable.npcfov4);
		}
		
		Reset();
		
		if( gameThread != null ) 
		{
			gameThread.start();
		}
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) 
	{
		
		hasSurface = false;
		
		audioSfx = null;
		audioBg = null;
		
		wallBottom = null;
		wallTop = null;
		exit = null;
		highScore = null;
		gameOver = null;
		pause = null;
		
		mPlayer.DestroyPlayer();
		
		mPlayer = null;
		
		mNpc1.DestroyEnemy();
		
		mNpc1 = null;
		
		mNpc2.DestroyEnemy();
		
		mNpc2 = null;
		
		mNpc3.DestroyEnemy();
		
		mNpc3 = null;
		
		mNpc4.DestroyEnemy();
		
		mNpc4 = null;
		
		mNpc5.DestroyEnemy();
		
		mNpc5 = null;
		
		mNpc6.DestroyEnemy();
		
		mNpc6 = null;
		
		mNpc7.DestroyEnemy();
		
		mNpc7 = null;
		
		mNpc8.DestroyEnemy();
		
		mNpc8 = null;
		
		mNpc9.DestroyEnemy();
		
		mNpc9 = null;
				
		endRun();
		
	}
	
	public boolean onTouchEvent(MotionEvent event)
	{
		Log.v( this.getClass().getName(), "public boolean onTouchEvent(MotionEvent event)" );

		pressed = true;
		isNewGame = false;
		mousePos.setX(event.getX());
		mousePos.setY(event.getY());
		mPlayer.SetTarget(mousePos);
		
		if(currentScreen == CONSTANTS.HIGH_SCORE)
		{
			Start = true;
			currentScreen = CONSTANTS.GAME_SCREEN;
		}
				
		return( super.onTouchEvent(event) );
	}
	
	private void init(Context context) 
	{
		Log.v( this.getClass().getName(), "private void init(Context context)" );

		this.context = context;

		// SurfaceHolder allows access to the SurfaceView's surface
		surfaceHolder = getHolder();
		surfaceHolder.addCallback( this );
		hasSurface = false;

		// This view is focusable (receive interaction)
		setFocusable( true );
		paint = new Paint(); 
		
		paint.setColor(Color.WHITE); 
		paint.setTextSize(10);
		paint.setTextAlign(Align.LEFT);	
		
		pressed = false;
		
		isNewGame = true;
				
		
		path = new Node[4];
        for (int i = 0; i < path.length; ++i)
        {
            path[i] = new Node();
            path[i].setPosition(new Vector2(i * 10, i * 10));
        }

        currentNode = 0;
        addedNode = 0;            
        mousePos = Vector2.Zero();	
	
	}
	
	public void beginRun() {
		Log.v( this.getClass().getName(), "public void resume()" );

		// If thread does not exist, instantiate
		if( gameThread == null ) {
			gameThread = new GameThread();

			// If surface has been created, it means thread has started so resume it
			if( hasSurface ) {
				gameThread.resume();
			}
		}
	}
	
	public void endRun() {
		Log.v( this.getClass().getName(), "public void pause()" );

		// Free thread
		if( gameThread != null ) {
			gameThread.requestExitAndWait();
			gameThread = null;
		} 
	}
	
	private void Reset() 
	{   
		pressed = false;
		
		isNewGame = true;
		
			
		isGameOver = false;
		
		currentScreen = CONSTANTS.HIGH_SCORE;
		
		audioBg.playFromRes(getResources().openRawResourceFd(R.raw.background));	
		audioBg.Looping(true);
		
		wallTop = new Sprite();
		wallTop.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
		
		wallBottom = new Sprite();
		wallBottom.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
		
		exit = new Sprite();
		exit.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.exit ));
		
								
		highScore = new Sprite();
		highScore.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.highscore ));
		
		wallTop.setPosition(0,0);
		wallBottom.setPosition(0, getHeight() - wallBottom.getHeight());
		exit.setPosition((getWidth() / 2) - (exit.getWidth() / 2), wallBottom.getPositionY());
		
		highScore.setPosition(0, 0);
		
		mPlayer.ResetPlayer(context);
		
		//Reset Enemy
		mNpc1.ResetEnemy(context, R.drawable.npc1, CONSTANTS.NPC_STATE_IDLE, false, CONSTANTS.NPC_STATE_FACING_DOWN, false, true, false);
		mNpc1.ResetFoView(context, R.drawable.npcfov1v);
		
		mNpc1.SetNpcPosition((screenWidth / 2) - (3 * mNpc1.GetWidth()), wallTop.getHeight());
		
				
		mNpc2.ResetEnemy(context, R.drawable.npc1, CONSTANTS.NPC_STATE_IDLE, false, CONSTANTS.NPC_STATE_FACING_DOWN, false, true, false);
		mNpc2.ResetFoView(context, R.drawable.npcfov1v);
		
		mNpc2.SetNpcPosition((screenWidth / 2) + (3 * mNpc2.GetWidth()), wallTop.getHeight());
		
		mNpc3.ResetEnemy(context, R.drawable.npc2, CONSTANTS.NPC_STATE_IDLE, false, CONSTANTS.NPC_STATE_FACING_TOP, true, false, false);
		mNpc3.ResetFoView(context, R.drawable.npcfov2v);
		
		mNpc3.SetNpcPosition((screenWidth / 2) - (3 * mNpc3.GetWidth()), (screenHeight - wallBottom.getHeight()) - mNpc3.GetHeight());
		
		mNpc4.ResetEnemy(context, R.drawable.npc2, CONSTANTS.NPC_STATE_IDLE, false, CONSTANTS.NPC_STATE_FACING_TOP, true, false, false);
		mNpc4.ResetFoView(context, R.drawable.npcfov2v);
		
		mNpc4.SetNpcPosition((screenWidth / 2) + (3 * mNpc4.GetWidth()), (screenHeight - wallBottom.getHeight()) - mNpc4.GetHeight());
		
		mNpc5.ResetEnemy(context, R.drawable.npc3, CONSTANTS.NPC_STATE_IDLE, false, CONSTANTS.NPC_STATE_FACING_TOP, true, true, false);
		mNpc5.ResetFoView(context, R.drawable.npcfov3v);
		
		mNpc5.SetNpcPosition((screenWidth / 2) - (mNpc5.GetWidth() / 2), (screenHeight / 2));
		
		mNpc6.ResetEnemy(context, R.drawable.npc4r, CONSTANTS.NPC_STATE_IDLE, true, CONSTANTS.NPC_STATE_FACING_RIGHT, false, false, true);
		mNpc6.ResetFoView(context, R.drawable.npcfov4);
		
		mNpc6.SetNpcPosition(0, wallTop.getHeight() + ( 2 * mNpc6.GetHeight()));
		
		mNpc7.ResetEnemy(context, R.drawable.npc4r, CONSTANTS.NPC_STATE_IDLE, true, CONSTANTS.NPC_STATE_FACING_RIGHT, false, false, true);
		mNpc7.ResetFoView(context, R.drawable.npcfov4);
		
		mNpc7.SetNpcPosition(0, mNpc5.GetPositionY() + ( 2 * mNpc7.GetHeight()));
		
		mNpc8.ResetEnemy(context, R.drawable.npc4l, CONSTANTS.NPC_STATE_IDLE, true, CONSTANTS.NPC_STATE_FACING_LEFT, false, false, true);
		mNpc8.ResetFoView(context, R.drawable.npcfov4);
		
		mNpc8.SetNpcPosition(screenWidth - mNpc8.GetWidth(), wallTop.getHeight() + ( 2 * mNpc8.GetHeight()));
		
		mNpc8.SetWalkLeft(true);
		
		mNpc9.ResetEnemy(context, R.drawable.npc4l, CONSTANTS.NPC_STATE_IDLE, true, CONSTANTS.NPC_STATE_FACING_LEFT, false, false, true);
		mNpc9.ResetFoView(context, R.drawable.npcfov4);
		
		mNpc9.SetNpcPosition(screenWidth - mNpc9.GetWidth(), mNpc5.GetPositionY() + ( 2 * mNpc9.GetHeight()));
		
		mNpc9.SetWalkLeft(true);
		
	}
		
		class GameThread extends Thread 
	{
		private boolean done;
		

		// --- Constructor
		public GameThread() 
		{
			super();

			Log.v( this.getClass().getName(), "public Game1Thread()" );

			done = false;
		}
		
		// Thread's run()
					@Override
					public void run() {
						//Log.v( this.getClass().getName(), "public void run()" );

						Canvas canvas = null;
						long now = SystemClock.elapsedRealtime();

						while (!done) {
							now = SystemClock.elapsedRealtime();

							// Update logic
							if (Start)
							{
								Update();
							}
							//
							

							// Lock canvas so we can update its content 
							canvas = surfaceHolder.lockCanvas();
							
							// Update canvas content
							doDraw( canvas );

							// Free canvas 
							surfaceHolder.unlockCanvasAndPost( canvas );

							// Lock FPS
							if( SystemClock.elapsedRealtime() - now < 30 ) {
								SystemClock.sleep( SystemClock.elapsedRealtime() - now );
							}
						
						}
					}
					
					public void requestExitAndWait() {
						Log.v( this.getClass().getName(), "public void requestExitAndWait()" );

						done = true;

						// Interrupt thread and wait for it to end
						try {
							join();
						} catch( Exception e ) {
						}
					}
					
					public void onWindowResize(int width, int height) {
						Log.v( this.getClass().getName(), "public void onWindowResize(int width, int height)" );
						
						rectwindow = new Rect(0, 0, width, height);


					}

					private void doDraw(Canvas canvas) 
					{
						Log.v( this.getClass().getName(), "private void doDraw(Canvas canvas)" );
						
						if (rectwindow != null)
			        	{
			        		paint.setColor(Color.GREEN); 
			        		canvas.drawRect(rectwindow, paint);
			        	}
						
						if (wallTop != null)
							wallTop.draw(canvas);
						
						if (wallBottom != null)
							wallBottom.draw(canvas);
						
						if (exit != null)
							exit.draw(canvas);
					
					//Player Draw
						if (mPlayer != null)
						{
							mPlayer.Draw(canvas);
						}
					
					//Enemy Draw
						if (mNpc1 != null)
						{
							switch(mNpc1.GetState())
							{
								case CONSTANTS.NPC_STATE_FACING_DOWN:
									mNpc1.SetImage(context, R.drawable.npc1);
									mNpc1.SetFImage(context, R.drawable.npcfov1v);
								break;
								
							case CONSTANTS.NPC_STATE_FACING_LEFT:
								mNpc1.SetImage(context, R.drawable.npc1l);
								mNpc1.SetFImage(context, R.drawable.npcfov1h);
							break;
								
							case CONSTANTS.NPC_STATE_FACING_RIGHT:
								mNpc1.SetImage(context, R.drawable.npc1r);
								mNpc1.SetFImage(context, R.drawable.npcfov1h);
							break;								
							}
							
							mNpc1.DrawEnemy(canvas);
							
							
						}
						
						if (mNpc2 != null)
						{
							switch(mNpc2.GetState())
							{
								case CONSTANTS.NPC_STATE_FACING_DOWN:
									mNpc2.SetImage(context, R.drawable.npc1);
									mNpc2.SetFImage(context, R.drawable.npcfov1v);
								break;
								
							case CONSTANTS.NPC_STATE_FACING_LEFT:
								mNpc2.SetImage(context, R.drawable.npc1l);
								mNpc2.SetFImage(context, R.drawable.npcfov1h);
							break;
								
							case CONSTANTS.NPC_STATE_FACING_RIGHT:
								mNpc2.SetImage(context, R.drawable.npc1r);
								mNpc2.SetFImage(context, R.drawable.npcfov1h);
							break;								
							}
							
							mNpc2.DrawEnemy(canvas);
							
							
						}
						
						if (mNpc3 != null)
						{
							switch(mNpc3.GetState())
							{
								case CONSTANTS.NPC_STATE_FACING_TOP:
									mNpc3.SetImage(context, R.drawable.npc2);
									mNpc3.SetFImage(context, R.drawable.npcfov2v);
								break;
								
							case CONSTANTS.NPC_STATE_FACING_LEFT:
								mNpc3.SetImage(context, R.drawable.npc2l);
								mNpc3.SetFImage(context, R.drawable.npcfov2h);
							break;
								
							case CONSTANTS.NPC_STATE_FACING_RIGHT:
								mNpc3.SetImage(context, R.drawable.npc2r);
								mNpc3.SetFImage(context, R.drawable.npcfov2h);
							break;								
							}
							
							mNpc3.DrawEnemy(canvas);
						
							
						}
						
						if (mNpc4 != null)
						{
							switch(mNpc4.GetState())
							{
								case CONSTANTS.NPC_STATE_FACING_TOP:
									mNpc4.SetImage(context, R.drawable.npc2);
									mNpc4.SetFImage(context, R.drawable.npcfov2v);
								break;
								
							case CONSTANTS.NPC_STATE_FACING_LEFT:
								mNpc4.SetImage(context, R.drawable.npc2l);
								mNpc4.SetFImage(context, R.drawable.npcfov2h);
							break;
								
							case CONSTANTS.NPC_STATE_FACING_RIGHT:
								mNpc4.SetImage(context, R.drawable.npc2r);
								mNpc4.SetFImage(context, R.drawable.npcfov2h);
							break;								
							}
							
							mNpc4.DrawEnemy(canvas);
												
						}
						
						if (mNpc5 != null)
						{
							switch(mNpc5.GetState())
							{
								case CONSTANTS.NPC_STATE_FACING_TOP:
									mNpc5.SetImage(context, R.drawable.npc3);
									mNpc5.SetFImage(context, R.drawable.npcfov3v);
								break;
								
							case CONSTANTS.NPC_STATE_FACING_LEFT:
								mNpc5.SetImage(context, R.drawable.npc3l);
								mNpc5.SetFImage(context, R.drawable.npcfov3h);
							break;
								
							case CONSTANTS.NPC_STATE_FACING_RIGHT:
								mNpc5.SetImage(context, R.drawable.npc3r);
								mNpc5.SetFImage(context, R.drawable.npcfov3h);
							break;	
							
							case CONSTANTS.NPC_STATE_FACING_DOWN:
								mNpc5.SetImage(context, R.drawable.npc3d);
								mNpc5.SetFImage(context, R.drawable.npcfov3v);
							break;
							}
							
							mNpc5.DrawEnemy(canvas);
													
						}
						
						if (mNpc6 != null)
						{
							switch(mNpc6.GetState())
							{
															
								case CONSTANTS.NPC_STATE_FACING_LEFT:
									mNpc6.SetImage(context, R.drawable.npc4l);
									mNpc6.SetFImage(context, R.drawable.npcfov4);
									break;
								
								case CONSTANTS.NPC_STATE_FACING_RIGHT:
									mNpc6.SetImage(context, R.drawable.npc4r);
									mNpc6.SetFImage(context, R.drawable.npcfov4);
									break;						
							}
							
							mNpc6.DrawEnemy(canvas);
														
						}
						
						if (mNpc7 != null)
						{
							switch(mNpc7.GetState())
							{
															
								case CONSTANTS.NPC_STATE_FACING_LEFT:
									mNpc7.SetImage(context, R.drawable.npc4l);
									mNpc7.SetFImage(context, R.drawable.npcfov4);
									break;
								
								case CONSTANTS.NPC_STATE_FACING_RIGHT:
									mNpc7.SetImage(context, R.drawable.npc4r);
									mNpc7.SetFImage(context, R.drawable.npcfov4);
									break;						
							}
							
							mNpc7.DrawEnemy(canvas);
													
						}
						
						if (mNpc8 != null)
						{
							switch(mNpc8.GetState())
							{
															
								case CONSTANTS.NPC_STATE_FACING_LEFT:
									mNpc8.SetImage(context, R.drawable.npc4l);
									mNpc8.SetFImage(context, R.drawable.npcfov4);
									break;
								
								case CONSTANTS.NPC_STATE_FACING_RIGHT:
									mNpc8.SetImage(context, R.drawable.npc4r);
									mNpc8.SetFImage(context, R.drawable.npcfov4);
									break;						
							}
							
							mNpc8.DrawEnemy(canvas);
														
						}
						
						if (mNpc9 != null)
						{
							switch(mNpc9.GetState())
							{
															
								case CONSTANTS.NPC_STATE_FACING_LEFT:
									mNpc9.SetImage(context, R.drawable.npc4l);
									mNpc9.SetFImage(context, R.drawable.npcfov4);
									break;
								
								case CONSTANTS.NPC_STATE_FACING_RIGHT:
									mNpc9.SetImage(context, R.drawable.npc4r);
									mNpc9.SetFImage(context, R.drawable.npcfov4);
									break;						
							}
							
							mNpc9.DrawEnemy(canvas);
														
						}
					//end NPC Draw
						
						
						
						if (pause != null)
						{
							if (isPaused)
							{
								pause.draw(canvas);
							}
						}
							
						
						if (highScore != null)
						{
							if (isNewGame)
							{
								highScore.draw(canvas);
							}
						}
							
						
						if (gameOver != null)
						{
							if (isGameOver == true)
							 	gameOver.draw(canvas);
						}
						
					
					}
					
					public void Update()
					{
						if (!isGameOver && !isPaused)
						{
							if (pressed)
							{	
								isPaused = false;
								mousePos.setX(mousePos.getX());
								mousePos.setY(mousePos.getY());
								mNpc1.SetIdle(false);
								
								if (mousePos != wallBottom.getPosition())
								{
									path[addedNode].setPosition(mousePos);
									
									currentNode = addedNode;
									mPlayer.SetTarget(path[currentNode].getPosition());
									addedNode++;
									if (addedNode >= path.length)
										addedNode = 0;
									if (Start)
										mPlayer.Update();   	//Player Update								
									
								}						
							}
						//Player Update	
							 if (mPlayer.ReachedPositionAt(path[currentNode].getPosition()))
							{
					                currentNode++;
					                if (currentNode >= path.length)
					                {
					                    currentNode = 0;
					                }
					                mPlayer.SetTarget(path[currentNode].getPosition());
					                mPlayer.SetVelocity(1.0f,1.0f);
					              
					        }
						
						//Npc Updates
							 mNpc1.UpdateNpc();
							 mNpc2.UpdateNpc();
							 mNpc3.UpdateNpc();
							 mNpc4.UpdateNpc();
							 mNpc5.UpdateNpc();
							 mNpc6.UpdateNpc();
							 mNpc7.UpdateNpc();
							 mNpc8.UpdateNpc();
							 mNpc9.UpdateNpc();
						
						//Collisions
							if (mPlayer.IsColliding(exit))
						        {
						        	audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.finish));
						        	mPlayer.ResetPlayer(context);
						        }
						        
						        if (mPlayer.IsColliding(wallBottom))
						        {
						        	mPlayer.SetPosition(mPlayer.GetPositionX(), mPlayer.GetPositionY() - wallBottom.getHeight());
						        }
						        
						    
						       
						}
						
						else if (isGameOver)	                	
			                {
			                	gameOver = new Sprite();    	   	
			        			gameOver.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.gameover ));
			        			isPaused = false;	        			
			                	
			                	if (pressed)
			                	{
			                		isGameOver = false;
			                		isPaused = false;
			                		gameOver = null;
			                		currentScreen = CONSTANTS.GAME_OVER;
			                		Reset();			                		
			                	}			                   
			                }	               
			               			            
						else if (isPaused)
			                {	
			                	    pause = new Sprite();
			                		pause.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.pausa ));
				                	pressed = false;
				                	Start = false;
									audioBg.stop();															
																
			                		if (pressed)
			                		{
			                			isPaused = false;
			                			pause = null;
			                		}	                	
			                } 
		
					
					}					
					
	}		
			
		public void SetPause(boolean pause)
		{
			isPaused = pause;
		}

		public boolean GetPaused() 
		{
			return isPaused;
		}
		
		public float GetWallPositionY()
		{
			return wallTop.getPositionY();
		}
		
}
