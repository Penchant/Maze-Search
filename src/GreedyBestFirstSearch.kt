class GreedyBestFirstSearch : SearchAlgorithm {

    override fun search(startingNode: Node): MutableList<Pair<Node, List<Node>>> {

        var current = startingNode
        var openNodes: MutableList<Node> = mutableListOf()
        var cameFrom: HashMap<Node, Node> = hashMapOf()
        var path: MutableList<Node> = mutableListOf()

        openNodes.add(current)
        while (openNodes.isNotEmpty()) {
            current = openNodes.first()
            if (openNodes.size % 1000 == 0) println("opened ${openNodes.size} nodes")

            if (current.type == '*') {
                println("you won")
                while (cameFrom[current] != startingNode) {
//                    this might be drawing the wrong path?
//                    cameFrom is assigned in order discovered?
                    cameFrom.getOrDefault(current, Node()).onPath = true
                    current = cameFrom.getOrDefault(current, Node())
                    path.add(current)
                    if (current == Node()) {
                        println("the path is broken")
                        break
                    }
                }
                break
            }

            var neighbors = current.neighbors
            neighbors.sortBy { node -> node.manhattanToGoal }
            neighbors.removeAll { node -> node.type == '%' || node in openNodes }
            if (neighbors.isEmpty()) {
                openNodes.remove(current)
                continue
            }

            for (node in neighbors) {
                node.explored = true
                if (node !in cameFrom.keys) cameFrom[node] = current
                openNodes.add(node)
            }
//            adds neighbors sorted by distance to openNodes
        }
        println("path length: ${path.size}")
        var out: MutableList<Pair<Node, List<Node>>> = mutableListOf()
        path.forEach { node: Node -> out.add(Pair(node, listOf())) }
        return out
    }
}