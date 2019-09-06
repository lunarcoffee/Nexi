package dev.lunarcoffee.nexi.parser

sealed class Node(val children: List<Node> = emptyList())

class NS32(val value: Int) : Node()
class NFuncCall(val name: String) : Node()
class NExp(vararg children: Node) : Node(children.toList())
class NRet(exp: NExp) : Node(listOf(exp))
class NFuncDef(val name: String, body: NRet) : Node(listOf(body))
class NProgram(children: List<Node>) : Node(children)

class NUnaryMinus(val exp: NExp) : Node()
class NComplement(val exp: NExp) : Node()
