/**
  * Created by starboy on 22.02.16.
  */
object NetworkUtils {
  def ip2Long(ip: String): Long = {
    val atoms: Array[Long] = ip.split("\\.").map(java.lang.Long.parseLong(_))
    val result: Long = (3 to 0 by -1).foldLeft(0L)(
      (result, position) => result | (atoms(3 - position) << position * 8))

    result & 0xFFFFFFFF
  }
}
