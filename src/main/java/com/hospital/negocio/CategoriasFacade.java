package com.hospital.negocio;

import com.hospital.repositorios.CategoriasRepository;
import com.hospital.repositorios.CategoriasRepositoryMySQL;

import java.util.List;

public class CategoriasFacade {
    private final CategoriasRepository repository = new CategoriasRepositoryMySQL();

    public void guardarCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }
        repository.guardarCategoria(categoria);
    }

    public void eliminarCategoria(String categoria) {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía.");
        }
        repository.eliminarCategoria(categoria);
    }

    public List<String> listarCategorias() {
        return repository.listarCategorias();
    }
}
