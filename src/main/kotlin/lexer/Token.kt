package lexer

import kotlin.reflect.KClass

internal sealed class Token
internal typealias TokenType = KClass<out Token>

internal class TInt(internal val value: Long) : Token()
internal class TId(internal val name: String) : Token()
internal class TKeyword(internal val name: String) : Token()

internal object TSemicolon : Token()
internal object TColon : Token()
internal object TLBrace : Token()
internal object TRBrace : Token()
internal object TLParen : Token()
internal object TRParen : Token()
internal object TEof : Token()
