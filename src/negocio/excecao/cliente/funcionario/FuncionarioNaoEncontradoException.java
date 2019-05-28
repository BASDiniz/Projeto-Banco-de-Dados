package negocio.excecao.cliente.funcionario;

public class FuncionarioNaoEncontradoException extends Exception{
    public FuncionarioNaoEncontradoException(){
        super("Nenhum funcionario foi encontrado");
    }

}
