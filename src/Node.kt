import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

class Node(val x: Int = 0, val y: Int = 0, val type: Char = '%') {
    var manhattanToGoal = Int.MAX_VALUE
    var euclideanToGoal = Float.MAX_VALUE
    var manhattanToStart = Int.MAX_VALUE
    var euclideanToStart = Float.MAX_VALUE

    //    these could be condensed into just two variables probably
    var visited = false
    var exploredNotUsed = false
    var onPath = false
    var pastNode = this

    lateinit var north: Node
    lateinit var east: Node
    lateinit var south: Node
    lateinit var west: Node

    val neighbors get() = mutableListOf(north, east, south, west)
    var deadEnd = false

    fun coordsString(): String {
//        plus 1 is for using the coords in the bottom right and testing with a maze file
        return "(${this.x + 1}, ${this.y + 1})"
    }

    fun printConnections() {
        println("node at ($x, $y) with the following neighbors:")
        println("\tnorth: (${this.north.x}, ${this.north.y}) of type: ${this.north.type}")
        println("\teast: (${this.east.x}, ${this.east.y}) of type: ${this.east.type}")
        println("\tsouth: (${this.south.x}, ${this.south.y}) of type: ${this.south.type}")
        println("\twest: (${this.west.x}, ${this.west.y}) of type: ${this.west.type}")
    }

    fun calcDistance(goalX: Int, goalY: Int, startX: Int, startY: Int) {
        val goalDiffX: Float = (this.x - goalX).toFloat()
        val goalDiffY: Float = (this.y - goalY).toFloat()
        val startDiffX: Float = (this.x - startX).toFloat()
        val startDiffY: Float = (this.y - startY).toFloat()

//        2-D Manhattan distance
        this.manhattanToGoal = (goalDiffX.absoluteValue + goalDiffY.absoluteValue).toInt()
        this.manhattanToStart = (startDiffX.absoluteValue + startDiffY.absoluteValue).toInt()

//        2-D Euclidean distance
        this.euclideanToGoal = sqrt(goalDiffX.pow(2) + goalDiffY.pow(2))
        this.euclideanToStart = sqrt(startDiffX.pow(2) + startDiffY.pow(2))
    }

    fun manhattanToNode(input: Node): Int {
        val diffX: Int = (this.x - input.x)
        val diffY: Int = (this.y - input.y)
        return diffX.absoluteValue + diffY.absoluteValue
    }

    fun assignNeighbors(northNode: Node, eastNode: Node, southNode: Node, westNode: Node) {
        this.north = northNode
        this.east = eastNode
        this.south = southNode
        this.west = westNode
        this.deadEnd = neighbors.count { neighbor -> neighbor.type != '%' } <= 1
    }
}