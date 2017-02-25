package ca.waterloo.dsg.graphflow.util;

import ca.waterloo.dsg.graphflow.graph.EdgeStore;
import ca.waterloo.dsg.graphflow.graph.VertexPropertyStore;
import ca.waterloo.dsg.graphflow.query.output.MatchQueryOutput;
import ca.waterloo.dsg.graphflow.query.structuredquery.QueryPropertyPredicate;
import ca.waterloo.dsg.graphflow.util.PropertyComparator.ComparisonOperator;

import java.util.Map;
import java.util.function.Predicate;

public class FilterPredicateFactory {

    public static Predicate<MatchQueryOutput> getFilterPredicate(QueryPropertyPredicate
        queryPropertyPredicate, Map<String, Integer> orderedVariableIndexMap) {
        switch (queryPropertyPredicate.getPredicateType()) {
            case TWO_VERTEX:
                return getTwoVertexPropertyPredicate(queryPropertyPredicate,
                    orderedVariableIndexMap);
            case TWO_EDGE:
                return getTwoEdgePropertyPredicate(queryPropertyPredicate, orderedVariableIndexMap);
            case EDGE_AND_VERTEX:
                return getVertexAndEdgePropertyPredicate(queryPropertyPredicate,
                    orderedVariableIndexMap);
            case VERTEX_AND_CONSTANT:
                return getVertexAndConstantPropertyPredicate(queryPropertyPredicate,
                    orderedVariableIndexMap);
            case EDGE_AND_CONSTANT:
                return getEdgeAndConstantPropertyPredicate(queryPropertyPredicate,
                    orderedVariableIndexMap);
            default:
                return null;
        }

    }

    private static Predicate<MatchQueryOutput> getTwoVertexPropertyPredicate
        (QueryPropertyPredicate queryPropertyPredicate, Map<String, Integer>
            orderedVariableIndexMap) {

        return p -> resolveOperator(VertexPropertyStore.getInstance().getProperty(p
                .vertexIds[orderedVariableIndexMap.get(queryPropertyPredicate.getVariable1())],
            queryPropertyPredicate.getPropertyOfVariable1()),
            VertexPropertyStore.getInstance().getProperty(p.vertexIds[orderedVariableIndexMap.get
                (queryPropertyPredicate.getVariable2())], queryPropertyPredicate
                .getPropertyOfVariable2()), queryPropertyPredicate.getComparisonOperator());
    }

    private static Predicate<MatchQueryOutput> getTwoEdgePropertyPredicate(QueryPropertyPredicate
        queryPropertyPredicate, Map<String, Integer> orderedVariableIndexMap) {
        return p -> resolveOperator(EdgeStore.getInstance().getProperty(p
                .edgeIds[orderedVariableIndexMap.get(queryPropertyPredicate.getVariable1())],
            queryPropertyPredicate.getPropertyOfVariable1()),
            EdgeStore.getInstance().getProperty(p.edgeIds[orderedVariableIndexMap.get
                (queryPropertyPredicate.getVariable2())], queryPropertyPredicate
                .getPropertyOfVariable2()), queryPropertyPredicate.getComparisonOperator());
    }

    private static Predicate<MatchQueryOutput> getVertexAndEdgePropertyPredicate
        (QueryPropertyPredicate
            queryPropertyPredicate, Map<String, Integer> orderedVariableIndexMap) {
        return p -> resolveOperator(VertexPropertyStore.getInstance().getProperty(p
                .vertexIds[orderedVariableIndexMap.get(queryPropertyPredicate.getVariable1())],
            queryPropertyPredicate.getPropertyOfVariable1()),
            EdgeStore.getInstance().getProperty(p.edgeIds[orderedVariableIndexMap.get
                (queryPropertyPredicate.getVariable2())], queryPropertyPredicate
                .getPropertyOfVariable2()), queryPropertyPredicate.getComparisonOperator());
    }

    private static Predicate<MatchQueryOutput> getVertexAndConstantPropertyPredicate
        (QueryPropertyPredicate
            queryPropertyPredicate, Map<String, Integer> orderedVariableIndexMap) {
        return p -> resolveOperator(VertexPropertyStore.getInstance().getProperty(p.
                vertexIds[orderedVariableIndexMap.get(queryPropertyPredicate.getVariable1())],
            queryPropertyPredicate.getPropertyOfVariable1()), queryPropertyPredicate.
            resolveConstant(queryPropertyPredicate.getVariable2()), queryPropertyPredicate.
            getComparisonOperator());
    }

    private static Predicate<MatchQueryOutput> getEdgeAndConstantPropertyPredicate
        (QueryPropertyPredicate
            queryPropertyPredicate, Map<String, Integer> orderedVariableIndexMap) {
        return p -> resolveOperator(EdgeStore.getInstance().getProperty(p.
                edgeIds[orderedVariableIndexMap.get(queryPropertyPredicate.getVariable1())],
            queryPropertyPredicate.getPropertyOfVariable1()), queryPropertyPredicate.
            resolveConstant(queryPropertyPredicate.getVariable2()), queryPropertyPredicate.
            getComparisonOperator());
    }

    private static boolean resolveOperator(Object operand1, Object operand2,
        ComparisonOperator comparisonOperator) {

        if (operand1 instanceof Boolean) {
            return PropertyComparator.compare((Boolean) operand1, (Boolean) operand2,
                comparisonOperator);
        } else if (operand1 instanceof String) {
            return PropertyComparator.compare((String) operand1, (String) operand2,
                comparisonOperator);
        } else if (operand1 instanceof Double && operand2 instanceof Integer) {
            return PropertyComparator.compare((Double) operand1, new Double(((Integer)
                operand2).intValue()), comparisonOperator);
        } else if (operand1 instanceof Integer && operand2 instanceof Double) {
            return PropertyComparator.compare(new Double(((Integer) operand1).intValue()),
                (Double) operand2, comparisonOperator);
        } else if (operand1 instanceof Double) {
            return PropertyComparator.compare((Double) operand1, (Double) operand2,
                comparisonOperator);
        } else {
            return PropertyComparator.compare((Integer) operand1, (Integer) operand2,
                comparisonOperator);
        }
    }
}

