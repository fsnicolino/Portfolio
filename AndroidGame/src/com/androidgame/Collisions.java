package com.androidgame;

public class Collisions //Clase usada para verificar as colisões do Jogador com Inimigos e Jogador com Parede/Saída
{
	public boolean IsColliding(Player sizeA,  Sprite sizeB)
    {
       boolean is_objA_to_left_of_objB = sizeA.GetPositionX() + sizeA.GetWidth() < sizeB.getPositionX();
       boolean is_objA_to_right_of_objB = sizeA.GetPositionX() > sizeB.getPositionX()+ sizeB.getWidth();
       boolean is_objA_above_objB = sizeA.GetPositionX() + sizeA.GetHeight() < sizeB.getPositionY();
       boolean is_objA_below_objB = sizeA.GetPositionX() > sizeB.getPositionY() + sizeB.getHeight();

       return !(is_objA_to_left_of_objB || is_objA_to_right_of_objB || is_objA_above_objB || is_objA_below_objB);
    }
	
	public boolean IsColliding(Player sizeA,  Enemy sizeB)
    {
       boolean is_objA_to_left_of_objB = sizeA.GetPositionX() + sizeA.GetWidth() < sizeB.GetPositionX();
       boolean is_objA_to_right_of_objB = sizeA.GetPositionX() > sizeB.GetPositionX()+ sizeB.GetWidth();
       boolean is_objA_above_objB = sizeA.GetPositionX() + sizeA.GetHeight() < sizeB.GetPositionY();
       boolean is_objA_below_objB = sizeA.GetPositionX() > sizeB.GetPositionY() + sizeB.GetHeight();

       return !(is_objA_to_left_of_objB || is_objA_to_right_of_objB || is_objA_above_objB || is_objA_below_objB);
    }
}
