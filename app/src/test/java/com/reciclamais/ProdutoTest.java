package com.reciclamais;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.reciclamais.model.Produto;

import java.util.List;

public class ProdutoTest {
    List<String> passos1 = List.of("Cortar caixa");

    List<String> materiais1 = List.of("caixa");

    List<String> tags1 = List.of("Papelao");

    @Test
    public void testProdutoCreation() {
        Produto produto = new Produto("Teste", "Nível: dificil", "key", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5dV05o95PP7ldtCqyU5p5P6LV1N4cCv9DAaVEf5NWiG_dRjG9", passos1, materiais1, tags1);
        assertEquals("Teste", produto.getNome());
        assertEquals("Nível: dificil", produto.getNivel());
        assertEquals("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ5dV05o95PP7ldtCqyU5p5P6LV1N4cCv9DAaVEf5NWiG_dRjG9", produto.getImagem());
        assertEquals(passos1, produto.getPassos());
        assertEquals(materiais1, produto.getMateriais());
        assertEquals(tags1, produto.getTags());
    }
}
