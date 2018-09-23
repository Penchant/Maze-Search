import kotlin.math.absoluteValue

class Node(val x: Int = 0, val y: Int = 0, val type: Char = '%') {
    var manhattanToGoal = Int.MAX_VALUE
    lateinit var north: Node
    lateinit var east: Node
    lateinit var south: Node
    lateinit var west: Node

    fun coordsString(): String {
        return "(${this.x}, ${this.y})"
    }

    fun printConnections() {
        println("node at ($x, $y) with the following neighbors:")
        println("\tnorth: (${this.north.x}, ${this.north.y}) of type: ${this.north.type}")
        println("\teast: (${this.east.x}, ${this.east.y}) of type: ${this.east.type}")
        println("\tsouth: (${this.south.x}, ${this.south.y}) of type: ${this.south.type}")
        println("\twest: (${this.west.x}, ${this.west.y}) of type: ${this.west.type}")
    }

    fun calcDistance(goalX: Int, goalY: Int) {
//        2-D Manhattan distance
        val diffX = (this.x - goalX).absoluteValue
        val diffY = (this.y - goalY).absoluteValue
        this.manhattanToGoal = diffX + diffY
    }

    fun assignNeighbors(northNode: Node, eastNode: Node, southNode: Node, westNode: Node) {
        this.north = northNode
        this.east = eastNode
        this.south = southNode
        this.west = westNode
    }
}