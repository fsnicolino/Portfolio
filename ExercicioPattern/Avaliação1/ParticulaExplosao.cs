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

    public class ParticulaExplosao : EmissorParticula //herda do emissor de particula e controla a explosao
    {
        public ParticulaExplosao() : base()
        {
           
        }

        public override void Setup(Vector2 position, Vector2 velocity, Vector2 externalForce, Texture2D image, float maxLife, bool isAlive, int particleQty)
        {//define posicao, velocidade, forca externa,imagem,vida,status e quatidade e sobescreve no emissor de particula
            prt_position = position;
            prt_velocity = velocity;
            prt_externalForce = externalForce;
            prt_image = image;
            prt_maxLife = maxLife;
            prt_particleQty = particleQty;
            SetupParticles();
            SetParticlesAlive(isAlive);
            
            
        }

        public override void SetupParticles()
        {//define as particulas
            if (prt_particles == null)
            {
                prt_particles = new Particula[prt_particleQty];
            }

            float angle = 360.0f / prt_particleQty;

            for (int i = 0; i < prt_particleQty; ++i)
            {
                if (prt_particles[i] == null)
                {
                    prt_particles[i] = new Particula();
                }
                prt_particles[i].Setup(
                    prt_position,
                    new Vector2(prt_velocity.X * (float)Math.Cos(i * angle), prt_velocity.Y * (float)Math.Sin(i * angle)),
                    prt_image,
                    prt_maxLife,
                    false,
                    Constants.DEATH_RULE_LIFETIME
                );
            }
        }

        public override void ResetParticles()
        {//reseta
            SetupParticles();
        }

      
        public override void Update(GameTime gameTime)
        {//atualiza as particulas

            if (prt_particles != null)
            {
                for (int i = 0; i < prt_particleQty; ++i)
                {
                    if (prt_particles[i] != null)
                    {
                        prt_particles[i].ApplyForce(prt_externalForce);
                        prt_particles[i].Update(gameTime);
                    }
                }
            }
           
        }

        public override void Draw(GameTime gameTime, SpriteBatch spriteBatch)
        {//desenha na tela
            if (prt_particles != null)
            {
                for (int i = 0; i < prt_particleQty; ++i)
                {
                    if (prt_particles[i] != null)
                    {
                        prt_particles[i].Draw(gameTime, spriteBatch);
                    }
                }
            }
        }

        public override void Unload()
        {//descarrega da memoria
            base.Unload();
        }

        public override void SetParticlesVelocity()
        {//define velocidade
            if (prt_particles != null)
            {
                float angle = 360.0f / prt_particleQty;

                for (int i = 0; i < prt_particleQty; ++i)
                {
                    if (prt_particles[i] != null)
                    {
                        prt_particles[i].SetVelocity(prt_velocity.X * (float)Math.Cos(i * angle), prt_velocity.Y * (float)Math.Sin(i * angle));
                    }
                }
            }
        }
    }
}