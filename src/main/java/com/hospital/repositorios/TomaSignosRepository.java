package com.hospital.repositorios;

import com.hospital.modelos.TomaSignos;
import java.util.List;

public interface TomaSignosRepository {
    void guardarTomaSignos(TomaSignos tomaSignos);
    List<TomaSignos> listarTodos();
}
