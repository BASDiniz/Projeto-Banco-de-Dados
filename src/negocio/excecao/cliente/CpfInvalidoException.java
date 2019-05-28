package negocio.excecao.cliente;

public class CpfInvalidoException extends DadosInvalidosException {

    public CpfInvalidoException(String cpf) {
        super(cpf + " não é um CPF válido.");
    }
}
