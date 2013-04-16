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
    public class ParallaxLayer
    {
        private Rectangle destOne;
        private Rectangle destTwo;
        private Texture2D image;
        private Rectangle origin;
        private Vector2 pos;
        private Vector2 velo;
        private Contexto strategy = new Contexto();

        public ParallaxLayer(Vector2 pos, Vector2 velo)
        {
            this.pos = pos;
            this.velo = velo;
        }

                    
        public void Draw(GameTime gameTime, SpriteBatch spriteBatch)
        {
            if (image != null) 
            {
                spriteBatch.Draw(image, destOne, Color.White);
                spriteBatch.Draw(image, destTwo, Color.White);
            }
        }

        public void LoadContent(ContentManager content, GraphicsDeviceManager graphics, String filename)
        {
            image = content.Load<Texture2D>(filename); 

            origin = new Rectangle((int)pos.X, (int)pos.Y, image.Width, image.Height); 

            destOne = origin;
            destTwo = origin; 
        }
  
        public void Unload()
        {
            if (image != null) 
            {
                image.Dispose();
            }
        }

        public void Update(int direcao)
        {
             float posicao = Player.getInstance().GetX();
             
             strategy.setPosicao(pos);
             strategy.setVelocidade(velo);

            if (direcao == 1 && posicao == 0)
            {
                strategy.setVelocidade(new VeloEsquerda());
                pos = strategy.processaVelocidade();
            }
            else if (direcao == 2 && posicao == (Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH))
            {
                strategy.setVelocidade(new VeloDireita());
                pos = strategy.processaVelocidade();
            }
            
            

            if (pos.X > 0) 
            {
                if (pos.X > origin.Width) 
                {
                    pos.X = 0; 
                }
                destOne.X = (int)pos.X;
                destTwo.X = (int)(-origin.Width + pos.X);
            }
            else
            {
                if (pos.X < -origin.Width) 
                {
                    pos.X = 0;
                }
                destOne.X = (int)pos.X;
                destTwo.X = (int)(origin.Width + pos.X);
            }
        }
    }
}
