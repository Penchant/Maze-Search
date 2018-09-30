class BreadthFirstSearch : SearchAlgorithm {

    var frontier: MutableList<Node> = mutableListOf()
    var nodesTraversed: MutableList<Node> = mutableListOf()

    override fun search(startingNode: Node): MutableList<Pair<Node, List<Node>>> {
        addNode(startingNode)

        while (!frontier.isEmpty()) {
            var currentNode = frontier.removeAt(0)

            nodesTraversed.add(currentNode)
            if (currentNode.type == '*') {
                break
            }
            currentNode.onPath = true
            currentNode.neighbors.forEach { neighbor -> addNode(neighbor) }
        }

        var out: MutableList<Pair<Node, List<Node>>> = mutableListOf()
        nodesTraversed.forEach { node: Node -> out.add(Pair(node, listOf())) }
        return out
    }

    fun addNode(node: Node) {
        if (node.type != '%' && !node.onPath) {
            frontier.add(node)
        }
    }
}