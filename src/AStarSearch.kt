class AStarSearch : SearchAlgorithm {

    var openNodes: MutableList<Node> = mutableListOf()
    var closedNodes: MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node): List<Node> {

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
                println("found goal at " + current.coordsString())

                path.add(current)
                while (path.last().type != 'P') {
//                    while not reached the start yet
                    path.last().visited = true
                    path.add(cameFrom.getOrDefault(path.last(), Node()))
                    pathLength++
                }
                for (a in closedNodes) a.exploredNotUsed = true
                println("path of length $pathLength")
                println("explored ${closedNodes.size} nodes")
                println("%.2f percent of explored nodes used in solution".format((pathLength.toDouble() / closedNodes.size.toDouble()) * 100))
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
            }
        }
        return path
    }
}