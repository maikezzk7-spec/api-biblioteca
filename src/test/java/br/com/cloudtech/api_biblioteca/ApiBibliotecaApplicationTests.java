package br.com.cloudtech.api_biblioteca;

import br.com.cloudtech.api_biblioteca.model.Livro;
import br.com.cloudtech.api_biblioteca.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApiBibliotecaApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void limparBancoDeDados() {
        livroRepository.deleteAll();
    }

    @Test
    void paginaInicialDeveRetornarStatus200() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").exists());
    }

    @Test
    void listarLivrosDeveRetornarLista() throws Exception {
        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void cadastrarLivroDeveRetornarStatus201() throws Exception {
        Livro livro = criarLivro("Introdução à Programação");

        mockMvc.perform(post("/livros/cadastrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(livro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.titulo").value("Introdução à Programação"));
    }

    @Test
    void buscarLivroPorIdDeveRetornarSeusDados() throws Exception {
        Livro livroSalvo = livroRepository.save(
                criarLivro("Redes de Computadores")
        );

        mockMvc.perform(get("/livros/{id}", livroSalvo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Redes de Computadores"));
    }

    @Test
    void alterarLivroDeveRetornarDadosAtualizados() throws Exception {
        Livro livroSalvo = livroRepository.save(
                criarLivro("Título antigo")
        );
        Livro dadosAtualizados = criarLivro("Título atualizado");

        mockMvc.perform(put("/livros/alterar/{id}", livroSalvo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dadosAtualizados)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Título atualizado"));
    }

    @Test
    void buscarLivroInexistenteDeveRetornarStatus404() throws Exception {
        mockMvc.perform(get("/livros/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void documentacaoDeveRetornarListaDeEndpoints() throws Exception {
        mockMvc.perform(get("/documentacao"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.endpoints").isArray())
                .andExpect(jsonPath("$.endpoints[0]").value("/"));
    }

    private Livro criarLivro(String titulo) {
        Livro livro = new Livro();
        livro.setTitulo(titulo);
        livro.setAutor("Autor de teste");
        livro.setEditora("Editora de teste");
        livro.setAno(2024);
        livro.setCategoria("Tecnologia");
        livro.setStatus("Disponível");
        return livro;
    }
}