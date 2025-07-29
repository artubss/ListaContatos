# Exercício 3.1: Paginação de Contatos

**Objetivo:**
Adicionar paginação à listagem de contatos.

**Conceitos treinados:**

- Spring Data Pageable
- Thymeleaf para navegação

## Enunciado

Implemente a funcionalidade de paginação para que a lista de contatos seja exibida em páginas, com botões para navegar entre elas.

## Passo a passo e Resolução

### 1. Altere o método do controller para usar Pageable:

```java
@GetMapping("/contatos")
public String listar(@RequestParam(required = false) String nome, Pageable pageable, Model model) {
    Page<Contato> pagina;
    if (nome != null && !nome.isEmpty()) {
        pagina = contatoRepository.findByNomeContainingIgnoreCase(nome, pageable);
    } else {
        pagina = contatoRepository.findAll(pageable);
    }
    model.addAttribute("pagina", pagina);
    return "listar";
}
```

### 2. No repositório, ajuste os métodos:

```java
Page<Contato> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
Page<Contato> findAll(Pageable pageable);
```

### 3. No template `listar.html`, adicione navegação:

```html
<div th:if="${pagina.totalPages > 1}">
  <ul>
    <li th:each="i : ${#numbers.sequence(0, pagina.totalPages-1)}">
      <a th:href="@{'/contatos?(page=' + i + ')'}" th:text="${i+1}"></a>
    </li>
  </ul>
</div>
```

Agora, a lista de contatos é paginada e você treina uso de Pageable e integração com Thymeleaf.
