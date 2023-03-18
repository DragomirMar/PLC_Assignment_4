/**
 * @author Lucas Enzi
 * @id 12029628
 */

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigCalc {
    public static void main(String[] args) {
        try {
            //final CharStream input = CharStreams.fromFileName(args[0]);
            //final BigCalcProgLexer lexer = new BigCalcProgLexer(input);
            String test = "x = 0;" +
                    "c = 6;" +
                    "x=c? 1: 12;";
            CharStream charStream = CharStreams.fromString(test);
            final BigCalcProgLexer lexer = new BigCalcProgLexer(charStream);
            final CommonTokenStream tokens = new CommonTokenStream(lexer);
            final BigCalcProgParser parser = new BigCalcProgParser(tokens);
            final ParseTree tree = parser.root();

            final BigCalcProgVisitor<BigDecimal> visitor = new BigCalcProgVisitorImpl();
            final BigDecimal result = visitor.visit(tree);

            if (result != null)
                System.out.println("result: " + result.setScale(10, RoundingMode.HALF_UP));
        }
        catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("usage: usage: java BigCalcProg <file>");
        }
        catch (Exception e) {
            System.out.println(e);
        }

    }
}
