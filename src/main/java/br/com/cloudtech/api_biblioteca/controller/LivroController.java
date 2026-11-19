package br.com.cloudtech.api_biblioteca.controller;

import br.com.cloudtech.api_biblioteca.model.Livro;
import br.com.cloudtech.api_biblioteca.service.LivroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping("/")
    public Map<String, String> paginaInicial() {
        return Map.of(
                "mensagem",
                "Bem-vindo ao sistema de gerenciamento da Biblioteca CloudTech Solutions"
        );
    }

    @GetMapping("/livros")
    public List<Livro> listarLivros() {
        return livroService.listarTodos();
    }

    @PostMapping("/livros/cadastrar")
    public ResponseEntity<Livro> cadastrarLivro(@RequestBody Livro livro) {
        Livro livroCadastrado = livroService.cadastrar(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroCadastrado);
    }

    @GetMapping("/livros/{id}")
    public Livro buscarLivroPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }

    @PutMapping("/livros/alterar/{id}")
    public Livro alterarLivro(
            @PathVariable Long id,
            @RequestBody Livro dadosAtualizados
    ) {
        return livroService.alterar(id, dadosAtualizados);
    }

    @DeleteMapping("/livros/{id}")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/documentacao")
    public Map<String, List<String>> documentacao() {
        return Map.of("endpoints", List.of(
                "/",
                "/livros",
                "/livros/cadastrar",
                "/livros/alterar/{id}",
                "/livros/{id}",
                "/documentacao"
        ));
    }
}
