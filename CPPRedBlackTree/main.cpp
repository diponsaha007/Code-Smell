#include <iostream>
#define RED 1
#define BLACK 2
#define DBLACK 3
#define nil -1
using namespace std;

class node
{
public:
    int key;
    int color;
    node* left;
    node* right;
    node* parent;
    node(int val = -1, int clr = RED)
    {
        key = val;
        color = clr;
        left = NULL;
        right = NULL;
        parent = NULL;
    }
};

class RedBlackTree
{
    node *root;
    node *Tnil;
    void left_rotate(node *x);
    void right_rotate(node *y);
    void insert_fixup(node *z);
    void preorder(node *ptr);
    node* tree_minimum(node *x);
    void rb_transplant(node* u, node* v);
    void delete_fixup(node* x);
    node* tree_search(node*x, int k);
public:
    RedBlackTree();
    void rbinsert(int k);
    void search_value(int k);
    void rbdelete(int k);
    ~RedBlackTree();

};

RedBlackTree::RedBlackTree()
{
    Tnil = new node(-1, BLACK);
    root = Tnil;
}

void RedBlackTree::left_rotate(node *x)
{
    node* y;
    y = x->right;
    x->right = y->left;
    if(y->left!=Tnil)
    {
        y->left->parent=x;
    }
    y->parent=x->parent;
    if(x->parent== Tnil)
        root = y;
    else if(x == x->parent->left)
        x->parent->left=y;
    else
        x->parent->right=y;
    y->left=x;
    x->parent=y;
}

void RedBlackTree::right_rotate(node *y)
{
    node *x;
    x = y->left;
    y->left = x->right;
    if (x->right!=Tnil)
        x->right->parent = y;
    x->parent=y->parent;
    if(y->parent== Tnil)
        root = x;
    else if(y == y->parent->right)
        y->parent->right=x;
    else
        y->parent->left=x;
    x->right = y;
    y->parent = x;
}

void RedBlackTree::insert_fixup(node *z)
{
    if(z->parent!=NULL)
    {
        while (z->parent->color == RED)
        {
            if (z->parent == z->parent->parent->left)
            {

                node *y = z->parent->parent->right;
                if (y->color==RED)
                {
                    z->parent->color = BLACK;
                    y->color = BLACK;
                    z->parent->parent->color=RED;
                    z=z->parent->parent;
                }

                else
                {
                    if(z==z->parent->right)
                    {
                        z = z->parent;
                        left_rotate(z);
                    }
                    z->parent->color = BLACK;
                    z->parent->parent->color = RED;
                    right_rotate(z->parent->parent);
                }

            }

            else
            {

                node *y = z->parent->parent->left;
                if (y->color==RED)
                {
                    z->parent->color = BLACK;
                    y->color = BLACK;
                    z->parent->parent->color=RED;
                    z=z->parent->parent;


                }
                else
                {
                    if(z==z->parent->left)
                    {
                        z = z->parent;
                        right_rotate(z);

                    }
                    z->parent->color = BLACK;

                    z->parent->parent->color = RED;

                    left_rotate(z->parent->parent);

                }
            }
        }

    }
    root->color=BLACK;
}


void RedBlackTree::rbinsert(int k)
{
    node *y = Tnil;
    node *x = root;
    node *z = new node(k);

    while (x != Tnil)
    {
        y=x;
        if (z->key<x->key)
        {
            x=x->left;
        }
        else
            x = x->right;
    }
    z->parent = y;
    if(y==Tnil)
        root = z;
    else if(z->key<y->key)
        y->left = z;
    else
        y->right = z;
    z->left = Tnil;
    z->right = Tnil;
    z->color = RED;
    insert_fixup(z);
    preorder(root);
    cout<<endl;
}


node* RedBlackTree::tree_minimum(node *x)
{
    while(x->left!=Tnil) x = x->left;
    return x;
}

void RedBlackTree::rb_transplant(node* u, node* v)
{
    if(u->parent == Tnil)
    {
        root = v;
    }
    else if(u==u->parent->left)
    {
        u->parent->left = v;
    }
    else u->parent->right = v;
    v->parent = u->parent;
}


void RedBlackTree::delete_fixup(node* x)
{
    while(x!=root && x->color==BLACK)
    {
        if(x==x->parent->left)
        {
            node* w = x->parent->right;
            if(w->color == RED)
            {
                w->color = BLACK;
                x->parent->color = RED;
                left_rotate(x->parent);
                w = x->parent->right;
            }
            if(w->left->color == BLACK && w->right->color == BLACK)
            {
                w->color = RED;
                x = x->parent;
            }
            else
            {
                if(w->right->color==BLACK)
                {
                    w->left->color = BLACK;
                    w->color = RED;
                    right_rotate(w);
                    w = x->parent->right;
                }
                w->color = x->parent->color;
                x->parent->color = BLACK;
                w->right->color = BLACK;
                left_rotate(x->parent);
                x = root;
            }

        }

        else
        {
            node* w = x->parent->left;
            if(w->color == RED)
            {
                w->color = BLACK;
                x->parent->color = RED;
                right_rotate(x->parent);
                w = x->parent->left;
            }
            if(w->right->color == BLACK and w->left->color == BLACK)
            {
                w->color = RED;
                x = x->parent;
            }
            else
            {
                if(w->left->color==BLACK)
                {
                    w->right->color = BLACK;
                    w->color = RED;
                    left_rotate(w);
                    w = x->parent->left;
                }
                w->color = x->parent->color;
                x->parent->color = BLACK;
                w->left->color = BLACK;
                right_rotate(x->parent);
                x = root;
            }

        }
    }
    x->color = BLACK;
}

node* RedBlackTree::tree_search(node*x, int k)
{
    if(x==Tnil || x->key==k) return x;
    if (k<x->key) tree_search(x->left, k);
    else tree_search(x->right,k);
}

void RedBlackTree::search_value(int k)
{
    if(tree_search(root, k)==Tnil) cout<<"False"<<endl;
    else cout<<"True"<<endl;
}

void RedBlackTree::rbdelete(int k)
{
    node* z = tree_search(root, k);
    node* y = z;
    node* x;
    int y_org_col = y->color;
    if(z->left == Tnil)
    {
        x = z->right;
        rb_transplant(z, z->right);
    }

    else if(z->right == Tnil)
    {
        x = z->left;
        rb_transplant(z, z->left);
    }
    else
    {
        y = tree_minimum(z->right);
        y_org_col = y->color;
        x = y->right;
        if(y->parent==z) x->parent = y;
        else
        {
            rb_transplant(y, y->right);
            y->right = z->right;
            y->right->parent = y;
        }
        rb_transplant(z,y);
        y->left = z->left;
        y->left->parent = y;
        y->color = z->color;
    }

    if(y_org_col==BLACK) delete_fixup(x);
    preorder(root);
    cout<<endl;
}

void RedBlackTree::preorder(node *ptr)
{
    if (ptr == Tnil)
        return;

    char c;
    if(ptr->color==BLACK)
        c='b';
    else if(ptr->color==RED)
        c='r';

    cout << ptr->key << ":" << c;
    if((ptr->left == Tnil) && (ptr->right==Tnil)) return;
    cout<<"(";
    preorder(ptr->left);
    cout<<")(";
    preorder(ptr->right);
    cout<<")";
}

RedBlackTree::~RedBlackTree()
{
    delete root;
    delete Tnil;
}

int main()
{
    RedBlackTree rbt;
    char inchar;
    int in;
    freopen("in.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    while(cin>>inchar>>in)
    {
        if(inchar == 'F') rbt.search_value(in);
        else if (inchar == 'D') rbt.rbdelete(in);
        else if (inchar == 'I') rbt.rbinsert(in);
    }
    return 0;
}