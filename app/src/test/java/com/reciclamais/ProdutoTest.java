package com.reciclamais;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.reciclamais.model.Produto;

public class ProdutoTest {

    @Test
    public void testProdutoCreation() {
        Produto produto = new Produto("Foguete", "Nível: dificil", R.drawable.foguete);
        assertEquals("Teste", produto.getNome());
        assertEquals("Nível: dificil", produto.getNivel());
        assertEquals(R.drawable.foguete, produto.getImagem());
    }
}
