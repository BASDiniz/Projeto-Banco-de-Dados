package negocio.excecao.produto;

public class DescricaoTamanhoException extends ProdutoInvalidoException {
    
    public DescricaoTamanhoException() {
        super("A decriçãoo não pode ser tão pequena");
    }
}
