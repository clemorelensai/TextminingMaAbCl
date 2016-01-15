package Arbre;


public abstract class ChildNode {

	public TypeChile type;
	
	public boolean isTerminale(){
		return (this instanceof FeuilleTerminale)?true:false;
	}
	
	protected ChildNode(TypeChile type){this.type=type;} 
}

enum TypeChile{F,T}
