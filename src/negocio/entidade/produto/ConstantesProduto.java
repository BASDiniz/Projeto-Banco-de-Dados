package negocio.entidade.produto;

import java.util.ArrayList;
import java.util.Arrays;

public final class ConstantesProduto {

    public static final ArrayList OPCOES_TIPO_DE_ROUPA = new ArrayList(Arrays.asList("camisa", "saia", "moleton", "vestido", "calça",
            "terno")); // fica grande n?!
    public static final ArrayList OPCOES_CATEGORIA = new ArrayList
            (Arrays.asList("dormir" , "praia", "academia", "festa", "trabalho", "passeio", "casual"));
    public static final ArrayList OPCOES_FAIXA_ETARIA = new ArrayList(Arrays.asList("infantil", "juvenil", "adulto"));
    public static final ArrayList OPCOES_GENERO = new ArrayList(Arrays.asList("masculino", "feminino", "unissex"));
    //public static final ArrayList OPCOES_COR = new ArrayList(Arrays.asList("preto", "azul", "verde")); // ?
    public static final ArrayList OPCOES_TAMANHO = new ArrayList(Arrays.asList("pp", "p", "m", "g", "gg"));
}