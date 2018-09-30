class AStarSearch : SearchAlgorithm {

    var openNodes: MutableList<Node> = mutableListOf()
    var closedNodes: MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node): MutableList<Pair<Node, List<Node>>> {

        var cameFrom: HashMap<Node, Pair<Node, List<Node>>> = hashMapOf()
        var gScore: HashMap<Node, Int> = hashMapOf()
        var fScore: HashMap<Node, Int> = hashMapOf()
        var path: MutableList<Pair<Node, List<Node>>> = mutableListOf()

        openNodes.add(startingNode)

        while (openNodes.isNotEmpty()) {

            openNodes.sortBy { x -> fScore.getOrDefault(x, Int.MAX_VALUE) }
            var current = Pair(openNodes[0], openNodes.toList())

            if (current.first.type == '*') {

                println("you won")
                while (cameFrom.getOrDefault(current.first, Pair(Node(), listOf())).first != startingNode) {
//                    cameFrom.getOrDefault(current, Node()).onPath = true
                    current = cameFrom.getOrDefault(current.first, Pair(Node(), listOf()))
                    path.add(Pair(current.first, current.second))
                    if (current.first == Node()) {
                        println("the path is broken")
                        break
                    }
                }
                path.reverse()
                break
            }
            openNodes.removeAt(0)
            closedNodes.add(current.first)

//            closedNodes.forEach { node: Node -> println(node.coordsString()) }
//            current.neighbors.forEach { node: Node -> cameFrom[node] = Pair(current, openNodes.toList()) }
//            cameFrom[neighbor] = Pair(current, closedNodes.toList())
//            println()

            if (closedNodes.size % 100000 == 0) println("closed ${closedNodes.size} nodes")

            for (neighbor in current.first.neighbors) {
                if (neighbor in closedNodes || neighbor.type == '%') continue
                val newDistance = gScore.getOrDefault(current.first, Int.MAX_VALUE) + current.first.manhattanToNode(neighbor)
//                cameFrom[neighbor] = Pair(current, openNodes.toList())
                if (neighbor !in openNodes) {
                    openNodes.add(neighbor)
//                    you need to mark these as explored only if you are not animating
//                    neighbor.explored = true
                } else if (newDistance >= gScore.getOrDefault(neighbor, Int.MAX_VALUE)) {
                    continue
                }
                gScore[neighbor] = newDistance
                val temp = openNodes.toList()

//                println("temp:")
//                temp.forEach { node: Node -> println(node.coordsString()) }
//                println()

                cameFrom[neighbor] = Pair(current.first, temp)

//                println("saved:")
//                cameFrom.getOrDefault(neighbor, Pair(Node(), listOf())).second.forEach { node: Node -> println(node.coordsString()) }
            }
        }
        return path
    }
}