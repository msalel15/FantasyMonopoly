package domain;

// Main Observable Interface
public interface Observable {
	public void notifyObservers(String desc, Object obj1, Object obj2);

	public void addObserver(Observer o);

	public void removeObserver(Observer o);
}
