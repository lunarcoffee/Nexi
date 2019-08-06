package dev.lunarcoffee.nexi.lexer

import kotlin.reflect.KClass

sealed class Token
typealias TokenType = KClass<out Token>

class TInt(internal val value: Long) : Token()
class TId(internal val name: String) : Token()
class TKeyword(internal val name: String) : Token()

object TSemicolon : Token()
object TColon : Token()
object TLBrace : Token()
object TRBrace : Token()
object TLParen : Token()
object TRParen : Token()
object TEof : Token()
