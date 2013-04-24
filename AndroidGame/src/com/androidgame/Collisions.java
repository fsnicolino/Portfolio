package com.androidgame;

public class Collisions 
{
	public boolean IsColliding(Sprite sizeA,  Sprite sizeB)
    {
       boolean is_objA_to_left_of_objB = sizeA.getPositionX() + sizeA.getWidth() < sizeB.getPositionX();
       boolean is_objA_to_right_of_objB = sizeA.getPositionX() > sizeB.getPositionX()+ sizeB.getWidth();
       boolean is_objA_above_objB = sizeA.getPositionX() + sizeA.getHeight() < sizeB.getPositionY();
       boolean is_objA_below_objB = sizeA.getPositionX() > sizeB.getPositionY() + sizeB.getHeight();

       return !(is_objA_to_left_of_objB || is_objA_to_right_of_objB || is_objA_above_objB || is_objA_below_objB);
    }
}
