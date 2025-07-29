# API REST - Sistema de Contatos

## Visão Geral

Esta documentação descreve a API REST implementada para o sistema de gerenciamento de contatos. A API fornece endpoints para operações CRUD completas, busca, filtros e estatísticas.

## Base URL

```
http://localhost:3000/api/contatos
```

## Formato de Resposta

Todas as respostas seguem o formato padrão:

```json
{
  "success": true,
  "message": "Mensagem descritiva",
  "data": {},
  "timestamp": 1640995200000
}
```

## Endpoints

### 1. Listar Todos os Contatos

**GET** `/api/contatos`

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Contatos listados com sucesso",
  "data": [
    {
      "id": 1,
      "nome": "João Silva",
      "telefone": "(11) 99999-9999",
      "email": "joao@email.com",
      "endereco": "Rua A, 123",
      "dataNascimento": "1990-05-15",
      "categoria": "FAMILIA",
      "favorito": true,
      "dataCriacao": "2024-01-01T10:00:00",
      "dataAtualizacao": "2024-01-01T10:00:00"
    }
  ],
  "timestamp": 1640995200000
}
```

### 2. Buscar Contato por ID

**GET** `/api/contatos/{id}`

**Parâmetros:**

- `id` (Long): ID do contato

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Contato encontrado",
  "data": {
    "id": 1,
    "nome": "João Silva",
    "telefone": "(11) 99999-9999",
    "email": "joao@email.com",
    "endereco": "Rua A, 123",
    "dataNascimento": "1990-05-15",
    "categoria": "FAMILIA",
    "favorito": true,
    "dataCriacao": "2024-01-01T10:00:00",
    "dataAtualizacao": "2024-01-01T10:00:00"
  },
  "timestamp": 1640995200000
}
```

**Resposta de Erro (404):**

```json
{
  "success": false,
  "message": "Contato não encontrado",
  "data": null,
  "timestamp": 1640995200000
}
```

### 3. Criar Novo Contato

**POST** `/api/contatos`

**Corpo da Requisição:**

```json
{
  "nome": "Maria Santos",
  "telefone": "(11) 88888-8888",
  "email": "maria@email.com",
  "endereco": "Av B, 456",
  "dataNascimento": "1985-08-20",
  "categoria": "TRABALHO",
  "favorito": false
}
```

**Resposta de Sucesso (201):**

```json
{
  "success": true,
  "message": "Contato criado com sucesso",
  "data": {
    "id": 6,
    "nome": "Maria Santos",
    "telefone": "(11) 88888-8888",
    "email": "maria@email.com",
    "endereco": "Av B, 456",
    "dataNascimento": "1985-08-20",
    "categoria": "TRABALHO",
    "favorito": false,
    "dataCriacao": "2024-01-01T10:00:00",
    "dataAtualizacao": "2024-01-01T10:00:00"
  },
  "timestamp": 1640995200000
}
```

**Resposta de Erro (400):**

```json
{
  "success": false,
  "message": "Dados inválidos",
  "data": null,
  "timestamp": 1640995200000
}
```

### 4. Atualizar Contato

**PUT** `/api/contatos/{id}`

**Parâmetros:**

- `id` (Long): ID do contato

**Corpo da Requisição:**

```json
{
  "nome": "João Silva Atualizado",
  "telefone": "(11) 99999-9999",
  "email": "joao.novo@email.com",
  "endereco": "Rua A, 123 - Atualizado",
  "dataNascimento": "1990-05-15",
  "categoria": "FAMILIA",
  "favorito": true
}
```

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Contato atualizado com sucesso",
  "data": {
    "id": 1,
    "nome": "João Silva Atualizado",
    "telefone": "(11) 99999-9999",
    "email": "joao.novo@email.com",
    "endereco": "Rua A, 123 - Atualizado",
    "dataNascimento": "1990-05-15",
    "categoria": "FAMILIA",
    "favorito": true,
    "dataCriacao": "2024-01-01T10:00:00",
    "dataAtualizacao": "2024-01-01T11:00:00"
  },
  "timestamp": 1640995200000
}
```

### 5. Remover Contato

**DELETE** `/api/contatos/{id}`

**Parâmetros:**

- `id` (Long): ID do contato

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Contato removido com sucesso",
  "data": null,
  "timestamp": 1640995200000
}
```

### 6. Alternar Status de Favorito

**POST** `/api/contatos/{id}/favorito`

**Parâmetros:**

- `id` (Long): ID do contato

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Status de favorito alterado",
  "data": {
    "id": 1,
    "nome": "João Silva",
    "favorito": true
  },
  "timestamp": 1640995200000
}
```

### 7. Buscar por Texto

**GET** `/api/contatos/busca?q={texto}`

**Parâmetros:**

- `q` (String): Texto para busca (nome, telefone, email, endereço)

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Busca realizada com sucesso",
  "data": [
    {
      "id": 1,
      "nome": "João Silva",
      "telefone": "(11) 99999-9999",
      "email": "joao@email.com"
    }
  ],
  "timestamp": 1640995200000
}
```

### 8. Listar Favoritos

**GET** `/api/contatos/favoritos`

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Favoritos listados com sucesso",
  "data": [
    {
      "id": 1,
      "nome": "João Silva",
      "favorito": true
    }
  ],
  "timestamp": 1640995200000
}
```

### 9. Listar por Categoria

**GET** `/api/contatos/categoria/{categoria}`

**Parâmetros:**

- `categoria` (String): Nome da categoria (FAMILIA, TRABALHO, AMIGOS, etc.)

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Contatos da categoria listados",
  "data": [
    {
      "id": 1,
      "nome": "João Silva",
      "categoria": "FAMILIA"
    }
  ],
  "timestamp": 1640995200000
}
```

### 10. Obter Estatísticas

**GET** `/api/contatos/estatisticas`

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Estatísticas obtidas com sucesso",
  "data": {
    "total": 5,
    "favoritos": 3,
    "mediaIdade": 32.5,
    "contagemPorCategoria": {
      "FAMILIA": 2,
      "TRABALHO": 1,
      "AMIGOS": 1,
      "FACULDADE": 1
    }
  },
  "timestamp": 1640995200000
}
```

### 11. Listar Categorias

**GET** `/api/categorias`

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Categorias listadas com sucesso",
  "data": [
    {
      "name": "FAMILIA",
      "descricao": "Família",
      "emoji": "👨‍👩‍👧‍👦"
    },
    {
      "name": "TRABALHO",
      "descricao": "Trabalho",
      "emoji": "💼"
    }
  ],
  "timestamp": 1640995200000
}
```

### 12. Buscar com Filtros Avançados

**POST** `/api/contatos/filtros`

**Corpo da Requisição:**

```json
{
  "nome": "João",
  "categoria": "FAMILIA",
  "favorito": true,
  "dataNascimentoInicio": "1980-01-01",
  "dataNascimentoFim": "2000-12-31"
}
```

**Resposta de Sucesso (200):**

```json
{
  "success": true,
  "message": "Filtro aplicado com sucesso",
  "data": [
    {
      "id": 1,
      "nome": "João Silva",
      "categoria": "FAMILIA",
      "favorito": true,
      "dataNascimento": "1990-05-15"
    }
  ],
  "timestamp": 1640995200000
}
```

## Códigos de Status HTTP

- **200 OK**: Operação realizada com sucesso
- **201 Created**: Recurso criado com sucesso
- **400 Bad Request**: Dados inválidos ou erro de validação
- **404 Not Found**: Recurso não encontrado
- **500 Internal Server Error**: Erro interno do servidor

## Validações

### Campos Obrigatórios

- `nome`: String (2-100 caracteres, apenas letras e espaços)
- `telefone`: String (formato brasileiro: (11) 99999-9999)

### Campos Opcionais

- `email`: String (formato de email válido, máximo 255 caracteres)
- `endereco`: String (máximo 500 caracteres)
- `dataNascimento`: Date (deve ser no passado, máximo 150 anos atrás)
- `categoria`: Enum (FAMILIA, TRABALHO, AMIGOS, FACULDADE, ACADEMIA, MEDICO, DENTISTA, ADVOGADO, OUTROS)
- `favorito`: Boolean

### Validações Especiais

- Email é obrigatório para contatos de trabalho
- Telefone deve ter pelo menos 10 dígitos
- Data de nascimento deve ser razoável (não mais de 150 anos atrás)

## Exemplos de Uso

### Criar um novo contato

```bash
curl -X POST http://localhost:3000/api/contatos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Maria Santos",
    "telefone": "(11) 88888-8888",
    "email": "maria@email.com",
    "categoria": "TRABALHO",
    "favorito": false
  }'
```

### Buscar contatos por texto

```bash
curl "http://localhost:3000/api/contatos/busca?q=João"
```

### Alternar favorito

```bash
curl -X POST http://localhost:3000/api/contatos/1/favorito
```

### Obter estatísticas

```bash
curl http://localhost:3000/api/contatos/estatisticas
```

## Logs

A API registra logs detalhados de todas as operações:

- Logs de INFO para operações principais
- Logs de DEBUG para detalhes de execução
- Logs de WARN para situações de atenção
- Logs de ERROR para erros e exceções

Os logs são salvos em arquivos separados:

- `logs/algafood-api.log`: Logs gerais
- `logs/api.log`: Logs específicos da API
- `logs/error.log`: Logs de erro
