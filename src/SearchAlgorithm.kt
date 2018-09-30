interface SearchAlgorithm {
    fun search(startingNode : Node): MutableList<Pair<Node, List<Node>>>
}