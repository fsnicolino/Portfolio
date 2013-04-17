using UnityEngine;
using System.Collections;
using Pathfinding;

/** Example AI */
[RequireComponent (typeof(Seeker))]
[RequireComponent (typeof(CharacterController))]
public class AIFollow : MonoBehaviour {
	
	/** Target to move to */
	public Transform target;
	
	public GameObject modeloPersonagem;
	
	public string ataque;
	public float distanciaMinimaAtaque = 2f;
	public float distanciaMinimaFollow = 10f;
	public bool isBoss = false;
	public string andando;
    public string ataque2;
	public string morrendo;
    public int vida = 200;

    private int direcaoAtual = 0;
	
	/** How often to search for a new path */
	public float repathRate = 0.1F;
	
	/** The minimum distance to a waypoint to consider it as "reached" */
	public float pickNextWaypointDistance = 1F;
	
	/** Units per second */
	public float speed = 5;
	
	/** How fast the AI can turn around */
	public float rotationSpeed = 1;
	
	/** Seeker component which handles pathfinding calls */
	protected Seeker seeker;
	
	/** CharacterController which handles movement */
	protected CharacterController controller;
	
	/** NavmeshController which handles movement if not null*/
	protected NavmeshController navmeshController;
	
	/** Transform, cached because of performance */
	protected Transform tr;
	
	protected float lastPathSearch = -9999;
	
	protected int pathIndex = 0;
	
	/** This is the path the AI is currently following */
	protected Vector3[] path;
	
	
	
	public void Awake(){		
		modeloPersonagem.animation[andando].wrapMode = WrapMode.Loop;
		modeloPersonagem.animation[ataque].speed = 0.6f;
		modeloPersonagem.animation[ataque].wrapMode = WrapMode.Loop;
		modeloPersonagem.animation.Play(andando);
	}
	
	/** Use this for initialization */
	public void Start () {
		seeker = GetComponent<Seeker>();
		controller = GetComponent<CharacterController>();
		navmeshController = GetComponent<NavmeshController>();
		tr = transform;
		Repath ();
	}
	
	/** Called when a path has completed it's calculation */
	public void OnPathComplete (Path p) {
		
		/*if (Time.time-lastPathSearch >= repathRate) {
			Repath ();
		} else {*/
			StartCoroutine (WaitToRepath ());
		//}
		
		//If the path didn't succeed, don't proceed
		if (p.error) {
			return;
		}
		
		//Get the calculated path as a Vector3 array
		path = p.vectorPath;
		
		//Find the segment in the path which is closest to the AI
		//If a closer segment hasn't been found in '6' iterations, break because it is unlikely to find any closer ones then
		float minDist = Mathf.Infinity;
		int notCloserHits = 0;
		
		for (int i=0;i<path.Length-1;i++) {
			float dist = Mathfx.DistancePointSegmentStrict (path[i],path[i+1],tr.position);
			if (dist < minDist) {
				notCloserHits = 0;
				minDist = dist;
				pathIndex = i+1;
			} else if (notCloserHits > 6) {
				break;
			}
		}
	}
	
	public IEnumerator WaitToRepath () {
		float timeLeft = repathRate - (Time.time-lastPathSearch);
		
		yield return new WaitForSeconds (timeLeft);
		Repath ();
	}
	
	/** Stops the AI. Does not prevent new path calls from making the AI move again */
	public void Stop () {
		pathIndex = -1;
	}
	
	/** Recalculates the path to #target */
	public virtual void Repath () {
		lastPathSearch = Time.time;
		
		if (seeker == null || target == null) {
			StartCoroutine (WaitToRepath ());
			return;
		}
		
		//Start a new path from transform.positon to target.position, return the result to the function 	OnPathComplete
		seeker.StartPath (transform.position,target.position,OnPathComplete);
		
	}
	
	/** Start a new path moving to \a targetPoint */
	public void PathToTarget (Vector3 targetPoint) {
		lastPathSearch = Time.time;
		
		if (seeker == null) {
			return;
		}
		
		//Start a new path from transform.positon to target.position, return the result to OnPathComplete
		seeker.StartPath (transform.position,targetPoint,OnPathComplete);
	}
	
	public virtual void ReachedEndOfPath () {
		//The AI has reached the end of the path
	}
	
	/** Update is called once per frame */
	public void Update () {
		
		target = GameObject.Find("Global").GetComponent<ControleGlobal>().currentJogador;
		if(!isBoss){
			if(Vector3.Distance(target.position, transform.position) <= distanciaMinimaFollow){
				if (path == null || pathIndex >= path.Length || pathIndex < 0) {
					return;
				}
				
				if(Vector3.Distance(target.position, transform.position) <= distanciaMinimaAtaque){
	                if (modeloPersonagem.animation.IsPlaying(andando))
	                {
	
	
	                    modeloPersonagem.animation.CrossFade(ataque);
	                    gameObject.audio.Play();
	                }
	                else
	                {
	                    //Debug.Log(modeloPersonagem.animation[ataque].time * 100 / 1.56);
						double posAtualAnim = modeloPersonagem.animation[ataque].time * 100 / 1.56;
	                    if (posAtualAnim >= 20 && posAtualAnim <= 21)
	                    {
	                        target.rigidbody.AddForce(-40, 0, 0, ForceMode.Impulse);
							target.GetComponent<BarraVida>().HP -= 20;
	                    }
	                  
	                }
						
				}else{
					if(modeloPersonagem.animation.IsPlaying(ataque)){
						
						modeloPersonagem.animation.CrossFade(andando);
					}
					//Change target to the next waypoint if the current one is close enough
					Vector3 currentWaypoint = path[pathIndex];
					currentWaypoint.y = tr.position.y;
					while ((currentWaypoint - tr.position).sqrMagnitude < pickNextWaypointDistance*pickNextWaypointDistance) {
						pathIndex++;
						if (pathIndex >= path.Length) {
							//Use a lower pickNextWaypointDistance for the last point. If it isn't that close, then decrement the pathIndex to the previous value and break the loop
							if ((currentWaypoint - tr.position).sqrMagnitude < (pickNextWaypointDistance*0.2)*(pickNextWaypointDistance*0.2)) {
								ReachedEndOfPath ();
								return;
							} else {
								pathIndex--;
								//Break the loop, otherwise it will try to check for the last point in an infinite loop
								break;
							}
						}
						currentWaypoint = path[pathIndex];
						currentWaypoint.y = tr.position.y;
					}
					
					
					Vector3 dir = currentWaypoint - tr.position;
					
					// Rotate towards the target
					tr.rotation = Quaternion.Slerp (tr.rotation, Quaternion.LookRotation(dir), rotationSpeed * Time.deltaTime);
					tr.eulerAngles = new Vector3(0, tr.eulerAngles.y, 0);
					
					Vector3 forwardDir = transform.forward;
					//Move Forwards - forwardDir is already normalized
					forwardDir = forwardDir * speed;
					forwardDir *= Mathf.Clamp01 (Vector3.Dot (dir, tr.forward));
					
					if (navmeshController != null) {
						navmeshController.SimpleMove (tr.position,forwardDir);
					} else {
						controller.SimpleMove (forwardDir);
					}
				}
			}
		}else{
			if(Vector3.Distance(target.position, transform.position) <= distanciaMinimaFollow){
				if (path == null || pathIndex >= path.Length || pathIndex < 0) {
					return;
				}

                if (vida < 50)
                {
                    //ataque2
                    if (modeloPersonagem.animation.IsPlaying(andando))
                    {


                        modeloPersonagem.animation.CrossFade(ataque2);
                        //gameObject.audio.Play();
                    }
                }
                else
                {


                    if (Vector3.Distance(target.position, transform.position) <= distanciaMinimaAtaque)
                    {
                        if (modeloPersonagem.animation.IsPlaying(andando))
                        {


                            modeloPersonagem.animation.CrossFade(ataque);
                            gameObject.audio.Play();
                        }
                        else
                        {
                            //Debug.Log(modeloPersonagem.animation[ataque].time * 100 / 1.56);
                            double posAtualAnim = modeloPersonagem.animation[ataque].time * 100 / 1.56;
                            if (posAtualAnim >= 20 && posAtualAnim <= 21)
                            {
                                target.rigidbody.AddForce(-40, 0, 0, ForceMode.Impulse);
                                target.GetComponent<BarraVida>().HP -= 40;
                            }

                        }

                    }
                    else
                    {
                        if (modeloPersonagem.animation.IsPlaying(ataque))
                        {

                            modeloPersonagem.animation.CrossFade(andando);
                        }
                        //Change target to the next waypoint if the current one is close enough
                        Vector3 currentWaypoint = path[pathIndex];
                        currentWaypoint.y = tr.position.y;
                        while ((currentWaypoint - tr.position).sqrMagnitude < pickNextWaypointDistance * pickNextWaypointDistance)
                        {
                            pathIndex++;
                            if (pathIndex >= path.Length)
                            {
                                //Use a lower pickNextWaypointDistance for the last point. If it isn't that close, then decrement the pathIndex to the previous value and break the loop
                                if ((currentWaypoint - tr.position).sqrMagnitude < (pickNextWaypointDistance * 0.2) * (pickNextWaypointDistance * 0.2))
                                {
                                    ReachedEndOfPath();
                                    return;
                                }
                                else
                                {
                                    pathIndex--;
                                    //Break the loop, otherwise it will try to check for the last point in an infinite loop
                                    break;
                                }
                            }
                            currentWaypoint = path[pathIndex];
                            currentWaypoint.y = tr.position.y;
                        }


                        Vector3 dir = currentWaypoint - tr.position;

                        // Rotate towards the target
                        //tr.rotation = Quaternion.Slerp (tr.rotation, Quaternion.LookRotation(dir), rotationSpeed * Time.deltaTime);
                        //tr.eulerAngles = new Vector3(0, tr.eulerAngles.y, 0);


                        if (direcaoAtual == 1)
                        {
                            if (target.position.x < transform.position.x)
                            {
                                Vector3 posAnt = transform.localPosition;
                                transform.Rotate(new Vector3(0, 180, 0), Space.Self);
                                transform.localPosition = posAnt;
                                direcaoAtual = 0;
                            }
                        }
                        else
                        {
                            if (target.position.x > transform.position.x)
                            {
                                Vector3 posAnt = transform.localPosition;
                                transform.Rotate(new Vector3(0, 180, 0), Space.Self);
                                transform.localPosition = posAnt;
                                direcaoAtual = 1;
                            }
                        }




                        Vector3 forwardDir = transform.forward;
                        //Move Forwards - forwardDir is already normalized
                        forwardDir = forwardDir * speed;
                        forwardDir *= Mathf.Clamp01(Vector3.Dot(dir, tr.forward));

                        if (navmeshController != null)
                        {
                            navmeshController.SimpleMove(tr.position, forwardDir);
                        }
                        else
                        {
                            controller.SimpleMove(forwardDir);
                        }
                    }
                }
			}	
		}
		
	}
   
}
