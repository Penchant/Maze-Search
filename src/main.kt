import java.awt.image.BufferedImage
import java.awt.image.WritableRaster
import java.awt.Image

fun main(args: Array<String>) {
    println("Hello, world!")
    var image = BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB)

    val wallColor: IntArray = intArrayOf(50, 50, 50)

//    lint message here, will return when more familiar with kotlin
    var rast = image.getRaster()

    for (i in 0 until image.width - 1) {
        for (j in 0 until image.height - 1) {
            print("x")
//            will fill an image with color based on the chars read from the maze file
//            can upsample/enlarge if we need larger output (almost certain)
        }
        println()
    }
}



//public static Image getImageFromArray(int[] pixels, int width, int height) {
//    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
//    WritableRaster raster = (WritableRaster) image.getData()
//    raster.setPixels(0,0,width,height,pixels)
//    return image
//}