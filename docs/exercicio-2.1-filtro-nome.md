# Exercício 2.1: Filtro por Nome

**Objetivo:**
Permitir que o usuário filtre a lista de contatos pelo nome.

**Conceitos treinados:**

- Manipulação de listas no Java
- Uso de parâmetros de requisição no Spring MVC
- Thymeleaf para exibir resultados filtrados

## Enunciado

Implemente um campo de busca na tela de listagem de contatos. O usuário poderá digitar parte do nome e ver apenas os contatos correspondentes.

## Passo a passo e Resolução

### 1. Adicione um campo de busca no template `listar.html`:

```html
<form method="get" action="/contatos">
  <input type="text" name="nome" placeholder="Buscar por nome" />
  <button type="submit">Buscar</button>
</form>
```

### 2. No controller, receba o parâmetro e filtre:

```java
@GetMapping("/contatos")
public String listar(@RequestParam(required = false) String nome, Model model) {
    List<Contato> contatos;
    if (nome != null && !nome.isEmpty()) {
        contatos = contatoRepository.findByNomeContainingIgnoreCase(nome);
    } else {
        contatos = contatoRepository.findAll();
    }
    model.addAttribute("contatos", contatos);
    return "listar";
}
```

### 3. No repositório, crie o método de busca:

```java
List<Contato> findByNomeContainingIgnoreCase(String nome);
```

Se usar Spring Data JPA, basta declarar na interface.

### 4. Teste

Digite parte do nome e veja a lista ser filtrada.

Assim, você treina manipulação de listas, parâmetros de requisição e integração frontend-backend.
