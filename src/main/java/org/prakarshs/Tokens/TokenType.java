package org.prakarshs.Tokens;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

//  Lexeme types with matching regex expression
@Getter
@RequiredArgsConstructor
public enum TokenType {

    Comment("\\#.*"),
    LineBreak("[\\n\\r]"),
    Whitespace("[\\s\\t]"),
    Keyword("(agar|yafir|ya|khatam|dekhiye_baapuji|lijiye_baapuji|dhancha|kaam|wapas|ghumaghum|andar|by|todiye|agla|assert|raise|ishtart|rescue|ensure|yeh_lo)(?=\\s|$)(?!_)"),
    GroupDivider("(\\[|\\]|\\,|\\{|}|\\.{2}|(\\:(?!\\:)))"),
    Logical("(sahi_baat_hai|galat_baat_hai)(?=\\s|$)(?!_)"),
    Numeric("([-]?(?=[.]?[0-9])[0-9]*(?![.]{2})[.]?[0-9]*)"),
    Null("(khaali)(?=,|\\s|$)(?!_)"),
    This("(ye)(?=,|\\s|$)(?!_)"),
    Text("\"([^\"]*)\""),
    Operator("(\\+|-|\\*{1,2}|/{1,2}|%|>=?|<=|<{1,2}|={1,2}|!=|!|ka\\s+naya|ka|\\(|\\)|(naya|aur|yatoh|as|is)(?=\\s|$)(?!_))"),
    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}
