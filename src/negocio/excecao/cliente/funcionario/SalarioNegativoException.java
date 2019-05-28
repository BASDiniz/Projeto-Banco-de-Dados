package negocio.excecao.cliente.funcionario;

import negocio.excecao.cliente.DadosInvalidosException;

public class SalarioNegativoException extends DadosInvalidosException {
    public SalarioNegativoException(){
        super("O salário não pode ser um número negativo");
    }
}
