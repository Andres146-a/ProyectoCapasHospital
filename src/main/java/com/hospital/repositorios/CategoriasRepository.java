package com.hospital.repositorios;

import java.util.List;

public interface CategoriasRepository {
    void guardarCategoria(String categoria);
    void eliminarCategoria(String categoria);
    List<String> listarCategorias();
}
