package com.algaworks;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Conversor personalizado para converter String para Categoria.
 * Permite que o Spring faça a conversão corretamente quando recebe
 * valores como "🦷 Dentista" ou "Dentista" do formulário.
 */
@Component
public class CategoriaConverter implements Converter<String, Categoria> {

    @Override
    public Categoria convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return Categoria.OUTROS;
        }

        // Remove emojis e espaços extras
        String textoLimpo = source.replaceAll("[\\p{So}]", "").trim();
        
        // Tenta converter usando o método fromDescricao
        return Categoria.fromDescricao(textoLimpo);
    }
}