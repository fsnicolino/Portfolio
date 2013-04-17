using UnityEngine;
using System.Collections;

public class ColisoesPlayer : MonoBehaviour
{


    private bool colisaoPortao = false;
    private static ColisoesPlayer instance;
    private bool audiofogocidade = false;
    private bool roar = false;

    public static ColisoesPlayer getInstance()
    {
        if (instance == null)
            instance = new ColisoesPlayer();
        return instance;
    }

	private void OnTriggerEnter(Collider theCollision){
	Debug.Log("Trigger Event = "+theCollision);
	 if (theCollision.gameObject.tag == "ChangeView")
        {
         
			if(theCollision.gameObject.active){
				GameObject.Find("Camera").GetComponent<SmoothFollow>().enabled = false;
				GameObject.Find("Camera").GetComponent<SmoothLookAt>().enabled = true;
           		GameObject.FindGameObjectWithTag("Gate").animation.Play();
   				GameObject.FindGameObjectWithTag("ChangeView").audio.Play();				
				
			}
         
        }

     if (theCollision.gameObject.tag == "bgsoundcity" && audiofogocidade == false)
     {
         GameObject.FindGameObjectWithTag("cityfire").audio.Play();
         audiofogocidade = true;
     }

     if (theCollision.gameObject.tag == "bgsdcitystop" && audiofogocidade == true )
     {
         GameObject.FindGameObjectWithTag("cityfire").audio.Stop();
         audiofogocidade = false;
     }
	}
	
	private void OnTriggerExit(Collider theCollision){
	
	 if (theCollision.gameObject.tag == "ChangeView")
        {
         	
				GameObject.Find("Camera").GetComponent<SmoothFollow>().enabled = true;
				GameObject.Find("Camera").GetComponent<SmoothLookAt>().enabled = false;
				theCollision.gameObject.active = false;
			
           	
        }

     if (theCollision.gameObject.name == "PlayerFStop2")
     {
         if (roar == false)
         {
             GameObject.Find("PlayerFStop2").audio.Play();
             roar = true;
         }
     }


    
	}
    private void OnParticleCollision(GameObject other)
    {
        Debug.Log(other.gameObject.name);
        if (other.name == "FogoBoss")
        {
            this.GetComponent<BarraVida>().HP -= 2;
        }
    }

    

   

 }