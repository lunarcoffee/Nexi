package dev.lunarcoffee.nexi.lexer

import kotlin.reflect.KClass

sealed class Token
typealias TokenType = KClass<out Token>

class TInt(val value: Long) : Token()
class TId(val name: String) : Token()
class TKeyword(val name: String) : Token()

object TSemicolon : Token()
object TColon : Token()
object TLBrace : Token()
object TRBrace : Token()
object TLParen : Token()
object TRParen : Token()
object TEof : Token()

object TPlus : Token()
object TMinus : Token()
object TAsterisk : Token()
object TDivide : Token()
object TComplement : Token()
