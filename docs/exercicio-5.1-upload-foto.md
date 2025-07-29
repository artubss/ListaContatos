# Exercício 5.1: Adicionando Foto ao Contato

**Objetivo:**
Permitir que o usuário faça upload de uma foto para cada contato.

**Conceitos treinados:**

- Upload de arquivos com Spring MVC
- Manipulação de arquivos no Java
- Exibição de imagens com Thymeleaf

## Enunciado

Implemente a funcionalidade de upload de foto no cadastro de contatos. A foto deve ser exibida na listagem.

## Passo a passo e Resolução

### 1. No formulário (`formulario.html`), adicione o campo de upload:

```html
<form ... enctype="multipart/form-data">
  <!-- outros campos -->
  <input type="file" name="foto" accept="image/*" />
  <button type="submit">Salvar</button>
</form>
```

### 2. No controller, receba o arquivo:

```java
@PostMapping("/contatos")
public String salvar(@Valid Contato contato, BindingResult result, @RequestParam("foto") MultipartFile foto, Model model) {
    if (!foto.isEmpty()) {
        String caminho = "caminho/para/salvar/" + foto.getOriginalFilename();
        foto.transferTo(new File(caminho));
        contato.setCaminhoFoto(caminho);
    }
    // salvar contato normalmente
    return "redirect:/contatos";
}
```

### 3. Exiba a foto na listagem (`listar.html`):

```html
<img th:src="@{'/imagens/' + ${contato.caminhoFoto}}" alt="Foto" width="50" />
```

### 4. Configure um endpoint para servir imagens, se necessário.

Assim, você treina upload e manipulação de arquivos no Spring.
