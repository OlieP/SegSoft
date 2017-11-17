package model.ast;

public class Assign_node extends Statement_node{

	private Expression_node left_node;
	private Expression_node right_node;

	public Assign_node(String kind, String left_name) {
		super(kind);
		left_node = null;
		right_node = null;
	}

	public Expression_node  getLeft_var_node() {
		return left_node;
	}

	public void setLeft_var_name(Expression_node left_node) {
		this.left_node = left_node;
	}

	public Expression_node getRight_side() {
		return right_node;
	}

	public void setRight_side(Expression_node right_side) {
		this.right_node= right_side;
	}

	public String toString() {
		return "Left: \n" + left_node.toString() + "\n right: \n"+ right_node.toString();
	}
}
