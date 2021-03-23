package hw4.puzzle;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private class SearchNode {
        private WorldState state;
        private int moves;
        private Integer priority;
        private SearchNode prev;

        private SearchNode(WorldState state, SearchNode prev) {
            this.state = state;
            if (prev == null) {
                this.moves = 0;
            } else {
                this.moves = prev.moves + 1;
            }
            if (esdMap.containsKey(this.state)) {
                int d = esdMap.get(this.state);
                this.priority = this.moves + d;
            } else {
                int d = this.state.estimatedDistanceToGoal();
                esdMap.put(this.state, d);
                this.priority = this.moves + d;
            }
            this.prev = prev;
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode left, SearchNode right) {
            return left.priority.compareTo(right.priority);
        }
    }

    private Map<WorldState, Integer> esdMap = new HashMap<>(); // estimated distance of arrival
    private Stack<WorldState> path = new Stack<>();

    /** Constructor which solves the puzzle, computing
        everything necessary for moves() and solution() to
        not have to solve the problem again. Solves the
        puzzle using the A* algorithm. Assumes a solution exists. */
    public Solver(WorldState initial) {
        MinPQ<SearchNode> pq = new MinPQ<>(new SearchNodeComparator());
        SearchNode firstNode = new SearchNode(initial, null);
        while (!firstNode.state.isGoal()) {
            for (WorldState nextState : firstNode.state.neighbors()) {
                if (firstNode.prev == null || !nextState.equals(firstNode.prev.state)) {
                    SearchNode nextNode = new SearchNode(nextState, firstNode);
                    pq.insert(nextNode);
                }
            }
            firstNode = pq.delMin();
        }
        for (SearchNode node = firstNode; node != null; node = node.prev) {
            path.push(node.state);
        }
    }

    /** Returns the minimum number of moves to solve the puzzle starting
        at the initial WorldState. */
    public int moves() {
        return path.size() - 1;
    }

    /** Returns a sequence of WorldStates from the initial WorldState
        to the solution. */
    public Iterable<WorldState> solution() {
        return path;
    }
}
