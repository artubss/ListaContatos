# Exercício 6.1: Login Simples

**Objetivo:**
Adicionar autenticação simples à aplicação, protegendo o acesso à lista de contatos.

**Conceitos treinados:**

- Spring Security (configuração básica)
- Controle de sessão

## Enunciado

Implemente uma tela de login. Apenas usuários autenticados poderão acessar a lista de contatos.

## Passo a passo e Resolução

### 1. Adicione a dependência do Spring Security no `pom.xml`:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 2. Crie uma configuração de segurança:

```java
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/login", "/css/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login").permitAll()
            .and()
            .logout().permitAll();
    }
}
```

### 3. Crie o template de login (`login.html`):

```html
<form method="post" action="/login">
  <input type="text" name="username" placeholder="Usuário" required />
  <input type="password" name="password" placeholder="Senha" required />
  <button type="submit">Entrar</button>
</form>
```

### 4. Teste

Ao acessar `/contatos`, o usuário não autenticado será redirecionado para o login.

Assim, você treina autenticação e proteção de rotas com Spring Security.
