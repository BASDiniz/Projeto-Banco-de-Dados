package negocio.excecao.cliente.funcionario;

import negocio.excecao.cliente.DadosInvalidosException;

public class SalarioApenasNumerosException extends DadosInvalidosException {
    public SalarioApenasNumerosException() {
        super("O sálario deve conter apenas números.");
    }
}
