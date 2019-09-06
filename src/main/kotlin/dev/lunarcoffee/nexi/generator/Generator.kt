package dev.lunarcoffee.nexi.generator

import dev.lunarcoffee.nexi.parser.*
import java.io.File

class Generator(parser: Parser, private val output: File) {
    private val ast = parser.constructAST()

    fun generate() = output.writeText(traverse(ast))

    private fun traverse(node: Node): String {
        return when (node) {
            is NS32 -> "mov eax, ${node.value}"
            is NFuncCall -> "call ${node.name}"
            is NUnaryMinus -> "${traverse(node.exp)}\nneg eax"
            is NComplement -> "${traverse(node.exp)}\nnot eax"
            is NExp -> traverse(node.children[0])
            is NRet -> traverse(node.children[0]) + "\nret"
            is NFuncDef -> "global ${node.name}\n${node.name}:\n${traverse(node.children[0])}"
            is NProgram -> node.children.joinToString("\n") { traverse(it) }
        }
    }
}
