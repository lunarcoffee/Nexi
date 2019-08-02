package dev.lunarcoffee.nexi.lexer

internal class LexerException(char: Char, pos: Int) :
    Exception("Unexpected character $char at char $pos!")
