package org.prakarshs.Context.definition;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.prakarshs.Parser.StatementParser;

import java.util.List;

/**
 * Details of a class
 * <p>
 *
 * @see ClassDefinition
 * @see StatementParser#readClassDetails()
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClassDetails {
    /**
     * Class's name
     */
    @EqualsAndHashCode.Include
    private final String name;
    /**
     * Names of the constructor properties
     */
    private final List<String> properties;
}
