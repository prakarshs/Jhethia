package org.prakarshs.Tokens;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prakarshs.Parser.LexicalParser;

@RequiredArgsConstructor
@Getter
public enum TokenType {

    Comment("\\#.*"),

    LineBreak("[\\n\\r]"),

    Whitespace("[\\s\\t]"),

    Keyword("(agar|yatoh|warna|khatam|dekhiye_baapuji|lijiye_baapuji|kilass|kaam|bhejo|chai_piyo|biscuit_khao|in|by|break|agla|assert|raise|ishtart|rescue|ensure)(?=\\s|$)(?!_)"),

    GroupDivider("(\\[|\\]|\\,|\\{|}|\\.{2}|(\\:(?!\\:)))"),

    Logical("(sahi_baat_hai|galat_baat_hai)(?=\\s|$)(?!_)"),

    Numeric("([-]?(?=[.]?[0-9])[0-9]*(?![.]{2})[.]?[0-9]*)"),

    Null("(null)(?=,|\\s|$)(?!_)"),

    This("(this)(?=,|\\s|$)(?!_)"),

    Text("\"([^\"]*)\""),

    Operator("(\\+|-|\\*{1,2}|/{1,2}|%|>=?|<=|<{1,2}|={1,2}|!=|!|ka\\s+naya|ka|\\(|\\)|(naya|aur|ya|as|is)(?=\\s|$)(?!_))"),

    Variable("[a-zA-Z_]+[a-zA-Z0-9_]*");

    private final String regex;
}

