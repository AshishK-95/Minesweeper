//Alek Hadzidedic (8237614) and Ashish Khiani (8385008)

public class LinkedQueue<E> implements Queue<E> {

    private static class Elem<T> {

        private T value;
        private Elem<T> next;

        private Elem( T value, Elem<T> next ) {
            this.value = value;
            this.next = next;
        }
    }

    private Elem<E> front;
    private Elem<E> rear;

    public E peek() {
        if (this.isEmpty()) {
            throw new EmptyQueueException("Empty Queue");
        }

        return front.value;
    }

    public void enqueue( E o ) {

        if (o == null) {
            throw new NullPointerException ("Unable to enqueue elements referring to <null> into the queue");
        }

        Elem<E> newElem;
        newElem = new Elem<E>( o, null );

        if ( rear == null ) {
            front = rear = newElem;
        } else {
            rear.next = newElem;
            rear = newElem;
        }
    }

    public E dequeue() {

        if (this.isEmpty()) {
            throw new EmptyQueueException("Empty Queue");
        }

        E result = front.value;
        if ( front != null & front.next == null ) {
            front = rear = null;
        } else {
            front = front.next;
        }
        return result;
    }

    public boolean isEmpty() {
        return front == null;
    }

}
