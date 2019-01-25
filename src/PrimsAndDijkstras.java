import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class PrimsAndDijkstras {
	public static void main(String[] args) {
		Node A = new Node(1, "A");
		Node B = new Node(2, "B");
		Node C = new Node(3, "C");
		Node D = new Node(4, "D");
		Node E = new Node(5, "E");
		Node F = new Node(6, "F");
		Node G = new Node(7, "G");
		
		A.addNeighbor(B);
		A.addNeighbor(D);
		
		B.addNeighbor(A);
		B.addNeighbor(C);
		B.addNeighbor(E);
		B.addNeighbor(D);
		

		C.addNeighbor(B);
		C.addNeighbor(E);

		
		D.addNeighbor(A);
		D.addNeighbor(B);
		D.addNeighbor(E);
		D.addNeighbor(F);
		
		E.addNeighbor(B);
		E.addNeighbor(C);
		E.addNeighbor(D);
		E.addNeighbor(F);
		E.addNeighbor(G);

		
		F.addNeighbor(D);
		F.addNeighbor(E);
		F.addNeighbor(G);
		
		G.addNeighbor(E);
		G.addNeighbor(F);
		
		int M = Integer.MAX_VALUE;
		int[][] adjMatrix = {{0,7,M,5,M,M,M},{7,0,8,9,7,M,M},{M,8,0,M,5,M,M},{5,9,M,0,15,6,M},{M,7,5,15,0,8,9},{M,M,M,6,8,0,11},{M,M,M,M,9,11,0}};
		//if there is no edge between two nodes their distance is infinity. Some others are 0?
		
		ArrayList<Node> nodes = new ArrayList<>();
		nodes.add(A); 
		nodes.add(B);
		nodes.add(C);
		nodes.add(D);
		nodes.add(E);
		nodes.add(F);
		nodes.add(G);
		
		MyGraph mygraph = new MyGraph(nodes,adjMatrix);
		System.out.println("Start Dijkstra ...");
		mygraph.dijkstraAlg(A);
		
		
	}
	
	

}

class MyGraph {
	ArrayList<Node> nodeList = new ArrayList<>();
	
	int[][] adjMatrix;
	
	public MyGraph(ArrayList<Node> nodeList, int[][] adjMatrix ) {
		this.nodeList = nodeList;
		this.adjMatrix = adjMatrix;
	}
	
	void dijkstraAlg(Node s) {
		//need a priority queue of nodes using d as criteria. Need comparator for the node
		
	PriorityQueue<Node> priorityQueue = new PriorityQueue<Node>(new Comparator<Node>(){
		
		@Override
		public int compare(Node u, Node v) {
			if(u.distance<v.distance) {
				return -1;
			}
			if(u.distance > v.distance) {
				return 1;
			}
			return 0;
			}
	});
	
	s.distance = 0;
	
	for (Node node: this.nodeList) {    ///Q<--V step
		if(node == s) {
			node.distance = 0;
		}
		priorityQueue.add(node);
	}

	print(priorityQueue);
	
	while(!priorityQueue.isEmpty()) {
		print(priorityQueue);
		Node u = priorityQueue.poll();
		System.out.println("Im exploring "+u.name + " with distance " + u.distance);
		ArrayList<Node> neighbors = u.getNeighbors();
		
		for(int i = 0; i < neighbors.size(); i++) {   //for v E u adj
			relax(u,neighbors.get(i), adjMatrix, priorityQueue);
		}
		
		
	}
	print(this.nodeList);
	}
	
	void relax(Node u, Node v, int[][]adjMatrix, PriorityQueue<Node> priorityQueue) {
		int indexOfU = u.data - 1;
		int indexOfV = v.data - 1;
		
		int w = adjMatrix[indexOfU][indexOfV];
		
		if(v.distance > u.distance + w) {  //must update PQ manually. remove wont heapify only add will. without doing this root of heap will be incorrect
			priorityQueue.remove(v);  
			v.distance = u.distance + w;
			v.parent = u;
			priorityQueue.add(v);       //removing then re-adding wont delete but it will update v's position
			System.out.println("\t Im relaxing "+v.name + " via " + u.name);
		}
	}

	void print(PriorityQueue<Node> priorityQueue) {
		for(Node node : priorityQueue) {
			System.out.print("(" + node.name + " , " + node.distance + ") -->");
		}
		System.out.println("End!");
	}

	void print(ArrayList<Node> nodeList) {
		for(Node node : nodeList) {
			System.out.print("(" + node.name + " , " + node.distance + ") -->");
		}
		System.out.println("End!");
	}
}

	


class Node{
	int data;
	String name;
	int color;  //Gray: -1 ; Red: -2; Black: -3;
	ArrayList<Node> neighbors; //neighboring nodes
	
	int distance;  //dijkstras stuff
	Node parent;
	
	
	//d is distance of shortest path, and p is parent on shortest path
	
	Node(int data, String name){
		this.data = data;
		this.color = -1;  //initialize to gray to save time in dfs
		this.neighbors = new ArrayList<Node>();
		this.distance = Integer.MAX_VALUE;
		this.parent = null;
		this.name = name;
	}
	
	ArrayList<Node> getNeighbors(){
		return this.neighbors;
	}
	
	void addNeighbor(Node newNeighbor) { //add neighbor node to neighbors list
		this.neighbors.add(newNeighbor);
	}
	
}