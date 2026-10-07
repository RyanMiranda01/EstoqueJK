package br.com.upa.estoque.exception;

public class MaterialNaoEncontradoException extends RuntimeException {


    public MaterialNaoEncontradoException(Long id) {
        super("Material não encontrado com o id: " + id);
    }
}
