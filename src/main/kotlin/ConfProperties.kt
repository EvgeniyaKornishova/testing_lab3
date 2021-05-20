import java.io.FileInputStream
import java.io.IOException
import java.util.*

object ConfProperties {
    internal var fileInputStream: FileInputStream? = null
    internal var PROPERTIES: Properties? = null
    fun getProperty(key: String?): String {
        return PROPERTIES!!.getProperty(key)
    }

    init {
        try {
            fileInputStream = FileInputStream("src/test/resources/conf.properties")
            PROPERTIES = Properties()
            PROPERTIES!!.load(fileInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fileInputStream != null) try {
                fileInputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
