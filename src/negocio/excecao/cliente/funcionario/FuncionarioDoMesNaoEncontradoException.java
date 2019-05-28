package negocio.excecao.cliente.funcionario;

public class FuncionarioDoMesNaoEncontradoException extends Exception {
    public FuncionarioDoMesNaoEncontradoException(){
        super("Esse mês ainda não possui um funcionário do mês");
    }
}
