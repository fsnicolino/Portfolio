using UnityEngine;
using System.Collections;

public class Onda : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
	
	}
	void OnParticleCollision(GameObject other){
		if(other.tag == "inimigo"){
			Destroy(other);
		}
	}
}
