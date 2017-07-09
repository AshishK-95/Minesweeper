//Alek Hadzidedic (8237614) and Ashish Khiani (8385008)

public interface Queue<E> {
    public abstract boolean isEmpty();
    public abstract void enqueue( E o );
    public abstract E dequeue();
}
