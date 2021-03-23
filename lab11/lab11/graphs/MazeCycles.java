package lab11.graphs;

import java.util.Random;

/**
 *  @author Yicheng Xia
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    
    private int[] parent;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        parent = new int[maze.V()];
        Random rand = new Random();
        int startX = rand.nextInt(maze.N());
        int startY = rand.nextInt(maze.N());
        int s = maze.xyTo1D(startX, startY);
        marked[s] = true;
        distTo[s] = 0;
        parent[s] = s;
        dfs(s);
    }

    private void dfs(int v) {
        for (int u : maze.adj(v)) {
            if (!marked[u]) {
                marked[u] = true;
                distTo[u] = distTo[v] + 1;
                announce();
                parent[u] = v;
                dfs(u);
            } else if (u != parent[v]) {
                // need to search for the common parent node, reverse and link the loop
                // to be finished
                return;
            }
        }
    }
}
