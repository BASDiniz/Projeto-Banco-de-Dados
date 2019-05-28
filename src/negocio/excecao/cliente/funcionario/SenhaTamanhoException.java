package negocio.excecao.cliente.funcionario;

import negocio.excecao.cliente.DadosInvalidosException;

public class SenhaTamanhoException extends DadosInvalidosException {
    public SenhaTamanhoException(){
        super("A senha deve ser maior que 4 digitos.");
    }
}
