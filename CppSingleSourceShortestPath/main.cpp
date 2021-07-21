#include <iostream>
#include <vector>
#include <queue>
#define INF 100000000
using namespace std;

class Graph
{
    vector<vector<pair<int, int> > > adj;//first value is the vertex and second value is the weight
    int vertices;
public:
    Graph()
    {
        vertices = 0;
    }

    Graph(int n)
    {
        adj.resize(n);
        vertices = n;
    }

    void setnVertices(int n)
    {
        adj.resize(n);
        vertices = n;
    }

    void add_edge(int u, int v, int w)
    {
        if (u >= 0 && u < vertices && v >= 0 && v < vertices)
            adj[u].push_back({v, w});
    }

    void get_path(int curr_vertex, int s, vector<int> &parent, vector<int> &path)
    {
        if (curr_vertex == s)
        {
            path.push_back(s);
            return;
        }
        get_path(parent[curr_vertex], s, parent, path);
        path.push_back(curr_vertex);

    }

    void dijkstra(int s, int d)
    {
        vector<int> dist(vertices, INF);
        vector<bool> visited(vertices, false);
        vector<int> parent(vertices, -1);
        vector<int> path;
        priority_queue<pair<int, int>, vector<pair<int, int> >, greater<pair<int, int> > > pq;

        dist[s] = 0;
        pq.push({0, s});
        while (!pq.empty())
        {
            int a = pq.top().second;
            pq.pop();
            if (visited[a])
                continue;
            visited[a] = true;
            for (pair<int, int> i : adj[a])
            {
                if (dist[i.first] > dist[a] + abs(i.second))
                {
                    dist[i.first] = dist[a] + abs(i.second);
                    pq.push({dist[i.first], i.first});
                    parent[i.first] = a;
                }
            }
        }

        cout << "Dijkstra Algorithm:\n" << dist[d] << endl;
        get_path(d, s, parent, path);
        for (int i = 0; i < path.size(); i++)
        {
            if (i == path.size() - 1)
                cout << path[i] << endl;
            else
                cout << path[i] << " -> ";
        }

    }

    void bellman_ford(int s, int d)
    {
        vector<int> dist(vertices, INF);
        vector<bool> visited(vertices, false);
        vector<int> parent(vertices, -1);
        vector<int> path;
        dist[s] = 0;
        for (int i = 1; i <= vertices - 1; i++)
        {
            for (int a = 0; a < vertices; a++)
            {
                for (pair<int, int> b : adj[a])
                {
                    if (dist[b.first] > dist[a] + b.second)
                    {
                        dist[b.first] = dist[a] + b.second;
                        parent[b.first] = a;
                    }
                }
            }
        }
        //Checking for negative weight cycle
        for (int a = 0; a < vertices; a++)
        {
            for (pair<int, int> b : adj[a])
            {
                if (dist[b.first] > dist[a] + b.second)
                {
                    cout << "Bellman Ford Algorithm:\n" << "The graph contains negative weight cycle.\n";
                    cout << "So, there is no shortest path." << endl;
                    return;
                }
            }
        }

        cout << "Bellman Ford Algorithm:\n" << dist[d] << endl;
        get_path(d, s, parent, path);
        for (int i = 0; i < path.size(); i++)
        {
            if (i == path.size() - 1)
                cout << path[i] << endl;
            else
                cout << path[i] << " -> ";
        }


    }


};

int main()
{
    Graph g;
    int n, m;
    int s, d;
    int u, v, w;

    cin >> n >> m;
    g.setnVertices(n);
    for (int i = 0; i < m; i++)
    {
        cin >> u >> v >> w;
        g.add_edge(u, v, w);
    }

    cin >> s >> d;
    g.bellman_ford(s, d);
    cout << endl;
    g.dijkstra(s, d);


    return 0;
}