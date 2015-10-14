package dundertext.server

trait AppContext {
  def documentsActor: DocumentsActor.Ref
}
