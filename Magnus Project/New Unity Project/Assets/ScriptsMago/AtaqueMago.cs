using UnityEngine;
using System.Collections;

public class AtaqueMago : MonoBehaviour {

    public GameObject prefab;
    private GameObject instance  = new GameObject();
    public bool fire = false;
    private float timer;

   
	private void Update () 
    {        
        if (Input.GetKey("a"))
        {
            if (fire == false)
            {
                instance = Instantiate(prefab, transform.position, transform.rotation) as GameObject;
                fire = true;
                timer = 2.0f;
                
            }
        }

        if (timer > 0)
        {
            timer -= Time.deltaTime;
        }
        else if (timer <= 0)
        {
            fire = false;
        }
	
	}

   

}
