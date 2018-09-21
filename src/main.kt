import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

//import java.awt.Image

fun main(args: Array<String>) {
    renderMaze("res/xl_maze.txt")
}

fun renderMaze(fileName: String) {
    var i = 0; var j = 0
    var image = BufferedImage(101, 101, BufferedImage.TYPE_INT_RGB)
    File(fileName).forEachLine {
        for (c in it) {
            if (c == '%') {
                image.setRGB(i, j, Color(50, 50, 50).rgb)
            }
            TODO(CASES FOR START AND END POSITION)
            j++
        }
        j = 0
        i++
    }
    var out : File = File("output.png")
    ImageIO. write(image,  "png", out)
}



//public static Image getImageFromArray(int[] pixels, int width, int height) {
//    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
//    WritableRaster raster = (WritableRaster) image.getData()
//    raster.setPixels(0,0,width,height,pixels)
//    return image
//}