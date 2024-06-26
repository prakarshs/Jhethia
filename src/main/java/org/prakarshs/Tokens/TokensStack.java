package org.prakarshs.Tokens;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.prakarshs.Constants.ErrorConstants;
import org.prakarshs.Exceptions.SyntaxException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TokensStack {
	private final List<Token> tokens;
	private int position;

	private static final List<TokenType> EMPTY_TOKENS = List.of(TokenType.LineBreak, TokenType.Comment);

	public Token next(TokenType type, TokenType... types) {
		skipEmptyTokens();
		TokenType[] tokenTypes = ArrayUtils.add(types, type);
		if (position < tokens.size()) {
			Token token = tokens.get(position);
			if (Stream.of(tokenTypes).anyMatch(t -> t == token.getType())) {
				position++;
				return token;
			}
		}

		String problem = ErrorConstants.SYNTAX_GALAT_HAI;
		String solution = String.format(String.format("After `%s` declaration expected any of the following lexemes `%s`", previous(), Arrays.toString(tokenTypes)));
		System.out.println("Poblem : "+problem);
		System.out.println("Solution : "+solution);
		throw new SyntaxException(problem,solution);
	}

	public void back() {
		position--;
	}

	public boolean hasNext() {
		skipEmptyTokens();
		return position < tokens.size();
	}

	public Token next(TokenType type, String value, String... values) {
		skipEmptyTokens();
		if (position < tokens.size()) {
			String[] allValues = ArrayUtils.add(values, value);
			Token token = tokens.get(position);
			if (token.getType() == type && Arrays.stream(allValues).anyMatch(t -> Objects.equals(t, token.getValue()))) {
				position++;
				return token;
			}
		}
		String problem = ErrorConstants.SYNTAX_GALAT_HAI;
		String solution = String.format(String.format("After `%s` declaration expected `%s, %s` lexeme", previous(), type, value));
		System.out.println("Poblem : "+problem);
		System.out.println("Solution : "+solution);
		throw new SyntaxException(problem,solution);
	}

	public Token next() {
		skipEmptyTokens();
		return tokens.get(position++);
	}

	public boolean peek(TokenType type, String value, String... values) {
		skipEmptyTokens();
		return peekSameLine(type, value, values);
	}

	public boolean peekSameLine(TokenType type, String value, String... values) {
		if (position < tokens.size()) {
			String[] allValues = ArrayUtils.add(values, value);
			Token token = tokens.get(position);
			return type == token.getType() && Arrays.stream(allValues).anyMatch(t -> Objects.equals(t, token.getValue()));
		}
		return false;
	}

	public boolean peek(TokenType type, TokenType... types) {
		skipEmptyTokens();
		return peekSameLine(type, types);
	}

	public boolean peekSameLine(TokenType type, TokenType... types) {
		if (position < tokens.size()) {
			TokenType[] tokenTypes = ArrayUtils.add(types, type);
			Token token = tokens.get(position);
			return Stream.of(tokenTypes).anyMatch(t -> t == token.getType());
		}
		return false;
	}

	private Token previous() {
		return tokens.get(position - 1);
	}

	private void skipEmptyTokens() {
		while (position != tokens.size() && EMPTY_TOKENS.contains(tokens.get(position).getType()))
			position++;
	}

}
