package negocio.excecao.cliente.funcionario;

public class FuncionarioInativoException extends Exception{
    public FuncionarioInativoException(){
        super("Funcionário inativo");
    }
}
