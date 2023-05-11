package ASD.AVL_Tree.TugasKelompok;

import java.util.Scanner;

class NodeAVL{
    int value;
    int height;
    NodeAVL leftChild;
    NodeAVL rightChild;

    NodeAVL(int value){
        this.value = value;
        this.height = 0;
        this.leftChild = null;
        this.rightChild = null;
    }
}

class AVLTree{
    private NodeAVL root;

    AVLTree(){
        root = null;
    }

    public boolean isEmpty(){
        return root == null;
    }

    public int getLevels(){
        return countLevels(root);
    }

    private int countLevels(NodeAVL node){
        if (node == null)
            return 0;
        else {
            int leftHeight = countLevels(node.leftChild);
            int rightHeight = countLevels(node.rightChild);
            return getMax(leftHeight, rightHeight) + 1;
        }
    }

    private int getHeight(NodeAVL node){
        if(node == null){
            return -1;
        } else {
            return node.height;
        }
    }

    private int getMax(int leftNodeHeight, int rightNodeHeight){
        return leftNodeHeight > rightNodeHeight? leftNodeHeight : rightNodeHeight;
    }

    private int countBalanceFactor(NodeAVL node){
        if(node == null){
            return 0;
        } else {
            return getHeight(node.leftChild) - getHeight(node.rightChild);
        }
    }

    public void insert(int value){
        root = insertRec(value, root);
    }

    private NodeAVL insertRec(int value, NodeAVL node){
        if(node == null){
            node = new NodeAVL(value);
        } else if(value < node.value){
            node.leftChild = insertRec(value, node.leftChild);
            if(countBalanceFactor(node) == 2){
                if(value < node.leftChild.value){
                    node = rightRotate(node);
                } else {
                    node = leftRightRotate(node);
                }
            }
        } else if(value > node.value){
            node.rightChild = insertRec(value, node.rightChild);
            if(countBalanceFactor(node) == -2){
                if(value > node.rightChild.value){
                    node = leftRotate(node);
                } else {
                    node = rightLeftRotate(node);
                }
            }
        } else {
            node.height = getMax(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
            return node;
        }
        node.height = getMax(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
        return node;
    }


    NodeAVL rightRotate(NodeAVL node2){
        NodeAVL node1 = node2.leftChild;
        node2.leftChild = node1.rightChild;
        node1.rightChild = node2;
        node2.height = getMax(getHeight(node2.leftChild), getHeight(node2.rightChild)) + 1;
        node1.height = getMax(getHeight(node1.leftChild), node2.height) + 1;

        return node1;
    }

    NodeAVL leftRotate(NodeAVL node1){
        NodeAVL node2 = node1.rightChild;
        node1.rightChild = node2.leftChild;
        node2.leftChild = node1;
        node1.height = getMax(getHeight(node1.leftChild), getHeight(node1.rightChild)) + 1;
        node2.height = getMax(getHeight(node2.leftChild), node1.height) + 1;

        return node2;
    }

    NodeAVL leftRightRotate(NodeAVL node) {
        node.leftChild = leftRotate(node.leftChild);
        return rightRotate(node);
    }

    NodeAVL rightLeftRotate(NodeAVL node){
        node.rightChild = rightRotate(node.rightChild);
        return leftRotate(node);
    }

    void preorder(){
        if(isEmpty()){
            System.out.println("Tree Kosong");
        } else {
            preorderRec(root);
        }
    }

    private void preorderRec(NodeAVL current){
        if(current != null){
            System.out.print(current.value + " ");
            preorderRec(current.leftChild);
            preorderRec(current.rightChild);
        }
    }

    void inorder(){
        if(isEmpty()){
            System.out.println("Tree Kosong");
        } else {
            inorderRec(root);
        }
    }

    private void inorderRec(NodeAVL current){
        if(current != null){
            inorderRec(current.leftChild);
            System.out.print(current.value + " ");
            inorderRec(current.rightChild);
        }
    }

    void postorder(){
        if(isEmpty()){
            System.out.println("Tree Kosong");
        } else {
            postorderRec(root);
        }
    }

    private void postorderRec(NodeAVL current){
        if(current != null){
            postorderRec(current.leftChild);
            postorderRec(current.rightChild);
            System.out.print(current.value + " ");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        AVLTree avl = new AVLTree();

        while (in.hasNext()){
            if(in.hasNextLine()){
                String perintah = in.nextLine();
                String[] perintahSplitted = perintah.split(" ");
                if(perintahSplitted[0].equalsIgnoreCase("print")){
                    if(perintahSplitted[1].equalsIgnoreCase("inorder")){
                        avl.inorder();
                        System.out.println();
                    } else if(perintahSplitted[1].equalsIgnoreCase("preorder")){
                        avl.preorder();
                        System.out.println();
                    } else if(perintahSplitted[1].equalsIgnoreCase("postorder")){
                        avl.postorder();
                        System.out.println();
                    } else if(perintahSplitted[1].equalsIgnoreCase("levels")){
                        System.out.println("Tree level: " + avl.getLevels());
                    }
                } else if(perintahSplitted[0].equalsIgnoreCase("insert")){
                    avl.insert(Integer.parseInt(perintahSplitted[1]));
                } else if(perintahSplitted[0].equalsIgnoreCase("exit")){
                    System.exit(0);
                }
            } else {
                break;
            }
        }
        in.close();
    }
}