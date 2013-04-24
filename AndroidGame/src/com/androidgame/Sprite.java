////////////////////////////////////////////////////////
// Portfólio: Estudo Android						  // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////
package com.androidgame;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Sprite {
	
    private Bitmap mImage;
    private Vector2 mPosition;
    private Vector2 mTarget;
    private float mRotation;
    private Vector2 mVelocity;
    private float mMaxVelocity;
   
    public Sprite()
    {
    	mImage = null;
    	mPosition = Vector2.Zero();
    	mVelocity = Vector2.Zero();
    	mMaxVelocity = 2.0f;    	
    	 	
    }
    
  
    
    public void setImage(Bitmap image)
    {
        mImage = image;
        
    }
    
    public int getHeight()
    {
    	return mImage.getHeight();
    }
    
    public int getWidth()
    {
    	return mImage.getWidth();
    }

    public Bitmap getImage()
    {
        return mImage;
    }

    public void setPosition(Vector2 pos)
    {
        mPosition = pos;
    }
    
    public void setPosition(float x, float y)
    {
        mPosition.setX(x);
        mPosition.setY(y);
        
    }

    public Vector2 getPosition()
    {
        return mPosition;
    }    
    
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(mImage, mPosition.getX(), mPosition.getY(), null);
    }
    
    public float getPositionX()
    {
    	return mPosition.getX();
    }
    
    public float getPositionY()
    {
    	return mPosition.getY();
    }

	public void setTarget(Vector2 pos) 
	{
		 mTarget = pos;		
	}
	
	 public void SetRotation(float rotation)
     {
         mRotation = rotation;
     }

     public float GetRotation()
     {
         return mRotation;
     }
     
     public boolean reachedPositionAt(Vector2 pos)
     {
     	boolean reachedX = mPosition.getX() >= pos.getX() - 5 && mPosition.getX() <= pos.getX() + 5;
     	boolean reachedY = mPosition.getY() >= pos.getY() - 5 && mPosition.getY() <= pos.getY() + 5;

 		return reachedX && reachedY;
 	}
     
     private void seek()
     {
         Vector2 direction = Vector2.sub(mTarget,mPosition);

         if (direction.lengthSquared() > 0.0f)
         {
             direction.normalize();
             mVelocity = Vector2.mul(direction, mMaxVelocity);
         }
         else
         {
             mVelocity = Vector2.Zero();
         }
     }
     
     public void update()
     {
         seek();
         mPosition.add(mVelocity);
     }



	public void setVelocity(float f, float g) {
		mVelocity.setX(f);
		mVelocity.setY(g);
		
	}
}


