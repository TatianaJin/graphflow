package ca.waterloo.dsg.graphflow.query;

import ca.waterloo.dsg.graphflow.graph.Graph;
import ca.waterloo.dsg.graphflow.query.parser.StructuredQueryParser;
import ca.waterloo.dsg.graphflow.query.planner.QueryPlanBuilder;
import ca.waterloo.dsg.graphflow.query.plans.QueryPlan;
import ca.waterloo.dsg.graphflow.query.utils.StructuredQuery;
import org.antlr.v4.runtime.misc.ParseCancellationException;

/**
 * Class to accept incoming queries from the gRPC server, process them and return the results.
 */
public class QueryProcessor {

    private Graph graph = new Graph();

    public String process(String query) {
        StructuredQuery structuredQuery;
        try {
            structuredQuery = new StructuredQueryParser().parse(query);
        } catch (ParseCancellationException e) {
            return "ERROR parsing: " + e.getMessage();
        }
        QueryPlan queryPlan = new QueryPlanBuilder().plan(structuredQuery);
        return queryPlan.execute(graph);
    }
}