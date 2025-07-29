package com.algaworks;

/**
 * Enum que representa as categorias de contatos disponíveis no sistema.
 * Utiliza conceitos avançados de Java como enum com métodos e propriedades.
 */
public enum Categoria {
    
    FAMILIA("Família", "👨‍👩‍👧‍👦"),
    TRABALHO("Trabalho", "💼"),
    AMIGOS("Amigos", "👥"),
    FACULDADE("Faculdade", "🎓"),
    ACADEMIA("Academia", "🏋️"),
    MEDICO("Médico", "👨‍⚕️"),
    DENTISTA("Dentista", "🦷"),
    ADVOGADO("Advogado", "⚖️"),
    OUTROS("Outros", "📞");
    
    private final String descricao;
    private final String emoji;
    
    Categoria(String descricao, String emoji) {
        this.descricao = descricao;
        this.emoji = emoji;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public String getEmoji() {
        return emoji;
    }
    
    @Override
    public String toString() {
        return emoji + " " + descricao;
    }
    
    /**
     * Busca uma categoria pelo nome da descrição (case-insensitive)
     */
    public static Categoria fromDescricao(String descricao) {
        if (descricao == null) return OUTROS;
        
        for (Categoria categoria : values()) {
            if (categoria.descricao.equalsIgnoreCase(descricao)) {
                return categoria;
            }
        }
        return OUTROS;
    }
}