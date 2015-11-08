/*

	Name : Rohit Siddheshwar
	Roll no. : 6787
	
	TITLE : Red-Black Tree

*/

import java.util.*;

class RedBlackTree{

	//defining colors of nodes in a RB Tree.
	static int RED_COLOR = 0;
	static int BLACK_COLOR = 1;
	static int DOUBLY_BLACK_COLOR = 2;

	static Node root;

	public static void main(String[] args){
		boolean megaFlag = true;
		System.out.println("|----------------------------------------|");
		System.out.println("|             RED BLACK TREE             |");
		System.out.println("|----------------------------------------|");
		while(megaFlag){
			System.out.println("|");
			System.out.println("|");
			System.out.println("|------------------MENU------------------|");
			System.out.println("|");
			System.out.println("|-------------1.Create Tree--------------|");
			System.out.println("|-------2.Insert Element into Tree-------|");
			System.out.println("|-------3.Delete Element from Tree-------|");
			System.out.println("|-------------4.Display Tree-------------|");
			System.out.println("|-----------------5.Exit-----------------|");
			System.out.println("|");
			System.out.println("|------------ Select Choice -------------|");
			System.out.print("|--->> ");
			Scanner sc = new Scanner(System.in);
			int choice = sc.nextInt();
			switch(choice){
				case 1:
					readInputForInsert();
				break;
				case 2:
					readInput();
				break;
				case 3:
					readInputForDelete();
				break;
				case 4:
					display(root);
				break;
				case 5:
					megaFlag = false;
				break;
				default:
					System.out.println("|-- Enter a number between 1 to 4 only.");
			}
		}
	}


	//-------------------INSERTION IN A RED BLACK TREE--------------------------------------//

	public static void readInputForInsert(){
		boolean flag = true;
		int dataTree;
		System.out.println("|- Start Entering Data and end it with a -1:- ");
		Scanner sc = new Scanner(System.in);
		while(flag){
			System.out.print("|-->> ");
			dataTree = sc.nextInt();
			if(dataTree == -1){
				flag = false;
				continue;
			}
			else{
				Node x = new Node();
				x.data = dataTree;
				x.parent = null;
				x.right = x.left = null;
				x.color = RED_COLOR;
				insertNode(x,root);
				fixTree(x);
			}
		}
	}

	public static void readInput(){
		Scanner sc = new Scanner(System.in);
		System.out.print("|- Enter element to be inserted :  ");
		int dataTree = sc.nextInt();
		Node x = new Node();
		x.data = dataTree;
		x.parent = null;
		x.right = x.left = null;
		x.color = RED_COLOR;
		insertNode(x,root);
		fixTree(x);
	}

	public static void insertNode(Node x,Node p){
		if(p == root && root == null){
			root = x;
		}
		else{
			if(x.data < p.data){
				if(p.left != null){
					insertNode(x,p.left);
				}
				else{
					p.left = x;
					x.parent = p;
				}
			}
			else if(x.data >= p.data){
				if(p.right != null){
					insertNode(x , p.right);
				}
				else{
					p.right = x;
					x.parent = p;
				}
			}
		}

	}

	public static void leftRotate(Node x){
		Node y = x.right;
		Node z = x.parent;
		Node b = y.left;

		y.left = x;
		x.parent = y;
		x.right = b;
		if(b != null)
			b.parent = x;

		if(z == null)
			root = y;
		else if(z.left == x)
			z.left = y;
		else if(z.right == x)
			z.right = y;
		y.parent = z;
	}

	public static void rightRotate(Node x){
		Node y = x.left;
		Node z = x.parent;
		Node b = y.right;

		y.right = x;
		x.parent = y;
		x.left = b;
		if(b != null)
			b.parent = x;

		if(z == null)
			root = y;
		else if(z.left == x)
			z.left = y;
		else if(z.right == x)
			z.right = y;
		y.parent = z;
	}

	public static void fixTree(Node x){
		if(x == root){
			x.color = BLACK_COLOR;
		}
		else{
			if(x.parent.color == RED_COLOR && x.color == RED_COLOR){
				Node p = x.parent;
				Node g = p.parent;
				Node u;
				if(p == g.left){
					u = g.right;
				}
				else{
					u = g.left;
				}
				if(u != null && u.color == RED_COLOR){
					//CASE 1 :
					u.color = BLACK_COLOR;
					p.color = BLACK_COLOR;
					g.color = RED_COLOR;
					fixTree(g);
				}
				else{
					if(g != null){
						if(p == g.left){
							if(p.left == x){
								//CASE 3:
								//LL
								rightRotate(g);
								int temp = p.color;
								p.color = g.color;
								g.color = temp;
							}	
							else{
								//CASE 2:
								//LR
								leftRotate(p);
								fixTree(p);
							}
						}
						else{
							if(p.right == x){
								//CASE 3':
								//RR
								leftRotate(g);
								int temp = p.color;
								p.color = g.color;
								g.color = temp;
							}
							else{
								//CASE 2':
								//RL
								rightRotate(p);
								fixTree(p);
							}
						}
					}	
				}
			}	
		}
	}

	//-------------------------------------------END----------------------------------------//

	public static void display(Node p){
		if(p == null){
			System.out.println("|--Empty Tree !!");
		}
		else{
			System.out.print("|--" + p.data + "--" + p.color);
			if(p.parent != null)
				System.out.print("--" + p.parent.data + "--");
			System.out.println();

			if(p.left != null){
				display(p.left);	
			}
			
			if(p.right != null){
				display(p.right);
			}
		}
	}

	//----------------------------DELETION IN A RED BLACK TREE-------------------------------//

	public static void readInputForDelete(){
		boolean flag = true;
		int toBeDeleted;
		Scanner sc = new Scanner(System.in);
		if(root != null){
			System.out.print("|- Enter element to be deleted : ");
			toBeDeleted = sc.nextInt();
			delete(searchData(root,toBeDeleted));
			display(root);
		}
		else{
			System.out.print("|--No element to be Deleted. \n|--Enter an element first?(Y/n) : ");
			String d = sc.nextLine();
			if(d.equals("Y") || d.equals("y")){
				readInput();
			}
		}
	}


	public static Node searchData(Node p,int data){
		if(p == null)
			return null;
		else if(p.data == data)
			return p;
		else{
			if(data < p.data)
				return searchData(p.left,data);
			else
				return searchData(p.right,data);
		}
	}

	public static Node inorderSuccessor(Node p){
		if(p.left == null)
			return p;
		else
			return inorderSuccessor(p.left);
	}

	public static void delete(Node z){
		Node y,x;
		if(z == null)
			System.out.println("|--Data Not Found, Please select some \n|--existing data item to be deleted.");
		else if(z.left == null && z.right == null && z == root)
			root = null;
		else{
			if(z.left == null || z.right == null){
				y = z;
			}
			else
				y = inorderSuccessor(z.right);

			if(y != z){
				z.data = y.data;
				delete(y);
			}
			else{
				if(y.left != null){
					x = y.left;
				}
				else if(y.right != null){
					x = y.right;
				}
				else{
					Node x1 = new Node();
					x1.parent = y;
					y.left = x1;
					x1.color = BLACK_COLOR;
					x1.left = x1.right = null;
					x1.data = -1;
					x = x1;
				}
				deleteRBT(y,x);
			}
		}
	}

	public static void deleteRBT(Node y,Node x){
		boolean flag;
		if(y == root){
			root = x;
			x.parent = null;
			flag = true;
		}
		else if(y == y.parent.left){
			y.parent.left = x;
			x.parent = y.parent;
			flag = true;
		}
		else{
			y.parent.right = x;
			x.parent = y.parent;
			flag = false;
		}
		x.color += y.color;
		if(x.color == DOUBLY_BLACK_COLOR)
			deleteRBTFixup(x,flag);
		else{
			if(x.data == -1){
				if(x == x.parent.right)
					x.parent.right = null;
				else
					x.parent.left = null;
			}
		}
	}

	public static void deleteRBTFixup(Node x,boolean flag){
		if(x == root){
			x.color = BLACK_COLOR;
			return;
		}
		Node p = x.parent;
		Node w;
		Node nl;
		Node nr;
		if(flag)
			w = p.right;
		else
			w = p.left;
		if(w != null){
			nl = w.left;
			nr = w.right;
		}
		else{
			nl = null;
			nr = null;
		}
		if(flag){
			//x is a left child.
			//case 1
			if(w != null && w.color == RED_COLOR){
				int temp;
				temp = p.color;
				p.color = w.color;
				w.color = temp;
				leftRotate(p);
				deleteRBTFixup(x,flag);
			}
			else{
				if(nr == null || nr.color == BLACK_COLOR){
					//case 2 (BB)
					if(nl == null || nl.color == BLACK_COLOR){
						x.color -= 1;
						if(w != null)
							w.color -= 1;
						p.color += 1;
						if(x.data == -1){
							p.left = null;
						}
						if(p.color == DOUBLY_BLACK_COLOR){
							if(p == p.parent.left)
								deleteRBTFixup(p,true);
							else
								deleteRBTFixup(p,false);
						}
						/*
						else{
							stop;
						}
						*/
					}
					//case 3 (RB)
					else{
						if(w != null){
							int temp;
							if(nl != null){
								temp = nl.color;
								nl.color = w.color;
								w.color = temp;
							}
							else{
								w.color = BLACK_COLOR;
							}
							rightRotate(w);
							deleteRBTFixup(x,flag);
						}
					}
				}
				else{
					//case 4
					if(nr != null)
						nr.color = w.color;
					w.color = p.color;
					p.color = BLACK_COLOR;
					x.color -=1;
					leftRotate(p);
					if(x.data == -1){
						p.left = null;
					}
					//stop
				}
			}
		}
		else{
			//x is a right child.
			//case 1'
			if(w != null && w.color == RED_COLOR){
				int temp;
				temp = p.color;
				p.color = w.color;
				w.color = temp;
				rightRotate(p);
				deleteRBTFixup(x,flag);
			}
			else{
				if(nl == null || nl.color == BLACK_COLOR){
					//case 2' (BB)
					if(nr == null || nr.color == BLACK_COLOR){
						x.color -= 1;
						if(w != null)
							w.color -= 1;
						p.color += 1;
						if(x.data == -1){
							p.right = null;
						}
						if(p.color == DOUBLY_BLACK_COLOR){
							if(p.parent == null)
								deleteRBTFixup(p,true);
							else if(p == p.parent.left)
								deleteRBTFixup(p,true);
							else
								deleteRBTFixup(p,false);
						}
						/*
						else{
							stop;
						}
						*/
					}
					//case 3' (BR)
					else{
						int temp;
						if(nr != null){
							temp = nr.color;
							nr.color = w.color;
							w.color = temp;
						}
						else
							w.color = BLACK_COLOR;
						leftRotate(w);
						deleteRBTFixup(x,flag);
					}
				}
				else{
					//case 4
					if(nl != null)
						nl.color = w.color;
					w.color = p.color;
					p.color = BLACK_COLOR;
					x.color -=1;
					rightRotate(p);
					if(x.data == -1){
						p.right = null;
					}
					//stop
				}
			}
		}

	}

	//-------------------------------------------END----------------------------------------//

	static class Node{
		int data;
		Node parent;
		Node right;
		Node left;
		int color;
	}
}

/*
Output : 

|----------------------------------------|
|             RED BLACK TREE             |
|----------------------------------------|
|
|
|------------------MENU------------------|
|
|-------------1.Create Tree--------------|
|-------2.Insert Element into Tree-------|
|-------3.Delete Element from Tree-------|
|-------------4.Display Tree-------------|
|-----------------5.Exit-----------------|
|
|------------ Select Choice -------------|
|--->> 1
|- Start Entering Data and end it with a -1:- 
|-->> 5
|-->> 4
|-->> 3
|-->> 6
|-->> 2
|-->> 8
|-->> 6
|-->> 7
|-->> 5
|-->> 1
|-->> -1
|
|
|------------------MENU------------------|
|
|-------------1.Create Tree--------------|
|-------2.Insert Element into Tree-------|
|-------3.Delete Element from Tree-------|
|-------------4.Display Tree-------------|
|-----------------5.Exit-----------------|
|
|------------ Select Choice -------------|
|--->> 4
|--4--1
|--2--1--4--
|--1--0--2--
|--3--0--2--
|--6--0--4--
|--5--1--6--
|--5--0--5--
|--7--1--6--
|--6--0--7--
|--8--0--7--
|
|
|------------------MENU------------------|
|
|-------------1.Create Tree--------------|
|-------2.Insert Element into Tree-------|
|-------3.Delete Element from Tree-------|
|-------------4.Display Tree-------------|
|-----------------5.Exit-----------------|
|
|------------ Select Choice -------------|
|--->> 3
|- Enter element to be deleted : 3
|--4--1
|--2--1--4--
|--1--0--2--
|--6--0--4--
|--5--1--6--
|--5--0--5--
|--7--1--6--
|--6--0--7--
|--8--0--7--
|
|
|------------------MENU------------------|
|
|-------------1.Create Tree--------------|
|-------2.Insert Element into Tree-------|
|-------3.Delete Element from Tree-------|
|-------------4.Display Tree-------------|
|-----------------5.Exit-----------------|
|
|------------ Select Choice -------------|
|--->> 5

*/
