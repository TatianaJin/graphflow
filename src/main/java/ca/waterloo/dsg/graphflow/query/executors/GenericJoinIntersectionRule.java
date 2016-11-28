package ca.waterloo.dsg.graphflow.query.executors;

import ca.waterloo.dsg.graphflow.graph.Graph;
import ca.waterloo.dsg.graphflow.graph.Graph.Direction;
import ca.waterloo.dsg.graphflow.graph.Graph.GraphVersion;
import ca.waterloo.dsg.graphflow.util.ExistsForTesting;

/**
 * Represents a Generic Join rule. A Generic Join rule consists of the following: (1) A {@code
 * prefixIndex} that indicates the vertex {@code u} in the prefix that is being extended. (2) A
 * {@code direction} which indicates whether to extend {@code u} in the
 * {@link Graph.Direction#FORWARD} or {@link Graph.Direction#BACKWARD} direction. (3) A
 * {@code graphVersion} that indicates which graph the adjacency list of {@code u} should be used
 * when extending. (4) An {@code edgeType} that filters the edges of {@code u} according to their
 * types when extending.
 */
public class GenericJoinIntersectionRule {

    private int prefixIndex;
    private Direction direction;
    private GraphVersion graphVersion;
    private short edgeType;

    /**
     * Constructor for creating {@link GenericJoinIntersectionRule} with no graph version specified.
     * Graph version will default to {@link GraphVersion#PERMANENT}.
     *
     * @param prefixIndex The index of the prefix indicating the vertex {at code u} from which this
     * rule will extend.
     * @param direction The direction of extension. Either {@link Graph.Direction#FORWARD} outgoing
     * from {@code u} or {@link Graph.Direction#BACKWARD} incoming towards {@code u}.
     * @param edgeType Filters {@code u}'s edges that do not have the given type. If the types is
     * {@link Graph#ANY_TYPE} does not filter any of {@code u}'s edges.
     */
    public GenericJoinIntersectionRule(int prefixIndex, Direction direction, short edgeType) {
        this(prefixIndex, direction, GraphVersion.PERMANENT, edgeType);
    }

    /**
     * Constructor for creating {@link GenericJoinIntersectionRule} extending in the
     * {@link GraphVersion#PERMANENT} graph and no edge type filtering. Edge type will default to
     * {@link Graph#ANY_TYPE} essentially not filtering extensions by an edge type.
     *
     * @param prefixIndex The index of the prefix indicating the vertex {at code u} from which
     * this rule will extend.
     * @param direction The direction of extension. Either {@link Graph.Direction#FORWARD} outgoing
     * from {@code u} or {@link Graph.Direction#BACKWARD} incoming towards {@code u}.
     */
    public GenericJoinIntersectionRule(int prefixIndex, Direction direction) {
        this(prefixIndex, direction, GraphVersion.PERMANENT, Graph.ANY_TYPE);
    }

    /**
     * Constructor for creating {@link GenericJoinIntersectionRule} extending in the given
     * {@code graphVersion} and no edge type filtering. Edge type will default to
     * {@link Graph#ANY_TYPE} essentially not filtering extensions by an edge type.
     *
     * @param prefixIndex The index of the prefix indicating the vertex {@code u} from which this
     * rule will extend.
     * @param direction The direction of extension. Either {@link Graph.Direction#FORWARD} from
     * {@code u} or {@link Graph.Direction#BACKWARD} towards {@code u}.
     * @param graphVersion The version of the graph to be used for this rule.
     */
    public GenericJoinIntersectionRule(int prefixIndex, Direction direction,
        GraphVersion graphVersion) {
        this(prefixIndex, direction, graphVersion, Graph.ANY_TYPE);
    }

    /**
     * Constructor for creating {@link GenericJoinIntersectionRule} extending in the given
     * {@code graphVersion}. Edges will be filtered to match the given {@code edgeType}.
     *
     * @param prefixIndex The index of the prefix indicating the vertex {@code u} from which this
     * rule will extend.
     * @param direction The direction of extension. Either {@link Graph.Direction#FORWARD} from
     * {@code u} or {@link Graph.Direction#BACKWARD} towards {@code u}.
     * @param graphVersion The version of the graph to be used for this rule.
     * @param edgeType Filters {@code u}'s edges that do not have the given type. If the types is
     * {@link Graph#ANY_TYPE} does not filter any of {@code u}'s edges.
     */
    public GenericJoinIntersectionRule(int prefixIndex, Direction direction,
        GraphVersion graphVersion, short edgeType) {
        this.prefixIndex = prefixIndex;
        this.direction = direction;
        this.graphVersion = graphVersion;
        this.edgeType = edgeType;
    }

    public short getEdgeType() {
        return edgeType;
    }

    public int getPrefixIndex() {
        return prefixIndex;
    }

    public Direction getDirection() {
        return direction;
    }

    public GraphVersion getGraphVersion() {
        return graphVersion;
    }

    /**
     * Used during unit testing to check the equality of objects. This is used instead of
     * overriding the standard {@code equals()} and {@code hashCode()} methods.
     *
     * @param a The actual object.
     * @param b The expected object.
     * @return {@code true} if the {@code actual} object values are the same as the
     * {@code expected} object values, {@code false} otherwise.
     */
    @ExistsForTesting
    public static boolean isSameAs(GenericJoinIntersectionRule a, GenericJoinIntersectionRule b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        return a.prefixIndex == b.prefixIndex &&
            a.direction == b.direction &&
            a.graphVersion == b.graphVersion &&
            a.edgeType == b.edgeType;
    }
}
