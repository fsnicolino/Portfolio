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
   public class EmissorParticula
    {//controla a emissao de particulas
        protected Vector2 prt_position;
        protected Vector2 prt_velocity;
        protected Vector2 prt_externalForce;
        protected Texture2D prt_image;
        protected float prt_maxLife;
        protected int prt_particleQty;
        protected Particula[] prt_particles;       

         public EmissorParticula()
        {//inicializa as variaveis
            prt_position = new Vector2(0, 0);
            prt_velocity = new Vector2(0, 0);
            prt_externalForce = new Vector2(0, 0);
            prt_image = null;
            prt_maxLife = 0;
            prt_particleQty = 0;
            prt_particles = null;
        }

         public virtual void Unload()
         {//descarrega as particulas caso nao sejam nulas
             prt_image.Dispose();

             if (prt_particles != null)
             {
                 for (int i = 0; i < prt_particleQty; ++i)
                 {
                     if (prt_particles[i] != null)
                     {
                         prt_particles[i] = null;
                     }
                 }
                 prt_particles = null;
             }
         }

         public virtual void Setup(Vector2 position, Vector2 velocity, Vector2 externalForce, Texture2D image, float maxLife, bool isAlive, int particleQty)
         {//define posicao, velocidade, forca externa, imagem, vida maxima, se esta ativa e quantidade
             prt_position = position;
             prt_velocity = velocity;
             prt_externalForce = externalForce;
             prt_image = image;
             prt_maxLife = maxLife;
             prt_particleQty = particleQty;
             SetupParticles();
             SetParticlesAlive(isAlive);
         }

         public virtual void SetupParticles() //controla as particulas para nao ultrapassar a quantidade definida
         {
             if (prt_particles == null)
             {
                 prt_particles = new Particula[prt_particleQty];
             }

             for (int i = 0; i < prt_particleQty; ++i)
             {
                 prt_particles[i] = null;
             }
         }

         public virtual void ResetParticles()
         {//reseta as particulas
             SetupParticles();
         }

         public virtual void Update(GameTime gameTime)
         {

         }

         public virtual void Draw(GameTime gameTime, SpriteBatch spriteBatch)
         {

         }

         public virtual void SetPosition(Vector2 position)
         {//define posicao
             prt_position = position;
             SetParticlesPosition();
         }

         public virtual Vector2 GetPosition()
         {//retorna a posicao
             return (prt_position);
         }

         public virtual void SetPosition(float x, float y)
         {
             //define posicao x e y
             prt_position.X = x;
             prt_position.Y = y;
             SetParticlesPosition();
         }

         public virtual void SetX(float x)
         {//define x
             prt_position.X = x;
             SetParticlesPosition();
         }

         public virtual void SetY(float y)
         {//define y
             prt_position.Y = y;
             SetParticlesPosition();
         }

         public virtual void SetParticlesPosition()
         {//define a posicao das particulas se elas nao ultrapassaram a quantidade maxima definida
             if (prt_particles != null)
             {
                 for (int i = 0; i < prt_particleQty; ++i)
                 {
                     if (prt_particles[i] != null)
                     {
                         prt_particles[i].SetPosition(prt_position);
                     }
                 }
             }
         }

         public virtual void SetVelocity(Vector2 velocity)
         {//define velocidade
             prt_velocity = velocity;
             SetParticlesVelocity();
         }

         public virtual Vector2 GetVelocity()
         {//retorna velocidade
             return (prt_velocity);
         }

         public virtual void SetVelocity(float x, float y)
         {//define velocidade x e y
             prt_velocity.X = x;
             prt_velocity.Y = y;
             SetParticlesVelocity();
         }

         public virtual void SetVelocityX(float x)
         {//define velocidade x
             prt_velocity.X = x;
             SetParticlesVelocity();
         }

         public virtual void SetVelocityY(float y)
         {//define velocidade y
             prt_velocity.Y = y;
             SetParticlesVelocity();
         }

         public virtual void ZeroVelocity()
         {//zera a velocidade
             prt_velocity.X = 0;
             prt_velocity.Y = 0;
             SetParticlesVelocity();
         }

         public virtual void SetParticlesVelocity()
         {//se particulas nao ultrapassam o limite e nao sao nulas define a velocidade
             if (prt_particles != null)
             {
                 for (int i = 0; i < prt_particleQty; ++i)
                 {
                     if (prt_particles[i] != null)
                     {
                         prt_particles[i].SetVelocity(prt_velocity);
                     }
                 }
             }
         }

         public virtual void SetExternalForce(Vector2 externalForce)
         {//define a forca externa
             prt_externalForce = externalForce;
         }

         public virtual Vector2 GetExternalForce()
         {//retorna o valor da força externa
             return (prt_externalForce);
         }

         public virtual void SetExternalForce(float x, float y)
         {//define forca x e y
             prt_externalForce.X = x;
             prt_externalForce.Y = y;
         }

         public virtual void SetExternalForceX(float x)
         {//define força x
             prt_externalForce.X = x;
         }

         public virtual void SetExternalForceY(float y)
         {//define forca y
             prt_externalForce.Y = y;
         }

         public virtual void ZeroExternalForce()
         {//zera a forca
             prt_externalForce.X = 0;
             prt_externalForce.Y = 0;
         }

         public virtual void SetMaxLife(float maxLife)
         {//define tempo de vida para cada particula
             prt_maxLife = maxLife;

             if (prt_particles != null)
             {
                 for (int i = 0; i < prt_particleQty; ++i)
                 {
                     if (prt_particles[i] != null)
                     {
                         prt_particles[i].SetMaxLife(prt_maxLife);
                     }
                 }
             }
         }

         public virtual float GetMaxLife()
         {//returna tempo de vida
             return (prt_maxLife);
         }

         public virtual void SetParticlesAlive(bool isAlive)
         {//define status de cada particula
             if (prt_particles != null)
             {
                 for (int i = 0; i < prt_particleQty; ++i)
                 {
                     if (prt_particles[i] != null)
                     {
                         prt_particles[i].SetAlive(isAlive);
                     }
                 }
             }
         }


    }
}
