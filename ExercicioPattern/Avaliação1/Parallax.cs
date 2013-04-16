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
    public class Parallax : Observador
    { 
        private const int ParallaxLayersBack = 2; 
        private GraphicsDeviceManager graphics;
       
        private ParallaxLayer[] layersBack; 
        private ParallaxLayer[] layersFore; 
        private float[] Layers_YBack = { 0.0f, 170.0f}; 
        private float[] Layers_YFore = { 0.0f }; 
        
        private const int ParallaxLayersFore = 1; 

        public void Draw(GameTime gameTime, SpriteBatch spriteBatch, bool desenho)
        {
            if (desenho == true)
            {
                for (int i = 0; i < ParallaxLayersBack; ++i)
                {
                    layersBack[i].Draw(gameTime, spriteBatch);
                }
            }

            else if (desenho == false) 
            {
                for (int i = 0; i < ParallaxLayersFore; ++i)
                {
                    layersFore[i].Draw(gameTime, spriteBatch);
                }
            }
        }

        public void LoadContent(ContentManager content, GraphicsDeviceManager graphics)
        {
            this.graphics = graphics; 

            layersBack = new ParallaxLayer[ParallaxLayersBack]; 
            for (int i = 0; i < ParallaxLayersBack; ++i) 
            {
                if (i == 0)
                {
                    layersBack[i] = new ParallaxLayer(new Vector2(0.0f, Layers_YBack[i]), new Vector2(-0.5f * (i + 1), 0.0f));
                    layersBack[i].LoadContent(content, graphics, "Background" + i);
                }
                else if (i == 1)
                {
                    layersBack[i] = new ParallaxLayer(new Vector2(0.0f, Layers_YBack[i]), new Vector2(-0.5f * (i + 7), 0.0f));
                    layersBack[i].LoadContent(content, graphics, "Background" + i);
                }
            }

            layersFore = new ParallaxLayer[ParallaxLayersFore]; 
            for (int i = 0; i < ParallaxLayersFore; ++i) 
            {
                layersFore[i] = new ParallaxLayer(new Vector2(0.0f, Layers_YFore[i]), new Vector2(-0.5f * (i + 10), 0.0f));
                layersFore[i].LoadContent(content, graphics, "paralax" + i); 
            }
        }

        public void Initialize()
        {
           
        }

        public void UnloadContent()
        { 
            for (int i = 0; i < ParallaxLayersBack; ++i)
            {
                layersBack[i].Unload();
            }

            for (int i = 0; i < ParallaxLayersFore; ++i)
            {
                layersFore[i].Unload();
            }
        }

        public void Reset()
        {
            
        }

        public void atualizar(int direcao)
        {
            for (int i = 0; i < ParallaxLayersBack; ++i)
            {
                layersBack[i].Update(direcao);
            }

            for (int i = 0; i < ParallaxLayersFore; ++i)
            {
                layersFore[i].Update(direcao);
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
