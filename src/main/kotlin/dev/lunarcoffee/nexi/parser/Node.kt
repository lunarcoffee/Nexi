package dev.lunarcoffee.nexi.parser

sealed class Node(val children: List<Node> = emptyList())

class NS32(val value: Int) : Node()
class NVariableReference(val name: String) : Node()
class NFuncCall(val name: String) : Node()
class NAssignment(val name: String, val value: NExp) : Node()
class NExp(vararg children: Node) : Node(children.toList())
class NRet(exp: NExp) : Node(listOf(exp))
class NFuncDef(val name: String, body: List<Node>) : Node(body)
class NProgram(children: List<Node>) : Node(children)

class NPlus(val left: Node, val right: Node) : Node()
class NMinus(val left: Node, val right: Node) : Node()
class NMultiply(val left: Node, val right: Node) : Node()
class NDivide(val left: Node, val right: Node) : Node()
class NModulo(val left: Node, val right: Node) : Node()
class NUnaryMinus(val exp: NExp) : Node()
class NComplement(val exp: NExp) : Node()
