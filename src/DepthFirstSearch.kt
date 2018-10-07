import java.util.*

class DepthFirstSearch : SearchAlgorithm {

    var frontier : MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node) {
        frontier.add(startingNode)
        var currentNode = startingNode
        while(!frontier.isEmpty())
        {
            if(currentNode.type == '*') {
                break
            }
            currentNode.visited = true

            addNodes(currentNode)
            currentNode = frontier.removeAt(frontier.lastIndex)
        }
        unwindPath(currentNode)
    }

    fun addNodes(node : Node) {
        node.neighbors.filter {neighbor -> neighbor.type != '%' && !neighbor.visited }
                .forEach { neighbor -> frontier.add(neighbor); neighbor.pastNode = node }
    }
}

fun unwindPath(goalNode : Node) {
    println("Starting unwind")
    var currentNode = goalNode
    var pathLength = 0
    while(currentNode.pastNode != currentNode)
    {
        currentNode.onPath = true
        currentNode = currentNode.pastNode
        pathLength++
    }
    println("Path length: $pathLength")
}