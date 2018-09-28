class BreadthFirstSearch : SearchAlgorithm {

    var frontier : MutableList<Node> = mutableListOf()
    var nodesTraversed : MutableList<Node> = mutableListOf()

    override fun Search(startingNode: Node): List<Node> {
        addNode(startingNode)

        while(!frontier.isEmpty())
        {
            var currentNode = frontier.removeAt(0)

            nodesTraversed.add(currentNode)
            if(currentNode.type == '*') {
                break
            }
            currentNode.visited = true
            currentNode.neighbors.forEach { neighbor -> addNode(neighbor) }
        }
        return nodesTraversed
    }

    fun addNode(node : Node)
    {
        if(node.type != '%' && !node.visited )
        {
            frontier.add(node)
        }
    }
}