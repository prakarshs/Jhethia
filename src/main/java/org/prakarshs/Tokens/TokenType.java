package org.prakarshs.Tokens;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//  Lexeme types with matching regex expression
@Getter
@RequiredArgsConstructor
public enum TokenType {

    Comment("\\#.*"),
    Whitespace("[\\s\\t\\n\\r]"),
    Keyword("(agar|toh|ya|khatam|dekhiye_baapuji|lijiye_baapuji|dhancha|yeh_lo|kaam|wapas|ghumaghum|andar|by|todiye|agla|assert|raise|ishtart|rescue|ensure)(?=\\s|$)(?!_)"),
    GroupDivider("(\\[|\\]|\\,|\\{|}|\\.{2}|(\\:(?!\\:)))"),
    Logical("sahi_baat_hai|galat_baat_hai"),
    Numeric("([-]?(?=[.]?[0-9])[0-9]*(?![.]{2})[.]?[0-9]*)"),
    Null("(khaali)(?=,|\\s|$)(?!_)"),
    This("(ye)(?=,|\\s|$)(?!_)"),
    Text("\"([^\"]*)\""),
    Operator("(\\+|\\-|\\*|\\/|\\>|\\<|\\={1,2}|\\!|ka|naya)"),
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}
