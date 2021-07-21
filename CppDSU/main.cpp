#include <iostream>

using namespace std;
class Node
{
public:
    int rnk,parent;
    Node()
    {
        rnk=-1;
        parent=-1;
    }
};

class Disjoint_Set
{
    Node *node;
    int sz;
public:
    Disjoint_Set()
    {
        sz=100;
        node= new Node[sz];
    }
    Disjoint_Set(int n)
    {
        sz=n;
        node= new Node[sz];
    }
    void make_set(int x)
    {
        node[x].rnk=0;
        node[x].parent=x;
    }
    int find_set(int x)
    {
        if(node[x].parent==-1)
        {
            return -1;
        }
        if(node[x].parent!=x)
        {
            node[x].parent = find_set(node[x].parent);
        }
        return node[x].parent;
    }
    void Union(int u,int v)
    {
        int p= find_set(u),q=find_set(v);
        if( node[p].rnk > node[q].rnk)
            node[q].parent= p;
        else
        {
            node[p].parent= q;
            if(node[p].rnk==node[q].rnk)
            {
                node[q].rnk +=1;
            }
        }
    }
    void print(int u)
    {
        cout<<"The elements of set "<<u<<" : ";
        int x= find_set(u);
        for(int i=0;i<sz;i++)
        {
            if(find_set(i)==x)
            {
                cout<<i<<" ";
            }
        }
        cout<<endl;

    }
};
int main()
{
    int n;
    cout<<"Input size : ";
    cin>>n;
    Disjoint_Set p(n);

    while(1)
    {
        cout<<"Choose an operation for Disjoint Set."<<endl;
        cout<<"1. Make Set.\n2. Find Set.\n3. Union."<<endl;
        cout<<"4. Print.\n5. Quit\n";
        int choice;
        cin>>choice;
        if(choice==1)
        {
            cout<<"Input the element : ";
            int x;
            cin>>x;
            p.make_set(x);
            cout<<"Set "<<x<<" inserted"<<endl;
        }
        else if(choice==2)
        {
            cout<<"Input the element : ";
            int x;
            cin>>x;
            cout<<"The set containing "<<x<<" is "<<p.find_set(x)<<endl;
        }
        else if(choice==3)
        {
            cout<<"Input the 2 Sets : ";
            int x,y;
            cin>>x>>y;
            p.Union(x,y);
            cout<<"Set "<<x<<" and Set "<<y<<" union done."<<endl;
        }
        else if(choice==4)
        {
            cout<<"Input the Set : ";
            int x;
            cin>>x;
            p.print(x);
        }
        else if(choice==5)
        {
            break;
        }
    }

    return 0;
}
