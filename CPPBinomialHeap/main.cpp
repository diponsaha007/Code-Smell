#include <iostream>
#include<bits/stdc++.h>
#define inf 99999
using namespace std;

class node
{
public:
    int key;
    int degree;
    node* child;
    node* sibling;
    node* parent;
    node(int val = -1, int dgr = 0)
    {
        key = val;
        degree = dgr;
        child = NULL;
        sibling = NULL;
        parent = NULL;
    }
};

class BinomialHeap
{

    node* heap_minimum();
    void binomial_link(node*y, node*z);
    void heap_merge(node* H2);
    void print_tree(node*x, int level=0);
public:
    node* head;
    BinomialHeap();
    BinomialHeap(node* x);
    int Minimum();
    void Insert(int k);
    void Union(node* h2);
    int ExtractMin();
    void Print();
};

BinomialHeap::BinomialHeap()
{
    head = NULL;
}

BinomialHeap::BinomialHeap(node* x)
{
    head = x;
}

node* BinomialHeap::heap_minimum()
{
    node* y = NULL;
    node* x = head;
    int min = inf;
    while(x!=NULL)
    {
        if(x->key<min)
        {
            min = x->key;
            y = x;
        }
        x = x->sibling;
    }

    return y;
}

int BinomialHeap::Minimum()
{
    node* y = heap_minimum();
    return y->key;
}

/*
Makes y a child of z
*/
void BinomialHeap::binomial_link(node*y, node*z)
{
    y->parent = z;
    y->sibling = z->child;
    z->child = y;
    z->degree = z->degree + 1;
}

void BinomialHeap::Insert(int k)
{
    node* x = new node(k);
    Union(x);
}

/*
H2: head of the heap we want to merge with the current one.

This function merges the other heap(whose head is H2) with this one by inserting H2's root list into current heap's
root list ordered by degree.
*/

void BinomialHeap::heap_merge(node* H2)
{
    node* a = head;
    node* b = H2;
    if(a==NULL)
    {
        head = b;
        b = NULL;
    }
    else if(a->degree>b->degree)
    {
        head = b;
        b = a;
    }
    else
        head = a;
    if (head == NULL)
        return;
    a = head;
    //All the lines above make sure that a points to the head who have the lesser degree
    //and a points to the head who have the greater degree among the two heaps we want to merge

    //This while loop maintains the a and b pointers degree property until b becomes NULL
    //When b becomes NULL there are no more nodes left to merge
    while(b!=NULL)
    {
        if(a->sibling==NULL)
        {
            a->sibling = b;
            return;
        }
        else if(a->sibling->degree<b->degree)
        {
            a = a->sibling;
        }
        else
        {
            node* c = b->sibling;
            b->sibling = a->sibling;
            a->sibling = b;
            a = a->sibling;
            b=c;
        }
    }

}

void BinomialHeap::Union(node* h2)
{
    if(h2==NULL) return;
    heap_merge(h2);

    if (head==NULL)
        return;
    //Now the heap contains a root list with roots sorted by degree
    //We now have to restore the heap property by linking them
    //We will use three pointers to point three consecutive nodes each time.
    node* prev_x = NULL;
    node* x = head;
    node* next_x = x->sibling;

    while(next_x!=NULL)
    {
        //if the next node is not the same degree as the current one or next node's sibling contains the same degree
        //with the current and the next node, we have to proceed.
        if((x->degree != next_x->degree) || (next_x->sibling!=NULL && (next_x->sibling->degree==x->degree)))
        {
            prev_x = x;
            x = next_x;
        }

        else
        {
            //if the current node contains smaller value we will make the next node it's child
            if(x->key <= next_x->key)
            {
                x->sibling = next_x->sibling;
                binomial_link(next_x, x);
            }
            else
            {
                //if the previous node is null, make the next one the heap's head and make x it's child.
                // else just make x it's child.
                if(prev_x==NULL)
                    head = next_x;
                else
                    prev_x->sibling = next_x;
                binomial_link(x,next_x);
                //proceed
                x = next_x;
            }
        }
        next_x = x->sibling;
    }
}

int BinomialHeap::ExtractMin()
{
    node *min_node = heap_minimum();
    node *prev_x = NULL;
    node *x = head;
    while (x != min_node)
    {
        prev_x = x;
        x = x -> sibling;
    }
    if (prev_x == NULL) head = x -> sibling;
    else prev_x -> sibling = x -> sibling;

    //in the above lines the min_node is removed from the root list.

    //now we have to reverse the children list. min_node_children points to the reversed list's head. cur and next pointers
    //are used to create the list.
    node *min_node_children = NULL;
    node *cur = min_node -> child;
    while (cur != NULL)
    {
        node *next = cur -> sibling;
        cur -> sibling = min_node_children;
        min_node_children = cur;
        cur = next;
    }
    Union(min_node_children);

    return min_node->key;
}

void BinomialHeap::print_tree(node* root, int level)
{
    if(root == NULL)
        return;
    queue<node *> q1, q2;

    q1.push(root);
    node* child_node = new node();

    while (!q1.empty() or !q2.empty()) {

        if (!q1.empty())
            cout << "Level " << level << ": ";

        while (!q1.empty()) {
            node * node = q1.front();
            q1.pop();
            cout << node->key << " ";


            child_node = node->child;

            while(child_node!=NULL)
            {
                q2.push(child_node);
                child_node = child_node->sibling;
            }
        }
        cout << endl;
        level++;

        if (!q2.empty())
            cout << "Level " << level << ": ";

        while (!q2.empty()) {
            node * node = q2.front();
            q2.pop();
            cout << node->key << " ";

            child_node = node->child;

            while(child_node!=NULL)
            {
                q1.push(child_node);
                child_node = child_node->sibling;
            }
        }
        cout << endl;
        level++;
    }
}



void BinomialHeap::Print()
{
    cout<<"Printing Binomial Heap..."<<endl<<endl;
    if (head == NULL)
    {
        cout<<"The Heap is empty"<<endl;
        return ;
    }
    node* tree_root;
    node* tree_child;
    tree_root = head;
    tree_child = head;
    while (tree_root != NULL)
    {
        cout<<"Binomial Tree, B"<<tree_root->degree<<endl;
        print_tree(tree_root,0);
        tree_root = tree_root->sibling;
        cout<<endl;
    }

}

int main()
{
    BinomialHeap H;
    char inchar;
    int in;
    int is_file_print = 0;
    freopen("in.txt", "r", stdin);
    if(is_file_print) freopen("output.txt", "w", stdout);
    while(cin>>inchar)
    {
        if (inchar == 'U')
        {
            BinomialHeap H2;
            string line;
            getline( cin, line );

            istringstream iss( line );
            int number;
            while( iss >> number )
            {
                H2.Insert(number);
            }
            H.Union(H2.head);

        }
        if(inchar == 'F')
        {
            cout<<"Find-Min returned "<<H.Minimum()<<endl;
        }
        else if (inchar == 'E')
        {
            cout<<"Extract-Min returned "<<H.ExtractMin()<<endl;
        }
        else if (inchar == 'P')
        {
            H.Print();
        }
        else if (inchar == 'I')
        {
            cin>>in;
            H.Insert(in);
        }
    }
    return 0;
}
