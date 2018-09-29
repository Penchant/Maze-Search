class DepthFirstSearch : SearchAlgorithm {

    var frontier : MutableList<Node> = mutableListOf()
    var nodesTraversed : MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node): List<Node> {
        addNode(startingNode)

        while(!frontier.isEmpty())
        {
            var currentNode = frontier.removeAt(frontier.lastIndex)

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