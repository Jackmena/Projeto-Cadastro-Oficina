package projeto.oficina.cadastro_cliente_carro.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Carro {
    
    @Id
    private String placa;
    private String marca;
    private String modelo;
    private String ano;

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public String getPlaca() {
        return placa;
    }
    public void setPlaca(String placa) {
        this.placa = placa;
    }
    public String getAno() {
        return ano;
    }
    public void setAno(String ano) {
        this.ano = ano;
    }

    


}
