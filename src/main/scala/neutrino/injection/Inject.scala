package neutrino.injection

import scala.quoted._

object inject {
  inline def inject[T] = ${ injectImpl[T]('[T]) }
  def injectImpl[T: Type](typeT: Type[T])(given qctx: QuoteContext) = {
    import qctx.tasty.{_, given}
    
    val tTypeTree = typeT.unseal
    val newExpr = '{ new Object }
    val newInjectedType = newExpr.unseal match {
      // this is the AST for newExpr
      // Inlined(Some(TypeIdent("inject$")), Nil, Apply(Select(New(TypeIdent("Object")), "<init>"), Nil))
      case Inlined(_, _, Apply(sel @ Select(classTerm, methodName), _)) =>
        // create a new New, replacing the class term with the type arg
        val newNew = New.copy(classTerm)(tTypeTree)
        // create new Select, using the New created above
        val select = Select.copy(sel)(newNew, methodName)
        
        val call = None
        val bindings = Nil
        val body = Apply(select, Nil)
        Inlined(call, bindings, body)
    }    
    newInjectedType.seal.cast[T]
  }   
}