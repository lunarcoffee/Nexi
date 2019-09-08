package dev.lunarcoffee.nexi.parser

import dev.lunarcoffee.nexi.lexer.*
import kotlin.reflect.KClass
import kotlin.reflect.full.cast
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class Parser(private val lexer: Lexer) {
    fun constructAST() = program()

    private fun program(): NProgram {
        val funcs = mutableListOf<Node>()
        var funcDef = funcDef()
        while (funcDef != null) {
            funcs += funcDef
            funcDef = funcDef()
        }
        return NProgram(funcs)
    }

    private fun funcDef(): NFuncDef? {
        val returnType = lexer.next()
        if (returnType is TEof)
            return null
        if (returnType !is TKeyword || returnType.name != "s32")
            throw ParserException(returnType::class, listOf(TKeyword::class))

        val name = nextTested(TId::class)
        nextTested(TColon::class)
        nextTested(TLBrace::class)
        val returnStatement = ret()
        nextTested(TRBrace::class)

        return NFuncDef(name.name, returnStatement)
    }

    private fun ret(): NRet {
        nextTested(TKeyword::class, "return")
        val exp = exp()
        semicolon()

        return NRet(exp)
    }

    private fun exp(): NExp {
        var term: Node = term()
        var next = lexer.peek()

        // Take arbitrarily many addition or subtraction operators and then another term.
        while (next == TPlus || next == TMinus) {
            lexer.next()
            val other = term()

            term = if (next == TPlus) NPlus(term, other) else NMinus(term, other)
            next = lexer.peek()
        }
        return NExp(term)
    }

    // Next level of precedence for high precedence binary operators, including multiplication and
    // division.
    private fun term(): NExp {
        var factor: Node = factor()
        var next = lexer.peek()

        // Take arbitrarily many multiplication or division operators and then another term.
        while (next == TAsterisk || next == TDivide) {
            lexer.next()
            val other = factor()

            factor = if (next == TAsterisk) NMultiply(factor, other) else NDivide(factor, other)
            next = lexer.peek()
        }
        return NExp(factor)
    }

    // Highest precedence tokens for forming a [NExp]: an integer literal, a function call, any
    // unary operators, or a parenthesized expression.
    private fun factor(): NExp {
        return NExp(
            when (val peek = lexer.peek()) {
                is TInt -> s32()
                is TId -> funcCall()
                is TMinus -> unaryMinus()
                is TComplement -> complement()
                is TLParen -> {
                    // Expect an expression surrounded by parentheses.
                    lexer.next()
                    val exp = exp()
                    nextTested(TRParen::class)
                    exp
                }
                else -> throw ParserException(
                    peek::class,
                    listOf(
                        TInt::class,
                        TId::class,
                        TMinus::class,
                        TComplement::class,
                        TLParen::class
                    )
                )
            }
        )
    }

    private fun funcCall(): NFuncCall {
        val name = nextTested(TId::class)
        nextTested(TLParen::class)
        nextTested(TRParen::class)

        return NFuncCall(name.name)
    }

    private fun unaryMinus(): NUnaryMinus {
        nextTested(TMinus::class)
        return NUnaryMinus(exp())
    }

    private fun complement(): NComplement {
        nextTested(TComplement::class)
        return NComplement(exp())
    }

    private fun s32() = NS32(nextTested(TInt::class).value.toInt())
    private fun semicolon() = nextTested(TSemicolon::class)

    // Returns the next token from the [Lexer] only if it is the [expected] token type. There is
    // also an optional [value] to match. If these "tests" fail, an exception is thrown.
    private fun <T : Token> nextTested(expected: KClass<out T>, value: Any? = null): T {
        val token = lexer.next()
        if (token::class == expected) {
            // Just return the token if there is no value to match.
            if (value == null)
                return expected.cast(token)

            val valueProperty = expected.memberProperties.firstOrNull()
            val castedValue = valueProperty?.javaField?.type?.kotlin?.cast(value)
            if (value == castedValue)
                return expected.cast(token)
        }
        throw ParserException(token::class, listOf(expected))
    }
}
