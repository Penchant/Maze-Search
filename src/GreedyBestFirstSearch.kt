class GreedyBestFirstSearch : SearchAlgorithm {

    var frontier : MutableList<Node> = mutableListOf()
    var nodesTraversed : MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node): List<Node> {
        addNode(startingNode)
        var pastNode = startingNode
        var currentNode = startingNode
        var count = 0
        while(!frontier.isEmpty())
        {
            count++
            if(count > 1000){
                break
            }

            pastNode = currentNode

            var sortedNeighbors = currentNode.neighbors.sortedWith(Comparator.comparing<Node, Int> { neighbor -> neighbor.manhattanToGoal })
            sortedNeighbors = sortedNeighbors.filter { neighbor -> neighbor.type != '%' && !(neighbor.visited && neighbor.deadEnd)}
            currentNode = if(sortedNeighbors.count() > 1)
            {
                sortedNeighbors.first{neighbor -> neighbor != pastNode}
            } else {
                sortedNeighbors[0]
            }
            if(!currentNode.visited)
                nodesTraversed.add(currentNode)
            if(pastNode.deadEnd && currentNode.neighbors.filter { neighbor -> neighbor.type != '%' }.count() == 1)
                currentNode.deadEnd = true
            currentNode.visited = true
            if(currentNode.type == '*')
            {
                break
            }
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