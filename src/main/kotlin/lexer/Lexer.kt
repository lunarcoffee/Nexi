package lexer

import java.util.*

internal class Lexer(private val code: String) {
    private var pos = 0
    private var curChar = code[pos]

    private val returned = Stack<Token>()
    private val keywords = setOf("s32", "return")

    internal fun next(): Token {
        if (returned.isNotEmpty())
            return returned.pop()

        while (curChar != '\u0000') {
            ignoreWhitespace()

            if (curChar.isDigit())
                return TInt(consumeWhile("""\d""").toLong())

            if (curChar.isLetter() || curChar == '_') {
                val id = consumeWhile("""\w""")
                return if (id in keywords) TKeyword(id) else TId(id)
            }

            return when (curChar) {
                ':' -> TColon
                ';' -> TSemicolon
                '{' -> TLBrace
                '}' -> TRBrace
                '(' -> TLParen
                ')' -> TRParen
                else -> throw LexerException(curChar, pos)
            }.also { advance() }
        }
        return TEof
    }

    internal fun peek() = next().also { returned += it }

    private fun consumeWhile(regexString: String): String {
        var res = ""
        val regex = regexString.toRegex()
        while (regex matches curChar.toString()) {
            res += curChar
            advance()
        }
        return res
    }

    private fun ignoreWhitespace() {
        while (curChar.isWhitespace())
            advance()
    }

    private fun advance() {
        pos++
        curChar = code.getOrElse(pos) { '\u0000' }
    }
}
