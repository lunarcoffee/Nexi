package parser

internal sealed class Node(internal val children: List<Node> = emptyList())

internal class NS32(internal val value: Int) : Node()
internal class NFuncCall(internal val name: String) : Node()
internal class NExp(vararg children: Node) : Node(children.toList())
internal class NRet(exp: NExp) : Node(listOf(exp))
internal class NFuncDef(internal val name: String, body: NRet) : Node(listOf(body))
internal class NProgram(children: List<Node>) : Node(children)
