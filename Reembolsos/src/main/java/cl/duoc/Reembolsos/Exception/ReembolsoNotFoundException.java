package cl.duoc.Reembolsos.Exception;

public class ReembolsoNotFoundException extends RuntimeException {

    public ReembolsoNotFoundException(Integer id) {
        super("Reembolso no encontrado con id: " + id);
    }
}
