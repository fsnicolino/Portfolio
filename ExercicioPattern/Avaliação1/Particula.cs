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


namespace Avaliacao1
{
   public class Particula
    { //controla as particulas
        protected Vector2 prt_position;
        protected Vector2 prt_velocity;
        protected Texture2D prt_image;
        protected float prt_currentLife;
        protected float prt_maxLife;
        protected bool prt_isAlive;
        protected Color prt_color;

        protected int prt_deathRule;


        public Particula()
        { //inicializa as variaveis
            prt_position = new Vector2(0, 0);
            prt_velocity = new Vector2(0, 0);
            prt_image = null;
            prt_currentLife = 0;
            prt_maxLife = 0;
            prt_isAlive = false;
            prt_color = new Color(1.0f, 1.0f, 1.0f, 1.0f);

            prt_deathRule = Constants.DEATH_RULE_LIFETIME;
        }

        public virtual void Unload()
        {//descarrega da memoria
            prt_image.Dispose();
        }

        public virtual void Setup(Vector2 position, Vector2 velocity, Texture2D image, float maxLife, bool isAlive, int deathRule)
        { //recebe a posicao, velocidade, imagem, tempo de vida, se esta ativo, regra de morte
            prt_position = position;
            prt_velocity = velocity;
            prt_image = image;
            prt_currentLife = 0;
            prt_maxLife = maxLife;
            prt_isAlive = isAlive;

            prt_deathRule = deathRule;
        }

        public virtual void Update(GameTime gameTime)
        {//atualiza as particulas
            if (prt_isAlive) //se particula é ativa
            {
                prt_position += prt_velocity; //define velocidade

                switch (prt_deathRule) //define comportamento da regra de morte (duraçao da particula)
                {
                    case Constants.DEATH_RULE_LIFETIME: // caso seja a constante lifetime
                        {
                            prt_currentLife++;

                            if (prt_currentLife >= prt_maxLife) //define como sendo falso isAlive e desativa a particula - usada na explosao
                            {
                                prt_isAlive = false;
                                prt_currentLife = 0;
                            }
                        } break;

                    case Constants.DEATH_RULE_COLLISION_AT_BOTTOM: //caso seja a colisao no fim
                        {
                            if (prt_position.Y > Constants.SCREEN_HEIGHT)
                            {
                                prt_isAlive = false; // define como sendo falso isAlive e desativa a particula - usado na chuva
                            }
                        } break;
                }
            }
        }

        public virtual void Draw(GameTime gameTime, SpriteBatch spriteBatch)
        {
            if (prt_isAlive) // desenha as particulas
            {
                if (prt_deathRule == Constants.DEATH_RULE_LIFETIME)
                {
                    prt_color.A = (byte)((100.0f / prt_currentLife) + 50);
                }
                spriteBatch.Draw(prt_image, prt_position, prt_color);
            }
        }

        public virtual void SetPosition(Vector2 position)
        {
            prt_position = position; //define posicao
        }

        public virtual Vector2 GetPosition()
        {
            return (prt_position); //retorna o valor da posicao
        }

        public virtual void SetPosition(float x, float y) //define posicao X e Y
        {
            prt_position.X = x;
            prt_position.Y = y;
        }

        public virtual void SetX(float x) // define posicao X
        {
            prt_position.X = x;
        }

        public virtual void SetY(float y) // define posicao Y
        {
            prt_position.Y = y;
        }

        public virtual void SetVelocity(Vector2 velocity) // define velocidade usando um Vector2 (x e y)
        {
            prt_velocity = velocity;
        }

        public virtual Vector2 GetVelocity() // retorna o valor da velocidade
        {
            return (prt_velocity);
        }

        public virtual void SetVelocity(float x, float y) // define velocidade X e Y
        {
            prt_velocity.X = x;
            prt_velocity.Y = y;
        }

        public virtual void SetVelocityX(float x) //define velocidade x
        {
            prt_velocity.X = x;
        }

        public virtual void SetVelocityY(float y) //define velocidade y
        {
            prt_velocity.Y = y;
        }

        public virtual void ZeroVelocity() // zera a velocidade
        {
            prt_velocity.X = 0;
            prt_velocity.Y = 0;
        }

        public virtual void ApplyForce(Vector2 force) //aplica força para a explosao
        {
            if (prt_isAlive)
            {
                prt_velocity += force;
            }
        }

        public virtual void SetCurrentLife(float currentLife) // define a vida atual
        {
            prt_currentLife = currentLife;
        }

        public virtual float GetCurrentLife() //retorna o status da vida
        {
            return (prt_currentLife);
        }

        public virtual void SetMaxLife(float maxLife) // define o tempo maximo de vida
        {
            prt_maxLife = maxLife;
        }

        public virtual float GetMaxLife() // retorna o tempo maximo de vida
        {
            return (prt_maxLife);
        }

        public virtual void SetAlive(bool isAlive) //define se esta vivo(ativo)
        {
            prt_isAlive = isAlive;
        }

        public bool IsAlive() //retorna o status
        {
            return (prt_isAlive);
        }

        public virtual void SetDeathRule(int deathRule) //define a regra de morte
        {
            prt_deathRule = deathRule;
        }

        public virtual int GetDeathRule() // retorna o status
        {
            return (prt_deathRule);
        }


        public virtual void SetColor(float r, float g, float b, float a) //define as cores
        {
            prt_color.R = (byte)(r * 255);
            prt_color.G = (byte)(g * 255);
            prt_color.B = (byte)(b * 255);
            prt_color.A = (byte)(a * 255);
        }

    }
}
