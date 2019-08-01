package parser

import lexer.TokenType

internal class ParserException(actual: TokenType, expected: List<TokenType> = emptyList()) :
    Exception("Expected one of ${expected.map { it.simpleName }}, got ${actual.simpleName}!")
