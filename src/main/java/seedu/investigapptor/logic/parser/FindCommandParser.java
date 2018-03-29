package seedu.investigapptor.logic.parser;

import static seedu.investigapptor.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.investigapptor.logic.parser.ParserKeywords.KEYWORD_INVEST;
import static seedu.investigapptor.logic.parser.ParserKeywords.KEYWORD_CASE;
import static seedu.investigapptor.logic.parser.ParserKeywords.KEYWORD_TAGS;

import java.util.Arrays;

import seedu.investigapptor.logic.commands.Command;
import seedu.investigapptor.logic.commands.FindCommand;
import seedu.investigapptor.logic.parser.exceptions.ParseException;
import seedu.investigapptor.model.person.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        String type = nameKeywords[0];

        switch(type) {
            case KEYWORD_INVEST:
                return new FindInvestTagsCommandParser().parse(args);
            default:
                return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }

    }

}
