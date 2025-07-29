package com.algaworks;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "Nome deve conter apenas letras e espaços")
    private String nome;

    @NotBlank(message = "Telefone é obrigatório")
    @Pattern(regexp = "^\\(?[1-9]{2}\\)? ?(?:[2-8]|9[1-9])[0-9]{3}\\-?[0-9]{4}$", 
             message = "Telefone deve estar no formato (11) 99999-9999 ou 11999999999")
    private String telefone;

    @Email(message = "Email deve ser válido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    private String email;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    private String endereco;

    @Past(message = "Data de nascimento deve ser no passado")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Categoria categoria = Categoria.OUTROS;

    private boolean favorito = false;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAtualizacao;

    private boolean novo;

    // Construtor padrão
    public Contato() {
        this.dataCriacao = LocalDateTime.now();
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Construtor com parâmetros
    public Contato(Long id, String nome, String telefone) {
        this();
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.novo = true;
    }

    // Construtor completo
    public Contato(Long id, String nome, String telefone, String email,
            String endereco, LocalDate dataNascimento, Categoria categoria, boolean favorito) {
        this(id, nome, telefone);
        this.email = email;
        this.endereco = endereco;
        this.dataNascimento = dataNascimento;
        this.categoria = categoria != null ? categoria : Categoria.OUTROS;
        this.favorito = favorito;
    }

    // Validação customizada para verificar se a data de nascimento é razoável
    @AssertTrue(message = "Data de nascimento deve ser anterior a 150 anos atrás")
    public boolean isDataNascimentoValida() {
        if (dataNascimento == null) {
            return true; // Campo opcional
        }
        LocalDate dataMinima = LocalDate.now().minusYears(150);
        return dataNascimento.isAfter(dataMinima);
    }

    // Validação customizada para verificar se o email é obrigatório para contatos de trabalho
    @AssertTrue(message = "Email é obrigatório para contatos de trabalho")
    public boolean isEmailObrigatorioParaTrabalho() {
        if (categoria == Categoria.TRABALHO && (email == null || email.trim().isEmpty())) {
            return false;
        }
        return true;
    }

    // Validação customizada para verificar se o telefone não é muito antigo
    @AssertTrue(message = "Telefone deve ter pelo menos 10 dígitos")
    public boolean isTelefoneValido() {
        if (telefone == null) {
            return false;
        }
        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");
        return telefoneLimpo.length() >= 10 && telefoneLimpo.length() <= 11;
    }

    // Métodos de negócio
    public boolean isNovo() {
        return id == null;
    }

    public String getNomeCompleto() {
        return nome;
    }

    public String getDataNascimentoFormatada() {
        return dataNascimento != null
                ? dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "";
    }

    public String getDataCriacaoFormatada() {
        return dataCriacao != null
                ? dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
    }

    public String getDataAtualizacaoFormatada() {
        return dataAtualizacao != null
                ? dataAtualizacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "";
    }

    public int getIdade() {
        if (dataNascimento == null) {
            return 0;
        }
        return LocalDate.now().getYear() - dataNascimento.getYear();
    }

    public void marcarComoFavorito() {
        this.favorito = true;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void desmarcarComoFavorito() {
        this.favorito = false;
        this.dataAtualizacao = LocalDateTime.now();
    }

    public void atualizarDataModificacao() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        atualizarDataModificacao();
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
        atualizarDataModificacao();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        atualizarDataModificacao();
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
        atualizarDataModificacao();
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        atualizarDataModificacao();
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria != null ? categoria : Categoria.OUTROS;
        atualizarDataModificacao();
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
        atualizarDataModificacao();
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public void setNovo(boolean novo) {
        this.novo = novo;
    }

    // equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contato contato = (Contato) o;
        return Objects.equals(id, contato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Contato{"
                + "id=" + id
                + ", nome='" + nome + '\''
                + ", telefone='" + telefone + '\''
                + ", email='" + email + '\''
                + ", categoria=" + categoria
                + ", favorito=" + favorito
                + '}';
    }
}
