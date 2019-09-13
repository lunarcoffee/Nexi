package dev.lunarcoffee.nexi.generator

import dev.lunarcoffee.nexi.parser.*
import java.io.File

class Generator(parser: Parser, private val output: File) {
    private val ast = parser.constructAST()

    fun generate() {
        // Remove blank lines and surrounding whitespace.
        val assembly = traverse(ast)
            .lines()
            .filter { it.isNotBlank() }
            .joinToString("\n") { it.trim() }

        output.writeText(stripComments(assembly))
    }

    // This allows for commenting the generated assembly in multiline string literals where
    // commenting the code is not possible, like in the [NDivide] branch in [traverse].
    private fun stripComments(assembly: String): String {
        return assembly.lines().joinToString("\n") { line -> line.takeWhile { it != ';' } }
    }

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
            is NPlus -> """
                ${traverse(node.left)}
                push eax
                ${traverse(node.right)}
                pop edx
                add eax, edx 
            """
            is NMinus -> """
                ${traverse(node.left)}
                push eax
                ${traverse(node.right)}
                pop edx
                sub edx, eax
                mov eax, edx
            """
            is NMultiply -> """
                ${traverse(node.left)}
                push eax
                ${traverse(node.right)}
                pop edx
                imul eax, edx
            """
            is NDivide -> """
                ${traverse(node.left)}
                push eax
                ${traverse(node.right)}
                mov ecx, eax             ; Use ecx to hold the divisor.
                pop eax                  ; Prepare the dividend.
                xor edx, edx
                div ecx
            """
            is NModulo -> """
                ${traverse(node.left)}
                push eax
                ${traverse(node.right)}
                mov ecx, eax             ; Use ecx to hold the divisor.
                pop eax                  ; Prepare the dividend.
                xor edx, edx
                div ecx
                mov eax, edx             ; Return the remainder.
            """
            is NAssignment -> ""
            is NVariableReference -> ""
        }
    }
}
