using UnityEngine;
using System.Collections;

public class Magia2 : MonoBehaviour {

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
		if(direcao != -1){
			float tempo = Time.deltaTime * 60;
			if(tempo > 1){
				controleDuracao++;	
			}
			if(controleDuracao > duracao){
                isAlive = false;
                Destroy(this.gameObject);
				
			}else{
				GameObject outer = GameObject.FindWithTag("raboAtaque");
				if(direcao == cnst.DIREITA){
					outer.particleEmitter.localVelocity = new Vector3(-2,0,0);
					this.transform.position += new Vector3(0.1f, 0, 0);
				}else{
					
					outer.particleEmitter.localVelocity = new Vector3(2,0,0);
					this.transform.position += new Vector3(-0.1f, 0, 0);
				}
			}
			
		}
	}
	void OnCollisionEnter(Collision theCollision)
    {
     
		Debug.Log("Colisao = "+theCollision.gameObject.tag);
        if (theCollision.gameObject.tag == "inimigo")
        {
            if (theCollision.gameObject.GetComponent<AIFollow>().isBoss)
            {
                //Debug.Log("Acertou Inimigo");
                theCollision.gameObject.GetComponent<AIFollow>().vida -= 20;

            }
            else
            {
                isAlive = false;
                Destroy(theCollision.gameObject);
                Destroy(this.gameObject);
            }

        }
        else
        {
            if (theCollision.gameObject.tag != "Player")
            {
                Destroy(this.gameObject);
            }
        }
        
    }

   
}
