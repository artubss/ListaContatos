# Exercício 1.2: Validação no Frontend

**Objetivo:**
Adicionar validação de dados no frontend usando recursos do HTML5.

**Conceitos treinados:**

- Atributos de validação HTML5 (`required`, `type`, `pattern`)
- Experiência do usuário

## Enunciado

Implemente validação nos campos do formulário para que o nome e o e-mail sejam obrigatórios e o e-mail esteja em formato válido, antes mesmo de enviar para o backend.

## Passo a passo e Resolução

### 1. No arquivo `formulario.html`, adicione os atributos de validação:

```html
<form ...>
  <input type="text" name="nome" required placeholder="Nome" />
  <input type="email" name="email" required placeholder="E-mail" />
  <input
    type="text"
    name="telefone"
    pattern="\\d{10,11}"
    placeholder="Telefone (apenas números)"
  />
  <!-- ... outros campos ... -->
  <button type="submit">Salvar</button>
</form>
```

- `required` obriga o preenchimento.
- `type="email"` obriga formato de e-mail válido.
- `pattern` pode ser usado para validar o telefone.

### 2. Teste o formulário

Tente enviar sem preencher os campos ou com e-mail inválido. O navegador impedirá o envio e mostrará mensagens automáticas.

Assim, o usuário recebe feedback imediato, melhorando a experiência e reduzindo erros enviados ao backend.
