package dev.lunarcoffee.nexi

import dev.lunarcoffee.nexi.generator.Generator
import dev.lunarcoffee.nexi.lexer.Lexer
import dev.lunarcoffee.nexi.lexer.TEof
import dev.lunarcoffee.nexi.parser.Parser
import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: nexic <file>")
        return
    }

    val code = File(args[0]).readText().trim()

    val lexer = Lexer(code)
    while (lexer.peek() !is TEof)
        println(lexer.next()::class.simpleName)

    val gen = Generator(Parser(Lexer(code)), File("src/test/resources/test0.asm"))
    gen.generate()
}
