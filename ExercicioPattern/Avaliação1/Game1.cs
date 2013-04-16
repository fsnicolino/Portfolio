////////////////////////////////////////////////////////
// Portfólio: Estudo de Design Patterns e Parallax    // 
// Fernando Sarra Nicolino                            //
// e-mail: fsnicolino@gmail.com                       //
// skype: fsnicolino                                  // 
// telefone para contato: 11 99409-2988               //
////////////////////////////////////////////////////////



using System;
using System.Collections.Generic;
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
  
    public class Game1 : Microsoft.Xna.Framework.Game, Observavel 
    {
        GraphicsDeviceManager graphics;
        SpriteBatch spriteBatch;
       
        private Parallax p_parallax; 

        private Observador[] observadores;
        private int indiceObservadores; 
        private const int maximoObservador = 2;       

        
   
        
        public Game1()
        {
            graphics = new GraphicsDeviceManager(this);
            graphics.PreferredBackBufferWidth = Constants.SCREEN_WIDTH;
            graphics.PreferredBackBufferHeight = Constants.SCREEN_HEIGHT;
            observadores = new Observador[maximoObservador];
            p_parallax = new Parallax();
            Player.getInstance().inscreverObservador(this);          
            p_parallax.inscreverObservador(this);
            Content.RootDirectory = "Content";
        }

            
        protected override void Initialize()
        {
            

            base.Initialize();
        }

    
        protected override void LoadContent()
        {

            spriteBatch = new SpriteBatch(GraphicsDevice);
           
            Player.getInstance().Load(Content.Load<Texture2D>("player"));
            Player.getInstance().SetPosition((Constants.SCREEN_WIDTH - Player.getInstance().GetWidth()) >> 1,280.0f);
            
                
           
            
            p_parallax.Initialize();
            p_parallax.LoadContent(Content, graphics);

                     
        }

    
        protected override void UnloadContent()
        {
            Player.getInstance().Unload();
            Player.getInstance().deletarObservador(this);
            p_parallax.deletarObservador(this);
            p_parallax.UnloadContent();

           
        }


        protected override void Update(GameTime gameTime)
        { 
            
            if (Keyboard.GetState().IsKeyDown(Keys.Left))
            {
               
                notificacao(new EventoEsquerda().getIdentificacao());
            }

            if (Keyboard.GetState().IsKeyDown(Keys.Right))
            {
           
                notificacao(new EventoDireita().getIdentificacao());               
            }
           
            base.Update(gameTime);
        }

  
        protected override void Draw(GameTime gameTime)
        {
           
            GraphicsDevice.Clear(Color.Black);

            spriteBatch.Begin();

            p_parallax.Draw(gameTime, spriteBatch, true);
            Player.getInstance().Draw(gameTime, spriteBatch);
            p_parallax.Draw(gameTime, spriteBatch, false);
           
            spriteBatch.End();

            base.Draw(gameTime);
        }
        
        public void criarColecaoObservadores()
        {
            reiniciaColecaoObservadores();
        }

        public  void adicionaOuvinte(Observador novoObservador)
        {
            observadores[indiceObservadores++] = novoObservador;
        }


        public void notificacao(int direcao)
        {
            for (int i = 0; i < maximoObservador; i++)
            {
                if(observadores != null)
                {
                    observadores[i].atualizar(direcao);
                }
            }
        }

        public void retirarOuvinte(Observador exObservador)
        {
              for (int i = 0; i < maximoObservador; i++)
            {
                if (observadores[i] != null)
                {
                    if (observadores[i] == exObservador)
                    {
                        observadores[i] = null;
                    }

                }
            }
        }

        public void reiniciaColecaoObservadores()
        {
            observadores = new Observador[maximoObservador];
            indiceObservadores = 0;
        }
    }
}
