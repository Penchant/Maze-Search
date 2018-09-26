import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val startTime = System.currentTimeMillis()
//    mazeInstance is a pair with first as the dense tree of nodes
//    and second as the i,j coords of the starting node
//    this pair of pairs should probably be a class...
    var mazeInstance = importMaze("res/titan_maze.txt")
    renderMaze(mazeInstance.first)

//    for testing manhattanDistance
    for (line in mazeInstance.first) {
        for (c in line) {
            if (c.type == 'P' || c.type == '*') {
                print("\t${c.type}${c.type}${c.type}${c.type}")
            } else {
                print("\t%.2f".format(c.euclideanToGoal))
            }
        }
        println()
    }
    println("Import, render, and print finished (${System.currentTimeMillis() - startTime}ms)")
    println("Starting coords: ${mazeInstance.first[mazeInstance.second.first][mazeInstance.second.second].coordsString()}")
}

fun importMaze(fileName: String): Pair<List<List<Node>>, Pair<Int, Int>> {
//    Currently, this just creates a nested List object with one Node for every pixel/tile/place
//    could prune later into points of interest if time/interest
    val input = File(fileName).readLines()
    var maze: MutableList<MutableList<Node>> = mutableListOf()
    var goalI = Int.MAX_VALUE
    var goalJ = Int.MAX_VALUE
    var startI = Int.MAX_VALUE
    var startJ = Int.MAX_VALUE

    for (i in 0 until input.size) {
        maze.add(mutableListOf())
        for (j in 0 until input[i].length) {
            maze[i].add(Node(i, j, input[i][j]))
            if (input[i][j] == 'P') {
//                start position
                startI = i
                startJ = j
            } else if (input[i][j] == '*') {
//                goal position
                goalI = i
                goalJ = j
            }
        }
    }

//    assign neighbors and calculate manhattan distance to goal
//    could be made more efficient, but should only run once per series of alg tests
    for (i in 0 until maze.size) {
        for (j in 0 until maze[i].size) {
            if (i == 0 || i == maze.size - 1 || j == 0 || j == maze[i].size - 1) {
                maze[i][j].assignNeighbors(
                        Node(), // north
                        Node(), // east
                        Node(), // south
                        Node() // west
                )
            } else {
                maze[i][j].assignNeighbors(
                        maze[i - 1][j], // north
                        maze[i][j + 1], // east
                        maze[i + 1][j], // south
                        maze[i][j - 1] // west
                )
            }
            maze[i][j].calcDistance(goalI, goalJ)
        }
    }

//    convert to immutable
//    also seems inefficient... again only runs once
    var rows = mutableListOf<List<Node>>()
    for (currentRow in 0 until maze.size) {
        rows.add(maze[currentRow].toList())
    }
    return Pair(rows.toList(), Pair(startI, startJ))
}

fun renderMaze(input: List<List<Node>>) {
    val wallColor = Color(50, 50, 50).rgb
    val pathColor = Color(100, 100, 100).rgb
    val pacColor = Color(255, 255, 0).rgb
    val foodColor = Color(175, 238, 238).rgb

//    assuming rectangular with top row widest
    var image = BufferedImage(input[1].size, input.size, BufferedImage.TYPE_INT_RGB)
    for (i in 0 until input.size) {
        for (j in 0 until input[i].size) {
            when (input[i][j].type) {
                '%' -> image.setRGB(j, i, wallColor)
                ' ' -> image.setRGB(j, i, pathColor)
                'P' -> image.setRGB(j, i, pacColor)
                '*' -> image.setRGB(j, i, foodColor)
            }
        }
    }
    var out = File("res/maze_images/output.png")
    ImageIO.write(image, "png", out)
}