_______________________________________________________________________________________________________________________
NodeData implements node_data
Graph_DS implements graph
Graph_Algo implements graph_algorithms

Those three implementations represent an unweighted graph of nodes and its algorithms.
_______________________________________________________________________________________________________________________
1/ NodeData

The NodeData class implements the node_data Interface.
It represents the set of operations applicable on a node (vertex) in an (undirectional) unweighted graph.


NodeData class attributes are:

private final int key: A unique key(id) associated with this node.
private HashMap<Integer, node_data> neighbours: A Hashmap were the keys are the node keys and the values are all the Neighbor nodes of this node_data.
private String info: Remark (meta data) associated with this node.
private int tag: Temporal data which can be used be algorithms.
private static int countIndex =0: A static int used to be able to call the NodeMap() constructor several times without providing the same key every time.

NodeData uses the following methods:

int getKey(): Returns the unique key (id) associated with this node.
Collection<node_data> getNi(): Returns a collection with all the Neighbor nodes of this node_data.
boolean hasNi(int key): return true iff this<==>key are adjacent, as an edge between them.
void addNi(node_data t): This method adds the node_data (t) to this node_data.
void removeNode(node_data node): Removes the edge this-node.
void setInfo(String s): Allows changing the remark (meta data) associated with this node.
int getTag(): Temporal data which can be used be algorithms.
HashMap<Integer, node_data> getNeighbours(): Returns the Hashmap neighbours.

NodeData has a Constructor and a CopyConstructor.
In the CopyConstructor we call the putAll() method from the Hashmap library - O(1) - to copy all the nodes.
_______________________________________________________________________________________________________________________
2/ Graph_DS

The Graph_DS class implements the graph Interface.
It represents an undirectional unweighted graph.


Graph_DS class attributes are:

private HashMap<Integer, node_data> nodeMap: A Hashmap representing the graph with all its nodes.
private int MC: Mode Count: Any change in the inner state of the graph increments it.
private int edge: Number of edges (undirectional graph).


Graph_DS uses the following methods:

node_data getNode(int key): Returns the node_data by the node_id.
boolean hasEdge(int node1, int node2): Return true iff (if and only if) there is an edge between node1 and node2. - O(1)
void addNode(node_data n): Adds a new node to the graph with the given node_data. - O(1)
void connect(int node1, int node2): Connects an edge between node1 and node2. - O(1)
Collection<node_data> getV(): Returns a pointer (shallow copy) for the collection representing all the nodes in the graph. - O(1)
Collection<node_data> getV(int node_id)returns a collection containing all the nodes connected to node_id - O(1)
node_data removeNode(int key): Delete the node (with the given ID) from the graph and removes all edges which starts or ends at this node - O(n), |V|=n
void removeEdge(int node1, int node2): Deletes the edge from the graph - O(1)
int nodeSize(): Returns the number of vertices (nodes) in the graph. - O(1)
int edgeSize(): Returns the number of edges (undirectional graph) - O(1)
int getMC(): Returns the Mode Count - for testing changes in the graph. Any change in the inner state of the graph increments it.

Graph_DS has a Constructor and a CopyConstructor.
It's Constructor is a regular one, but it's CopyConstructor is special: it allows a deep copy of the Graph_DS received as parameter using an Iterator
by calling the CopyConstructor of NodeData for each node.


The hasEdge() method has an O(1) complexity since containsKey() method for Hashmaps used by hasNi has a O(1) complexity.
The connect() method simply adds node1 and node2 to each other's neighbours list, then increments edge and MC by one.


_______________________________________________________________________________________________________________________
3/ Graph_Algo

The Graph_Algo class implements the graph_algorithms Interface.
It represents the "regular" Graph Theory algorithms.


Graph_Algo class attributes is:

graph myGraph: The (unweighted) graph on which we will use all or algorithms.


Graph_Algo uses the following methods:

void init(graph g): Init the graph on which this set of algorithms operates on.
graph copy(): Computes a deep copy of this graph.
boolean isConnected(): Returns true if and only if (iff) there is a valid path from EVERY node to each other node.
int shortestPathDist(int src, int dest): Returns the length of the shortest path between src to dest. Returns -1 if there isn't any path.
List<node_data> shortestPath(int src, int dest): returns the shortest path between src to dest - as an ordered List of nodes: src--> n1-->n2-->...dest.
void init_Tag(Collection<node_data> nodeList): Sets every node's tag to 0 (unvisited).
boolean checkTags(Collection<node_data> nodeList): Iterates on all the nodes and checks if every tag = 1 (visited).


The init() method sets the graph we will work our algorithms on.
The copy() method calls the Graph_DS CopyConstructor which calls itself the NodeData CopyConstructor for each node, thus performing a deep copy.
The isConnected() method checks if a graph is strongly connected (Kachir) using the BFS algorithm.

BFS is a graph traversal algorithm that starts traversing the graph from a random node and explores all the neighbours nodes,
then do the same with all it's unexplored nearest nodes.The algorithm first initiates all tags to 0 by calling the init_Tag() method.
Then it sets tag to 1 as visited each time it visits a node.The algorithm follows the same process by using a queue stocking all explored nodes,
until the queue is empty. Then it uses a method called checksTag() to check if all tags are marked as visited (==1)
meaning that all the neighbours are connected by an existing path.
_________________________________________________________________________________________________________