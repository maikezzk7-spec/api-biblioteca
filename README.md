if (-not (Test-Path ".\pom.xml")) {
    throw "Não encontrei o pom.xml. Abra o terminal na pasta raiz api-biblioteca e execute novamente."
}

$caminhoReadme = ".\README.md"

if (Test-Path $caminhoReadme) {
    $resposta = Read-Host "O README.md já existe. Deseja substituí-lo? Digite S para confirmar"

    if ($resposta -cne "S") {
        Write-Host "Operação cancelada. O README.md existente foi mantido."
        return
    }
}

$conteudo = @'
# API Biblioteca CloudTech Solutions

API REST desenvolvida em Java e Spring Boot para gerenciar os livros do acervo da Biblioteca CloudTech Solutions.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA e Hibernate
- Banco de dados H2
- Maven
- JUnit 5 e MockMvc
- GitHub Actions para Integração Contínua
- Render para hospedagem

## Funcionalidades

A API permite cadastrar, listar, consultar, atualizar e excluir livros.

Cada livro possui os seguintes dados:

- `id`: identificador gerado pela aplicação;
- `titulo`: título do livro;
- `autor`: nome do autor;
- `editora`: editora responsável pela publicação;
- `ano`: ano de publicação;
- `categoria`: categoria do livro;
- `status`: situação do livro, como `Disponível` ou `Emprestado`.

## Endpoints

| Método | Endpoint | Descrição | Resposta esperada |
|---|---|---|---|
| GET | `/` | Exibe uma mensagem de boas-vindas | 200 |
| GET | `/livros` | Lista os livros cadastrados | 200 |
| POST | `/livros/cadastrar` | Cadastra um livro | 201 |
| GET | `/livros/{id}` | Consulta um livro pelo identificador | 200 ou 404 |
| PUT | `/livros/alterar/{id}` | Atualiza os dados de um livro | 200 ou 404 |
| DELETE | `/livros/{id}` | Exclui um livro | 204 ou 404 |
| GET | `/documentacao` | Lista os endpoints disponíveis | 200 |

### Exemplo de cadastro

Envie uma requisição `POST` para `/livros/cadastrar` com o corpo JSON:

    {
      "titulo": "Banco de Dados para Iniciantes",
      "autor": "Maria Souza",
      "editora": "EduTech",
      "ano": 2023,
      "categoria": "Informática",
      "status": "Disponível"
    }

O identificador `id` é gerado pela aplicação. Não é necessário enviá-lo no cadastro.

## Executar localmente

Pré-requisitos: Java 21 e Git.

No terminal, entre na pasta raiz do projeto — a pasta que contém o `pom.xml` — e execute:

    .\mvnw.cmd spring-boot:run

A API ficará disponível localmente em:

    http://localhost:8080

## Executar os testes

Na mesma pasta, execute:

    .\mvnw.cmd clean verify

Os testes automatizados usam JUnit 5 e MockMvc para verificar os endpoints e suas respostas HTTP.

## Integração Contínua

O projeto contém um workflow do GitHub Actions em `.github/workflows/ci.yml`. Ele é executado em alterações enviadas à branch `main` e em pull requests direcionados a essa branch. O workflow configura o Java, compila a aplicação e executa os testes automatizados com Maven.

## Deploy

A aplicação está hospedada no Render:

[https://api-biblioteca-1-h839.onrender.com](https://api-biblioteca-1-h839.onrender.com)

Exemplos de endpoints publicados:

- `/`
- `/livros`
- `/documentacao`

Em instâncias gratuitas do Render, o serviço pode ficar inativo após um período sem uso. A primeira requisição seguinte pode demorar mais para responder.

## Banco de dados

O projeto utiliza H2 em memória. Portanto, os dados cadastrados são temporários e podem ser apagados quando a aplicação reinicia.

## Testes manuais

Os endpoints podem ser testados pelo Thunder Client no Visual Studio Code. Para cada requisição, confira o método HTTP, a URL, o corpo JSON quando necessário e o código de resposta.
'@

$utf8SemBOM = New-Object System.Text.UTF8Encoding($false)
[System.IO.File]::WriteAllText(
    (Join-Path (Get-Location).Path "README.md"),
    $conteudo,
    $utf8SemBOM
)

Write-Host "README.md criado na raiz do projeto."