package dev.lunarcoffee.nexi.lexer

import java.util.*

class Lexer(private val code: String) {
    private var pos = 0
    private var curChar = code[pos]

    private val returned = Stack<Token>()
    private val keywords = setOf("s32", "return")

    fun next(): Token {
        if (returned.isNotEmpty())
            return returned.pop()

        while (curChar != '\u0000') {
            ignoreWhitespace()

            // Try to consume an integral token.
            if (curChar.isDigit())
                return TInt(consumeWhile("""\d""").toLong())

            // Try to consume a keyword, or just an identifier if not a keyword.
            if (curChar.isLetter() || curChar == '_') {
                val id = consumeWhile("""\w""")
                return if (id in keywords) TKeyword(id) else TId(id)
            }

            return when (curChar) {
                ':' -> TColon
                ';' -> TSemicolon
                '+' -> TPlus
                '-' -> TMinus
                '*' -> TAsterisk
                '/' -> TDivide
                '%' -> TModulo
                '~' -> TComplement
                '{' -> TLBrace
                '}' -> TRBrace
                '(' -> TLParen
                ')' -> TRParen
                else -> throw LexerException(curChar, pos)
            }.also { advance() }
        }
        return TEof
    }

    fun peek() = next().also { returned += it }

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
