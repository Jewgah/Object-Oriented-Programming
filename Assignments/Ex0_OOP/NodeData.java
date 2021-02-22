package ex0;

import java.util.Collection;
import java.util.HashMap;
/******************************************************************************
 *  @author Jordan Perez
 *  https://github.com/Jewgah
 *  OOP 2020 - CS Ariel university
 *  The NodeData class implements node_data Interface.
 *  It represents the set of operations applicable on a node (vertex) in an (undirectional) unweighted graph.
 ******************************************************************************/


public class NodeData implements node_data {

    private final int key;
    private HashMap<Integer, node_data> neighbours;
    private String info;
    private int tag;
    private static int countIndex =0;

    //Constructor
    public NodeData() {
        this.key = this.countIndex++;
        this.neighbours = new HashMap<>();
        this.info = "";
        this.tag = -1;
    }


    //used for personal testers
    public NodeData(int key) {
        this.neighbours = new HashMap<>();
        this.info = "";
        this.tag = -1;
        this.countIndex = key;
        this.key = this.countIndex++;
    }

    public NodeData(NodeData n) {

        this.neighbours = new HashMap<>();
        this.neighbours.putAll(n.getNeighbours());
        this.info = n.getInfo();
        this.tag = n.getTag();
        this.key = n.getKey();
    }

    @Override
    //returns key
    public int getKey() {
        return this.key;
    }

    @Override
    // return neighbour's map
    public Collection<node_data> getNi() {
        return this.neighbours.values();
    }

    @Override
    //returns true iff this node and key node are neighbours
    public boolean hasNi(int key) {

        return this.neighbours.containsKey(key); //O(1)
    }

    @Override
    //Adds node t to the map O(1)
    public void addNi(node_data t) {
        this.neighbours.put(t.getKey(), t);

    }

    @Override
    //Removes the edge between this and node
    public void removeNode(node_data node) {
        if (this.neighbours.containsValue(node)) {
            this.neighbours.remove(node.getKey());
        }
    }

    @Override
    //returns info
    public String getInfo() {
        return this.info;
    }

    @Override
    //sets info to s
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    //returns tag
    public int getTag() {
        return this.tag;
    }

    @Override
    //sets tag to t
    public void setTag(int t) {
        this.tag = t;
    }

    public HashMap<Integer, node_data> getNeighbours() {
        return this.neighbours;
    }
}