using UnityEngine;
using System.Collections;

public class Magia : MonoBehaviour {

	// Use this for initialization
	
	private int duracao = 60;
	private int controleDuracao;
	private float tempoInicio;
	private Constantes cnst;
	private int direcao = -1;
	private bool isAlive = true;
	void Start () {
		cnst = GetComponent<Constantes>();
		controleDuracao = 0;
		tempoInicio = Time.deltaTime * 60;
		//Debug.Log(tempoInicio);
		
	
	}
	
	public void setDirecao(int dir){
		
		direcao = dir;
	}
	
	public bool getStatus(){ 
		
		return isAlive;
	}
	
	// Update is called once per frame
	void Update () {
		//Debug.Log("Update Bola de FOgo " + direcao);
        if (!isAlive)
        {
            Debug.Log("MORRE SKILL");
            Destroy(this.gameObject);
        }
        else
        {
            if (direcao != -1)
            {
                float tempo = Time.deltaTime * 60;
                if (tempo > 1)
                {
                    controleDuracao++;
                }
                Debug.Log("controleDuracao " + controleDuracao);
                if (controleDuracao > duracao)
                {
                    isAlive = false;

                }
                else
                {
                    GameObject outer = GameObject.Find("OuterCore");
                    if (direcao == cnst.DIREITA)
                    {
                        outer.particleEmitter.localVelocity = new Vector3(-2, 0, 0);
                        this.transform.position += new Vector3(0.1f, 0, 0);
                    }
                    else
                    {
                        outer.particleEmitter.localVelocity = new Vector3(2, 0, 0);
                        this.transform.position += new Vector3(-0.1f, 0, 0);
                    }
                }

            }
        }
	}
	void OnCollisionEnter(Collision theCollision)
    {
     
		Debug.Log("Colisao = "+theCollision.gameObject.tag);
         if (theCollision.gameObject.tag == "inimigo")
        {
            Debug.Log("Acertou Inimigo");
			isAlive = false;
            Destroy(theCollision.gameObject);
			
        }
        
    }

}
