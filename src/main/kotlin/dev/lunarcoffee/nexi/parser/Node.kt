package dev.lunarcoffee.nexi.parser

sealed class Node(internal val children: List<Node> = emptyList())

class NS32(internal val value: Int) : Node()
class NFuncCall(internal val name: String) : Node()
class NExp(vararg children: Node) : Node(children.toList())
class NRet(exp: NExp) : Node(listOf(exp))
class NFuncDef(internal val name: String, body: NRet) : Node(listOf(body))
class NProgram(children: List<Node>) : Node(children)
