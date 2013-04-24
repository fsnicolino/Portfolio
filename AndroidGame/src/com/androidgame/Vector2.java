////////////////////////////////////////////////////////
// Portfólio: Estudo Android						  // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////

package com.androidgame;

public class Vector2 {
	
	private float mX;
	private float mY;
	
	public Vector2(float value)
	{
		mX = value;
		mY = value;
	}
	
	public Vector2(float x, float y)
	{
		mX = x;
		mY = y;
	}
	
	public float getX()
	{
		return mX;
	}
	
	public void setX(float x)
	{
		mX = x;
	}
	
	public float getY()
	{
		return mY;
	}
	
	public void setY(float y)
	{
		mY = y;
	}
	
	public void add(Vector2 v)
	{
		mX += v.mX;
		mY += v.mY;
	}
	
	public void sub(Vector2 v)
	{
		mX -= v.mX;
		mY -= v.mY;
	}
	
	public void mul(float value)
	{
		mX *= value;
		mY *= value;
	}
	
	public static final Vector2 One()
	{
		Vector2 ret = new Vector2(1.0f);
		return ret;
	}
	
	public static final Vector2 Zero()
	{
		Vector2 ret = new Vector2(0.0f);
		return ret;
	}
	
	public static final Vector2 add(Vector2 v1, Vector2 v2)
	{
		Vector2 ret = new Vector2(0.0f);
		ret.mX = v1.mX + v2.mX;
		ret.mY = v1.mY + v2.mY;
		return ret;
	}

	public static final Vector2 sub(Vector2 v1, Vector2 v2)
	{
		Vector2 ret = new Vector2(0.0f);
		ret.mX = v1.mX - v2.mX;
		ret.mY = v1.mY - v2.mY;
		return ret;
	}
	
	public static final Vector2 mul(Vector2 v, float value)
	{
		Vector2 ret = new Vector2(0.0f);
		ret.mX = v.mX * value;
		ret.mY = v.mY * value;
		return ret;
	}
	
	public float lengthSquared()
	{
		return (float) Math.sqrt(mX * mX + mY * mY);
	}
	
	public void normalize()
	{
		float length = lengthSquared();
		
		if (length != 0.0f)
		{
			mX /= length;
			mY /= length;
		}
		else
		{
			// Just to avoid division by zero
			mX /= 0.1;
			mY /= 0.1;
		}
	}

}

