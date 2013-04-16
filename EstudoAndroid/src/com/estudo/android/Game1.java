////////////////////////////////////////////////////////
// Portfólio: Estudo Android						  // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////

package com.estudo.android;

import java.util.Random;




import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Game1  extends SurfaceView implements SurfaceHolder.Callback {
	
	private Audio audioSfx = null;
	private Audio audioBg = null;
	
	private Sprite btnPlay = null;
	private Sprite audioOn = null;
	
	private int currentScreen;
	private boolean Start;
	
	private int HIGH_SCORE = 1;
	private int PAUSE = 2;
	private int GAME_OVER = 3;
	boolean AudioOn;
	boolean isPaused = false;
	boolean isNewGame;
	
	Canvas canvas;

	private Paint paint;
	
	Random random;
	
	int rand;
	
	private Rect rectwindow;
	
	private SurfaceHolder surfaceHolder;
	private boolean hasSurface;
	
	boolean isGameOver = false;
	
	private Context context;

	private Game1Thread gameThread;
	
	int npc1State;
	int npc3State;
	int npc5State;
		
	
	int NPC1_previous_State;
	int NPC3_previous_State;
	
	
	//NPC 6 7 8 9
	boolean isWalkingLeft;
	
	//NPC 1 2
	boolean isFacingLeft;
	boolean isFacingRight;
	boolean isFacingBottom;
	
	//NPC 3/4
	boolean isFacingTop;
	boolean isFacingLeft2;
	boolean isFacingRight2;
	
	//NPC 5
	boolean isFacingTop2;
	boolean isFacingBottom2;
	boolean isFacingLeft3;
	boolean isFacingRight3;
	
	
	
	public int  PLAYER_VELOCITY = (getHeight() / 100) * 2;
	 
	int NPC_VELOCITY = (getWidth() / 100) + 1;
	 
	 static final int NPC_STATE_IDLE = 0;
	 static final int NPC_STATE_FACING_DOWN = 1;
	 static final int NPC_STATE_FACING_RIGHT = 2;
	 static final int NPC_STATE_FACING_LEFT = 3;
	 static final int NPC_STATE_FACING_TOP = 4;
	 static final int NPC_STATE_FOUND_PLAYER = 5;
	 
	 
	 static final int NPC_FACING_TIME = 2000;
	 long timer;
	 long timer2;
	 long timer3;
	 

	 
	  float PLAYER_START_POSITION1;
	  float PLAYER_START_POSITION2;
	  float PLAYER_START_POSITION3;
	  
	  float PLAYER_START_POSITION;
	 
	 
	 
	public Sprite Player;
	public Sprite Npc1;
	public Sprite Npc2;
	public Sprite Npc3;
	public Sprite Npc4;
	public Sprite Npc5;
	public Sprite Npc6;
	public Sprite Npc7;
	public Sprite Npc8;
	public Sprite Npc9;
	public Sprite NpcFov1;
	public Sprite NpcFov2;
	public Sprite NpcFov3;
	public Sprite NpcFov4;
	public Sprite NpcFov5;
	public Sprite NpcFov6;
	public Sprite NpcFov7;
	public Sprite NpcFov8;
	public Sprite NpcFov9;
	public Sprite WallTop;
	public Sprite WallBottom;
	public Sprite Exit;
	public Sprite GameOver;
	public Sprite Pause;
	public Sprite HighScore;
	
	  private Node[] path;
	  private int currentNode;
	  private int addedNode;
	    
	  private boolean followClick;
	  private Vector2 mousePos;
	   
     
	  
	  private boolean pressed;
	  
	  public Game1(Context context) {
	    	super( context );
	    	
	    	Log.v( this.getClass().getName(), "public Game1(Context context)" );

	    	init( context );
		}

		public Game1(Context context, AttributeSet attrs) 
		{
			super( context, attrs );

			Log.v( this.getClass().getName(), "public Game1(Context context, AttributeSet attrs)" );

			init( context );

		}

		public Game1(Context context, AttributeSet attrs, int defStyle) 
		{
			super( context, attrs, defStyle );

			Log.v( this.getClass().getName(), "public Game1(Context context, AttributeSet attrs, int defStyle)" );

			init( context );


		}
		
		public void surfaceCreated(SurfaceHolder holder) {
			Log.v( this.getClass().getName(), "public void surfaceCreated(SurfaceHolder holder)" );

			// Surface created, so we do have a surface
			hasSurface = true;
			isNewGame = true;
			
			beginRun();
		   
			if (audioBg == null)
			{
				audioBg = new Audio();
			}
			
			
			if(audioSfx == null)
			{
				audioSfx = new Audio();
			}
			
			if(btnPlay == null)
			{
				btnPlay = new Sprite();
			}
			btnPlay.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.btnpause ));
			
			if(audioOn == null)
			{
				audioOn = new Sprite();
			}			
			audioOn.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.audion ));
								
			if (Player == null)
			{
				Player = new Sprite();
			}    	
			Player.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.player ));
									
			if (Npc1 == null)
			{
				Npc1 = new Sprite();
			}    	
			Npc1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1 ));
			
			if (Npc2 == null)
			{
				Npc2 = new Sprite();
			}    	
			Npc2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1 ));
			
			if (Npc3 == null)
			{
				Npc3 = new Sprite();
			}    	
			Npc3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2 ));
			
			if (Npc4 == null)
			{
				Npc4 = new Sprite();
			}    	
			Npc4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2 ));
			
			
			if (Npc5 == null)
			{
				Npc5 = new Sprite();
			}    	
			Npc5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc3 ));
			
			if (Npc6 == null)
			{
				Npc6 = new Sprite();
			}    	
			Npc6.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
			
			if (Npc7 == null)
			{
				Npc7 = new Sprite();
			}    	
			Npc7.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
			
			if (Npc8 == null)
			{
				Npc8 = new Sprite();
			}    	
			Npc8.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
			
			if (Npc9 == null)
			{
				Npc9 = new Sprite();
			}    	
			Npc9.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
			
			if (NpcFov1 == null)
			{
				NpcFov1 = new Sprite();
			}    	
			NpcFov1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1v ));
			
			if (NpcFov2 == null)
			{
				NpcFov2 = new Sprite();
			}    	
			NpcFov2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov2v ));
			
			if (NpcFov3 == null)
			{
				NpcFov3 = new Sprite();
			}    	
			NpcFov3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov3v ));
			
			if (NpcFov4 == null)
			{
				NpcFov4 = new Sprite();
			}    	
			NpcFov4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov4 ));
			
			if (WallTop == null)
			{
				WallTop = new Sprite();
			}    	
			WallTop.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
			
			if (WallBottom == null)
			{
				WallBottom = new Sprite();
			}    	
			WallBottom.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
			
			if (Exit == null)
			{
				Exit = new Sprite();
			}    	
			Exit.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.exit ));
		
			
			if (HighScore == null)
			{
				HighScore = new Sprite();
			}    	
			HighScore.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.highscore ));
			
			
			  PLAYER_START_POSITION1 = Player.getWidth() * 2;
			  PLAYER_START_POSITION2 = (getWidth() / 2) - Player.getWidth();
			  PLAYER_START_POSITION3 = getWidth() - Player.getWidth();
			
			Reset();
			// If thread is instantiated, start it
			if( gameThread != null ) {
				gameThread.start();
			}
		}
		
		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.v( this.getClass().getName(), "public void surfaceDestroyed(SurfaceHolder holder)" );

			// Surface destroyed, we no longer have a surface
			hasSurface = false;

			 Player = null;			 
			 Npc1 = null;
			 Npc2 = null;
			 Npc3 = null;
			 Npc4 = null;
			 Npc5 = null;
			 Npc6 = null;
			 Npc7 = null;
			 Npc8 = null;
			 Npc9 = null;
			 NpcFov1 = null;
			 NpcFov2 = null;
			 NpcFov3 = null;
			 NpcFov4 = null;
			 NpcFov5 = null;
			 NpcFov6 = null;
			 NpcFov7 = null;
			 NpcFov8 = null;
			 NpcFov9 = null;
			 WallTop = null;
			 Exit = null;
			 GameOver = null;
			 Pause = null;
			 HighScore = null;

			endRun();
		}
			
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
		{
			Log.v( this.getClass().getName(), "public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)" );

			// Surface or window had its size changed (e.g. device orientation changed)
			if( gameThread != null ) {
				gameThread.onWindowResize( width, height );
			}
		}	
			
		public boolean onTouchEvent(MotionEvent event)
		{
			Log.v( this.getClass().getName(), "public boolean onTouchEvent(MotionEvent event)" );

			pressed = true;
			isNewGame = false;
			mousePos.setX(event.getX());
			mousePos.setY(event.getY());
			Player.setTarget(mousePos);
			
			if(currentScreen == HIGH_SCORE)
			{
				Start = true;
				currentScreen = 0;
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
				gameThread = new Game1Thread();

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
			
			isPaused = false;
			
			isGameOver = false;
			
			currentScreen = HIGH_SCORE;
			
			audioBg.playFromRes(getResources().openRawResourceFd(R.raw.background));	
			audioBg.Looping(true);	
			
			
			
			btnPlay = new Sprite();
			btnPlay.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.btnpause ));
			
			audioOn = new Sprite();
			audioOn.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.audion ));
				
										
			
			Npc1 = new Sprite();
			Npc1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1 ));
			
			Npc2 = new Sprite();
			Npc2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1 ));
			
			Npc3 = new Sprite();
			Npc3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2 ));
			
			Npc4 = new Sprite();
			Npc4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2 ));
			
			Npc5 = new Sprite();
			Npc5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc3 ));
			
			Npc6 = new Sprite();
			Npc6.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
			
			Npc7 = new Sprite();
			Npc7.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
			
			Npc8 = new Sprite();
			Npc8.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
			
			Npc9 = new Sprite();
			Npc9.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
			
			NpcFov1 = new Sprite();
			NpcFov1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1v ));
			
			NpcFov2 = new Sprite();
			NpcFov2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1v ));
			
			NpcFov3 = new Sprite();
			NpcFov3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov2v ));
			
			NpcFov4 = new Sprite();
			NpcFov4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov2v ));
			
			NpcFov5 = new Sprite();
			NpcFov5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov3v ));
			
			NpcFov6 = new Sprite();
			NpcFov6.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov4 ));
			
			NpcFov7 = new Sprite();
			NpcFov7.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov4 ));
			
			NpcFov8 = new Sprite();
			NpcFov8.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov4 ));
			
			NpcFov9 = new Sprite();
			NpcFov9.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov4 ));
			
			WallTop = new Sprite();
			WallTop.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
			
			WallBottom = new Sprite();
			WallBottom.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.wall ));
			
			Exit = new Sprite();
			Exit.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.exit ));
			
									
			HighScore = new Sprite();
			HighScore.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.highscore ));
			
									
			
			Npc1.setPosition((getWidth() / 2) - (3 * Npc1.getWidth()),  WallTop.getHeight());
			NpcFov1.setPosition(Npc1.getPositionX(), Npc1.getPositionY() + Npc1.getHeight());
			Npc2.setPosition((getWidth() / 2) + (2 * Npc2.getWidth()),  WallTop.getHeight());
			NpcFov2.setPosition(Npc2.getPositionX(), Npc2.getPositionY() + Npc2.getHeight());			
			Npc3.setPosition((getWidth() / 2) - (3 * Npc3.getWidth()), (getHeight() - WallBottom.getHeight()) - Npc3.getHeight());
			NpcFov3.setPosition(Npc3.getPositionX(), Npc3.getPositionY() - NpcFov3.getHeight());
			Npc4.setPosition((getWidth() / 2) + (2 * Npc4.getWidth()), (getHeight() - WallBottom.getHeight()) - Npc4.getHeight());
			NpcFov4.setPosition(Npc4.getPositionX(), Npc4.getPositionY() - NpcFov4.getHeight());
			Npc5.setPosition((getWidth() / 2) - (Npc5.getWidth() / 2), (getWidth() / 2) + (3 * Npc5.getHeight()));
			NpcFov5.setPosition(Npc5.getPositionX(), Npc5.getPositionY() - NpcFov5.getHeight());
			Npc6.setPosition(0,  WallTop.getHeight() + ( 2 * Npc6.getHeight()));
			NpcFov6.setPosition(Npc6.getPositionX() + Npc6.getWidth(), Npc6.getPositionY());
			Npc7.setPosition(0, Npc5.getPositionY() + (2 * Npc7.getHeight()));
			NpcFov7.setPosition(Npc7.getPositionX() + Npc7.getWidth(), Npc7.getPositionY());
			Npc8.setPosition(getWidth() - Npc8.getWidth(), Npc5.getPositionY() - (2 * Npc8.getHeight()));
			NpcFov8.setPosition(Npc8.getPositionX() - NpcFov8.getWidth(), Npc8.getPositionY());
			Npc9.setPosition(getWidth() - Npc9.getWidth(), NpcFov4.getPositionY());
			NpcFov9.setPosition(Npc9.getPositionX() - NpcFov9.getWidth(), Npc9.getPositionY());
			WallTop.setPosition(0,0);
			WallBottom.setPosition(0, getHeight() - WallBottom.getHeight());
			Exit.setPosition((getWidth() / 2) - (Exit.getWidth() / 2), WallBottom.getPositionY());
			btnPlay.setPosition(Exit.getPositionX() + (2 * btnPlay.getWidth()) , WallBottom.getPositionY());
			audioOn.setPosition(btnPlay.getPositionX() + (2 * audioOn.getWidth() ), WallBottom.getPositionY());
			
			//GameOver.setPosition(0, 0 );
			HighScore.setPosition(0, 0);
			//Pause.setPosition(0, 0);
					
			PlayerReset();
			
			isWalkingLeft = true;
			
			isFacingBottom = true;
			isFacingTop = true;
			
			isFacingLeft = false;
			isFacingRight = false;
				
			isFacingLeft2 = false;
			isFacingRight2 = false;
			
			isFacingTop2 = true;
			isFacingBottom2 = false;
			isFacingLeft3 = false;
			isFacingRight3 = false;
			
			 
			timer = System.currentTimeMillis();
			timer2 = System.currentTimeMillis();
			timer3 = System.currentTimeMillis();
			
			 npc1State = NPC_STATE_FACING_DOWN;
			 npc3State = NPC_STATE_FACING_TOP;
			 npc5State = NPC_STATE_FACING_TOP;
			 
			 NPC1_previous_State = NPC_STATE_FACING_LEFT;
			 NPC3_previous_State = NPC_STATE_FACING_RIGHT;
			 
			
			 
			 
		}
		
		class Game1Thread extends Thread 
		{

			private boolean done;

			// --- Constructor
			public Game1Thread() {
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

			// --- Our own methods
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

			private void doDraw(Canvas canvas) {
				//Log.v( this.getClass().getName(), "private void doDraw(Canvas canvas)" );
				
				if (rectwindow != null)
	        	{
	        		paint.setColor(Color.GREEN); 
	        		canvas.drawRect(rectwindow, paint);
	        	}
				
				if (WallTop != null)
					WallTop.draw(canvas);
				
				if (WallBottom != null)
					WallBottom.draw(canvas);
				
			
				if (Player != null)
					Player.draw(canvas);
				
				if (Npc1 != null)
				{
					if (isFacingBottom)
					{
						Npc1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1 ));				
					}
					
					else if (isFacingLeft)
					{
						Npc1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1l ));				
					}
					
					else if (isFacingRight)
					{
						Npc1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1r ));
					}
					
					Npc1.draw(canvas);  
				}
					
				
				if (Npc2 != null)
				{
					if (isFacingBottom)
					{
						Npc2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1 ));				
					}
					
					else if (isFacingLeft)
					{
						Npc2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1l ));				
					}
					
					else if (isFacingRight)
					{
						Npc2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc1r ));
					}
					
					Npc2.draw(canvas);
				}
					
				
				if (Npc3 != null)
				{
					if (isFacingTop)
					{
						Npc3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2 ));				
					}
					
					else if (isFacingLeft2)
					{
						Npc3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2l ));				
					}
					
					else if (isFacingRight2)
					{
						Npc3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2r ));
					}
					
					Npc3.draw(canvas);
				}
					
				
				if (Npc4 != null)
				{
					if (isFacingTop)
					{
						Npc4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2 ));				
					}
					
					else if (isFacingLeft2)
					{
						Npc4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2l ));				
					}
					
					else if (isFacingRight2)
					{
						Npc4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc2r ));
					}
					
					Npc4.draw(canvas);
				}
					
				
				if (Npc5 != null)
				{
					if (isFacingTop2)
					{
						Npc5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc3 ));				
					}
					
					else if (isFacingBottom2)
					{
						Npc5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc3d ));
					}
					
					else if (isFacingLeft3)
					{
						Npc5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc3l ));				
					}
					
					else if (isFacingRight3)
					{
						Npc5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc3r ));
					}
					
					Npc5.draw(canvas);
				}
					
				
				if (Npc6 != null)
				{
					if (isWalkingLeft)
					{
						Npc6.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
					}
					
					else
					{
						Npc6.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
					}
					
					Npc6.draw(canvas);
				}
					
				
				if (Npc7 != null)
				{
					if (isWalkingLeft)
					{
						Npc7.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
					}
					
					else
					{
						Npc7.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
					}
					
					Npc7.draw(canvas);
				}
					
				
				if (Npc8 != null)
				{
					if (isWalkingLeft)
					{
						Npc8.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
					}
					
					else
					{
						Npc8.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
					}
					
					Npc8.draw(canvas);
				}
					
				
				if (Npc9 != null)
				{
					if (isWalkingLeft)
					{
						Npc9.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4l ));
					}
					
					else
					{
						Npc9.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npc4r ));
					}
					
					Npc9.draw(canvas);
				}
					
					
				if (NpcFov1 != null)
				{
					if (isFacingBottom)
					{
						NpcFov1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1v ));				
					}
					
					else if (isFacingLeft || isFacingRight)
					{
						NpcFov1.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1h ));				
					}				  
					  
					 NpcFov1.draw(canvas);
				}
					
				
				if (NpcFov2 != null)
				{
					if (isFacingBottom)
					{
						NpcFov2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1v ));				
					}
					
					else if (isFacingLeft || isFacingRight)
					{
						NpcFov2.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1h ));				
					}				  
					
					NpcFov2.draw(canvas);
				}
					
				
				if (NpcFov3 != null)
				{
					if (isFacingTop)
					{
						NpcFov3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1v ));				
					}
					
					else if (isFacingLeft || isFacingRight)
					{
						NpcFov3.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov1h ));				
					}	
					
					NpcFov3.draw(canvas);
				}
					
				
				if (NpcFov4 != null)
				{
					if (isFacingTop)
					{
						NpcFov4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov2v ));				
					}
					
					else if (isFacingLeft || isFacingRight)
					{
						NpcFov4.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov2h ));				
					}	
					
					NpcFov4.draw(canvas);
				}
					
				
				if (NpcFov5 != null)
				{
					if (isFacingTop2 || isFacingBottom2)
					{
						NpcFov5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov3v ));				
					}
					
					else if (isFacingLeft3 || isFacingRight3)
					{
						NpcFov5.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.npcfov3h ));				
					}	
					
					NpcFov5.draw(canvas);
				}
					
				
				if (NpcFov6 != null)
					NpcFov6.draw(canvas);
				
				if (NpcFov7 != null)
					NpcFov7.draw(canvas);
				
				if (NpcFov8 != null)
					NpcFov8.draw(canvas);
				
				if (NpcFov9 != null)
					NpcFov9.draw(canvas);
				
							
				if (Exit != null)
					Exit.draw(canvas);
				
				if (Pause != null)
				{
					if (isPaused)
					{
						Pause.draw(canvas);
					}
				}
					
				
				if (HighScore != null)
				{
					if (isNewGame)
					{
						HighScore.draw(canvas);
					}
				}
					
				
				if (GameOver != null)
				{
					if (isGameOver == true)
					 	GameOver.draw(canvas);
				}
				
				if (audioOn != null)
				{
					if (AudioOn = true)
					{
						audioOn.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.audion ));
						audioOn.draw(canvas);
					}
					
					else if (AudioOn = false)
					{
						audioOn.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.audiooff ));
						audioOn.draw(canvas);
					}
				}
				
				if (btnPlay != null)
				{
					if (isPaused == false)
					{
						btnPlay.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.btnpause ));
						btnPlay.draw(canvas);
					}
					
					else if (isPaused == true)
					{
						btnPlay.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.btnplay ));
						btnPlay.draw(canvas);
					}
				}
				
						
			}

			public void Update()
			{
				if (!isGameOver)
				{
					if (pressed)
					{						
						
							mousePos.setX(mousePos.getX());
							mousePos.setY(mousePos.getY());
							
							if (mousePos == audioOn.getPosition())
							{
								if(AudioOn == true)
								{
									AudioOn = false;
								}
								else
								{
									AudioOn = true;
								}
							}
							
							else if (mousePos != WallBottom.getPosition())
							{
								path[addedNode].setPosition(mousePos);
								
								currentNode = addedNode;
								Player.setTarget(path[currentNode].getPosition());
								addedNode++;
								if (addedNode >= path.length)
									addedNode = 0;
								if (Start)
									Player.update();   
							}						
					
		             }				
					
					 if (Player.reachedPositionAt(path[currentNode].getPosition()))
						{
			                currentNode++;
			                if (currentNode >= path.length)
			                {
			                    currentNode = 0;
			                }
			                Player.setTarget(path[currentNode].getPosition());
			                Player.setVelocity(1.0f,1.0f);
			              
			            }				
					 
			        UpdateNpc();
			        
			        if (IsColliding(Player, Exit))
			        {
			        	audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.finish));
			        	PlayerReset();
			        }
			        
			        if (IsColliding(Player,WallBottom))
			        {
			        	Player.setPosition(Player.getPositionX(), Player.getPositionY());
			        }
			        
			       
				}
				
				else // Game over, do not update game
	            {
	                if (isGameOver)	                	
	                {
	                	GameOver = new Sprite();    	   	
	        			GameOver.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.gameover ));
	        			
	                	
	                	if (pressed)
	                	{
	                		isGameOver = false;
	                		isPaused = false;
	                		GameOver = null;
	                		currentScreen = GAME_OVER;
	                		Reset();
	                		
	                	}
	                   
	                }
	               
	               
	                }
	                if (isPaused)
	                {
	                	
	                	Pause = new Sprite();
	        			Pause.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.pausa ));
	                	pressed = false;
	                	Start = false;
						audioBg.stop();
	                	if (pressed)
	                	{
	                		isPaused = false;
	                		Pause = null;
	                	}
	                } 
	            }
							
			}				
				          
		
			
			 public boolean IsColliding(Sprite sizeA,  Sprite sizeB)
		     {
		        boolean is_objA_to_left_of_objB = sizeA.getPositionX() + sizeA.getWidth() < sizeB.getPositionX();
		        boolean is_objA_to_right_of_objB = sizeA.getPositionX() > sizeB.getPositionX()+ sizeB.getWidth();
		        boolean is_objA_above_objB = sizeA.getPositionY() + sizeA.getHeight() < sizeB.getPositionY();
		        boolean is_objA_below_objB = sizeA.getPositionY() > sizeB.getPositionY() + sizeB.getHeight();

		        return !(is_objA_to_left_of_objB || is_objA_to_right_of_objB || is_objA_above_objB || is_objA_below_objB);
		     }


		private void UpdateNpc() 
		{				
			
			if (isWalkingLeft)
			{
				Npc8.setPosition(Npc8.getPositionX() - NPC_VELOCITY, Npc8.getPositionY()); 
            	NpcFov8.setPosition(Npc8.getPositionX() - NpcFov8.getWidth(), Npc8.getPositionY());
    			Npc9.setPosition(Npc9.getPositionX() - NPC_VELOCITY, Npc9.getPositionY());
    			NpcFov9.setPosition(Npc9.getPositionX() - NpcFov9.getWidth(), Npc9.getPositionY());
    			
    			Npc6.setPosition(Npc6.getPositionX() + NPC_VELOCITY, Npc6.getPositionY()); 
            	NpcFov6.setPosition(Npc6.getPositionX() + Npc6.getWidth(), Npc6.getPositionY());
    			Npc7.setPosition(Npc6.getPositionX() + NPC_VELOCITY, Npc7.getPositionY());
    			NpcFov7.setPosition(Npc7.getPositionX() + Npc7.getWidth(), Npc7.getPositionY()); 
    			
    			if (Npc6.getPositionX()  >= getWidth() - Npc6.getWidth() && Npc8.getPositionX() <= 0)
    			{
    				isWalkingLeft = false;
    			}    			
			}			
			else
			{
				Npc8.setPosition(Npc8.getPositionX() + NPC_VELOCITY, Npc8.getPositionY()); 
            	NpcFov8.setPosition(Npc8.getPositionX() + Npc8.getWidth(), Npc8.getPositionY());
    			Npc9.setPosition(Npc9.getPositionX() + NPC_VELOCITY, Npc9.getPositionY());
    			NpcFov9.setPosition(Npc9.getPositionX() + Npc9.getWidth(), Npc9.getPositionY());
    			
    			Npc6.setPosition(Npc6.getPositionX() - NPC_VELOCITY, Npc6.getPositionY()); 
            	NpcFov6.setPosition(Npc6.getPositionX() - NpcFov6.getWidth(), Npc6.getPositionY());
    			Npc7.setPosition(Npc6.getPositionX() - NPC_VELOCITY, Npc7.getPositionY());
    			NpcFov7.setPosition(Npc7.getPositionX() - NpcFov7.getWidth(), Npc7.getPositionY()); 
    			
    			if (Npc6.getPositionX() <= 0 && Npc8.getPositionX() >= getWidth() - Npc8.getWidth())
    			{
    				isWalkingLeft = true;    				
    			}	                           
				
			}
			//NPC 1 e 2
			if (isFacingBottom)
			{				
				NpcFov1.setPosition(Npc1.getPositionX(), Npc1.getPositionY() + Npc1.getHeight());
				NpcFov2.setPosition(Npc2.getPositionX(), Npc2.getPositionY() + Npc2.getHeight());			
			}
			
			else if (isFacingLeft)
			{						
				NpcFov1.setPosition(Npc1.getPositionX() - NpcFov1.getWidth(), Npc1.getPositionY());
				NpcFov2.setPosition(Npc2.getPositionX()- NpcFov2.getWidth(), Npc2.getPositionY());				
			}
			
			else if (isFacingRight)
			{							
				NpcFov1.setPosition(Npc1.getPositionX() + Npc1.getWidth(), Npc1.getPositionY());
				NpcFov2.setPosition(Npc2.getPositionX() + Npc2.getWidth(), Npc2.getPositionY());				
			}
			
			
			//NPC 3 e 4
			if (isFacingTop)
			{
				
				NpcFov3.setPosition(Npc3.getPositionX(), Npc3.getPositionY() - NpcFov3.getHeight());
				NpcFov4.setPosition(Npc4.getPositionX(), Npc4.getPositionY() - NpcFov4.getHeight());
				
			}
			
			else if (isFacingLeft2)
			{						
				NpcFov3.setPosition(Npc3.getPositionX() - NpcFov3.getWidth(), Npc3.getPositionY());
				NpcFov4.setPosition(Npc4.getPositionX()- NpcFov4.getWidth(), Npc4.getPositionY());				
			}
			
			else if (isFacingRight2)
			{							
				NpcFov3.setPosition(Npc3.getPositionX() + Npc3.getWidth(), Npc3.getPositionY());
				NpcFov4.setPosition(Npc4.getPositionX() + Npc4.getWidth(), Npc4.getPositionY());				
			}
			
			//NPC 5
			if (isFacingTop2)
			{
				NpcFov5.setPosition(Npc5.getPositionX(), Npc5.getPositionY() - NpcFov5.getHeight());
			}
			
			else if (isFacingLeft3)
			{						
				NpcFov5.setPosition(Npc5.getPositionX() - NpcFov5.getWidth(), Npc5.getPositionY());
								
			}
			
			else if (isFacingRight3)
			{							
				NpcFov5.setPosition(Npc5.getPositionX() + Npc5.getWidth(), Npc5.getPositionY());
							
			}
			 
			else if (isFacingBottom2)
			{				
				NpcFov5.setPosition(Npc5.getPositionX(), Npc5.getPositionY() + Npc5.getHeight());
						
			}
			 
			 
			 switch (npc1State)
	            {
	                case NPC_STATE_IDLE:
	                    break;

	                case NPC_STATE_FACING_DOWN:	                	
	                	
	        			if (  System.currentTimeMillis() - timer > NPC_FACING_TIME)
	        			{	        				
	        				
	        				if (NPC1_previous_State == NPC_STATE_FACING_LEFT)
	        				{
	        					npc1State = NPC_STATE_FACING_RIGHT;
	        					isFacingRight = true;
	        					isFacingBottom = false;
	        					timer = System.currentTimeMillis();
	        				}
	        				
	        				else if (NPC1_previous_State == NPC_STATE_FACING_RIGHT)
	        				{
	        					npc1State = NPC_STATE_FACING_LEFT;
	        					isFacingLeft = true;
	        					isFacingBottom = false;
	        					timer = System.currentTimeMillis();
	        				}
	        			}	                
	                   
	                    break;
	                 
	                case NPC_STATE_FACING_LEFT:
	                	
	                	if (System.currentTimeMillis() - timer > NPC_FACING_TIME)
	        			{
	                		isFacingBottom = true;
	                		npc1State = NPC_STATE_FACING_DOWN;
	                		NPC1_previous_State = NPC_STATE_FACING_LEFT;
	                		timer = System.currentTimeMillis();
	                		isFacingLeft = false;
	        			}
	                	
	                    break;
	                    
	                case NPC_STATE_FACING_RIGHT:
	                	
	                	if (System.currentTimeMillis() - timer > NPC_FACING_TIME)
	        			{
	                		isFacingBottom = true;
	                		timer = System.currentTimeMillis();
	                		isFacingRight = false;
	                		npc1State = NPC_STATE_FACING_DOWN;
	                		NPC1_previous_State = NPC_STATE_FACING_RIGHT;
	                		
	        			}
	                	 
	                    break;	                 
	                         
	                
	                case NPC_STATE_FOUND_PLAYER:
	                {
	                	audioBg.stop();
	                	isGameOver = true;
	                	pressed = false;
	                	audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.atencao));
	                	
	                } 	                			                
	                    break;
	            }
			 
			 switch (npc3State)
	            {
	                case NPC_STATE_IDLE:
	                    break;

	                case NPC_STATE_FACING_TOP:	                	
	                	
	        			if (  System.currentTimeMillis() - timer2 > NPC_FACING_TIME)
	        			{	        				
	        				
	        				if (NPC3_previous_State == NPC_STATE_FACING_LEFT)
	        				{
	        					npc3State = NPC_STATE_FACING_RIGHT;
	        					isFacingRight2 = true;
	        					isFacingTop = false;
	        					timer2 = System.currentTimeMillis();
	        				}
	        				
	        				else if (NPC3_previous_State == NPC_STATE_FACING_RIGHT)
	        				{
	        					npc3State = NPC_STATE_FACING_LEFT;
	        					isFacingLeft2 = true;
	        					isFacingTop = false;
	        					timer2 = System.currentTimeMillis();
	        				}
	        			}	                
	                   
	                    break;
	                 
	                case NPC_STATE_FACING_LEFT:
	                	
	                	if (System.currentTimeMillis() - timer2 > NPC_FACING_TIME)
	        			{
	                		isFacingTop = true;
	                		npc3State = NPC_STATE_FACING_TOP;
	                		NPC3_previous_State = NPC_STATE_FACING_LEFT;
	                		timer2 = System.currentTimeMillis();
	                		isFacingLeft2 = false;
	        			}
	                	
	                    break;
	                    
	                case NPC_STATE_FACING_RIGHT:
	                	
	                	if (System.currentTimeMillis() - timer2 > NPC_FACING_TIME)
	        			{
	                		isFacingTop = true;
	                		timer2 = System.currentTimeMillis();
	                		isFacingRight2 = false;
	                		npc3State = NPC_STATE_FACING_TOP;
	                		NPC3_previous_State = NPC_STATE_FACING_RIGHT;
	                		
	        			}
	                	 
	                    break;	                 
	                         
	                
	                case NPC_STATE_FOUND_PLAYER:		               
	                {
	                	audioBg.stop();
	                	isGameOver = true;
	                	pressed = false;
	                	audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.atencao));
	                }                	
		                
	                	break;
	            }
			 
			 switch (npc5State)
	            {
	                case NPC_STATE_IDLE:
	                    break;
	                    
	                case NPC_STATE_FACING_TOP:	                	
	                	
	        			if (  System.currentTimeMillis() - timer3 > NPC_FACING_TIME)
	        			{	        				
	        				npc5State = NPC_STATE_FACING_RIGHT;
	        				isFacingRight3 = true;
	        				isFacingTop2 = false;
	        				timer3 = System.currentTimeMillis();	        			
	        			}	                

	                case NPC_STATE_FACING_DOWN:	                	
	                	
	        			if (  System.currentTimeMillis() - timer3 > NPC_FACING_TIME)
	        			{
	        				npc5State = NPC_STATE_FACING_LEFT;
	        				isFacingLeft3 = true;
	        				isFacingBottom2 = false;
	        				timer3 = System.currentTimeMillis();
	        				
	        			}	                
	                   
	                    break;
	                 
	                case NPC_STATE_FACING_LEFT:
	                	
	                	if (System.currentTimeMillis() - timer3 > NPC_FACING_TIME)
	        			{
	                		isFacingTop2 = true;
	                		npc5State = NPC_STATE_FACING_TOP;
	                		timer3 = System.currentTimeMillis();
	                		isFacingLeft3 = false;
	        			}
	                	
	                    break;
	                    
	                case NPC_STATE_FACING_RIGHT:
	                	
	                	if (System.currentTimeMillis() - timer3 > NPC_FACING_TIME)
	        			{
	                		isFacingBottom2 = true;
	                		timer3 = System.currentTimeMillis();
	                		isFacingRight3 = false;
	                		npc5State = NPC_STATE_FACING_DOWN;
	                		
	                		
	        			}
	                	 
	                    break;	                 
	                         
	                
	                case NPC_STATE_FOUND_PLAYER:
	                {
	                	audioBg.stop();
	                	isGameOver = true;
	                	pressed = false;
	                	audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.atencao));
	                	
	                }	
		                
	                    break;
	            }
			 
			 if (IsColliding(Player,Npc1) || IsColliding(Player, NpcFov1))
		     {
				 
				 npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;		       	
		     }
			 
			 else if (IsColliding(Player,Npc2) || IsColliding(Player, NpcFov2))
		     {
				
				 npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;
		     }
			 
			 else if (IsColliding(Player,Npc3) || IsColliding(Player, NpcFov3))
		     {
				
				 npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;
		     }
			 
			 else if (IsColliding(Player,Npc4) || IsColliding(Player, NpcFov4))
		     {
				
				 npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;
		     }
			 
			 else if (IsColliding(Player,Npc5) || IsColliding(Player, NpcFov5))
		     {
				
				 npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;
		     }
			 
			 else if (IsColliding(Player,Npc6) || IsColliding(Player, NpcFov6))
		     {
				 audioBg.stop();
				 isGameOver = true;
				 pressed = false;				
				 audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.atencao));
		   	   	 npc1State = NPC_STATE_FOUND_PLAYER;
		   	   	 npc3State = NPC_STATE_FOUND_PLAYER;
		   	   	 npc5State = NPC_STATE_FOUND_PLAYER;
		       
		     }
			 
			 else if (IsColliding(Player,Npc7) || IsColliding(Player, NpcFov7))
		     {
				 audioBg.stop();
				 isGameOver = true;
			     pressed = false;
			     audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.atencao));
			     npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;
		     }
			 
			 else if (IsColliding(Player,Npc8) || IsColliding(Player, NpcFov8))
		     {
				 audioBg.stop();
				 isGameOver = true;
			     pressed = false;
			     audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.atencao));
			     npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;
		     }
			 
			 else if (IsColliding(Player,Npc9) || IsColliding(Player, NpcFov9))
		     {
				 audioBg.stop();
			     isGameOver = true;
			     pressed = false;			    
			     audioSfx.playFromRes(getResources().openRawResourceFd(R.raw.atencao));
			     npc1State = NPC_STATE_FOUND_PLAYER;
			   	 npc3State = NPC_STATE_FOUND_PLAYER;
			     npc5State = NPC_STATE_FOUND_PLAYER;
		     }		
			}
		
	
		private void PlayerReset()
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
			
			Player = new Sprite();
			Player.setImage(BitmapFactory.decodeResource( context.getResources(), R.drawable.player ));	
			
			Player.setPosition(PLAYER_START_POSITION, WallTop.getPositionY());
			
			
		}
		
		public void SetPause(boolean pause)
		{
			isPaused = pause;
		}
		
}

