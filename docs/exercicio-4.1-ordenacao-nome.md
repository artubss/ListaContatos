# Exercício 4.1: Ordenação por Nome

**Objetivo:**
Permitir que o usuário ordene a lista de contatos pelo nome.

**Conceitos treinados:**

- Ordenação de listas no Java
- Uso de parâmetros de requisição para ordenação
- Integração com Spring Data

## Enunciado

Implemente a funcionalidade de ordenação para que o usuário possa clicar em um botão e ver a lista de contatos ordenada por nome (ascendente ou descendente).

## Passo a passo e Resolução

### 1. Adicione um botão de ordenação no template `listar.html`:

```html
<a th:href="@{'/contatos?sort=nome,' + (${ordem} == 'asc' ? 'desc' : 'asc')}">
  Ordenar por nome
</a>
```

### 2. No controller, use o parâmetro de ordenação:

```java
@GetMapping("/contatos")
public String listar(@RequestParam(required = false) String nome, @RequestParam(defaultValue = "asc") String ordem, Model model) {
    Sort sort = ordem.equals("desc") ? Sort.by("nome").descending() : Sort.by("nome").ascending();
    List<Contato> contatos;
    if (nome != null && !nome.isEmpty()) {
        contatos = contatoRepository.findByNomeContainingIgnoreCase(nome, sort);
    } else {
        contatos = contatoRepository.findAll(sort);
    }
    model.addAttribute("contatos", contatos);
    model.addAttribute("ordem", ordem);
    return "listar";
}
```

### 3. No repositório, ajuste os métodos:

```java
List<Contato> findByNomeContainingIgnoreCase(String nome, Sort sort);
List<Contato> findAll(Sort sort);
```

Agora, o usuário pode alternar a ordenação clicando no botão, treinando manipulação de listas, parâmetros e integração com Spring Data.
