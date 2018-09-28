import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

class Node(val x: Int = 0, val y: Int = 0, val type: Char = '%') {
    var manhattanToGoal = Int.MAX_VALUE
    var euclideanToGoal = Float.MAX_VALUE
    var visited = false
    lateinit var north: Node
    lateinit var east: Node
    lateinit var south: Node
    lateinit var west: Node

    val neighbors get() = mutableListOf(north, east, south, west)
    var deadend = false

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
        val diffX: Float = (this.x - goalX).toFloat()
        val diffY: Float = (this.y - goalY).toFloat()

//        2-D Manhattan distance
        this.manhattanToGoal = (diffX.absoluteValue + diffY.absoluteValue).toInt()

//        2-D Euclidean distance
        this.euclideanToGoal = sqrt(diffX.pow(2) + diffY.pow(2))
    }

    fun assignNeighbors(northNode: Node, eastNode: Node, southNode: Node, westNode: Node) {
        this.north = northNode
        this.east = eastNode
        this.south = southNode
        this.west = westNode
        this.deadend = neighbors.count { neighbor -> neighbor.type != '%'  } <= 1
    }
}