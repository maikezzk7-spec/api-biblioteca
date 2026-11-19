package br.com.cloudtech.api_biblioteca.repository;

import br.com.cloudtech.api_biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
