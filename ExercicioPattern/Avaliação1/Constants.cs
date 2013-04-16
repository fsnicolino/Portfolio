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
    public static  class Constants
    {//constantes do jogo, classe usada para auxiliar nos valores usados com frequencia por mais de uma classe
        public const int ARROW_QTY = 4;
        public const int NEIGHBORS_QTY = 4;        
        public const int NO_NEIGHBOR = -1;
        
       
        public const int PLAYER_VELOCITY = 5;
        public const int SCENE_OFFSET = 30;
        public const int SCENE_PLAYER_OFFSET = 10;
        public const int SCENE_QTY = 7;
        public const int TRANSITION_DELAY = 6;

        public const int SCREEN_HEIGHT = 480;
        public const int SCREEN_WIDTH = 640;
        public const int PLAYER_WIDTH = 237;
               
       
        public const int NORTH = 0;
        public const int SOUTH = 1;
        public const int EAST = 2;
        public const int WEST = 3;


        public const int DEATH_RULE_LIFETIME = 0;
        public const int DEATH_RULE_COLLISION_AT_BOTTOM = 1;
    }
}
