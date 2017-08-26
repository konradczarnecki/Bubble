package konra.common;

public class Response <E> {

    private String status;
    private E item;

    public Response() {
    }

    public Response(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public E getItem() {
        return item;
    }

    public void setItem(E item) {
        this.item = item;
    }
}
