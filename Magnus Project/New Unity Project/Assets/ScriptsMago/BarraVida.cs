using UnityEngine;
 
public class BarraVida : MonoBehaviour
{
	public int maxHP;
	public int left;
	public int top;
	public Texture2D hpBar;
 
	private Texture2D currentHPBar;
    private Texture2D hud;
    private string hudAnt;
	private int barWidth;
	private int barHeight;
 	public int tipoPersonagem;
	private int hp;
	private GameObject controleGlobal;
	
	public void Awake(){
		controleGlobal = GameObject.Find("Global");
		if(controleGlobal.GetComponent<ControleGlobal>().vidaMago == -1 ||
		   controleGlobal.GetComponent<ControleGlobal>().vidaGuerreiro == -1 ||
		   controleGlobal.GetComponent<ControleGlobal>().vidaMerc == -1 ){
			hp = maxHP;
		}else{
			if(tipoPersonagem == 1){
				hp = controleGlobal.GetComponent<ControleGlobal>().vidaMago;
			}else if(tipoPersonagem == 2){
				hp = controleGlobal.GetComponent<ControleGlobal>().vidaGuerreiro;
			}else{
				hp = controleGlobal.GetComponent<ControleGlobal>().vidaMerc;
			}
		}
		
	}
	
	public int HP
    {
		get { return hp; }
		set
        {
            hp = value;
			
			if (hp > maxHP) 
			{
				hp = maxHP;
			}
			
			if (hp < 0) 
			{
				hp = 0;
			}
			if(tipoPersonagem == 1){
                
				controleGlobal.GetComponent<ControleGlobal>().vidaMago = hp;
			}else if(tipoPersonagem == 2){
                
				controleGlobal.GetComponent<ControleGlobal>().vidaGuerreiro = hp;
			}else{
                
				controleGlobal.GetComponent<ControleGlobal>().vidaMerc = hp;
			}
        }
    }
 	

	public void Update()
    {
        //hp = maxHP;
 
		if (hpBar != null)
        {
            hud = null;
            currentHPBar = hpBar;
            barWidth = hpBar.width;
            barHeight = hpBar.height;
        }
        
		else
        {
			if(hp > 0){
                int perc = hp * 100 / 200;
                if (perc > 95 && perc < 100)
                {
                    perc = 100;
                }
                if (perc > 90 && perc < 100)
                {
                    perc = 90;
                }
                else if (perc > 80 && perc < 90)
                {
                    perc = 80;
                }
                else if (perc > 70 && perc < 80)
                {
                    perc = 70;
                }
                else if (perc > 60 && perc < 70)
                {
                    perc = 60;
                }
                else if (perc > 50 && perc < 60)
                {
                    perc = 50;
                }
                else if (perc > 40 && perc < 50)
                {
                    perc = 40;
                }
                else if (perc > 30 && perc < 40)
                {
                    perc = 30;
                }
                else if (perc > 20 && perc < 30)
                {
                    perc = 20;
                }
                else if (perc < 20)
                {
                    perc = 10;
                }
                
                string caminho = "Hud/hud" + perc;
                if (caminho != hudAnt)
                {
                    hud = null;
                    hud = Resources.Load(caminho, typeof(Texture2D)) as Texture2D;
                
                    hudAnt = caminho;
                }
        	}else{
				Application.LoadLevel("GameOver");
			}
		
			
		}
    }
 
	public void OnGUI()
    {
        if (hud != null)
        {
           
                GUI.DrawTexture(new Rect(0, 0, hud.width * 0.9f, hud.height * 0.8f), hud, ScaleMode.StretchToFill);
               
        }
	}
}