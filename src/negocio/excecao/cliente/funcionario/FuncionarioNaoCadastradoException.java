package negocio.excecao.cliente.funcionario;

public class FuncionarioNaoCadastradoException extends Exception {
    public FuncionarioNaoCadastradoException(){
        super("Funcionário não cadastrado.");
    }
}
