import scala.io.Source
import java.io._

/**
  * Created by starboy on 23.02.16.
  */
object AdformTestTaskApp extends App{
  val rangesfilename = "src/main/res/ranges.tsv"
  val outputfilename = "src/main/res/output.tsv"
  val transactionsfilename = "src/main/res/transactions.tsv"

  val ranges = Source.fromFile(rangesfilename).getLines().map { line =>
    val splitted = line.split("\t")
    val ips = splitted(0)
    val segmentName = splitted(1)
    val ipsSeq = ips.split("-")
    val from = NetworkUtils.ip2Long(ipsSeq(0))
    val to = NetworkUtils.ip2Long(ipsSeq(1))
    (from to to, segmentName)
  }.toList

  val pw = new PrintWriter(new File(outputfilename))
  Source.fromFile(transactionsfilename).getLines().map { line =>
    val splitted = line.split("\t")
    val userId = splitted(0)
    val ip = NetworkUtils.ip2Long(splitted(1))
    ranges.find { case (range, name) => range.contains(ip) }.map { x =>
      pw.write(userId + "\t" + x._2 + "\n")
    }
  }.toList
  pw.close
}
