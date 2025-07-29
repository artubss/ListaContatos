# AlgaWorks Contatos - Sistema Completo de Gerenciamento

## 📋 Visão Geral

Sistema completo de gerenciamento de contatos desenvolvido em Spring Boot com funcionalidades avançadas, validações robustas, sistema de logs e APIs REST.

## 🚀 Funcionalidades Implementadas

### ✅ Funcionalidades Básicas
- **CRUD Completo**: Criar, listar, editar e remover contatos
- **Interface Web**: Interface responsiva com Thymeleaf
- **Navegação Intuitiva**: Menu de navegação com todas as funcionalidades

### ✅ Sistema de Categorias
- **9 Categorias**: Família, Trabalho, Amigos, Faculdade, Academia, Médico, Dentista, Advogado, Outros
- **Emojis Visuais**: Cada categoria possui emoji para melhor identificação
- **Filtro por Categoria**: Busca e filtro específico por categoria

### ✅ Campos Avançados
- **Nome**: Validação de tamanho (2-100 caracteres) e formato (apenas letras)
- **Telefone**: Validação de formato brasileiro ((11) 99999-9999)
- **Email**: Validação de formato e tamanho máximo (255 caracteres)
- **Endereço**: Campo de texto com limite de 500 caracteres
- **Data de Nascimento**: Validação de data no passado
- **Categoria**: Seleção obrigatória com enum
- **Favorito**: Checkbox para marcar contatos favoritos
- **Datas de Criação/Atualização**: Automáticas

### ✅ Busca e Filtros Avançados
- **Busca por Texto**: Pesquisa em nome, telefone, email e endereço
- **Filtro por Categoria**: Botões para filtrar por categoria específica
- **Filtro de Favoritos**: Lista apenas contatos favoritos
- **Filtros Avançados**: Por data de nascimento, categoria, favorito
- **Builder Pattern**: Implementação elegante de filtros usando Builder

### ✅ Contatos Favoritos
- **Marcar/Desmarcar**: Toggle de status de favorito
- **Visualização**: Destaque visual para contatos favoritos
- **Lista Separada**: Endpoint específico para favoritos

### ✅ Validações Avançadas
- **Validações de Campo**:
  - Nome: 2-100 caracteres, apenas letras e espaços
  - Telefone: Formato brasileiro válido
  - Email: Formato válido, máximo 255 caracteres
  - Endereço: Máximo 500 caracteres
  - Data de Nascimento: Deve ser no passado, máximo 150 anos atrás

- **Validações Customizadas**:
  - Email obrigatório para contatos de trabalho
  - Telefone deve ter 10-11 dígitos
  - Data de nascimento deve ser razoável

- **Validações Condicionais**:
  - Regras específicas por categoria
  - Validações cruzadas entre campos

### ✅ Sistema de Logs Avançado
- **Logback Configuration**: Configuração completa de logs
- **Múltiplos Appenders**:
  - Console: Logs em tempo real
  - Arquivo geral: `logs/algafood-api.log`
  - Arquivo de API: `logs/api.log`
  - Arquivo de erros: `logs/error.log`
- **Rolling Policy**: Rotação automática de logs (10MB, 30 dias)
- **Níveis de Log**: DEBUG, INFO, WARN, ERROR
- **Logs Específicos**: Por classe e funcionalidade

### ✅ APIs REST Completas
- **12 Endpoints REST**:
  - `GET /api/contatos` - Listar todos
  - `GET /api/contatos/{id}` - Buscar por ID
  - `POST /api/contatos` - Criar novo
  - `PUT /api/contatos/{id}` - Atualizar
  - `DELETE /api/contatos/{id}` - Remover
  - `POST /api/contatos/{id}/favorito` - Alternar favorito
  - `GET /api/contatos/busca?q=texto` - Buscar por texto
  - `GET /api/contatos/favoritos` - Listar favoritos
  - `GET /api/contatos/categoria/{categoria}` - Por categoria
  - `GET /api/contatos/estatisticas` - Estatísticas
  - `GET /api/categorias` - Listar categorias
  - `POST /api/contatos/filtros` - Filtros avançados

- **Resposta Padronizada**:
  ```json
  {
    "success": true,
    "message": "Mensagem descritiva",
    "data": {},
    "timestamp": 1640995200000
  }
  ```

- **Códigos HTTP**: 200, 201, 400, 404, 500
- **CORS**: Configurado para aceitar requisições cross-origin
- **Validação**: Validação automática de dados de entrada

### ✅ Estatísticas e Relatórios
- **Estatísticas Gerais**: Total de contatos, favoritos, média de idade
- **Contagem por Categoria**: Distribuição de contatos por categoria
- **Cálculos Dinâmicos**: Usando Java Streams e Collectors

### ✅ Interface Moderna
- **Design Responsivo**: Adaptável a diferentes tamanhos de tela
- **Gradientes e Animações**: Interface moderna com CSS3
- **Feedback Visual**: Estados hover, focus e loading
- **Validação Visual**: Campos com erro destacados
- **Acessibilidade**: Labels, títulos e navegação por teclado

## 🛠️ Tecnologias Utilizadas

### Backend
- **Spring Boot 3.x**: Framework principal
- **Spring MVC**: Controllers web
- **Spring Validation**: Validações avançadas
- **Thymeleaf**: Template engine
- **SLF4J + Logback**: Sistema de logs
- **Maven**: Gerenciamento de dependências

### Java Concepts Avançados
- **Enums**: Categoria com propriedades e métodos
- **Streams API**: Filtros, mapeamentos e coletas
- **Optional**: Tratamento seguro de valores nulos
- **Builder Pattern**: ContatoFiltro com Builder
- **Predicates**: Filtros dinâmicos
- **Collections**: ArrayList, HashMap, List
- **Time API**: LocalDate, LocalDateTime
- **Service Layer**: Separação de responsabilidades

### Frontend
- **HTML5**: Estrutura semântica
- **CSS3**: Estilos modernos e responsivos
- **JavaScript**: Interações básicas
- **Thymeleaf**: Integração com Spring

## 📁 Estrutura do Projeto

```
algafood-api/
├── src/main/java/com/algaworks/
│   ├── AlgaWorksContatosApplication.java
│   ├── Contato.java                    # Modelo com validações
│   ├── ContatoService.java             # Lógica de negócio
│   ├── ContatosControle.java           # Controller Web
│   ├── ContatoRestController.java      # Controller REST
│   ├── ContatoFiltro.java             # Filtros com Builder
│   └── Categoria.java                  # Enum de categorias
├── src/main/resources/
│   ├── templates/                      # Templates Thymeleaf
│   ├── static/css/estilo.css          # Estilos CSS
│   ├── application.properties          # Configurações
│   └── logback-spring.xml             # Configuração de logs
├── docs/
│   └── API_REST_DOCUMENTATION.md      # Documentação da API
└── logs/                              # Arquivos de log
```

## 🚀 Como Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### Execução
```bash
# Clonar o repositório
git clone <repository-url>
cd algafood-api

# Executar com Maven
mvn spring-boot:run

# Ou compilar e executar
mvn clean package
java -jar target/algafood-api-0.0.1-SNAPSHOT.jar
```

### Acessos
- **Interface Web**: http://localhost:3000
- **API REST**: http://localhost:3000/api/contatos
- **Documentação**: http://localhost:3000/docs/API_REST_DOCUMENTATION.md

## 📊 Exemplos de Uso

### Interface Web
1. Acesse http://localhost:3000
2. Navegue pelo menu para listar, criar, editar contatos
3. Use os filtros e busca para encontrar contatos
4. Marque contatos como favoritos

### API REST
```bash
# Listar todos os contatos
curl http://localhost:3000/api/contatos

# Criar novo contato
curl -X POST http://localhost:3000/api/contatos \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "telefone": "(11) 99999-9999",
    "email": "joao@email.com",
    "categoria": "FAMILIA",
    "favorito": true
  }'

# Buscar por texto
curl "http://localhost:3000/api/contatos/busca?q=João"

# Obter estatísticas
curl http://localhost:3000/api/contatos/estatisticas
```

## 🔍 Logs e Monitoramento

### Arquivos de Log
- `logs/algafood-api.log`: Logs gerais da aplicação
- `logs/api.log`: Logs específicos das APIs REST
- `logs/error.log`: Logs de erro apenas

### Níveis de Log
- **DEBUG**: Detalhes de execução
- **INFO**: Operações principais
- **WARN**: Situações de atenção
- **ERROR**: Erros e exceções

## 🧪 Validações Implementadas

### Validações de Campo
- **Nome**: Regex para apenas letras e espaços
- **Telefone**: Regex para formato brasileiro
- **Email**: Validação de formato e tamanho
- **Data**: Validação de data no passado
- **Tamanhos**: Limites específicos por campo

### Validações de Negócio
- Email obrigatório para contatos de trabalho
- Telefone deve ter formato válido
- Data de nascimento deve ser razoável
- Categoria deve ser válida

## 📈 Melhorias Futuras

- [ ] Persistência com banco de dados
- [ ] Autenticação e autorização
- [ ] Upload de fotos de contatos
- [ ] Exportação de dados (CSV, PDF)
- [ ] Notificações em tempo real
- [ ] Testes automatizados
- [ ] Docker containerization
- [ ] CI/CD pipeline

## 🤝 Contribuição

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👨‍💻 Autor

Desenvolvido como projeto de estudo em Spring Boot com foco em conceitos avançados de Java e boas práticas de desenvolvimento.