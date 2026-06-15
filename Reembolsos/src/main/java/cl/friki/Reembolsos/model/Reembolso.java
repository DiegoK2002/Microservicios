package cl.friki.Reembolsos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reembolsos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reembolso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombreReembolso;

    @Column(nullable = false)
    private Integer monto;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String estado;

    @Column
    private String descripcion;
}