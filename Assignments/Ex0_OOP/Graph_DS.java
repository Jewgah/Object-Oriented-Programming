package ex0;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/******************************************************************************
 *  @author Jordan Perez
 *  https://github.com/Jewgah
 *  OOP 2020 - CS Ariel university
 * The Graph_DS class implements the graph Interface.
 * It represents an undirectional unweighted graph.
 ******************************************************************************/

public class Graph_DS implements graph {

    private HashMap<Integer, node_data> nodeMap;
    private int MC=0;
    private int edge=0;

    //Constructor
    public Graph_DS(){
        this.nodeMap = new HashMap<>();
        this.MC=0;
        this.edge=0;
    }

    //CopyConstructor
    public Graph_DS(Graph_DS g){

        this.nodeMap = new HashMap<>();

        //deep copying
        if(g!=null){
            Iterator itr = g.nodeMap.values().iterator();

            while (itr.hasNext()){
                NodeData copyNode = (NodeData) itr.next();
                this.nodeMap.put(copyNode.getKey(),new NodeData(copyNode));
            }
        }

        this.MC = g.getMC();
        this.edge = g.getEdge();
    }


    public int getEdge() {
        return this.edge;
    }


    @Override
    // returns the node_data by the node_id (key)
    public node_data getNode(int key) {
        if(this.nodeMap.containsKey(key)) {
            return this.nodeMap.get(key); //if the node exists return it
        }
        return null; //else return null
    }

    @Override
    //returns true iff (if and only if) there is an edge between node1 and node2
    public boolean hasEdge(int node1, int node2) {
        //  O(1) since containsKey() method for Hashmaps used by hasNi has a O(1) complexity
        return this.nodeMap.get(node1).hasNi(node2);
    }

    @Override
    //add a new node to the graph with the given node_data
    public void addNode(node_data n) {
        //if id isn't used
        if(!nodeMap.containsKey(n.getKey())) {
            this.nodeMap.put(n.getKey(), n); //add the new node to the map
            this.MC++;
        }
        else System.out.println("node wasn't added successfully: key already used");
    }

    @Override
    // Connect an edge between node1 and node2
    public void connect(int node1, int node2) {
        //If both nodes exist
        if(nodeMap.containsKey(node1) && nodeMap.containsKey(node2)) {
            //this is to avoid adding a connection between a node and itself
            if (node1 != node2) {
                //if the the nodes aren't connected yet
                if (!hasEdge(node1, node2)) {
                    //add each of them in each other's neighbour's list
                    this.nodeMap.get(node1).addNi(this.nodeMap.get(node2)); //O(1)
                    this.nodeMap.get(node2).addNi(this.nodeMap.get(node1)); //O(1)
                    this.MC++;
                    this.edge++;
                }
            }
        }
       // else if(!nodeMap.containsKey(node1)) System.out.println("Can't connect: "+node1+" is missing");
       // else if(!nodeMap.containsKey(node2)) System.out.println("Can't connect: "+node2+" is missing");
    }

    @Override
    public Collection<node_data> getV() {
        return this.nodeMap.values();
    }

    @Override
    public Collection<node_data> getV(int node_id) {
        return this.nodeMap.get(node_id).getNi();
    }

    @Override
    //Deletes the node (with the key ID) from the graph and removes all edges which starts or ends at this node - O(n)
    public node_data removeNode(int key) {
        //if the node to remove is in the map
        if (this.nodeMap.containsKey(key)){
            //run on all the map - O(n)
            for (Integer i : this.nodeMap.keySet()) {
                //If node i has an edge with node key
                if(this.nodeMap.get(i).getNi().contains(this.nodeMap.get(key))){ // O(1)
                    //remove edge between i and key - O(1) (not recursive ! using NodeData removeNode method)
                    this.nodeMap.get(i).removeNode(this.nodeMap.get(key));
                    this.edge--;
                }
            }
            MC++;
            //returns the node from the graph and deletes it
            return this.nodeMap.remove(key);
        }
        return null; //else return null
    }

    @Override
    public void removeEdge(int node1, int node2) {
        //If node1 has an edge with node2
        if(this.nodeMap.get(node1).getNi().contains(this.nodeMap.get(node2))){ // O(1)
            this.nodeMap.get(node1).removeNode(this.nodeMap.get(node2)); //remove edge between node1 and node2 - O(1)
            this.nodeMap.get(node2).removeNode(this.nodeMap.get(node1)); //remove edge between node2 and node1 - O(1)
            this.MC++;
            this.edge--;
        }
    }

    @Override
    //returns the number of vertices (nodes) in the graph - O(1)
    public int nodeSize() {
        return this.nodeMap.size();
    }

    @Override
    // returns the number of edges (undirectional graph) - O(1)
    public int edgeSize() {
        return this.edge;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    //tester
    public void ToString() {
        System.out.println(nodeMap.toString());
    }
}