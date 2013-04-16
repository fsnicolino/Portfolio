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
    class Contexto
    {
        private VelocidadeParallax velocidadeAtual;
        private Vector2 posicao, velocidade;
        
        
        public void setVelocidade(VelocidadeParallax velocidadeAtual)
        {
            this.velocidadeAtual = velocidadeAtual;
        }
        public void setPosicao(Vector2 posicao)
        {
            this.posicao = posicao;
        }

        public void setVelocidade(Vector2 velocidade)
        {
            this.velocidade = velocidade;
        }
        public Vector2 processaVelocidade()
        {
             return velocidadeAtual.atualizavelocidade(posicao, velocidade);
        }
    }
}
