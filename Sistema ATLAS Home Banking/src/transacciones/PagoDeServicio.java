package transacciones;

// Clase que representa un pago de servicio, hereda de Transaccion
public class PagoDeServicio extends Transaccion {
    private String tipoServicio; // Tipo de servicio pagado
    private String nombreDelProveedor; // Nombre del proveedor del servicio
    private String numeroDeFactura; // Número de factura del servicio

    // Constructor que recibe el monto, tipo de servicio, proveedor y factura
    public PagoDeServicio(double monto, String tipoServicio, String nombreDelProveedor, String numeroDeFactura) {
        super(monto);
        this.tipoServicio = tipoServicio;
        this.nombreDelProveedor = nombreDelProveedor;
        this.numeroDeFactura = numeroDeFactura;
    }

    // Metodo para ejecutar el pago de servicio
    @Override
    public void ejecutar() {
        System.out.println("Pago realizado a " + nombreDelProveedor + " por " + tipoServicio + ". Factura N°: " + numeroDeFactura);
    }

    // Getter para el tipo de servicio
    public String getTipoServicio() {
        return tipoServicio;
    }

    // Getter para el nombre del proveedor
    public String getNombreDelProveedor() {
        return nombreDelProveedor;
    }

    // Getter para el número de factura
    public String getNumeroDeFactura() {
        return numeroDeFactura;
    }
}