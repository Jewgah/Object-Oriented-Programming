package ex0;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/******************************************************************************
 *  @author Jordan Perez
 *  https://github.com/Jewgah
 *  OOP 2020 - CS Ariel university
 * The Graph_Algo class implements the graph_algorithms Interface.
 * It represents the "regular" Graph Theory algorithms..
 ******************************************************************************/

public class Graph_Algo implements graph_algorithms {

    private graph myGraph;


    //Constructor
    public Graph_Algo() {
        this.myGraph = new Graph_DS();
    }

    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g
     */
    @Override
    public void init(graph g) {
        this.myGraph =g;
    }

    /**
     * Compute a deep copy of this graph.
     * @return deep copy of this graph
     */
    @Override
    public graph copy() {

        graph myCopy = new Graph_DS((Graph_DS) this.myGraph);
        return myCopy;
    }

    /**
     * @return true if and only if (iff) there is a valid path from EVERY node to each other node (kachir)
     */
    @Override
    public boolean isConnected() {
        if (this.myGraph == null) {
            return false;
        }
        if(this.myGraph.nodeSize() == 0 || this.myGraph.nodeSize() == 1) {
            return true;
        }

        Collection<node_data> nodeList = this.myGraph.getV();
        if (BFS(nodeList, this.myGraph)) return true;
        else return false;
    }


    /**
     * Breath First Search algorithm:
     * <p>
     * <p>
     * Given a Collection of nodes_data from a given graph,
     * the BFS algorithm checks if the graph KACHIR.
     * First we initiate all tags to 0 by calling init_Tag() method.
     * BFS is a graph traversal algorithm that starts traversing the graph from a random node and explores
     * all the neighbours nodes, then do the same with all it's unexplored nearest nodes.
     * Each time we visit a node we set its tag to 1, as visited.
     * The algorithm follows the same process by using a queue stocking all explored nodes, until
     * the queue is empty which means all the neighbours are connected by an existing path
     * Then we use a method called checksTag() to check if all tags are marked as visited (==1).
     *
     * @param myNodeList collections of node_data.
     * @param g          given graph.
     * @return boolean , true: if all nodes have been visited, false if not;
     */
    static private boolean BFS(Collection<node_data> myNodeList, graph g) {

        Queue<node_data> q = new LinkedList<>();

        if (myNodeList.isEmpty()) {
            return false;
        }

        //initializes all nodes tag to 0 (unvisited)
        init_Tag(g.getV());

        Iterator hit = myNodeList.iterator();
        node_data a = (node_data) hit.next();
        a.setTag(1); //set first node to visited
        q.add(a);    //adds it to queue


        while (!q.isEmpty()) {
            System.out.println(q.size());

            //dequeue the first in queue.
            node_data first = q.remove();
            //getting all the neighbours
            Collection<node_data> neigh = g.getV(first.getKey());
            if (neigh == null) {
                //if there are no neighbours then there are no edges
                // meaning the graph cannot be connected. then return false
                return false;
            }

            Iterator bgu = neigh.iterator();
            while (bgu.hasNext()) {  //iterating over the neighbours nodes
                node_data ed = (node_data) bgu.next();
                int next = ed.getKey();    //gets the next neighbour.
                if (g.getV().contains(ed)) {
                    if (g.getNode(next).getTag() == 0) {    //checks if already visited.
                        g.getNode(next).setTag(1);    //if not visited set to visited.
                        q.add(g.getNode(next));    //adds the node to the queue.
                    }
                }
            }
        }
        return checkTags(myNodeList); //checks the tag on all vertexes.
    }

    /**
     * sets every node's tag to 0 (unvisited)
     *
     * @param nodeList
     */
    static private void init_Tag(Collection<node_data> nodeList) {
        Iterator itr = nodeList.iterator();
        while (itr.hasNext()) {
            node_data node = (node_data) itr.next();
            node.setTag(0);
        }
    }

    /**
     * Iterates on all the nodes and checks if every tag = 1 (visited). If they're all visited the graph is kachir
     *
     * @param nodeList
     * @return true: all tags == 1 , false: if at least one tag == 0 ;
     */
    private static boolean checkTags(Collection<node_data> nodeList) {

        Iterator cavs = nodeList.iterator();
        while (cavs.hasNext()) {
            node_data check = (node_data) cavs.next();
            if (check.getTag() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * returns the length of the shortest path between src to dest
     *
     * @param src
     * @param dest
     * @return path's length if exists, else return -1
     */
    @Override
    public int shortestPathDist(int src, int dest) {

        List directions = shortestPath(src, dest);
        if (directions == null) return -1;
        else return directions.size() - 1;
    }

    /**
     * returns the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * Note if no such path --> returns null;
     *
     * @param dest
     * @param src
     * @return returns a List of nodes representing the shortest path from src to dest if it exists
     * and returns null if it doesn't
     */

    @Override
    public List<node_data> shortestPath(int src, int dest) {

        HashMap<node_data, node_data> zbao = new HashMap<>();

        List visa = new LinkedList<node_data>();
        List directions = new LinkedList<node_data>();
        Queue q = new LinkedList<node_data>();

        node_data current = myGraph.getNode(src);
        //adding to the queue the source node
        q.add(current);

        //src node is visited by default, so we set it as true (visited)
        visa.add(current);
        //while there are nodes in the queue
        while (!q.isEmpty()) {
            //reads the current node and remove it from the queue
            current = (node_data) q.remove();
            //if we arrived at destination node, break
            if (current.equals(myGraph.getNode(dest))) {
                break;
            } else {
                //for adjacent nodes of n
                for (node_data node : current.getNi()) {
                    //if visited value is false (if node is yet unvisited)
                    if (!visa.contains(node)) {
                        //add it to queue and to prev and make it true (visited)
                        q.add(node);
                        visa.add(node);
                        zbao.put(node, current);
                    }
                }
            }
        }
        if (!current.equals(myGraph.getNode(dest))) {
            return null;
        }


        for (node_data node = myGraph.getNode(dest); node != myGraph.getNode(src); node = zbao.get(node)) {

            directions.add(node);
        }
        directions.add(myGraph.getNode(src));
       // System.out.println(printNodeCollection(directions));
        Collections.reverse(directions);
        return directions;

    }

//    public static String printNodeCollection(Collection<node_data> node) {
//
//        String str = "[";
//        int a = 0;
//        for (node_data n : node) {
//            if (a == 0) {
//                str += n.getKey();
//            } else {
//                str += " , " + n.getKey();
//            }
//            a++;
//        }
//        str += "]";
//        return str;
//    }

}
