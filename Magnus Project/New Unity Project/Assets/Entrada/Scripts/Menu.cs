using UnityEngine;
using System.Collections;

public class Menu : MonoBehaviour {
	
	public Texture2D fundo;
	public GUIStyle estilo;
	// Use this for initialization
	void Start () {
       
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	
	void OnGUI (){
		GUI.DrawTexture(new Rect(0,0,1024,768),fundo);
		if (GUI.Button ( new Rect (Screen.width/2-50,Screen.height/2-10,100,20), "Começar")) {
			Application.LoadLevel("Atual+ai");
		}
		GUI.Label(new Rect(Screen.width/2-100,20,200,40),"Projeto Maguns",estilo);
	}
}
