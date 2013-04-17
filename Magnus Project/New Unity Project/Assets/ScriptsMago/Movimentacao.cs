using UnityEngine;
using System.Collections;

public class Movimentacao : MonoBehaviour
{

    private Vector3 eulerVelocidadeDoAngulo = new Vector3(0, 180, 0);
	
    public int direcao;
	private Constantes cnst;
    private Magia2 mg = null;
    private bool tocouChao;
	
	private	Transform magiaAtk = null;
	public float constVelocidade;
	public float constPulo;
    public Transform attack1;

	private bool atacando = false;
    private int controleAtaque = 1;
		
	public string ataque;
	public string andando;
	public string morrendo;
	
	//USANDO APENAS PARA RODAR AS ANIMAÇÕES DO LADINO / MERCENÁRIO
	public bool verfica_mercenario = false;
	public bool tecla_pressionada = false;

    private GameObject instancepersonagem;

    public GameObject guerreiro;
    public GameObject mercenario;
    public GameObject mago;
    //public GameObject cacadora;

	void Awake(){

	  	cnst = GetComponent<Constantes>();	
		direcao = cnst.DIREITA;
		//Rodando a animação do mercenário
        if (verfica_mercenario)
        {
            animation[ataque].speed = 2;
        }
		GameObject.Find("Global").GetComponent<ControleGlobal>().currentJogador = this.transform;
	}
		
  	//Verificação de Colisão com o Chão
    void OnCollisionEnter(Collision theCollision)
    {
        
       if (theCollision.gameObject.tag == "Floor" || theCollision.gameObject.tag == "Pedra")
        {
            
            tocouChao = true;
        }
        
	}

    void Update()
    {

        //troca de personagens
        if (Input.GetKey("1"))
        {

            instancepersonagem = Instantiate(mago, transform.position, transform.rotation) as GameObject;
            instancepersonagem.GetComponent<Movimentacao>().direcao = this.direcao;
			GameObject.Find("Camera").GetComponent<SmoothFollow>().target = instancepersonagem.transform;
			GameObject.Find("Camera").GetComponent<SmoothLookAt>().target = instancepersonagem.transform;;
			verfica_mercenario = false;
            Destroy(gameObject);

        }

        else if (Input.GetKey("2"))
        {

            instancepersonagem = Instantiate(guerreiro, transform.position, transform.rotation) as GameObject;
            instancepersonagem.GetComponent<Movimentacao>().direcao = this.direcao;
			GameObject.Find("Camera").GetComponent<SmoothFollow>().target = instancepersonagem.transform;;
			GameObject.Find("Camera").GetComponent<SmoothLookAt>().target = instancepersonagem.transform;;
			verfica_mercenario = false;
            Destroy(gameObject);

        }

        else if (Input.GetKey("3"))
        {

            instancepersonagem = Instantiate(mercenario, transform.position, transform.rotation) as GameObject;
            instancepersonagem.GetComponent<Movimentacao>().direcao = this.direcao;
			GameObject.Find("Camera").GetComponent<SmoothFollow>().target = instancepersonagem.transform;;
			GameObject.Find("Camera").GetComponent<SmoothLookAt>().target = instancepersonagem.transform;;
			verfica_mercenario = true;
            Destroy(gameObject);

        }
 
    }
   

   void FixedUpdate()
    {
		//Rotação
		Quaternion deltaRotation = Quaternion.Euler(eulerVelocidadeDoAngulo);
		
		//Para a animação do mercenário
		if (Input.GetKeyUp("right") || Input.GetKeyUp("left")|| Input.GetKeyUp("up") || Input.GetKeyUp("down"))
		{
			animation.Stop();
		}
		
		//Movimentação
        if (Input.GetKey("left"))
        {
            rigidbody.AddForce(-constVelocidade, 0, 0);
			tecla_pressionada = true;
			if(direcao != cnst.ESQUERDA)
			{
               // rigidbody.MoveRotation(rigidbody.rotation * deltaRotation);
				Vector3 posAnt = transform.localPosition;
				transform.Rotate(new Vector3(0,180,0), Space.Self);
				transform.localPosition = posAnt;
				direcao = cnst.ESQUERDA;
			}
			
			
			
			if(!animation.IsPlaying(andando))
			{
				animation.CrossFade(andando);
			}
			
		}
		
		
		
        if (Input.GetKey("right"))
        {
            rigidbody.AddForce(constVelocidade, 0, 0);
			tecla_pressionada = true;
			if(direcao != cnst.DIREITA)
			{
				Vector3 posAnt = transform.localPosition;
				transform.Rotate(new Vector3(0,180,0), Space.Self);
				transform.localPosition = posAnt;
				direcao = cnst.DIREITA;
			}
			
			if(!animation.IsPlaying(andando))
			{
				animation.CrossFade(andando);
			}
			
        }

        if (Input.GetKey("up"))
        {
            rigidbody.AddForce(0, 0, constVelocidade);
			
			if(!animation.IsPlaying(andando))
			{
				animation.CrossFade(andando);
			}
           
        }

        if (Input.GetKey("down"))
        {
            rigidbody.AddForce(0, 0, -constVelocidade);
			
			if(!animation.IsPlaying(andando))
			{
				animation.CrossFade(andando);
                
			}
        }
		
         //Pulo
        if (tocouChao == true)
        {
            if (Input.GetButtonUp("Jump"))
            {
                rigidbody.AddForce(0, constPulo, 0);
                tocouChao = false;
            }

        }
		
		//Ataque
		if(Input.GetKey("a"))
		{

			if(!atacando && attack1 != null)
			{
             

				if(direcao == cnst.DIREITA)
					{
						magiaAtk = Instantiate(attack1, new Vector3 (this.transform.position.x + 2, this.transform.position.y + 1, this.transform.position.z + 0.5f), Quaternion.identity) as Transform;
					}
				
				else
					{
						magiaAtk = Instantiate(attack1, new Vector3 (this.transform.position.x - 2f, this.transform.position.y + 1, this.transform.position.z + 0.5f), Quaternion.identity) as Transform;
					}
				
				mg = magiaAtk.GetComponent<Magia2>();
				mg.setDirecao(direcao);
				atacando = true;

              
			}

            
            if (!animation.IsPlaying(andando))
            {
                animation.CrossFade(ataque);
            }
            else
            {

                animation.Play(ataque);
            }
            GameObject.FindGameObjectWithTag("Player").audio.Play();
			
		}

        if (atacando)
        {
            if (controleAtaque > 25)
            {
                controleAtaque = 0;
                atacando = false;
            }
            controleAtaque++;
        }
			

    }
 
    void OnTriggerEnter ( Collider collisionInfo)
{
    //Debug.Log("Event " + collisionInfo.gameObject.tag);
    if (collisionInfo.gameObject.tag == "inimigo")
    {

        if (animation.IsPlaying(ataque))
        {
            float totalAnimacao = animation[ataque].clip.length;
            if (animation[ataque].time * 100 / totalAnimacao >= 30)
            {
                Destroy(collisionInfo.gameObject);
            }
        }
    }
    
}
  
}     