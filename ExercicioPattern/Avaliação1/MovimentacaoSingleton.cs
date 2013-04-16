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

namespace Avaliação1
{
    class MovimentacaoSingleton
    {
        private bool lado;

        private static MovimentacaoSingleton instancia;

        private MovimentacaoSingleton()
        {
        }

        public static MovimentacaoSingleton getInstance()
        {
            if (instancia == null)
            {
                instancia = new MovimentacaoSingleton();
            }
            return instancia;
        }

        public bool getDirecao()
        {
            if (Keyboard.GetState().IsKeyDown(Keys.Left))
            {
                lado = true;

            }
            else if (Keyboard.GetState().IsKeyDown(Keys.Right))
            {
                lado = false;
            }
            return lado;
        }
                
    }
}
