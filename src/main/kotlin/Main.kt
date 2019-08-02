import dev.lunarcoffee.nexi.lexer.Lexer
import dev.lunarcoffee.nexi.lexer.TEof
import dev.lunarcoffee.nexi.parser.Parser
import java.io.File

internal fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: nexic <file>")
        return
    }

    val code = File(args[0]).readText().trim()

    // TODO: Turn these into proper unit tests.
    val lexer = Lexer(code)
    while (lexer.peek() !is TEof)
        println(lexer.next()::class.simpleName)

    val parser = Parser(Lexer(code))
    parser.constructAST()
}
