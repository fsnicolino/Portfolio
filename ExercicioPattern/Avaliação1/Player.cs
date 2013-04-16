////////////////////////////////////////////////////////
// Portfólio: Estudo de Design Patterns e Parallax    // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////
using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;
using Microsoft.Xna.Framework;
using Microsoft.Xna.Framework.Audio;
using Microsoft.Xna.Framework.Content;
using Microsoft.Xna.Framework.GamerServices;
using Microsoft.Xna.Framework.Graphics;
using Microsoft.Xna.Framework.Input;
using Microsoft.Xna.Framework.Media;
using Microsoft.Xna.Framework.Net;
using Microsoft.Xna.Framework.Storage;

namespace ExercicioPattern
{
    public class Player : Observador
    {
        private Vector2 p_position;
        private Texture2D p_sprite;
        private static Player instance;

             
        
       
        private Player()
        {
            p_sprite = null;
            p_position = new Vector2(0.0f, 0.0f);
           
        }

        public static Player getInstance()
        {
            if (instance == null)
            {
                instance = new Player();
            }
            return instance;
        }

        public void Draw(GameTime gameTime, SpriteBatch spriteBatch)
        {
            spriteBatch.Draw(p_sprite, p_position, Color.White);  
        }

        public float GetX()
        {
            return (p_position.X); 
        }

        public float GetY()
        {
            return (p_position.Y); 
        }

        public int GetHeight() 
        {
            if (p_sprite != null) 
            {
                return p_sprite.Height;
            }

            return (0); 
        }

        public Vector2 GetPosition() 
        {
            return (p_position);
        }



        public int GetWidth() 
        {
            if (p_sprite != null) 
            {
                return (p_sprite.Width);
            }

            return (0); 
        }

        public void Load(Texture2D sprt)
        {
            p_sprite = sprt; 
        }

        public void SetPosition(float x, float y) 
        {
            p_position.X = x;
            p_position.Y = y;
        }

        public void SetX(float x)
        {
            p_position.X = x;
        }

        public void SetY(float y) 
        {
            p_position.Y = y;
        }

        

        public void Unload() 
        {
            p_sprite.Dispose();
        }

        public void Update(GameTime gameTime)
        {
            
        }

        public void atualizar(int direcao)
        {
            if (direcao == 1)
            {
                p_position.X -= Constants.PLAYER_VELOCITY;

                if (p_position.X < 0)
                {
                    p_position.X = 0;
                }
            }

            else if (direcao == 2)
            {
                p_position.X += Constants.PLAYER_VELOCITY;

                if (p_position.X > (Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH))
                {
                    p_position.X = (Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH);
                }
            }
        }

        public void inscreverObservador(Observavel novoObservavel)
        {
            novoObservavel.adicionaOuvinte(this);
        }

        public void deletarObservador(Observavel delObservavel)
        {
            delObservavel.retirarOuvinte(this);
        }

       
    }
}
