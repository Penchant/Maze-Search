class BreadthFirstSearch : SearchAlgorithm {

    var frontier : MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node) {
        frontier.add(startingNode)
        var currentNode = startingNode
        var iterations = 0
        while(!frontier.isEmpty())
        {
            if(currentNode.type == '*') {
                break
            }
            currentNode.visited = true
            addNodes(currentNode)
            currentNode = frontier.removeAt(0)
            iterations++
            if (iterations % 10 == 0){
                frontier = frontier.distinct().toMutableList()
            }
        }
        unwindPath(currentNode)
    }

    fun addNodes(node : Node) {
        node.neighbors.forEach {neighbor ->
            if(neighbor.type != '%' && !neighbor.visited)
            {
                frontier.add(neighbor); neighbor.pastNode = node
            }
        }
    }
}