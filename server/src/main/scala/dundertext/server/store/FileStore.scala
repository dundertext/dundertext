package dundertext.server.store

import java.nio.charset.StandardCharsets.UTF_8
import java.nio.file.{StandardOpenOption, Files, Paths}

import dundertext.data.Document
import dundertext.editor.DocumentPatch
import dundertext.format.SrtFormat
import dundertext.server.store.FileStore._

class FileStore {
  Files.createDirectories(base)

  def put(doc: Document): Unit = {
    val s = SrtFormat.writeString(doc)
    val f = base.resolve(doc.id + ".srt")
    Files.write(f, s.getBytes(UTF_8))
  }

  def log(docId: String, patches: Seq[DocumentPatch]): Unit = {
    val f = base.resolve(docId + ".log")
    val s = patches.map(_.serialize + "\n").mkString
    Files.write(f, s.getBytes(UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND)
  }
}

object FileStore {
  val base = Paths.get("work", "documents")
}
