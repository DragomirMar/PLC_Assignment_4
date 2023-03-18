import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.TreeMap;

public class BigCalcProgVisitorImpl extends BigCalcProgBaseVisitor<BigDecimal> {
    private Map<String, BigDecimal> variables = new TreeMap<>();



    @Override
    public BigDecimal visitRoot(BigCalcProgParser.RootContext ctx) {
        BigDecimal returnValue = BigDecimal.ZERO;

        for(BigCalcProgParser.ExpressionStatementContext ExpressionStatement : ctx.expressionStatement()){
            returnValue = visit(ExpressionStatement);
        }
        return returnValue;
    }

    @Override
    public BigDecimal visitExpState(BigCalcProgParser.ExpStateContext ctx){
        return visit(ctx.expression());
    }

    @Override
    public BigDecimal visitAssState(BigCalcProgParser.AssStateContext ctx){
        return visit(ctx.assign());
    }

    @Override
    public BigDecimal visitMulDiv(BigCalcProgParser.MulDivContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("*")) {
            return left.multiply(right);
        } else {
            return left.divide(right, 10, RoundingMode.HALF_UP);
        }
    }

    @Override
    public BigDecimal visitAddSub(BigCalcProgParser.AddSubContext ctx) {
        final BigDecimal left = visit(ctx.expression(0));
        final BigDecimal right = visit(ctx.expression(1));
        if (ctx.op.getText().equals("+")) {
            return left.add(right);
        } else {
            return left.subtract(right);
        }
    }

    public BigDecimal visitAssign(BigCalcProgParser.AssignContext ctx){
        BigDecimal resultValue = visit(ctx.expression());
        variables.put(ctx.Identifier().getText(), resultValue);
        return resultValue;
    }

    @Override
    public BigDecimal visitNum(BigCalcProgParser.NumContext ctx) {
        return new BigDecimal(ctx.Number().getText());
    }

    @Override
    public BigDecimal visitVar(BigCalcProgParser.VarContext ctx) {
        if(!variables.containsKey(ctx.Identifier().getText())){
            System.out.println("Warning: undefined: " + ctx.Identifier());
        }
        return variables.getOrDefault(ctx.Identifier().getText(), BigDecimal.ZERO);
    }

    @Override
    public BigDecimal visitExample1(BigCalcProgParser.Example1Context ctx){
        final BigDecimal exp1 = visit(ctx.expression(0));
        final BigDecimal exp2 = visit(ctx.expression(1));
        final BigDecimal exp3 = visit(ctx.expression(2));


        if(exp1.doubleValue() != 0.0){
            return exp2;
        }
        else{
            return exp3;
        }
    }
    @Override
    public Boolean visitEqState(BigCalcProgParser.EqStateContext ctx){
        final BigDecimal exp1 = visit(ctx.expression(0));
        final BigDecimal exp2 = visit(ctx.expression(1));
        if(exp1 == exp2){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public BigDecimal visitPara(BigCalcProgParser.ParaContext ctx){
        return visit(ctx.expression());
    }



}
