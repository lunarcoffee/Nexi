package dev.lunarcoffee.nexi.parser

import dev.lunarcoffee.nexi.lexer.TokenType

internal class ParserException(actual: TokenType, expected: List<TokenType> = emptyList()) :
    Exception("Expected one of ${expected.map { it.simpleName }}, got ${actual.simpleName}!")
