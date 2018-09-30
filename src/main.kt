import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun main(args: Array<String>) {
    val startTime = System.currentTimeMillis()
//    mazeInstance is a pair with first as the dense tree of nodes
//    and second as the i,j coords of the starting node
//    this pair of pairs should probably be a class...
    var mazeInstance = importMaze("res/open_maze.txt")

//    var searchAlgo: SearchAlgorithm = GreedyBestFirstSearch()
    var searchAlgo: SearchAlgorithm = AStarSearch()

    val path = searchAlgo.search(mazeInstance.first[mazeInstance.second.first][mazeInstance.second.second])
//    assuming rectangular with top row widest again
    println("%.2f percent of nodes used by final  path".format(path.size.toDouble() / (mazeInstance.first.size.toDouble() * mazeInstance.first[0].size.toDouble())))

//    renderMaze(mazeInstance.first)
    animatePath(mazeInstance.first, path, "res/test/test")

    println("solve and render finished (${System.currentTimeMillis() - startTime}ms)")
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
//                println("goal at ($i, $j)")
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
            maze[i][j].calcDistance(goalI, goalJ, startI, startJ)
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

fun renderMaze(map: List<List<Node>>,
               outputFilename: String = "res/maze_images/output/defaultOutput.png",
               wallColor: Int = Color(50, 50, 50).rgb,
               floorColor: Int = Color(125, 125, 125).rgb,
               pacColor: Int = Color(255, 255, 0).rgb,
               foodColor: Int = Color(175, 238, 238).rgb,
               pathColor: Int = Color(30, 30, 150).rgb,
               exploredColor: Int = Color(80, 80, 80).rgb) {
//    assuming rectangular with top row widest
    var image = BufferedImage(map[1].size, map.size, BufferedImage.TYPE_INT_RGB)
    for (i in 0 until map.size) {
        for (j in 0 until map[i].size) {
            var temp = map[i][j]
            when (temp.type) {
                '%' -> image.setRGB(j, i, wallColor)
                'P' -> image.setRGB(j, i, pacColor)
                '*' -> image.setRGB(j, i, foodColor)
                ' ' -> {
                    if (temp.onPath) {
                        image.setRGB(j, i, pathColor)
                    } else if (temp.explored) {
                        image.setRGB(j, i, exploredColor)
                    } else {
                        image.setRGB(j, i, floorColor)
                    }
                }
            }
        }
        var out = File(outputFilename)
        ImageIO.write(image, "png", out)
    }
}

fun animatePath(map: List<List<Node>>, pathNodes: MutableList<Pair<Node, List<Node>>>, outputFilename: String = "res/maze_images/output/defaultOutput.png") {
//    pathNodes key is a node on the path with value as a list of explored nodes at that step
    for (step in pathNodes) {
        step.first.onPath = true
        step.second.forEach { node: Node -> node.explored = true }
        renderMaze(map, "$outputFilename-${pathNodes.indexOf(step)}.png")
        step.second.forEach { node: Node -> node.explored = false }

    }
}