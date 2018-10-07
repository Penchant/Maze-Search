class GreedyBestFirstSearch : SearchAlgorithm {

    override fun search(startingNode: Node) {

        startingNode.visited = true
        var nodeStack: MutableList<MutableList<Node>> = mutableListOf(mutableListOf(startingNode))
        nodeStack.add(getSortedNeighbors(startingNode))
        var count = 0
        while (nodeStack.isNotEmpty()) {
//            if no more neighbors at this level, pop it from the stack
            if (nodeStack.last().isEmpty()) {
                nodeStack.removeAt(nodeStack.lastIndex)
                continue
            }

//            if we found the goal
            if (nodeStack.last().last().type == '*') {
                println("you win")
//                resolve path below
                break
            }

//            maybe convert to a sequence if this works?
            val sortedNeighbors = getSortedNeighbors(nodeStack.last().last())
            if (sortedNeighbors.size > 0) {
                nodeStack.add(sortedNeighbors)
            } else {
                nodeStack.last().removeAt(nodeStack.last().lastIndex)
            }

        }
        var pathLength = 0
        nodeStack.forEach { layer: MutableList<Node> -> layer.last().onPath = true; pathLength++ }
        println("Path Length: $pathLength")
    }

    private fun getSortedNeighbors(input: Node): MutableList<Node> {
//        only adds to output if not a wall and not already visited
        var output = input.neighbors.filter { node -> node.type != '%' && !node.visited}.toMutableList()

        output.sortBy { node -> node.manhattanToGoal }
//        mark as visited
        output.forEach { node: Node -> node.visited = true }

//        reverse so the best nodes is the last
        return output.asReversed()
    }
}