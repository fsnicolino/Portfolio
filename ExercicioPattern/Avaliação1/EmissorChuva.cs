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
    public class EmissorChuva : EmissorParticula // tambem herda de emissor de particula sobescrevendo os metodos e controla o emissor de chuva
    {
        public EmissorChuva()
            : base()
        {

        }

        public override void Unload()
        {
            base.Unload();
        }

        public override void Setup(Vector2 position, Vector2 velocity, Vector2 externalForce, Texture2D image, float maxLife, bool isAlive, int particleQty)
        {
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

            float angle = 90.0f / prt_particleQty;

            for (int i = 0; i < prt_particleQty; ++i)
            {//instancia uma nova particula ate alcançar o maximo definido
                if (prt_particles[i] == null)
                {
                    prt_particles[i] = new Particula();
                }

                Random rand = new Random(DateTime.Now.Millisecond);
                double posY = (rand.NextDouble() * Constants.SCREEN_HEIGHT - 1) - 1.0; //randomiza a posicao x e y de acordo com a altura e largura da tela
                double posX = (rand.NextDouble() * Constants.SCREEN_WIDTH - 1) - 1.0;
                prt_particles[i].Setup(
                    new Vector2((float)posX, (float)posY),
                    new Vector2(prt_velocity.X * (float)Math.Cos(i * angle), prt_velocity.Y * (float)Math.Sin(i * angle)),
                    prt_image,
                    prt_maxLife,
                    true,
                    Constants.DEATH_RULE_COLLISION_AT_BOTTOM
                );

            }
        }

        public override void ResetParticles()
        {//reseta as particulas
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
        {//desenha as particulas
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

        public override void SetParticlesVelocity()
        {//define a velocidade
            if (prt_particles != null)
            {
                float angle = 90.0f / prt_particleQty;

                for (int i = 0; i < prt_particleQty; ++i)
                {
                    if (prt_particles[i] != null)
                    {
                        prt_particles[i].SetVelocity(0, prt_velocity.Y * (float)Math.Sin(i * angle));
                    }
                }
            }
        }
    }
}
