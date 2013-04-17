using UnityEngine;
using System.Collections;

public class MovimentacaoCharacter : MonoBehaviour {
    
    private Constantes cnst;
    private Magia2 mg = null;
    public float speed = 4.0f;
    public float jumpForce = 20;
    public float airModifier = 0.2f;
    public string andando = "ANDANDO";
    public string ataque = "";
    private bool atacando = false;
    public Transform attack1;
    private Transform magiaAtk = null;
    private int direcao = 0;
    private int controleAtaque = 0;
    private int jumpCount = 0;
    private bool controleTroca = false;

    public bool verfica_mercenario = false;

    private GameObject instancepersonagem;

    public GameObject guerreiro;
    public GameObject mercenario;
    public GameObject mago;


	void Start () {
        cnst = GetComponent<Constantes>();
        //direcao = cnst.DIREITA;
        if (verfica_mercenario)
        {
            
            animation[ataque].speed = 2;
        }
        animation[andando].wrapMode = WrapMode.Loop;
        GameObject controleIA = GameObject.Find("Global");
        controleIA.GetComponent<ControleGlobal>().currentJogador = this.transform;

      
	}


    public void setDirecao(int dir, bool init)
    {
        Debug.Log(direcao + "  -  " + dir);
        if (init)
        {
            direcao = dir;

        }
        else
        {
            if (direcao != dir)
            {


                Vector3 posAnt = transform.localPosition;
                transform.Rotate(new Vector3(0, 180, 0), Space.Self);
                transform.localPosition = posAnt;
                direcao = dir;

            }
        }
    }
    public void Update()
    {
        
        
       

        bool grounded;

        
        

        float hor = Input.GetAxis("Horizontal");
        float ver = Input.GetAxis("Vertical");
      //      Debug.Log(hor + "  - " + ver);
        if (hor != 0 || ver != 0)
        {
            if (!animation.IsPlaying(ataque))
            {
                animation.CrossFade(andando);
            }
            else
            {
                animation.Play(ataque);
            }
            
            if (hor > 0)
            {

                if (direcao != cnst.DIREITA)
                {

                    setDirecao(cnst.DIREITA,false);
                }
            }
            else
            {
                if (hor < 0)
                {
                    setDirecao(cnst.ESQUERDA,false);
                }
               
            }
        }
        else
        {
            if (!animation.IsPlaying(ataque))
            {
                animation.Stop(andando);
            }
        }
        verificaAtaque();
        hor *= -1;
        ver *= -1;


        
        //is the user pressing left or right (or "a & "d") on the keyboard?
        Vector3 horMovement = hor  * transform.right * Time.deltaTime * speed;

        //is the user pressing up or down (or "w" & "s") on the keyboard?
        Vector3 forwardMovement = ver  * transform.forward * Time.deltaTime * speed;

        //are we grounded?
        if (Physics.Raycast(transform.position, -transform.up, 0.15f))
        {
            //Debug.Log("Encostrou no chao");
            grounded = true;
            //reset our jump count since we hit the ground
            jumpCount = 0;
        }
        else
        {
           // Debug.Log("No ar");
            horMovement *= airModifier;
            forwardMovement *= airModifier;

            if (hor != 0)
            {

                
                rigidbody.AddRelativeForce(transform.right * hor * speed/2, ForceMode.Impulse);
                
                
            }
            grounded = false;
        }

        //jump if the user pressing the space key
        //AND our character is grounded
        //OR we have not jumped in the air yet
        if (Input.GetKeyUp("space") && (grounded || jumpCount < 1))
        {
            rigidbody.AddRelativeForce(transform.up * jumpForce, ForceMode.Impulse);
            
            
            //increase our jump count
            jumpCount++;
        }

        //move our character
        transform.Translate(forwardMovement + horMovement);

        verificaTroca();
        
    }

    public void verificaAtaque()
    {
        
        if (Input.GetKey(KeyCode.LeftControl))
        {
            if (!atacando)
            {
                if (!animation.IsPlaying(ataque))
                {
                    if (animation.IsPlaying(andando))
                    {
                        Debug.Log("entrou aki");

                        animation.CrossFade(ataque);
                    }
                    else
                    {
                        Debug.Log("entrou aki 2");
                        animation.Play(ataque);
                    }
                    GameObject.FindGameObjectWithTag("Player").audio.Play();
                }
                

                if (attack1 != null)
                {


                    if (direcao == cnst.DIREITA)
                    {
                        magiaAtk = Instantiate(attack1, new Vector3(this.transform.position.x + 2f, this.transform.position.y + 1, this.transform.position.z + 0.5f), Quaternion.identity) as Transform;
                    }

                    else
                    {
                        magiaAtk = Instantiate(attack1, new Vector3(this.transform.position.x - 2f, this.transform.position.y + 1, this.transform.position.z + 0.5f), Quaternion.identity) as Transform;
                    }

                    mg = magiaAtk.GetComponent<Magia2>();
                    mg.setDirecao(direcao);
                    atacando = true;


                }

            }
            


           
        }
        if (atacando)
        {
            if (controleAtaque > 35)
            {
                controleAtaque = 0;
                atacando = false;
            }
            controleAtaque++;
        }
    }

    public void verificaTroca()
    {
        //troca de personagens
        if (Input.GetKey("1") && !controleTroca)
        {
            if (mago != null)
            {
                controleTroca = true;
                Debug.Log("Trocou Personagem");
                instancepersonagem = Instantiate(mago, transform.position, transform.rotation) as GameObject;
                instancepersonagem.GetComponent<MovimentacaoCharacter>().setDirecao(this.direcao,true);
                GameObject.Find("Camera").GetComponent<SmoothFollow>().target = instancepersonagem.transform;
                GameObject.Find("Camera").GetComponent<SmoothLookAt>().target = instancepersonagem.transform; ;
                verfica_mercenario = false;
                Destroy(this.gameObject);
            }

        }

        else if (Input.GetKey("2") && !controleTroca)
        {
            if (guerreiro != null)
            {
                controleTroca = true;
                Debug.Log("Trocou Personagem");
                instancepersonagem = Instantiate(guerreiro, transform.position, transform.rotation) as GameObject;
                instancepersonagem.GetComponent<MovimentacaoCharacter>().setDirecao(this.direcao, true);
                GameObject.Find("Camera").GetComponent<SmoothFollow>().target = instancepersonagem.transform; ;
                GameObject.Find("Camera").GetComponent<SmoothLookAt>().target = instancepersonagem.transform; ;
                verfica_mercenario = false;
                Destroy(this.gameObject);
            }

        }

        else if (Input.GetKey("3") && !controleTroca)
        {
            if (mercenario != null)
            {
                controleTroca = true;
                Debug.Log("Trocou Personagem");
                instancepersonagem = Instantiate(mercenario, transform.position, transform.rotation) as GameObject;
                instancepersonagem.GetComponent<MovimentacaoCharacter>().setDirecao(this.direcao, true);
                GameObject.Find("Camera").GetComponent<SmoothFollow>().target = instancepersonagem.transform; ;
                GameObject.Find("Camera").GetComponent<SmoothLookAt>().target = instancepersonagem.transform; ;
                verfica_mercenario = true;
                Destroy(this.gameObject);
            }

        }
    }

    void OnTriggerEnter(Collider collisionInfo)
    {
        //Debug.Log("Event " + collisionInfo.gameObject.tag);
        
        //Debug.Log(animation[ataque].time * 100 / totalAnimacao);
        if (collisionInfo.gameObject.tag == "inimigo")
        {

            if (animation.IsPlaying(ataque))
            {
                float totalAnimacao = animation[ataque].clip.length;
                float posAtual = animation[ataque].time * 100 / totalAnimacao;
                if (verfica_mercenario)
                {
                    if (posAtual >= 30 && posAtual <= 55)
                    {
                        Destroy(collisionInfo.gameObject);
                    }
                }
                else
                {
                    if (posAtual >= 15 && posAtual <= 45)
                    {
                        Destroy(collisionInfo.gameObject);
                    }
                }
            }
        }

    }


    
}
