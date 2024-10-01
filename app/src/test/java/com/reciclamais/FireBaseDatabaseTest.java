package com.reciclamais;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reciclamais.model.Produto;

import org.junit.Before;
import org.junit.Test;

public class FireBaseDatabaseTest {

    private DatabaseReference databaseReference;

    @Before
    public void setUp() {
        // Inicializa o Firebase Realtime Database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("produtos_test");
    }

    @Test
    public void adicionarProdutoAoFirebase() {
        // Cria um novo produto
        Produto produto = new Produto("Foguete", "Fácil", R.drawable.foguete);

        // Adiciona o produto ao Firebase
        databaseReference.push().setValue(produto).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                System.out.println("Produto adicionado com sucesso.");
            } else {
                System.err.println("Erro ao adicionar o produto.");
            }
        });

        // Aguarda até que a operação esteja completa
        try {
            Thread.sleep(3000); // Tempo para garantir a execução do listener
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
