package it.polito.tdp.ufo.bean;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import com.sun.javafx.geom.Edge;

import it.polito.tdp.ufo.db.UfoDAO;

public class Model {

	UfoDAO dao ; 

	private List<Integer> listAnni;
	private List<CoppiaInt> listAnniAvvistamenti;
	private List<Sighting> ListSightingStati;
	private DirectedGraph<Sighting, DefaultEdge> graph;

	public Model() {
		
		dao = new UfoDAO();
		if(listAnni==null) listAnni= dao.getAllAnni();
		if(listAnniAvvistamenti==null) listAnniAvvistamenti= dao.getAllCoppiaIntAnni();

		
	}
	
	public List<Integer>getAllAnni(){
		return listAnni;
	}

	public List<CoppiaInt>getAllCoppiaIntAnni(){
		return listAnniAvvistamenti;
	}

	public List<Sighting> getAllStati(int anno){
		if(ListSightingStati==null) 	ListSightingStati=dao.getAllStati(anno) ;					 			
		System.out.println(ListSightingStati );
		return ListSightingStati;
	}

	public void  creaGrafo(int anno) {
		String s="";
		if(graph==null)		this.graph = new DefaultDirectedGraph<>(DefaultEdge.class);
		
		//aggiungo vertici stati degli avvistamenti      
			
		Graphs.addAllVertices (graph, this.getAllStati(anno)); 
		System.out.println("Grafo creato: " + graph.vertexSet().size() +"  vertici "  );	//ok
		
		//creo archi
		for (Sighting a1 : graph.vertexSet()) {
			for (Sighting a2 : graph.vertexSet()) {		
				if (!a1.equals(a2)) {	
					Double p = (double) ChronoUnit.DAYS.between(a1.getDatetime(), a2.getDatetime());
					//se distanza input < distanza calcolata aggiungo archi				
					if (p<0)	{
						Graphs.addEdgeWithVertices(graph, a1,a2);
						//stringa grafo 
							s += a1+" "+a2+" "+p+"\n";
						}		
				}
			}
		 }
			//stampo
			System.out.println("Grafo creato: " + graph.vertexSet().size() + " nodi, " + graph.edgeSet().size() + " archi");
			System.out.println(s);
//			System.out.println(graph);
			
	}
	
//-------------------------------ok

	
	
/**	Una volta costruito il grafo, SELEZIONARE STATO del grafo si determini:
		- stati immediatamente precedenti e successivi 
		- quanti e quali altri stati sono raggiungibili(attraverso uno o piu archi). 
*/	
	
	public List<Sighting> trovaSucessori(Sighting s) {	
		List<Sighting> successori = new ArrayList<Sighting>();
		successori.addAll(Graphs.successorListOf(graph,s));
					
		return successori;
	}
	public List<Sighting> trovaPredecessori(Sighting s) {
		List<Sighting>predecessori = new ArrayList<Sighting>();
		predecessori.addAll(Graphs.predecessorListOf(graph,s));
		return predecessori;
	}

	List<Sighting> vicini = new ArrayList<Sighting>();
	
	public List<Sighting> getRaggiungibiliInAmpiezza(Sighting partenza)
	{	
		BreadthFirstIterator<Sighting, DefaultEdge> visita = 
				new BreadthFirstIterator<Sighting, DefaultEdge>( this.graph, partenza);
		
		while(visita.hasNext()) //Finchè ogni nodo ha un successore
		{
			Sighting s = visita.next(); //Prendo il successore
			vicini.add(s); //e lo metto nella lista
		}
		return vicini;
	}
	public int raggiungibili(){
		return vicini.size();
	}

	
	public static void main(String[] args) {
		
		Model model = new Model();	
		
		System.out.println("-------- Grafo ---------");	
		model.creaGrafo(2014) ;
		String st="ca";
		Sighting s= new Sighting(7613, "ca");

		
		System.out.println("\n");
		System.out.println("-------- Sucessori ---------");
		System.out.println("\n");
		
		List<Sighting> trovaSucessori =model.trovaSucessori(s);
		System.out.println(trovaSucessori);

		System.out.println("\n");
		System.out.println("-------- Predecessori ---------");
		System.out.println("\n");
		
		List<Sighting> trovaPredeccessori =model.trovaPredecessori(s);
		System.out.println(trovaPredeccessori);

		System.out.println("\n");
		System.out.println("-------- raggiungibili ---------");
		System.out.println("\n");
		
		List<Sighting> trovaRaggiungibili =model.getRaggiungibiliInAmpiezza(s);
		System.out.println(trovaRaggiungibili);
		
		System.out.println("\n");
		System.out.println("-------- quanti raggiungibili? ---------");
		System.out.println("\n");
		
		int n=model.raggiungibili();
		System.out.println(n);
	}

}
	
	
	


