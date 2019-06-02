package negocio.entidade;

public class VendaForm{
    private String cod;
    private String cpfFunc;
    private String cpfCli;
    private String data;
    private String parcelado;
    private String valor;
    //    private String carrinho ???

    public VendaForm(String cod, String cpfFunc, String cpfCli, String data, String parcelado, String valor) {
        this.cod = cod;
        this.cpfFunc = cpfFunc;
        this.cpfCli = cpfCli;
        this.data = data;
        this.parcelado = parcelado;
        this.valor = valor;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getCpfFunc() {
        return cpfFunc;
    }

    public void setCpfFunc(String cpfFunc) {
        this.cpfFunc = cpfFunc;
    }

    public String getCpfCli() {
        return cpfCli;
    }

    public void setCpfCli(String cpfCli) {
        this.cpfCli = cpfCli;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getParcelado() {
        return parcelado;
    }

    public void setParcelado(String parcelado) {
        this.parcelado = parcelado;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}