package br.com.cloudtech.api_biblioteca.service;

import br.com.cloudtech.api_biblioteca.model.Livro;
import br.com.cloudtech.api_biblioteca.repository.LivroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro cadastrar(Livro livro) {
        livro.setId(null);
        return livroRepository.save(livro);
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Livro não encontrado"
                ));
    }

    public Livro alterar(Long id, Livro dadosAtualizados) {
        Livro livroExistente = buscarPorId(id);

        livroExistente.setTitulo(dadosAtualizados.getTitulo());
        livroExistente.setAutor(dadosAtualizados.getAutor());
        livroExistente.setEditora(dadosAtualizados.getEditora());
        livroExistente.setAno(dadosAtualizados.getAno());
        livroExistente.setCategoria(dadosAtualizados.getCategoria());
        livroExistente.setStatus(dadosAtualizados.getStatus());

        return livroRepository.save(livroExistente);
    }

    public void excluir(Long id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }
}
