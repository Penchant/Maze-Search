class AStarSearch : SearchAlgorithm {

    var openNodes: MutableList<Node> = mutableListOf()
    var closedNodes: MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node) {

        var cameFrom: HashMap<Node, Node> = hashMapOf()
        var gScore: HashMap<Node, Int> = hashMapOf()
        var fScore: HashMap<Node, Int> = hashMapOf()
        var path: MutableList<Node> = mutableListOf()

        openNodes.add(startingNode)

        while (openNodes.isNotEmpty()) {
            openNodes.sortBy { x -> fScore.getOrDefault(x, Int.MAX_VALUE) }
            var current = openNodes[0]
            if (current.type == '*') {
//                if we have found the goal, mark the nodes on the path as visited
                var pathLength = 0

                path.add(current)
                while (path.last().type != 'P') {
//                    while not reached the start yet
                    path.last().onPath = true
                    path.add(cameFrom.getOrDefault(path.last(), Node()))
                    pathLength++
                }
                closedNodes.forEach { node: Node -> node.visited = true }
                println("path of length $pathLength")
                break
            }
            openNodes.removeAt(0)
            closedNodes.add(current)
            if (closedNodes.size % 10000 == 0) println("closed ${closedNodes.size} nodes")
            for (neighbor in current.neighbors) {
                if (neighbor in closedNodes || neighbor.type == '%') continue
                val newDistance = gScore.getOrDefault(current, Int.MAX_VALUE) + current.manhattanToNode(neighbor)
                if (neighbor !in openNodes) {
                    openNodes.add(neighbor)
//                    println("adding " + neighbor.coordsString() + " to openNodes")
                } else if (newDistance >= gScore.getOrDefault(neighbor, Int.MAX_VALUE)) {
                    continue
                }
                cameFrom[neighbor] = current
                gScore[neighbor] = newDistance
                fScore[neighbor] = gScore.getOrDefault(neighbor, Int.MAX_VALUE) + neighbor.manhattanToGoal
            }
        }
    }
}